package com.ssafy.api.response.product;

import com.ssafy.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * 상품 API ([GET] /api/v1/product) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("ProductResponse")
public class ProductResponse {

    @ApiModelProperty(name="User ID")
    String userId; // 판매자
    String productName; // 상품명
    Integer basePrice; // 가격
    String categories; // 상품 분류
    String description; // 상품 설명
    String state; // 상품 상태
    String imageUrl; // 상품 사진
    Boolean isSold; // 판매 여부
    LocalDate registTime; //상품 등록 시간
    LocalDate reserveTime; // 거래 방 예약 시간
    LocalDate restrictTime; // 거래 방 제한 시간

//    public static com.ssafy.api.response.product.ProductResponse of(User user) {
//        com.ssafy.api.response.product.ProductResponse res = new com.ssafy.api.response.product.ProductResponse();
//        res.setUserId(user.getUserId());
//        res.setEmail(user.getEmail());
//        res.setAddress(user.getAddress());
//        res.setName(user.getName());
//        return res;
//    }

}