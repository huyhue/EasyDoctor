// For opening the note modal
function showDeclarationModal(declarationId, doctorId) {
	const declarationForm = document.getElementById("declaration-form");

	if (declarationId) {
		declarationForm.setAttribute("action", `/patients/declaration/${declarationId}/${doctorId}`);
	} else {
		declarationForm.setAttribute("action", `/patients/declaration/${doctorId}`);
	}
	$('#declarationModal').modal('show');
}

$(document).ready(function() {
	$('#loader').hide();
	$('#successImage').css('display', 'none');

	$("#profileImage").change(function() {
		var formData = new FormData($('#uploadForm')[0]);
		formData.append("file", $(this)[0].files[0]);
		$('#loader').show();
		$.ajax({
			type: 'POST',
			enctype: 'multipart/form-data',
			url: "/file/saveImageProfile",
			data: formData,
			processData: false,
			contentType: false,
			cache: false,
			success: function(data, statusText, xhr) {
				console.log(xhr.status);
				if (xhr.status == "200") {
					$.confirm({
						type: 'blue',
						title: '<i class="fa fa-check-circle" aria-hidden="true" style="color:blue;"></i> ' + 'Xác nhận!!',
						content: 'Upload hình ảnh thành công',
						buttons: {
							OK: function() {
								$('#loader').hide();
							}
						}
					});
				}
				if (xhr.status == "400") {

				}
			},
			error: function(e) {
				$('#loader').hide();
				$('#successImage').css('display', 'block');
				$("#successImage").css("text-color", "red");
				location.reload();
			}
		});
	});


	$("#certification").change(function() {
		var formData = new FormData(); 
		formData.append("file", $(this)[0].files[0]);
		$.ajax({
			type: 'POST',
			enctype: 'multipart/form-data',
			url: "/file/saveCertification",
			data: formData,
			processData: false,
			contentType: false,
			cache: false,
			success: function(data, statusText, xhr) {
				$.confirm({
					type: 'blue',
					title: '<i class="fa fa-check-circle" aria-hidden="true" style="color:blue;"></i> ' + 'Xác nhận!!',
					content: 'Upload chứng chỉ thành công',
					buttons: {
						OK: function() {
						}
					}
				});
			},
			error: function(e) {
				$.confirm({
					type: 'red',
					title: '<i class="fa fa-exclamation-triangle" aria-hidden="true" style="color:red;"></i> ' + 'Xác nhận!!',
					content: 'Upload chứng chỉ thất bại!',
					buttons: {
						OK: function() {
							location.reload();
						}
					}
				});
			}
		});
	});

	$("#btn-change-password").click(function() {
		var formPassword = $("#formPassword").val();
		if (dataValidation()) {
			var uniList = {};
			uniList.id = $("#id").val();
			uniList.currentPassword = $("#currentPassword").val();
			uniList.password = $("#password").val();
			$.ajax({
				type: "POST",
				contentType: "application/json",
				url: formPassword,
				data: JSON.stringify(uniList),
				timeout: 100000,
				async: true,
				dataType: 'json',
				success: function(data) {
					if (data.msgCode == '200') {
						$.confirm({
							type: 'green',
							title: '<i class="fas fa-check-circle" aria-hidden="true" style="color:#51D88A;"></i> ' + 'Thành công!!',
							content: 'Thay đổi mật khẩu thành công.',
							buttons: {
								ok: function() {
									resetText();
								}
							}
						});
					} else if (data.msgCode == "205") {
						$("#errorMsgCurrentPassword").text("Mật khẩu cũ không đúng!!");
						$("#currentPassword").css('border', '1px solid red');
						$("#currentPassword").focus();
					}
				},
				error: function(xhr, status, error) {
					console.log("Sorry,Something wrong.Please contact with IT Team");
				}
			});
		}
	});

	function dataValidation() {
		var status = true;

		if ($("#currentPassword").val() == "") {
			status = false;
			$("#errorMsgCurrentPassword").text("Không được để trống.");
			$("#currentPassword").css('border', '1px solid red');
			$("#currentPassword").focus();
			return status;
		}
		if ($("#currentPassword").val().length<6) {
			status = false;
			$("#errorMsgCurrentPassword").text("Mật khẩu phải hơn 6 ký tự.");
			$("#currentPassword").css('border', '1px solid red');
			$("#currentPassword").focus();
			return status;
		}
		if ($("#password").val() == "") {
			status = false;
			$("#errorMsgPassword").text("Không được để trống.");
			$("#password").css('border', '1px solid red');
			$("#password").focus();
			return status;
		}
		if ($("#password").val().length<6) {
			status = false;
			$("#errorMsgPassword").text("Mật khẩu phải hơn 6 ký tự.");
			$("#password").css('border', '1px solid red');
			$("#password").focus();
			return status;
		}
		if ($("#matchingPassword").val() == "") {
			status = false;
			$("#errorMsgMatchingPassword").text("Không được để trống.");
			$("#matchingPassword").css('border', '1px solid red');
			$("#matchingPassword").focus();
			return status;
		}
		if ($("#matchingPassword").val().length<6) {
			status = false;
			$("#errorMsgMatchingPassword").text("Mật khẩu phải hơn 6 ký tự.");
			$("#matchingPassword").css('border', '1px solid red');
			$("#matchingPassword").focus();
			return status;
		}
		if ($("#password").val() != $("#matchingPassword").val()) {
			status = false;
			$("#errorMsgMatchingPassword").text("Mật khẩu không trùng nhau.");
			$("#matchingPassword").css('border', '1px solid red');
			$("#matchingPassword").focus();
			return status;
		}
		return status;
	}

	function resetText() {
		$("#currentPassword").val('');
		$("#errorMsgCurrentPassword").text("");
		$("#currentPassword").css('border', '1px solid #CED4DA');

		$("#password").val('');
		$("#errorMsgPassword").text("");
		$("#password").css('border', '1px solid #CED4DA');

		$("#matchingPassword").val('');
		$("#errorMsgMatchingPassword").text("");
		$("#matchingPassword").css('border', '1px solid #CED4DA');
	}

});

