package com.ssafy.api.controller;

import com.ssafy.api.request.product.ProductRegisterPostReq;
import com.ssafy.api.service.ProductService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Product;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(value="상품 API", tags = {"Product"})
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping()
    @ApiOperation(value="회원 가입", notes = "<strong>아이디와 패스워드</strong>를 통해 회원가입 한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> register(
            @RequestBody @ApiParam(value="상품 정보", required = true) ProductRegisterPostReq productInfo){
        Product product = productService.createUser(productInfo);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }


}
