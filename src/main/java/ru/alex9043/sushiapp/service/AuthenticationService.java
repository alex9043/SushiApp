package ru.alex9043.sushiapp.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.alex9043.sushiapp.DTO.user.JwtAuthenticationResponseDTO;
import ru.alex9043.sushiapp.DTO.user.RefreshTokenRequestDTO;
import ru.alex9043.sushiapp.DTO.user.SignInRequestDTO;
import ru.alex9043.sushiapp.DTO.user.SignUpRequestDTO;
import ru.alex9043.sushiapp.model.product.Role;
import ru.alex9043.sushiapp.model.user.Address;
import ru.alex9043.sushiapp.model.user.RefreshToken;
import ru.alex9043.sushiapp.model.user.User;
import ru.alex9043.sushiapp.repository.user.AddressRepository;
import ru.alex9043.sushiapp.repository.user.DistrictRepository;
import ru.alex9043.sushiapp.repository.user.RefreshTokenRepository;
import ru.alex9043.sushiapp.repository.user.UserRepository;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final DistrictRepository districtRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final ModelMapper modelMapper;
    private final AddressRepository addressRepository;

    public JwtAuthenticationResponseDTO signUp(SignUpRequestDTO request) {
        if (!request.getConfirmPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("Password mismatch!");
        }
        User user = modelMapper.map(request, User.class);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ROLE_USER);

        Address address = modelMapper.map(request, Address.class);
        address.setDistrict(districtRepository.findById(request.getDistrictId()).orElseThrow(
                () -> new IllegalArgumentException("District not found")));

        userRepository.save(user);
        address.setUser(user);

        addressRepository.save(address);

        return JwtAuthenticationResponseDTO.builder()
                .accessToken(jwtService.generateAccessToken(user))
                .refreshToken(jwtService.generateRefreshToken(user))
                .build();
    }

    @Transactional
    public JwtAuthenticationResponseDTO signIn(SignInRequestDTO request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getPhone(),
                request.getPassword()
        ));

        User user = userRepository.findByPhone(request.getPhone()).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));

        refreshTokenRepository.deleteByUser(user);

        return JwtAuthenticationResponseDTO.builder()
                .accessToken(jwtService.generateAccessToken(user))
                .refreshToken(jwtService.generateRefreshToken(user))
                .build();
    }

    @Transactional
    public JwtAuthenticationResponseDTO refreshToken(RefreshTokenRequestDTO refreshToken) {
        RefreshToken refresh_token = refreshTokenRepository.findByToken(
                refreshToken.getRefreshToken()).orElseThrow(
                () -> new IllegalArgumentException("Invalid refresh token"));

        if (refresh_token.getExpirationDate().before(new Date())) {
            throw new IllegalArgumentException("Refresh token expired");
        }

        refreshTokenRepository.delete(refresh_token);
        return JwtAuthenticationResponseDTO.builder()
                .accessToken(jwtService.generateAccessToken(refresh_token.getUser()))
                .refreshToken(jwtService.generateRefreshToken(refresh_token.getUser()))
                .build();
    }
}
