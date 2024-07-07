package ru.alex9043.sushiapp.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.alex9043.sushiapp.DTO.user.JwtAuthenticationResponseDTO;
import ru.alex9043.sushiapp.DTO.user.SignInRequestDTO;
import ru.alex9043.sushiapp.DTO.user.SignUpRequestDTO;
import ru.alex9043.sushiapp.model.product.Role;
import ru.alex9043.sushiapp.model.user.User;
import ru.alex9043.sushiapp.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public JwtAuthenticationResponseDTO signUp(SignUpRequestDTO request) {
        User user = modelMapper.map(request, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_USER);

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
