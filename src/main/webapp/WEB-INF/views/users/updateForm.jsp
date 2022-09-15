<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<br />

	<form>
		<input id="id" type="hidden" value="${users.id}">
		<div class="mb-3 mt-3">
			<input type="text" class="form-control" placeholder="Enter password" value="${users.username}" readonly="readonly">
		</div>
		<div class="mb-3">
			<input id="password" type="password" class="form-control" placeholder="Enter password" value="${users.password}">
		</div>
		<div class="mb-3">
			<input id="email" type="email" class="form-control" placeholder="Enter email" value="${users.email}">
		</div>
		<button id="btnUpdate" type="button" class="btn btn-primary">수정하기</button>
		<button id="btnDelete" type="button" class="btn btn-danger">회원탈퇴</button>
	</form>
</div>

<script>
	$("#btnUpdate").click(()=>{
		let data = { //Object 생성
				password:$("#password").val(),
				email:$("#email").val(),
		};
		
		let id = $("#id").val();
		
		$.ajax("/users/"+id,{
			type:"PUT",
			dataType:"json",
			data:JSON.stringify(data), //data는 js오브젝트기 때문에 json으로 변경후 전송
			headers:{
				"Content-Type" : "application/json; charset=utf-8"
			}
		}).done((res)=>{
			if(res.code==1){ //통신이 성공했으면
				alert("수정완료!");
				location.reload();
			}
			else{
				alert("수정실패");
			}
		});
	});
	$("#btnDelete").click(()=>{
	
		let id = $("#id").val();
		
		$.ajax("/users/"+id,{
			type:"DELETE",
			dataType:"json",
		}).done((res)=>{
			if(res.code==1){ 
				alert("회원탈퇴완료!");
				location.href="/";
			}
			else{
				alert("회원탈퇴실패");
			}
		});
	});
</script>

<%@ include file="../layout/footer.jsp"%>

