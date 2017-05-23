<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>DevOps | Create Project</title>
<!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <%String path = request.getContextPath(); %>
  <!-- Bootstrap 3.3.6 -->
  <link rel="stylesheet" href="<%=path %>/bootstrap/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
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
                <a href="/jenkins" target="_blank" class="text-center">Jenkins <small>(link to Jenkins)</small></a>
                <a href="http://127.0.0.1:9000" target="_blank" class="text-center">SonarQube <small>(link to SonarQube)</small></a>
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
            <li class="active"><a href="<%=path %>/createProject"><i class="fa fa-circle-o"></i> Create a project</a></li>
          </ul>
        </li>
        <li class="treeview">
          <a href="#"><i class="fa fa-link"></i> <span>Statistics</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
            <li><a href="<%=path %>/ProjectDetailServlet"><i class="fa fa-circle-o"></i> Team</a></li>
            <li><a href="<%=path %>/UserStatisticsServlet"><i class="fa fa-circle-o"></i> Individual</a></li>
          </ul>
        </li>
      </ul>
      <!-- /.sidebar-menu -->
    </section>
    <!-- /.sidebar -->
  </aside>
  
  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <!-- Content Header (Page header) -->
    <section class="content-header text-center">
      <h1>
        	Create a project
        <small></small>
      </h1>
    </section>

    <!-- Main content -->
    <section class="content">
	  <div class="row">
	    <div class="col-md-8 col-md-push-2">
	      <div class="box box-primary">
          <!-- form start -->
	        <form action="<%=path %>/CreateProjectServlet" method="post" role="form">
	          <p class="text-danger">
			    <%if(request.getAttribute("createProjectRes")!=null){ %>
			    	<%=request.getAttribute("createProjectRes") %>
			    <%} %>
			  </p>
	          <div class="box-body">
	            <div class="form-group">
	              <label for="exampleInputEmail1">Project name</label>
	              <input type="text" class="form-control" id="projectname" name="projectname" placeholder="Enter project name" onkeyup="createProjectCheck()">
	            </div>
	            <div class="form-group">
	              <label for="exampleInputPassword1">Project key (for SonarQube)</label>
	              <input type="text" class="form-control" id="projectkey" name="projectkey" placeholder="Enter project key" onkeyup="createProjectCheck()">
	            </div>
	            <div class="form-group">
	              <label>Project repository</label><input type="hidden" id="repositoryChecked" value="unCheck">
	              <div class="input-group">
	              	<input type="text" class="form-control" id="projectRepository" name="projectRepository" placeholder="Enter project repository" onblur="repositoryCheck()">
	              	<span class="input-group-addon" id="repositoryCheckAddon"><i class="fa fa-circle-o"></i></span>
	              </div>
	            </div>
	            <div class="form-group">
	              <label>Project artifact</label><input type="hidden" id="artifactChecked" value="unCheck">
	              <div class="input-group">
	                <span class="input-group-addon">
                      <abbr title="Select it to enter artifact,or not select to add it later in the project view"><input type="checkbox" id="artifactAddon"></abbr>
                    </span>
	                <input type="text" class="form-control" id="projectArtifact" name="projectArtifact" placeholder="Enter project artifact" disabled onblur="artifactCheck()">
	                <span class="input-group-addon" id="artifactCheckAddon"><i class="fa fa-circle-o"></i></span>
	              </div>
	            </div>
	            <p id="input_info" class="text-danger"></p>
	          </div>
	          <!-- /.box-body -->
	
	          <div class="box-footer">
	            <button type="submit" id="createProjectButton" class="btn btn-primary" disabled>Create Project</button>
	          </div>
	        </form>
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
<!-- AdminLTE App -->
<script src="<%=path %>/dist/js/app.min.js"></script>
<!-- Slimscroll -->
<script src="<%=path %>/plugins/slimScroll/jquery.slimscroll.min.js"></script>
<!-- FastClick -->
<script src="<%=path %>/plugins/fastclick/fastclick.js"></script>
<script type="text/javascript">
function createProjectCheck(){
	var projectName = document.getElementById('projectname').value;
	var projectKey = document.getElementById('projectkey').value;
	var repositoryChecked = document.getElementById('repositoryChecked').value;
	var artifactChecked = document.getElementById('artifactChecked').value;
	if(projectName===''||projectKey===''||repositoryChecked!=='true'||($("#artifactAddon").is(':checked')&&artifactChecked!=='true')){
		if(projectName===''){
			document.getElementById('input_info').innerHTML = "project name should not be empty";
		}
		else if(projectKey===''){
			document.getElementById('input_info').innerHTML = "project key should not be empty";
		}
		else if(repositoryChecked!=='true'){
			if(repositoryChecked==='unCheck'){
				document.getElementById('input_info').innerHTML = "project repository should not be empty";
			}
			else if(repositoryChecked==='false'){
				document.getElementById('input_info').innerHTML = "project repository is not valid";
			}
		}
		else{
			if(artifactChecked==='unCheck'){
				document.getElementById('input_info').innerHTML = "project artifact should not be empty(or cancel the tick)";
			}
			else if(artifactChecked==='false'){
				document.getElementById('input_info').innerHTML = "project artifact is not valid";
			}
		}
		document.getElementById('createProjectButton').disabled = true;
	}
	else{
		document.getElementById('input_info').innerHTML = "";
		document.getElementById('createProjectButton').disabled = false;
	}
}
function repositoryCheck(){
	var repository = document.getElementById('projectRepository').value;
	var xmlhttp;
    if (window.XMLHttpRequest){
    	// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp=new XMLHttpRequest();
    }
    else{
    	// code for IE6, IE5
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }
    xmlhttp.onreadystatechange=function(){
    	if (xmlhttp.readyState==4 && xmlhttp.status==200){
      		var result=xmlhttp.responseText.replace(/(^\s*)|(\s*$)/g,"");
      		document.getElementById('repositoryChecked').value = result;
      		if(result==='true'){
      			document.getElementById('repositoryCheckAddon').innerHTML = '<i class="fa fa-check text-success"></i>';
      		}
      		else if(result==='false'){
      			document.getElementById('repositoryCheckAddon').innerHTML = '<i class="fa fa-remove text-danger"></i>';
      		}
      		createProjectCheck();
      	}
    }
    var url = '<%=path %>/UrlCheckServlet?checkUrl='+repository;
    xmlhttp.open("GET",url,true);
    xmlhttp.send();
}
$("#artifactAddon").change(function() {
	if($("#artifactAddon").is(':checked')){
		document.getElementById('projectArtifact').disabled = false;
	}
	else{
		document.getElementById('projectArtifact').value = "";
		document.getElementById('projectArtifact').disabled = true;
		document.getElementById('artifactChecked').value = 'unCheck';
	}
	createProjectCheck();
});
function artifactCheck(){
	var artifact = document.getElementById('projectArtifact').value;
	var xmlhttp;
    if (window.XMLHttpRequest){
    	// code for IE7+, Firefox, Chrome, Opera, Safari
        xmlhttp=new XMLHttpRequest();
    }
    else{
    	// code for IE6, IE5
        xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
    }
    var url = '<%=path %>/UrlCheckServlet';
    xmlhttp.open("POST",url,true);
    xmlhttp.onreadystatechange=function(){
    	if (xmlhttp.readyState==4 && xmlhttp.status==200){
      		var result=xmlhttp.responseText.replace(/(^\s*)|(\s*$)/g,"");
      		document.getElementById('artifactChecked').value = result;
      		if(result==='true'){
      			document.getElementById('artifactCheckAddon').innerHTML = '<i class="fa fa-check text-success"></i>';
      		}
      		else if(result==='false'){
      			document.getElementById('artifactCheckAddon').innerHTML = '<i class="fa fa-remove text-danger"></i>';
      		}
      		createProjectCheck();
      	}
    }
    xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
    xmlhttp.send('checkUrl='+artifact);
}
</script>
</body>
</html>