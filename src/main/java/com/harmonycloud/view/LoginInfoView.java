package com.harmonycloud.view;

public class LoginInfoView {
	private String id;
	private String name;
	private String role;
	private String unionid_dingding;

	public LoginInfoView(){}

	public LoginInfoView(String id, String name, String role){
		this.id=id;
		this.name=name;
		this.role=role;
	}

	public LoginInfoView(String id, String name, String role,String unionid){
		this.id=id;
		this.name=name;
		this.role=role;
		this.unionid_dingding=unionid;
	}

	public String getId() {
		return id;
	}

	public String getUnionid_dingding() {
		return unionid_dingding;
	}

	public void setUnionid_dingding(String unionid_dingding) {
		this.unionid_dingding = unionid_dingding;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
