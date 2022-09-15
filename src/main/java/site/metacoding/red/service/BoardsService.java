package site.metacoding.red.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.boards.Boards;
import site.metacoding.red.domain.boards.BoardsDao;
import site.metacoding.red.domain.users.Users;
import site.metacoding.red.web.dto.request.boards.UpdateDto;
import site.metacoding.red.web.dto.request.boards.WriteDto;
import site.metacoding.red.web.dto.response.boards.MainDto;
import site.metacoding.red.web.dto.response.boards.PagingDto;

@RequiredArgsConstructor
@Service
public class BoardsService {
	private final BoardsDao boardsDao;
	private final HttpSession session;

	public PagingDto 게시글목록보기(Integer page, String keyword) {
		if (page == null) {
			page = 0;
		}
		
		int startNum = page * 3;
		List<MainDto> boardsList = boardsDao.findAll(startNum, keyword);
		PagingDto pagingDto = boardsDao.paging(page, keyword);
	
		pagingDto.makeBlockInfo(keyword);
		pagingDto.setMainDtos(boardsList);

		return pagingDto;

	}

	public Boards 게시글상세보기(Integer id) {
		return boardsDao.findById(id);
	}

	public void 게시글수정하기(Integer id, UpdateDto updateDto) {
		Boards boardsPS = boardsDao.findById(id);
		boardsPS.updateBoards(updateDto);
		boardsDao.update(boardsPS);
	} // DTO추가

	public void 게시글삭제하기(Integer id) {
		boardsDao.deleteById(id); // 핵심로직
	}

	public void 게시글쓰기(WriteDto writeDto) {
		Users principal = (Users) session.getAttribute("principal");
		Integer usersId = principal.getId();

		boardsDao.insert(writeDto.toEntity(usersId));
	}
}
