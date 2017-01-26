package com.frostox.livechess.entities;

import com.frostox.livechess.util.DateFormatter;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by roger on 10/27/2016.
 */
public class Tournament {


    private String name;

    private Long dateStart;

    private Long dateEnd;

    private Boolean notificationStatus = false;

    private String status = null;

    private Boolean live = false;

    public Tournament() {
    }

    public Tournament(String name, Long start, Long dateEnd, String status, boolean live) {
        this.name = name;
        this.dateEnd = dateEnd;
        this.dateStart = start;
        this.status = status;
        this.live = live;
    }

    public Boolean getLive() {
        return live;
    }

    public void setLive(Boolean live) {
        this.live = live;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(Boolean notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public Long getDateStart() {
        return dateStart;
    }

    public void setDateStart(Long dateStart) {
        this.dateStart = dateStart;
    }

    public Long getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Long dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
