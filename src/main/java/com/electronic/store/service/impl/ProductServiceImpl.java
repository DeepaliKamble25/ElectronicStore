package com.electronic.store.service.impl;

import com.electronic.store.dto.ProductDto;
import com.electronic.store.exception.ResourceNotFoundException;
import com.electronic.store.helper.Helper;
import com.electronic.store.model.Category;
import com.electronic.store.model.Product;
import com.electronic.store.playload.ApiConstant;
import com.electronic.store.playload.PageableResponse;
import com.electronic.store.repository.CategoryRepository;
import com.electronic.store.repository.ProductRepository;
import com.electronic.store.service.ProductService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Value("${product.image.path}")
    private String productPath;
    public Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public ProductDto createProductDto(ProductDto productDto) {
        logger.info("");
        String productId = UUID.randomUUID().toString();
        productDto.setProductId(productId);
        productDto.setAddeddate(new Date());

        Product product = this.modelMapper.map(productDto, Product.class);
        Product savedProduct = this.productRepository.save(product);
        logger.info("");
        return this.modelMapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public ProductDto updateProductDto(ProductDto productDto, String productId) {
        logger.info("" + productId);
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(ApiConstant.PRODUCT_Not_Found + productId));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setAddeddate(new Date());
        product.setLive(productDto.isLive());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setStock(productDto.isStock());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setProductImageName(productDto.getProductImageName());
        Product savedProduct = this.productRepository.save(product);
        logger.info("" + productId);
        return this.modelMapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public void deleteProductDto(String productId) {
        logger.info("" + productId);
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(ApiConstant.PRODUCT_Not_Found + productId));
        String productImageName = product.getProductImageName();
        String fullpath= productPath+productImageName;
        try {


                Path path= Paths.get(fullpath);
                Files.delete(path);
        } catch(NoSuchFileException ex)
        {
            logger.info("User image not found in folder ");

            ex.printStackTrace();

        }catch (IOException e)
        {
            e.printStackTrace();
        }

        this.productRepository.delete(product);
        logger.info("Completing request to Deleted UserById "+productId);
    }

    @Override
    public PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        logger.info(":  {}");
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> productPage = this.productRepository.findAll(pageable);
        PageableResponse<ProductDto> response = Helper.getPageableResponse(productPage, ProductDto.class);
        return response;
    }

    @Override
    public ProductDto getSingleProductDto(String productId) {
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(ApiConstant.PRODUCT_Not_Found + productId));

        return this.modelMapper.map(product, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllLiveProductDto(int pageNumber, int pageSize, String sortBy, String sortDir) {
       Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
       //manually written pageable
        Pageable pageable  = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> productPageableResponse = this.productRepository.findByLiveTrue(pageable);

        return Helper.getPageableResponse(productPageableResponse,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> searchByTilte(String subTitle, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        //manually written pageable
        Pageable pageable  = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> productPage = this.productRepository.findByTitleContaining(subTitle, pageable);

        return Helper.getPageableResponse(productPage,ProductDto.class);
    }

    @Override
    public ProductDto createWithCategory(ProductDto productDto, String categoryId) {
        logger.info("");
        //category get from database
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(ApiConstant.CSTEGORY_NOT_FOUND + categoryId));
      Product product= this.modelMapper.map(productDto,Product.class);
        String productId = UUID.randomUUID().toString();
        product.setProductId(productId);
        //added set category;
        product.setAddeddate(new Date());
        product.setCategory(category);
        Product savedProduct = this.productRepository.save(product);
        logger.info("");
        return this.modelMapper.map(savedProduct, ProductDto.class);


    }

    @Override
    public ProductDto updatewithCategory(String productId, String categoryId) {
        //get product
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(ApiConstant.PRODUCT_Not_Found + productId));


        //get category
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(ApiConstant.CSTEGORY_NOT_FOUND + categoryId));
      //set category in product
       product.setCategory(category);
       //save in product repo category


        Product savedProduct = this.productRepository.save(product);
        logger.info("" + productId);
        return this.modelMapper.map(savedProduct, ProductDto.class);

    }

    @Override
    public PageableResponse<ProductDto> getallCategories(String categoryId, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(ApiConstant.CSTEGORY_NOT_FOUND + categoryId));
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        //manually written pageable
        Pageable pageable  = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> productPage = this.productRepository.findByCategory(category,pageable);

        return Helper.getPageableResponse(productPage,ProductDto.class);
    }


}
