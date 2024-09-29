package ru.alex9043.sushiapp.controller.product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.alex9043.sushiapp.DTO.error.ErrorMessageDTO;
import ru.alex9043.sushiapp.DTO.product.product.ProductRequestDTO;
import ru.alex9043.sushiapp.DTO.product.product.ProductResponseDTO;
import ru.alex9043.sushiapp.DTO.product.product.ProductsResponseDTO;
import ru.alex9043.sushiapp.service.product.ProductService;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Schema(description = "Product")
@Validated
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Get all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products get successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductsResponseDTO.class))),
    })
    @GetMapping
    public ProductsResponseDTO getProducts() {
        return productService.getProducts();
    }

    @Operation(summary = "Create a new product",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product created successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "403", description = "Invalid credentials",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessageDTO.class)))
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product creation request",
            required = true, content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ProductRequestDTO.class)))
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ProductsResponseDTO createProduct(@Valid @RequestBody ProductRequestDTO productRequestDTO) {
        return productService.createProduct(productRequestDTO);
    }

    @Operation(summary = "Update product",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Product updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProductsResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "403", description = "Invalid credentials",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessageDTO.class)))
            })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Product updating request",
            required = true, content = @Content(mediaType = "application/json",
            schema = @Schema(implementation = ProductRequestDTO.class)))
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{productId}")
    public ProductsResponseDTO updateProduct(
            @Valid @RequestBody ProductRequestDTO productRequestDTO,
            @PathVariable(name = "productId") Long productId
    ) {
        return productService.updateProduct(productRequestDTO, productId);
    }

    @Operation(summary = "Delete product",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Product updated successfully",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProductsResponseDTO.class))),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "403", description = "Invalid credentials",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ErrorMessageDTO.class)))
            })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{productId}")
    public ProductsResponseDTO deleteProduct(
            @PathVariable(name = "productId") Long productId
    ) {
        return productService.deleteProduct(productId);
    }
}
