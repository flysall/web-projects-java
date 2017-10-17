package com.flysall.model;

public class AnswerComment {
	private Integer answerCommentId;
	private String answerCommnetContent;
	private Integer likedCount;
	private long createTime;
	private Integer atUserId;

	private Integer answerId;
	private Integer userId;

	private String likeState;

	private User user;

	public Integer getAnswerCommentId() {
		return answerCommentId;
	}

	public void setAnswerCommentId(Integer answerCommentId) {
		this.answerCommentId = answerCommentId;
	}

	public String getAnswerCommnetContent() {
		return answerCommnetContent;
	}

	public void setAnswerCommnetContent(String answerCommnetContent) {
		this.answerCommnetContent = answerCommnetContent;
	}

	public Integer getLikedCount() {
		return likedCount;
	}

	public void setLikedCount(Integer likedCount) {
		this.likedCount = likedCount;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public Integer getAtUserId() {
		return atUserId;
	}

	public void setAtUserId(Integer atUserId) {
		this.atUserId = atUserId;
	}

	public Integer getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Integer answerId) {
		this.answerId = answerId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getLikeState() {
		return likeState;
	}

	public void setLikeState(String likeState) {
		this.likeState = likeState;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "AnswerComment [answerCommentId=" + answerCommentId + ", answerCommnetContent=" + answerCommnetContent
				+ ", likedCount=" + likedCount + ", createTime=" + createTime + ", atUserId=" + atUserId + ", answerId="
				+ answerId + ", userId=" + userId + ", likeState=" + likeState + "]";
	}
}