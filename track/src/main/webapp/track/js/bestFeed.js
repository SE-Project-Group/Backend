$(function() {
	$(".showPic").click(function(e) {
		$('#modalTitle').html("Picture");
		var dataset = e.currentTarget.dataset;
		var feed_id=dataset.feedid;
		var count=dataset.count;
		var img = document.getElementById("picture");
		
		jQuery.ajax({
			url : 'getPicture',
			processData : true,
			dataType : "text",
			data : {
				feed_id:feed_id,
				count:count
			},
			success : function(data) {
				bootbox.alert({
					message : data,
					callback : function() {
						location.reload();
					}
				});
			}
		});
		$("input[name='user_name']").val(dataset.user_name);
		$("input[name='password']").val(dataset.password);
		$("input[name='phone']").val(dataset.phone);
		$("input[name='email']").val(dataset.email);
		$("input[name='gender']").val(dataset.gender);
		$("input[name='birthday']").val(dataset.birthday);

		$("#save").attr("data-userid", dataset.userid);
		$('#modal').modal('show');
	});

});
