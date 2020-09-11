<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>

	<head>
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<title>购物车</title>
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
			
			.container .row div {
				/* position:relative;
	 float:left; */
			}
			
			font {
				color: #3164af;
				font-size: 18px;
				font-weight: normal;
				padding: 0 10px;
			}
		</style>
	</head>

	<body>

	<%@include file="header.jsp"%>

		<div class="container">
			<c:if test="${empty cart.cartItems }">
				<div class="row">
					<div class="col-md-12">
						<h1>开启剁手模式</h1>
					</div>
				</div>
			</c:if>

			<c:if test="${not empty cart.cartItems }">
				<div class="row">
					<div style="margin:0 auto; margin-top:10px;width:950px;">
						<strong style="font-size:16px;margin:5px 0;">订单详情</strong>
						<table class="table table-bordered">
							<tbody>
							<tr class="warning">
								<th>图片</th>
								<th>商品</th>
								<th>价格</th>
								<th>数量</th>
								<th>小计</th>
								<th>操作</th>
							</tr>
							<c:forEach items="${cart.cartItems}" var="item" varStatus="status">
								<tr class="active">
									<td width="60" width="40%">

										<img src="${pageContext.request.contextPath}/${item.product.pimage}" width="70" height="60">
									</td>
									<td width="25%">
										<a target="_blank">${item.product.pname}</a>
									</td>
									<td width="15%">
										￥
										<fmt:formatNumber value=" ${item.product.shop_price}" pattern="#"/>
									</td>
									<td width="20%">
										<a href="javascript:void(0);">
											<input type="submit" onclick="changeNum('-','${status.count}');"  width="25" value="-" name="submit" border="0" style="background: url('${pageContext.request.contextPath}/img/admin/bt_02.jpg') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
													height:25px;width:25px;color:white;">
										</a>

										<input type="text" id="num_${status.count}" onkeyup="keyup('${status.count}');" name="quantity" value="${item.num}" maxlength="3" size="10" oninput = "value=value.replace(/[^\d]/g,'')">
										<a href="javascript:void(0);">
											<input type="submit" id="plus" onclick="changeNum('+','${status.count}');"  width="25" value="+" name="submit" border="0" style="background: url('${pageContext.request.contextPath}/img/admin/bt_02.jpg') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
													height:25px;width:25px;color:white;">
										</a>
										<input hidden id="pid_${status.count}" value="${item.product.pid}">
									</td>
									<td width="15%">
										<span class="subtotal" id="subtotal_${status.count}">￥<fmt:formatNumber value=" ${item.subTotal}" pattern="#"/></span>
									</td>
									<td>
										<a href="javascript:void(0);" id="${item.product.pid}" class="delete">删除</a>
									</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</div>
				</div>

				<div style="margin-right:130px;">
					<div style="text-align:right;">
						<em style="color:#ff6600;">
							登录后确认是否享有优惠&nbsp;&nbsp;
						</em> 赠送积分: <em id="totals1" style="color:#ff6600;"><fmt:formatNumber value=" ${cart.total}" pattern="#"/></em>&nbsp; 商品金额: <strong id="totals2" style="color:#ff6600;">￥<fmt:formatNumber value=" ${cart.total}" pattern="#"/>元</strong>
					</div>
					<div style="text-align:right;margin-top:10px;margin-bottom:10px;">
						<a href="${pageContext.request.contextPath}/CartServlet?method=clearCart" id="clear" class="clear">清空购物车</a>
						<a href="${pageContext.request.contextPath}/OrderServlet?method=saveOrder">
								<%--提交表单 --%>
							<input type="submit" id="sub"  width="100" value="提交订单" name="submit" border="0" style="background: url('${pageContext.request.contextPath}/img/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0);
									height:35px;width:100px;color:white;">
						</a>
					</div>
				</div>

			</c:if>

		</div>

		<%@include file="footer.jsp"%>

	</body>
	<script>
	$(function(){
	//页面加载完毕之后获取到class的值为delete元素,为其绑定点击事件
	$(".delete").click(function(){
	if(confirm("确认删除?")){
	//获取到被删除商品pid
	var pid=this.id;
	window.location.href="${pageContext.request.contextPath}/CartServlet?method=removeCartItem&pid="+pid;
	}
	});
	});

	function keyup(i) {
		var num=$("#num_"+i).val();
		if (num==0||num.length==0){
			alert("数量不能为0或空！");
			num=1;
			$("#num_"+i).val(num);
		}
		$("#sub").prop("disabled","disabled");
		var pid=$("#pid_"+i).val();
		console.log(num);
		var url="${pageContext.request.contextPath}/CartServlet?method=changeCartgItemToCart&pid="+pid+"&num="+num;
		$.ajax({
			type: "GET",
			url: url,
			dataType: "json",
			success:function (data) {
				if (data.flag=="true"){
					var subtotal=parseFloat(data.total).toString();
					$("#subtotal_"+i).html("￥"+ subtotal);
					var totals=parseFloat(data.totals).toString();
					$("#totals1").html(totals);
					$("#totals2").html("￥"+totals+"元")
					$("#sub").prop("disabled",false);
				}

			}
		});
	}

	function changeNum(str,i) {
		var num=$("#num_"+i).val();
		if (str=="-"){
			if (num>1){
				$("#num_"+i).val(--num);
			}
		}else{
			if (num<999){
				$("#num_"+i).val(++num);
			}
		}
		$("#sub").prop("disabled","disabled");
		var pid=$("#pid_"+i).val();
		console.log(num);
		var url="${pageContext.request.contextPath}/CartServlet?method=changeCartgItemToCart&pid="+pid+"&num="+num;
		$.ajax({
			type: "GET",
			url: url,
			dataType: "json",
			success:function (data) {
				if (data.flag=="true"){
					var subtotal=parseFloat(data.total).toString();
					$("#subtotal_"+i).html("￥"+ subtotal);
					var totals=parseFloat(data.totals).toString();
					$("#totals1").html(totals);
					$("#totals2").html("￥"+totals+"元")
					$("#sub").prop("disabled",false);
				}
			}
		});

	}
	</script>

</html>