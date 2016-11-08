package com.drink.interceptor;

import com.drink.cornerstone.constant.ConstantElement;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author kk
 *
 */
public class MyHandlerInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String uri=request.getRequestURI();
		// System.out.println("preHandle => "+uri);
		Subject subject = SecurityUtils.getSubject();
		if(!uri.contains("security/login")&&
				!uri.contains("security/adminLogin")&&
				!uri.contains("testCompanyController")&&
				!uri.contains("companyOnlineRegiste")&&
				!uri.contains("companyOnlineRegiste/findInformation")&&
				!uri.contains("mobile")&&!uri.contains("sendMailTwo")&&!uri.contains("testLogin")&&
				subject.getSession().getAttribute(ConstantElement._USERINFO)==null){
			return false;
		}
		return true;
	}
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		//System.out.println("postHandle => "+request.getRequestURI());
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		//System.out.println("afterCompletion => "+request.getRequestURI());
	}
}
