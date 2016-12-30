package com.mysql.entity;

import java.util.Date;

public class GroupBaby {
    private Long gbid;

    private Long gid;

    private Long bid;

    private Date createTime;

    private Long uid;

    private Integer status;

    private Date updateTime;

    public Long getGbid() {
        return gbid;
    }

    public void setGbid(Long gbid) {
        this.gbid = gbid;
    }

    public Long getGid() {
        return gid;
    }

    public void setGid(Long gid) {
        this.gid = gid;
    }

    public Long getBid() {
        return bid;
    }

    public void setBid(Long bid) {
        this.bid = bid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}