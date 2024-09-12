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
import ru.alex9043.sushiapp.model.address.Address;
import ru.alex9043.sushiapp.model.address.District;
import ru.alex9043.sushiapp.model.user.RefreshToken;
import ru.alex9043.sushiapp.model.user.Role;
import ru.alex9043.sushiapp.model.user.User;
import ru.alex9043.sushiapp.repository.user.AddressRepository;
import ru.alex9043.sushiapp.repository.user.DistrictRepository;
import ru.alex9043.sushiapp.repository.user.RefreshTokenRepository;
import ru.alex9043.sushiapp.repository.user.UserRepository;

import java.util.Date;
import java.util.Objects;

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

    @Transactional
    public JwtAuthenticationResponseDTO signUp(SignUpRequestDTO request) {
        if (!Objects.equals(request.getPassword(), request.getConfirmPassword())) {
            throw new IllegalArgumentException("Password mismatch");
        }

        if (userRepository.findByPhone(request.getPhone()).isPresent()) {
            throw new IllegalArgumentException("Phone already registered");
        }

        User user = modelMapper.map(request, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_USER);
        user.setId(null);
        log.debug("User in request - {}", user);

        District district = districtRepository.findById(request.getDistrictId()).orElseThrow(
                () -> new IllegalArgumentException("District not found"));

        log.debug("District in request - {}", district.toString());

        Address address = modelMapper.map(request, Address.class);
        address.setDistrict(district);
        address.setId(null);
        address.setName(request.getAddressName());

        log.debug("Address in request - {}", address);

        User savedUser = userRepository.save(user);
        log.debug("Saved user id - {}", savedUser.getId());

        address.setUser(savedUser);

        addressRepository.save(address);

        return JwtAuthenticationResponseDTO.builder()
                .accessToken(jwtService.generateAccessToken(savedUser))
                .refreshToken(jwtService.generateRefreshToken(savedUser))
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
