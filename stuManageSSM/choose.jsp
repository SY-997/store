
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css" />
		<!--需要引入JQuery-->
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.0.js "></script>
		<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>具体负责人</title>
</head>
<script >
		$(function(){
			
			//全选框
			$("#all_check").click(function(){
				//属性选择器
			
				$("input[type='checkbox'][name='uid[]']").prop("checked",this.checked);
			});
			
		});
	</script>
<body>
<div class="container-fluid">
			<!-- Stack the columns on mobile by making one full-width and the other half-width -->
			<form action="${pageContext.request.contextPath}/user/changeChecked" method="post">
			<div class="row">
				<div class="col-xs-12 col-md-12">
					<h2>勾选提交的人员,请仔细确认选择</h2>
				</div>
				<div class="col-xs-12 col-md-12">
					<table class="table">
						<tr>
							<th>序号</th>
							<th>姓名</th>
							<th>
						        <input type="checkbox" id="all_check" />全选
							</th> 
						</tr>
<c:forEach var="user" items="${users}">
<tr>
							<td>${user.id}</td>
							<td>${user.name}</td>
							<td>
								<c:if test="${user.flag==1}">
									<%--如果完成了的用户,则禁用checkbox--%>
									<input type="checkbox" checked="checked" disabled="true"  value="${user.id}"/>
								</c:if>
								<c:if test="${user.flag!=1}">
									<input type="checkbox" name="uid[]"  value="${user.id}"/>
								</c:if>
							</td>
</c:forEach>
					</table>
			<button type="submit" class="btn btn-primary btn-lg btn-block">确认提交</button>
				</div>
			</div>
				</form>
			</div>
</body>
</html>