package com.electronic.store.service.impl;

import com.electronic.store.dto.ProductDto;
import com.electronic.store.exception.ResourceNotFoundException;
import com.electronic.store.helper.Helper;
import com.electronic.store.model.Product;
import com.electronic.store.playload.ApiConstant;
import com.electronic.store.playload.PageableResponse;
import com.electronic.store.repository.ProductRepository;
import com.electronic.store.service.ProductService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

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
        Product savedProduct = this.productRepository.save(product);
        logger.info("" + productId);
        return this.modelMapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public void deleteProductDto(String productId) {
        logger.info("" + productId);
        Product product = this.productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException(ApiConstant.PRODUCT_Not_Found + productId));
        this.productRepository.delete(product);
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
}
