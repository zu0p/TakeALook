package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.SecondaryTable;
import javax.persistence.SecondaryTables;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="user_conference")
@SecondaryTables({
        @SecondaryTable(name="user_conference"),
        @SecondaryTable(name="conference_category")
})
@Getter
@Setter
public class Conference extends BaseEntity{
    int ownerId;
    int conferenceCategory;
    Date callStartTime;
    Date callEndTime;
    String title;
    String description;
    Boolean isActive;
}
