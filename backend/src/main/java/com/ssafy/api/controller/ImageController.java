package com.ssafy.api.controller;

import com.ssafy.api.request.user.UserRegisterPostReq;
import com.ssafy.api.response.user.UserRegistPostRes;
import com.ssafy.api.service.image.ImageService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import retrofit2.http.Multipart;

import java.awt.*;
import java.io.IOException;

@Slf4j
@Api(value="이미지 API", tags = {"Images."})
@RestController
@RequestMapping("/api/v1/image")
public class ImageController {
    @Autowired
    ImageService imageService;

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ApiOperation(value = "이미지 업로드", notes = "이미지를 받으면 서버에 저장한다.")
    public ResponseEntity<?> uploadImages(@RequestParam("imageFile") MultipartFile image) throws IOException {
        log.info("image has arrived!!!! which is {}", image.getOriginalFilename());
        Long productId = 111L;
        imageService.saveImage(image,productId);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200,"Images arrived"));
    }
}
