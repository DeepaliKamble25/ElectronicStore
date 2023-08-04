package com.electronic.store.controller;

import com.electronic.store.dto.CategoryDto;
import com.electronic.store.dto.ProductDto;
import com.electronic.store.dto.UserDto;
import com.electronic.store.model.Category;
import com.electronic.store.model.Product;
import com.electronic.store.playload.PageableResponse;
import com.electronic.store.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
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

Product product1;
    Product product2;

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

        product2 = Product.builder()
                .title("Bulk test")
                .description("I am developer")
                .live(true)
                .price(2800)
                .addeddate(new Date())
                .discountedPrice(2500)
                .quantity(1)
                .productImageName("oops.jpeg")
                .stock(true)
                .build();
    }
    @Test
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
    @Test
    public void updateProductDtoTest() throws Exception {
        ProductDto productDto = this.modelMapper.map(product, ProductDto.class);
        String productId="mnop";
        Mockito.when(productService.updateProductDto(Mockito.any(),Mockito.anyString())).thenReturn(productDto);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/products/update/" + productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(product))
                        .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());
    }
    @Test
    public void getAllProductTest() throws Exception {
        ProductDto p= this.modelMapper.map(product,ProductDto.class);
        ProductDto p1= this.modelMapper.map(product1,ProductDto.class);
        ProductDto p2= this.modelMapper.map(product2,ProductDto.class);
        PageableResponse<ProductDto> pageableReasponse = new PageableResponse<>();
        pageableReasponse.setContent(Arrays.asList(p1,p2,p));
        pageableReasponse.setLastPage(false);
        pageableReasponse.setPageNumber(100);
        pageableReasponse.setTotalElements(1000);
        pageableReasponse.setPageSize(10);
        Mockito.when(productService.getAll(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pageableReasponse);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/products/getall")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

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
