<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt" %>
<!doctype html>
<html>

<head>
	<meta charset="utf-8" />
	<meta name="viewport" content="width=device-width, initial-scale=1">
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
		td,.th{
			text-align:center;
			valign:center;
		}
	</style>
</head>

<body>
</nav>

<div class="container-fluid">
	<div class="row">
		<div style="margin:0 auto; margin-top:10px;">
			<table class="table table-bordered table-hover" >
					<tbody>
					<tr>
						<td class="ta_01" align="center" bgColor="#afd1f3" colspan="5">
							<strong>订单详情</strong>
							<INPUT class="button_ok" STYLE="float: right" type="button" onclick="history.go(-1)" value="返回"/>
						</td>
					</tr>
					<tr class="success">

						<th colspan="5">订单编号:${order.oid}
							<span style="float: right">
								创建时间：
								<fmt:formatDate value="${order.ordertime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/>
								&nbsp;&nbsp;&nbsp;合计:￥<c:if test="${order.total==0}">0.00元</c:if>
								          <c:if test="${order.total!=0}"><fmt:formatNumber value=" ${ order.total }" pattern="#.00"/>元</c:if>&nbsp;&nbsp;&nbsp;&nbsp;
								订单状态:<c:if test="${order.state==1}">未付款</c:if>
								<c:if test="${order.state==2}">未发货，点击<a href="AdminOrderServlet?method=sendOut&oid=${order.oid}">发货</a></c:if>
							           	 <c:if test="${order.state==3}">待签收</c:if>
								         <c:if test="${order.state==4}">订单完成</c:if>

							</span>
						</th>
					</tr>
					<tr class="success">
						<th class="th">收货人</th>
						<th class="th">电话号码</th>
						<th class="th" colspan="3">地址</th>
					</tr>
					<tr class="active">
						<td width="10%">
							${order.name}
						</td>
						<td width="15%">
							${order.telephone}
						</td>
						<td width="50%" colspan="3">
							${order.address}

						</td>
					</tr>
					<tr class="warning">
						<th class="th">图片</th>
						<th class="th">商品</th>
						<th class="th">价格</th>
						<th class="th">数量</th>
						<th class="th">小计</th>
					</tr>
					<c:forEach items="${order.list}" var="o">
						<tr class="active">
							<td  width="10%">
								<input type="hidden" name="id" value="22">
								<img src="${pageContext.request.contextPath}/${o.product.pimage}" width="70" height="60">
							</td>
							<td width="30%">
								<a target="_blank"> ${o.product.pname}</a>
							</td>
							<td width="15%">
								￥
								<fmt:formatNumber value=" ${o.product.shop_price}" pattern="#.00"/>
							</td>
							<td width="20%">
									${o.quantity}
							</td>
							<td width="15%">
								<span class="subtotal">￥<fmt:formatNumber value=" ${ o.total }" pattern="#.00"/></span>
							</td>
						</tr>
					</c:forEach>
					</tbody>
			</table>
		</div>
	</div>
</div>
</body>

</html>