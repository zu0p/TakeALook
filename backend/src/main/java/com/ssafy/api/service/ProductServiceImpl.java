package com.ssafy.api.service;

import com.ssafy.api.request.product.ProductRegisterPostReq;
import com.ssafy.api.request.product.ProductUpdatePatchReq;
import com.ssafy.db.entity.Product;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.UserRepository;
import com.ssafy.db.repository.UserRepositorySupport;
import com.ssafy.db.repository.product.ProductRepository;
import com.ssafy.db.repository.product.ProductRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

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
    public Product createProduct(ProductRegisterPostReq productRegisterPostReq) {
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

    @Override
    public List<Product> getProductsByUID(String userId) {
        return null;
    }

    @Override
    public Product getProductByPID(Long productIndexId) {
        try{
            Product product = productRepositorySupport.findByProductId(productIndexId).get();
            return product;
        }catch (NoSuchElementException e){
            return null;
        }
    }

    @Override
    public Product updateProduct(Product product, ProductUpdatePatchReq productUpdatePatchReq) {
        product.setUser(userRepositorySupport.findUserByUserId(productUpdatePatchReq.getUserId()).get());
        product.setProductName(productUpdatePatchReq.getProductName());
        product.setBasePrice(productUpdatePatchReq.getBasePrice());
        product.setCategories(productUpdatePatchReq.getCategories());
        product.setDescription(productUpdatePatchReq.getDescription());
        product.setState(productUpdatePatchReq.getState());
        product.setImageUrl(productUpdatePatchReq.getImageUrl());
        product.setIsSold(productUpdatePatchReq.getIsSold());
        product.setRegistTime(productUpdatePatchReq.getRegistTime());
        product.setReserveTime(productUpdatePatchReq.getReserveTime());
        product.setRestrictTime(productUpdatePatchReq.getRestrictTime());
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long productIndexId) {
        Product product = productRepositorySupport.findByProductId(productIndexId).get();
        productRepository.delete(product);
    }

}
