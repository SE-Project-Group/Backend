<%@ page import="java.util.ArrayList"%>
<%@ page import="model.Client"%>
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
		ArrayList<Client> clientList = new ArrayList<Client>();
			if (request.getAttribute("clients") != null) {
		clientList = (ArrayList<Client>) request.getAttribute("clients");
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
					<li><a href="getTodayFeed"><i class="fa fa-table fa-fw"></i> Best Feeds</a></li>
					<li><a href="inputDate.jsp" class="active"><i class="fa fa-table fa-fw "></i> Modify Best Feeds</a></li>
					<li><a href="managerLogout">&nbsp&nbsp&nbsp&nbsp&nbsp&nbspLogout</a></li>	
				</ul>
			</div>
			<!-- /.sidebar-collapse -->
		</div>
		
		<!-- /.navbar-static-side --> </nav>

		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Please Input Date</h1>
				</div>
			</div>
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<form>
						<label>Date:</label> <input name="date" type="date" formaction="getBestFeed">&nbsp&nbsp&nbsp&nbsp
						<input class="btn btn-default" type="submit" formaction="getBestFeed" value="query">
					</form>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<!-- /.row -->
		</div>
		<!-- /#page-wrapper -->
	</div>
	<!-- /#wrapper -->


	<script src="<%=path%>/track/js/jquery.min.js"></script>
	<script src="<%=path%>/track/js/bootstrap.min.js"></script>
	<script src="<%=path%>/track/js/jquery.dataTables.min.js"></script>
	<script src="<%=path%>/track/js/dataTables.bootstrap.min.js"></script>
	<script src="<%=path%>/track/js/track2.js"></script>
	<script src="<%=path%>/track/js/bootbox.min.js"></script>

	<script>
		$(document).ready(function() {
			$('#dataTables').DataTable({
				responsive : true
			});
		});
	</script>

</body>

</html>

