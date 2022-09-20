<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<br /> <br /> <input type="hidden" value="${detailDto.boards.id}" id="id">
	<div class="d-flex justify-content-between">
		<h3>${detailDto.boards.title}</h3>
		<div>
			좋아요 수 : <span id="countLove">${detailDto.lovesDto.get(0).count}</span>
			<i id="iconLove" class='${detailDto.lovesDto.get(0).isLoved?"fa-solid ":"fa-regular "}fa-heart my_pointer my_red'></i>
		</div>
	</div>
	<br />
	<div>${detailDto.boards.content}</div>
	<hr />
	<div class="d-flex justify-content-end">
		<a href="/boards/${detailDto.boards.id}/updateForm" class="btn btn-warning">수정하러가기</a>
		<form>
			<button id="btnDelete" type="button" class="btn btn-danger">삭제</button>
		</form>
	</div>
	<br /> <input type="hidden" id="page" value="${sessionScope.referer.page}"> <input type="hidden" id="keyword" value="${sessionScope.referer.keyword}">
</div>
<script>

	$("#iconLove").click(()=>{
		let isLovedState=$("#iconLove").hasClass("fa-solid");
		if(isLovedState){
			deleteLove();
		}
		else{
			insertLove();
		}
	});
	
	function insertLove(){
		let id = $("#id").val();
			$.ajax("/boards/"+id+"/loves", {
				type: "POST",
				dataType: "json",
			}).done((res) => {
				if (res.code == 1) {
					renderLoves();
					let count = $("#countLove").text();
					$("#countLove").text(Number(count)+1);
				}else{
					alert("좋아요 실패했습니다!");
				}
			});
	}
	function deleteLove(){

		let id = $("#id").val();
		$.ajax("/boards/"+id+"/lovesCancle", {
			type: "POST",
			dataType: "json",
		}).done((res) => {
			if (res.code == 1) {
				renderCancelLoves();
				let count = $("#countLove").text();
				$("#countLove").text(Number(count)-1);
			}else{
				alert("좋아요 실패했습니다!");
			}
		});
		
	}
	
	function renderLoves(){
		$("#iconLove").removeClass("fa-regular");
		$("#iconLove").addClass("fa-solid");
		$("#iconLove").css("color","red");
	}
	function renderCancelLoves(){
		$("#iconLove").removeClass("fa-solid");
		$("#iconLove").addClass("fa-regular");
		$("#iconLove").css("color","black");
	}
	
	 
</script>

<script src="/js/boards.js"></script>

<%@ include file="../layout/footer.jsp"%>

