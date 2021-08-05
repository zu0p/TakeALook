package com.ssafy.api.service.wish;

import com.ssafy.api.request.paging.PageReq;
import com.ssafy.api.request.wish.WishRegistPostReq;
import com.ssafy.api.response.wish.WishListGetRes;
import com.ssafy.api.service.user.UserService;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.WishProduct;
import com.ssafy.db.repository.wish.WishRepository;
import com.ssafy.db.repository.wish.WishRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Page<WishListGetRes> selectWishList(PageReq pageReq, String userId) {
        Pageable pageable = PageRequest.of(pageReq.getPage(),pageReq.getSize());
        Page<WishListGetRes> wlist = wishRepositorySupport.findByUserId(pageable, userId);
        return wlist;
    }

    @Override
    public Long insertWishProduct(String userId, WishRegistPostReq wishRegistPostReq) {
        User user = userService.getUserByUserId(userId);
        WishProduct wishProduct = new WishProduct();

        wishProduct.setProductId(wishRegistPostReq.getProductId());
        wishProduct.setUser(user);
        wishRepository.save(wishProduct);
        return wishProduct.getProductId();
    }

    @Override
    public void deleteWishProduct(String userId, Long productId) {
        Optional<WishProduct> wishProduct = wishRepository.findWishProductByUserUserIdAndProductId(userId, productId);
        wishRepository.delete(wishProduct.get());
    }

    @Override
    public Boolean getWishExistMessage(String userId, Long productId){
        if(wishRepository.findWishProductByUserUserIdAndProductId(userId, productId).isPresent()) return true;
        else return false;
    }

    @Override
    public Long countWishProductByProductId(Long productId){
       Long wishCount = wishRepository.countByProductId(productId);
       return wishCount;
    }
}
