package site.metacoding.red.web.dto.request.boards;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UpdateDto {
	
	private String title;
	private String content;
}
