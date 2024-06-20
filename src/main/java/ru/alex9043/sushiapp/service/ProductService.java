package ru.alex9043.sushiapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.alex9043.sushiapp.DTO.product.ProductRequestDTO;
import ru.alex9043.sushiapp.DTO.product.ProductResponseDTO;
import ru.alex9043.sushiapp.DTO.product.ProductsResponseDTO;
import ru.alex9043.sushiapp.model.product.Product;
import ru.alex9043.sushiapp.repository.product.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;

    public ProductsResponseDTO getProducts() {
        log.info("Fetching all products");
        List<ProductResponseDTO> productResponseDTOList = productRepository.findAll().stream().map(
                p -> modelMapper.map(p, ProductResponseDTO.class)
        ).toList();
        log.debug("Found {} products", productResponseDTOList.size());

        return ProductsResponseDTO.builder()
                .products(productResponseDTOList)
                .build();
    }

    public ProductResponseDTO createProduct(ProductRequestDTO productRequestDTO) {
        log.info("Creating a new product with name: {}", productRequestDTO.getName());
        Product product = productRepository.save(modelMapper.map(productRequestDTO, Product.class));
        log.debug("Created product with ID: {}", product.getId());
        return modelMapper.map(product, ProductResponseDTO.class);
    }
}
