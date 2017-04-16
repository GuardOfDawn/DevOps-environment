<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@page import="model.Project"%>
    <%@page import="java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <title>DevOps | Project detail</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <%String path = request.getContextPath(); %>
  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="<%=path %>/bootstrap/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
  <!-- DataTables -->
  <link rel="stylesheet" href="<%=path %>/plugins/datatables/dataTables.bootstrap.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="<%=path %>/dist/css/AdminLTE.min.css">
  <!-- AdminLTE Skins. We have chosen the skin-blue for this starter
        page. However, you can choose any other skin. Make sure you
        apply the skin class to the body tag so the changes take effect.
  -->
  <link rel="stylesheet" href="<%=path %>/dist/css/skins/skin-blue.min.css">

  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
  <header class="main-header">
  
    <!-- Logo -->
    <a href="#" class="logo">
      <!-- mini logo for sidebar mini 50x50 pixels -->
      <span class="logo-mini"><b>D</b>OE</span>
      <!-- logo for regular state and mobile devices -->
      <span class="logo-lg"><b>DevOps</b>Environment</span>
    </a>
  
    <!-- Header Navbar -->
    <nav class="navbar navbar-static-top" role="navigation">
      <!-- Sidebar toggle button-->
      <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
        <span class="sr-only">Toggle navigation</span>
      </a>
      
      <!-- Navbar Right Menu -->
      <div class="navbar-custom-menu">
        <ul class="nav navbar-nav">
          <li class="dropdown user user-menu">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
              <span>Welcome </span>
              <span><b><%=session.getAttribute("username") %></b></span>
              <span>!</span>
            </a>
            <ul class="dropdown-menu">
              <li class="footer">
                <a href="/jenkins" class="text-center">Jenkins <small>(link to Jenkins)</small></a>
                <a href="http://127.0.0.1:9000" class="text-center">SonarQube <small>(link to SonarQube)</small></a>
              </li>
            </ul>
          </li>
          <li>
            <a href="<%=path %>/LogoutServlet" >Sign out</a>
          </li>
        </ul>
      </div>
    </nav>
  </header>

  <!-- Left side column. contains the logo and sidebar -->
  <aside class="main-sidebar">

    <!-- sidebar: style can be found in sidebar.less -->
    <section class="sidebar">

      <!-- Sidebar Menu -->
      <ul class="sidebar-menu">
        <li class="header">MAIN NAVIGATION</li>
        <!-- Optionally, you can add icons to the links -->
        <li class="active treeview">
          <a href="#"><i class="fa fa-link"></i> <span>Project</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
            <li><a href="<%=path %>/AllProjectsServlet"><i class="fa fa-circle-o"></i> All projects</a></li>
            <li><a href="<%=path %>/createProject"><i class="fa fa-circle-o"></i> Create a project</a></li>
          </ul>
        </li>
        <li class="treeview">
          <a href="#"><i class="fa fa-link"></i> <span>Statistics</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
            <li><a href="#"><i class="fa fa-circle-o"></i> Team</a></li>
            <li><a href="#"><i class="fa fa-circle-o"></i> Individual</a></li>
          </ul>
        </li>
      </ul>
      <!-- /.sidebar-menu -->
    </section>
    <!-- /.sidebar -->
  </aside>
  
  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <jsp:useBean id="project"
		class="model.Project"
		scope="page"></jsp:useBean>
        <%project = (Project)request.getAttribute("project");
        pageContext.setAttribute("project",project);%>
    <!-- Content Header (Page header) -->
    <section class="content-header">
      <h1>
        Detail view of project
      </h1>
    </section>

    <!-- Main content -->
    <section class="content">
      <div class="box box-default">
        <div class="box-header with-border">
          <h3 class="box-title"><%=project.getProjectName() %></h3>
		  <%List<String> projectList = (List<String>) request.getAttribute("projectList");
            if(projectList!=null){%>
          <div class="box-tools pull-right form-group">
            <select id="projectSelection" class="form-control select2" onchange="projectChange()">
            <%for(int i=0;i<projectList.size();i++){ 
                if(project.getProjectName().equals(projectList.get(i))){%>
              <option selected="selected"><%=projectList.get(i) %></option>
              <%}else{ %>
              <option><%=projectList.get(i) %></option>
              <%}
			  }%>
            </select>
          </div>
          <%} %>
        </div>
        <!-- /.box-header -->
        <div class="box-body">
        
          <div class="row">
	        <div class="col-md-5 col-md-push-2">
	          <div class="box box-success box-solid">
	            <div class="box-header with-border">
		          <h3 class="box-title">Basic Info</h3>
		          <!--<div class="box-tools pull-right">
		            <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i>
		            </button>
		          </div>-->
		          <!-- /.box-tools -->
		        </div>
		        <!-- /.box-header -->
		        <div class="box-body">
		          <ul class="list-group list-group-unbordered">
	                <li class="list-group-item">
	                  <b>Build result</b> <a class="pull-right"><jsp:getProperty name="project" property="result" /></a>
	                </li>
	                <li class="list-group-item">
	                  <b>Build timeStamp</b> <a class="pull-right"><jsp:getProperty name="project" property="timeStamp" /></a>
	                </li>
	                <li class="list-group-item">
	                  <b>Build duration</b> <a class="pull-right"><jsp:getProperty name="project" property="duration" /></a>
	                </li>
	              </ul>
		        </div>
		        <!-- /.box-body -->
	          </div>
	          <!-- /.box -->
	        </div>
	        
	        <div class="col-md-3 col-md-push-2">
		      <div class="box box-warning box-solid">
		        <div class="box-header with-border">
		          <h3 class="box-title">Members</h3>
		          <div class="box-tools pull-right">
	                <%if(project.isMember(String.valueOf(session.getAttribute("username")))){ %>
	                <button type="button" class="btn btn-box-tool" onclick="quitProject('<jsp:getProperty name="project" property="projectName" />')">
	                  <b>Quit</b>
		            </button>
		            <%}else{ %>
		            <button type="button" class="btn btn-box-tool" onclick="joinProject('<jsp:getProperty name="project" property="projectName" />')">
		              <b>Join</b>
		            </button>
		              <%} %>
		          </div>
		          <!-- /.box-tools -->
		        </div>
		        <!-- /.box-header -->
		        <%if(project.getMembers().size()==0){ %>
		          <div class="box-body">There is no member.</div>
		        <%}else{
		          for(int i=0;i<project.getMembers().size();i++){ %>
		        <div class="box-body">
		          <%if(session.getAttribute("username").equals(project.getMembers().get(i))){ %>
		          <%=project.getMembers().get(i) %><a class="pull-right">you</a>
		          <%}else{ %>
		          <%=project.getMembers().get(i) %>
		          <%} %>
		        </div>
		        <%}
		        }%>
		        <!-- /.box-body -->
		      </div>
		      <!-- /.box -->
	        </div>
	        
	      </div>
        
        
        </div>
      </div>
      
    </section>
    <!-- /.content -->
  </div>
  <!-- /.content-wrapper -->
</div>
<!-- ./wrapper -->

<!-- REQUIRED JS SCRIPTS -->

<!-- jQuery 2.2.3 -->
<script src="<%=path %>/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="<%=path %>/bootstrap/js/bootstrap.min.js"></script>
<!-- SlimScroll -->
<script src="<%=path %>/plugins/slimScroll/jquery.slimscroll.min.js"></script>
<!-- FastClick -->
<script src="<%=path %>/plugins/fastclick/fastclick.js"></script>
<!-- AdminLTE App -->
<script src="<%=path %>/dist/js/app.min.js"></script>
<!-- Slimscroll -->
<script src="<%=path %>/plugins/slimScroll/jquery.slimscroll.min.js"></script>
<!-- FastClick -->
<script src="<%=path %>/plugins/fastclick/fastclick.js"></script>
<!-- page script -->
<script>
  function joinProject(project){
	  var page = "ProjectDetailServlet";
	  window.location.href='<%=path%>/JoinProjectServlet?project='+project+'&page='+page;
  }
  function quitProject(project){
	  var page = "ProjectDetailServlet";
	  window.location.href='<%=path%>/QuitProjectServlet?project='+project+'&page='+page;
  }
  function projectChange(){
	  var myselect = document.getElementById("projectSelection");
	  var index = myselect.selectedIndex;
	  var projectSelected = myselect.options[index].text;
	  window.location.href='<%=path%>/ProjectDetailServlet?projectName='+projectSelected;
  }
</script>
</body>
</html>