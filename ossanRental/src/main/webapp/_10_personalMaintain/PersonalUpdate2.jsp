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

<style>
#quote {
          width: 70%;
          height: 50px;
          border: 1px solid #ccc;
          border-radius: 5px;
          resize: none; 
          padding: 6px;
}

#intro {
          width: 70%;
          height: 400px;
          border: 1px solid #ccc;
          border-radius: 5px;
          resize: none; 
          padding: 6px;
}

</style>


<script type="text/javascript">

function setFocus()
{
     document.getElementById("quote").focus();
}

function updateBook() {
    document.forms[0].action="PersonalUpdate2.do" ;
	document.forms[0].method="POST";
	document.forms[0].submit();
}

// function addNewLines(){

// 	text = document.getElementById('intro').value;
// 	text = text.replace(/\r?\n/g, '<br>');
// 	document.getElementById('intro').value = text;
// }

</script>

</head>
<body onload="setFocus()">

<!-- 引入共同的頁首 -->  
<jsp:include page="/fragment/top.jsp" />

<div style="padding-left:50px">

<a class="pure-button pure-button-primary" href="<c:url value=
'/_10_personalMaintain/PersonalPreUpdate.do' />">個人基本資料設定</a>

<span class="pure-form-message-inline" id="afterBookInsert">${successMsg.success}${ErrMsg.Exception}</span>
<!-- 為了單人更新新加的 -->
<span class="pure-form-message-inline">${updateSuccess}</span>
<!-- 下面這條不確定是否會使用 -->
<c:remove var="successMsg" scope='session'/>
</div>

<hr>
<form class="pure-form pure-form-aligned" id="form1" name="form1" method="post" 
       action="PersonalUpdate2.do" onsubmit="addNewLines();" enctype="multipart/form-data">
    <fieldset>
    
    	<img width='100' 
     src='${pageContext.servletContext.contextPath}/_00_init/getImage?id=${OssanSessionPKey}&type=MEMBEROSSAN'>
     
     
        <div class="pure-control-group">
            <label for="quote">格言</label>
            
            <textarea name ="quote" id="quote">${quote}</textarea>
            <span class="pure-form-message-inline">${ErrMsg.errQuote}</span>
        </div>

        <div class="pure-control-group">
            <label for="intro">自我介紹</label>
           
            <textarea name ="intro" id="intro">${intro}</textarea>
            <span class="pure-form-message-inline">${ErrMsg.errIntro}</span>
        </div>

        
        <input name="pKey" type="hidden" id="pKey" value="${OssanSessionPKey}>" />
        
        <div class="pure-controls">
            <button type="submit" name="update" onclick='updateBook()'
            class="pure-button pure-button-primary">修改
            	</button>
        </div>   
        
    </fieldset>
</form>
<c:remove var="ErrMsg" scope='session'/>

</body>
</html>