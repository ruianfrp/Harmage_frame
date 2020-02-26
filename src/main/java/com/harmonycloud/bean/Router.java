package com.harmonycloud.bean;

public class Router {
	private String key;
	private String title;
	private String icon;
	public Router(){}
	public Router(String key,String title,String icon){
		this.key=key;
		this.title=title;
		this.icon=icon;
	}
	public String getKey() {
		return key;
	}
	public String getTitle() {
		return title;
	}
	public String getIcon() {
		return icon;
	}
	public void setKey(String key){this.key=key;}
	public void setTitle(String title){this.title=title;}
	public void setIcon(String icon){this.icon=icon;}

}
