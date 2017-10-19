package com.flysall.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.alibaba.fastjson.JSON;
import com.flysall.mapper.*;
import com.flysall.model.*;
import com.flysall.util.*;

@Service
public class QuestionService {
	@Autowired
	private QuestionMapper questionMapper;
	@Autowired
	private AnswerMapper answerMapper;
	@Autowired
	private TopicMapper topicMapper;
	@Autowired 
	private UserMapper userMapper;
	@Autowired 
	private CommentMapper commentMapper;
	@Autowired
	private JedisPool jedisPool;
	
	/**
	 * 提问
	 * @param question
	 * @param topicNameString
	 * @param userId
	 * @return
	 */
	public Integer ask(Question question, String topicNameString, Integer userId){
		String[] topicNames = topicNameString.split(",");
		System.out.println(Arrays.toString(topicNames));
		Map<Integer, String> map = new HashMap<>();
		
		List<Integer> topicIdList = new ArrayList<>();
		for(String topicName : topicNames){
			Topic topic = new Topic();
			Integer topicId = topicMapper.selectTopicIdByTopicName(topicName);
			if(topicId == null){
				topic.setTopicName(topicName);;
				topic.setParentTopicId(1);
				topicMapper.insertTopic(topic);
				topicId = topic.getTopicId();
			}
			map.put(topicId, topicName);
			topicIdList.add(topicId);
		}
		String topicKvList = JSON.toJSONString(map);
		question.setTopicKvList(topicKvList);
		question.setCreatetime(new Date().getTime());
		question.setUserId(userId);
		questionMapper.insertQuestion(question);
		
		//向关联表插入数据
		for(Integer topicId : topicIdList){
			questionMapper.insertIntoQuestionTopic(question.getQuestionId(), topicId);;
		}
		
		return question.getQuestionId();
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getQuestionDetail(Integer questionId, Integer userId){
		Map<String, Object> map = new HashMap<>();
		//获取问题信息
		Question question = questionMapper.selectQuestionByQuestionId(questionId);
		if(question == null){
			throw new RuntimeException("该问题id不存在");
		}
		//获取问题被浏览次数
		Jedis jedis = jedisPool.getResource();
		jedis.zincrby(RedisKey.QUESTION_SCANED_COUNT, 1, questionId + "");
		question.setSanedCount((int) jedis.zscore(RedisKey.QUESTION_SCANED_COUNT, questionId + "").doubleValue());
		
	}
}

























