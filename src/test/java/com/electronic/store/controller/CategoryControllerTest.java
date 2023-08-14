package com.electronic.store.controller;

import com.electronic.store.dto.CategoryDto;
import com.electronic.store.dto.ProductDto;
import com.electronic.store.dto.UserDto;
import com.electronic.store.model.Category;

import com.electronic.store.model.Product;
import com.electronic.store.playload.PageableResponse;
import com.electronic.store.service.CategoryService;
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
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {
    private Category category;
    private Product product;
    @MockBean
    private CategoryService categoryService;

    @MockBean
    private ProductService productService;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void init(){
         category = Category.builder().title("Mockito Test Case writting")
                .description("complusory Mocking the data ")
                .coverImage("oop.jpeg").build();
    }


    @Test
    public void createCategoryDtoTest() throws Exception {
        CategoryDto categoryDto = this.modelMapper.map(category, CategoryDto.class);
        Mockito.when(categoryService.createCategoryDto(Mockito.any())).thenReturn(categoryDto);
        this.mockMvc.perform
                        (MockMvcRequestBuilders.post("/categories/save")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(convertObjectToJson(category))
                                .accept(MediaType.APPLICATION_JSON)
                        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());

    }
    @Test
    public void updateCategoryDtoTest() throws Exception {
        CategoryDto categoryDto = this.modelMapper.map(category, CategoryDto.class);
        String categoryId = "De12";
        Mockito.when(categoryService.updateCategoryDto(Mockito.any(),Mockito.anyString())).thenReturn(categoryDto);
        mockMvc.perform(MockMvcRequestBuilders.put("/categories/updated/" + categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(category))
                        .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").exists());
    }
    @Test
    public void getallCategoryDtoTest() throws Exception {
       CategoryDto c1= CategoryDto.builder().title("software engg").description("java developer description").coverImage("oop.jpeg").build();
        CategoryDto c2= CategoryDto.builder().title("computer engg").description("sb description").coverImage("oop.jpeg").build();
        CategoryDto c3= CategoryDto.builder().title("It engg").description("graphic designer description").coverImage("oop.jpeg").build();

        PageableResponse<CategoryDto> pageableReasponse = new PageableResponse<>();
        pageableReasponse.setContent(Arrays.asList(c1,c2,c3));
        pageableReasponse.setLastPage(false);
        pageableReasponse.setPageNumber(100);
        pageableReasponse.setTotalElements(1000);
        pageableReasponse.setPageSize(10);
        Mockito.when(categoryService.getAllCategories(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString(), Mockito.anyString())).thenReturn(pageableReasponse);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/categories/getall")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

    }
    @Test
    public void searchCategoriesByTitleTest() throws Exception {
        CategoryDto c1= CategoryDto.builder().title("software engg").description("java developer description").coverImage("oop.jpeg").build();
        CategoryDto c2= CategoryDto.builder().title("computer engg").description("sb description").coverImage("oop.jpeg").build();
        CategoryDto c3= CategoryDto.builder().title("It engg").description("graphic designer description").coverImage("oop.jpeg").build();

        String keywords = "Pra";
        //UserDto userDto1=new UserDto("asdf","dip","")
        List<CategoryDto> list = Arrays.asList(c1,c2,c3);

        Mockito.when(categoryService.searchByCategoryId(keywords)).thenReturn(list);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/categories/" + keywords)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

    }
    @Test
    public void getCategoryDtoByIdTest() throws Exception {
        CategoryDto categoryDto = modelMapper.map(category, CategoryDto.class);
        String categoryId="asd";
        this.mockMvc.perform(MockMvcRequestBuilders.get("/categories/getcategory" + categoryId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());

    }
   /* @Test
    public void updateCategoryInProductTest() throws Exception {
         product = Product.builder().category(category).title("Jio service").description("broadBand Network Service")
                .price(10000).discountedPrice(9800).live(true).stock(true).productImageName("oip.jpeg").quantity(1).build();
        ProductDto productDto = this.modelMapper.map(product, ProductDto.class);
        Product product = this.modelMapper.map(productDto, Product.class);
        String categoryId="asd";
        String productId="asdf";
        Mockito.when(productService.updatewithCategory(Mockito.anyString(),Mockito.anyString())).thenReturn(productDto);

       this.mockMvc.perform(MockMvcRequestBuilders.put("/categories/{categoryId}/products/" + categoryId+productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJson(category))
                        .accept(MediaType.APPLICATION_JSON)
                ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").exists());
    }*/


    private String convertObjectToJson(Object category) {
        try {
            return new ObjectMapper().writeValueAsString(category);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}








