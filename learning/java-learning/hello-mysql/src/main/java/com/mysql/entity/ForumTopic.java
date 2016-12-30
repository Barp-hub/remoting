package com.mysql.entity;

import java.util.Date;

public class ForumTopic {
    private Long tid;

    private Short status;

    private Long groupid;

    private Boolean withPhoto;

    private Boolean receiveMsg;

    private Boolean selected;

    private Long uid;

    private Date createTime;

    private Date updateTime;

    private Integer postNum;

    private Integer visitNum;

    private Integer likeNum;

    private Integer ownerPost;

    private Integer photoPost;

    private Integer hot;

    private String secret;

    private String title;

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        this.tid = tid;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Long getGroupid() {
        return groupid;
    }

    public void setGroupid(Long groupid) {
        this.groupid = groupid;
    }

    public Boolean getWithPhoto() {
        return withPhoto;
    }

    public void setWithPhoto(Boolean withPhoto) {
        this.withPhoto = withPhoto;
    }

    public Boolean getReceiveMsg() {
        return receiveMsg;
    }

    public void setReceiveMsg(Boolean receiveMsg) {
        this.receiveMsg = receiveMsg;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getPostNum() {
        return postNum;
    }

    public void setPostNum(Integer postNum) {
        this.postNum = postNum;
    }

    public Integer getVisitNum() {
        return visitNum;
    }

    public void setVisitNum(Integer visitNum) {
        this.visitNum = visitNum;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public Integer getOwnerPost() {
        return ownerPost;
    }

    public void setOwnerPost(Integer ownerPost) {
        this.ownerPost = ownerPost;
    }

    public Integer getPhotoPost() {
        return photoPost;
    }

    public void setPhotoPost(Integer photoPost) {
        this.photoPost = photoPost;
    }

    public Integer getHot() {
        return hot;
    }

    public void setHot(Integer hot) {
        this.hot = hot;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}