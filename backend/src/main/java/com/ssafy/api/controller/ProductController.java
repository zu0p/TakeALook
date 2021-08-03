package com.ssafy.api.controller;

import com.ssafy.api.request.paging.PageReq;
import com.ssafy.api.request.product.ProductRegisterPostReq;
import com.ssafy.api.request.product.ProductUpdatePatchReq;
import com.ssafy.api.response.product.ProductDeleteRes;
import com.ssafy.api.response.product.ProductListGetRes;
import com.ssafy.api.response.product.ProductRegistPostRes;
import com.ssafy.api.response.product.ProductUpdatePatchRes;
import com.ssafy.api.response.trade.TradeListGetRes;
import com.ssafy.api.service.product.ProductService;
import com.ssafy.api.service.user.UserService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Product;
import com.ssafy.db.entity.User;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Api(value="상품 API", tags = {"Product."})
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;

    @PostMapping("/list")
    @ApiOperation(value = "판매 상품 목록 (신상품순)", notes = "전체 상품을 등록일 순서로 조회한다")
    public ResponseEntity<?> getProductList(@RequestBody PageReq pageReq) {
        Page<ProductListGetRes> productList = productService.getList(pageReq);
        if(productList.isEmpty()) return ResponseEntity.status(200).body(BaseResponseBody.of(404, "Not found"));
        return ResponseEntity.status(200).body(productList);
    }

    @PostMapping("/reservetime")
    @ApiOperation(value = "판매 상품 목록 조회 (판매예정일순)", notes = "전체 상품을 거래 예약일 순서로 조회한다")
    public ResponseEntity<?> getProductListReserveTime(@RequestBody PageReq pageReq) {
        Page<ProductListGetRes> productList = productService.getListByReserveTime(pageReq);
        if(productList.isEmpty()) return ResponseEntity.status(200).body(BaseResponseBody.of(404, "Not found"));
        return ResponseEntity.status(200).body(productList);
    }

    @PostMapping("/lowprice")
    @ApiOperation(value = "판매 상품 목록 조회 (낮은가격순)", notes = "전체 상품을 낮은 가격 순으로 조회한다")
    public ResponseEntity<?> getProductListLowPrice(@RequestBody PageReq pageReq) {
        Page<ProductListGetRes> productList = productService.getListByLowPrice(pageReq);
        if(productList.isEmpty()) return ResponseEntity.status(200).body(BaseResponseBody.of(404, "Not found"));
        return ResponseEntity.status(200).body(productList);
    }

    @PostMapping("/highprice")
    @ApiOperation(value = "판매 상품 목록 조회 (높은가격순)", notes = "전체 상품을 높은 가격 순으로 조회한다")
    public ResponseEntity<?> getProductListHighPrice(@RequestBody PageReq pageReq) {
        Page<ProductListGetRes> productList = productService.getListByHighPrice(pageReq);
        if(productList.isEmpty()) return ResponseEntity.status(200).body(BaseResponseBody.of(404, "Not found"));
        return ResponseEntity.status(200).body(productList);
    }

    @PostMapping()
    @ApiOperation(value="상품 등록", notes = "판매 상품을 등록한다.")
    public ResponseEntity<?> registProduct(@ApiIgnore Authentication authentication,
            @RequestBody @ApiParam(value="상품 정보", required = true) ProductRegisterPostReq productInfo){
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        String authId = userDetails.getUsername();
        if(!userService.getUserExistMessage(authId))
            return ResponseEntity.status(200).body(BaseResponseBody.of(404, "Not found"));

        Product product = productService.createProduct(authId, productInfo);
        return ResponseEntity.status(200).body(ProductRegistPostRes.of(product));
    }

    @GetMapping("/{productId}")
    @ApiOperation(value = "상품 상세 조회", notes ="상품 상세 정보를 조회한다.")
    public ResponseEntity<?> getProductInfo(@PathVariable Long productId){
        if(!productService.getProductExistMessage(productId))
            return ResponseEntity.status(200).body(BaseResponseBody.of(404,"Not found"));

        Product product = productService.getProductByProductId(productId);
        return ResponseEntity.status(200).body(ProductRegistPostRes.of(product));

    }

    @PatchMapping("/{productId}")
    @ApiOperation(value = "상품 정보 수정", notes ="상품 정보를 수정한다.")
    public ResponseEntity<?> updateProductInfo(@ApiIgnore Authentication authentication, @PathVariable Long productId,
            @RequestBody @ApiParam(value="상품 갱신할 정보", required = true) ProductUpdatePatchReq productInfo){
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        if(!productService.checkProductAuth(userDetails.getUsername(),productId))
            return ResponseEntity.status(200).body(BaseResponseBody.of(404,"Not found"));

        Product newProductState = productService.updateProduct(userDetails.getUsername(),productId, productInfo);
        return ResponseEntity.status(200).body(ProductUpdatePatchRes.of(newProductState));
    }

    @DeleteMapping("/{productId}")
    @ApiOperation(value = "상품 삭제", notes ="상품을 삭제한다.")
    public ResponseEntity<?> deleteProduct(@ApiIgnore Authentication authentication, @PathVariable Long productId){
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        if(!productService.checkProductAuth(userDetails.getUsername(),productId))
            return ResponseEntity.status(200).body(BaseResponseBody.of(404,"Not found"));

        productService.deleteProduct(productId);
        return ResponseEntity.status(200).body(ProductDeleteRes.of(userDetails.getUser(), productId));
    }

    @PatchMapping("/sold/{productId}")
    @ApiOperation(value = "상품 판매 완료", notes ="판매자 기준으로 상품을 판매 완료한다. (isSold=true)")
    public ResponseEntity<?> update(@ApiIgnore Authentication authentication, @PathVariable Long productId){
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        if(!productService.checkProductAuth(userDetails.getUsername(),productId))
            return ResponseEntity.status(200).body(BaseResponseBody.of(404,"Not found"));

        Product product = productService.getProductByProductId(productId);
        productService.updateIsSold(product);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200,"Success"));
    }
}
