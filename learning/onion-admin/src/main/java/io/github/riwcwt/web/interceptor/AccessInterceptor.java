package io.github.riwcwt.web.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class AccessInterceptor extends HandlerInterceptorAdapter {
	private static final Logger logger = LoggerFactory.getLogger(AccessInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = HandlerMethod.class.cast(handler);
			Method method = handlerMethod.getMethod();

			logger.info(method.getDeclaringClass().getName());

			request.setAttribute("context", request.getContextPath());
		}

		return super.preHandle(request, response, handler);
	}

}
