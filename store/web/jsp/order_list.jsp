<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>会员登录</title>
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

		</nav>

		<div class="container">
			<div class="row">
				<c:if test="${empty page.records}">
					<h2>还没有任何订单!!!</h2>
				</c:if>

				<c:if test="${not empty page.records}">
				<div style="margin:0 auto; margin-top:10px;width:950px;">
					<strong>我的订单</strong>
					<table class="table table-bordered">
					<c:forEach items="${page.records}" var="v">
						<tbody>
						<tr class="success">

							<th colspan="5">订单编号:${v.oid} <span style="float: right">
								创建时间：
								<fmt:formatDate value="${v.ordertime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
								&nbsp;&nbsp;&nbsp;合计:￥ <fmt:formatNumber value=" ${v.total}" pattern="#.00"/>元&nbsp;&nbsp;&nbsp;&nbsp;
								订单状态:<c:if test="${v.state==1}"><a href="${pageContext.request.contextPath}/OrderServlet?method=findOrderByOid&oid=${v.oid}">未付款</a></c:if>
								         <c:if test="${v.state==2}">待发货</c:if>
								<c:if test="${v.state==3}">已发货，<a href="${pageContext.request.contextPath}/OrderServlet?method=takeDelivery&oid=${v.oid}">确认收货</a></c:if>
								         <c:if test="${v.state==4}">交易完成</c:if>

							</span> </th>
						</tr>
						<tr class="success">
							<th class="th">收货人</th>
							<th class="th">电话号码</th>
							<th class="th" colspan="3">地址</th>
						</tr>



						<tr class="active">
							<td width="10%">
									${v.name}
							</td>
							<td width="15%">
									${v.telephone}
							</td>
							<td width="50%" colspan="3">
									${v.address}

							</td>
						</tr>
						<tr class="warning">
							<th>图片</th>
							<th>商品</th>
							<th>价格</th>
							<th>数量</th>
							<th>小计</th>
						</tr>
						<c:forEach items="${v.list}" var="o">
							<tr class="active">
								<td width="60" width="40%">
									<input type="hidden" name="id" value="22">
									<img src="${pageContext.request.contextPath}/${o.product.pimage}" width="70" height="60">
								</td>
								<td width="30%">
									<a target="_blank"> ${o.product.pname}</a>
								</td>
								<td width="20%">
									￥
									<fmt:formatNumber value=" ${o.product.shop_price}" pattern="#.00"/>
								</td>
								<td width="10%">
										${o.quantity}
								</td>
								<td width="15%">
									<span class="subtotal">￥ <fmt:formatNumber value=" ${o.total}" pattern="#.00"/></span>
								</td>
							</tr>
						</c:forEach>
						</tbody>
					</c:forEach>
					</table>
				</div>
			</div>
			<%@include file="pageFile.jsp"%>
			</c:if>

		</div>

	<%@include file="footer.jsp"%>
	</body>

</html>