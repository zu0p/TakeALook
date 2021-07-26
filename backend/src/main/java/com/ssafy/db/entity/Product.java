package com.ssafy.db.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.sql.Timestamp;

@Entity
@Data
public class Product extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    User user;
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
