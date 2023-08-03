package com.electronic.store.service;

import com.electronic.store.dto.CategoryDto;
import com.electronic.store.dto.ProductDto;
import com.electronic.store.dto.UserDto;
import com.electronic.store.model.Category;
import com.electronic.store.model.Product;
import com.electronic.store.playload.PageableResponse;
import com.electronic.store.repository.CategoryRepository;
import com.electronic.store.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProductServiceTest {
    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper modelMapper;
    @MockBean
    private CategoryRepository categoryRepository;

    Product product;
    Category category;

    @BeforeEach
    public void init() {
        product = Product.builder()
                .title("Product test")
                .description("I am developer")
                .live(true)
                .price(2000)
                .addeddate(new Date())
                .discountedPrice(1800)
                .quantity(1)
                .productImageName("oops.jpeg")
                .stock(true)
                .build();
         category = Category.builder()
                .title("ProductSoapWeb")
                .description("wed server details working ")
                .coverImage("kkp.jpeg")
                .build();
    }

    @Test
    public void createProductDto() {
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);
        ProductDto productDto = this.modelMapper.map(product, ProductDto.class);
        ProductDto productDto1 = productService.createProductDto(productDto);
        Assertions.assertNotNull(productDto1);
    }

    @Test
    public void updateProductDto() {
        String productId = "";
        ProductDto productDto = ProductDto.builder()
                .title("Press 500watt")
                .description("new feature auto cloth deaction")
                .live(true)
                .price(2000)
                .addeddate(new Date())
                .discountedPrice(1800)
                .quantity(1)
                .productImageName("oops.jpeg")
                .stock(true)
                .build();
        Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);
        ProductDto productDto1 = productService.updateProductDto(productDto, productId);
        System.out.println(productDto1.getTitle());
        Assertions.assertNotNull(productDto1);
        Assertions.assertEquals(product.getTitle(), productDto1.getTitle(), "Test Fail for updated ProductDto !!!");

    }

    @Test
    public void deleteProductDto() {
        String productId = "adv";
        Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.of(product));
        productService.deleteProductDto(productId);
        Mockito.verify(productRepository, Mockito.times(1)).delete(product);
    }

    @Test
    public void getAllTest() {
        Product product1 = Product.builder()
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

        Product product2 = Product.builder()
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
        List<Product> productList = Arrays.asList(product, product2, product1);
        Page page = new PageImpl(productList);
        Mockito.when(productRepository.findAll((Pageable) Mockito.any())).thenReturn(page);

        PageableResponse<ProductDto> allProduct = productService.getAll(1, 2, "title", "asc");

        Assertions.assertEquals(3, allProduct.getContent().size());
    }

    @Test
    public void getSingleProductDtoTest() {
        String productId = "aarti123";
        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        ProductDto singleProductDto = productService.getSingleProductDto(productId);
        Assertions.assertNotNull(singleProductDto);
        Assertions.assertEquals(product.getTitle(), singleProductDto.getTitle(), "Product not found test case fail !!!");

    }

    @Test
    public void getAllLiveProductDtoTest() {
        boolean live = true;
        Product product1 = Product.builder()
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

        Product product2 = Product.builder()
                .title("Bulk test")
                .description("I am developer")
                .live(false)
                .price(2800)
                .addeddate(new Date())
                .discountedPrice(2500)
                .quantity(1)
                .productImageName("oops.jpeg")
                .stock(true)
                .build();
        List<Product> list = Arrays.asList(product, product2, product1);
        Page page = new PageImpl<>(list);
        Mockito.when(productRepository.findByLiveTrue((Pageable) Mockito.any())).thenReturn(page);
        PageableResponse<ProductDto> allLiveProductDto = productService.getAllLiveProductDto(1, 2, "title", "asc");
        Assertions.assertEquals(3, allLiveProductDto.getContent().size(), "Test Fail getAlllive !!!");

    }

    @Test
    public void searchByTilteTest() {
        String subTitle = "test";
        Product product1 = Product.builder()
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

        Product product2 = Product.builder()
                .title("Bulk test")
                .description("I am developer")
                .live(false)
                .price(2800)
                .addeddate(new Date())
                .discountedPrice(2500)
                .quantity(1)
                .productImageName("oops.jpeg")
                .stock(true)
                .build();
        List<Product> list = Arrays.asList(product, product2, product1);
        Page page = new PageImpl<>(list);
        Mockito.when(productRepository.findByTitleContaining(Mockito.anyString(), (Pageable) Mockito.any())).thenReturn(page);
        PageableResponse<ProductDto> dtoPageableResponse = productService.searchByTilte("subTitle", 1, 2, "title", "asc");
        Assertions.assertEquals(3, dtoPageableResponse.getContent().size(), "Content not match Test fail !!!");
    }

    @Test
    public void createWithCategoryTest() {


        String categoryId = "asdd12";

        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);
        ProductDto productDto = this.modelMapper.map(product, ProductDto.class);
        ProductDto productDto1 = productService.createWithCategory(productDto,categoryId);
        Assertions.assertNotNull(productDto1);
        Assertions.assertEquals(product.getTitle(),productDto1.getTitle(),"Test failfor createProduct with category !!!");
    }
    @Test
    public void updatewithCategoryTest(){
        CategoryDto categoryDto = this.modelMapper.map(category, CategoryDto.class);

        String productId = "";
        ProductDto productDto = ProductDto.builder()
                .title("Light test")
                .description("I am developer")
                .live(true)
                .price(1600)
                .addeddate(new Date())
                .discountedPrice(1400)
                .quantity(1)
                .productImageName("oops.jpeg")
                .stock(true)
                .category(categoryDto)
                .build();
        String categoryId = "asdd12";

        Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.of(product));
        Mockito.when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);

        ProductDto productDto1 = productService.updateProductDto(productDto, productId);
        System.out.println(productDto1.getTitle());
        Assertions.assertNotNull(productDto1);
        Assertions.assertEquals(product.getTitle(), productDto1.getTitle(), "Test Fail for updated ProductDto !!!");

    }

}
