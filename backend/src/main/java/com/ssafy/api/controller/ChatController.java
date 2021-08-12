package com.ssafy.api.controller;

import com.ssafy.api.request.product.ProductRegisterPostReq;
import com.ssafy.api.response.product.ProductRegistPostRes;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Product;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Api(value="채팅 API", tags = {"Chat."})
@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {

    @PostMapping()
    @ApiOperation(value="채팅 저장", notes = "채팅 목록을 저장한다.")
    public ResponseEntity<?> saveChat(@RequestBody String chatList){
        return ResponseEntity.status(200).body(BaseResponseBody.of(200,"Success"));
    }
}
