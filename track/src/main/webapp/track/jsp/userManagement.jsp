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
					<li><a href="allClient" class="active"><i class="fa fa-user fa-fw"></i> Client Management</a></li>
					<li><a href="bestFeed"><i class="fa fa-table fa-fw"></i> Best Feeds</a></li>
					<li><a href="managerLogout">&nbsp&nbsp&nbsp&nbsp&nbsp&nbspLogout</a></li>	
				</ul>
			</div>
			<!-- /.sidebar-collapse -->
		</div>
		
		<!-- /.navbar-static-side --> </nav>

		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Client</h1>
				</div>
			</div>
			<!-- /.row -->
			<div class="row">
				<div class="col-lg-12">
					<div class="panel panel-default">
						<div class="panel-heading">
							add client
							<button class="btn btn-default" type="button" id="add">
								<i class="fa fa-plus"></i>
							</button>
						</div>
						<!-- /.panel-heading -->
						<div class="panel-body">
							<div class="dataTable_wrapper">
								<table class="table table-striped table-bordered table-hover"
									id="dataTables">
									<thead>
										<tr>
										    <th>ID</th>
											<th>Username</th>
											<th>Password</th>
											<th>Phone</th>
											<th>Email</th>
											<th>Gender</th>
										    <th>Birthday</th>
											<th></th>
										</tr>
									</thead>
									<tbody>
										<%
											for (int i = 0; i < clientList.size(); i++) {
												Client client = clientList.get(i);
										%>
										<tr>
										    <td><%=client.getUserId()%></td>
											<td><%=client.getUserName()%></td>
											<td><%=client.getPassword()%></td>
											<td><%=client.getPhone()%></td>
											<td><%=client.getEmail()%></td>
											<td><%=client.getGender()%></td>
											<td><%=client.getBirthday()%> </td>
											<td>
												<button class="btn btn-default delete" type="button"
													data-userid="<%=client.getUserId()%>"
													>
													<i class="fa fa-trash"></i>
												</button>
												<button class="btn btn-default edit" type="button"
													data-userid="<%=client.getUserId()%>"
													data-user_name="<%=client.getUserName()%>"
													data-password="<%=client.getPassword()%>"
													data-phone="<%=client.getPhone()%>"
													data-email="<%=client.getEmail()%>"
													data-gender="<%=client.getGender()%>"
												    data-birthday="<%=client.getBirthday()%>">
													<i class="fa fa-edit"></i>
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
									<label>Username</label> <input class="form-control" name="user_name">
								</div>
								<div class="form-group">
									<label>Password</label> <input class="form-control"
										name="password">
								</div>
								<div class="form-group">
									<label>Phone</label> <input class="form-control"
										name="phone">
								</div>
								<div class="form-group">
									<label>Email</label> <input class="form-control"
										type="email" name="email">
								</div>
								<div class="form-group">
									<label>Gender</label> <input class="form-control" name="gender">
								</div>
								<div class="form-group">
									<label>Birthday</label> <input class="form-control" type="date" name="birthday">
								</div>
							</form>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary" id="save">Save</button>
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

	<script src="<%=path%>/track/js/client2.1.js"></script>

	<script>
		$(document).ready(function() {
			$('#dataTables').DataTable({
				responsive : true
			});
		});
	</script>

</body>

</html>

