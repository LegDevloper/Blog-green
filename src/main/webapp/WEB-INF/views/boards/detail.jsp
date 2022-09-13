<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<br /> <br />

	<div>
		<h3>${boards.title}</h3>
	</div>
	<br/>
	<div>${boards.content}</div>
	<hr/>
	<div class="d-flex justify-content-end">

		<a href="/boards/updateForm" class="btn btn-warning">수정하러가기</a>

		<form>
			<button class="btn btn-danger">삭제</button>
		</form>
	</div>


	<br />
	

</div>

<%@ include file="../layout/footer.jsp"%>

