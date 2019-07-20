package com.tl.job007.pojos;

import java.util.List;

/**
 * 将结构化完成的对象集合，封装到该join pojo类中
 * @author tianliang
 *
 * @date 2019年6月11日
 */
public class UserAndContentJoinPojo {
	private List<UserInfoPojo> userPojoList;
	private List<ContentInfoPojo> contentPojoList;
	
	public UserAndContentJoinPojo(List<UserInfoPojo> userPojoList,
			List<ContentInfoPojo> contentPojoList) {
		super();
		this.userPojoList = userPojoList;
		this.contentPojoList = contentPojoList;
	}
	public List<UserInfoPojo> getUserPojoList() {
		return userPojoList;
	}
	public void setUserPojoList(List<UserInfoPojo> userPojoList) {
		this.userPojoList = userPojoList;
	}
	public List<ContentInfoPojo> getContentPojoList() {
		return contentPojoList;
	}
	public void setContentPojoList(List<ContentInfoPojo> contentPojoList) {
		this.contentPojoList = contentPojoList;
	}
	@Override
	public String toString() {
		return "UserAndContentJoinPojo [userPojoList.size()=" + userPojoList.size()
				+ ", contentPojoList.size()=" + contentPojoList.size() + "]";
	}
}
