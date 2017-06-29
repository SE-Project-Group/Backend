$(function() {

	$("#save").click(function(e) {
		var user_name = $("input[name='user_name']").val();
		var password = $("input[name='password']").val();
		var phone = $("input[name='phone']").val();
		var email = $("input[name='email']").val();
		var gender = $("input[name='gender']").val();
		var birthday = $("input[name='birthday']").val();
		//console.log(username, password,phone,email, role);

		var dataset = e.currentTarget.dataset;
		var user_ID = dataset.user_ID;

		if (user_ID != "") { // Edit
			jQuery.ajax({
				url : 'updateClient',
				processData : true,
				dataType : "text",
				data : {
					user_ID : user_ID,
					user_name : user_name,
					password : password,
					phone : phone,
					email : email,
					gender : gender,
					birthday :birthday
				},
				success : function(data) {
					console.log(user_ID);
					bootbox.alert({
						message : 'Modify Successfully! '
							+ 'PS: No change if foreign key does not exist!',
						callback : function() {
							location.reload();
						}
					});
				}
			});
		} else { // Add
			jQuery.ajax({
				url : 'addClient',
				processData : true,
				dataType : "text",
				data : {
					user_ID : user_ID,
					user_name : user_name,
					password : password,
					phone : phone,
					email : email,
					gender : gender,
					birthday :birthday
				},
				success : function(data) {
					bootbox.alert({
						message : 'Add Successfully! '
							+ 'PS: No change if foreign key does not exist!',
						callback : function() {
							location.reload();
						}
					});
				}
			})
		}

		$('#modal').modal('hide');
	});

	$(".delete").click(function(e) {
		bootbox.confirm({
			buttons : {
				confirm : {
					label : 'Delete'
				},
				cancel : {
					label : 'Cancel'
				}
			},
			message : 'Sure to delete?',
			callback : function(result) {
				if (result) {

					var dataset = e.currentTarget.dataset;
					var user_ID = dataset.user_ID;
					jQuery.ajax({
						url : 'deleteClient',
						processData : true,
						dataType : "text",
						data : {
							user_ID : user_ID
						},
						success : function(data) {
							console.log(user_ID);
							bootbox.alert({
								message : 'Delete Successfully! '
									+ 'PS: No change if foreign key does not exist!',
								callback : function() {
									location.reload();
								}
							});
						}
					});

				}
			}
		});
	});

	$("#add").click(function(e) {
		$('#modalTitle').html("Add");

		$("input[name='user_name']").val("");
		$("input[name='password']").val("");
		$("input[name='phone']").val("");
		$("input[name='email']").val("");
		$("input[name='gender']").val("");
		$("input[name='birthday']").val("");

		$("#save").attr("data-user_ID", "");
		$('#modal').modal('show');
	});

	$(".edit").click(function(e) {
		$('#modalTitle').html("Edit");
		var dataset = e.currentTarget.dataset;
		var user_ID = dataset.user_ID;
		console.log(user_ID);

		$("input[name='user_name']").val(dataset.user_name);
		$("input[name='password']").val(dataset.password);
		$("input[name='phone']").val(dataset.phone);
		$("input[name='email']").val(dataset.email);
		$("input[name='gender']").val(dataset.gender);
		$("input[name='birthday']").val(dataset.birthday);

		$("#save").attr("data-user_ID", dataset.user_ID);
		$('#modal').modal('show');
	});

});
