package site.metacoding.red.domain.boards;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import site.metacoding.red.web.dto.response.boards.MainDto;
import site.metacoding.red.web.dto.response.boards.PagingDto;

public interface BoardsDao {
	public void insert(Boards boards);
	public List<MainDto> findAll(@Param("startNum") int startNum,@Param("keyword") String keyword, @Param("row") int row);
	public Boards findById(Integer id);
	public List<Boards> findByUsersId(Integer usersId);
	public void update(Boards boards);
	public void deleteById(Integer id);
	public void updateToNull(Integer usersId);
	public PagingDto paging(@Param("page") Integer page,@Param("keyword") String keyword, @Param("row") int row);
	
}
