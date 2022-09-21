package site.metacoding.red.handler;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;

import site.metacoding.red.domain.users.Users;
import site.metacoding.red.web.dto.response.CMRespDto;

public class Loginintercepter implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String uri = request.getRequestURI();
		HttpSession session = request.getSession();
		Users principal = (Users) session.getAttribute("principal");
		if (principal == null) {
			if (uri.contains("api")) {
				response.setContentType("application/json; charset=utf-8");
				PrintWriter out = response.getWriter();
				
				CMRespDto<?> dto = new CMRespDto<>(-1, "인증필요", null);
				ObjectMapper om = new ObjectMapper(); //object->json
				String json = om.writeValueAsString(dto);
				out.println(json);
			} else {
				response.sendRedirect("/loginForm");
			}
			return false;

		}

		System.out.println("==============로그인잘댐 실행==============");
		// return HandlerInterceptor.super.preHandle(request, response, handler);
		return true;
	}
}
