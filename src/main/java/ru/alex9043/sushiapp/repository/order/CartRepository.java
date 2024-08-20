package ru.alex9043.sushiapp.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alex9043.sushiapp.model.order.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}