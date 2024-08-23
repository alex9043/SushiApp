package ru.alex9043.sushiapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.alex9043.sushiapp.DTO.order.cart.CartItemResponseDTO;
import ru.alex9043.sushiapp.DTO.order.cart.CartProductRequestDTO;
import ru.alex9043.sushiapp.DTO.order.cart.CartRequestDTO;
import ru.alex9043.sushiapp.DTO.order.cart.CartResponseDTO;
import ru.alex9043.sushiapp.model.order.Cart;
import ru.alex9043.sushiapp.model.order.CartItem;
import ru.alex9043.sushiapp.model.product.Product;
import ru.alex9043.sushiapp.model.user.User;
import ru.alex9043.sushiapp.repository.order.CartItemRepository;
import ru.alex9043.sushiapp.repository.order.CartRepository;
import ru.alex9043.sushiapp.repository.product.ProductRepository;
import ru.alex9043.sushiapp.repository.user.UserRepository;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {
    private final CartItemRepository cartItemRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;

    public CartResponseDTO getCartForUser(UserDetails userDetails) {
        User currentUser = userService.getUserByPhone(userDetails.getUsername());
        Cart cart = currentUser.getCart();

        if (cart != null) {
            Set<CartItem> cartItems = cartItemRepository.findAllByCart(cart);
            return getCartResponseDTO(cart, cartItems);
        }

        return null;
    }

    private CartResponseDTO getCartResponseDTO(Cart cart, Set<CartItem> cartItems) {
        return CartResponseDTO.builder()
                .id(cart.getId())
                .cartItems(cartItems.stream().map(
                        i -> modelMapper.map(i, CartItemResponseDTO.class)
                ).collect(Collectors.toSet()))
                .build();
    }

    public CartResponseDTO addProductIntoCart(UserDetails userDetails, CartProductRequestDTO cartProductRequestDTO) {
        User currentUser = userService.getUserByPhone(userDetails.getUsername());
        Cart cart = currentUser.getCart();
        if (cart != null) {
            Set<CartItem> cartItems = cartItemRepository.findAllByCart(cart);
            log.debug("Request product id - {}", cartProductRequestDTO.getProductId());
            Product product = productRepository.findById(cartProductRequestDTO.getProductId()).orElseThrow(
                    () -> new IllegalArgumentException("Product not found")
            );
            CartItem cartItem = cartItems.stream().filter(
                    i -> Objects.equals(i.getProduct(), product)
            ).findFirst().orElse(null);
            if (cartItem == null) {
                cartItem = CartItem.builder()
                        .count(1)
                        .product(product)
                        .cart(cart)
                        .build();
                cartItemRepository.save(cartItem);
                cartItems = cartItemRepository.findAllByCart(cart);
            } else {
                cartItem.setCount(cartItem.getCount() + 1);
                cartItemRepository.save(cartItem);
                cartItems = cartItemRepository.findAllByCart(cart);
            }
            return getCartResponseDTO(cart, cartItems);
        }
        cart = new Cart();
        cart = cartRepository.save(cart);
        currentUser.setCart(cart);
        userRepository.save(currentUser);
        Product product = productRepository.findById(cartProductRequestDTO.getProductId()).orElseThrow(
                () -> new IllegalArgumentException("Product not found")
        );
        CartItem cartItem = CartItem.builder()
                .count(1)
                .product(product)
                .cart(cart)
                .build();
        cartItemRepository.save(cartItem);
        Set<CartItem> cartItems = cartItemRepository.findAllByCart(cart);
        return getCartResponseDTO(cart, cartItems);
    }

    public CartResponseDTO removeProductIntoCart(UserDetails userDetails, CartProductRequestDTO cartProductRequestDTO) {
        User currentUser = userService.getUserByPhone(userDetails.getUsername());
        Cart cart = currentUser.getCart();
        if (cart != null) {
            Set<CartItem> cartItems = cartItemRepository.findAllByCart(cart);
            CartItem cartItem = cartItems.stream().filter(
                    i -> Objects.equals(i.getProduct().getId(), cartProductRequestDTO.getProductId())
            ).findFirst().orElse(null);
            if (cartItem == null) {
                throw new IllegalArgumentException("Product not found");
            } else {
                cartItem.setCount(cartItem.getCount() - 1);
                if (cartItem.getCount() <= 0) {
                    cartItemRepository.delete(cartItem);
                    if (cartItemRepository.findAllByCart(cart).isEmpty()) {
                        currentUser.setCart(null);
                        userRepository.save(currentUser);
                    }
                } else {
                    cartItemRepository.save(cartItem);
                }
                cartItems = cartItemRepository.findAllByCart(cart);
            }
            return getCartResponseDTO(cart, cartItems);
        }
        throw new IllegalArgumentException("Cart not found");
    }

    public ResponseEntity<String> removeCart(UserDetails userDetails) {
        User currentUser = userService.getUserByPhone(userDetails.getUsername());
        Cart cart = currentUser.getCart();
        if (cart != null) {
            currentUser.setCart(null);
            userRepository.save(currentUser);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    public CartResponseDTO refreshCart(UserDetails userDetails, CartRequestDTO cartRequestDTO) {
        User currentUser = userService.getUserByPhone(userDetails.getUsername());
        Cart cart = currentUser.getCart();
        if (cart != null) {
            currentUser.setCart(null);
            userRepository.save(currentUser);
        }
        Cart newCart = cartRepository.save(new Cart());

        cartRequestDTO.getCart().forEach(
                i -> {
                    CartItem cartItem = new CartItem();
                    cartItem.setCount(i.getCount());
                    cartItem.setProduct(productRepository.findById(i.getProductId()).orElseThrow(
                            () -> new IllegalArgumentException("Product not found")
                    ));
                    cartItem.setCart(newCart);
                    cartItemRepository.save(cartItem);
                }
        );

        currentUser.setCart(newCart);
        userRepository.save(currentUser);
        Set<CartItem> cartItems = cartItemRepository.findAllByCart(newCart);
        return getCartResponseDTO(newCart, cartItems);
    }
}
