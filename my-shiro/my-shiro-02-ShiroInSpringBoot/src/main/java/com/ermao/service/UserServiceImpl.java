package com.ermao.service;

import com.ermao.mapper.UserMapper;
import com.ermao.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Ermao
 * Date: 2021/9/20 11:27
 */
@Service
public class UserServiceImpl implements UserService {

	private UserMapper userMapper;

	@Autowired
	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Override
	public User selectUserByUsername(String username) {
		return userMapper.selectUserByUsername(username);
	}
}
