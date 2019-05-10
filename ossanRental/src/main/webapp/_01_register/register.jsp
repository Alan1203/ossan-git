<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- git test -->  
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>加入會員</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/pure-min.css" type="text/css" />

<script type="text/javascript">
function setFocusToUserId(){   
	 document.forms[0].memberId.focus();   // 將游標放在mid欄位內
}
</script>

</head>
<body onload="setFocusToUserId()">
<c:set var="funcName" value="REG" scope="session"/>

<!-- 引入共同的頁首 -->  
<jsp:include page="/fragment/top.jsp" />


<div id="content" style="padding-left:50px">

<span class="pure-form-message-inline">加入會員</span>
<span class="pure-form-message-inline"> ${MsgMap.InsertNG}${MsgMap.errorSaveData}${MsgMap.passwordError}</span>

</div>

<hr>
<form class="pure-form pure-form-aligned" id="register.do" method="post" 
       action="<c:url value='register.do' />"  enctype="multipart/form-data">
    <fieldset>
        <div class="pure-control-group">
            <label for="memberId">帳號</label>
            <input name= "memberId" value="${param.memberId}" id="memberId" type="text" placeholder="Username">
            <span class="pure-form-message-inline">${MsgMap.errorIDEmpty}${MsgMap.errorIDDup}</span>
        </div>

        <div class="pure-control-group">
            <label for="password">密碼</label>
            <input name="password" value="${param.password}" id="password" type="password" placeholder="Password" required>
            <span class="pure-form-message-inline">${MsgMap.errorPasswordEmpty}</span>
        </div>
        
        <div class="pure-control-group">
            <label for="password">密碼確認</label>
            <input name="password2" value="${param.password2}" id="password2" type="password" placeholder="Password" required>
            <span class="pure-form-message-inline">${MsgMap.errorPassword2Empty}</span>
        </div>        

        <div class="pure-control-group">
            <label for="name">姓名</label>
            <input name="name" value="${param.name}" id="name" type="text" placeholder="Your Name">
            <span class="pure-form-message-inline">${MsgMap.errorName}</span>
        </div>

        <div class="pure-control-group">
            <label for="nickname">暱稱</label>
            <input name ="nickname" value="${param.nickname}" id="nickname" type="text" placeholder="A.K.A">
            <span class="pure-form-message-inline">${MsgMap.errorNickname}</span>
        </div>
        
        
<!--         PARTIII -->
		<div class="pure-control-group">
            <label for="uid">身分證字號</label>
            <input name ="uid" value="${param.uid}" id="uid" type="text" placeholder="A123456789">
            <span class="pure-form-message-inline">${MsgMap.errorUid}</span>
        </div>
        
        <div class="pure-control-group">
            <label for="address">地址</label>
            <input name ="address" value="${param.address}" id="address" type="text" placeholder="Somewhere">
            <span class="pure-form-message-inline">${MsgMap.errorAddr}</span>
        </div>
        
        <div class="pure-control-group">
            <label for="tel">連絡電話</label>
            <input name ="tel" value="${param.tel}" id="tel" type="text" placeholder="09XX-XXXXXX">
            <span class="pure-form-message-inline">${MsgMap.errorTel}</span>
        </div>
        
        <div class="pure-control-group">
            <label for="email">電子郵件</label>
            <input name ="email" value="${param.email}" id="email" type="text" placeholder="example@gmail.com">
            <span class="pure-form-message-inline">${MsgMap.errorEmail}</span>
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
            <button id="submit" type="submit" name="Submit" class="pure-button pure-button-primary">確認送出</button>
        </div>
        
        <div class="pure-controls">
            <button id="cancel" type="reset" name="cancel" class="pure-button pure-button-primary">重填</button>
        </div>
        
    </fieldset>
</form>
</body>
</html>