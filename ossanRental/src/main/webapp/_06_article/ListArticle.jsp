<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>   
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"  %>
<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>大叔故事館</title>
  <style type="text/css"></style>
</head>
<body>

<jsp:include page="/fragment/top.jsp" />
<div style="position:absolute; top:100px ; left: 20px">
	<table class="pure-table pure-table-bordered">
	    <thead>
	        <tr>
	            <th>大叔編號</th>
	            <th>image</th>
	            <th>title</th>
	            <th>content</th>
	            <th>time</th>
	        </tr>
	    </thead>
	    <tbody>
	    <c:forEach varStatus="stVar"  var="aOssanBean"  items="${products_DPP}" >
	        <tr>
	            <td>${aOssanBean.seqNo}</td>
	            <td> <img width='100' height='100'
     src='${pageContext.servletContext.contextPath}/_00_init/getImage?id=${aOssanBean.artNo}&type=article'></td>
     			<td>${aOssanBean.title}</td>
	            <td width="200">${aOssanBean.sArticle}</td>
	            <td width="100">${aOssanBean.updateTime}</td>
	        </tr>
	     </c:forEach>
	           <tr>
        	<td>
		        <c:if test="${pageNo_li > 1}">
		           <div id="pfirst">
		              <a href="<c:url value='listArticle_visitor?pageNo_li=1' />">第一頁</a>
		           </div>
		        </c:if>
        	</td>
        	<td>
		        <c:if test="${pageNo_li > 1}">
		           <div id="pprev">
		              <a href="<c:url value='listArticle_visitor?pageNo_li=${pageNo_li-1}' />">上一頁</a>
		           </div>
		        </c:if>       
        	</td>
        	<td>
	            <c:if test="${pageNo_li != totalPages_li}">
	                <div id="pnext">
	                   <a href="<c:url value='listArticle_visitor?pageNo_li=${pageNo_li+1}' />">下一頁</a>
	                </div>
	            </c:if>       	
        	</td>
        	<td>
	            <c:if test="${pageNo_li != totalPages_li}">
	                <div id="plast">
	                    <a href="<c:url value='listArticle_visitor?pageNo_li=${totalPages_li}' />">最末頁</a>
	                </div>
	            </c:if>        	
        	</td>
        	<td>
				 第${pageNo_li}頁 / 共${totalPages_li}頁
        	</td>
        </tr>
	    </tbody>
	</table>
</div>
</body>
</html>