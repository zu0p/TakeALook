package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@ToString
public class TradeSection extends BaseEntity {
    Long productId;
    String seller;
    LocalDate startTime; // 실제 거래 방 시작 시간
    LocalDate endTime; // 실제 거래 방 종료 시간
    String title;
    Boolean isActive;
    String url;
    Integer priceGap;
    Integer maxPrice;
    Integer userAmount; // 접속자 수
    Integer userAmountLimit; // 최대 접속자 수
}
