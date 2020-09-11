﻿
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>商品详情信息</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" type="text/css" />
		<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
		<script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
		<!-- 引入自定义css文件 style.css -->
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css" type="text/css"/>

		<style>
			body {
				margin-top: 20px;
				margin: 0 auto;
			}
			
			.carousel-inner .item img {
				width: 100%;
				height: 300px;
			}
		</style>
	</head>

	<body>

	<%@include file="header.jsp"%>


		<div class="container">
			<div class="row">
				<a><INPUT  type="button" onclick="history.go(-1)" value="返回"/></a>

				<div style="margin:0 auto;width:950px;">
					<div class="col-md-6">
						<img style="opacity: 1;width:400px;height:350px;" title="" class="medium" src="${pageContext.request.contextPath}/${info.pimage}">
					</div>

					<div class="col-md-6">
						<div><strong>${info.pname}</strong></div>
						<div style="border-bottom: 1px dotted #dddddd;width:350px;margin:10px 0 10px 0;">
							<div>编号：${info.pid}</div>
						</div>

						<div style="margin:10px 0 10px 0;">商城价: <strong style="color:#ef0101;">￥： <fmt:formatNumber value=" ${ info.shop_price }" pattern="#.00"/>元/份</strong> 市场价： <del>￥<fmt:formatNumber value=" ${ info.market_price }" pattern="#.00"/>元/份</del>
						</div>

						<div style="margin:10px 0 10px 0;">促销: <a target="_blank" title="限时抢购 (2014-07-30 ~ 2015-01-01)" style="background-color: #f07373;">限时抢购</a> </div>

						<div style="padding:10px;border:1px solid #e7dbb1;width:330px;margin:15px 0 10px 0;;background-color: #fffee6;">
							<form id="myForm" method="post" action="${pageContext.request.contextPath}/CartServlet?method=addCartgItemToCart">

							<div style="border-bottom: 1px solid #faeac7;margin-top:20px;padding-left: 10px;">购买数量:

								<input  name="pid" value="${info.pid}"  type="hidden">

								<input id="quantity" name="count" value="1" maxlength="3" size="10" type="text" oninput = "value=value.replace(/[^\d]/g,'')"> </div>

							<div style="margin:20px 0 10px 0;;text-align: center;">
								<%--加入到购物车 --%>
								<a href="javascript:void(0)">
									<input id="btnId" style="background: url('${pageContext.request.contextPath}/img/product.gif') no-repeat scroll 0 -600px rgba(0, 0, 0, 0);height:36px;width:127px;" value="加入购物车" type="button">
								</a> &nbsp;
							</div>
						    	</form>
						</div>
					</div>
				</div>
				<div class="clear"></div>
				<div style="width:950px;margin:0 auto;">
					<div style="background-color:#d3d3d3;width:930px;padding:10px 10px;margin:10px 0 10px 0;">
						<strong>商品介绍</strong>
						<h3>${info.pdesc}</h3>
					</div>
				</div>

			</div>
		</div>

	<%@include file="footer.jsp"%>

	</body>
	<script>

		$(function(){
			//提交购物车
			$("#btnId").click(function(){
				var formObj=document.getElementById("myForm");
				var num=$("#quantity").val();
				if (num==0||num.length==0){
					alert("购买数量不能为0或者空!!!");
					$("#quantity").val(1);
				}else {
					formObj.submit();
				}

			});

			/*//购买数量合法
			$("#quantity").blur(function () {
				var num=$(this).val();
				if (num==0||num.length==0){

					$(this).val(1);

				}

			});*/
		});

	</script>

</html>