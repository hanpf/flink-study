package com.hpf.study.flink.study01;

public class Event {

    private String userName;
    private String url;
    private String datatime;

    @Override
    public String toString() {
        return "Event{" +
                "userName='" + userName + '\'' +
                ", url='" + url + '\'' +
                ", datatime='" + datatime + '\'' +
                '}';
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDatatime(String datatime) {
        this.datatime = datatime;
    }

    public String getUserName() {
        return userName;
    }

    public String getUrl() {
        return url;
    }

    public String getDatatime() {
        return datatime;
    }
}
