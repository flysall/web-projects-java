package com.flysall.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.flysall.mapper.*;
import com.flysall.model.*;
import com.flysall.util.*;

@Service
public class TopicService {
	@Autowired
	private TopicMapper topicMapper;
	@Autowired
	private AnswerMapper answerMapper;

	@Autowired
	private QuestionMapper questionMapper;
	@Autowired
	private JedisPool jedisPool;

	/**
	 * 返回热点话题和根话题
	 * 
	 * @return Map对象
	 */
	public Map<String, Object> listAllTopic() {
		Map<String, Object> map = new HashMap<>();
		List<Topic> hotTopicList = topicMapper.listHotTopic();
		List<Topic> rootTopicList = topicMapper.listRootTopic();
		map.put("hotTopicList", hotTopicList);
		map.put("rootTopicList", rootTopicList);

		return map;
	}

	/**
	 * 根据父话题获取子话题列表
	 * 
	 * @param parentTopicId
	 * @return
	 */
	public List<Topic> listTopicByParentTopicId(Integer parentTopicId) {
		List<Topic> list = topicMapper.listTopicByParentId(parentTopicId);
		System.out.println(parentTopicId);
		System.out.println(list);
		return list;
	}

	/**
	 * 获取话题详情
	 * 
	 * @param topicId
	 * @param allQuestion
	 * @param curPage
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getTopicDetail(Integer topicId, Boolean allQuestion, Integer curPage, Integer userId) {
		Map<String, Object> map = new HashMap<>();
		// 获取话题信息
		Topic topic = topicMapper.selectTopicByTopicId(topicId);

		Jedis jedis = jedisPool.getResource();
		// 获取关注人数
		Long followedCount = jedis.zcard(topicId + RedisKey.FOLLOW_TOPIC);
		topic.setFollowedCount(Integer.parseInt(followedCount + ""));

		// 如果不是请求所有问题列表
		if (allQuestion == null || !allQuestion) {
			// 获得该话题下答案列表的分页数据
			List<Integer> questionIdList = topicMapper.selectQuestionIdByTopicId(topic.getTopicId());

			if (questionIdList.size() > 0) {
				PageBean<Answer> pageBean = _listGoodAnswerByQuestionId(questionIdList, curPage, jedis, userId);
				map.put("pageBean", pageBean);
			} else {
				map.put("pageBean", new PageBean<Answer>());
			}
		} else {
			PageBean<Question> pageBean = _listAllQuestionByTopicId(topic.getTopicId(), curPage, jedis);
			map.put("PageBean", pageBean);
			// 表明返回的是问题列表，而不是答案列表
			map.put("allQuestion", true);
		}

		map.put("topic", topic);
		jedisPool.returnResource(jedis);

		return map;
	}

	/**
	 * 判断用户是否关注了话题
	 * 
	 * @param userId
	 * @param topicId
	 * @return
	 */
	public boolean judgePeopleFollowTopic(Integer userId, Integer topicId) {
		Jedis jedis = jedisPool.getResource();
		Long rank = jedis.zrank(userId + RedisKey.FOLLOW_TOPIC, String.valueOf(topicId));
		jedisPool.returnResource(jedis);
		return rank == null ? false : true;
	}

	/**
	 * 关注话题
	 * 
	 * @param userId
	 * @param topicId
	 */
	public void followTopic(Integer userId, Integer topicId) {
		Jedis jedis = jedisPool.getResource();
		jedis.zadd(userId + RedisKey.FOLLOW_TOPIC, new Date().getTime(), String.valueOf(topicId));
		jedis.zadd(topicId + RedisKey.FOLLOWED_TOPIC, new Date().getTime(), String.valueOf(userId));
		jedisPool.returnResource(jedis);

		// 将话题关注数量增1
		topicMapper.updateFollowedCount(topicId);
	}

	/**
	 * 取消关注话题
	 * 
	 * @param userId
	 * @param topicId
	 */
	public void unfollowTopic(Integer userId, Integer topicId) {
		Jedis jedis = jedisPool.getResource();
		jedis.zrem(userId + RedisKey.FOLLOW_TOPIC, String.valueOf(topicId));
		jedis.zrem(topicId + RedisKey.FOLLOWED_TOPIC, String.valueOf(userId));
		jedisPool.returnResource(jedis);
	}

	/**
	 * 构造PageBean
	 * 
	 * @param questionIdList
	 * @param curPage
	 * @param jedis
	 * @param userId
	 * @return
	 */
	private PageBean<Answer> _listGoodAnswerByQuestionId(List<Integer> questionIdList, Integer curPage, Jedis jedis,
			Integer userId) {
		curPage = curPage == null ? 1 : curPage;
		int limit = 0;
		int offset = (curPage - 1) * limit;
		// 获得总记录数，总页数
		int allCount = answerMapper.listAnswerCountByQuestionId(questionIdList);
		int allPage = 0;
		if (allCount <= limit) {
			allPage = 1;
		} else if (allCount / limit == 0) {
			allPage = allCount / limit;
		} else {
			allPage = allCount / limit + 1;
		}

		// 构造查询map
		Map<String, Object> map = new HashMap<>();
		map.put("offset", offset);
		map.put("limit", limit);
		map.put("questionIdList", questionIdList);
		List<Answer> answerList = answerMapper.listGoodAnswerByQuestionId(map);

		for (Answer answer : answerList) {
			// 获取用户点赞状态
			Long rank = jedis.zrank(answer.getAnswerId() + RedisKey.LIKED_ANSWER, String.valueOf(userId));
			answer.setLikeState(rank == null ? "false" : "true");
		}
		// 构造PageBean
		PageBean<Answer> pageBean = new PageBean<>(allPage, curPage);
		pageBean.setList(answerList);
		return pageBean;
	}
	
	private PageBean<Question> _listAllQuestionByTopicId(Integer topicId, Integer curPage, Jedis jedis){
		curPage = curPage == null ? 1 : curPage;
		int limit = 8; 
		int offset = (curPage - 1) * limit;
		int allCount = questionMapper.selectQuestionCountByTopicId(topicId);
		int allPage = 0;
		if(allCount <= limit){
			allPage = 1;
		} else if(allCount / limit == 0){
			allPage = allCount / limit;
		} else{
			allPage = allCount / limit + 1;
		}
		
		Map<String, Object> map = new HashMap<>();
		map.put("offset", offset);
		map.put("limit", limit);
		map.put("topicId", topicId);
		//获得某页数据列表
		List<Integer> questionIdList = questionMapper.listQuestionIdByTopicId(map);
		System.out.println(questionIdList);
		List<Question> questionList = new ArrayList<Question>();
		if(questionList.size() > 0){
			questionList = questionMapper.listQuestionByQuestionId(questionIdList);
			for(Question question : questionList){
				Long followedCount = jedis.zcard(question.getQuestionId() + RedisKey.FOLLOWED_QUESTITON);
				question.setFollowedCount(Integer.parseInt(followedCount + ""));
			}
		}
		PageBean<Question> pageBean = new PageBean<>(allPage, curPage);
		pageBean.setList(questionList);
		return pageBean;
	}

	/**
	 * 列出用户关注的所有话题
	 * 
	 * @param userId
	 * @return
	 */
	public List<Topic> listFollowingTopic(Integer userId) {
		Jedis jedis = jedisPool.getResource();
		Set<String> idSet = jedis.zrange(userId + RedisKey.FOLLOW_TOPIC, 0, -1);
		List<Integer> idList = MyUtil.StringSetToIntegerList(idSet);

		List<Topic> list = new ArrayList<Topic>();
		if (idList.size() > 0) {
			list = topicMapper.listTopicByTopicId(idList);
		}
		jedisPool.returnResource(jedis);
		return list;
	}

	/**
	 * 通过话题名获得话题列表
	 * 
	 * @param topicName
	 * @return
	 */
	public Map<String, Object> listTopicByTopicName(String topicName) {
		Map<String, Object> map = new HashMap<>();
		List<Topic> topicList = topicMapper.listTopicByTopicName("%" + topicName + "%");
		map.put("topicList", topicList);
		return map;
	}
}
