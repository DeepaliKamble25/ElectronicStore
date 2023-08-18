package com.electronic.store.service;

import com.electronic.store.dto.AddItemToCartRequest;
import com.electronic.store.dto.CartDto;
import com.electronic.store.model.Cart;
import com.electronic.store.model.CartItem;
import com.electronic.store.model.Product;
import com.electronic.store.model.User;
import com.electronic.store.repository.CartItemRepository;
import com.electronic.store.repository.CartRepository;
import com.electronic.store.repository.ProductRepository;
import com.electronic.store.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CartServiceTest {

    @Autowired
    private CartService cartService;
    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private CartRepository cartRepository;
    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private CartItemRepository cartItemRepository;
    Product product1;
    User user;
    String productId = "sfdf";
    CartItem cartItem;
    Cart cart;
    List<CartItem> items;

    @BeforeEach
    public void init() {

        AddItemToCartRequest request = new AddItemToCartRequest(productId, 1);
        product1 = Product.builder()
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
        user = User.builder()
                .name("Aarti")
                .email("aarti@gmail.com")
                .about("this is teating user method for servive")
                .gender("female")
                .image("asd.jpeg")
                .password("asdD25")
                .build();

        cartItem = CartItem.builder()
                .product(product1).cart(cart).quantity(1).totalPrice(1400).build();
        CartItem cartItems = CartItem.builder()
                .product(product1).cart(cart).quantity(1).totalPrice(1400).build();
        items = Arrays.asList(cartItems, cartItem);
        cart = Cart.builder().items(items).cardCreatedDate(new Date()).user(user).build();

    }

    @Test
    public void addToCartTest() {
        String userId = "dgf52";
        Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.of(product1));

        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));
        Mockito.when(cartRepository.save(Mockito.any())).thenReturn(cart);

        CartDto cartByUser = this.cartService.getCartByUser(userId);
        Assertions.assertNotNull(cartByUser);
        System.out.println(cartByUser.getItems());

    }

    @Test
    public void removeItemFromCartTest() {
        int cartItemId = 1;
        Mockito.when(cartItemRepository.findById(Mockito.any())).thenReturn(Optional.of(cartItem));

        cartService.removeItemFromCart(user.getUserId(), cartItemId);
        Mockito.verify(cartItemRepository, Mockito.times(1)).delete(cartItem);

    }
/*
    @Test
    public void removeAllItemfromCartTest() {
String userId="12589";

        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        System.out.println(user);
        Mockito.when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));
       cartService.removeAllItemfromCart(userId);
        System.out.println(cart);


        Mockito.when(cartRepository.save(Mockito.any())).thenReturn(Optional.of(cart));
       

    }*/
    @Test
    public void getCartByUserTest(){
        String userId="12589";

        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        System.out.println(user);
        Mockito.when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));
        CartDto cartByUser = cartService.getCartByUser(userId);
        System.out.println(cart.getUser().getName());
        Assertions.assertEquals("Aarti",cart.getUser().getName());

    }

}
