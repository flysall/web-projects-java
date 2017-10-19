package com.flysall.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.flysall.mapper.*;
import com.flysall.model.*;
import com.flysall.model.Collection;
import com.flysall.util.*;

@Service
public class CollectionService {
	@Autowired 
	private CollectionMapper collectionMapper;
	
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private AnswerMapper answerMapper;
	@Autowired
	private JedisPool jedisPool;
	
	/**
	 * 创建收藏夹
	 * @param collection
	 * @param userId
	 */
	public void addCollection(Collection collection, Integer userId){
		collection.setUserId(userId);
		collection.setCreateTime(new Date().getTime());
		collection.setUpdateTime(new Date().getTime());
		collectionMapper.insertCollection(collection);
	}
	
	/**
	 * 列出自己创建的收藏夹
	 * @param userId
	 * @return
	 */
	public List<Collection> listCreatingCollection(Integer userId){
		//获取收藏夹列表
		List<Collection> list = collectionMapper.listCreatingCollectionByUserId(userId);
		//获取每个列表中的答案条数
		Jedis jedis = jedisPool.getResource();
		for(Collection collection : list){
			long answerCount = jedis.zcard(collection.getCollectionId() + RedisKey.COLLECT);
			collection.setAnswerCount(Integer.parseInt(answerCount + ""));
			System.out.println(answerCount);
		}
		jedisPool.returnResource(jedis);
		return list;
	}
	
	/**
	 * 取消收藏答案
	 * @param collectionId
	 * @param answerId
	 */
	public void uncollectAnswer(Integer collectionId, Integer answerId){
		Jedis jedis = jedisPool.getResource();
		jedis.zrem(collectionId + RedisKey.COLLECT, String.valueOf(answerId));
		jedis.zrem(answerId + RedisKey.COLLECTED, String.valueOf(collectionId));
		jedisPool.returnResource(jedis);
	}
	
	/**
	 * 若回答(answerId)在该收藏夹(collectionId)中返回true
	 * @param collectionId
	 * @param answerId
	 * @return
	 */
	public boolean collectionContainAnswer(Integer collectionId, Integer answerId){
		Jedis jedis = jedisPool.getResource();
		Long rank = jedis.zrank(collectionId + RedisKey.COLLECT, String.valueOf(answerId));
		jedisPool.returnResource(jedis);
		return rank == null ? false : true;
	}
	
	/**
	 * 获取收藏夹内容详情
	 * @param collectionId
	 * @param localUserId
	 * @return
	 */
	public Map<String, Object> getCollectionContent(Integer collectionId, Integer localUserId){
		Map<String, Object> map = new HashMap<String, Object>();
		Jedis jedis = jedisPool.getResource();
		
		//获取收藏夹内所有答案的id列表
		Set<String> idSet = jedis.zrange(collectionId + RedisKey.COLLECT, 0, -1);
		List<Integer> idList = MyUtil.StringSetToIntegerList(idSet);
		if(idList.size() > 0){
			List<Answer> answerList = answerMapper.listAnswerByAnswerId(idList);
			map.put("answerList", answerList);
		}
		
		//判断是否是在读取自己的收藏夹信息
		Integer userId = collectionMapper.selectUserIdByCollectionId(collectionId);
		if(userId.equals(localUserId)){
			map.put("isSelf",  true);
		}
		
		//获取收藏夹信息
		Collection collection = collectionMapper.selectCollectionByCollectionId(collectionId);
		long answerCount = jedis.zcard(collectionId + RedisKey.COLLECT);
		collection.setAnswerCount(Integer.parseInt(answerCount + ""));
		map.put("collection", collection);
		
		jedisPool.returnResource(jedis);
		return map;
	}
	
	/**
	 * 判断某人是否关注了某收藏夹
	 * @param userId
	 * @param collectionId
	 * @return
	 */
	public boolean judgePeopleFollowCollection(Integer userId, Integer collectionId){
		Jedis jedis = jedisPool.getResource();
		Long rank = jedis.zrank(userId + RedisKey.FOLLOW_COLLECTION, String.valueOf(collectionId));
		jedisPool.returnResource(jedis);
		
		System.out.println(rank);
		return rank == null ? false : true;
	}
	
	/**
	 * 关注收藏夹
	 * @param userId
	 * @param collectionId
	 */
	public void followCollection(Integer userId, Integer collectionId){
		Jedis jedis = jedisPool.getResource();
		jedis.zadd(userId + RedisKey.FOLLOW_COLLECTION, new Date().getTime(), String.valueOf(collectionId));
		jedis.zadd(collectionId + RedisKey.FOLLOW_COLLECTION, new Date().getTime(), String.valueOf(userId));
		jedisPool.returnResource(jedis);
	}
	
	/**
	 * 取消关注收藏夹
	 * @param userId
	 * @param collectionId
	 */
	public void unfollowCollection(Integer userId, Integer collectionId){
		Jedis jedis = jedisPool.getResource();
		jedis.zrem(userId + RedisKey.FOLLOW_COLLECTION, String.valueOf(collectionId));
		jedis.zrem(collectionId + RedisKey.FOLLOWED_COLLECTION, String.valueOf(userId));
		jedisPool.returnResource(jedis);
	}
	
	/**
	 * 列出某用户所关注的收藏夹
	 * @param userId
	 * @return
	 */
	public List<Collection> listFollowingCollection(Integer userId){
		Jedis jedis = jedisPool.getResource();
		Set<String> idSet = jedis.zrange(userId + RedisKey.FOLLOW_COLLECTION, 0, -1);
		List<Integer> idList = MyUtil.StringSetToIntegerList(idSet);
		
		List<Collection> list = new ArrayList<Collection>();
		if(idList.size() > 0){
			list = collectionMapper.listCollectionByCollectionId(idList);
			for(Collection collection : list){
				Long answerCount = jedis.zcard(collection.getCollectionId() + RedisKey.COLLECT);
				collection.setAnswerCount(Integer.parseInt(answerCount + ""));
				System.out.println(answerCount);
			}
		}
		
		jedisPool.returnResource(jedis);
		return list;
	}
}


























