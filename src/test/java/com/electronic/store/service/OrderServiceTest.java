package com.electronic.store.service;

import com.electronic.store.dto.CreateOrderRequest;
import com.electronic.store.dto.OrderDto;
import com.electronic.store.model.Order;
import com.electronic.store.model.OrderItem;
import com.electronic.store.model.Product;
import com.electronic.store.model.User;
import com.electronic.store.repository.CartRepository;
import com.electronic.store.repository.OrderItemRepository;
import com.electronic.store.repository.OrderRepository;
import com.electronic.store.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;

import java.util.Date;
import java.util.UUID;

@SpringBootTest
public class OrderServiceTest {

    @MockBean
    private UserRepository userRepository;
    @Autowired
    private OrderService orderService;
    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private CartRepository cartRepository;
    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private OrderItemRepository orderItemRepository;

    Product product;
    OrderItem orderItem;
    OrderDto orderDto;
    Order order;

CreateOrderRequest createOrderRequest;

    User user;

    @BeforeEach
    public void init() {
        user = User.builder()
                .name("Aarti")
                .email("aarti@gmail.com")
                .about("this is teating user method for servive")
                .gender("female")
                .image("asd.jpeg")
                .password("asdD25")
                .build();

        String name = user.getName();
        product = Product.builder()
                .title("Light test")
                .description("I am developer")
                .live(true)
                .price(1600)
                .addeddate(new Date())
                .discountedPrice(1400)
                .quantity(1)
                .productImageName("oops.jpeg")
                .stock(true)
                .build();
        OrderDto.builder().orderedDate(new Date())
                .orderStatus("Pending")
                .billingName("Aarti")
                .billingAddress("Hingoli")
                .billingPhone("65413698")
                .paymentStatus("done")
                .orderAmount(20000).build();

orderItem=OrderItem.builder()
        .product(product)
        .quantity(1)
        .orderItemId(1)
        .totalPrice(1400)
        .build();

createOrderRequest=CreateOrderRequest.builder()
        .orderStatus("Pending")
        .billingAddress("Hingoli")
        .billingName("Noora")
        .billingPhone("987456321")
        .paymentStatus("pending").build();
     order = Order.builder()
                .billingName(orderDto.getBillingName())
                .billingAddress(orderDto.getBillingAddress())
                .billingPhone(orderDto.getBillingPhone())
                .orderedDate(new Date())
                .deliveredDate(null)
                .paymentStatus(orderDto.getPaymentStatus())
                .orderStatus(orderDto.getOrderStatus())
                .orderId(UUID.randomUUID().toString())
                .user(user)
                .build();
        order.setBillingName(name);
    }
@Test
public void createOrderTest(){
    Mockito.when(orderRepository.save(Mockito.any())).thenReturn(order);
    OrderDto order1 = orderService.createOrder(createOrderRequest);

    Assertions.assertEquals("Hingoli",order1.getBillingAddress());
}
}
