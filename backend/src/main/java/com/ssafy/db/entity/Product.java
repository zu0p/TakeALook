package com.ssafy.db.entity;

import lombok.Data;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Data
public class Product {
    @ManyToOne(fetch = FetchType.LAZY)
    String userId;
    Timestamp registTime; //상품 등록 시간
    Timestamp reserveTime; // 거래 방 예약 시간
    Timestamp restrictTime; // 거래 방 제한 시간
    String productName;
    Integer basePrice;
    String category;
    String desc;
    String state;
    String imageUrl;
    Boolean idSold;
}
