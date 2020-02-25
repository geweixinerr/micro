package micro.web.config.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import micro.web.config.shiro.filter.JwtFilter;
import micro.web.config.shiro.realm.UserRealm;

/**
 * shiro配置
 * 
 * @author gewx
 */
@Configuration
public class ShiroConfig {

	/**
	 * 自定义Realm,权限校验
	 * 
	 * @author gewx
	 */
	@Bean(value = "userRealm")
	public UserRealm userRealm() {
		UserRealm userRealm = new UserRealm();
		userRealm.setCachingEnabled(false);
		return userRealm;
	}

	/**
	 * 安全管理器
	 * 
	 * @author gewx
	 */
	@Bean(value = "securityManager")
	public SecurityManager securityManager(@Qualifier(value = "userRealm") UserRealm userRealm) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(userRealm);
		// 无状态应用服务器禁止session创建
		DefaultSubjectDAO subjectDao = new DefaultSubjectDAO();
		DefaultSessionStorageEvaluator webSubjectDao = (DefaultSessionStorageEvaluator) subjectDao
				.getSessionStorageEvaluator();
		webSubjectDao.setSessionStorageEnabled(false);
		securityManager.setSubjectDAO(subjectDao);
		securityManager.setSubjectFactory(new StatelessDefaultSubjectFactory());
		DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
		defaultWebSessionManager.setSessionValidationSchedulerEnabled(false);
		securityManager.setSessionManager(defaultWebSessionManager);

		return securityManager;
	}

	/**
	 * Shiro过滤器配置
	 * 
	 * @author gewx
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(
			@Qualifier(value = "securityManager") SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		shiroFilterFactoryBean.setLoginUrl("");
		shiroFilterFactoryBean.setSuccessUrl("");
		shiroFilterFactoryBean.setUnauthorizedUrl("");
		LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
		filterChainDefinitionMap.put("/druid/**", "anon");
		filterChainDefinitionMap.put("/captcha/captchaImage**", "anon");
		filterChainDefinitionMap.put("/api/logout", "logout");
		filterChainDefinitionMap.put("/api/login", "anon");
		filterChainDefinitionMap.put("/**", "jwt");

		Map<String, Filter> filters = new LinkedHashMap<>();
		filters.put("jwt", new JwtFilter());
		shiroFilterFactoryBean.setFilters(filters);

		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

		return shiroFilterFactoryBean;
	}

	/**
	 * 开启shiro注解通知器
	 * 
	 * @author gewx
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
			@Qualifier(value = "securityManager") SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}
}