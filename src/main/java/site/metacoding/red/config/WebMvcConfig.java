package site.metacoding.red.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import site.metacoding.red.handler.Loginintercepter;
import site.metacoding.red.handler.TeacherStupid;

//IOC컨테이너 등록
@Configuration
public class WebMvcConfig implements WebMvcConfigurer{
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new Loginintercepter())
		.addPathPatterns("/s/**"); // /s/*(s/뒤에 한개의 주소만)
		//.addPathPatterns("/admin/**").
		//excludePathPatterns("/s/boards/**"); ]
		
		registry.addInterceptor(new TeacherStupid())
		.addPathPatterns("/hello/**");
	}
}
