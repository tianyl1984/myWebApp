package com.hzth.myapp.dc;

import java.io.Serializable;

public class TeachingMaterialContent implements Serializable {

	private static final long serialVersionUID = 5731536573435995686L;
	private Integer _id;
	private String teachingMaterialDcId;
	private String dcId;
	private Integer pageNo;
	private String filePath;
	private String status = STATUS_UNDOWNLOAD;
	private String url;

	public static final String STATUS_UNDOWNLOAD = "0";
	public static final String STATUS_DOWNLOAD_FINISHED = "1";

	public TeachingMaterialContent() {

	}

	public Integer get_id() {
		return _id;
	}

	public void set_id(Integer _id) {
		this._id = _id;
	}

	public String getTeachingMaterialDcId() {
		return teachingMaterialDcId;
	}

	public void setTeachingMaterialDcId(String teachingMaterialDcId) {
		this.teachingMaterialDcId = teachingMaterialDcId;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDcId() {
		return dcId;
	}

	public void setDcId(String dcId) {
		this.dcId = dcId;
	}

}
