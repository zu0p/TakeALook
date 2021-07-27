package com.ssafy.api.controller;

import com.ssafy.api.request.trade.TradeDeleteReq;
import com.ssafy.api.request.trade.TradeRegistPatchReq;
import com.ssafy.api.service.trade.TradeService;
import com.ssafy.api.service.user.UserService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Product;
import com.ssafy.db.entity.TradeHistory;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Slf4j
@Api(value = "구매내역 API", tags = {"Trade."})
@RestController
@RequestMapping("/api/v1/trade")
public class TradeController {
    @Autowired
    UserService userService;
    @Autowired
    TradeService tradeService;

    @GetMapping()
    @ApiOperation(value = "구매 내역 조회", notes = "로그인한 회원의 구매 내역을 반환한다.")
    public ResponseEntity<?> getBuyList(@ApiIgnore Authentication authentication) {
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        String authId = userDetails.getUsername();
        if(!userService.getUserExistMessage(authId))
            return ResponseEntity.status(200).body(BaseResponseBody.of(404, "Not found"));

        List<Product> buyList = tradeService.getBuyerList(authId);
        if(buyList.isEmpty()) return ResponseEntity.status(200).body(BaseResponseBody.of(404, "Not found"));
        return ResponseEntity.status(200).body(buyList);
    }

    @GetMapping()
    @ApiOperation(value = "판매 내역 조회", notes = "로그인한 회원의 판매 내역을 반환한다.")
    public ResponseEntity<?> getSellerList(@ApiIgnore Authentication authentication) {
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        String authId = userDetails.getUsername();
        if(!userService.getUserExistMessage(authId))
            return ResponseEntity.status(200).body(BaseResponseBody.of(404, "Not found"));

        List<Product> sellList = tradeService.getSellerList(authId);
        if(sellList.isEmpty()) return ResponseEntity.status(200).body(BaseResponseBody.of(404, "Not found"));
        return ResponseEntity.status(200).body(sellList);
    }

    @PostMapping()
    @ApiOperation(value = "거래 목록 등록", notes = "상품 거래를 등록한다.")
    public ResponseEntity<? extends BaseResponseBody> registBuyProduct(@ApiIgnore Authentication authentication,
            @RequestBody @ApiParam(value="거래 상품 정보", required = true) TradeRegistPatchReq tradeRegistPatchReq) {
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        if(!userService.checkAuthByUserId(tradeRegistPatchReq.getBuyer(),userDetails.getUsername()))
            return ResponseEntity.status(200).body(BaseResponseBody.of(401, "Fail"));

        if(tradeService.checkTradeHistory(tradeRegistPatchReq.getProductId()))
            return ResponseEntity.status(200).body(BaseResponseBody.of(409,"Exist"));

        TradeHistory tradeHistory = tradeService.createTradeHistory(tradeRegistPatchReq);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @DeleteMapping()
    @ApiOperation(value = "거래 목록 삭제", notes = "거래 목록을 삭제한다.")
    public ResponseEntity<? extends BaseResponseBody> deleteBuyProduct(
            @ApiIgnore Authentication authentication,
            @RequestBody @ApiParam(value="구매 상품 정보", required = true) TradeDeleteReq tradeDeleteReq) {
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        if(!userService.checkAuthByUserId(tradeDeleteReq.getBuyer(),userDetails.getUsername()))
            return ResponseEntity.status(200).body(BaseResponseBody.of(401, "Fail"));

        if(!tradeService.checkTradeHistory(tradeDeleteReq.getProductId()))
            return ResponseEntity.status(200).body(BaseResponseBody.of(404,"Not found"));

        tradeService.deleteTradeInfo(tradeDeleteReq.getProductId());
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }
}
