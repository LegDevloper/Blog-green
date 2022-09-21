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

function koreanCheck() {
	let username = $("#username").val();
	let korRule = /^[가-힣]*$/;
	if (korRule.test(username)) {
		return true;
	} else {
		return false;
	}
}
function alphaCheck() {
	let username = String($("#username").val());
	let str = String(username);
	let isBigAlpha = false;
	for (let i = 0; i < str.length; i++) {
		if (str[i] >= 'A' && str[i] <= 'Z')
			isBigAlpha = true;
	}
	return isBigAlpha;
}
function passwordSameCheck() {
	let password = $("#password").val();
	let passwordSame = $("#passwordSameCheck").val();

	if (password == passwordSame)
		return true;
	else
		return false;
}
function emailTypeCheck() {
	let eamil = $("#email").val();
	let str = String(eamil);
	let isCheck = false;
	for (let i = 0; i < str.length; i++) {
		if (str[i] == '@')
			isCheck = true;
	}
	return isCheck;

}
function joinTempCheck() {
	let username = $("#username").val();
	let usernameStr = String(username);

	let password = $("#password").val();
	let passwordStr = String(password);

	let email = $("#email").val();
	let emailStr = String(email);

	let isEmpty = false;

	if (usernameStr == '') {
		isEmpty = true;
		return isEmpty;
	}

	if (passwordStr == '') {
		isEmpty = true;
		return isEmpty;
	}
	if (emailStr == '') {
		isEmpty = true;
		return isEmpty;
	}
	for (let i = 0; i < usernameStr.length; i++) {
		if (usernameStr[i] == ' ') {
			isEmpty = true;
			return isEmpty;
		}
	}
	for (let i = 0; i < passwordStr.length; i++) {
		if (passwordStr[i] == ' ') {
			isEmpty = true;
			return isEmpty;

		}
	}
	for (let i = 0; i < emailStr.length; i++) {
		if (emailStr[i] == ' ') {
			isEmpty = true;
			return isEmpty;
		}
	}

}

function loginTempCheck() {
	let username = $("#username").val();
	let usernameStr = String(username);

	let password = $("#password").val();
	let passwordStr = String(password);

	let isEmpty = false;

	if (usernameStr == '') {
		isEmpty = true;
		return isEmpty;
	}
	if (passwordStr == '') {
		isEmpty = true;
		return isEmpty;
	}
	for (let i = 0; i < usernameStr.length; i++) {
		if (usernameStr[i] == ' ') {
			isEmpty = true;
			return isEmpty;
		}
	}
	for (let i = 0; i < passwordStr.length; i++) {
		if (passwordStr[i] == ' ') {
			isEmpty = true;
			return isEmpty;
		}
	}
}

function updateTempCheck() {

	let password = $("#password").val();
	let passwordStr = String(password);

	let email = $("#email").val();
	let emailStr = String(email);

	let isEmpty = false;
	if (passwordStr == '') {
		isEmpty = true;
		return isEmpty;
	}
	if (emailStr == '') {
		isEmpty = true;
		return isEmpty;
	}

	for (let i = 0; i < passwordStr.length; i++) {

		if (passwordStr[i] == ' ') {
			isEmpty = true;
			return isEmpty;
		}
	}
	for (let i = 0; i < emailStr.length; i++) {
		if (emailStr[i] == ' ') {
			isEmpty = true;
			return isEmpty;
		}
	}
}

function join() {
	if (isUsernameSameCheck == false) {
		alert("아이디 중복체크를 진행해주세요");
		return;
	}
	//한글 유효성검사
	if (koreanCheck() == true) {
		alert("유저네임에 한글이 있으면 안됩니다!");
		return;
	}
	//대문자 1개받기 유효성 검사
	if (alphaCheck() == false) {
		alert("유저네임에 대문자 한개를 받아야 합니다.");
		return;
	}
	//비밀번호 확인 유효성 검사
	if (passwordSameCheck() == false) {
		alert("패스워드를 다시 확인해 주십시오");
		return;
	}
	//이메일 형식 확인 유효성 검사
	if (emailTypeCheck() == false) {
		alert("이메일 형식을 올바르게 입력해주세요");
		return;
	}
	//공백체크
	if (joinTempCheck() == true) {
		alert("공백을 입력받지 못합니다!");
		return;
	}
	let data = {
		username: $("#username").val(),
		password: $("#password").val(),
		email: $("#email").val()
	};

	$.ajax("/api/join", {
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

	if (loginTempCheck() == true) {
		alert("공백을 입력받지 못합니다!");
		return;
	}
	let data = {
		username: $("#username").val(),
		password: $("#password").val(),
		remember: $("#remember").prop("checked")
	};

	$.ajax("/api/login", {
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

	$.ajax(`/s/api/users/${id}`, {
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

	if (updateTempCheck() == true) {
		alert("공백을 입력받지 못합니다!");
		return;
	}

	let data = { //Object 생성
		password: $("#password").val(),
		email: $("#email").val(),
	};

	let id = $("#id").val();

	$.ajax(`/s/api/users/${id}`, {
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


