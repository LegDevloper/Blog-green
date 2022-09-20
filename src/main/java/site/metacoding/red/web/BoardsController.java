package site.metacoding.red.web;

import java.util.HashMap;
import java.util.Map;

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
import site.metacoding.red.domain.boards.Boards;
import site.metacoding.red.domain.loves.Loves;
import site.metacoding.red.domain.users.Users;
import site.metacoding.red.service.BoardsService;
import site.metacoding.red.web.dto.request.boards.UpdateDto;
import site.metacoding.red.web.dto.request.boards.WriteDto;
import site.metacoding.red.web.dto.response.CMRespDto;
import site.metacoding.red.web.dto.response.boards.PagingDto;
import site.metacoding.red.web.dto.response.loves.LovesDto;

@RequiredArgsConstructor
@Controller
public class BoardsController {
	private final BoardsService boardsService;
	private final HttpSession session;

	// ========loves
	// 좋아요
	@PostMapping("/boards/{id}/loves") // REST API 주소매핑방식
	public @ResponseBody CMRespDto<?> insertLoves(@PathVariable Integer id) {
		Users principal = (Users) session.getAttribute("principal");
		Loves loves = new Loves(id, principal.getId());
		// Loves lovesPS = boardsService.좋아요(loves);
		boardsService.좋아요(loves);
		return new CMRespDto<>(1, "좋아요성공", null);
	}
	//좋아요 취소
	@PostMapping("/boards/{id}/lovesCancle") // REST API 주소매핑방식
	public @ResponseBody CMRespDto<?> deleteLoves(@PathVariable Integer id) {
		Users principal = (Users) session.getAttribute("principal");
		
		Integer usersId = principal.getId();
		boardsService.좋아요취소(usersId, id);
		return new CMRespDto<>(1, "좋아요취소 성공", null);
	}
	
	// ========delete
	@DeleteMapping("/boards/{id}")
	public @ResponseBody CMRespDto<?> deleteById(@PathVariable Integer id) {
		boardsService.게시글삭제하기(id);
		// model.addAttribute("boards",id);
		return new CMRespDto<>(1, "삭제성공", null);
	}

	// =========update
	@GetMapping("/boards/{id}/updateForm")
	public String updateForm(@PathVariable Integer id, Model model) {
		Boards boards = boardsService.게시글상세보기(id);
		model.addAttribute("boards", boards);
		return "/boards/updateForm";
	}

	@PutMapping("boards/{id}")
	public @ResponseBody CMRespDto<?> update(@PathVariable Integer id, @RequestBody UpdateDto updateDto) {
		boardsService.게시글수정하기(id, updateDto);
		return new CMRespDto<>(1, "수정성공", null);
	}

	// ==========write
	@GetMapping("/boards/writeForm")
	public String writeForm() {
		return "/boards/writeForm";
	}

	@PostMapping("/boards")
	public @ResponseBody CMRespDto<?> write(@RequestBody WriteDto writeDto) {
		Users principal = (Users) session.getAttribute("principal");
		boardsService.게시글쓰기(writeDto, principal);
		return new CMRespDto<>(1, "글쓰기완료", null);
	}

	// ==========read
	@GetMapping({ "/", "/boards" })
	public String boardsList(Integer page, String keyword, Model model) {
		PagingDto pagingdto = boardsService.게시글목록보기(page, keyword);
		model.addAttribute("pagingdto", pagingdto);

		Map<String, Object> referer = new HashMap<>();
		referer.put("page", pagingdto.getCurrentPage());
		referer.put("keyword", pagingdto.getKeyword());
		session.setAttribute("referer", referer);

		return "/boards/main";
	}

	@GetMapping("/boards/{id}")
	public String getBoardDetail(@PathVariable Integer id, Model model) {
		Users principal = (Users) session.getAttribute("principal");
		
		if (principal == null) {
			model.addAttribute("detailDto", boardsService.게시글상세보기(id, null));
		} else {
			model.addAttribute("detailDto", boardsService.게시글상세보기(id, principal.getId()));
		}

		return "boards/detail";
	}

}
