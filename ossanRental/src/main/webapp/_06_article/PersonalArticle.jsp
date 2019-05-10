<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>   
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"  %>
<!DOCTYPE html>
<html>

<head>
<script type="text/javascript">
function deleteArticle(n) {
	if (confirm("確定刪除此篇文章 ? ") ) {
		document.forms[0].action="<c:url value='modifyArticle?cmd=del&artNo=" + n +"' />";
		document.forms[0].method="POST";
		document.forms[0].submit();
		return;
	} else {
		return;
	}
}

function modifyArticle(n) {
	if (confirm("確定修改此篇文章 ? ") ) {
		document.forms[0].action="<c:url value='modifyArticle?cmd=mod&artNo=" + n +"' />";
		document.forms[0].method="POST";
		document.forms[0].submit();
		return;
	} else {
		return;
	}
}
</script>
<meta charset="UTF-8">
<title>個人文章管理</title>
  <style type="text/css"></style>
</head>
<body>

<jsp:include page="/fragment/top.jsp" />
<form>
   <input type="hidden" name="a"/>
</form>
<c:choose>
	<c:when test="${!empty article}">
		<div style="position:absolute; top:100px ; left: 800px">
			<form class="pure-form" action="<c:url value='updateArticle'/>" method="post" enctype="multipart/form-data">
			    <fieldset class="pure-group">
			       <input type="text" name="title" value="${article.title}" class="pure-input-1-2" style="width: 400px ; height: 40px ; margin-bottom: 10px" required="required">
			       <textarea  class="pure-input-1-2" name="article"  style="width: 400px ; height: 300px" required="required">${article.sArticle}</textarea>
			    </fieldset>
			    <input type="hidden" name="artNo" value="${article.artNo}">
				上傳圖片: <input class="pure-input-1-2"  style="width: 400px; margin-bottom: 10px" type="file" name="ImageFile" >
			    <button type="submit" style="width: 400px" class="pure-button pure-input-1-2 pure-button-primary">編輯完成</button>
			</form>
		</div>
	</c:when>
	<c:otherwise>
				<div style="position:absolute; top:100px ; left: 10px">
					<table class="pure-table pure-table-bordered">
						<thead>
				        	<tr>
					            <th>#</th>
					            <th>image</th>
					            <th>title</th>
					            <th>content</th>
					            <th>time</th>
					            <th>modify</th>
				        	</tr>
				    	</thead>
				    	<tbody>
					    	<c:forEach varStatus="stVar"  var="aOssanBean"  items="${products_DPP}" >
					        	<tr>
					        	
						            <td>${stVar.index + 3*pageNo_up - 2}</td>
						            <td> <img width='100' height='100'
					     src='${pageContext.servletContext.contextPath}/_00_init/getImage?id=${aOssanBean.artNo}&type=article'></td>
						            <td>${aOssanBean.title}</td>
<%-- 						            <td width="200">${aOssanBean.sArticle}</td> --%>
						            <td width="200">${aOssanBean.sArticle}</td>
						            <td width="100">${aOssanBean.updateTime}</td>
						            <td> <button type="submit" name="modify" onclick="modifyArticle(${aOssanBean.artNo})" style="width: 55px" class="pure-button pure-input-1-2 pure-button-primary">編輯</button>
						             <button type="submit" name="delete" onclick="deleteArticle(${aOssanBean.artNo})" style="width: 55px" class="pure-button pure-input-1-2 pure-button-primary">刪除</button></td>
					        	</tr>
				     		</c:forEach>
			           		<tr>
			        			<td>
							        <c:if test="${pageNo_up > 1}">
							           <div id="pfirst">
							              <a href="<c:url value='listArticle?pageNo_up=1' />">第一頁</a>
							           </div>
							        </c:if>
					        	</td>
		        				<td>
							        <c:if test="${pageNo_up > 1}">
							           <div id="pprev">
							              <a href="<c:url value='listArticle?pageNo_up=${pageNo_up-1}' />">上一頁</a>
							           </div>
							        </c:if>       
		        				</td>
		        				<td>
						            <c:if test="${pageNo_up != totalPages_up}">
						                <div id="pnext">
						                   <a href="<c:url value='listArticle?pageNo_up=${pageNo_up+1}' />">下一頁</a>
						                </div>
						            </c:if>       	
		        				</td>
		        				<td>
						            <c:if test="${pageNo_up != totalPages_up}">
						                <div id="plast">
						                    <a href="<c:url value='listArticle?pageNo_up=${totalPages_up}' />">最末頁</a>
						                </div>
						            </c:if>        	
		        				</td>
					        	<td>
									 第${pageNo_up}頁 / 共${totalPages_up}頁
					        	</td>
		        			</tr>
			    		</tbody>
					</table>
				</div>
			<div style="position:absolute; top:100px ; left: 950px">
				<form class="pure-form" action="<c:url value='updateArticle'/>" method="post" enctype="multipart/form-data">
				
				    <fieldset class="pure-group">
				       <input type="text" name="title" class="pure-input-1-2" style="width: 300px ; height: 40px ; margin-bottom: 10px"  placeholder="標題" required="required">
				       <textarea class="pure-input-1-2" name="article" style="width: 300px ; height: 300px"  placeholder="想說點什麼" required="required"></textarea>
				    </fieldset>
				
					上傳圖片: <input class="pure-input-1-2"  style="width: 300px; margin-bottom: 10px" type="file" name="ImageFile" >
				    <button type="submit" style="width: 300px" class="pure-button pure-input-1-2 pure-button-primary">發文</button>
				</form>
			</div>
		
	</c:otherwise>
</c:choose>
</body>
</html>