package com.ssafy.db.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QTradeHistory is a Querydsl query type for TradeHistory
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTradeHistory extends EntityPathBase<TradeHistory> {

    private static final long serialVersionUID = 325409618L;

    public static final QTradeHistory tradeHistory = new QTradeHistory("tradeHistory");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath buyer = createString("buyer");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final NumberPath<Long> product = createNumber("product", Long.class);

    public final StringPath seller = createString("seller");

    public final DateTimePath<java.sql.Timestamp> tradeDate = createDateTime("tradeDate", java.sql.Timestamp.class);

    public QTradeHistory(String variable) {
        super(TradeHistory.class, forVariable(variable));
    }

    public QTradeHistory(Path<? extends TradeHistory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTradeHistory(PathMetadata metadata) {
        super(TradeHistory.class, metadata);
    }

}

