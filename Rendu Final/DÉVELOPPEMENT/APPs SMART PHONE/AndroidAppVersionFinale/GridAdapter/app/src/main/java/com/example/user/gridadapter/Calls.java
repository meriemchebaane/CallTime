package com.example.user.gridadapter;

/**
 * Created by Meriem Chebaane
 */

public class Calls {
    private String callNumber ;
    private String callName;
    private String callDay;
    private String callMonth;

    public String getCallDayWeek() {
        return callDayWeek;
    }

    public void setCallDayWeek(String callDayWeek) {
        this.callDayWeek = callDayWeek;
    }

    private String callDayWeek;
    private String callYear;
    private String callTime;
    private String callType;
    private String isCallNew ;
    private String duration ;


    public Calls() {
    }

    public String getCallDay() {
        return callDay;
    }

    public void setCallDay(String callDay) {
        this.callDay = callDay;
    }

    public String getCallMonth() {
        return callMonth;
    }

    public void setCallMonth(String callMonth) {
        this.callMonth = callMonth;
    }

    public String getCallYear() {
        return callYear;
    }

    public void setCallYear(String callYear) {
        this.callYear = callYear;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public String getCallTime() {
        return callTime;
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
