package site.metacoding.red.web.dto.response.loves;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class LovesDto {
	private Integer usersId;
	private Boolean isLoved;
	private Integer count;
	
	
}
