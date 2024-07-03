package ru.alex9043.sushiapp.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alex9043.sushiapp.model.product.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
}