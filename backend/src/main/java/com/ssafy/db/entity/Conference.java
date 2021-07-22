package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * conference 모델 정의.
 */
@Entity
@Getter
@Setter
@ToString
public class Conference extends BaseEntity{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    User owner_id;

    Long conference_category;
    Date call_start_time;
    Date call_end_time;
    String thumbnail;
    String title;
    String description;
    Boolean is_active;
}
