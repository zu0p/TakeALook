package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Getter
@Setter
@ToString
public class Chat extends BaseEntity {
    String writer;
    String message;
    @Temporal(TemporalType.TIMESTAMP)
    Date sendTime;
    String roomId;
}
