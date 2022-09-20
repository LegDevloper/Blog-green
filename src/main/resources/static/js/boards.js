/**
 * 
 */



$("#btnSave").click(() => {
	save();
});
$("#btnUpdate").click(() => {
	update();
});
$("#btnDelete").click(() => {
	del();
});


$("#iconLove").click(() => {
	let isLovedState = $("#iconLove").hasClass("fa-solid");
	if (isLovedState) {
		deleteLove();
	}
	else {
		insertLove();
	}
});

function insertLove() {
	let id = $("#id").val();
	$.ajax("/s/api/boards/" + id + "/loves", {
		type: "POST",
		dataType: "json",
	}).done((res) => {
		if (res.code == 1) {
			renderLoves();
			let count = $("#countLove").text();
			$("#countLove").text(Number(count) + 1);
		} else {
			alert(res.msg);
		}
	});
}
function deleteLove() {
	let id = $("#id").val();
	$.ajax("/s/api/boards/" + id + "/lovesCancle", {
		type: "DELETE",
		dataType: "json",
	}).done((res) => {
		if (res.code == 1) {
			renderCancelLoves();
			let count = $("#countLove").text();
			$("#countLove").text(Number(count) - 1);
		} else {
			alert(res.msg);
		}
	});

}

function renderLoves() {
	$("#iconLove").removeClass("fa-regular");
	$("#iconLove").addClass("fa-solid");
	$("#iconLove").css("color", "red");
}
function renderCancelLoves() {
	$("#iconLove").removeClass("fa-solid");
	$("#iconLove").addClass("fa-regular");
	$("#iconLove").css("color", "black");
}
/** 글쓰기 기능 */
function save() {
	let data = {
		title: $("#title").val(),
		content: $("#content").val()
	};
	$.ajax("/s/api/boards", {
		type: "POST",
		dataType: "json",
		data: JSON.stringify(data),
		headers: {
			"Content-Type": "application/json; charset=utf-8"
		}
	}).done((res) => {
		if (res.code == 1) {
			alert("글쓰기 완료!");
			location.href = "/";
		}
		else alert("글쓰기실패!")

	});
}
/**글 수정 기능 */
function update() {
	let id = $("#id").val();

	let data = {
		title: $("#title").val(),
		content: $("#content").val()
	}
	$.ajax(`/s/api/boards/${id}`, {
		type: "PUT",
		dataType: "json",
		data: JSON.stringify(data),
		headers: {
			"Content-Type": "application/json"
		}
	}).done((res) => {
		if (res.code == 1) {
			alert("게시글 수정 완료!");
			location.href = `/boards/${id}`;
		}

	});
}
/**글 삭제 기능 */
function del() {
	let id = $("#id").val();
	let page = $("#page").val();
	let keyword = $("#keyword").val();
	$.ajax(`/s/api/boards/${id}`, {
		type: "DELETE",
		dataType: "json"
	}).done((res) => {
		if (res.code == 1) {
			alert("삭제완료");
			location.href = `/?page=${page}&keyword=${keyword}`;
		} else {
			alert("글삭제 실패");
		}
	});
}