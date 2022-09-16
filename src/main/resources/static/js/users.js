/**
 * 
 */

//joinForm
let isUsernameSameCheck = false;

$("#btnJoin").click(() => {
	join();
});

$("#btnUsernameSameCheck").click(() => {
	checkUsername();
});

//loginForm
$("#btnLogin").click(() => {
	login();
});

//update
$("#btnUpdate").click(() => {
	update();
});

//delete
$("#btnDelete").click(() => {
	resign();
});


function join() {
	if (isUsernameSameCheck == false) {
		alert("아이디 중복체크를 진행해주세요");
		return;
	}

	let data = {
		username: $("#username").val(),
		password: $("#password").val(),
		email: $("#email").val()
	};

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
function checkUsername() {
	let username = $("#username").val();

	$.ajax(`/users/usernameSameCheck?username=${username}`, {
		type: "GET",
		dataType: "json",
		async: true
	}).done((res) => {
		console.log(res);
		if (res.code == 1) {

			if (res.data == false) {
				alert("사용가능한 아이디입니다.");
				isUsernameSameCheck = true;
			}
			else {
				alert("아이디가 중복되었습니다");
				isUsernameSameCheck = false;
				$("#username").val("");
			}
		}
	});
}
function login() {
	let data = {
		username: $("#username").val(),
		password: $("#password").val(),
	};

	$.ajax("/login", {
		type: "POST",
		dataType: "json",
		data: JSON.stringify(data), //data는 js오브젝트기 때문에 json으로 변경후 전송
		headers: {
			"Content-Type": "application/json; charset=utf-8"
		}
	}).done((res) => {
		if (res.code == 1) { //통신이 성공했으면
			location.href = "/";
		}
		else {
			alert("아이디 혹은 패스워드가 틀렸습니다.");
		}

	});
}

function resign() {
	let id = $("#id").val();

	$.ajax(`/users/${id}`, {
		type: "DELETE",
		dataType: "json",
	}).done((res) => {
		if (res.code == 1) {
			alert("회원탈퇴완료!");
			location.href = "/";
		}
		else {
			alert("회원탈퇴실패");
		}
	});
}

function update() {
	let data = { //Object 생성
		password: $("#password").val(),
		email: $("#email").val(),
	};

	let id = $("#id").val();

	$.ajax(`/users/${id}`, {
		type: "PUT",
		dataType: "json",
		data: JSON.stringify(data), //data는 js오브젝트기 때문에 json으로 변경후 전송
		headers: {
			"Content-Type": "application/json; charset=utf-8"
		}
	}).done((res) => {
		if (res.code == 1) { //통신이 성공했으면
			alert("수정완료!");
			location.reload();
		}
		else {
			alert("수정실패");
		}
	});
}