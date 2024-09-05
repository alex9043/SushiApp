package ru.alex9043.sushiapp.repository.order.order;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alex9043.sushiapp.model.order.order.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}