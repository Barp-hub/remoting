package com.mysql.entity;

public class DbSequence {
    private String tablename;

    private Long currentposition;

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    public Long getCurrentposition() {
        return currentposition;
    }

    public void setCurrentposition(Long currentposition) {
        this.currentposition = currentposition;
    }
}