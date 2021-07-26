package com.ssafy.db.entity;

import lombok.Data;

import javax.persistence.Entity;
import java.sql.Timestamp;

@Entity
@Data
public class TradeHistory extends BaseEntity {
    Timestamp tradeDate;
    Long product;
    Integer price;
    String seller;
    String buyer;
}
