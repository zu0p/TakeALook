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
public class TradeHistory extends BaseEntity {
    LocalDate tradeDate;
    Long product;
    Integer price;
    String seller;
    String buyer;
}
