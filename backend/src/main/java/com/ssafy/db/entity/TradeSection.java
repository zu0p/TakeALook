package com.ssafy.db.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TradeSection {
    String productId;
    Timestamp startTime; // 실제 거래 방 시작 시간
    Timestamp endTime; // 실제 거래 방 종료 시간
    String title;
    Boolean isActive;
    String url;
    Integer priceGap;
    Integer maxPrice;
    Integer userAmount; // 접속자 수
    Integer userAmountLimit; // 최대 접속자 수
}
