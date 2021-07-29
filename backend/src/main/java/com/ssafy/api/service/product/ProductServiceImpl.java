package com.ssafy.api.service.product;

import com.ssafy.api.request.product.ProductRegisterPostReq;
import com.ssafy.api.request.product.ProductUpdatePatchReq;
import com.ssafy.api.response.product.ProductListGetRes;
import com.ssafy.api.service.user.UserService;
import com.ssafy.db.entity.Product;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.user.UserRepository;
import com.ssafy.db.repository.user.UserRepositorySupport;
import com.ssafy.db.repository.product.ProductRepository;
import com.ssafy.db.repository.product.ProductRepositorySupport;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service("productService")
public class ProductServiceImpl implements ProductService{
    @Autowired
    ProductService productService;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductRepositorySupport productRepositorySupport;
    @Autowired
    UserService userService;

    @Override
    public List<ProductListGetRes> getAllProduct(){
        List<ProductListGetRes> productList = productRepositorySupport.findAllProduct();
        return productList;
    }

    @Override
    public List<ProductListGetRes> getAllProductByUserId(String userId){
        List<ProductListGetRes> productList = productRepositorySupport.findByUserId(userId);
        return productList;
    }

    @Override
    public Product createProduct(String sellerId, ProductRegisterPostReq productRegisterPostReq) {
        Product product = new Product();
        User user = userService.getUserByUserId(sellerId);

        product.setUser(user);
        product.setProductName(productRegisterPostReq.getProductName());
        product.setBasePrice(productRegisterPostReq.getBasePrice());
        product.setCategories(productRegisterPostReq.getCategories());
        product.setDescription(productRegisterPostReq.getDescription());
        product.setState(productRegisterPostReq.getState());
        product.setImageUrl(productRegisterPostReq.getImageUrl());
        product.setIsSold(false);
        product.setRegistTime(productRegisterPostReq.getRegistTime());
        product.setReserveTime(productRegisterPostReq.getReserveTime());
        //시간 제한 추가 필요
        product.setRestrictTime(productRegisterPostReq.getReserveTime());
        return productRepository.save(product);
    }

    @Override
    public Product getProductByProductId(Long productId) {
        Product product = productRepositorySupport.findByProductId(productId).get();
        return product;
    }

    @Override
    public Boolean getProductExistMessage(Long productId) {
        if(productRepositorySupport.findByProductId(productId).isPresent()) return true;
        else return false;
    }

    @Override
    public Boolean checkProductAuth(String sellerId, Long productId) {
        if(productRepository.findProductByUserUserIdAndId(sellerId, productId).isPresent()) return true;
        else return false;
    }

    @Override
    public Product updateProduct(String sellerId, Long productId, ProductUpdatePatchReq productUpdatePatchReq) {
        Product product = productService.getProductByProductId(productId);
        User user = userService.getUserByUserId(sellerId);

        product.setUser(user);
        product.setProductName(productUpdatePatchReq.getProductName());
        product.setBasePrice(productUpdatePatchReq.getBasePrice());
        product.setCategories(productUpdatePatchReq.getCategories());
        product.setDescription(productUpdatePatchReq.getDescription());
        product.setState(productUpdatePatchReq.getState());
        product.setImageUrl(productUpdatePatchReq.getImageUrl());
        product.setRegistTime(productUpdatePatchReq.getRegistTime());
        product.setReserveTime(productUpdatePatchReq.getReserveTime());
        product.setRestrictTime(productUpdatePatchReq.getReserveTime());
        return productRepository.save(product);
    }

    @Override
    public void updateIsSold(Product product) {
        product.setIsSold(true);
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long productIndexId) {
        Product product = productRepositorySupport.findByProductId(productIndexId).get();
        productRepository.delete(product);
    }

}
