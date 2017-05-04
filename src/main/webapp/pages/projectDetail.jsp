<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@page import="model.Project"%>
    <%@page import="model.BuildStatus"%>
    <%@page import="java.util.List" %>
    <%@page import="java.util.Map" %>
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
  <!-- chartjs tooltip -->
  <link rel="stylesheet" href="<%=path %>/plugins/chartjs/chartjs-tooltip.css">

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
            <li><a href="<%=path %>/AllProjectsServlet"><i class="fa fa-circle-o"></i> All projects</a></li>
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
            <li class="active"><a href="<%=path %>/ProjectDetailServlet"><i class="fa fa-circle-o"></i> Team</a></li>
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
          <div class="box-tools pull-right">
            <abbr title="select to switch view of project which you have joined">
	            <select id="projectSelection" class="form-control select2" onchange="projectChange()">
		          <%for(int i=0;i<projectList.size();i++){ 
		            if(project.getProjectName().equals(projectList.get(i))){%>
		          <option selected="selected"><%=projectList.get(i) %></option>
		          <%}else{ %>
		          <option><%=projectList.get(i) %></option>
		          <%}
		          }%>
		        </select>
	        </abbr>
          </div>
          <%} %>
        </div>
        <!-- /.box-header -->
        <div class="box-body">
        
          <div class="row">
          
	        <div class="col-md-9">
	          <!-- Custom Tabs -->
	          <div class="nav-tabs-custom">
	            <ul class="nav nav-tabs">
	              <li class="active"><a href="#tab_jenkins" data-toggle="tab">Jenkins Status</a></li>
	              <li><a href="#tab_sonar" data-toggle="tab">Sonar Status</a></li>
	            </ul>
	            <div class="tab-content">
	              <div class="tab-pane active" id="tab_jenkins">
	                <div class="row">
	                  <div class="col-md-6">
			            <div class="box box-warning">
				            <div class="box-header with-border">
				              <h3 class="box-title">Last build status</h3>
				            </div>
				            <!-- /.box-header -->
				            <div class="box-body">
				              <ul class="list-group list-group-unbordered">
				                <li class="list-group-item">
					              <b>Build result</b> <a class="pull-right"><%=project.getResult() %></a>
					            </li>
					            <li class="list-group-item">
					              <b>Timestamp</b> <a class="pull-right"><%=project.getTimeStamp() %></a>
					            </li>
					            <li class="list-group-item">
					              <b>Duration</b> <a class="pull-right"><%=project.getDuration() %></a>
					            </li>
				              </ul>
				            </div>
				            <!-- /.box-body -->
			            </div>
			            <!-- /.box -->
			          </div>
			          <!-- /.col -->
			          <div class="col-md-6">
			            <div class="box box-warning">
				          <div class="box-header with-border">
				            <h3 class="box-title">Build frequency</h3>
				          </div>
				          <!-- /.box-header -->
				          <div class="box-body">
				            <ul class="list-group list-group-unbordered">
				              <li class="list-group-item">
					            <b>Last five builds</b> <a class="pull-right"><%=project.getFrequency() %></a>
					          </li>
				            </ul>
				          </div>
				          <!-- /.box-body -->
			            </div>
			            <!-- /.box -->
			          </div>
			          <!-- /.col -->
			        </div>
			        <div class="row">
			        <!-- .row2 -->
			          <div class="col-md-5">
			            <div class="box box-warning">
				            <div class="box-header with-border">
				              <h3 class="box-title">Build success rate</h3>
				            </div>
				            <!-- /.box-header -->
				            <div class="box-body">
					          <%if(project.getSuccessRate()==-1){ %>
						      <div class="box-body text-center">
			                    <b>No data</b>
			              	  </div>
						      <%}
					            else{%>
				              <canvas id="pieChartOfJenkins" style="height:250px"></canvas>
				              <div id="chartjs-tooltip"></div>
				              <div class="box-body text-center">
			                    <b>Successful build rate : <%=String.format("%.2f", project.getSuccessRate()*100)%>%</b>
			              	  </div>
			          		  <%} %>
				            </div>
				            <!-- /.box-body -->
			            </div>
			          </div>
			          <!-- /.col -->
			          <div class="col-md-7">
			            <div class="box box-warning">
				            <div class="box-header with-border">
				              <h3 class="box-title">Build condition of last ten builds</h3>
				            </div>
				            <!-- /.box-header -->
				            <div class="box-body">
				              <%if(project.getLastTenBuilds().size()==0){ %>
				              <div class="box-body text-center">
			                    <b>No data</b>
			              	  </div>
				              <%}
				                else{%>
				              <canvas id="barChartOfJenkins" style="height:230px"></canvas>
				              <%} %>
				            </div>
				            <!-- /.box-body -->
			            </div>
			          </div>
			          <!-- /.col -->
			        </div>
			        <!-- /.row2 -->
	              </div>
	              <!-- /.tab-pane -->
	              <div class="tab-pane" id="tab_sonar">
	                <div class="row">
	                  <div class="col-md-6">
			            <div class="box box-warning">
				            <div class="box-header with-border">
				              <h3 class="box-title"><abbr>Last build status</abbr></h3>
				            </div>
				            <!-- /.box-header -->
				            <div class="box-body">
				              <ul class="list-group list-group-unbordered">
				                <li class="list-group-item">
					              <b>Quality gates</b> <a class="pull-right"><%=project.getQualityGates() %></a>
					            </li>
					            <li class="list-group-item">
					              <b>Analysis time</b> <a class="pull-right"><%=project.getAnalysisTime() %></a>
					            </li>
					            <li class="list-group-item">
					              <b>Lines</b> 
					                <%if(Double.parseDouble(project.getLines()[1])>=0){ %>
					              <a class="pull-right text-success"><%=project.getLines()[1]%></a>
					                <%}
					                  else{%>
					              <a class="pull-right text-danger"><%=project.getLines()[1]%></a>
					                <%} %>
					              <a class="pull-right"><%=project.getLines()[0]%></a>
					            </li>
					            <li class="list-group-item">
					              <b>Complexity</b> 
					                <%if(Double.parseDouble(project.getComplexity()[1])>=0){ %>
					              <a class="pull-right text-success"><%=project.getComplexity()[1]%></a>
					                <%}
					                  else{%>
					              <a class="pull-right text-danger"><%=project.getComplexity()[1]%></a>
					                <%} %>
					              <a class="pull-right"><%=project.getComplexity()[0]%></a>
					            </li>
					            <li class="list-group-item">
					              <b>Sqale index</b> 
					                <%if(Double.parseDouble(project.getSqaleIndex()[1])>=0){ %>
					              <a class="pull-right text-success"><%=project.getSqaleIndex()[1]%></a>
					                <%}
					                  else{%>
					              <a class="pull-right text-danger"><%=project.getSqaleIndex()[1]%></a>
					                <%} %>
					              <a class="pull-right"><%=project.getSqaleIndex()[0]%></a>
					            </li>
				              </ul>
				            </div>
				            <!-- /.box-body -->
			            </div>
			            <!-- /.box -->
			          </div>
			          <!-- /.col -->
			          <div class="col-md-6">
			            <div class="box box-warning">
				          <div class="box-header with-border">
				            <h3 class="box-title">Density</h3>
				          </div>
				          <!-- /.box-header -->
				          <div class="box-body">
				            <ul class="list-group list-group-unbordered">
				              <li class="list-group-item">
					            <b>Duplicated lines</b> 
					              <%if(Double.parseDouble(project.getDuplicatedDensity()[1])>=0){ %>
					            <a class="pull-right text-success"><%=project.getDuplicatedDensity()[1] %>%</a>
					              <%}
					                else{%>
					            <a class="pull-right text-danger"><%=project.getDuplicatedDensity()[1] %>%</a>
					              <%} %>
					            <a class="pull-right"><%=project.getDuplicatedDensity()[0] %>%</a>
					          </li>
					          <li class="list-group-item">
					            <b>Comment lines</b> 
					              <%if(Double.parseDouble(project.getCommentDensity()[1])>=0){ %>
					            <a class="pull-right text-success"><%=project.getCommentDensity()[1] %>%</a>
					              <%}
					                else{%>
					            <a class="pull-right text-danger"><%=project.getCommentDensity()[1] %>%</a>
					              <%} %>
					            <a class="pull-right"><%=project.getCommentDensity()[0] %>%</a>
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
			                <h3 class="box-title">Issues</h3>
			              </div>
			              <!-- /.box-header -->
			              <%if(project.getViolationsData().size()>1){ %>
			              <div class="box-body">
			                <br>
			                <canvas id="doughnutChartOfSonar" style="height:250px"></canvas>
			              </div>
			              <div class="box-body text-center">
			                <b>Violations : <%=project.getViolationsData().get("violations")[0] %></b>
			              </div>
			              <%}
			                else{%>
			              <div class="box-body text-center">
			                  <b>No data</b>
			              </div>
			              <%} %>
			              <!-- /.box-body -->
			            </div>
	                  </div>
			          <!-- /.col -->
	                  <div class="col-md-7">
	                    <div class="box box-warning">
			              <div class="box-header with-border">
			                <h3 class="box-title">Changes of Issues</h3>
			              </div>
			              <!-- /.box-header -->
			              <%if(project.getViolationsData().size()>0){ %>
			              <div class="box-body">
			                <canvas id="barChartOfSonar" style="height:230px"></canvas>
			              </div>
			              <%}
			                else{%>
			              <div class="box-body text-center">
			                  <b>No data</b>
			              </div>
			              <%} %>
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
	        
	        <div class="col-md-3">
		      <div class="box box-warning box-solid">
		        <div class="box-header with-border">
		          <h3 class="box-title">Members</h3>
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
	    initJenkinsChart();
  });
  $(function(){
	  $('a[data-toggle="tab"]').on("shown.bs.tab",function(e){
		  var activeTab = $(e.target).text();
		  if(activeTab==="Sonar Status"){
			  initSonarChart();
		  }
		  else if(activeTab==="Jenkins Status"){
			  initJenkinsChart();
		  }
	  });
  });
  function initJenkinsChart(){
	    //get data
	    var successRate = <%=project.getSuccessRate()%>;
	    if(successRate!==-1){//if successRate is -1, the data is not valid.
	    	var failureRate = 1-successRate;
		    //-------------
		    //- PIE CHART -
		    //-------------
		    // Get context with jQuery - using jQuery's .get() method.
		    var pieChartCanvas = $("#pieChartOfJenkins").get(0).getContext("2d");
		    var pieChart = new Chart(pieChartCanvas);
		    var PieData = [
		      {
		        value: successRate.toFixed(4),
		        color: "#00a65a",
		        highlight: "#00a65a",
		        label: "SUCCESS"
		      },
		      {
		        value: failureRate.toFixed(4),
		        color: "#f56954",
		        highlight: "#f56954",
		        label: "FAILURE"
		      }
		    ];
		    var pieOptions = {
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
		      //Boolean - whether to maintain the starting aspect ratio or not when responsive, if set to false, will take up entire container
		      maintainAspectRatio: true,
		      //tooltip
		      customTooltips: function (tooltip) {
			        var tooltipEl = $('#chartjs-tooltip');
			
			        if (!tooltip) {
			            tooltipEl.css({
			                opacity: 0
			            });
			            return;
			        }
			
			        //tooltipEl.removeClass('above below');
			        //tooltipEl.addClass(tooltip.yAlign);
			
			        // split out the label and value and make your own tooltip here
			        var parts = tooltip.text.split(":");
			        var innerHtml = '<span>' + parts[0].trim() + '</span> : <span><b>' + 100 * parseFloat(parts[1]) + '%' + '</b></span>';
			        tooltipEl.html(innerHtml);
			
			        tooltipEl.css({
			            opacity: 1,
			            left: tooltip.chart.canvas.offsetLeft + tooltip.x + 'px',
			            top: tooltip.chart.canvas.offsetTop + tooltip.y + 'px',
			            fontFamily: tooltip.fontFamily,
			            fontSize: tooltip.fontSize,
			            fontStyle: tooltip.fontStyle,
			        });
			   }
		    };
		    //Create pie or douhnut chart
		    // You can switch between pie and douhnut using the method below.
		    var mychart = pieChart.Doughnut(PieData, pieOptions);
	    }
	    
	    //-------------
	    //- BAR CHART -
	    //-------------
	    var size = '<%=project.getLastTenBuilds().size()%>';
	    if(size!=='0'){//If size is 0, there is no data available.
	    	var barChartCanvas = $("#barChartOfJenkins").get(0).getContext("2d");
		    var barChart = new Chart(barChartCanvas);
		    //get data
		    var labelsOfBarChart = new Array(); 
		    var dataOfBarChartTotal = new Array();
		    var dataOfBarChartSuccess = new Array();
		    var dataOfBarChartFailure = new Array();
		    <% 
		    	List<BuildStatus> buildStatus =  project.getLastTenBuilds();
		        if(buildStatus!=null){
		    	  for(int i=0;i<buildStatus.size();i++){
		    		  if(buildStatus.get(i).getTimePrefix().equals("")){
		    			%>
		  	    		  labelsOfBarChart[<%=i%>]='<%=buildStatus.get(i).getTime()%>';
		  	    		<%
		    		  }
		    		  else{
		    			%>
			  	    	  labelsOfBarChart[<%=i%>]='<%="("+buildStatus.get(i).getTimePrefix()+")"+buildStatus.get(i).getTime()%>';
			  	    	<% 
		    		  }
		    		%>
		    		dataOfBarChartTotal[<%=i%>]='<%=buildStatus.get(i).getTotalBuild()%>';
		    		dataOfBarChartSuccess[<%=i%>]='<%=buildStatus.get(i).getSuccessBuild()%>';
		    		dataOfBarChartFailure[<%=i%>]=dataOfBarChartTotal[<%=i%>]-dataOfBarChartSuccess[<%=i%>];
				   <%
		    	  }
		        }
		    %>
		    var barChartData = {
		    	      labels: labelsOfBarChart,
		    	      datasets: [
		    	        {
		    	          label: "Total builds",
		    	          fillColor: "rgba(210, 214, 222, 1)",
		    	          strokeColor: "rgba(210, 214, 222, 1)",
		    	          pointColor: "rgba(210, 214, 222, 1)",
		    	          pointStrokeColor: "#c1c7d1",
		    	          pointHighlightFill: "#fff",
		    	          pointHighlightStroke: "rgba(220,220,220,1)",
		    	          data: dataOfBarChartTotal
		    	        },
		    	        {
		    	          label: "Success builds",
		    	          fillColor: "rgba(60,141,188,0.9)",
		    	          strokeColor: "rgba(60,141,188,0.8)",
		    	          pointColor: "#3b8bba",
		    	          pointStrokeColor: "rgba(60,141,188,1)",
		    	          pointHighlightFill: "#fff",
		    	          pointHighlightStroke: "rgba(60,141,188,1)",
		    	          data: dataOfBarChartSuccess
		    	        },
		    	        {
			    	      label: "Failure builds",
			    	      fillColor: "rgba(60,141,188,0.9)",
			    	      strokeColor: "rgba(60,141,188,0.8)",
			    	      pointColor: "#3b8bba",
			    	      pointStrokeColor: "rgba(60,141,188,1)",
			    	      pointHighlightFill: "#fff",
			    	      pointHighlightStroke: "rgba(60,141,188,1)",
			    	      data: dataOfBarChartFailure
			    	    }
		    	      ]
		    	    };
		    barChartData.datasets[1].fillColor = "#00a65a";
		    barChartData.datasets[1].strokeColor = "#00a65a";
		    barChartData.datasets[1].pointColor = "#00a65a";
		    barChartData.datasets[2].fillColor = "#f56954";
		    barChartData.datasets[2].strokeColor = "#f56954";
		    barChartData.datasets[2].pointColor = "#f56954";
		    var barChartOptions = {
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
		      barValueSpacing: 15,
		      //Number - Spacing between data sets within X values
		      barDatasetSpacing: 5,
		      //scales
		      showScale: false,
		      //Boolean - whether to make the chart responsive
		      responsive: true,
		      maintainAspectRatio: true
		    };
		    barChartOptions.datasetFill = false;
		    barChart.Bar(barChartData, barChartOptions);
	    }
  }
  function initSonarChart(){
	    var size = '<%=project.getViolationsData().size()%>';
	    if(size!=='0'){
	    	//get data
		    var labelsOfBarChart2 = new Array();
		    var dataOfBarChartTotal2 = new Array();
		    var dataOfBarChartChange = new Array();
		    var colorOfPieChart = new Array();
		    <% 
		    	String[] labels = new String[]{"violations","blocker","critical","major","minor","info"};
		        Map<String,String[]> dataOfViolations = project.getViolationsData();
		        int index = 0;
		    	for(int i=0;i<labels.length;i++){
		    		if(dataOfViolations.containsKey(labels[i])){
		    		%>
			    		labelsOfBarChart2[<%=index%>]='<%=labels[i]%>';
			    		dataOfBarChartTotal2[<%=index%>]='<%=dataOfViolations.get(labels[i])[0]%>';
			    		dataOfBarChartChange[<%=index%>]='<%=dataOfViolations.get(labels[i])[1]%>';
			    		if(labelsOfBarChart2[<%=index%>]==='blocker'){
			    			colorOfPieChart[<%=index%>]="#f56954";
			    		}
			    		else if(labelsOfBarChart2[<%=index%>]==='critical'){
			    			colorOfPieChart[<%=index%>]="#f39c12";
			    		}
			    		else if(labelsOfBarChart2[<%=index%>]==='major'){
			    			colorOfPieChart[<%=index%>]="#3c8dbc";
			    		}
			    		else if(labelsOfBarChart2[<%=index%>]==='minor'){
			    			colorOfPieChart[<%=index%>]="#00c0ef";
			    		}
			    		else if(labelsOfBarChart2[<%=index%>]==='info'){
			    			colorOfPieChart[<%=index%>]="#00a65a";
			    		}
			    	<%
			    		index++;
		    		}
		    	}
		    %>
	    	if(size!=='1'){//generate doughtchart of violations
	    		//-------------
	    	    //- DOUGHNUT CHART -
	    	    //-------------
	    	    // Get context with jQuery - using jQuery's .get() method.
	    	    var doughnutChartCanvas = $("#doughnutChartOfSonar").get(0).getContext("2d");
	    	    var doughnutChart = new Chart(doughnutChartCanvas);
	    	    var doughnutData = new Array();
	    	    for(var i=1;i<labelsOfBarChart2.length;i++){
	    	    	doughnutData[i-1] = {
	    	    	        value: dataOfBarChartTotal2[i],
	    	    	        color: colorOfPieChart[i],
	    	    	        highlight: colorOfPieChart[i],
	    	    	        label: labelsOfBarChart2[i]
	    	    	      };
	    	    }
	    	    var doughnutOptions = {
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
	    	    doughnutChart.Doughnut(doughnutData, doughnutOptions);
	    	}
	    	//generate bar chart of violations change
	    	//-------------
		    //- BAR CHART -
		    //-------------
		    var colorsOfBarChart = new Array();
		    for(i=0;i<dataOfBarChartChange.length;i++){
		    	if(dataOfBarChartChange[i]>=0){
		    		colorsOfBarChart[i] = "#00a65a";
		    	}
		    	else{
		    		colorsOfBarChart[i] = "#f56954";
		    	}
		    }
		    
		    var barChartCanvas2 = $("#barChartOfSonar").get(0).getContext("2d");
		    var barChart2 = new Chart(barChartCanvas2);
		    var barChartData2 = {
		    	      labels: labelsOfBarChart2,
		    	      datasets: [
		    	        {
		    	          label: "Total",
		    	          fillColor: "rgba(210, 214, 222, 1)",
		    	          strokeColor: "rgba(210, 214, 222, 1)",
		    	          pointColor: "rgba(210, 214, 222, 1)",
		    	          pointStrokeColor: "#c1c7d1",
		    	          pointHighlightFill: "#fff",
		    	          pointHighlightStroke: "rgba(220,220,220,1)",
		    	          data: dataOfBarChartTotal2
		    	        },
		    	        {
		    	          label: "Change",
		    	          fillColor: "rgba(60,141,188,0.9)",
		    	          strokeColor: "rgba(60,141,188,0.8)",
		    	          pointColor: "#3b8bba",
		    	          pointStrokeColor: "rgba(60,141,188,1)",
		    	          pointHighlightFill: "#fff",
		    	          pointHighlightStroke: "rgba(60,141,188,1)",
		    	          data: dataOfBarChartChange
		    	        }
		    	      ]
		    	    };
		    barChartData2.datasets[1].fillColor = colorsOfBarChart;
		    barChartData2.datasets[1].strokeColor = colorsOfBarChart;
		    barChartData2.datasets[1].pointColor = colorsOfBarChart;
		    var barChartOptions2 = {
		      //Boolean - Whether the scale should start at zero, or an order of magnitude down from the lowest value
		      scaleBeginAtZero: false,
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
		      barValueSpacing: 10,
		      //Number - Spacing between data sets within X values
		      barDatasetSpacing: 5,
		      //Boolean - whether to make the chart responsive
		      responsive: true,
		      maintainAspectRatio: true,
		    };
		    barChartOptions2.datasetFill = false;
		    barChart2.Bar(barChartData2, barChartOptions2);
	    }
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