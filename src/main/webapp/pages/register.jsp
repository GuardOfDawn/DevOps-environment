<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <title>DevOps | Registration</title>
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
    <p class="login-box-msg">
    <%if(request.getAttribute("registerRes")!=null){ %>
    	<%=request.getAttribute("registerRes") %>
    <%} %>
    </p>

    <form action="<%=path %>/RegisterServlet" method="post">
      <div class="form-group has-feedback">
      <%if(request.getAttribute("username")!=null){ %>
        <input id="username" name="username" type="text" class="form-control" placeholder="<%=request.getAttribute("username") %>">
      <%}else{ %>
        <input id="username" name="username" type="text" class="form-control" placeholder="User name" onkeyup="limitUserInput('username')" onblur="registerCheck()">
      <%} %>
        <span class="glyphicon glyphicon-user form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input id="password" name="password" type="password" class="form-control" placeholder="Password" onkeyup="limitUserInput('password')" onblur="registerCheck()">
        <span class="glyphicon glyphicon-lock form-control-feedback"></span>
      </div>
      <div class="form-group has-feedback">
        <input id="password_check" type="password" class="form-control" placeholder="Retype password" onkeyup="limitUserInput('password_check')" onblur="registerCheck()">
        <span class="glyphicon glyphicon-log-in form-control-feedback"></span>
      </div>
      <div class="row">
        <div class="col-xs-8">
		  <p id="psw_check_info" class="text-danger"></p>
		  <a href="<%=path %>/login" class="text-center">I have a membership</a>
        </div>
        <!-- /.col -->
        <div class="col-xs-4">
          <button type="submit" id="registerButton" class="btn btn-primary btn-block btn-flat" disabled>Register</button>
        </div>
        <!-- /.col -->
      </div>
    </form>
    
  </div>
  <!-- /.form-box -->
</div>
<!-- /.register-box -->

<!-- jQuery 2.2.3 -->
<script src="<%=path %>/plugins/jQuery/jquery-2.2.3.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="<%=path %>/bootstrap/js/bootstrap.min.js"></script>
</body>
<script type="text/javascript">
function limitUserInput(elementId){
	var element_value = document.getElementById(elementId).value;
	document.getElementById(elementId).value = element_value.replace(/[^\w\.\/]/ig,'')
	if(element_value.length>40){
		document.getElementById(elementId).value = element_value.substr(0,39);
	}
}
function registerCheck(){
	var user_name = document.getElementById('username').value;
	var pwd = document.getElementById('password').value;
	var pwd_check = document.getElementById('password_check').value;
	if(user_name===''||pwd===''||pwd_check!==pwd){
		if(user_name===''){
			document.getElementById('psw_check_info').innerHTML = "username should not be empty";
		}
		else if(pwd===''){
			document.getElementById('psw_check_info').innerHTML = "password should not be empty";
		}
		else{
			document.getElementById('psw_check_info').innerHTML = "password not match";
		}
		document.getElementById('registerButton').disabled = true;
	}
	else{
		document.getElementById('psw_check_info').innerHTML = "";
		document.getElementById('registerButton').disabled = false;
	}
}
</script>
</html>