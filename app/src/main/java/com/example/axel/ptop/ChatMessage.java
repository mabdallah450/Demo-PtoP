package com.example.axel.ptop;

/**
 * Created by axel_ on 29/08/2016.
 */
public class ChatMessage {
    private long id;
    private boolean isMe;
    private long userId;
    private String msg;
    private String time;

    public boolean getisMe() {
        return isMe;
    }

    public void setMe(boolean me) {
        this.isMe = me;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
