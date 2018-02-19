package com.example.user.calltime2;

/**
 * Created by user on 05/11/2017.
 */

public class Calls {
    private String callNumber ;
    private String callName;
    private String callDate;
    private String dateString;
    private String callType;
    private String isCallNew ;
    private String duration ;


    public Calls() {
    }

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public String getCallName() {
        return callName;
    }

    public void setCallName(String callName) {
        this.callName = callName;
    }

    public String getCallDate() {
        return callDate;
    }

    public void setCallDate(String callDate) {
        this.callDate = callDate;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getIsCallNew() {
        return isCallNew;
    }

    public void setIsCallNew(String isCallNew) {
        this.isCallNew = isCallNew;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
