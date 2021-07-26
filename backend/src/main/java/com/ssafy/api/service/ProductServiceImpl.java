package com.ssafy.api.service;

import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.request.product.ProductRegisterPostReq;
import com.ssafy.db.entity.Product;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;
import com.ssafy.db.repository.UserRepositorySupport;
import com.ssafy.db.repository.product.ProductRepository;
import com.ssafy.db.repository.product.ProductRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service("productService")
public class ProductServiceImpl implements ProductService{

    // Product Support
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductRepositorySupport productRepositorySupport;

    // User Support
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRepositorySupport userRepositorySupport;

    @Override
    public Product createUser(ProductRegisterPostReq productRegisterPostReq) {
        Product product = new Product();

        String userId = productRegisterPostReq.getUserId();
        User user = userRepositorySupport.findUserByUserId(userId).get();

        product.setUser(user);
        product.setProductName(productRegisterPostReq.getProductName());
        product.setBasePrice(productRegisterPostReq.getBasePrice());
        product.setCategories(productRegisterPostReq.getCategories());
        product.setDescription(productRegisterPostReq.getDescription());
        product.setState(productRegisterPostReq.getState());
        product.setImageUrl(productRegisterPostReq.getImageUrl());
        product.setIsSold(productRegisterPostReq.getIsSold());
        product.setRegistTime(productRegisterPostReq.getRegistTime());
        product.setReserveTime(productRegisterPostReq.getReserveTime());
        product.setRestrictTime(productRegisterPostReq.getRestrictTime());

        return productRepository.save(product);
    }
}
