package site.metacoding.red.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.metacoding.red.domain.boards.Boards;
import site.metacoding.red.domain.boards.BoardsDao;
import site.metacoding.red.domain.loves.Loves;
import site.metacoding.red.domain.loves.LovesDao;
import site.metacoding.red.domain.users.Users;
import site.metacoding.red.web.dto.request.boards.UpdateDto;
import site.metacoding.red.web.dto.request.boards.WriteDto;
import site.metacoding.red.web.dto.response.boards.DetailDto;
import site.metacoding.red.web.dto.response.boards.MainDto;
import site.metacoding.red.web.dto.response.boards.PagingDto;
import site.metacoding.red.web.dto.response.loves.LovesDto;

@RequiredArgsConstructor
@Service
public class BoardsService {
	private final BoardsDao boardsDao;
	private final LovesDao lovesDao;

	public void 좋아요취소(Integer usersId, Integer boardsId) {
		lovesDao.deleteById(usersId, boardsId);
	}

	public void 좋아요(Loves loves) {
		lovesDao.insert(loves);
	}

	public PagingDto 게시글목록보기(Integer page, String keyword) {
		if (page == null) {
			page = 0;
		}

		int startNum = page * PagingDto.ROW;
		List<MainDto> boardsList = boardsDao.findAll(startNum, keyword, PagingDto.ROW);
		PagingDto pagingDto = boardsDao.paging(page, keyword, PagingDto.ROW);

		pagingDto.makeBlockInfo(keyword);
		pagingDto.setMainDtos(boardsList);

		return pagingDto;

	}

	public DetailDto 게시글상세보기(Integer id, Integer principalId) {
		Boards boardsPS = boardsDao.findById(id);
		List<LovesDto> lovesDtoList = boardsDao.findByBoardsId(id, principalId);
		Boolean isUser=false;
		if (lovesDtoList.isEmpty()) {
			LovesDto lovesDto=new LovesDto();
			lovesDto.setCount(0);
			lovesDto.setIsLoved(false);
			lovesDto.setUsersId(null);
	
			lovesDtoList.add(lovesDto);
		}
		for(int i=0;i<lovesDtoList.size();i++) {
			if(lovesDtoList.get(i).getUsersId()==principalId)
				isUser=true;
		}

		return new DetailDto(boardsPS, lovesDtoList,isUser);
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

	public void 게시글쓰기(WriteDto writeDto, Users principal) {
		boardsDao.insert(writeDto.toEntity(principal.getId()));
	}
}
