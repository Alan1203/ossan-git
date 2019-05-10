<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${AppName}</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/pure-min.css" type="text/css" />

<script type="text/javascript">
function setFocus(fld) {
     document.getElementById(fld).focus();
}
</script>

</head>
<body onload="setFocus('title')">

<!-- 引入共同的頁首 -->  
<jsp:include page="/fragment/top.jsp" />

<!-- 這裡的跳轉還有問題 -->
<div style="padding-left:50px">
<a class="pure-button pure-button-primary" href="<c:url value='DisplayMaintainProducts?pageNo=${param.pageNo}' />">回清單管理</a>
<span class="pure-form-message-inline" id="afterBookInsert">${successMsg.success}${ErrMsg.Exception}</span>
<c:remove var="successMsg" scope='session'/>
</div>

<hr>
<form class="pure-form pure-form-aligned" id="form1" name="form1" method="post" 
       action="OssanInsert.do"  enctype="multipart/form-data">
    <fieldset>
        <div class="pure-control-group">
            <label for="memberId">帳號</label>
            <input name= "memberId" value="${requestScope.memberId}" id="memberId" type="text" placeholder="Username">
            <span class="pure-form-message-inline">${ErrMsg.errMemberId}</span>
        </div>

        <div class="pure-control-group">
            <label for="password">密碼</label>
            <input name="password" value="${requestScope.password}" id="password" type="password" placeholder="Password" required>
            <span class="pure-form-message-inline">${ErrMsg.errPassword}</span>
        </div>

        <div class="pure-control-group">
            <label for="name">姓名</label>
            <input name="name" value="${requestScope.name}" id="name" type="text" placeholder="Your Name">
            <span class="pure-form-message-inline">${ErrMsg.errName}</span>
        </div>

        <div class="pure-control-group">
            <label for="nickname">暱稱</label>
            <input name ="nickname" value="${requestScope.nickname}" id="nickname" type="text" placeholder="A.K.A">
            <span class="pure-form-message-inline">${ErrMsg.errNickname}</span>
        </div>
        
        
<!--         PARTIII -->
		<div class="pure-control-group">
            <label for="uid">身分證字號</label>
            <input name ="uid" value="${requestScope.uid}" id="uid" type="text" placeholder="A123456789">
            <span class="pure-form-message-inline">${ErrMsg.errUid}</span>
        </div>
        
        <div class="pure-control-group">
            <label for="address">地址</label>
            <input name ="address" value="${requestScope.address}" id="address" type="text" placeholder="Somewhere">
            <span class="pure-form-message-inline">${ErrMsg.errAddress}</span>
        </div>
        
        <div class="pure-control-group">
            <label for="tel">連絡電話</label>
            <input name ="tel" value="${requestScope.tel}" id="tel" type="text" placeholder="09XX-XXXXXX">
            <span class="pure-form-message-inline">${ErrMsg.errTel}</span>
        </div>
        
        <div class="pure-control-group">
            <label for="email">電子郵件</label>
            <input name ="email" value="${requestScope.email}" id="email" type="text" placeholder="example@gmail.com">
            <span class="pure-form-message-inline">${ErrMsg.errEmail}</span>
        </div>      
        
        
        <div class="pure-control-group">
        <label for="birthday">出生日期</label>
        <input type="date" id="start" name="birthday"
      			 min="1950-01-01" max="2000-12-31">  
        </div>  
        
        <div class="pure-control-group">
        <label for="uploadFile">上傳照片</label>
        <input type="file" name="uploadFile" required> 
		</div>

        <div class="pure-controls">
            <button type="submit" name="Submit" class="pure-button pure-button-primary">新增</button>
            ${ErrMsg.errPicture}
        </div>
        
    </fieldset>
</form>
<c:remove var="ErrMsg" scope='session'/>
</body>
</html>