package com.flysall.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.flysall.model.Message;

public interface MessageMapper {
	void insertTypeFollowed(Message message);

	void insertTypeLiked(Message message);

	void insertTypeComment(Message message);

	void insertTypeAnswer(Message message);

	List<Message> listMessageByUserId(@Param("userId") Integer userId);
}
