package com.flysall.appoint.dto;

import com.flysall.appoint.enums.AppointStateEnum;

public class AppointExecution {
    private long bookId;
    //秒杀预约结果状态
    private int state;
    //状态标识
    private String stateInfo;

    public AppointExecution(){

    }

    //预约失败的构造器
    public AppointExecution(long bookId, AppointStateEnum stateEnum){
        this.bookId = bookId;
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    @Override
    public String toString() {
        return "AppointExecution [bookId=" + bookId + ", state=" + state + ", stateInfo=" + stateInfo+"]";
    }
}
