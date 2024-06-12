package ru.alex9043.sushiapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.alex9043.sushiapp.DTO.products.ProductRequestDTO;
import ru.alex9043.sushiapp.DTO.products.ProductResponseDTO;
import ru.alex9043.sushiapp.DTO.products.ProductsResponseDTO;
import ru.alex9043.sushiapp.service.ProductService;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ProductsResponseDTO getProducts() {
        return productService.getProducts();
    }

    @PostMapping
    public ProductResponseDTO createProduct(@RequestBody ProductRequestDTO productRequestDTO) {
        return productService.createProduct(productRequestDTO);
    }
}
