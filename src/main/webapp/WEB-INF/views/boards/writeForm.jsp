<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form>
		<div class="mb-3 mt-3">
			<input id="title" type="text" class="form-control" placeholder="Enter title" required="required">
		</div>
		<div class="mb-3">
			<textarea id="content" class="form-control" rows="8" required="required"></textarea>
		</div>
		<button id="btnWrite" type="button" class="btn btn-primary">글쓰기완료</button>

	</form>
</div>

<script>

	$("#btnWrite").click(()=>{
		let data = {
				title:$("#title").val(),
				content:$("#content").val()
		};
		$.ajax("/boards/write",{
			type:"PUT",
			dataType:"json",
			data:JSON.stringify(data), //data는 js오브젝트기 때문에 json으로 변경후 전송
			headers:{
				"Content-Type" : "application/json; charset=utf-8"
			}
		}).done((res)=>{
			if(res.code==1){ //통신이 성공했으면
				alert("글쓰기 완료!");
				location.href="/";
			}
			else alert("글쓰기실패!")
			
		});
	});
</script>
<%@ include file="../layout/footer.jsp"%>

