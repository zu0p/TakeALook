package com.ssafy.api.controller;

import com.ssafy.api.request.wish.WishRegistPostReq;
import com.ssafy.api.response.wish.WishListGetRes;
import com.ssafy.api.response.wish.WishRes;
import com.ssafy.api.service.WishService;
import com.ssafy.common.model.response.BaseResponseBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "관심 상품 API", tags = {"Wish."})
@RestController
@RequestMapping("/api/v1/wish")
public class WishController {

    @Autowired
    WishService wishService;

    @GetMapping("/{userId}")
    @ApiOperation(value = "관심 상품 목록 조회", notes = "유저 인덱스를 통해 관심 상품 목록을 조회한다.")
    public ResponseEntity<?> getWishList(@PathVariable Long userId){
        List<WishListGetRes> wlist = wishService.selectWishList(userId);
        if(wlist.isEmpty()) return ResponseEntity.status(404).body(BaseResponseBody.of(404,"Not found"));
        return ResponseEntity.status(200).body(wlist);
    }

    @PostMapping()
    @ApiOperation(value="관심 상품 등록", notes="유저 인덱스와 상품 인덱스로 관심 상품을 등록합니다")
    public ResponseEntity<?> registWishProduct(@RequestBody WishRegistPostReq wishRegistPostReq){
        if(wishService.getWishExistMessage(wishRegistPostReq.getUserId(),wishRegistPostReq.getProductId()))
            return ResponseEntity.status(409).body(BaseResponseBody.of(409,"Exist"));

        Long res = wishService.insertWishProduct(wishRegistPostReq);
        return ResponseEntity.status(200).body(WishRes.of(res));
    }

    @DeleteMapping("/{userId}/{productId}")
    @ApiOperation(value="관심 상품 삭제", notes="유저 인덱스와 상품 인덱스로 관심 상품을 삭제합니다")
    public ResponseEntity<?> deleteWishProduct(@PathVariable Long userId, @PathVariable Long productId){
        if(!wishService.getWishExistMessage(userId,productId))
            return ResponseEntity.status(404).body(BaseResponseBody.of(404,"Not found"));

        Long res = wishService.deleteWishProduct(userId, productId);
        return ResponseEntity.status(200).body(WishRes.of(res));
    }

}
