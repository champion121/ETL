package com.tl.job007.pojos;

/**
 * 
 * @author tianliang
 *
 * @date 2019年6月5日
 */
public class ContentInfoPojo {
	private String content;
	private String time;
	private String repostsCount;
	private String commentsCount;
	private String id;
	
	@Override
	public String toString() {
		return "ContentInfoPojo [id=" + id + ", content=" + content + ", time="
				+ time + ", repostsCount=" + repostsCount + ", commentsCount="
				+ commentsCount + "]";
	}
	public ContentInfoPojo(String content, String time, String repostsCount,
			String commentsCount) {
		super();
		this.content = content;
		this.time = time;
		this.repostsCount = repostsCount;
		this.commentsCount = commentsCount;
	}
	public String getId() {
		return id;
	}
	public ContentInfoPojo(String id, String content, String time,
			String repostsCount, String commentsCount) {
		super();
		this.id = id;
		this.content = content;
		this.time = time;
		this.repostsCount = repostsCount;
		this.commentsCount = commentsCount;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getRepostsCount() {
		return repostsCount;
	}
	public void setRepostsCount(String repostsCount) {
		this.repostsCount = repostsCount;
	}
	public String getCommentsCount() {
		return commentsCount;
	}
	public void setCommentsCount(String commentsCount) {
		this.commentsCount = commentsCount;
	}
	
}
