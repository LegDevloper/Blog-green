package site.metacoding.red.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.users.Users;
import site.metacoding.red.service.UsersService;
import site.metacoding.red.util.Script;
import site.metacoding.red.web.dto.request.users.JoinDto;
import site.metacoding.red.web.dto.request.users.LoginDto;
import site.metacoding.red.web.dto.request.users.UpdateDto;
import site.metacoding.red.web.dto.response.CMRespDto;

@RequiredArgsConstructor
@Controller
public class UsersController {
	private final UsersService usersService;
	private final HttpSession session;
	
	//중복체크 컨트롤러
	@GetMapping("/users/usernameSameCheck")
	public @ResponseBody CMRespDto<Boolean> usernameSameCheck(String username){ //class응답하면 json
		boolean isSame = usersService.아이디중복확인(username); //true:중복
		return new CMRespDto<>(1,"통신성공",isSame);
	}
	@GetMapping("/joinForm")
	public String joinForm() {
		return "users/joinForm";
	}
	@PostMapping("/join")
	public @ResponseBody CMRespDto<?> join(@RequestBody JoinDto joinDto) {
		usersService.회원가입(joinDto);
		return new CMRespDto<>(1,"회원가입성공",null);
	}
	
	@GetMapping("/loginForm")
	public String loginForm() { //쿠키(cookie)
		return "users/loginForm";
	}
	@PostMapping("/login")
	public @ResponseBody String login(LoginDto loginDto) {
		
		Users principal = usersService.로그인(loginDto);
		
		if(principal==null) { //로그인이 안됐다면
			return Script.back("아이디 혹은 비밀번호가 틀렸습니다");

		}
		session.setAttribute("principal", principal);
		return Script.href("/");
	}
	
	@GetMapping("/users/{id}")
	public String updateForm(@PathVariable Integer id, Model model) {
		Users usersPS = usersService.회원정보보기(id);
		model.addAttribute("users",usersPS);
		
		return "users/updateForm"; //updateForm만들기(회원탈퇴 버튼을 만든다)
	}
	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/loginForm";
	}
	
	//====================================================================
	//수정,삭제는 JavaScript로 
	@PutMapping("/users/{id}")
	public String update(@PathVariable Integer id,UpdateDto updateDto) {
		usersService.회원수정(id, updateDto);
		
		return "redirect:/users/"+id;
	}
	
	@DeleteMapping("/users/{id}")
	public @ResponseBody String delete(@PathVariable Integer id) {
		usersService.회원탈퇴(id);
		
		return Script.href("/loginForm", "회원탈퇴가 완료되었습니다");
	}
	
	
	
	
	
}
