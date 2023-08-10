package com.electronic.store.service.impl;

import com.electronic.store.dto.AddItemToCartRequest;
import com.electronic.store.dto.CartDto;
import com.electronic.store.dto.CartItemDto;
import com.electronic.store.exception.ResourceNotFoundException;
import com.electronic.store.model.Cart;
import com.electronic.store.model.CartItem;
import com.electronic.store.model.Product;
import com.electronic.store.model.User;
import com.electronic.store.playload.ApiConstant;
import com.electronic.store.repository.CartRepository;
import com.electronic.store.repository.ProductRepository;
import com.electronic.store.repository.UserRepository;
import com.electronic.store.service.CartService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private static Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CartDto addToCart(String userId, AddItemToCartRequest request) {
        logger.info("Initiating request to addToCart: {}", userId);
        String productId = request.getProductId();
        int quantity = request.getQuantity();
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(ApiConstant.PRODUCT_Not_Found + productId));
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(ApiConstant.User_Not_Found + userId));

        Cart cart = null;
        try {
            cart = cartRepository.findByUser(user).get();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            cart = new Cart();
            cart.setCardId(UUID.randomUUID().toString());
            cart.setCardCreatedDate(new Date());
        }
//      we get cart know  perform cart operation
        //if cart items already present update

        AtomicReference<Boolean> updated=new AtomicReference<>(false);

        List<CartItem> items = cart.getItems();
        List<CartItem> updatedItems = items.stream().map(iTem ->
                {
                    if (iTem.getProduct().getProductId().equals(productId))
                    {
                        iTem.setQuantity(quantity);
                        iTem.setTotalPrice(quantity * product.getPrice());
                        updated.set(true);
                    }
                    //iTem alreay present in the cart
                    return iTem;
                }).collect(Collectors.toList());
        cart.setItems(updatedItems);


        if (!updated.get() ) {
            CartItem cartItem = CartItem.builder()
                    .quantity(quantity)
                    .totalPrice(quantity * product.getPrice())
                    .cart(cart)
                    .product(product)
                    .build();
            cart.getItems().add(cartItem);
        }
        cart.setUser(user);
        Cart cartSaved = cartRepository.save(cart);


        logger.info("Completing request to  addToCart: {}", userId);
        return this.modelMapper.map(cartSaved, CartDto.class);
    }

    @Override
    public void removeItemFromCart(String userId, int cartItemId) {

    }

    @Override
    public void removeAllItemfromCart(String userId) {

    }
}
