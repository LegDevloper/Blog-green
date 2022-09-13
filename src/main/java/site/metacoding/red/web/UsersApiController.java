package site.metacoding.red.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.users.Users;
import site.metacoding.red.domain.users.UsersDao;
import site.metacoding.red.service.UsersService;
import site.metacoding.red.web.dto.request.users.JoinDto;
import site.metacoding.red.web.dto.request.users.LoginDto;
import site.metacoding.red.web.dto.request.users.UpdateDto;

@RequiredArgsConstructor
@RestController
public class UsersApiController {
	private final UsersService usersService;
	private final UsersDao usersDao;

	@GetMapping("/api/{id}")
	public Users getUsers(@PathVariable Integer id) {
		return usersDao.findById(id);
	}
	
	@PostMapping("api/login")
	public String  loginUsers(LoginDto loginDto) {
		Users usersPS = usersService.로그인(loginDto);

		if(usersPS!=null) {
			return "성공";
		}
		else return "실패";

	}
	@PostMapping("api/join")
	public String joinUsers(JoinDto joinDto) {
		usersService.회원가입(joinDto);
		
		return "회원가입완료?";
	}
	
	@PostMapping("api/{id}/update")
	public String updateUsers(@PathVariable Integer id, UpdateDto updateDto) {
		usersService.회원수정(id, updateDto);
		
		return "수정완료";
	}
	
	@GetMapping("api/vail")
	public String usernameVaildation(String username) {
		boolean isSame=usersService.아이디중복확인(username);
		if(isSame) return "중복";
		else return "사용";
	}
	
	@GetMapping("api/{id}/find")
	public Users findById(@PathVariable Integer id) {
		return usersService.회원정보보기(id);
	}
	
	@GetMapping("api/{id}/delete")
	public String deleteById(@PathVariable Integer id) {
		usersService.회원탈퇴(id);
		
		return "탈퇴완료";
	}
}
