package com.electronic.store.controller;

import com.electronic.store.dto.CreateOrderRequest;
import com.electronic.store.dto.OrderDto;
import com.electronic.store.playload.ApiConstant;
import com.electronic.store.playload.ApiResponse;
import com.electronic.store.playload.PageableResponse;
import com.electronic.store.service.OrderService;
import org.aspectj.lang.annotation.control.CodeGenerationHint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private static Logger logger= LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @PostMapping("/save")
    public ResponseEntity<OrderDto> createOrder(@RequestBody CreateOrderRequest orderRequest) {
        logger.info("Entering the request for save OrderDto data" );

        OrderDto order = this.orderService.createOrder(orderRequest);
        logger.info("Entering the request for save OrderDto data" );
        return new ResponseEntity<>(order, HttpStatus.CREATED);

    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponse> removeOrder(@PathVariable String orderId) {
        logger.info("Entering the request for remove/delete OrderDto {} :",orderId );
        this.orderService.removeOrder(orderId);
        ApiResponse response = ApiResponse.builder().message(ApiConstant.Order_DELETED).success(true).status(HttpStatus.OK).build();
        logger.info("Completed the request for remove/delete OrderDto {} :",orderId );
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<OrderDto>> getOrdersOfUser(String userId){
        logger.info("Initialize request to get order by userId : {}",userId);
        List<OrderDto> ordersOfUser = this.orderService.getOrdersOfUser(userId);
        logger.info("Completed request to get order by  userId : {}",userId);
        return new ResponseEntity<>(ordersOfUser,HttpStatus.OK);
    }
      @GetMapping("/")
        public ResponseEntity<PageableResponse<OrderDto>> getallorder(
                @RequestParam(value="pageNumber",defaultValue ="0",required = true )int pageNumber,
                @RequestParam(value ="pageSize",defaultValue = "10",required = true)int pageSize,
                @RequestParam(value ="sortBy",defaultValue = "billingName",required = true)String sortBy,
                @RequestParam(value = "sortDir",defaultValue = "asc",required = true)String sortDir

      ){
          logger.info("Initiating request to getAllorder");
          return new ResponseEntity<>( this.orderService.getOrder(pageNumber, pageSize, sortBy, sortDir),HttpStatus.OK);

      }


}
