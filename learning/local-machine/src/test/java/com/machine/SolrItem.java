package com.machine;

import java.util.ArrayList;
import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;

public class SolrItem {

	@Field
	private String id;

	@Field
	private Date updateTime;

	@Field
	private String objectId;

	@Field
	private String typeId;

	@Field
	private String typeName;

	@Field
	private String title;

	@Field
	private ArrayList<String> content;

	@Field
	private String cat;

	@Field
	private String keywords;

	@Field
	private float weight;

	/**
	 * @return the id
	 */
	public final String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public final void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public final String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public final void setTitle(String title) {
		this.title = title;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setContent(ArrayList<String> content) {
		this.content = content;
	}

	public ArrayList<String> getContent() {
		return content;
	}

	public void setCat(String cat) {
		this.cat = cat;
	}

	public String getCat() {
		return cat;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public float getWeight() {
		return weight;
	}

}
