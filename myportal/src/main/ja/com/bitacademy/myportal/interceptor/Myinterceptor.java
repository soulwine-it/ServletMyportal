package com.bitacademy.myportal.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
	
/*
 * Interceptor: 컨트롤러의 앞과 뒤에서 요청과 응답을 가로채서 처리
 * 필터와의 타이점은 필터가 DispatcherController 이전에서 요청/응답을 처리
 * 인터셉터는  DispatcherController 이후애 작동하기 때문에
 * Spring container 내부에 생성된 Bean객체를 활용할 수 있다
 */
public class Myinterceptor implements HandlerInterceptor {
	private static final Logger logger= LoggerFactory.getLogger(Myinterceptor.class);
	//prehandle:컨트롤러 실행 이전
	//만약에 return false면 연결되어 있는 handler로 요청이 전달되지 않는다
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		logger.debug("Myinterceptor.preHandle call");
		return true;
	}
	
	//posthanmle: 컨트롤러 수행 이우
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		logger.debug("Myinterceptor.postHandle call");

	}
	
	//aftercompletion:뷰 작어가지 완료된 시점에서 수행
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		logger.debug("Myinterceptor.afterCompletion call");

	}

}
