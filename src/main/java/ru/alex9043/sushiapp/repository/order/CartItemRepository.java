package ru.alex9043.sushiapp.repository.order;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alex9043.sushiapp.model.order.Cart;
import ru.alex9043.sushiapp.model.order.CartItem;

import java.util.Set;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Set<CartItem> findAllByCart(Cart cart);
}