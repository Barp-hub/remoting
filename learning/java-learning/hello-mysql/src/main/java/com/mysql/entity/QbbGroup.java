package com.mysql.entity;

import java.math.BigDecimal;
import java.util.Date;

public class QbbGroup {
    private Long gid;

    private String title;

    private String des;

    private String location;

    private BigDecimal longitude;

    private BigDecimal latitude;

    private Long createBy;

    private Date createTime;

    private Integer limit;

    private Integer status;

    private Integer withBaby;

    private Integer needValidation;

    private Integer previewAlbum;

    private String avatar;

    private String secret;

    private Date updateTime;

    public Long getGid() {
        return gid;
    }

    public void setGid(Long gid) {
        this.gid = gid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getWithBaby() {
        return withBaby;
    }

    public void setWithBaby(Integer withBaby) {
        this.withBaby = withBaby;
    }

    public Integer getNeedValidation() {
        return needValidation;
    }

    public void setNeedValidation(Integer needValidation) {
        this.needValidation = needValidation;
    }

    public Integer getPreviewAlbum() {
        return previewAlbum;
    }

    public void setPreviewAlbum(Integer previewAlbum) {
        this.previewAlbum = previewAlbum;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}