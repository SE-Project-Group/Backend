<%@ page import="java.util.ArrayList"%>
<%@ page import="model.Feed"%>
<%@ page import="java.sql.Date" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<title>Track</title>

<%
	String path = request.getContextPath();
%>
<link href="<%=path%>/track/css/bootstrap.min.css" rel="stylesheet">
<link href="<%=path%>/track/css/dataTables.bootstrap.css" rel="stylesheet">
<link href="<%=path%>/track/css/dataTables.responsive.css" rel="stylesheet">
<link href="<%=path%>/track/css/font-awesome.min.css" rel="stylesheet" type="text/css">
<link href="<%=path%>/track/css/track.css" rel="stylesheet">
</head>

<body>
	<%
		ArrayList<Feed> feedList = new ArrayList<Feed>();
		if (request.getAttribute("feeds") != null) {
			feedList = (ArrayList<Feed>) request.getAttribute("feeds");
		}
	%>
	<div id="wrapper">
		<!-- Navigation -->
		<nav class="navbar navbar-default navbar-static-top" role="navigation"
			style="margin-bottom: 0">

		<div class="navbar-header">
			<a class="navbar-brand" href="#">Track</a>
		</div>

		<div class="navbar-default sidebar" role="navigation">
			<div class="sidebar-nav navbar-collapse">
				<ul class="nav" id="side-menu">
					<li><a href="allClient"><i class="fa fa-user fa-fw"></i> Client Management</a></li>
					<li><a href="bestFeed" class="active"><i class="fa fa-table fa-fw"></i> Best Feeds</a></li>
					<li><a href="managerLogout">&nbsp&nbsp&nbsp&nbsp&nbsp&nbspLogout</a></li>	
				</ul>
			</div>
			<!-- /.sidebar-collapse -->
		</div>
		
		<!-- /.navbar-static-side --> </nav>

		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Best Feed</h1>
				</div>
			</div>
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<table class="table table-striped table-bordered table-hover"
									id="dataTables">
									<thead>
										<tr>
										    <th>Position</th>
											<th>Text</th>
											<th>Like Count</th>
											<th></th>
										</tr>
									</thead>
									<tbody>
										<%
											for (int i = 0; i < feedList.size(); i++) {
												Feed feed = feedList.get(i);
										%>
										<tr>
										    <td><%=feed.getPosition()%></td>
											<td><%=feed.getText()%></td>
											<td><%=feed.getLikeCount()%></td>
											<td>
												<button class="btn btn-default showPic" type="button"
													data-feedid="<%=feed.get_id()%>",
													data-count="<%=feed.getPicCount()%>">show picture
												</button>
												<button class="btn btn-default setBest" type="button"
													data-feedid="<%=feed.get_id()%>">set best feed
												</button>
											</td>
										</tr>
										<%
											}
										%>
									</tbody>
								</table>
							</div>
						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
		</div>
		<!-- /#page-wrapper -->
	</div>
	<!-- /#wrapper -->

	<div class="modal fade" id="modal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
					</button>
					<h4 class="modal-title" id="modalTitle"></h4>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-lg-12">
							<form role="form">
								<div class="form-group">
									<img id="picture" src="E:/book1.jpg" height="300px" length="350px">
								</div>
							
							</form>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" id="pre">Pre</button>
					<button type="button" class="btn btn-default" id="next">Next</button>
				</div>
			</div>
		</div>
	</div>

	<script src="<%=path%>/track/js/jquery.min.js"></script>
	<script src="<%=path%>/track/js/bootstrap.min.js"></script>
	<script src="<%=path%>/track/js/jquery.dataTables.min.js"></script>
	<script src="<%=path%>/track/js/dataTables.bootstrap.min.js"></script>
	<script src="<%=path%>/track/js/track2.js"></script>
	<script src="<%=path%>/track/js/bootbox.min.js"></script>
	<script src="<%=path%>/track/js/bestFeed.js"></script>

	<script>
		$(document).ready(function() {
			$('#dataTables').DataTable({
				responsive : true
			});
		});
	</script>

</body>

</html>
