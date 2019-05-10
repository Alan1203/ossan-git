<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="UTF-8">
<title>顯示商品資訊</title>
  <style type="text/css"></style>
</head>

<body>
<jsp:include page="/fragment/top.jsp" />

<div style="padding-left:50px">

<span class="pure-form-message-inline">${AdminLoginOKString}${OssanLoginOK}${logoutMessage}${MsgOK.InsertOK}</span>
 <% // 顯示MsgOK.InsertOK後，就要立刻移除，以免每次回到首 頁都會顯示新增成功的訊息
    session.removeAttribute("MsgOK");  
	session.removeAttribute("OssanLoginOK"); 
 %>
</div>
<hr>

<div style="padding:50px">

2019/5/10   Github/Eclipse e-git <br>
<br>
1.上傳圖檔的大小限制，檔名長度限制<br>
2.上傳圖檔的解析度自動變更(版面上不需要太大的解析度)<br>
3. 評分機制排行榜<br>
<br>
1. 取消帳號 , 直接使用 email 帳戶<br>
3. 使用 FB 註冊 (直接改成 email 註冊?)<br>
1. 各種輸入條件的判斷式加入 ex: email 格式 , 身分證字號<br>
1. 會員註冊認證信<br>
1. 結帳完寄信至大叔信箱<br>
<br>
1. 文章或是大叔簡介可以直接分享到 FB 或 Line<br>
3. 報表列印功能<br>
2. 每一頁的"從新到舊" 等排序<br>
<br>
資安<br>
1. 我不是機器人, 選取圖片裡所有包含 xx 的圖<br>
2. (攻擊)同一IP位置 每秒登入或註冊上限頻率<br>
1. 憑證加密<br>
3. SQL injection<br>

<br>
<br>
<br>
<br>
<br>
2019/4/29 資料庫+首頁<br>
2019/5/1 Clob 讀取 , JSP foreach multiple index<Br>
2019/5/2 維護會員資料: 新增/ 修改/ 刪除<Br>
2019/5/3 大叔註冊功能、大叔登入登出功能<Br>
2019/5/3 增加了管理員(跟大叔區別)，可以用EDMTableReset重新建表後測試<Br>
2019/5/4 新增訂單與新建兩個表格 Ossanorders & OssanorderItems<Br>
2019/5/4 管理員 - 全部交易紀錄 - 訂單細項<Br>
2019/5/4 大叔帳號登入 - 看個人頁面(TextArea排版問題)<Br>
2019/5/5 大叔帳號登入 - 看個人交易資料(JDBC to Bean)<Br>
2019/5/5 封鎖管理員和大叔進入購物按鈕<Br>
2019/5/6 訂單細項更新欄位<Br>
2019/5/6 增加地區欄位加初始配置隨選勾選地區，完成"北部"分頁(暫停)<Br>
<Br>
<Br>
(eDMoee) BUG<Br>
檔案名稱太長會跳錯<Br>
Insert 和 維護回主畫面的狀態不同(已解)<Br>
<Br>
文章要顯示大叔的資料，只能在文章資料庫件表格? 用hibernate好像可以解決(大叔表裏面沒有文章表的外鍵<Br>
文章編號表示?<br>
<Br>
特殊問題: 只有kitty 登入異常<Br>
</div>

</body>
</html>