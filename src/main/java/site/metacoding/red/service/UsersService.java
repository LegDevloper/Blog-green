package site.metacoding.red.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.boards.Boards;
import site.metacoding.red.domain.boards.BoardsDao;
import site.metacoding.red.domain.users.Users;
import site.metacoding.red.domain.users.UsersDao;
import site.metacoding.red.web.dto.request.users.JoinDto;
import site.metacoding.red.web.dto.request.users.LoginDto;
import site.metacoding.red.web.dto.request.users.UpdateDto;

@RequiredArgsConstructor // @AutoWired도 사용가능(근데 비추)
@Service
public class UsersService { // Service에서는 권한,인증 체크 xx(Controller역할)

	private final UsersDao usersDao;
	private final BoardsDao boardsDao;

	public void 회원가입(JoinDto joinDto) { // username,password,email -> DTO로 받는다
		Users usersPS = joinDto.toEntity();
		
		usersDao.insert(usersPS);
	}

	public Users 로그인(LoginDto loginDto) { // username,password -> DTO로 받는다.
		Users usersPS = usersDao.findByUsername(loginDto.getUsername());
		// IF문 usersPS 와 dto.getPassword()비교
		if (usersPS.getPassword().equals(usersPS.getPassword()))
			return usersPS;
		else
			return null;

	}

	public void 회원수정(Integer id, UpdateDto updateDto) { // Integer id,dto(password,email) 을 받는다
		// 영속화(id로 select)
		Users usersPS = usersDao.findById(id);
	
		// 영속화 객체 변경
		usersPS.update(updateDto);
		// update실행
		usersDao.update(usersPS);
	}

	@Transactional(rollbackFor = RuntimeException.class) // 자동롤백
	public void 회원탈퇴(Integer id) {// Integer id(usersId)
		// 내부적으로 2개의 Trancsaction 이 실행되는데
		// 어노테이션으로 1개의 Trancsaction 으로 묶어줌
		
		usersDao.deleteById(id);
		
		usersDao.incrementInit(id);
		
		boardsDao.updateToNull(id);
		
	} 
	public boolean 아이디중복확인(String username) { //
		Users usersPS = usersDao.findByUsername(username);
		// if문사용 (있으면 true 없으면 false)
		if(usersPS!=null) return true; //중복안댐
		else return false; //중복
	}

	public Users 회원정보보기(Integer id) {
		return usersDao.findById(id);
	}
}
