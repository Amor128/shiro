package com.ermao;

import com.ermao.mapper.UserMapper;
import com.ermao.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MyShiro02ShiroInSpringBootApplicationTests {

	@Autowired
	UserService userService;

	@Autowired
	UserMapper userMapper;

	@Test
	void testSelectUserByUsername() {
		System.out.println(userService.selectUserByUsername("ermao"));
	}
	@Test
	void contextLoads() {
	}

}
