package ru.alex9043.sushiapp.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.alex9043.sushiapp.DTO.product.ProductRequestDTO;
import ru.alex9043.sushiapp.DTO.product.ProductResponseDTO;
import ru.alex9043.sushiapp.DTO.product.ProductsResponseDTO;
import ru.alex9043.sushiapp.service.ProductService;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Schema(description = "Product")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Get all products")
    @GetMapping
    public ProductsResponseDTO getProducts() {
        return productService.getProducts();
    }

    @Operation(summary = "Create a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ProductResponseDTO createProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO) {
        return productService.createProduct(productRequestDTO);
    }
}
