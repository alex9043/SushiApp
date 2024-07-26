package ru.alex9043.sushiapp.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alex9043.sushiapp.model.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPhone(String phone);
}