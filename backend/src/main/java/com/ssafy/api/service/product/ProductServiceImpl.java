package com.ssafy.api.service.product;

import com.ssafy.api.request.paging.PageReq;
import com.ssafy.api.request.product.ProductRegisterPostReq;
import com.ssafy.api.request.product.ProductSearchPostReq;
import com.ssafy.api.request.product.ProductUpdatePatchReq;
import com.ssafy.api.response.product.ProductListGetRes;
import com.ssafy.api.service.trade.TradeService;
import com.ssafy.api.service.user.UserService;
import com.ssafy.api.service.wish.WishService;
import com.ssafy.db.entity.Product;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.product.ProductRepository;
import com.ssafy.db.repository.product.ProductRepositorySupport;
import com.ssafy.db.repository.wish.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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
    @Autowired
    WishService wishService;
    @Autowired
    WishRepository wishRepository;
    @Autowired
    TradeService tradeService;

    @Override
    public List<ProductListGetRes> getAllProduct(){
        List<ProductListGetRes> productList = productRepositorySupport.findAll();
        return productList;
    }

    @Override
    public Page<ProductListGetRes> getList(PageReq pageReq){
        Pageable pageable = PageRequest.of(pageReq.getPage(),pageReq.getSize());
        Page<ProductListGetRes> productList = productRepositorySupport.findAllList(pageable);
        return productList;
    }

    @Override
    public Page<ProductListGetRes> getListByReserveTime(PageReq pageReq){
        Pageable pageable = PageRequest.of(pageReq.getPage(),pageReq.getSize());
        Page<ProductListGetRes> productList = productRepositorySupport.findAllReserveTime(pageable);
        return productList;
    }

    @Override
    public Page<ProductListGetRes> getListByHighPrice(PageReq pageReq){
        Pageable pageable = PageRequest.of(pageReq.getPage(),pageReq.getSize());
        Page<ProductListGetRes> productList = productRepositorySupport.findAllHighPrice(pageable);
        return productList;
    }

    @Override
    public Page<ProductListGetRes> getListByLowPrice(PageReq pageReq){
        Pageable pageable = PageRequest.of(pageReq.getPage(),pageReq.getSize());
        Page<ProductListGetRes> productList = productRepositorySupport.findAllLowPrice(pageable);
        return productList;
    }

    @Override
    public List<ProductListGetRes> getAllProductByUserId(String userId){
        List<ProductListGetRes> productList = productRepositorySupport.findByUserId(userId);
        return productList;
    }

    @Override
    public Page<ProductListGetRes> searchProduct(ProductSearchPostReq productSearchInfo){
        Pageable pageable = PageRequest.of(productSearchInfo.getPage(),productSearchInfo.getSize());
        String categories =  productSearchInfo.getCategories();
        String keyword = productSearchInfo.getKeyword();
        Page<ProductListGetRes> productList;

        if(keyword==null) keyword="";
        if(categories==null) productList= productRepositorySupport.searchALL(pageable, keyword);
        else if(categories.equals("digital")) productList= productRepositorySupport.searchDigital(pageable, keyword);
        else if(categories.equals("furniture")) productList= productRepositorySupport.searchFurniture(pageable, keyword);
        else if(categories.equals("fashion")) productList= productRepositorySupport.searchFashion(pageable, keyword);
        else if(categories.equals("art")) productList= productRepositorySupport.searchArt(pageable, keyword);
        else productList = productRepositorySupport.searchALL(pageable, keyword);

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
        product.setRestrictTime(productRegisterPostReq.getRestrictTime());
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
        product.setRestrictTime(productUpdatePatchReq.getRestrictTime());
        return productRepository.save(product);
    }

    @Override
    public void updateIsSold(Product product) {
        product.setIsSold(true);
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void deleteProduct(Long productId) {
        if(wishService.countWishProductByProductId(productId)>0) wishRepository.deleteAllByProductId(productId);
        //if(tradeService.checkTradeHistory(productId))tradeService.deleteTradeInfo(productId);

        Product product = productRepositorySupport.findByProductId(productId).get();
        productRepository.delete(product);
    }
}
