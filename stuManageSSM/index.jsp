<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
		<link rel="stylesheet" href="css/bootstrap.css" />

		<!--需要引入JQuery-->
		<script type="text/javascript" src="js/jquery-1.11.0.js "></script>

		<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
		<script type="text/javascript" src="js/bootstrap.js"></script>
		<link rel="stylesheet" href="css/style.css" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>任务完成情况</title>
	</head>
	<script type="text/javascript">
	//请求页面
		function getinfo() {
			$.ajax({
				url: "user/findIndexInfo", //请求的url地址
				dataType: "json", //返回格式为json
				async: true, //请求是否异步，默认为异步，这也是ajax重要特性
				type: "get", //请求方式
				success: function(req) {
					$.each(req,function(key,value){
						console.log(key+"="+value);
						$("#"+key).html(value);
					});
					$("#wid").css("width",req.count);
				},
				error: function() {
					//请求出错处理
					console.log("请求失败");
				}
			});
		}
	</script>
	<body onload="getinfo()">
		<div class="container-fluid">

			<!-- Stack the columns on mobile by making one full-width and the other half-width -->
			<div class="row">


				<div class="col-xs-12 col-md-12">
					<h2>本次任务是： <small><span id="tname">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></small></h2>
				</div>
				<div class="col-xs-5 col-md-2" style="">
					<h4><a href="user/findListInfo" target="_blank">任务完成进度:</a></h4>
				</div>
				<div class="col-xs-7 col-md-10">
					<div class="progress" style="margin-bottom: 0px;height: 35px;margin-left: -20px;">
						<div id="wid" class="progress-bar progress-bar-striped active" role="progressbar" aria-valuenow="60"
						 aria-valuemin="0" aria-valuemax="100" style="min-width:  2em;width: 0%;">
							<font id="count" style="line-height: 40px;font-size: 20px;">0%</font>
						</div>
					</div>
				</div>
				<div class="col-xs-12 col-md-12">
					<table class="table table-striped">
						<tr>
							<th>序号</th>
							<th>负责姓名</th>
							<th id="total">完成人数</th>
						</tr>
						<tr>
							<td>1</td>
							<td><a href="user/findTaskFinished?sid=1">谢东琴</a></td>
							<td id="s1">0</td>
						</tr>
						<tr>
							<td>2</td>
							<td><a href="user/findTaskFinished?sid=2">黎可</a></td>
							<td id="s2">0</td>
						</tr>
						<tr>
							<td>3</td>
							<td><a href="user/findTaskFinished?sid=3">曾永吉</a></td>
							<td id="s3">0</td>
						</tr>
						<tr>
							<td>4</td>
							<td><a href="user/findTaskFinished?sid=4">陈双强</a></td>
							<td id="s4">0</td>
						</tr>
						<tr>
							<td>5</td>
							<td><a href="user/findTaskFinished?sid=5">郑辉</a></td>
							<td id="s5">0</td>
						</tr>
						<tr>
							<td>6</td>
							<td><a href="user/findTaskFinished?sid=6">张跃</a></td>
							<td id="s6">0</td>
						</tr>
						<tr>
							<td>7</td>
							<td><a href="user/findTaskFinished?sid=7">杨原苏</a></td>
							<td id="s7">0</td>
						</tr>
						<tr>
							<td>8</td>
							<td><a href="user/findTaskFinished?sid=8">曾樊奥</a></td>
							<td id="s8">0</td>
						</tr>
					</table>
				</div>
			</div>
	</body>
</html>
