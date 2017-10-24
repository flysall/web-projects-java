package com.flysall.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.flysall.model.*;
import com.flysall.model.Collection;
import com.flysall.service.*;
import com.flysall.util.Response;

@Controller
@RequestMapping("/")
public class QuestionController {
	@Autowired
	private QuestionService questionService;
	@Autowired
	private CollectionService collectionService;
	@Autowired
	private UserService userService;

	@RequestMapping("/ask")
	@ResponseBody
	public Response ask(Question question, String topicNameString, HttpServletRequest request) {
		Integer userId = userService.getUserIdFromRedis(request);
		Integer questionId = questionService.ask(question, topicNameString, userId);
		return new Response(0, "", questionId);
	}

	@RequestMapping("/question/{questionId}")
	public String questionDetail(@PathVariable Integer questionId, HttpServletRequest request, Model model) {
		Integer userId = userService.getUserIdFromRedis(request);
		Map<String, Object> questionDetail = questionService.getQuestionDetail(questionId, userId);
		List<Collection> collectionList = collectionService.listCreatingCollection(userId);
		questionDetail.put("collectionList", collectionList);
		model.addAllAttributes(questionDetail);
		return "questionDetail";
	}

	@RequestMapping("/listQuestionByPage")
	@ResponseBody
	public Response listQuestionByPage(Integer page) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Question> questionList = questionService.listQuestionByPage(page);
		map.put("questionList", questionList);
		return new Response(0, "", map);
	}

	@RequestMapping("/judgePeopleFollowQuestion")
	@ResponseBody
	public Response judgePeopleFollowQuestion(Integer questionId, HttpServletRequest request) {
		Integer userId = userService.getUserIdFromRedis(request);
		boolean status = questionService.judgePeopleFollowQuestion(userId, questionId);
		return new Response(0, "", status);
	}

	@RequestMapping("/followQuestion")
	@ResponseBody
	public Response followQuestion(Integer questionId, HttpServletRequest request) {
		Integer userId = userService.getUserIdFromRedis(request);
		questionService.followQuestion(userId, questionId);
		;
		return new Response(0, "");
	}

	@RequestMapping("/unfollowQuestion")
	@ResponseBody
	public Response unfollowQuestion(Integer questionId, HttpServletRequest request) {
		Integer userId = userService.getUserIdFromRedis(request);
		questionService.unfollowQuestion(userId, questionId);
		return new Response(0, "");
	}
}
