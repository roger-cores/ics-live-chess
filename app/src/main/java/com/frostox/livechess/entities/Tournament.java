package com.frostox.livechess.entities;

import java.util.Date;

/**
 * Created by roger on 10/27/2016.
 */
public class Tournament {

    private String title;

    private Date start;

    private Date end;

    private Boolean notificationStatus = false;

    private String status = null;

    private Boolean live = false;

    public Tournament(String title, Date start, Date end, String status, boolean live) {
        this.title = title;
        this.end = end;
        this.start = start;
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

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
