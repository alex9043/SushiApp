package ru.alex9043.sushiapp.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alex9043.sushiapp.model.product.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}