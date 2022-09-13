package site.metacoding.red.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.boards.BoardsDao;
import site.metacoding.red.service.BoardsService;
import site.metacoding.red.web.dto.request.boards.UpdateDto;
import site.metacoding.red.web.dto.request.boards.WriteDto;
import site.metacoding.red.web.dto.response.boards.PagingDto;

@RequiredArgsConstructor
@Controller
public class BoardsController {
	private final BoardsService boardsService;
	private final BoardsDao boardsDao;
	private final HttpSession session;
	
	@DeleteMapping("/boards/{id}")
	public @ResponseBody String deleteById(@PathVariable Integer id) {
		boardsService.게시글삭제하기(id);
		
		return "삭제되었습니다.";
	}
	
	
	@GetMapping("/boards/updateForm")
	public String updateForm() {
		return "/boards/updateForm";
	}
	@PutMapping("boards/{id}")
	public String update(@PathVariable Integer id, UpdateDto updateDto) {
		boardsService.게시글수정하기(id, updateDto);
		
		return "redirect:/boards/"+id;
	}
	//수정,삭제는 js로 
	@GetMapping("/boards/writeForm")
	public String writeForm() {
		return "/boards/writeForm";
	}
	@PostMapping("/boards/write")
	public String write(WriteDto writeDto) {
		boardsService.게시글쓰기(writeDto);
		
		return "redirect:/";
	}
	@GetMapping({"/","/boards"})
	public String boardsList(Integer page, String keyword, Model model) {
		PagingDto pagingdto = boardsService.게시글목록보기(page, keyword);
		model.addAttribute("pagingdto",pagingdto);
		return "/boards/main";
	}
	
	@GetMapping("/boards/{id}")
	public  String boardsDetail(@PathVariable Integer id, Model model) {
		
		model.addAttribute("boards",boardsService.게시글상세보기(id));
		return "/boards/detail";
	}
	

}
