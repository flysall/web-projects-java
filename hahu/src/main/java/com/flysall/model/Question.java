package com.flysall.model;

import java.util.*;

public class Question {
	private Integer questionId;
	private String questionTitle;
	private String questionConnent;
	private String topicKvList;
	private Integer followedCount;
	private Integer sanedCount;
	private Long createtime;
	
	private Integer userId;
	
	private User user;
	private Integer answerCount;
	
	private List<QuestionComment> questionCommentList;

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public String getQuestionTitle() {
		return questionTitle;
	}

	public void setQuestionTitle(String questionTitle) {
		this.questionTitle = questionTitle;
	}

	public String getQuestionConnent() {
		return questionConnent;
	}

	public void setQuestionConnent(String questionConnent) {
		this.questionConnent = questionConnent;
	}

	public String getTopicKvList() {
		return topicKvList;
	}

	public void setTopicKvList(String topicKvList) {
		this.topicKvList = topicKvList;
	}

	public Integer getFollowedCount() {
		return followedCount;
	}

	public void setFollowedCount(Integer followedCount) {
		this.followedCount = followedCount;
	}

	public Integer getSanedCount() {
		return sanedCount;
	}

	public void setSanedCount(Integer sanedCount) {
		this.sanedCount = sanedCount;
	}

	public Long getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Long createtime) {
		this.createtime = createtime;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getAnswerCount() {
		return answerCount;
	}

	public void setAnswerCount(Integer answerCount) {
		this.answerCount = answerCount;
	}

	public List<QuestionComment> getQuestionCommentList() {
		return questionCommentList;
	}

	public void setQuestionCommentList(List<QuestionComment> questionCommentList) {
		this.questionCommentList = questionCommentList;
	}

	@Override
	public String toString() {
		return "Question [questionId=" + questionId + ", questionTitle=" + questionTitle + ", questionConnent="
				+ questionConnent + ", topicKvList=" + topicKvList + ", followedCount=" + followedCount
				+ ", sanedCount=" + sanedCount + ", createtime=" + createtime + ", userId=" + userId + ", answerCount="
				+ answerCount + "]";
	}
}