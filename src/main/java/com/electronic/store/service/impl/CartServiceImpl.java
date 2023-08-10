package com.electronic.store.service.impl;

import com.electronic.store.dto.AddItemToCartRequest;
import com.electronic.store.dto.CartDto;
import com.electronic.store.dto.CartItemDto;
import com.electronic.store.exception.BadApiRequest;
import com.electronic.store.exception.ResourceNotFoundException;
import com.electronic.store.model.Cart;
import com.electronic.store.model.CartItem;
import com.electronic.store.model.Product;
import com.electronic.store.model.User;
import com.electronic.store.playload.ApiConstant;
import com.electronic.store.repository.CartItemRepository;
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

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public CartDto addToCart(String userId, AddItemToCartRequest request) {
        logger.info("Initiating request to addToCart: {}", userId);
        String productId = request.getProductId();
        int quantity = request.getQuantity();
        logger.info("Initiating request to getquantity: {}", quantity);
        if (quantity <= 0) {
            throw new BadApiRequest(ApiConstant.Bad_ApiRequest_Quantity);
        }

//        fetch product
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(ApiConstant.PRODUCT_Not_Found + productId));
        //fetch user

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(ApiConstant.User_Not_Found + userId));

        Cart cart = null;
        try {
            logger.info("Initiating request to find user cart if present");
            cart = cartRepository.findByUser(user).get();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            logger.info("Initiating request to create user new cart ");
            cart = new Cart();
            cart.setCardId(UUID.randomUUID().toString());
            cart.setCardCreatedDate(new Date());
            logger.info("Completing request to create user new cart ");
        }
//      we get cart know  perform cart operation
        //if cart items already present update

        AtomicReference<Boolean> updated = new AtomicReference<>(false);

        List<CartItem> items = cart.getItems();
        List<CartItem> updatedItems = items.stream().map(iTem ->
        {
            if (iTem.getProduct().getProductId().equals(productId)) {
                logger.info("Initiating request to add iTem to cart ");
                iTem.setQuantity(quantity);
                iTem.setTotalPrice(quantity * product.getPrice());
                updated.set(true);
                logger.info("Completing request to added iTem to cart");
            }
            //iTem alreay present in the cart
            return iTem;
        }).collect(Collectors.toList());
        cart.setItems(updatedItems);


        if (!updated.get()) {
            logger.info("Initiating request to adding cartItem");
            CartItem cartItem = CartItem.builder()
                    .quantity(quantity)
                    .totalPrice(quantity * product.getPrice())
                    .cart(cart)
                    .product(product)
                    .build();
            cart.getItems().add(cartItem);
        }
        logger.info("Completing request to added cartItem");
        cart.setUser(user);
        Cart cartSaved = cartRepository.save(cart);


        logger.info("Completing request to  addToCart: {}", userId);
        return this.modelMapper.map(cartSaved, CartDto.class);
    }

    @Override
    public void removeItemFromCart(String userId, int cartItemId) {
        logger.info("Initiating request to removeAllItemfromCart: {}", userId+cartItemId);
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new ResourceNotFoundException(ApiConstant.CARTITEMS_NOT_FOUND));
        cartItemRepository.delete(cartItem);
    }

    @Override
    public void removeAllItemfromCart(String userId) {
        logger.info("Initiating request to removeAllItemfromCart: {}", userId);
//fetch user
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(ApiConstant.User_Not_Found + userId));

        //fetch cart
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException(ApiConstant.CART_NOT_FOUND));
        cart.getItems().clear();
        cartRepository.save(cart);
        logger.info("Completing request to removeAllItemfromCart: {}", userId);
    }

    @Override
    public CartDto getCartByUser(String userId) {
        logger.info("Initiating request to getCartByUser: {}", userId);
        //fetch user
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException(ApiConstant.User_Not_Found + userId));
        //fetch cart
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException(ApiConstant.CART_NOT_FOUND));
        logger.info("Completing request to  getCartByUse: {}", userId);
        return this.modelMapper.map(cart,CartDto.class);
    }
}
