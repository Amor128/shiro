package com.ermao.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Ermao
 * Date: 2021/9/19 23:04
 */
@Controller
public class MyController {

	@RequestMapping({"/", "/index"})
	public String toIndex(Model model) {
		model.addAttribute("msg", "hello, shiro");
		return "index";
	}

	@RequestMapping("/user/insert")
	public String add(Model model) {
		model.addAttribute("msg", "hello, insert");
		return "user/insert";
	}

	@RequestMapping("/user/update")
	public String update(Model model) {
		model.addAttribute("msg", "hello, update");
		return "user/update";
	}

	@RequestMapping("/toLogin")
	public String toLogin() {
		return "login";
	}

	@RequestMapping("/login")
	public String login(String username, String password, Model model) {
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {
			subject.login(token);	// 执行登录的方法，如果没有异常就说明 OK
			return "index";
		} catch (UnknownAccountException e) {	// 用户名不存在
			model.addAttribute("msg", "用户名错误");
			return "login";
		} catch (IncorrectCredentialsException e) {	// 用户名不存在
			model.addAttribute("msg", "密码错误");
			return "login";
		}
	}

	@RequestMapping("/unauth")
	@ResponseBody
	public String unAuthorized() {
		return "未经授权无法访问";
	}

	@RequestMapping("/logout")
	public String logout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return "login";
	}
}
