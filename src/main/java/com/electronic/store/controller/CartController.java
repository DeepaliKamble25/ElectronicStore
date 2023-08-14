package com.electronic.store.controller;

import com.electronic.store.dto.AddItemToCartRequest;
import com.electronic.store.dto.CartDto;

import com.electronic.store.playload.ApiConstant;
import com.electronic.store.playload.ApiResponse;
import com.electronic.store.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {
 private static Logger logger= LoggerFactory.getLogger(CartController.class);
    @Autowired
    private CartService cartService;

    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> addItemTOCartRequest(@PathVariable String userId, @RequestBody AddItemToCartRequest request) {
   logger.info("Entering the request for save cartDto data ");
        CartDto cartDto = cartService.addToCart(userId, request);
        logger.info("Completed  the request for save cartDto data ");
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/items/{cartItemId}")
    public ResponseEntity<ApiResponse> removeItemfromCart(@PathVariable String userId, @PathVariable int cartItemId) {
        logger.info("Entering the request for remove/delete item from cartDto data {} :",userId +cartItemId);
        cartService.removeItemFromCart(userId, cartItemId);

        ApiResponse response = ApiResponse.builder()
                .message(ApiConstant.Item_REMOVED)
                .success(true)
                .status(HttpStatus.OK)
                .build();

        logger.info("Completed the request for remove/delete item from cartDto data {} :",userId +cartItemId);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable String userId) {
        logger.info("Entering the request for getCartDto  data {} :",userId );

        CartDto cartDto = cartService.getCartByUser(userId);
        logger.info("Completed the request for getCartDto  data {} :",userId );
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @DeleteMapping("/getall/{userId}")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable String userId) {
        logger.info("Entering the request for clearCartDto   from data {} :",userId );
        cartService.removeAllItemfromCart(userId);
        ApiResponse response = ApiResponse.builder()
                .message(ApiConstant.ALLItem_REMOVED)
                .success(true)
                .status(HttpStatus.OK)
                .build();
        logger.info("Completed the request for clearCartDto   from data {} :",userId );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
