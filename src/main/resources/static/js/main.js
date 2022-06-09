// For opening the note modal
function showDeclarationModal(declarationId, doctorId) {
        console.log(background);

    if(declarationId) {
        const declarationForm = document.getElementById("declaration-form");
        declarationForm.setAttribute("action", `/patients/declaration/${declarationId}/${doctorId}`);
    }

    /*$('#id').val(id ? id : '');
    $('#blood').val(blood ? blood : '');
    $('#background').val(background ? background : '');
    $('#medicine').val(medicine ? medicine : '');
    $('#symptom').val(symptom ? symptom : '');
    $('#notes').val(notes ? notes : '');*/
    $('#declarationModal').modal('show');
}

$(document).ready(function() {
	$('#loader').hide();
	$('#successImage').css('display', 'none');
	$("#profileImage").change(function() {
		var formData = new FormData($('#uploadForm')[0]);
		console.log("hello huyhue1");
		$('#loader').show();
		$.ajax({
			type: 'POST',
			enctype: 'multipart/form-data',
			url: "/image/saveImageProfile",
			data: formData,
			processData: false,
			contentType: false,
			cache: false,
			success: function(data, statusText, xhr) {
				console.log(xhr.status);
				if (xhr.status == "200") {
					$('#loader').hide();
					console.log("huy: "+data);
				}
				if (xhr.status == "400") {
					console.log("Fail: " + data);
				}
			},
			error: function(e) {
				console.log(e);
				$('#loader').hide();
				$('#successImage').css('display', 'block');
				$("#successImage").css("text-color", "red");
				location.reload();
			}
		});
	});
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
