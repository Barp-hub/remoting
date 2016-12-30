package com.mysql.entity;

import java.util.Date;

public class FileData {
	private Long	fid;

	private Long	owner;

	private Date	uploadtime;

	private Date	origtime;

	private Integer	size;

	private Integer	duration;

	private Short	filetype;

	private Short	width;

	private Short	height;

	private Short	farm;

	private Byte	status;

	private String	secret;

	private String	gpsinfo;

	private String	table;

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public Long getFid() {
		return fid;
	}

	public void setFid(Long fid) {
		this.fid = fid;
	}

	public Long getOwner() {
		return owner;
	}

	public void setOwner(Long owner) {
		this.owner = owner;
	}

	public Date getUploadtime() {
		return uploadtime;
	}

	public void setUploadtime(Date uploadtime) {
		this.uploadtime = uploadtime;
	}

	public Date getOrigtime() {
		return origtime;
	}

	public void setOrigtime(Date origtime) {
		this.origtime = origtime;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Short getFiletype() {
		return filetype;
	}

	public void setFiletype(Short filetype) {
		this.filetype = filetype;
	}

	public Short getWidth() {
		return width;
	}

	public void setWidth(Short width) {
		this.width = width;
	}

	public Short getHeight() {
		return height;
	}

	public void setHeight(Short height) {
		this.height = height;
	}

	public Short getFarm() {
		return farm;
	}

	public void setFarm(Short farm) {
		this.farm = farm;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getGpsinfo() {
		return gpsinfo;
	}

	public void setGpsinfo(String gpsinfo) {
		this.gpsinfo = gpsinfo;
	}
}