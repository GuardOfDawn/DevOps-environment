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
    <p class="login-box-msg">注册成功</p>

    <form action="<%=path %>/homePage" method="post" class="form-horizontal">
      <div class="box-body">
        <div class="form-group">
          <label for="textUsername" class="col-sm-4 control-label">用户名</label>
          <div class="col-sm-8">
            <input type="text" class="form-control" id="textUsername" placeholder="zy" disabled>
          </div>
        </div>
        <div class="form-group">
          <label for="textPassword" class="col-sm-4 control-label">密码</label>
          <div class="col-sm-8">
            <input type="text" class="form-control" id="textPassword" placeholder="123456" disabled>
          </div>
        </div>
        <div class="form-group">
          <label for="textUsername2" class="col-sm-4 control-label">jenkins</label>
          <div class="col-sm-8">
            <input type="text" class="form-control" id="textUsername2" placeholder="zy" disabled>
          </div>
        </div>
        <div class="form-group">
          <label for="textPassword2" class="col-sm-4 control-label">密码</label>
          <div class="col-sm-8">
            <input type="text" class="form-control" id="textPassword2" placeholder="123456" disabled>
          </div>
        </div>
        <div class="form-group">
          <label for="textUsername3" class="col-sm-4 control-label">Sonar</label>
          <div class="col-sm-8">
            <input type="text" class="form-control" id="textUsername3" placeholder="zy" disabled>
          </div>
        </div>
        <div class="form-group">
          <label for="textPassword3" class="col-sm-4 control-label">密码</label>
          <div class="col-sm-8">
            <input type="text" class="form-control" id="textPassword3" placeholder="123456" disabled>
          </div>
        </div>
      </div>
      <!-- /.box-body -->
      <div class="box-footer">
        <button type="submit" class="btn btn-info pull-right">进入主页</button>
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
</body>
</html>