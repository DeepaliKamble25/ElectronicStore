package com.electronic.store.service;

import com.electronic.store.dto.CreateOrderRequest;
import com.electronic.store.dto.OrderDto;
import com.electronic.store.playload.PageableResponse;

import java.util.List;

public interface OrderService {


    //create
    OrderDto createOrder(CreateOrderRequest orderDto);

    //remove
    void removeOrder(String orderId);

    //get orders of user
    List<OrderDto> getOrdersOfUser(String userId);

    //order
    PageableResponse<OrderDto> getOrder(int pageNumber, int pageSize, String sortBy, String sortDir);


    //order method related to order

}
