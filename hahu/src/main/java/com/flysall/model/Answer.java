package com.flysall.model;

import java.util.List;

public class Answer {
	private Integer answerId;
	private String answerContent; 	//回答的具体内容
	private Integer likedCount; 	//回答被赞数目
	private Long createTime; 		//回答创建时间，为Long类型
	
	private Integer questionId;	//该回答对应的问题ID
	private Integer userId; 		//该回答的用户ID
	
	private Question question;
	private User user;
	
	private String likeState;
	private Integer commentCount;
	
	private List<AnswerComment> answerCommentList;	
	
	public Integer getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Integer answerId) {
		this.answerId = answerId;
	}

	public String getAnswerContent() {
		return answerContent;
	}

	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
	}

	public Integer getLikedCount() {
		return likedCount;
	}

	public void setLikedCount(Integer likedCount) {
		this.likedCount = likedCount;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
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

	public List<AnswerComment> getAnswerCommentList() {
		return answerCommentList;
	}

	public void setAnswerCommentList(List<AnswerComment> answerCommentList) {
		this.answerCommentList = answerCommentList;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public String getLikeState() {
		return likeState;
	}

	public void setLikeState(String likeState) {
		this.likeState = likeState;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}
	
	@Override
	public String toString(){
		return "Answer [answerId=" + answerId + ", anserContent=" + answerContent + ", likedCount=" + likedCount + ", createTime=" + createTime + ", questionId=" + questionId + ", userId=" + userId + ", question=" + question + ", user=" + user + ", likeState=" + likeState + ", answerCommentList=" 
				+ answerCommentList + "]";
	}
}





















