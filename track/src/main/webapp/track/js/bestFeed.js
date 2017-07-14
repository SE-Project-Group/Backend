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
				var urls=data.split(',');
				img.src=urls[0];
				$('#modal').modal('show');
			}
		});
	});

});
