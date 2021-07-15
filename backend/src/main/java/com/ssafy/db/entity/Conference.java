package com.ssafy.db.entity;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.Date;

@Entity
@Getter
@Setter
public class Conference {
    int ownerId;
    int conferenceCategory;
    Date callStartTime;
    Date callEndTime;
    String title;
    String description;
    Boolean isActive;
}
