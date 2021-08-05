package com.ssafy.api.service.wish;

import com.ssafy.api.request.paging.PageReq;
import com.ssafy.api.request.wish.WishRegistPostReq;
import com.ssafy.api.response.wish.WishListGetRes;
import com.ssafy.db.entity.WishProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WishService {
    Page<WishListGetRes> selectWishList(PageReq pageReq, String userId);
    Long insertWishProduct(String userId, WishRegistPostReq wishRegistPostReq);
    void deleteWishProduct(String userId, Long productId);
    Boolean getWishExistMessage(String userId, Long productId);
    Long countWishProductByProductId(Long productId);
}
