package com.ssafy.api.service;

import com.ssafy.api.request.wish.WishRegistPostReq;
import com.ssafy.api.response.wish.WishListGetRes;
import com.ssafy.db.entity.WishProduct;

import java.util.List;

public interface WishService {
    List<WishListGetRes> selectWishList(Long userId);
    Long insertWishProduct(WishRegistPostReq wishRegistPostReq);
    Long deleteWishProduct(Long userId, Long productId);
    Boolean getWishExistMessage(Long userId, Long productId);
}
