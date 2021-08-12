package com.ssafy.api.controller;

import com.ssafy.api.request.paging.PageReq;
import com.ssafy.api.request.trade.*;
import com.ssafy.api.response.product.ProductListGetRes;
import com.ssafy.api.response.trade.*;
import com.ssafy.api.service.product.ProductService;
import com.ssafy.api.service.trade.TradeService;
import com.ssafy.api.service.user.UserService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.TradeHistory;
import com.ssafy.db.entity.TradeSection;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Optional;

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

    @GetMapping("/chatroom")
    @ApiOperation(value = "거래 상품 목록 조회(채팅방 목록)", notes = "로그인한 회원의 거래 목록을 반환한다.")
    public ResponseEntity<?> getChatList(@ApiIgnore Authentication authentication) {
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        String authId = userDetails.getUsername();
        if(!userService.getUserExistMessage(authId))
            return ResponseEntity.status(200).body(BaseResponseBody.of(404, "Not found"));

        List<TradeCompleteRes> chatList = tradeService.getChatList(authId);
       if(chatList.isEmpty()) return ResponseEntity.status(200).body(BaseResponseBody.of(404, "Not found"));
        return ResponseEntity.status(200).body(chatList);
    }

    @PostMapping("/history")
    @ApiOperation(value = "거래 히스토리 등록", notes = "상품 거래를 등록한다.")
    public ResponseEntity<?> registBuyProduct(@RequestBody @ApiParam(value="거래 상품 정보", required = true) TradeRegistPatchReq tradeRegistInfo) {
        if(!productService.checkProductAuth(tradeRegistInfo.getSeller(),tradeRegistInfo.getProductId()))
            return ResponseEntity.status(200).body(BaseResponseBody.of(401,"Fail"));

        if(tradeService.checkTradeHistory(tradeRegistInfo.getProductId()))
            return ResponseEntity.status(200).body(BaseResponseBody.of(409,"Exist"));

        TradeHistory tradeHistory = tradeService.createTradeHistory(tradeRegistInfo);
        return ResponseEntity.status(200).body(TradeRes.of(tradeRegistInfo.getProductId()));
    }

    @DeleteMapping("history/{productId}")
    @ApiOperation(value = "거래 히스토리 삭제", notes = "거래 목록을 삭제한다.")
    public ResponseEntity<?> deleteBuyProduct(@PathVariable Long productId) {
        if(!tradeService.checkTradeHistory(productId))
            return ResponseEntity.status(200).body(BaseResponseBody.of(404,"Not found"));

        tradeService.deleteTradeInfo(productId);
        return ResponseEntity.status(200).body(TradeRes.of(productId));
    }

    @PostMapping("/section/create")
    @ApiOperation(value = "거래 방 생성", notes = "거래 방을 생성한다.")
    public ResponseEntity<?> tradeSectionCreate(@RequestBody @ApiParam(value="거래 상품 정보", required = true) TradeSectionCreateReq tradeSectionCreateReq) {
        TradeSectionCreateRes tradeSectionCreateRes = TradeSectionCreateRes.of(tradeSectionCreateReq.getSeller(), tradeSectionCreateReq.getProductId());
        try{
            TradeSection tradeSectionCheck = tradeService.findTradeSection(TradeSectionEnterReq.of(tradeSectionCreateReq.getSeller(),tradeSectionCreateReq.getProductId()));
            if(tradeSectionCheck==null) {
                tradeService.createTradeSection(tradeSectionCreateReq, tradeSectionCreateRes.getRoom());
                return ResponseEntity.status(200).body(tradeSectionCreateRes);
            }
        }catch (Exception e){ // 이미 섹션이 존재함
            // do nothing
        }
        return ResponseEntity.status(200).body(tradeSectionCreateRes);
    }

    @PostMapping("/section/enter")
    @ApiOperation(value = "거래 방 리턴", notes = "거래 방을 반환한다.")
    public ResponseEntity<?> tradeSectionEnter(@RequestBody @ApiParam(value="거래 상품 정보", required = true) TradeSectionEnterReq tradeSectionEnterReq) {
        try{
            TradeSection tradeSection = tradeService.findTradeSection(tradeSectionEnterReq);
            TradeSectionCreateRes tradeSectionCreateRes = TradeSectionCreateRes.of(tradeSection.getSeller(), tradeSection.getProductId());
            if (!tradeSection.getIsActive()) {
                tradeSectionCreateRes.setRoom("createdButNotStarted");
                log.info("setRoom createdButNotStarted, {}", tradeSectionCreateRes.getRoom());
                return ResponseEntity.status(200).body(tradeSectionCreateRes);
            }
            if (tradeSection.getIsStarted()){
                tradeSectionCreateRes.setRoom("createdAndStarted");
                log.info("setRoom createdAndStarted, {}", tradeSectionCreateRes.getRoom());
            }
            return ResponseEntity.status(200).body(tradeSectionCreateRes);
        }catch (Exception e){
            TradeSectionCreateRes tradeSectionCreateRes = TradeSectionCreateRes.of(null, null);
            tradeSectionCreateRes.setRoom("notCreated");
            log.info("setRoom notCreated, {}", tradeSectionCreateRes.getRoom());
            return ResponseEntity.status(200).body(tradeSectionCreateRes);
        }
    }

    @PostMapping("/section/info")
    @ApiOperation(value = "거래 방 정보반환", notes = "거래 방 정보를 반환한다.")
    public ResponseEntity<?> tradeSectionInfo(@RequestBody @ApiParam(value="room번호", required = true) TradeSectionInfoReq tradeSectionInfoReq) {
        String roomUrl = tradeSectionInfoReq.getRoom();
        TradeSection tradeSection = tradeService.findTradeSectionByRoomUrl(roomUrl);
        TradeSectionInfoRes tradeSectionInfoRes = TradeSectionInfoRes.of(
                tradeSection.getProductId(),
                tradeSection.getSeller(),
                productService.getProductByProductId(tradeSection.getProductId()).getBasePrice(),
                tradeSection.getPriceGap()
        );
        return ResponseEntity.status(200).body(tradeSectionInfoRes);
    }

    @PostMapping("/section/updateMaxPrice")
    @ApiOperation(value = "거래 방 max price를 갱신", notes = "거래 방 max price를 갱신한다.")
    public ResponseEntity<?> updateSectionMaxPrice (@RequestBody @ApiParam(value="curPrice, room번호", required = true) TradeSectionUpdatePriceReq tradeSectionUpdatePriceReq) {
        TradeSection tradeSection = tradeService.findTradeSectionByRoomUrl(tradeSectionUpdatePriceReq.getRoom());
        log.info("updateMaxPrice 입력: {}",tradeSectionUpdatePriceReq.toString());
        log.info("찾은 tradeSection: {}",tradeSection.toString());
        tradeService.updateTradeSection(tradeSection, tradeSectionUpdatePriceReq.getCurPrice());
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "maxPrice is now updated"));
    }

    @GetMapping("/section/started/{room}")
    @ApiOperation(value = "거래 방 시작등록", notes = "거래 방 시작을 알린다.")
    public ResponseEntity<?> isTradeSectionStarted(@PathVariable String room){
        tradeService.findTradeSectionByRoomUrl(room);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "trade is now started"));
    }
}
