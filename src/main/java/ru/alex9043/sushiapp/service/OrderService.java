package ru.alex9043.sushiapp.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.alex9043.sushiapp.DTO.order.order.*;
import ru.alex9043.sushiapp.model.order.order.Order;
import ru.alex9043.sushiapp.model.order.order.OrderItem;
import ru.alex9043.sushiapp.model.order.order.OrderStatus;
import ru.alex9043.sushiapp.model.product.Product;
import ru.alex9043.sushiapp.model.user.User;
import ru.alex9043.sushiapp.repository.order.order.OrderRepository;
import ru.alex9043.sushiapp.repository.product.ProductRepository;
import ru.alex9043.sushiapp.repository.user.AddressRepository;
import ru.alex9043.sushiapp.repository.user.DistrictRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final AddressRepository addressRepository;
    private final DistrictRepository districtRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final UserService userService;

    private Order reformOrderForResponse(Order order) {
        order.setUserName(order.getUser().getName());
        order.setStreet(order.getAddress().getStreet());
        order.setHouseNumber(order.getAddress().getHouseNumber());
        order.setBuilding(order.getAddress().getBuilding());
        order.setEntrance(order.getAddress().getEntrance());
        order.setFloor(order.getAddress().getFloor());
        order.setApartmentNumber(order.getAddress().getApartmentNumber());
        return order;
    }

    private OrderResponseDTO createResponse(Order order) {
        return OrderResponseDTO.builder()
                .userName(order.getUserName())
                .street(order.getStreet())
                .houseNumber(order.getHouseNumber())
                .building(order.getBuilding())
                .entrance(order.getEntrance())
                .floor(order.getFloor())
                .apartmentNumber(order.getApartmentNumber())
                .deliveryType(order.getDeliveryType().toString())
                .paymentType(order.getPaymentType().toString())
                .orderItems(order.getOrderItems().stream().map(
                        i -> {
                            log.debug("Product id - {}", i.getId());
                            log.debug("Product count - {}", i.getCount());
                            log.debug("Product order id - {}", i.getOrder().getId());
                            OrderItemResponseDTO itemResponse = modelMapper.map(i, OrderItemResponseDTO.class);
                            itemResponse.setProduct(modelMapper.map(i.getProduct(), ProductInOrderItemResponseDTO.class));
                            return itemResponse;
                        }
                ).collect(Collectors.toSet()))
                .build();
    }

    public OrderResponseDTO createGuestOrder(GuestOrderRequestDTO guestOrderRequestDTO) {
        log.debug("order request - {}", guestOrderRequestDTO.toString());

        Order order = modelMapper.map(guestOrderRequestDTO, Order.class);
        order.setId(null);
        order.setUser(null);
        order.setIsUser(false);
        order.setDistrict(districtRepository.findById(guestOrderRequestDTO.getDistrictId()).orElseThrow(
                () -> new IllegalArgumentException("District not found")
        ));
        order.setOrderStatus(OrderStatus.CREATED);

        Set<OrderItem> orderItems = guestOrderRequestDTO.getOrderItems().stream().map(i -> {
            Product product = productRepository.findById(i.getId()).orElseThrow(
                    () -> new IllegalArgumentException("Product not found")
            );
            return OrderItem.builder()
                    .count(i.getCount())
                    .product(product)
                    .order(order)
                    .build();
        }).collect(Collectors.toSet());

        order.setOrderItems(orderItems);

        Order savedOrder = orderRepository.save(order);

        return createResponse(savedOrder);
    }

    @Transactional
    public OrderResponseDTO createUserOrder(UserDetails userDetails, UserOrderRequestDTO userOrderRequestDTO) {
        log.info("Create order fo user");
        User currentUser = userService.getUserByPhone(userDetails.getUsername());
        Order order = modelMapper.map(userOrderRequestDTO, Order.class);
        order.setId(null);
        order.setOrderStatus(OrderStatus.CREATED);
        order.setUser(currentUser);
        order.setIsUser(true);
        order.setAddress(addressRepository.findById(userOrderRequestDTO.getAddressId()).orElseThrow(
                () -> new IllegalArgumentException("Address not found")
        ));
        Set<OrderItem> orderItems = userOrderRequestDTO.getOrderItems().stream().map(
                i -> {
                    Product product = productRepository.findById(i.getId()).orElseThrow(
                            () -> new IllegalArgumentException("Product not found")
                    );
                    return OrderItem.builder()
                            .product(product)
                            .count(i.getCount())
                            .order(order)
                            .build();
                }
        ).collect(Collectors.toSet());
        order.setOrderItems(orderItems);
        Order savedOrder = orderRepository.save(order);

        Order responseOrder = reformOrderForResponse(savedOrder);

        return createResponse(responseOrder);
    }
}
