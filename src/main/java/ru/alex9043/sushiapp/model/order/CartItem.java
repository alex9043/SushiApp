package ru.alex9043.sushiapp.model.order;

import jakarta.persistence.*;
import lombok.*;
import ru.alex9043.sushiapp.model.product.Product;

@Getter
@Setter
@Entity
@Table(name = "cart_item")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private Integer count;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cart_id", nullable = false)
    private Cart cart;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

}