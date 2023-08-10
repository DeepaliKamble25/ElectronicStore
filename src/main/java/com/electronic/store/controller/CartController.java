package com.electronic.store.controller;

import com.electronic.store.dto.AddItemToCartRequest;
import com.electronic.store.dto.CartDto;

import com.electronic.store.playload.ApiConstant;
import com.electronic.store.playload.ApiResponse;
import com.electronic.store.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> addItemTOCartRequest(@PathVariable String userId, @RequestBody AddItemToCartRequest request) {

        CartDto cartDto = cartService.addToCart(userId, request);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/items/{cartItemId}")
    public ResponseEntity<ApiResponse> removeItemfromCart(@PathVariable String userId, @PathVariable int cartItemId) {

        cartService.removeItemFromCart(userId, cartItemId);

        ApiResponse response = ApiResponse.builder()
                .message(ApiConstant.Item_REMOVED)
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable String userId) {
        CartDto cartDto = cartService.getCartByUser(userId);
        return new ResponseEntity<>(cartDto, HttpStatus.OK);
    }

    @DeleteMapping("/getall/{userId}")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable String userId) {
        cartService.removeAllItemfromCart(userId);
        ApiResponse response = ApiResponse.builder()
                .message(ApiConstant.ALLItem_REMOVED)
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
