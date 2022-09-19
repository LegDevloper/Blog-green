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

/** 글쓰기 기능 */
function save() {
	let data = {
		title: $("#title").val(),
		content: $("#content").val()
	};
	$.ajax("/boards", {
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
	$.ajax(`/boards/${id}`, {
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
	let keyword=$("#keyword").val();
	$.ajax(`/boards/${id}`, {
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