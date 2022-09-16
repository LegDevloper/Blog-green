/**
 * 
 */

$("#btnDelete").click(() => {
	let id = $("#id").val();

	$.ajax(`/boards/${id}`, {
		type: "DELETE",
		dataType: "json"
	}).done((res) => {
		if (res.code == 1) {
			alert("삭제완료!");
			location.href = "/boards";
		}
	});
});	