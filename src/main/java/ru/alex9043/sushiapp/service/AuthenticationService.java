package ru.alex9043.sushiapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.alex9043.sushiapp.DTO.user.JwtAuthenticationResponseDTO;
import ru.alex9043.sushiapp.DTO.user.SignInRequestDTO;
import ru.alex9043.sushiapp.DTO.user.SignUpRequestDTO;
import ru.alex9043.sushiapp.model.product.Role;
import ru.alex9043.sushiapp.model.user.Address;
import ru.alex9043.sushiapp.model.user.User;
import ru.alex9043.sushiapp.repository.user.AddressRepository;
import ru.alex9043.sushiapp.repository.user.DistrictRepository;
import ru.alex9043.sushiapp.repository.user.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserRepository repository;
    private final AddressRepository addressRepository;
    private final DistrictRepository districtRepository;

    public JwtAuthenticationResponseDTO signUp(SignUpRequestDTO request) {
        if (!request.getConfirmPassword().equals(request.getPassword())) {
            throw new IllegalArgumentException("Password mismatch!");
        }
        User user = new User();
        user.setName(request.getName());
        user.setPhone(request.getPhone());
        user.setEmail(request.getEmail());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ROLE_USER);

        Address address = new Address();
        address.setName(request.getAddressName());
        address.setDistrict(districtRepository.findById(request.getDistrictId()).orElseThrow(
                () -> new IllegalArgumentException("District not found")));
        address.setStreet(request.getStreet());
        address.setHouseNumber(request.getHouseNumber());
        address.setBuilding(request.getBuilding());
        address.setEntrance(request.getEntrance());
        address.setFloor(request.getFloor());
        address.setApartmentNumber(request.getApartmentNumber());

        Address savedAddress = addressRepository.save(address);

        user.getAddresses().add(savedAddress);

        User savedUser = repository.save(user);

        return JwtAuthenticationResponseDTO.builder()
                .token(jwtService.generateToken(savedUser))
                .build();
    }

    public JwtAuthenticationResponseDTO signIn(SignInRequestDTO request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getPhone(),
                request.getPassword()
        ));

        UserDetails userDetails = userService.userDetailsService().loadUserByUsername(request.getPhone());
        String token = jwtService.generateToken(userDetails);
        return JwtAuthenticationResponseDTO.builder()
                .token(token)
                .build();
    }
}
