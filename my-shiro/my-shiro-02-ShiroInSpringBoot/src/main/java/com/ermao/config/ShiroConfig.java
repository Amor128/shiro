package com.ermao.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Ermao
 * Date: 2021/9/19 23:16
 */
@Configuration
public class ShiroConfig {
	// 1. 创建 ShiroFilterFactoryBean
	@Bean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("getDefaultWebSecurityManager") DefaultWebSecurityManager getDefaultWebSecurityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		// 设置安全管理器
		shiroFilterFactoryBean.setSecurityManager(getDefaultWebSecurityManager);

		// 添加 shiro 内置过滤器
		// anon 无需认证
		// authc 必须认证才能访问
		// user 类必须拥有 记住我 才能使用
		// perms 拥有对某个资源的权限才能访问
		// role 拥有某个角色产能访问
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

		// 授权、鉴权
		filterChainDefinitionMap.put("/user/insert", "perms[user:insert]");
		filterChainDefinitionMap.put("/user/update", "perms[user:update]");

		// 拦截
		filterChainDefinitionMap.put("/user/*", "authc");

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		shiroFilterFactoryBean.setLoginUrl("/toLogin");	// 设置登录请求
		shiroFilterFactoryBean.setUnauthorizedUrl("/unauth");	// 设置未授权请求

		return shiroFilterFactoryBean;
	}

	// 2. 创建 DefaultWebSecurityManager
	@Bean
	public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm) {
		DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
		// 管理 realm
		defaultWebSecurityManager.setRealm(userRealm);
		return defaultWebSecurityManager;
	}

	// 1. 创建 Realm 对象，自定义类
	@Bean
	public UserRealm userRealm() {
		return new UserRealm();
	}

	// 整合 ShiroDialect
	@Bean
	public ShiroDialect getShiroDialect() {
		return new ShiroDialect();
	}
}
