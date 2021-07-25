package com.ssafy.db.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class TradeHistory extends BaseEntity {
    Timestamp tradeDate;
    String product;
    Integer price;
    String seller;
    String buyer;
}
