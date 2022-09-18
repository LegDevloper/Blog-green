package site.metacoding.red.web;

import java.util.List;

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
import site.metacoding.red.service.BoardsService;
import site.metacoding.red.web.dto.request.boards.UpdateDto;
import site.metacoding.red.web.dto.request.boards.WriteDto;
import site.metacoding.red.web.dto.response.CMRespDto;
import site.metacoding.red.web.dto.response.boards.PagingDto;

@RequiredArgsConstructor
@Controller
public class BoardsController {
	private final BoardsService boardsService;
	private final HttpSession session;
	
	//========delete
	@DeleteMapping("/boards/{id}")
	public @ResponseBody CMRespDto<?> deleteById(@PathVariable Integer id,Model model) {
		boardsService.게시글삭제하기(id);
		model.addAttribute("boards",id);
		return new CMRespDto<>(1,"삭제성공",null);
	}
	
	
	//=========update
	@GetMapping("/boards/updateForm/{id}")
	public String updateForm(@PathVariable Integer id, Model model) {
		model.addAttribute("boards",id);
		return "/boards/updateForm";
	}
	@PutMapping("boards/update/{id}")
	public @ResponseBody CMRespDto<?> update(@PathVariable Integer id, @RequestBody UpdateDto updateDto) {
		boardsService.게시글수정하기(id, updateDto);
		return new CMRespDto<>(1,"수정성공",null);
	}
	
	
	
	//==========write
	@GetMapping("/boards/writeForm")
	public String writeForm() {
		return "/boards/writeForm";
	}
	@PostMapping("/boards")
	public @ResponseBody CMRespDto<?> write(@RequestBody WriteDto writeDto) {
		Users principal = (Users)session.getAttribute("principal");
		boardsService.게시글쓰기(writeDto, principal);
		return new CMRespDto<>(1,"글쓰기완료",null);
	}
	
	
	//==========read
	@GetMapping({ "/", "/boards" })
	public String boardsList(Integer page, String keyword, Model model) {
		PagingDto pagingdto = boardsService.게시글목록보기(page, keyword);
		model.addAttribute("pagingdto", pagingdto);
		return "/boards/main";
	}
	@GetMapping("/boards/{id}")
	public String boardsDetail(@PathVariable Integer id, Model model) {
		model.addAttribute("boards", boardsService.게시글상세보기(id));
		return "/boards/detail";
	}

}
