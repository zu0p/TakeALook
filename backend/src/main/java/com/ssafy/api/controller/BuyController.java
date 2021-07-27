package com.ssafy.api.controller;

import com.querydsl.core.Tuple;
import com.ssafy.api.request.BuyUpdatePostReq;
import com.ssafy.api.request.UserRegisterPostReq;
import com.ssafy.api.response.BuyRes;
import com.ssafy.api.response.UserRes;
import com.ssafy.api.service.BuyService;
import com.ssafy.api.service.UserService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Product;
import com.ssafy.db.entity.TradeHistory;
import com.ssafy.db.entity.User;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Slf4j
@Api(value = "구매내역 API", tags = {"Buy"})
@RestController
@RequestMapping("/api/v1/buy")
public class BuyController {
    @Autowired
    UserService userService; // 유저 관련 비즈니스 처리 로직

    @Autowired
    BuyService buyService;

    @GetMapping()
    @ApiOperation(value = "회원의 구매 내역 조회", notes = "로그인한 회원의 구매 내역을 반환한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> getBuyList(@ApiIgnore Authentication authentication) {
        /**
         * 요청 헤더 액세스 토큰이 포함된 경우에만 실행되는 인증 처리이후, 리턴되는 인증 정보 객체(authentication) 통해서 요청한 유저 식별.
         * 액세스 토큰이 없이 요청하는 경우, 403 에러({"error": "Forbidden", "message": "Access Denied"}) 발생.
         */
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        User user = userService.getUserByUserId(userDetails.getUsername());

        String userId = user.getUserId();
        System.out.println(userId);
        System.out.println("controller");
        List<Product> list = buyService.getBuyList(userId);
        return (ResponseEntity<?>) ResponseEntity.status(200).body(list);
    }

    @PostMapping()
    @ApiOperation(value = "구매상품 등록", notes = "거래 시 최고가로 낙착된 경우 구매자가 buyer로 구매등록된다.") // swagger 설명 추가
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> register(
            @ApiIgnore Authentication authentication,
            @RequestBody @ApiParam(value="구매 상품 정보", required = true) BuyUpdatePostReq buyInfo) {
        //User user = userService.createUser(registerInfo);
        SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
        User user = userService.getUserByUserId(userDetails.getUsername());
        // 현재 로그인한 아이디의 회원 == 구매자
        buyInfo.setBuyer(user.getUserId());

        TradeHistory tradeHistory = buyService.createBuyHistory(buyInfo);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }
}
