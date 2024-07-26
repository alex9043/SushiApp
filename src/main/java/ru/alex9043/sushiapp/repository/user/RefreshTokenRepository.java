package ru.alex9043.sushiapp.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alex9043.sushiapp.model.user.RefreshToken;
import ru.alex9043.sushiapp.model.user.User;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    void deleteByUser(User user);
}