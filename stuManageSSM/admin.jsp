<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css" />
	<!--需要引入JQuery-->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.0.js "></script>
	<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/admin.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>管理信息</title>
</head>
<body>
<div class="container-fluid">
	<!-- Single button -->

	<div class="row">
		<div class="col-xs-4 col-md-3">
			<br>
			<span class="label label-default" style="font-size: 19px;">完成情况：</span>
		</div>
		<div class="col-xs-5 col-md-4">
			<br>
			<select class="form-control" id="t_thing1" style="margin-top: -7px;margin-left: 2px; " autocomplete="off">
				<c:forEach items="${tasks}" var="name" varStatus="status">
					<c:if test="${status.count==tid}">
						<option value="${status.count}" selected="selected">${status.count}.${name}</option>
					</c:if>
					<c:if test="${status.count!=tid}">
						<option value="${status.count}" >${status.count}.${name}</option>
					</c:if>

				</c:forEach>
			</select>
		</div>
		<div class="col-xs-3 col-md-4">
			<br>
			<span class="label label-success" id="count" style="font-size: 19px;">${count}人</span>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-4 col-md-3">
			<br>
			<span class="label label-default" style="font-size: 19px;">当前任务：</span>
		</div>
		<div class="col-xs-5 col-md-4">
			<br>
			<select class="form-control" id="t_thing2" style="margin-top: -7px;margin-left: 2px;"autocomplete="off">
				<c:forEach items="${tasks}" var="name" varStatus="status">
					<c:if test="${status.count==tid}">
						<option value="${status.count}" selected="selected">${status.count}.${name}</option>
					</c:if>
					<c:if test="${status.count!=tid}">
						<option value="${status.count}" >${status.count}.${name}</option>
					</c:if>

				</c:forEach>
			</select>
		</div>
		<div class="col-xs-3 col-md-4">
			<br>
			<button type="button" id="c_thing" class="btn btn-warning" data-toggle="modal" data-target="#myModal" style="font-size: 13px;margin-top: -6px;margin-left: 1px;">确认更改</button>
		</div>
	</div>
	<div class="row">

		<div class="col-xs-12 col-md-12">
			<br>
			<div class="form-group">
				<span class="label label-default control-label" style="font-size: 19px;margin-right: 60px;">添加新的任务：</span>
				<button type="button" class="btn btn-warning" id="t_bt" style="font-size: 18px;width: 130px;">确认添加</button>
				<input type="text" style="position: relative;margin-top: 10px;" class="form-control" id="t_name" placeholder="添加的任务名称">
			</div>
		</div>

	</div>
	<div class="row">
		<div class="col-xs-12 col-md-12">
			<span class="label label-default control-label" style="font-size: 19px;margin-right:20px;">查看方式：</span>
			<label class="radio-inline">
				<input type="radio" checked=true name="inlineRadioOptions" id="inlineRadio1" value="option1"> 全部
			</label>
			<label class="radio-inline">
				<input type="radio" name="inlineRadioOptions" id="inlineRadio2" value="option2"> 未完成
			</label>
			<label class="radio-inline">
				<input type="radio" name="inlineRadioOptions" id="inlineRadio3" value="option3"> 已完成
			</label>
		</div>
	</div>
	<div class="row">
		<div class="col-xs-12 col-md-12">
			<br>
			<form class="form-horizontal ">
				<!-- <label for="inputPassword3" class="col-xs-3 control-label">Password</label> -->
				<span class="label label-default control-label col-xs-4 col-md-1" style="font-size: 19px;margin-right:20px;">查看姓名：</span>
				<div class="col-xs-4">
					<input type="text" class="form-control" id="inputname" placeholder="输入姓名" value="">
				</div>
				<button type="button" id="findname" class="btn btn-warning" data-toggle="modal" style="font-size: 15px;margin-top:2px;margin-left: 5px;">查看</button>
			</form>
		</div>
	</div>

	<!-- 模态框（Modal） -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="myModalLabel">
						确认当前执行任务修改为：
					</h4>
				</div>
				<div id="t_text" class="modal-body">
					1.劳动截图
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal" id="set_tid">
						提交更改
					</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
	<div class="row">
		<div class="col-xs-12 col-md-12">
			<h2>完成任务情况：</h2>
		</div>
		<div class="col-xs-12 col-md-12">
			<table class="table" id="tab">
				<tr>
					<th>序号</th>
					<th>姓名</th>
					<th>所属Sid</th>
					<th>完成情况</th>
					<th> </th>
					<th>更改状态</th>
				</tr>
				<tr>
					<c:forEach var="user" items="${users}">
				<tr>
					<td>${user.id}</td>
					<td>${user.name}</td>
					<td>${user.sid}</td>
					<td name="${user.id}">
						<c:if test="${user.flag==1}">
							<input type="checkbox" checked="checked" disabled="disabled"   value="${user.id}"/>
						</c:if>
						<c:if test="${user.flag!=1}">
							<input type="checkbox" name="unchecked" disabled="disabled"  value="${user.id}"/>
						</c:if>
					</td>
					<td><span id="span_${user.id}" class="" aria-hidden="true"></span></td>
					<td id="change_${user.id}"><button type="button" onclick="change(${user.id},${user.flag})"  class="btn btn-warning">更改</button></td>
					</c:forEach>
				</tr>
			</table>

		</div>
	</div>
</div>
</body>
</html>
