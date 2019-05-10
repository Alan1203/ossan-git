<%--
	執行本網頁之前，會先執行_02_login.filter.FindUserPassword.java這個過濾器。執行過濾器目的
	在檢視請求物件是否含有帳號與密碼等資料。
	  
        本網頁 login.jsp 提供登入的畫面，讓使用者輸入帳號與密碼。輸入完畢後，按下Submit按鈕，瀏覽器
        會帳號與密碼給  <Form>標籤action屬性對應的程式: _02_login.controller.LoginServlet.java，
        由該Servlet來檢查帳號與密碼是否正確。
            
--%>
<!DOCTYPE HTML>
<html>
<head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<meta charset="UTF-8">
<title>登入</title>
<style type="text/css"></style>
<script type="text/javascript">
//由<body>的onLoad事件處理函數觸發此函數
function setFocusToUserId(){   
	 document.forms[0].userId.focus();   // 將游標放在userId欄位內
}
</script>

</head>
<body onLoad="setFocusToUserId()">
<!-- 下列敘述設定變數funcName的值為LOG，top.jsp 會用到此變數 -->
<!-- 特別注意這邊!!! -->
<c:set var="funcName" value="LOG" scope="session"/>
<c:set var="msg" value="登入" />

<!-- 引入共同的頁首 -->  
<jsp:include page="/fragment/top.jsp" />

<div id="content" style="padding-left:50px">

<span class="pure-form-message-inline">大叔登入</span>
<c:if test="${ ! empty sessionScope.timeOut }" > <!-- 表示使用逾時，重新登入 -->
	<span class="pure-form-message-inline">${sessionScope.timeOut}</span>
</c:if>
</div>
<hr>
<form class="pure-form pure-form-aligned" id="login.do" method="post" name="loginForm"
       action="<c:url value='new_login.do' />" >
    <fieldset>
        <div class="pure-control-group">
            <label for="userId">帳號</label>
            <input name= "userId" value="${requestScope.user}${param.userId}" id="userId" type="text" placeholder="Username">
            <span class="pure-form-message-inline">${ErrorMsgKey.AccountEmptyError}</span>
        </div>

        <div class="pure-control-group">
            <label for="password">密碼</label>
            <input name="pswd" value="${requestScope.password}${param.pswd}" id="password" type="password" placeholder="Password" required>
            <span class="pure-form-message-inline">${ErrorMsgKey.PasswordEmptyError}</span>
        </div>
		
		<div class="pure-control-group">
            <label for="rememberMe" class="pure-checkbox">記住密碼</label>
           			<input type="checkbox" name="rememberMe" id= "rememberMe"
           			       <c:if test='${requestScope.rememberMe==true}'>
                  					checked='checked'
               				</c:if>               				          			
           			 value="true" >
           			 <span class="pure-form-message-inline">${ErrorMsgKey.LoginError}</span>    		
        </div>
			
        <div class="pure-controls">       	        	
            <button id="submit" type="submit" name="Submit" class="pure-button pure-button-primary">提交</button>
        </div>
        

    </fieldset>
</form>


</body>
</html>