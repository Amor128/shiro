package com.ermao.config;

import com.ermao.pojo.User;
import com.ermao.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义的 Realm，继承自 AuthorizingRealm
 * @author Ermao
 * Date: 2021/9/19 23:19
 */
public class UserRealm extends AuthorizingRealm {

	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	// 授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		System.out.println("执行了授权==>doGetAuthorizationInfo");

		Subject subject = SecurityUtils.getSubject();
		User currentUser = (User) subject.getPrincipal();

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addStringPermission(currentUser.getPermissions());
		return info;
	}

	// 认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		System.out.println("执行了认证==>doGetAuthenticationInfo");

		// 封装用户当前登录数据
		// 用户名，密码 从数据库中取
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		User user = userService.selectUserByUsername(token.getUsername());
		if (user == null) {
			return null;	// 抛出异常 UnknownAccountException
		}
		// 密码认证由 shiro 框架完成
		// 还可以进行加密 MD5、MD5盐值加密
		return new SimpleAuthenticationInfo(user, user.getPwd(), "");
	}
}
