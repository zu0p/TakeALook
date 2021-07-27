package com.ssafy.api.service;

import com.ssafy.api.request.wish.WishRegistPostReq;
import com.ssafy.api.response.wish.WishListGetRes;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.WishProduct;
import com.ssafy.db.repository.WishRepository;
import com.ssafy.db.repository.WishRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishServiceImpl implements WishService{
    @Autowired
    WishRepository wishRepository;

    @Autowired
    WishRepositorySupport wishRepositorySupport;

    @Autowired
    UserService userService;

    @Override
    public List<WishListGetRes> selectWishList(Long userId) {
        List<WishListGetRes> wlist = wishRepositorySupport.findByUserId(userId);
        return wlist;
    }

    @Override
    public Long insertWishProduct(WishRegistPostReq wishRegistPostReq) {
        User user = userService.selectUserById(wishRegistPostReq.getUserId());
        WishProduct wishProduct = new WishProduct();
        wishProduct.setProductId(wishRegistPostReq.getProductId());
        wishProduct.setUser(user);
        wishRepository.save(wishProduct);
        return wishProduct.getProductId();
    }

    @Override
    public Long deleteWishProduct(Long userId, Long productId) {
        Optional<WishProduct> wishProduct = wishRepository.findWishProductByUserIdAndProductId(userId, productId);
        wishRepository.delete(wishProduct.get());
        return productId;
    }

    public Boolean getWishExistMessage(Long userId, Long productId){
        Optional<WishProduct> wishProduct = wishRepository.findWishProductByUserIdAndProductId(userId, productId);
        if(wishProduct.isPresent()) return true;
        else return false;
    }
}
