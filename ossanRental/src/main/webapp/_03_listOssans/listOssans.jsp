<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>   
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"  %>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>顯示商品資訊</title>
	<style type="text/css">
  		#quote { white-space:pre-wrap }
	</style>
</head>

<body>

<jsp:include page="/fragment/top.jsp" />
	<div style="padding-left:50px">
	
		<a class="pure-button pure-button-primary" href="<c:url value=
				'/_03_listOssans/DisplayOssanProducts' />">全部</a>
		<a class="pure-button pure-button-primary" href="<c:url value=
				'/_03_listOssans/DisplayOssanProductsNorth' />">北部</a>
		<a class="pure-button pure-button-primary" href="<c:url value=
				'/_03_listOssans/DisplayOssanProducts' />">中部</a>
		<a class="pure-button pure-button-primary" href="<c:url value=
				'/_03_listOssans/DisplayOssanProducts' />">南部</a>
		<a class="pure-button pure-button-primary" href="<c:url value=
				'/_03_listOssans/DisplayOssanProducts' />">其他</a>
	
		<c:choose>
		
		   <c:when test="${ShoppingCart.itemNumber > 0}">
		      <!-- 購物車內有一項以上的商品 -->
		      <c:set var="cartContent1" value="購物車內有${ShoppingCart.itemNumber}項商品"/>
		      	<span class="pure-form-message-inline">${cartContent1}</span>
				<span class="pure-form-message-inline">金額小計(OK):<c:out value="${ShoppingCart.subtotal}" default="0"/> 元</span>
		   </c:when>
		   
		   <c:otherwise>
		   		<span class="pure-form-message-inline"></span>
		   </c:otherwise>
		   
		</c:choose>
	</div>
<hr>

<table class="pure-table">
    <thead>
        <tr>
            <th>#</th>
            <th>圖檔</th>
            <th>姓名</th>
            <th>暱稱</th>
            <th>格言</th>
            <th>看個人頁面</th>
        </tr>
    </thead>

    <tbody>
    	<c:forEach varStatus="stVar"  var="aOssanBean"  items="${products_DPP}" >
    	
        <tr>
            <td>${aOssanBean.pKey}</td>
            <td>
            <img width='100' 
     src='${pageContext.servletContext.contextPath}/_00_init/getImage?id=${aOssanBean.pKey}&type=MEMBEROSSAN'>
            </td>
            <td>${aOssanBean.name}</td>
            <td>${aOssanBean.nickname}</td>
<!-- 			Jay:注意這邊的用法，可以在迴圈裡用兩種變數 -->
            <td id="quote">${ossan_quote[stVar.index]}</td>
            
            <td>
             	<button class="button-small pure-button " onclick="javascript:location.href=
             	'DisplayOneProduct.do?pKey=${aOssanBean.pKey}&pageNo=${pageNo}'">個人頁面</button>
            </td>         
     
        </tr>
        </c:forEach> 
      
        <tr>
        	<td>
		        <c:if test="${pageNo > 1}">
		           <div id="pfirst">
		              <a href="<c:url value='DisplayOssanProducts?pageNo=1' />">第一頁</a>
		           </div>
		        </c:if>
        	</td>
        	<td>
		        <c:if test="${pageNo > 1}">
		           <div id="pprev">
		              <a href="<c:url value='DisplayOssanProducts?pageNo=${pageNo-1}' />">上一頁</a>
		           </div>
		        </c:if>       
        	</td>
        	<td>
	            <c:if test="${pageNo != totalPages}">
	                <div id="pnext">
	                   <a href="<c:url value='DisplayOssanProducts?pageNo=${pageNo+1}' />">下一頁</a>
	                </div>
	            </c:if>       	
        	</td>
        	<td>
	            <c:if test="${pageNo != totalPages}">
	                <div id="plast">
	                    <a href="<c:url value='DisplayOssanProducts?pageNo=${totalPages}' />">最末頁</a>
	                </div>
	            </c:if>        	
        	</td>
        	<td>
				 第${pageNo}頁 / 共${totalPages}頁
        	</td>
        	
        	<td></td>
        	
        </tr>
    </tbody>
</table>
</body>
</html>