package com.ssafy.api.controller;

import com.ssafy.api.request.product.ProductRegisterPostReq;
import com.ssafy.api.request.product.ProductUpdatePatchReq;
import com.ssafy.api.response.product.ProductDeleteRes;
import com.ssafy.api.response.product.ProductRegistPostRes;
import com.ssafy.api.response.product.ProductUpdatePatchRes;
import com.ssafy.api.service.product.ProductService;
import com.ssafy.api.service.user.UserService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Product;
import com.ssafy.db.entity.User;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@Slf4j
@Api(value="상품 API", tags = {"Product."})
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    // 상품 등록 (C)
    @PostMapping()
    @ApiOperation(value="상품 등록", notes = "<strong>Product Info</strong>를 통해 상품을 등록한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> register(
            @RequestBody @ApiParam(value="상품 정보", required = true) ProductRegisterPostReq productInfo,
            @ApiIgnore Authentication authentication
    ){
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        User seller =  userService.getUserByUserId(userDetails.getUsername());
        if(seller==null){
            return ResponseEntity.status(200).body(BaseResponseBody.of(401, "Invaild auth-token"));
        }else{
            Product product = productService.createProduct(productInfo);
            return ResponseEntity.status(200).body(ProductRegistPostRes.of(product));
        }
    }

    // 상품 조회 (R)
    @GetMapping("/{productId}")
    @ApiOperation(value = "상품 인덱스로 상품 정보 조회", notes ="<strong>Product Info</strong>를 통해 상품을 등록한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> read(
        @PathVariable Long productId,
        @ApiIgnore Authentication authentication
    ){
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        User seller =  userService.getUserByUserId(userDetails.getUsername());
        if(seller==null){
            return ResponseEntity.status(200).body(BaseResponseBody.of(401, "Invaild auth-token"));
        }else{
            Product product = productService.getProductByPID(productId);
            System.out.println(product.toString());
            return ResponseEntity.status(200).body(ProductRegistPostRes.of(product));
        }
    }

    // 상품 갱신 (U)
    @PatchMapping("/{productId}")
    @ApiOperation(value = "상품 인덱스로 상품 정보 갱신", notes ="<strong>Product ID와 새로운 정보로</strong>를 통해 상품정보를 갱신한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> update(
            @PathVariable Long productId,
            @RequestBody @ApiParam(value="상품 갱신할 정보", required = true) ProductUpdatePatchReq productInfo,
            @ApiIgnore Authentication authentication
    ){
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        User seller =  userService.getUserByUserId(userDetails.getUsername());
        if(seller==null){
            return ResponseEntity.status(200).body(BaseResponseBody.of(401, "Invaild auth-token"));
        }else{
            Product oldProductState = productService.getProductByPID(productId);
            Product newProductState = productService.updateProduct(oldProductState, productInfo);
            System.out.println(newProductState.toString());
            return ResponseEntity.status(200).body(ProductUpdatePatchRes.of(newProductState));
        }
    }

    // 상품 삭제 (D)
    @DeleteMapping("/{productId}")
    @ApiOperation(value = "상품 인덱스로 상품을 삭제한다", notes ="<strong>Product ID</strong>를 통해 해당 상품을 삭제하고, <strong>삭제한 유저와 삭제된 상품명을 반환한다.</strong>.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> update(
            @PathVariable Long productId,
            @ApiIgnore Authentication authentication
    ){
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        User seller =  userService.getUserByUserId(userDetails.getUsername());
        if(seller==null){
            return ResponseEntity.status(200).body(BaseResponseBody.of(401, "Invaild auth-token"));
        }else{
            Product product = productService.getProductByPID(productId);
            productService.deleteProduct(productId);
            return ResponseEntity.status(200).body(ProductDeleteRes.of(seller, product));
        }
    }
}
