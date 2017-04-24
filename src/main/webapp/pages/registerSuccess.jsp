<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <title>DevOps | Registration Success</title>
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

  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>
<body class="hold-transition register-page">
<div class="register-box">
  <div class="register-logo">
    <a href="#"><b>DevOps</b>Environment</a>
  </div>

  <div class="register-box-body">
    <p class="login-box-msg">Register successfully</p>

    <form action="<%=path %>/AllProjectsServlet" method="post" class="form-horizontal">
      <div class="box-body">
        <div class="input-group">
          <span class="input-group-addon">User name</span>
          <input type="text" class="form-control" id="textUsername1" value='<%=request.getAttribute("username") %>' disabled>
        </div>
        <br>
        
        <div class="input-group">
          <input type="hidden" id="Password1" value="hide">
          <span class="input-group-addon">Password</span>
          <input type="password" class="form-control" id="textPassword1" value='<%=request.getAttribute("password") %>' disabled>
          <span class="glyphicon glyphicon-eye-open input-group-addon" onclick="passwordShow('Password1')"></span>
        </div>
        <br>
        
        <div class="input-group">
          <span class="input-group-addon">Jenkins</span>
          <input type="text" class="form-control" id="textUsername2" value='<%=request.getAttribute("username") %>' disabled>
        </div>
        <br>
        
        <div class="input-group">
          <input type="hidden" id="Password2" value="hide">
          <span class="input-group-addon">Password</span>
          <input type="password" class="form-control" id="textPassword2" value='<%=request.getAttribute("password") %>' disabled>
          <span class="glyphicon glyphicon-eye-open input-group-addon" onclick="passwordShow('Password2')"></span>
        </div>
        <br>
        
        <div class="input-group">
          <span class="input-group-addon">SonarQube</span>
          <input type="text" class="form-control" id="textUsername3" value='<%=request.getAttribute("username") %>' disabled>
        </div>
        <br>
        
        <div class="input-group">
          <input type="hidden" id="Password3" value="hide">
          <span class="input-group-addon">Password</span>
          <input type="password" class="form-control" id="textPassword3" value='<%=request.getAttribute("password") %>' disabled>
          <span class="glyphicon glyphicon-eye-open input-group-addon" onclick="passwordShow('Password3')"></span>
        </div>
        <br>
        
      </div>
      <!-- /.box-body -->
      <div class="box-footer">
        <button type="submit" class="btn btn-info pull-right">Start Using</button>
      </div>
      <!-- /.box-footer -->
    </form>
    
  </div>
  <!-- /.form-box -->
</div>
<!-- /.register-box -->

<!-- jQuery 2.2.3 -->
<script src="<%=path %>/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="<%=path %>/bootstrap/js/bootstrap.min.js"></script>
<script>
function passwordShow(elementId){
	var condition = document.getElementById(elementId).value;
	if(condition==="hide"){
		document.getElementById("text"+elementId).type = 'text';
		document.getElementById(elementId).value = "show";
	}
	else if(condition==="show"){
		document.getElementById("text"+elementId).type = 'password';
		document.getElementById(elementId).value = "hide";
	}
}
</script>
</body>
</html>