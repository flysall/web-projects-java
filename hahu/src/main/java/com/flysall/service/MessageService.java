package com.flysall.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flysall.mapper.*;
import com.flysall.model.*;

@Service
public class MessageService {
	@Autowired
	private AnswerMapper answerMapper;

	@Autowired
	private QuestionMapper questionMapper;

	@Autowired
	private MessageMapper messageMapper;
	
	/**
	 * 获得Message信息
	 * @param userId
	 * @return
	 */
	public Map<String, List<Message>> listMessage(Integer userId) {
		List<Message> messageList = messageMapper.listMessageByUserId(userId);
		Map<String, List<Message>> map = new HashMap<>();
		for (Message message : messageList) {
			String time = message.getMessageDate();
			if (map.get(time) == null) {
				map.put(time, new LinkedList<Message>());
				map.get(time).add(message);
			} else {
				map.get(time).add(message);
			}
		}
		return map;
	}
}
