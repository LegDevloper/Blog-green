<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form>
		<div class="mb-3 mt-3">
			<input id="username" type="text" class="form-control" placeholder="Enter username">
		</div>
		<div class="mb-3">
			<input id="password" type="password" class="form-control" placeholder="Enter password">
		</div>
		<button id="btnLogin" type="button" class="btn btn-primary">로그인</button>
	</form>
</div>

<script>
	$("#btnLogin").click(()=>{
		let data = {
				username:$("#username").val(),
				password:$("#password").val(),
		};

		$.ajax("/login",{
			type:"POST",
			dataType:"json",
			data:JSON.stringify(data), //data는 js오브젝트기 때문에 json으로 변경후 전송
			headers:{
				"Content-Type" : "application/json; charset=utf-8"
			}
		}).done((res)=>{
			if(res.code==1){ //통신이 성공했으면
				location.href="/";
			}
			else{
				alert("아이디 혹은 패스워드가 틀렸습니다.");
			}
			
		});
	});

</script>
<%@ include file="../layout/footer.jsp"%>

