package io.github.riwcwt.token.interceptor;

import io.github.riwcwt.token.annotation.Function;
import io.github.riwcwt.token.util.SecurityUtil;
import io.jsonwebtoken.Claims;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by michael on 2017-06-09.
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            request.setAttribute("context", request.getContextPath());

            HandlerMethod method = HandlerMethod.class.cast(handler);

            Function function = method.getMethodAnnotation(Function.class);
            if (function == null) {
                return true;
            }

            Claims claims = SecurityUtil.decode(getToken(request.getCookies()));
            if (claims == null) {
                response.sendRedirect("/login");
            } else {
                //TODO 验证能否访问该功能
                request.setAttribute("id", claims.getId());
            }
        }
        return super.preHandle(request, response, handler);
    }

    private String getToken(Cookie[] cookies) {
        if (cookies == null || cookies.length < 1) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if ("token".equalsIgnoreCase(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

}
