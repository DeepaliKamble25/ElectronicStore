package com.electronic.store.controller;

import com.electronic.store.dto.CategoryDto;
import com.electronic.store.dto.ProductDto;
import com.electronic.store.model.Product;
import com.electronic.store.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    private Product product;
    @MockBean
    private ProductService productService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MockMvc mockMvc;


    @BeforeEach
    public void init(){
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
    }
    public void createProductDtoTest() throws Exception {

       ProductDto productDto=  this.modelMapper.map(product, ProductDto.class);
        Mockito.when(productService.createProductDto(Mockito.any())).thenReturn(productDto);
        this.mockMvc.perform
                        (MockMvcRequestBuilders.post("/products/save")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJson(product))
                                .accept(MediaType.APPLICATION_JSON)
                        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());
    }

    private String convertObjectToJson(Object product) {
        try {
            return new ObjectMapper().writeValueAsString(product);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


}
