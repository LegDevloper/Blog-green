<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<br /> <br />
	<div class="d-flex justify-content-between" >
		<h3>${boards.title}</h3>
		<div>좋아요 수 : 10<i id="iconHeart" class="fa-regular fa-heart"></i></div>
		<!-- <div>좋아요 수 : 10<i class="fa-solid fa-heart"></i></div> -->
	</div>
	<br />
	<div>${boards.content}</div>
	<hr />
	<div class="d-flex justify-content-end">

		<input type="hidden" value="${boards.id}" id="id"> <a href="/boards/updateForm" class="btn btn-warning">수정하러가기</a>

		<form>
			<button id="btnDelete" type="button" class="btn btn-danger">삭제</button>
		</form>
	</div>
	<br />
</div>
<script>
	
	$("#iconHeart").click((event)=>{
		let check=$("#iconHeart").hasClass("fa-regular");
		
		if(check==true){
			$("#iconHeart").removeClass("fa-regular");
			$("#iconHeart").addClass("fa-solid");
			$("#iconHeart").css("color","red");
		}
		else{
			$("#iconHeart").removeClass("fa-solid");
			$("#iconHeart").addClass("fa-regular");
			$("#iconHeart").css("color","black");
		}

	});
</script>

<script src="/js/boards.js"></script>

<%@ include file="../layout/footer.jsp"%>

