package ru.alex9043.sushiapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.alex9043.sushiapp.DTO.AccountRequestDTO;
import ru.alex9043.sushiapp.DTO.user.PasswordRequestDTO;
import ru.alex9043.sushiapp.DTO.user.UserResponseDTO;
import ru.alex9043.sushiapp.model.user.User;
import ru.alex9043.sushiapp.repository.user.UserRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserResponseDTO getUserProfile(UserDetails userDetails) {
        User user = userService.getUserByPhone(userDetails.getUsername());
        return modelMapper.map(user, UserResponseDTO.class);
    }

    public UserResponseDTO changeUserProfile(UserDetails userDetails, AccountRequestDTO account) {
        User user = userService.getUserByPhone(userDetails.getUsername());
        user.setName(account.getName());
        user.setEmail(account.getEmail());
        user.setPhone(account.getPhone());
        user.setDateOfBirth(account.getDateOfBirth());
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserResponseDTO.class);
    }

    public UserResponseDTO changePassword(UserDetails userDetails, PasswordRequestDTO passwordRequestDTO) {
        User user = userService.getUserByPhone(userDetails.getUsername());
        log.debug("Pass - {}", passwordRequestDTO.toString());
        log.debug("Old pass - {}", user.getPassword());
        if (Objects.equals(passwordRequestDTO.getPassword(), passwordRequestDTO.getConfirmPassword())) {
            if (passwordEncoder.matches(passwordRequestDTO.getOldPassword(), user.getPassword())) {
                user.setPassword(passwordEncoder.encode(passwordRequestDTO.getPassword()));
                User savedUser = userRepository.save(user);
                return modelMapper.map(savedUser, UserResponseDTO.class);
            } else {
                throw new IllegalArgumentException("Current password is incorrect");
            }
        } else {
            throw new IllegalArgumentException("Passwords do not match");
        }
    }
}
