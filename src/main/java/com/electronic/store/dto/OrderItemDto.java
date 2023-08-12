package com.electronic.store.dto;



import lombok.*;

;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class OrderItemDto {

    private int orderItemId;

    private int quantity;
    private int totalPrice;

    private ProductDto productDto;

    private OrderDto orderDto;

}
