package com.ermao.service;

import com.ermao.pojo.User;

/**
 * @author Ermao
 * Date: 2021/9/20 11:26
 */
public interface UserService {
	User selectUserByUsername(String username);
}
