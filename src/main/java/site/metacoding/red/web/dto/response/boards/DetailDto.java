package site.metacoding.red.web.dto.response.boards;



import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.metacoding.red.domain.boards.Boards;
import site.metacoding.red.web.dto.response.loves.LovesDto;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DetailDto {
	private Boards boards;
	private List<LovesDto> lovesDto;
	private Boolean isUser;
}
