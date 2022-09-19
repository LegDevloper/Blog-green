<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<br />
	<div class="d-flex justify-content-end">
		<div style="width: 300px">
			<form class="d-flex" method="get" action="/">
				<input class="form-control me-2" type="text" placeholder="Search" name="keyword">
				<button class="btn btn-primary" type="submit">Search</button>
			</form>
		</div>
	</div>



	<table class="table table-striped">
		<thead>
			<tr>
				<th>번호</th>
				<th>게시글제목</th>
				<th>작성자이름</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="boards" items="${pagingdto.mainDtos}">
				<tr>
					<td>${boards.id}</td>
					<td><a href="/boards/${boards.id}">${boards.title}</a></td>
					<td>${boards.username}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="d-flex justify-content-center">
		<ul class="pagination">
			<c:choose>
				<c:when test="${pagingdto.totalPage == 0}">
					<li class="page-item disabled"><a class="page-link" href="">Previous</a></li>
					<li class="page-item disabled"><a class="page-link" href="">Next</a></li>
				</c:when>
				<c:otherwise>
					<li class="page-item ${(pagingdto.first)?'disabled':''}"><a class="page-link" href="?page=${(pagingdto.currentPage)-1}&keyword=${pagingdto.keyword}">Previous</a></li>
					<c:forEach var="page" begin="${pagingdto.startPageNum}" end="${pagingdto.lastPageNum}" step="1">
						<li class="page-item ${(pagingdto.currentPage)==(page-1)?'active':''}"><a class="page-link" href="/?page=${page-1}&keyword=${pagingdto.keyword}">${page}</a></li>
					</c:forEach>
					<li class="page-item ${(pagingdto.last)?'disabled':''}"><a class="page-link" href="?page=${(pagingdto.currentPage)+1}&keyword=${pagingdto.keyword}">Next</a></li>
				</c:otherwise>
			</c:choose>

		</ul>
	</div>
</div>
<script src="/js/boards.js"></script>

<%@ include file="../layout/footer.jsp"%>

