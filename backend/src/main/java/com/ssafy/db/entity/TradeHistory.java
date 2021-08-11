package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
public class TradeHistory extends BaseEntity {
    Date tradeDate;
    Long productId;
    Integer price;
    String seller;
    String buyer;
    String roomId;
}
