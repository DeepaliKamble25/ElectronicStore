package com.electronic.store.service.impl;

import com.electronic.store.dto.CreateOrderRequest;
import com.electronic.store.dto.OrderDto;
import com.electronic.store.dto.UserDto;
import com.electronic.store.exception.BadApiRequest;
import com.electronic.store.exception.ResourceNotFoundException;
import com.electronic.store.helper.Helper;
import com.electronic.store.model.*;
import com.electronic.store.playload.ApiConstant;
import com.electronic.store.playload.PageableResponse;
import com.electronic.store.repository.CartRepository;
import com.electronic.store.repository.OrderItemRepository;
import com.electronic.store.repository.OrderRepository;
import com.electronic.store.repository.UserRepository;
import com.electronic.store.service.OrderService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class OrderServiceImpl implements OrderService {
    private static Logger logger= LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public OrderDto createOrder(CreateOrderRequest orderDto) {
        String userId = orderDto.getUserId();
        String cardId = orderDto.getCardId();

        //fetch user

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(ApiConstant.User_Not_Found));
        //fetch cart
        Cart cart = cartRepository.findById(cardId).orElseThrow(() -> new ResourceNotFoundException(ApiConstant.CART_NOT_FOUND));
        List<CartItem> cartitems = cart.getItems();
        if (cartitems.size() <= 0) {
            throw new BadApiRequest(ApiConstant.CART_ITEMS_NULL);
        }
        Order order = Order.builder()
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
        AtomicReference<Integer> orderAmount=new AtomicReference<>(0);
        List<OrderItem> orderItemList = cartitems.stream().map(cartItem -> {
            //cartItems ==convert Orderitem
            OrderItem orderItem = OrderItem.builder()
                    .quantity(cartItem.getQuantity())
                    .product(cartItem.getProduct())
                    .totalPrice(cartItem.getQuantity() * cartItem.getProduct().getDiscountedPrice())
                    .order(order)
                    .build();

       orderAmount.set(orderAmount.get()+orderItem.getTotalPrice());

            return orderItem;
        }).collect(Collectors.toList());

        order.setOrdersItem(orderItemList);
        order.setOrderAmount(orderAmount.get());
        //clear cart
        cart.getItems().clear();
        cartRepository.save(cart);
        Order savedOrder = orderRepository.save(order);
        return this.modelMapper.map(savedOrder,OrderDto.class);

    }

    @Override
    public void removeOrder(String orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException(ApiConstant.Order_NOT_FOUND));
        orderRepository.delete(order);
    }

    @Override
    public List<OrderDto> getOrdersOfUser(String userId) {
        //fetch user
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(ApiConstant.User_Not_Found));

        List<Order> userOrderList = orderRepository.findByUser(user);
        List<OrderDto> orderDtoList = userOrderList.stream().map(p -> this.modelMapper.map(p, OrderDto.class)).collect(Collectors.toList());

        return orderDtoList;
    }

    @Override
    public PageableResponse<OrderDto> getOrder(int pageNumber, int pageSize, String sortBy, String sortDir) {

        logger.info("Initiating request to get Allorder");
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        // Sort sort = Sort.by(sortBy);
        //pageNumber default start from
        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);

        Page<User> page = this.userRepository.findAll(pageable);

        PageableResponse<OrderDto> response = Helper.getPageableResponse(page, OrderDto.class);

        logger.info("Completing request to get Allorder");
        return response;

    }
}
