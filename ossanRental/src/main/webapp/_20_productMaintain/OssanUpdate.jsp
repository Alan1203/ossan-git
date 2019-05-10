<%@page import="_03_listOssans.model.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
               "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${AppName}</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/pure-min.css" type="text/css" />
<script type="text/javascript">

function setFocus()
{
     document.getElementById("memberId").focus();
}

function updateBook() {
    document.forms[0].action="OssanUpdate.do?pageNo=${param.pageNo}" ;
	document.forms[0].method="POST";
	document.forms[0].submit();
}

function confirmDelete() {
	if (confirm("確定刪除此項大叔資料(編號:${param.pKey}  姓名:${bean.name})?") ) {
		document.forms[0].action="OssanDelete.do?pageNo=${param.pageNo}&pKey=${param.pKey}&name=${bean.name}" ;
		document.forms[0].method="POST";
		document.forms[0].submit();
	} else {
	}
}

</script>

</head>
<body onload="setFocus()">

<!-- 引入共同的頁首 -->  
<jsp:include page="/fragment/top.jsp" />

<div style="padding-left:50px">
<c:choose>
   <c:when test="${not empty param.pKey}">
  
     <c:set var="memberId" value='${bean.memberId}' />
     <c:set var="password" value='${bean.password}' />
     <c:set var="name" value='${bean.name}' />
     <c:set var="nickname" value='${bean.nickname}' />
     <c:set var="uid" value='${bean.uid}' />
     <c:set var="address" value='${bean.address}' />
     <c:set var="tel" value='${bean.tel}' />
     <c:set var="email" value='${bean.email}' />
     <c:set var="birthday" value='${bean.birthday}' />
     <c:set var="quote" value='${bean.quote}' />
     <c:set var="intro" value='${bean.intro}' />
     
   </c:when>
   <c:otherwise>
   <!-- 
     <c:out value="第二次"/>
      -->
     <c:set var="memberId" value='${bean.memberId}' />
     <c:set var="password" value='${bean.password}' />
     <c:set var="name" value='${bean.name}' />
     <c:set var="nickname" value='${bean.nickname}' />
     <c:set var="uid" value='${bean.uid}' />
     <c:set var="address" value='${bean.address}' />
     <c:set var="tel" value='${bean.tel}' />
     <c:set var="email" value='${bean.email}' />
     <c:set var="birthday" value='${bean.birthday}' />
     <c:set var="quote" value='${bean.quote}' />
     <c:set var="intro" value='${bean.intro}' />
   </c:otherwise>
</c:choose>


<a class="pure-button pure-button-primary" href="<c:url value=
'DisplayMaintainProducts?pageNo=${param.pageNo}' />">回清單管理</a>


        
<span class="pure-form-message-inline" id="afterBookInsert">${successMsg.success}${ErrMsg.Exception}</span>
<c:remove var="successMsg" scope='session'/>
</div>

<hr>
<form class="pure-form pure-form-aligned" id="form1" name="form1" method="post" 
       action="OssanUpdate.do"  enctype="multipart/form-data">
    <fieldset>
    
    	<img width='100' 
     src='${pageContext.servletContext.contextPath}/_00_init/getImage?id=${param.pKey}&type=MEMBEROSSAN'>
     
     
        <div class="pure-control-group">
            <label for="memberId">帳號</label>
            <input name= "memberId" value="${memberId}" id="memberId" type="text">
            <span class="pure-form-message-inline">${ErrMsg.errMemberId}</span>
        </div>

        <div class="pure-control-group">
            <label for="password">密碼</label>
            <input name="password" value="${password}" id="password" type="password">
            <span class="pure-form-message-inline">${ErrMsg.errPassword}</span>
        </div>

        <div class="pure-control-group">
            <label for="name">姓名</label>
            <input name="name" value="${name}" id="name" type="text">
            <span class="pure-form-message-inline">${ErrMsg.errName}</span>
        </div>

        <div class="pure-control-group">
            <label for="nickname">暱稱</label>
            <input name ="nickname" value="${nickname}" id="nickname" type="text">
            <span class="pure-form-message-inline">${ErrMsg.errNickname}</span>
        </div>
        
        
<!--         PARTIII -->
		<div class="pure-control-group">
            <label for="uid">身分證字號</label>
            <input name ="uid" value="${uid}" id="uid" type="text" >
            <span class="pure-form-message-inline">${ErrMsg.errUid}</span>
        </div>
        
        <div class="pure-control-group">
            <label for="address">地址</label>
            <input name ="address" value="${address}" id="address" type="text" >
            <span class="pure-form-message-inline">${ErrMsg.errAddress}</span>
        </div>
        
        <div class="pure-control-group">
            <label for="tel">連絡電話</label>
            <input name ="tel" value="${tel}" id="tel" type="text">
            <span class="pure-form-message-inline">${ErrMsg.errTel}</span>
        </div>
        
        <div class="pure-control-group">
            <label for="email">電子郵件</label>
            <input name ="email" value="${email}" id="email" type="text" >
            <span class="pure-form-message-inline">${ErrMsg.errEmail}</span>
        </div>      
        
        
        <div class="pure-control-group">
        <label for="birthday">出生日期</label>
        <input type="date" id="start" name="birthday" value="${birthday}"
      			 min="1950-01-01" max="2000-12-31">  
        </div>  
        
        <div class="pure-control-group">
        <label for="uploadFile">上傳照片</label>
        <input type="file" name="uploadFile"> 
		</div>

     
    
        <input name="pKey" type="hidden" id="pKey" value="${param.pKey}>" />
        
        		<div class="pure-controls">
            <button type="submit" name="update" onclick='updateBook()'
            class="pure-button pure-button-primary">修改
            	</button>
        </div>
       
		<div class="pure-controls">
            <button type="submit" name="delete" onclick='confirmDelete()'
            class="pure-button pure-button-primary">刪除
            </button>
        </div>         
        
    </fieldset>
</form>
<c:remove var="ErrMsg" scope='session'/>

</body>
</html>