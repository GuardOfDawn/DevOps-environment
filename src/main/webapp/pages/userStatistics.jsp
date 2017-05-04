<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@page import="model.UserStat"%>
    <%@page import="model.IssueSeverity"%>
    <%@page import="model.IssueType"%>
    <%@page import="java.util.Map"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  <title>DevOps | User Statistics</title>
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
        <li class="treeview">
          <a href="#"><i class="fa fa-link"></i> <span>Project</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
            <li class="active"><a href="<%=path %>/AllProjectsServlet"><i class="fa fa-circle-o"></i> All projects</a></li>
            <li><a href="<%=path %>/createProject"><i class="fa fa-circle-o"></i> Create a project</a></li>
          </ul>
        </li>
        <li class="active treeview">
          <a href="#"><i class="fa fa-link"></i> <span>Statistics</span>
            <span class="pull-right-container">
              <i class="fa fa-angle-left pull-right"></i>
            </span>
          </a>
          <ul class="treeview-menu">
            <li><a href="<%=path %>/ProjectDetailServlet"><i class="fa fa-circle-o"></i> Team</a></li>
            <li class="active"><a href="<%=path %>/UserStatisticsServlet"><i class="fa fa-circle-o"></i> Individual</a></li>
          </ul>
        </li>
      </ul>
      <!-- /.sidebar-menu -->
    </section>
    <!-- /.sidebar -->
  </aside>
  
  <!-- Content Wrapper. Contains page content -->
  <div class="content-wrapper">
    <jsp:useBean id="userStat"
		class="model.UserStat"
		scope="page"></jsp:useBean>
	<%userStat = (UserStat)request.getAttribute("userStat");
	  pageContext.setAttribute("userStat",userStat);%>
    <!-- Content Header (Page header) -->
    <section class="content-header text-center">
      <h1>
        User Statistics
      </h1>
    </section>

    <!-- Main content -->
    <section class="content">
      <div class="box box-default">
        <div class="box-header with-border">
          <h3 class="box-title"><%=session.getAttribute("username") %>'s user statistics</h3>
        </div>
        <!-- /.box-header -->
        <div class="box-body">
          <div class="row">
	        <!-- Custom Tabs -->
	        <div class="nav-tabs-custom">
	          <ul class="nav nav-tabs">
	            <li class="active"><a href="#tab_sonar" data-toggle="tab">Sonar Statistics</a></li>
	          </ul>
	          <div class="tab-content">
	            <div class="tab-pane active" id="tab_sonar">
	              <div class="row">
	                <div class="col-md-6">
		              <div class="box box-warning">
			            <div class="box-header with-border">
			              <h3 class="box-title">Issues status</h3>
			            </div>
			            <!-- /.box-header -->
			            <div class="box-body">
			              <ul class="list-group list-group-unbordered">
			                <li class="list-group-item">
				              <b>Total</b> <a class="pull-right">
				              <%if(userStat.getTotalIssues()!=-1){ %>
				                <%=userStat.getTotalIssues() %>
				              <%}
				                else{%>
				                No data
				              <%} %>
				              </a>
				            </li>
				            <li class="list-group-item">
				              <b>Unresolved</b> <a class="pull-right">
				              <%if(userStat.getUnresolved()!=-1){ %>
				                <%=userStat.getUnresolved() %>
				              <%}
				                else{%>
				                No data
				              <%} %>
				              </a>
				            </li>
			              </ul>
			            </div>
			            <!-- /.box-body -->
		              </div>
		              <!-- /.box -->
		            </div>
		            <!-- /.col -->
	              </div>
	              <!-- /.row -->
	              <div class="row">
	                <div class="col-md-5">
	                  <div class="box box-warning">
			            <div class="box-header with-border">
			              <h3 class="box-title">Issues group by severity</h3>
			            </div>
			            <!-- /.box-header -->
			            <div class="box-body">
			              <canvas id="chartOfSeverity" style="height:250px"></canvas>
			            </div>
			            <div class="box-body text-center">
			              <b>Total Issues : 
			              <%if(userStat.getTotalIssues()!=-1){ %>
			                <%=userStat.getTotalIssues() %>
			              <%}
			                else{%>
			                No data
			              <%} %>
			              </b>
			            </div>
			            <!-- /.box-body -->
			          </div>
	                </div>
			        <!-- /.col -->
			        <div class="col-md-5">
	                  <div class="box box-warning">
			            <div class="box-header with-border">
			              <h3 class="box-title">Issues group by type</h3>
			            </div>
			            <!-- /.box-header -->
			            <div class="box-body">
			              <br>
			              <canvas id="chartOfType" style="height:250px"></canvas>
			            </div>
			            <div class="box-body text-center">
			              <b>Total Issues : 
			              <%if(userStat.getTotalIssues()!=-1){ %>
			                <%=userStat.getTotalIssues() %>
			              <%}
			                else{%>
			                No data
			              <%} %>
			              </b>
			            </div>
			            <!-- /.box-body -->
			          </div>
	                </div>
			        <!-- /.col -->
	              </div>
	              <!-- /.row -->
	              <div class="row">
	                <div class="col-md-7">
	                  <div class="box box-warning">
		                <div class="box-header with-border">
		                  <h3 class="box-title">Issues group by project</h3>
		                </div>
		                <!-- /.box-header -->
		                <div class="box-body">
		                  <canvas id="chartOfProject" style="height:230px"></canvas>
		                </div>
		                <!-- /.box-body -->
		              </div>
	                </div>
		            <!-- /.col -->
	              </div>
	              <!-- /.row -->
	            </div>
	            <!-- /.tab-pane -->
	          </div>
	          <!-- /.tab-content -->
		    </div>
		    <!-- nav-tabs-custom -->
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
<!-- ChartJS 1.0.1 -->
<script src="<%=path %>/plugins/chartjs/Chart.min.js"></script>
<!-- SlimScroll -->
<script src="<%=path %>/plugins/slimScroll/jquery.slimscroll.min.js"></script>
<!-- FastClick -->
<script src="<%=path %>/plugins/fastclick/fastclick.js"></script>
<!-- AdminLTE App -->
<script src="<%=path %>/dist/js/app.min.js"></script>
<!-- page script -->
<script>
  $(function () {
	    initSonarChart();
  });
  $(function(){
	  $('a[data-toggle="tab"]').on("shown.bs.tab",function(e){
		  var activeTab = $(e.target).text();
		  if(activeTab==="Sonar Statistics"){
			  initSonarChart();
		  }
	  });
  });
  function initSonarChart(){
	  var labelsOfChartSeverity = new Array();
	  var dataOfChartSeverity = new Array();
	  var colorOfChartSeverity = new Array();
	  <% 
  		IssueSeverity[] severities = IssueSeverity.values();
	    int[] severityData = userStat.getSeverityIssues();
	    String[] colors = new String[]{"#f56954","#f39c12","#3c8dbc","#00c0ef","#00a65a"};
	    int index = 0;
  		for(int i=0;i<severities.length;i++){
  			if(severityData[i]!=-1){
  			%>
  				labelsOfChartSeverity[<%=index%>]='<%=String.valueOf(severities[i])%>';
  				dataOfChartSeverity[<%=index%>]='<%=severityData[i]%>';
  				colorOfChartSeverity[<%=index%>]='<%=colors[i]%>';
  			<%
  			index++;
  			}
  		}
  	  %>
  	  //-------------
	  //- DOUGHNUT CHART -
	  //-------------
	  // Get context with jQuery - using jQuery's .get() method.
	  var chartOfSeverityCanvas = $("#chartOfSeverity").get(0).getContext("2d");
	  var chartOfSeverity = new Chart(chartOfSeverityCanvas);
	  var chartOfSeverityData = new Array();
	  for(var i=0;i<labelsOfChartSeverity.length;i++){
		  chartOfSeverityData[i] = {
			      value: dataOfChartSeverity[i],
			      color: colorOfChartSeverity[i],
			      highlight: colorOfChartSeverity[i],
			      label: labelsOfChartSeverity[i]
			    };
	  }
	  var chartOfSeverityOptions = {
	      //Boolean - Whether we should show a stroke on each segment
	      segmentShowStroke: true,
	      //String - The colour of each segment stroke
	      segmentStrokeColor: "#fff",
	      //Number - The width of each segment stroke
	      segmentStrokeWidth: 2,
	      //Number - The percentage of the chart that we cut out of the middle
	      percentageInnerCutout: 50, // This is 0 for Pie charts
	      //Number - Amount of animation steps
	      animationSteps: 100,
	      //String - Animation easing effect
	      animationEasing: "easeOutBounce",
	      //Boolean - Whether we animate the rotation of the Doughnut
	      animateRotate: true,
	      //Boolean - Whether we animate scaling the Doughnut from the centre
	      animateScale: false,
	      //Boolean - whether to make the chart responsive to window resizing
	      responsive: true,
	      // Boolean - whether to maintain the starting aspect ratio or not when responsive, if set to false, will take up entire container
	      maintainAspectRatio: true,
	  };
	  //Create pie or douhnut chart
	  // You can switch between pie and douhnut using the method below.
	  chartOfSeverity.Doughnut(chartOfSeverityData, chartOfSeverityOptions);
	  
	  var labelsOfChartType = new Array();
	  var dataOfChartType = new Array();
	  var colorOfChartType = new Array();
	  <% 
		IssueType[] types = IssueType.values();
	    int[] typeData = userStat.getTypeIssues();
	    String[] colors2 = new String[]{"#f56954","#f39c12","#00a65a"};
	    index=0;
		for(int i=0;i<types.length;i++){
			if(typeData[i]!=-1){
			%>
				labelsOfChartType[<%=index%>]='<%=String.valueOf(types[i])%>';
				dataOfChartType[<%=index%>]='<%=typeData[i]%>';
				colorOfChartType[<%=index%>]='<%=colors2[i]%>';
			<%
			index++;
			}
		}
	  %>
	  //-------------
	  //- DOUGHNUT CHART -
	  //-------------
	  // Get context with jQuery - using jQuery's .get() method.
	  var chartOfTypeCanvas = $("#chartOfType").get(0).getContext("2d");
	  var chartOfType = new Chart(chartOfTypeCanvas);
	  var chartOfTypeData = new Array();
	  for(var i=0;i<labelsOfChartType.length;i++){
		  chartOfTypeData[i] = {
				    value: dataOfChartType[i],
				    color: colorOfChartType[i],
				    highlight: colorOfChartType[i],
				    label: labelsOfChartType[i]
				};
	  }
	  var chartOfTypeOptions = {
	      //Boolean - Whether we should show a stroke on each segment
	      segmentShowStroke: true,
	      //String - The colour of each segment stroke
	      segmentStrokeColor: "#fff",
	      //Number - The width of each segment stroke
	      segmentStrokeWidth: 2,
	      //Number - The percentage of the chart that we cut out of the middle
	      percentageInnerCutout: 50, // This is 0 for Pie charts
	      //Number - Amount of animation steps
	      animationSteps: 100,
	      //String - Animation easing effect
	      animationEasing: "easeOutBounce",
	      //Boolean - Whether we animate the rotation of the Doughnut
	      animateRotate: true,
	      //Boolean - Whether we animate scaling the Doughnut from the centre
	      animateScale: false,
	      //Boolean - whether to make the chart responsive to window resizing
	      responsive: true,
	      // Boolean - whether to maintain the starting aspect ratio or not when responsive, if set to false, will take up entire container
	      maintainAspectRatio: true,
	  };
	  //Create pie or douhnut chart
	  // You can switch between pie and douhnut using the method below.
	  chartOfType.Doughnut(chartOfTypeData, chartOfTypeOptions);
	  
	  
	  var labelsOfChartProject = new Array();
	  var dataOfChartProjectTotal = new Array();
	  var dataOfChartProjectMine = new Array();
	  
	  <%
	    Map<String,Integer[]> projectIssueMap = userStat.getProjectIssues();
	    index = 0;
	    for(Map.Entry<String,Integer[]> entry:projectIssueMap.entrySet()){
	    	%>
	    	labelsOfChartProject[<%=index%>]='<%=entry.getKey()%>';
	    	dataOfChartProjectTotal[<%=index%>]='<%=entry.getValue()[0]%>';
	    	dataOfChartProjectMine[<%=index%>]='<%=entry.getValue()[1]%>';
			<%
			index++;
	    }
	  %>
	    //-------------
	    //- BAR CHART -
	    //-------------
	    var chartOfProjectCanvas = $("#chartOfProject").get(0).getContext("2d");
	    var chartOfProject = new Chart(chartOfProjectCanvas);
	    var chartOfProjectData = {
	    	      labels: labelsOfChartProject,
	    	      datasets: [
	    	        {
	    	          label: "Total issues",
	    	          fillColor: "#3c8dbc",
	    	          strokeColor: "#3c8dbc",
	    	          pointColor: "#3c8dbc",
	    	          pointStrokeColor: "#c1c7d1",
	    	          pointHighlightFill: "#fff",
	    	          pointHighlightStroke: "rgba(220,220,220,1)",
	    	          data: dataOfChartProjectTotal
	    	        },
	    	        {
	    	          label: "My issues",
	    	          fillColor: "#f56954",
	    	          strokeColor: "#f56954",
	    	          pointColor: "#f56954",
	    	          pointStrokeColor: "#c1c7d1",
	    	          pointHighlightFill: "#fff",
	    	          pointHighlightStroke: "rgba(220,220,220,1)",
	    	          data: dataOfChartProjectMine
	    	        }
	    	      ]
	    	    };
	    var chartOfProjectOptions = {
	      //Boolean - Whether the scale should start at zero, or an order of magnitude down from the lowest value
	      scaleBeginAtZero: true,
	      //Boolean - Whether grid lines are shown across the chart
	      scaleShowGridLines: true,
	      //String - Colour of the grid lines
	      scaleGridLineColor: "rgba(0,0,0,.05)",
	      //Number - Width of the grid lines
	      scaleGridLineWidth: 1,
	      //Boolean - Whether to show horizontal lines (except X axis)
	      scaleShowHorizontalLines: true,
	      //Boolean - Whether to show vertical lines (except Y axis)
	      scaleShowVerticalLines: true,
	      //Boolean - If there is a stroke on each bar
	      barShowStroke: true,
	      //Number - Pixel width of the bar stroke
	      barStrokeWidth: 2,
	      //Number - Spacing between each of the X value sets
	      barValueSpacing: 50,
	      //Number - Spacing between data sets within X values
	      barDatasetSpacing: 20,
	      //Boolean - whether to make the chart responsive
	      responsive: true,
	      maintainAspectRatio: true
	    };
	    chartOfProjectOptions.datasetFill = false;
	    chartOfProject.Bar(chartOfProjectData, chartOfProjectOptions);
  }
</script>
</body>
</html>