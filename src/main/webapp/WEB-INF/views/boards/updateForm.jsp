<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">

	<form>
	<h2>${boardsID.usersId}</h2>
		<input type="hidden" id="id" value="">
		<div class="mb-3 mt-3">
			<input id="title" type="text" class="form-control" placeholder="Enter title" required="required">
		</div>
		<div class="mb-3">
			<textarea id="content" class="form-control" rows="8" required="required" >내용을 입력하시오</textarea>
		</div>
		<button id="btnUpdate" type="button" class="btn btn-primary">수정완료</button>
	</form>
</div>

<script>
	$("#btnUpdate").click(()=>{
		update();
	});
	function update(){
		$.ajax("/join", {
			type: "POST",
			dataType: "json",
			data: JSON.stringify(data),
			headers: {
				"Content-Type": "application/json"
			}
		}).done((res) => {
			if (res.code == 1) {
				location.href = "/loginForm";
			}

		});
	}
</script>
<%@ include file="../layout/footer.jsp"%>

