package org.shine.stb.model;

public class Channel {
	
	private String imagePath;
	private String title;
	private String info;
	private String detail;
	private String rtspurl;
	
	public Channel(String imagePath,String title,String info,String detail,String rtspurl){
		this.imagePath = imagePath;
		this.title = title;
		this.info = info;
		this.detail = detail;
		this.rtspurl = rtspurl;
	}
	
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getRtspurl() {
		return rtspurl;
	}
	public void setRtspurl(String rtspurl) {
		this.rtspurl = rtspurl;
	}
}
