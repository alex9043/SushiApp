package ru.alex9043.sushiapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.alex9043.sushiapp.model.user.User;
import ru.alex9043.sushiapp.repository.user.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDetailsService userDetailsService() {
        return this::getByPhone;
    }

    private User getByPhone(String phone) {
        return userRepository.findByPhone(phone).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
