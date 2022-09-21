package site.metacoding.red.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.users.Users;
import site.metacoding.red.handler.ex.MyApiException;
import site.metacoding.red.service.UsersService;
import site.metacoding.red.web.dto.request.users.JoinDto;
import site.metacoding.red.web.dto.request.users.LoginDto;
import site.metacoding.red.web.dto.request.users.UpdateDto;
import site.metacoding.red.web.dto.response.CMRespDto;

@RequiredArgsConstructor
@Controller
public class UsersController {
	private final UsersService usersService;
	private final HttpSession session;
	private final HttpServletResponse response;

	// 중복체크 컨트롤러
	@GetMapping("/users/usernameSameCheck")
	public @ResponseBody CMRespDto<Boolean> usernameSameCheck(String username) { // class응답하면 json
		boolean isSame = usersService.아이디중복확인(username); // true:중복
		return new CMRespDto<>(1, "통신성공", isSame);
	}

	// 회원가입 컨트롤러
	@GetMapping("/joinForm")
	public String joinForm() {
		return "users/joinForm";
	}

	@PostMapping("/api/join")
	public @ResponseBody CMRespDto<?> join(@RequestBody @Valid JoinDto joinDto, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			System.out.println("에러가 있습니다!");
			FieldError fe = bindingResult.getFieldError();

			throw new MyApiException(fe.getDefaultMessage());
		} else {
			System.out.println("에러가 없습니다!");
		}

		usersService.회원가입(joinDto);
		return new CMRespDto<>(1, "회원가입성공", null);
	}
	

	// 로그인 컨트롤러
	@GetMapping("/loginForm")
	public String loginForm(Model model, HttpServletRequest request) { // 쿠키(cookie)
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("username")) {
				model.addAttribute("username", cookie.getValue());
			}
		}
		return "users/loginForm";
	}

	// ====================login
	@PostMapping("/api/login")
	public @ResponseBody CMRespDto<?> login(@RequestBody LoginDto loginDto) {

		if (loginDto.isRemember()) {
			Cookie cookie = new Cookie("username", loginDto.getUsername());
			cookie.setMaxAge(60 * 60 * 24); // 쿠키가 저장되있는 시간
			response.addCookie(cookie);

		} else {
			Cookie cookie = new Cookie("usernmae", null);
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
		Users principal = usersService.로그인(loginDto);

		if (principal == null) { // 로그인이 안됐다면
			return new CMRespDto<>(-1, "로그인 실패", null);
		}
		session.setAttribute("principal", principal);
		return new CMRespDto<>(1, "로그인성공", null);
	}

	// =============logout
	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/loginForm";
	}

	// 인증필요(session이 있는지 확인)
	// 회원정보 보는 컨트롤러
	@GetMapping("/s/users/{id}")
	public String updateForm(@PathVariable Integer id, Model model) {

		Users usersPS = usersService.회원정보보기(id);
		model.addAttribute("users", usersPS);
		return "users/updateForm"; // updateForm만들기(회원탈퇴 버튼을 만든다)
	}

	// ==============update
	@PutMapping("/s/api/users/{id}")
	public @ResponseBody CMRespDto<?> update(@PathVariable Integer id, @RequestBody UpdateDto updateDto) {
		Users usersPS = usersService.회원수정(id, updateDto);
		session.setAttribute("principal", usersPS); // 세션동기화(덮어쓴다)

		return new CMRespDto<>(1, "회원수정성공", null);
	}

	// 인증필요
	// =================delete
	@DeleteMapping("/s/api/users/{id}")
	public @ResponseBody CMRespDto<?> delete(@PathVariable Integer id) {
		usersService.회원탈퇴(id);
		session.invalidate();
		return new CMRespDto<>(1, "탈퇴완료", null);
	}

}
