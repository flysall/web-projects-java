package com.flysall.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.flysall.mapper.*;
import com.flysall.model.*;
import com.flysall.util.*;

@Service
public class CommentService {
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private CommentMapper commentMapper;

	@Autowired
	private MessageMapper messageMapper;

	@Autowired
	private QuestionMapper questionMapper;
	@Autowired
	private AnswerMapper answerMapper;

	@Autowired
	private JedisPool jedisPool;

	/**
	 * 对问题评论
	 * 
	 * @param questionId
	 * @param commentContent
	 * @param userId
	 * @return
	 */
	public QuestionComment commentQuestion(Integer questionId, String commentContent, Integer userId) {
		QuestionComment comment = new QuestionComment();
		comment.setLikedCount(0);
		comment.setCreateTime(new Date().getTime());
		comment.setQuestionCommentContent(commentContent);
		comment.setQuestionId(questionId);
		comment.setUserId(userId);

		commentMapper.insertQuestionComment(comment);
		User user = userMapper.selectUserInfoByUserId(userId);
		comment.setUser(user);

		return comment;
	}

	/**
	 * 
	 * @param comment
	 * @param userId
	 * @return
	 */
	public QuestionComment replyQuestionComment(QuestionComment comment, Integer userId) {
		comment.setLikedCount(0);
		comment.setCreateTime(new Date().getTime());
		comment.setUserId(userId);

		commentMapper.insertQuestionCommentReply(comment);
		User user = userMapper.selectUserInfoByUserId(userId);
		comment.setUser(user);

		return comment;
	}

	/**
	 * 对回答进行评论
	 * 
	 * @param answerId
	 * @param commentContent
	 * @param userId
	 * @return
	 */
	public AnswerComment commentAnswer(Integer answerId, String commentContent, Integer userId) {
		AnswerComment comment = new AnswerComment();
		comment.setLikedCount(0);
		comment.setCreateTime(new Date().getTime());
		comment.setAnswerCommentContent(commentContent);
		comment.setAnswerId(answerId);
		comment.setUserId(userId);

		commentMapper.insertAnswerComment(comment);
		User user = userMapper.selectUserInfoByUserId(userId);
		comment.setUser(user);

		// 插入一条评论信息
		Message message = new Message();
		message.setType(Message.TYPE_COMMENT);
		message.setSecondType(1);
		Date date = new Date();
		message.setMessageDate(MyUtil.formatDate(date));
		message.setMessageTime(date.getTime());
		message.setFromUserId(userId);
		message.setFromUserName(userMapper.selectUsernameByUserId(userId));
		Question question = questionMapper.selectQuestionByAnswerId(answerId);
		message.setQuestionId(question.getQuestionId());
		message.setQuestionTitle(question.getQuestionTitle());
		message.setAnswerId(answerId);
		message.setCommentId(comment.getAnswerCommentId());
		message.setUserId(answerMapper.selectUserIdByAnswerId(answerId));
		messageMapper.insertTypeComment(message);

		return comment;
	}

	/**
	 * 
	 * @param comment
	 * @param userId
	 * @return
	 */
	public AnswerComment replyAnswerComment(AnswerComment comment, Integer userId) {
		comment.setLikedCount(0);
		comment.setCreateTime(new Date().getTime());
		comment.setUserId(userId);

		commentMapper.insertAnswerCommentReply(comment);
		User user = userMapper.selectUserInfoByUserId(userId);
		comment.setUser(user);

		return comment;
	}

	/**
	 * 对问题进行点赞
	 * 
	 * @param userId
	 * @param questionCommentId
	 */
	public void likeQuestionComment(Integer userId, Integer questionCommentId) {
		Jedis jedis = jedisPool.getResource();
		jedis.zadd(userId + RedisKey.LIKE_QUESTION_COMMENT, new Date().getTime(), String.valueOf(questionCommentId));
		jedis.zadd(questionCommentId + RedisKey.LIKED_QUESTION_COMMENT, new Date().getTime(), String.valueOf(userId));
		jedisPool.returnResource(jedis);
	}

	/**
	 * 对回答进行点赞
	 * 
	 * @param userId
	 * @param answerCommentId
	 */
	public void likeAnswerComment(Integer userId, Integer answerCommentId) {
		Jedis jedis = jedisPool.getResource();
		jedis.zadd(userId + RedisKey.LIKE_ANSWER, new Date().getTime(), String.valueOf(answerCommentId));
		jedis.zadd(answerCommentId + RedisKey.LIKED_ANSWER_COMMENT, new Date().getTime(), String.valueOf(userId));
		jedisPool.returnResource(jedis);
	}
}
