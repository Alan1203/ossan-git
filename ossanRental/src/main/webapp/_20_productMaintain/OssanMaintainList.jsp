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
	
	</style>
	
	</head>
<body>

<jsp:include page="/fragment/top.jsp" />
<div style="padding-left:50px">
<a class="pure-button pure-button-primary" href="<c:url value='/_20_productMaintain/OssanPreInsert.do?pageNo=${pageNo}' />">新增資料</a>
${ OssanDeleteMsg }
<c:remove var="OssanDeleteMsg" />
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
            <th></th>
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
            
            <td>
             	${ossan_quote[stVar.index]}
            </td>
            
            <td>
             	<button class="button-small pure-button " onclick="javascript:location.href=
             	'OssanPreUpdate.do?pKey=${aOssanBean.pKey}&pageNo=${pageNo}'">修改</button>
            </td>
            
        </tr>
        </c:forEach> 
        <tr>
        	<td>
		        <c:if test="${pageNo > 1}">
		           <div id="pfirst">
		              <a href="<c:url value='DisplayMaintainProducts?pageNo=1' />">第一頁</a>
		           </div>
		        </c:if>
        	</td>
        	<td>
		        <c:if test="${pageNo > 1}">
		           <div id="pprev">
		              <a href="<c:url value='DisplayMaintainProducts?pageNo=${pageNo-1}' />">上一頁</a>
		           </div>
		        </c:if>       
        	</td>
        	<td>
	            <c:if test="${pageNo != totalPages}">
	                <div id="pnext">
	                   <a href="<c:url value='DisplayMaintainProducts?pageNo=${pageNo+1}' />">下一頁</a>
	                </div>
	            </c:if>       	
        	</td>
        	<td>
	            <c:if test="${pageNo != totalPages}">
	                <div id="plast">
	                    <a href="<c:url value='DisplayMaintainProducts?pageNo=${totalPages}' />">最末頁</a>
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