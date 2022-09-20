package site.metacoding.red.handler;

import java.io.BufferedReader;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;

import site.metacoding.red.web.dto.response.CMRespDto;

public class TeacherStupid implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		BufferedReader br = request.getReader();
		String inputData = br.readLine();

		
		if (inputData.contains("바보")) {
			ObjectMapper om = new ObjectMapper();
			String s = om.writeValueAsString(inputData);
			response.setContentType("application/json; charset=utf-8");
			PrintWriter out = response.getWriter();
			CMRespDto<?> dto = new CMRespDto<>(-1, "욕하지마", null);
			out.println(s);
			return false;
		}

		return true;
	}
}
