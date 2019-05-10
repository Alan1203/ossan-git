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
  
  #intro { white-space:pre-wrap }
  
  </style>
</head>

<body>

<jsp:include page="/fragment/top.jsp" />
<div style="padding-left:50px">
<a class="pure-button pure-button-primary" href="<c:url value='//_03_listOssans/DisplayOssanProducts?pageNo=${pageNo}' />">回到列表</a>

</div>
<hr>

<div class="pure-g">   
    <div class="pure-u-4-5">
		<table class="pure-table">
		
		    <thead>
		        <tr>
		            <th>#</th>
		            <th>圖檔</th>
		            <th>姓名</th>
		            <th>暱稱</th>
		            <th>加入購物車</th>	            
		        </tr>
		    </thead>
		
		    <tbody>
		    	
		    	
		        <tr>
		            <td>${aOssanBean.pKey}</td>
		            <td>
		            <img class="pure-img" 
		     src='${pageContext.servletContext.contextPath}/_00_init/getImage?id=${aOssanBean.pKey}&type=MEMBEROSSAN'>
		            </td>
		            <td>${aOssanBean.name}</td>
		            <td>${aOssanBean.nickname}</td>
 					
 					
		            <td>
	             	   <FORM  action="<c:url value='/_03_listBooks/BuyOssan.do' />" method="POST">
				                                購買數量:
				               <select name='qty'>
				                    <option value="1">1</option>
				                    <option value="2">2</option>
				                    <option value="3">3</option>
				                    <option value="4">4</option>
				                    <option value="5">5</option>
				                    <option value="6">6</option>
				                    <option value="7">7</option>
				                    <option value="8">8</option>
				                    <option value="9">9</option>
				                    <option value="10">10</option>
				               </select>
				               <!-- 這些隱藏欄位都會送到後端 -->
				               <Input type='hidden' name='pKey' value='${aOssanBean.pKey}'>
				               
				               <Input type='hidden' name='memberId' value='${aOssanBean.memberId}'>
				               <Input type='hidden' name='name' value='${aOssanBean.name}'>
				               <Input type='hidden' name='nickname' value='${aOssanBean.nickname}'>

				               <Input type='submit' value='加入購物車' 
					               <c:if test="${!(empty LoginOK && empty AdminLoginOK)}">
					               disabled
					               </c:if>
				               >
				       </FORM>
		            </td>
		        </tr>
		    </tbody>
		</table>
	</div>
</div>

<BR>

<div class="pure-g">   
    <div class="pure-u-4-5">
		<table class="pure-table">
		    <thead>
		        <tr>  
		            <th>自我介紹</th>
		        </tr>
		    </thead>
		    <tbody> 	
		        <tr>       
		            <td id="intro">${intro}</td>
		        </tr> 
		    </tbody>
		</table>
	</div>
</div>

</body>
</html>