<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/pure-min.css" type="text/css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/grids-responsive-min.css" type="text/css" />

<!-- <script -->
<%-- 	src="${pageContext.request.contextPath}/javascript/jquery-1.9.1.js"></script> --%>
<%-- <c:set var='debug' value='true' scope='application' /> --%>
<hr>
<table>
	<c:if test="${empty LoginOK && empty AdminLoginOK}">
	<tr height="24px" >
		<td width="128px"></td>		
		<td>
			<div class="pure-menu pure-menu-horizontal">
				 <ul class="pure-menu-list">
				 	 <li class="pure-menu-item pure-menu-selected"><a href="<c:url value='/_03_listOssans/DisplayOssanProducts' />" class="pure-menu-link">首頁</a></li>
				    				     <li class="pure-menu-item"><a href="<c:url value='/_06_article/listArticle_visitor' />" class="pure-menu-link">大叔故事</a></li>
				     <li class="pure-menu-item pure-menu-selected"><a href="<c:url value='/AboutUs.jsp' />" class="pure-menu-link">網站理念</a></li>
				     <li class="pure-menu-item"><a href="#" class="pure-menu-link">聯絡我們</a></li>
				     <li class="pure-menu-item pure-menu-selected"><a href="<c:url value='/_04_ShoppingCart/ShowCartContent.jsp' />" class="pure-menu-link">購物結帳</a></li>
				     <li class="pure-menu-item pure-menu-selected"><a href="<c:url value='/_01_register/register.jsp' />" class="pure-menu-link">大叔註冊</a></li>
				     <li class="pure-menu-item pure-menu-selected"><a href="<c:url value='/_02_login/login.jsp' />" class="pure-menu-link">大叔登入</a></li>
				     <li class="pure-menu-item pure-menu-selected" ><small>Session ID → ${pageContext.session.id }</small></li>
			     
				</ul>
			</div>
		</td>		
	</tr>
	</c:if>	
	
	<c:if test="${!empty LoginOK}">
	<tr height="24px" >
		<td width="128px"></td>		
		<td>
			<div class="pure-menu pure-menu-horizontal">
				 <ul class="pure-menu-list">
				 	 <li class="pure-menu-item pure-menu-selected"><a href="<c:url value='/_03_listOssans/DisplayOssanProducts' />" class="pure-menu-link">首頁</a></li>
				     <li class="pure-menu-item"><a href="<c:url value='/_06_article/listArticle_visitor' />" class="pure-menu-link">大叔故事</a></li>
				     <li class="pure-menu-item pure-menu-selected"><a href="<c:url value='/AboutUs.jsp' />" class="pure-menu-link">網站理念</a></li>
				     <li class="pure-menu-item"><a href="#" class="pure-menu-link">聯絡我們</a></li>
				 	 <li class="pure-menu-item"><a href="<c:url value='/_06_article/listArticle' />" class="pure-menu-link">個人故事管理</a></li>
				     <li class="pure-menu-item pure-menu-selected"><a href="<c:url value='/_03_listOssans/DisplayOneProduct.do?pKey=${sessionScope.OssanSessionPKey}' />" class="pure-menu-link">看個人頁面</a></li>
				     <li class="pure-menu-item pure-menu-selected"><a href="<c:url value='/_10_personalMaintain/PersonalPreUpdate.do' />" class="pure-menu-link">個人資料維護</a></li>
				     <li class="pure-menu-item pure-menu-selected"><a href="<c:url value='/_05_orderProcess/oneOssanOrderList.do' />" class="pure-menu-link">個人交易紀錄</a></li>
				     <li class="pure-menu-item pure-menu-selected"><a href="<c:url value='/_02_login/logout.jsp' />" class="pure-menu-link">大叔登出</a></li>
				     <li class="pure-menu-item"><small>大叔用頁面</small></li>
				     <li class="pure-menu-item pure-menu-selected"><a href="/eOssan/index.jsp" class="pure-menu-link">回Index.jsp</a></li> 		     
				</ul>
			</div>
		</td>		
	</tr>
	</c:if>

	<c:if test="${!empty AdminLoginOK}">	
	<tr height="24px" >
		<td width="128px"></td>		
		<td>
			<div class="pure-menu pure-menu-horizontal">
				 <ul class="pure-menu-list">
				 	 <li class="pure-menu-item pure-menu-selected"><a href="<c:url value='/_03_listOssans/DisplayOssanProducts' />" class="pure-menu-link">首頁</a></li>
				     <li class="pure-menu-item"><a href="<c:url value='/_06_article/listArticle_visitor' />" class="pure-menu-link">大叔故事</a></li>
			 	     <li class="pure-menu-item"><a href="<c:url value='/_06_article/listArticle_admin' />" class="pure-menu-link">全部故事管理</a></li>
				     <li class="pure-menu-item pure-menu-selected"><a href="<c:url value='/_20_productMaintain/DisplayMaintainProducts' />" class="pure-menu-link">大叔清單管理</a></li>
				     <li class="pure-menu-item"><a href="#" class="pure-menu-link">聯絡我們彙整</a></li>
				     <li class="pure-menu-item pure-menu-selected"><a href="<c:url value='/_05_orderProcess/orderList.do' />" class="pure-menu-link">全部交易紀錄</a></li>
				     <li class="pure-menu-item pure-menu-selected"><a href="<c:url value='/_02_login/logout.jsp' />" class="pure-menu-link">管理員登出</a></li>
				     <li class="pure-menu-item"><small>管理員用頁面</small></li>		 
				     <li class="pure-menu-item pure-menu-selected"><a href="/eOssan/index.jsp" class="pure-menu-link">回Index.jsp</a></li>    
				</ul>
			</div>
		</td>		
	</tr>
	</c:if>		
</table>
<hr>

