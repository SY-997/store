
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
    <title>任务进度</title>
</head>
<script >
$(function () {


    $("#inlineRadio1").change(function(){
        //显示全部
        $("tr").css("display","");
    })
    $("#inlineRadio1").click(function(){
        //显示全部
        $("tr").css("display","");
    })
    $("#inlineRadio2").change(function(){
        //未完成
        $("tr:has(input[type='checkbox'][checked='checked'])").css("display","none");
        $("tr:has(input[type='checkbox'][name='uid[]'])").css("display","");
    })
    $("#inlineRadio3").change(function(){
        //完成
        $("tr:has(input[type='checkbox'][checked='checked'])").css("display","");
        $("tr:has(input[type='checkbox'][name='uid[]'])").css("display","none");
    })

});
</script>
<body>
<div class="container-fluid">
    <!-- Stack the columns on mobile by making one full-width and the other half-width -->
        <div class="row">
            <div class="col-xs-12 col-md-12">
                <h2>已完成${total}人，未完成${45-total}人</h2>
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
            <div class="col-xs-12 col-md-12">
                <table class="table">
                    <tr>
                        <th>序号</th>
                        <th>姓名</th>
                        <th>
                           是否完成
                        </th>
                    </tr>
                    <c:forEach var="user" items="${users}">
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.name}</td>
                        <td>
                            <c:if test="${user.flag==1}">
                                <input type="checkbox" checked="checked" disabled="true"  value="${user.id}"/>
                            </c:if>
                            <c:if test="${user.flag!=1}">
                                <input type="checkbox" name="uid[]" disabled="true"   value="${user.id}"/>
                            </c:if>
                        </td>
                        </c:forEach>
                </table>
            </div>
        </div>
</div>
</body>
</html>