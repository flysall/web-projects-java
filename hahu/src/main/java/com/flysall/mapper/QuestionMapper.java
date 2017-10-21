package com.flysall.mapper;

import java.util.*;

import org.apache.ibatis.annotations.Param;

import com.flysall.model.Question;

public interface QuestionMapper {
	Integer insertQuestion(Question question);

	Question selectQuestionByQuestionId(@Param("questionId") Integer questionId);

	int selectQuestionCountByUserId(@Param("userId") Integer userId);

	List<Question> listQuestionByUserId(Map<String, Object> map);

	List<Question> listQuestionByPage(@Param("offset") int offset, @Param("limit") int limit);

	void insertIntoQuestionTopic(@Param("questionId") Integer questionId, @Param("topicId") Integer topicId);

	int selectQuestionCountByTopicId(@Param("topicId") Integer topicId);

	List<Integer> listQuestionIdByTopicId(Map<String, Object> map);
	
	List<Question> listQuestionByQuestionId(List<Integer> questionIdList);

	List<Question> listRelateQuestion(@Param("questionId") Integer questionId);

	Question selectQuestionByAnswerId(@Param("answerId") Integer answerId);
}
