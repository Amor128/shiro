import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Simple Quickstart application showing how to use Shiro's API.
 *
 * @since 0.9 RC2
 */
public class Quickstart {

	private static final transient Logger log = LoggerFactory.getLogger(Quickstart.class);


	public static void main(String[] args) {

		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
		SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);

		// 获取当前用户对象 subject
		Subject currentUser = SecurityUtils.getSubject();

		// 通过当前用户拿到 session
		Session session = currentUser.getSession();
		session.setAttribute("someKey", "你好，世界！");
		String value = (String) session.getAttribute("someKey");
		if (value.equals("你好，世界！")) {
			log.info("得到了正确的结果! [" + value + "]");
		}


		// 判断当前的用户是否被认证
		if (!currentUser.isAuthenticated()) {
			// new 一个 token，通过用户名和密码
			UsernamePasswordToken token = new UsernamePasswordToken("lonestarr", "vespa");
			token.setRememberMe(true);	// 设置 记住我
			try {
				currentUser.login(token);	// 执行登录操作
			} catch (UnknownAccountException uae) {	// 未知账户
				log.info("There is no user with username of " + token.getPrincipal());
			} catch (IncorrectCredentialsException ice) {
				log.info("Password for account " + token.getPrincipal() + " was incorrect!");
			} catch (LockedAccountException lae) {	// 用户被锁定
				log.info("The account for username " + token.getPrincipal() + " is locked.  " +
						"Please contact your administrator to unlock it.");
			}
			// ... catch more exceptions here (maybe custom ones specific to your application?
			catch (AuthenticationException ae) {	// 认证异常
				//unexpected condition?  error?
			}
		}

		// 表明是谁：
		//print their identifying principal (in this case, a username):
		log.info("User [" + currentUser.getPrincipal() + "] logged in successfully.");

		// 测试角色
		if (currentUser.hasRole("schwartz")) {
			log.info("你拥有 schwartz 的角色");
		} else {
			log.info("你只是凡人.");
		}

		// 测试权限（粗粒度）
		if (currentUser.isPermitted("lightsaber:wield")) {
			log.info("你拥有 lightsaber:* 的权限");
		} else {
			log.info("抱歉，你没有 lightsaber:* 的权限");
		}

		// 测试权限（细粒度），带有参数
		if (currentUser.isPermitted("winnebago:drive:eagle5")) {
			log.info("You are permitted to 'drive' the winnebago with license plate (id) 'eagle5'.  " +
					"Here are the keys - have fun!");
		} else {
			log.info("Sorry, you aren't allowed to drive the 'eagle5' winnebago!");
		}

		// 注销
		currentUser.logout();

		System.exit(0);
	}
}