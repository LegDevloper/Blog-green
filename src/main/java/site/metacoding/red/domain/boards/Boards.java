package site.metacoding.red.domain.boards;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter //Entity는 setter를 안만든다.(원칙)
public class Boards {
	private Integer id;
	private String title;
	private String content;
	private Integer usersId; //
	private Timestamp createdAt; // At 시분초 다 표현 , Dt 년원일
}
