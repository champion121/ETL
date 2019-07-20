package com.tl.job007.pojos;

public class UserInfoPojo {
	private String id;
	private String remark;

	public UserInfoPojo(String id, String remark) {
		super();
		this.id = id;
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "UserInfoPojo [id=" + id + ", remark=" + remark + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
