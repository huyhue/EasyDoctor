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
				alert("Upload thành công");
				if (xhr.status == "200") {
					$('#loader').hide();
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
		var formData = new FormData($('#certificationForm')[0]);
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
					content: 'OTP không phù hợp. Vui lòng nhập lại!',
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
		if ($("#password").val() == "") {
			status = false;
			$("#errorMsgPassword").text("Không được để trống.");
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


/*$(document).ready(function () {
	$('#loader').hide();
	$("#submit").on("click", function () {
		$("#submit").prop("disabled", true);
		var name = $("#name").val();
		var file = $("#image").val();
		var price = $("#price").val();
		var description = $("#description").val();
		var form = $("#form").serialize();
		var data = new FormData($("#form")[0]);
		data.append('name', name);
		data.append('price', price);
		data.append('description', description);
		//alert(data);
		$('#loader').show();
		if (name === "" || file === "" || price === "" || description === "") {
			$("#submit").prop("disabled", false);
			$('#loader').hide();
			$("#name").css("border-color", "red");
			$("#image").css("border-color", "red");
			$("#price").css("border-color", "red");
			$("#description").css("border-color", "red");
			$("#error_name").html("Please fill the required field.");
			$("#error_file").html("Please fill the required field.");
			$("#error_price").html("Please fill the required field.");
			$("#error_description").html("Please fill the required field.");
		} else {
			$("#name").css("border-color", "");
			$("#image").css("border-color", "");
			$("#price").css("border-color", "");
			$("#description").css("border-color", "");
			$('#error_name').css('opacity', 0);
			$('#error_file').css('opacity', 0);
			$('#error_price').css('opacity', 0);
			$('#error_description').css('opacity', 0);
			$.ajax({
				type: 'POST',
				enctype: 'multipart/form-data',
				data: data,
				url: "/image/saveImageDetails",
				processData: false,
				contentType: false,
				cache: false,
				success: function (data, statusText, xhr) {
					console.log(xhr.status);
					if (xhr.status == "200") {
						$('#loader').hide();
						$("#form")[0].reset();
						$('#success').css('display', 'block');
						$("#error").text("");
						$("#success").html("Product Inserted Succsessfully.");
						$('#success').delay(2000).fadeOut('slow');
					}
				},
				error: function (e) {
					$('#loader').hide();
					$('#error').css('display', 'block');
					$("#error").html("Oops! something went wrong.");
					$('#error').delay(5000).fadeOut('slow');
					location.reload();
				}
			});
		}
	});
});*/
