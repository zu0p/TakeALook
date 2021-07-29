package com.ssafy.db.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
public class Product extends BaseEntity {
    String productName;
    Integer basePrice;
    String categories;
    String description;
    String state;
    String imageUrl;
    Boolean isSold;

    LocalDateTime registTime; //상품 등록 시간
    LocalDateTime reserveTime; // 거래 방 예약 시간
    LocalDateTime restrictTime; // 거래 방 제한 시간

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    User user;

}
