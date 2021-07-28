package com.ssafy.api.controller;

import com.ssafy.api.request.wish.WishRegistPostReq;
import com.ssafy.api.response.wish.WishListGetRes;
import com.ssafy.api.response.wish.WishRes;
import com.ssafy.api.service.product.ProductService;
import com.ssafy.api.service.user.UserService;
import com.ssafy.api.service.wish.WishService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(value = "관심 상품 API", tags = {"Wish."})
@RestController
@RequestMapping("/api/v1/wish")
public class WishController {
    @Autowired
    WishService wishService;
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;

    @GetMapping()
    @ApiOperation(value = "관심 상품 목록 조회", notes = "유저 인덱스를 통해 관심 상품 목록을 조회한다.")
    public ResponseEntity<?> getWishList(@ApiIgnore Authentication authentication){

        SsafyUserDetails userDetails = (SsafyUserDetails) authentication.getDetails();
        if(!userService.getUserExistMessage(userDetails.getUsername()))
            return ResponseEntity.status(200).body(BaseResponseBody.of(404, "Not found"));

        List<WishListGetRes> wishList = wishService.selectWishList(userDetails.getUsername());
        if(wishList.isEmpty()) return ResponseEntity.status(404).body(BaseResponseBody.of(404,"Not found"));
        return ResponseEntity.status(200).body(wishList);
    }

    @PostMapping()
    @ApiOperation(value="관심 상품 등록", notes="유저 인덱스와 상품 인덱스로 관심 상품을 등록합니다")
    public ResponseEntity<?> registWishProduct(@ApiIgnore Authentication authentication, @RequestBody WishRegistPostReq wishRegistInfo){

        SsafyUserDetails userDetails = (SsafyUserDetails) authentication.getDetails();
        String authId = userDetails.getUsername();
        if(!productService.getProductExistMessage(wishRegistInfo.getProductId()))
            return ResponseEntity.status(200).body(BaseResponseBody.of(409,"Exist"));

        if(wishService.getWishExistMessage(authId,wishRegistInfo.getProductId()))
            return ResponseEntity.status(200).body(BaseResponseBody.of(409,"Exist"));

        Long res = wishService.insertWishProduct(authId,wishRegistInfo);
        return ResponseEntity.status(200).body(WishRes.of(res));
    }

    @DeleteMapping("/{productId}")
    @ApiOperation(value="관심 상품 삭제", notes="유저 인덱스와 상품 인덱스로 관심 상품을 삭제합니다")
    public ResponseEntity<?> deleteWishProduct(@ApiIgnore Authentication authentication, @PathVariable Long productId){

        SsafyUserDetails userDetails = (SsafyUserDetails) authentication.getDetails();
        String authId = userDetails.getUsername();

        if(!userService.getUserExistMessage(authId))
            return ResponseEntity.status(200).body(BaseResponseBody.of(404, "Not found"));

        if(!wishService.getWishExistMessage(authId,productId))
            return ResponseEntity.status(200).body(BaseResponseBody.of(404,"Not found"));

        wishService.deleteWishProduct(authId, productId);
        return ResponseEntity.status(200).body(WishRes.of(productId));
    }

}
