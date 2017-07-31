$(function() {
	$(".unset").click(function(e) {
		var dataset = e.currentTarget.dataset;
		var feed_id=dataset.feedid;
		jQuery.ajax({
			url : 'unsetBestFeed',
			processData : true,
			cache:false,
			data : {
				feedId:feed_id
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
	})
	
	$(".showPic").click(function(e) {
		$('#modalTitle').html("Picture");
		var dataset = e.currentTarget.dataset;
		var feed_id=dataset.feedid;
		var count=dataset.count;
		if(count==0){
			bootbox.alert({
				message : "This feed has no picture!"
			});
			return;
		}
		var img = document.getElementById("picture");
		
		jQuery.ajax({
			url : 'getPicture',
			processData : true,
			dataType : "text",
			cache:false,
			data : {
				feedId:feed_id,
				picCount:count
			},
			success : function(data) {
				var urls=data.split(',');
				img.src=urls[0];
				$('#pre').attr("data-urls", urls);
				$('#pre').attr("data-cur", 0);
				$('#next').attr("data-urls", urls);
				$('#next').attr("data-cur", 0);
				$('#modal').modal('show');
				
			}
		});
	});
	
	
	$("#pre").click(function(e){
		var dataset = e.currentTarget.dataset;
		var temp_urls=dataset.urls;
		var urls=temp_urls.split(',');
		var temp_cur=dataset.cur;
		var cur = parseInt(temp_cur);
		if(cur==0){
			bootbox.alert({
				message : "This is the first picture!"
			});
			return;
		}
		cur-=1;
		var img = document.getElementById("picture");
		img.src=urls[cur];
		$('#pre').attr("data-cur", cur);
		$('#next').attr("data-cur", cur);
	});
	
	$("#next").click(function(e){
		var dataset = e.currentTarget.dataset;
		var temp_urls=dataset.urls;
		var urls=temp_urls.split(',');
		var temp_cur=dataset.cur;
		var cur = parseInt(temp_cur);
		if(cur==urls.length-1){
			bootbox.alert({
				message : "No more pictures!"
			});
			return;
		}
		cur+=1;
		var img = document.getElementById("picture");
		img.src=urls[cur];
		$('#pre').attr("data-cur", cur);
		$('#next').attr("data-cur", cur);
	});


});
