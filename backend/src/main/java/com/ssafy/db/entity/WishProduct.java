package com.ssafy.db.entity;

import lombok.Data;

import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Data
public class WishProduct {
    String productId;
    @ManyToOne(fetch = FetchType.LAZY)
    String userId;
}
