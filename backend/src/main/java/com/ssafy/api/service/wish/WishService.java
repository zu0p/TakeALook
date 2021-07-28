package com.ssafy.api.service.wish;

import com.ssafy.api.request.wish.WishRegistPostReq;
import com.ssafy.api.response.wish.WishListGetRes;
import com.ssafy.db.entity.WishProduct;

import java.util.List;

public interface WishService {
    List<WishListGetRes> selectWishList(String userId);
    Long insertWishProduct(String userId, WishRegistPostReq wishRegistPostReq);
    void deleteWishProduct(String userId, Long productId);
    Boolean getWishExistMessage(String userId, Long productId);
}
