<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<br /> <br /> <input type="hidden" value="${detailDto.boards.id}" id="id">
	<div class="d-flex justify-content-between">

		<h3>${detailDto.boards.title}</h3>
		<div>
			좋아요 수 : <span id="countLove">${detailDto.lovesDto.get(0).count}</span> <i id="iconLove" class='${detailDto.isUser?"fa-solid ":"fa-regular "}fa-heart my_pointer my_red'></i>
		</div>
	</div>
	<br />
	<div>${detailDto.boards.content}</div>
	<hr />
	<c:if test="${!empty sessionScope.principal}">
		<div class="d-flex justify-content-end">
			<a href="/s/boards/${detailDto.boards.id}/updateForm" class="btn btn-warning">수정하러가기</a>
			<form>
				<button id="btnDelete" type="button" class="btn btn-danger">삭제</button>
			</form>
		</div>
	</c:if>

	<br /> <input type="hidden" id="page" value="${sessionScope.referer.page}"> <input type="hidden" id="keyword" value="${sessionScope.referer.keyword}">
</div>


<script src="/js/boards.js"></script>

<%@ include file="../layout/footer.jsp"%>

