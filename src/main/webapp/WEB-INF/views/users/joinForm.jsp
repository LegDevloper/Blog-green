<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">

	<form>
		<div class="mb-3 mt-3">
			<input id="username" type="text" class="form-control" placeholder="Enter username">
			<button id="btnUsernameSameCheck" class="btn btn-warning" type="button">유저네임 중복체크</button>
		</div>
		<div class="mb-3">
			<input id="password" type="password" class="form-control" placeholder="Enter password">
		</div>
		<div class="mb-3">
			<input id="email" type="email" class="form-control" placeholder="Enter email">
		</div>
		<button id="btnJoin" type="button" class="btn btn-primary">회원가입</button>
	</form>
</div>

<script>
	
	let isUsernameSameCheck = false;
	
	//회원가입
	
	$("#btnJoin").click(()=>{
		if(isUsernameSameCheck==false){
			alert("아이디 중복체크를 진행해주세요");
			return;
		}
		//0. 통신 오브젝트 생성(post요청은 body가 있다.):json
		let data = {
				username:$("#username").val(),
				password:$("#password").val(),
				email:$("#email").val()
		};
		//1.Ajax통신으로 전송
		$.ajax("/join",{
			type:"POST",
			dataType:"json",
			data:JSON.stringify(data), //data는 js오브젝트기 때문에 json으로 변경후 전송
			headers:{
				"Content-Type" : "application/json"
			}
		}).done((res)=>{
			if(res.code==1){ //통신이 성공했으면
				location.href="/loginForm";
			}
			
		});
	});
	
	
	

	//Username 중복체크
	$("#btnUsernameSameCheck").click(()=>{
		//0. 통신 오브젝트 생성(Get요청은 body가 없다.):json
		//1. 사용자 입력값을 받고
		let username= $("#username").val();
		//2. Ajax 통신->$.ajax(주소,오브젝트).done(행위);
		$.ajax("/users/usernameSameCheck?username="+username,{
			type:"GET",
			dataType:"json", //디폴트값이 json이다.
			async: true //걍 무조건 비동기
		}).done((res)=>{
			console.log(res);
			if(res.code==1) {
				//alert("통신성공");
				if(res.data == false) {
					alert("사용가능한 아이디입니다.");
					isUsernameSameCheck=true;
				}
				else {
					alert("아이디가 중복되었습니다");
					isUsernameSameCheck=false;
					$("#username").val("");
				}
			}
		});
	});
	
	
</script>
<%@ include file="../layout/footer.jsp"%>

