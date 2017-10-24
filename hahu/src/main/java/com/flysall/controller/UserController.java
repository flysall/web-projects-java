package com.flysall.controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private AnswerService answerService;
	@Autowired
	private QuestionService questionService;
	@Autowired
	private CollectionService collectionService;
	@Autowired
	private TopicService topicService;

	@RequestMapping("/toLogin")
	public String toLogin() {
		return "toLogin";
	}

	@RequestMapping("/register")
	@ResponseBody
	public Response register(String username, String email, String password) {
		Map<String, String> map = userService.register(username, email, password);
		if (map.get("ok") != null) {
			return new Response(0, "系统已经向你的邮箱发送了一封邮件，验证后即可登录");
		} else {
			return new Response(1, "error", map);
		}
	}

	@RequestMapping("/login")
	@ResponseBody
	public Response login(String email, String password, HttpServletResponse response) {
		Map<String, Object> map = userService.login(email, password, response);
		if (map.get("error") == null) {
			return new Response(0, "", map);
		} else {
			return new Response(1, map.get("error").toString());
		}
	}

	@RequestMapping("weiboLogin")
	@ResponseBody
	public String weiboLogin(String code, HttpServletResponse response) throws IOException {
		userService.weiboLogin(code, response);
		return "index";
	}

	@RequestMapping("/activate")
	public String activate(String code) {
		userService.activate(code);
		return "redirect:/toLogin#activateSuccess";
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		userService.logout(request, response);
		return "redirect:/toLogin";
	}

	@RequestMapping("/profile/{userId}")
	public String profile(@PathVariable Integer userId, Integer page, HttpServletRequest request, Model model) {
		Integer localUserId = userService.getUserIdFromRedis(request);
		Map<String, Object> map = userService.profile(userId, localUserId);
		// 获取答案列表
		PageBean<Answer> pageBean = answerService.listAnswerByUserId(userId, page);
		map.put("pageBean", pageBean);

		model.addAllAttributes(map);
		return "profileAnswer";
	}

	@RequestMapping("/profileQuestion/{userId}")
	public String profileQuestion(@PathVariable Integer userId, Integer page, HttpServletRequest request, Model model) {
		Integer localUserId = userService.getUserIdFromRedis(request);
		Map<String, Object> map = userService.profile(userId, localUserId);
		// 获取回答列表
		PageBean<Question> pageBean = questionService.listQuestionByUserId(userId, page);
		map.put("pageBean", pageBean);

		model.addAllAttributes(map);
		return "profileQuestion";
	}

	@RequestMapping("/profileCollection/{userId}")
	public String profileCollection(@PathVariable Integer userId, HttpServletRequest request, Model model) {
		Integer localUserId = userService.getUserIdFromRedis(request);
		Map<String, Object> map = userService.profile(userId, localUserId);
		// 获取收藏夹列表
		List<Collection> collectionList = collectionService.listCreatingCollection(userId);
		map.put("collectionList", collectionList);
		return "profileCollection";
	}

	@RequestMapping("/profileFolllowCollection/{userId}")
	public String profileFollowCollection(@PathVariable Integer userId, HttpServletRequest request, Model model) {
		Integer localUserId = userService.getUserIdFromRedis(request);
		Map<String, Object> map = userService.profile(userId, localUserId);
		List<User> userList = userService.listFollowingUser(userId);
		map.put("userList", userList);

		model.addAllAttributes(map);
		return "profileFollowPeople";
	}

	@RequestMapping("/profileFollowTopic/{userId}")
	public String profileFollowTopic(@PathVariable Integer userId, HttpServletRequest request, Model model) {
		Integer localUserId = userService.getUserIdFromRedis(request);
		Map<String, Object> map = userService.profile(userId, localUserId);
		List<Topic> topicList = topicService.listFollowingTopic(userId);
		map.put("topicList", topicList);
		model.addAllAttributes(map);
		return "profileFollowTopic";
	}

	@RequestMapping("/profileFollowQuestion/{userId}")
	public String profileFollowQuestion(@PathVariable Integer userId, HttpServletRequest request, Model model) {
		Integer localUserId = userService.getUserIdFromRedis(request);
		// 获取用户信息
		Map<String, Object> map = userService.profile(userId, localUserId);

		// 获取问题列表
		List<Question> questionList = questionService.listFollowingQuestion(userId);
		map.put("questionList", questionList);
		model.addAllAttributes(map);
		return "profileFollowQuestion";
	}

	@RequestMapping("/judgePeopleFollowUser")
	@ResponseBody
	public Response judgePeoplefollowUser(Integer userId, HttpServletRequest request) {
		Integer localUserId = userService.getUserIdFromRedis(request);
		boolean status = userService.judgePeopleFollowUser(localUserId, userId);
		return new Response(0, "", status);
	}

	@RequestMapping("/unfollowUser")
	@ResponseBody
	public Response unfollowUser(Integer userId, HttpServletRequest request) {
		Integer localUserId = userService.getUserIdFromRedis(request);
		userService.unfollowUser(localUserId, userId);
		return new Response(0, "");
	}

	@RequestMapping("/getWeiboUserInfo")
	@ResponseBody
	public Response getWeiboUserInfo(HttpServletRequest request) {
		Integer localUserId = userService.getUserIdFromRedis(request);
		Response response = userService.getWeiboUserInfo(localUserId);
		return response;
	}
}
