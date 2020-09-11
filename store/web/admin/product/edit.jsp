<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<HTML>
<HEAD>
	<meta http-equiv="Content-Language" content="zh-cn">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<LINK href="${pageContext.request.contextPath}/css/Style1.css" type="text/css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js" type="text/javascript"></script>
</HEAD>
<script type="text/javascript">
	function readURL(input) {
		if (input.files && input.files[0]) {
			var reader = new FileReader();
			reader.onload = function (e) {
				$('#blah').attr('src', e.target.result);
			}
			reader.readAsDataURL(input.files[0]);
		}
	}
$(function () {
	$("#img").change(function(){
		readURL(this);
	});
});

</script>

<body>
<!--  -->
<form id="userAction_save_do" name="Form1" action="${pageContext.request.contextPath}/AdminProductServlet?method=updateProduct" method="post" enctype="multipart/form-data">
	&nbsp;
	<table cellSpacing="1" cellPadding="5" width="100%" align="center" bgColor="#eeeeee" style="border: 1px solid #8ba7e3" border="0">
		<tr>
			<td class="ta_01" align="center" bgColor="#afd1f3" colSpan="4"
				height="26">
				<STRONG>编辑商品</STRONG>
			</td>
		</tr>

		<tr>
			<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
				商品名称：
			</td>
			<td class="ta_01" bgColor="#ffffff">
				<input type="text" name="pname" value="${product.pname}" id="userAction_save_do_logonName" class="bg"/>
				<input type="hidden" name="pid" value="${product.pid}">
			</td>
			<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
				是否热门：
			</td>
			<td class="ta_01" bgColor="#ffffff">
				<select name="is_hot">
					<c:if test="${product.is_hot==1}">
						<option value="1" checked="checked">是</option>
						<option value="0">否</option>
					</c:if>
					<c:if test="${product.is_hot==0}">
						<option value="1">是</option>
						<option value="0" selected="selected">否</option>
					</c:if>
				</select>
			</td>
		</tr>
		<tr>
			<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
				市场价格：
			</td>
			<td class="ta_01" bgColor="#ffffff">
				<input type="text" name="market_price" value="<fmt:formatNumber value=" ${ product.market_price }" pattern="#"/>" id="userAction_save_do_market_price" class="bg"/>
			</td>
			<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
				商城价格：
			</td>
			<td class="ta_01" bgColor="#ffffff">
				<input type="text" name="shop_price" value="<fmt:formatNumber value=" ${ product.shop_price }" pattern="#"/>" id="userAction_save_do_shop_price" class="bg"/>
			</td>
		</tr>
		<tr>
			<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
				商品图片：
			</td>
			<td class="ta_01" bgColor="#ffffff" colspan="3">
				<input type="hidden" name="pimage" value="${product.pimage}">
				<img  id="blah" width="130" height="130" src="${ pageContext.request.contextPath }/${product.pimage}">
				<input type="file" id="img" accept=".png,.jpg,.gif,.bmp,.jpeg" name="pimage" />


			</td>
		</tr>
		<tr>
			<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
				所属的分类：
			</td>
			<td class="ta_01" bgColor="#ffffff" colspan="3">
				<select name="cid">
					<c:forEach items="${list}" var="c" >
						<c:if test="${c.cid==product.cid}"><option value="${c.cid}" selected="selected">${c.cname}</option></c:if>
						<c:if test="${c.cid!=product.cid}"><option value="${c.cid}" >${c.cname}</option></c:if>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td width="18%" align="center" bgColor="#f5fafe" class="ta_01">
				商品描述：
			</td>
			<td class="ta_01" bgColor="#ffffff" colspan="3">
					<textarea name="pdesc" value="" rows="5" cols="30">${product.pdesc}</textarea>
			</td>
		</tr>
		<tr>
			<td class="ta_01" style="WIDTH: 100%" align="center"
				bgColor="#f5fafe" colSpan="4">
				<button type="submit" id="userAction_save_do_submit" value="确定修改" class="button_ok">
					&#30830;&#23450;
				</button>


				<FONT face="宋体">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</FONT>
				<INPUT class="button_ok" type="button" onclick="history.go(-1)" value="返回"/>
				<span id="Label1"></span>
			</td>
		</tr>
	</table>
</form>
</body>
</HTML>