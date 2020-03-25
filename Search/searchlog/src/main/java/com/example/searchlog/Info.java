package com.example.searchlog;

public class Info {
    private String time;
    private String keyword;
    public Info(String time,String keyword){
        this.time=time;
        this.keyword=keyword;
    }
    public void setTime(String time){
        this.time=time;
    }
    public void setKeyword(String keyword){
        this.keyword=keyword;
    }

    public String getTime(){
        return time;
    }
    public String getKeyword(){
        return keyword;
    }
}
