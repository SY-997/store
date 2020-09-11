<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!--
    描述：菜单栏
-->
<div class="container-fluid">
    <div class="col-md-4">
        <img height="51" src="${pageContext.request.contextPath}/img/logo3.jpg" />
    </div>
    <div class="col-md-5">
        <img src="${pageContext.request.contextPath}/img/header.png" />
    </div>
    <div class="col-md-3" style="padding-top:20px">
        <ol class="list-inline">
            <c:if test="${empty loginUser}">
            <li><a href="${pageContext.request.contextPath}/UserServlet?method=loginUI">登录</a></li>
            <li><a href="${pageContext.request.contextPath}/UserServlet?method=registUI">注册</a></li>
            </c:if>

            <c:if test="${not empty loginUser}">
                <li>欢迎${loginUser.username}</li>
                <li><a href="${pageContext.request.contextPath}/UserServlet?method=outLogin">退出</a></li>
                <li><a href="${pageContext.request.contextPath}/jsp/cart.jsp">购物车</a></li>
                <li><a href="${pageContext.request.contextPath}/OrderServlet?method=findMyOrdersWithPage&num=1">我的订单</a></li>
            </c:if>

        </ol>
    </div>
</div>
<!--
    描述：导航条
-->
<div class="container-fluid">
    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="${pageContext.request.contextPath}/index.jsp">首页</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav" id="myUL">

                </ul>


            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container-fluid -->
    </nav>
</div>
<script>
$(function(){
	$.post("${pageContext.request.contextPath}/CategoryServlet",{"method":"findCategory"},function(dt){
		//jquery遍历数据
		$.each(dt,function(i,obj){
			var li="<li><a href='${pageContext.request.contextPath}/ProductServlet?method=findProductsWithCidAndPage&num=1&cid="+obj.cid+"'>"+obj.cname+"</a></li>";
			$("#myUL").append(li);
		});		
		
	},"json");
});
</script>







