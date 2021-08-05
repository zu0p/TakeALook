package com.ssafy.api.controller;

import com.ssafy.api.request.paging.PageReq;
import com.ssafy.api.request.trade.TradeDeleteReq;
import com.ssafy.api.request.trade.TradeRegistPatchReq;
import com.ssafy.api.response.product.ProductListGetRes;
import com.ssafy.api.response.trade.TradeListGetRes;
import com.ssafy.api.response.trade.TradeRes;
import com.ssafy.api.service.product.ProductService;
import com.ssafy.api.service.trade.TradeService;
import com.ssafy.api.service.user.UserService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.TradeHistory;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Slf4j
@Api(value = "거래 API", tags = {"Trade."})
@RestController
@RequestMapping("/api/v1/trade")
public class TradeController {
    @Autowired
    UserService userService;
    @Autowired
    TradeService tradeService;
    @Autowired
    ProductService productService;

    @PostMapping("/buy")
    @ApiOperation(value = "구매 상품 목록 조회", notes = "로그인한 회원의 구매 내역을 반환한다.")
    public ResponseEntity<?> getBuyList(@ApiIgnore Authentication authentication, @RequestBody PageReq pageReq) {
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        String authId = userDetails.getUsername();
        if(!userService.getUserExistMessage(authId))
            return ResponseEntity.status(200).body(BaseResponseBody.of(404, "Not found"));

        Page<TradeListGetRes> buyList = tradeService.getBuyerList(pageReq, authId);
        if(buyList.isEmpty()) return ResponseEntity.status(200).body(BaseResponseBody.of(404, "Not found"));
        return ResponseEntity.status(200).body(buyList);
    }

    @PostMapping("/sell")
    @ApiOperation(value = "판매 상품 목록 조회", notes = "로그인한 회원의 판매 내역을 반환한다.")
    public ResponseEntity<?> getSellerList(@ApiIgnore Authentication authentication, @RequestBody PageReq pageReq) {
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        String authId = userDetails.getUsername();
        if(!userService.getUserExistMessage(authId))
            return ResponseEntity.status(200).body(BaseResponseBody.of(404, "Not found"));

        Page<ProductListGetRes> sellList = productService.getAllProductByUserId(pageReq, authId);
        if(sellList.isEmpty()) return ResponseEntity.status(200).body(BaseResponseBody.of(404, "Not found"));
        return ResponseEntity.status(200).body(sellList);
    }


    @PostMapping()
    @ApiOperation(value = "거래 목록 등록", notes = "상품 거래를 등록한다.")
    public ResponseEntity<?> registBuyProduct(@RequestBody @ApiParam(value="거래 상품 정보", required = true) TradeRegistPatchReq tradeRegistInfo) {
        if(!productService.checkProductAuth(tradeRegistInfo.getSeller(),tradeRegistInfo.getProductId()))
            return ResponseEntity.status(200).body(BaseResponseBody.of(401,"Fail"));

        if(tradeService.checkTradeHistory(tradeRegistInfo.getProductId()))
            return ResponseEntity.status(200).body(BaseResponseBody.of(409,"Exist"));

        TradeHistory tradeHistory = tradeService.createTradeHistory(tradeRegistInfo);
        return ResponseEntity.status(200).body(TradeRes.of(tradeRegistInfo.getProductId()));
    }

    @DeleteMapping("/{productId}")
    @ApiOperation(value = "거래 목록 삭제", notes = "거래 목록을 삭제한다.")
    public ResponseEntity<?> deleteBuyProduct(@PathVariable Long productId) {
        if(!tradeService.checkTradeHistory(productId))
            return ResponseEntity.status(200).body(BaseResponseBody.of(404,"Not found"));

        tradeService.deleteTradeInfo(productId);
        return ResponseEntity.status(200).body(TradeRes.of(productId));
    }
}
