package com.flysall.util;

public class RedisKey {
	//收藏夹收藏的回答
	public static final String COLLECT = ":collect";
	//回答被哪些收藏夹收藏
	public static final String COLLECTED = ":collected";
	
	//某人关注的人
	public static final String FOLLOW_PEOPLE = ":follow_people";
	//某人被那些人关注
	public static final String FOLLOWED_PEOPLE = ":followed_people";
	
	//某人关注的哪些话题
	public static final String FOLLOW_TOPIC = ":follow_topic";
	//某话题被哪些人关注
	public static final String FOLLOWED_TOPIC = ":followed_topic";
	
	//某人关注哪些问题
	public static final String FOLLOW_QUESTION = ":follow_question";
	//某些问题被哪些人关注
	public static final String FOLLOWED_QUESTITON = ":followed_question";
	
	//某人关注哪些收藏夹
	public static final String FOLLOW_COLLECTION = ":follow_question";
	//某收藏夹被那些人关注
	public static final String FOLLOWED_COLLECTION = ":followed_question";
	
	//某人点赞哪些回答
	public static final String LIKE_ANSWER = ":like_answer";
	//某回答被哪些人点赞
	public static final String LIKED_ANSWER = ":liked_answer";
	
	//某人点赞了哪些问题评论
	public static final String LIKE_QUESTION_COMMENT = 	":like_question_answer";
	//某问题评论被哪些人点赞 
	public static final String LIKED_QUESTION_COMMENT = ":liked_question_answer";
	
	// 某人点赞了哪些回答评论
	public static final String LIKE_ANSWER_COMMENT = ":like_answer_comment";
	// 某回答评论被哪些人点赞
	public static final String LIKED_ANSWER_COMMENT = ":liked_answer_comment";

	// 某问题被浏览次数
	public static final String QUESTION_SCANED_COUNT = ":question_scaned_count";
}

























