package _20_productMaintain.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Date;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import _00_init.util.SystemUtils2018;
import _01_register.model.MemberOssanBean;
import _03_listOssans.service.OssanService;
import _03_listOssans.service.impl.OssanServiceImpl;

@WebServlet("/_20_productMaintain/OssanUpdate.do")
@MultipartConfig(location = "", fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 500, maxRequestSize = 1024 * 1024 * 500 * 5)
// 更新書籍資料，邏輯上與新增書籍資料類似
public class OssanUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	MemberOssanBean bb ;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	} 

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (!session.isNew())  {
			bb = (MemberOssanBean) session.getAttribute("bean");
		} else {
			bb = new MemberOssanBean();
		}
		String pageNo = "1";
		Map<String, String> errorMsgs = new HashMap<String, String>();
		Map<String, String> successMsgs = new HashMap<String, String>();
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		request.setAttribute("ErrMsg", errorMsgs);
		session.setAttribute("successMsg", successMsgs);
		try {
			String memberId = "";
			String password = "";
			String name = "";

			String nickname = "";
//			String companyID = "";
			String fileName = "";
//			String category = "";
			
			String uid = "";
			String address = "";
			String email = "";
			String tel = "";
			String birthday = "";

			long sizeInBytes = 0;
			InputStream is = null;
			// request.getParts()方法傳回一個由javax.servlet.http.Part物件所組成的Collection
			// javax.servlet.http.Part: 代表上傳到Server的資料，可以是正常的表單資料(form data)，
			// 也可以上傳的檔案。
			// Part介面可以:
			// 1. 傳回欄位的名稱(<input>的name屬性)、大小、ContentType
			// 2. 每個Part的Header
			// 3. 刪除Part
			// 4. 將Part寫入硬碟
			Collection<Part> parts = request.getParts();

			//GlobalService.exploreParts(parts, request);
			if (parts != null) { // 如果這是一個上傳資料的表單
				for (Part p : parts) {
					String fldName = p.getName();

					String value = request.getParameter(fldName);
					if (p.getContentType() == null) {   // 表示 p 為一般欄位而非上傳的表單
						// 根據欄位名稱來讀取欄位的內容，然後存入適當的變數內
						//PartII	
						if (fldName.equals("memberId")) {
							memberId = value;
							if (value == null || memberId.trim().length() == 0) {
								errorMsgs.put("errMemberId", "必須輸入帳號");
							} else {
								request.setAttribute("memberId", memberId);
							}
							
						} else if (fldName.equals("password")) {
							password = value;
							if (password == null || password.trim().length() == 0) {
								errorMsgs.put("errPassword", "必須輸入密碼");
							} else {
								request.setAttribute("password", password);
							}
							
						} else if (fldName.equals("name")) {
							name  = value;
							if (name  == null || name.trim().length() == 0) {
								errorMsgs.put("errName", "必須輸入姓名");
							} else {
								request.setAttribute("name", name);
							}

						} else if (fldName.equals("nickname")) {
							nickname  = value;
							if (nickname  == null || nickname.trim().length() == 0) {
								errorMsgs.put("errNickname", "必須輸入暱稱");
							} else {
								request.setAttribute("nickname", nickname);
							}
							
							
							
						//PartIII	
						} else if (fldName.equals("uid")) {
							uid  = value;
							if (uid  == null || uid.trim().length() == 0) {
								errorMsgs.put("errUid", "必須輸入身分證字號");
							} else {
								request.setAttribute("uid", uid);
							}
							
						} else if (fldName.equals("address")) {
							address  = value;
							if (address  == null || address.trim().length() == 0) {
								errorMsgs.put("errAddress", "必須輸入地址");
							} else {
								request.setAttribute("nickname", nickname);
							}
							
						} else if (fldName.equals("tel")) {
							tel  = value;
							if (tel  == null || tel.trim().length() == 0) {
								errorMsgs.put("errTel", "必須輸入電話");
							} else {
								request.setAttribute("tel", tel);
							}
							
						} else if (fldName.equals("email")) {
							email  = value;
							if (email  == null || email.trim().length() == 0) {
								errorMsgs.put("errEmail", "必須輸入電子郵件");
							} else {
								request.setAttribute("email", email);
							}
							
						} else if (fldName.equals("birthday")) {
							 birthday = value;
							if (birthday  == null) {
								errorMsgs.put("errBirthday", "必須輸入出生日期");
							} else {
								request.setAttribute("birthday", birthday);
							}
							
							
						}

					} else {
						fileName = getFileName(p); // 此為圖片檔的檔名
						if (fileName != null && fileName.trim().length() > 0) {
							sizeInBytes = p.getSize();
							is = p.getInputStream();
						} else {
							sizeInBytes = -1;
						}
					}
				}
				
			} else {
				errorMsgs.put("errTitle", "這個位置在Insert的144");
			}
			
			// 如果輸入資料有錯誤
			if (!errorMsgs.isEmpty()) {
				// 轉交給原輸入資料的網頁來顯示錯誤訊息
				RequestDispatcher rd = request.getRequestDispatcher("OssanUpdate.jsp");
				rd.forward(request, response);
				return;
			}
			
		    //
			OssanService ossanService = new OssanServiceImpl();
			
			
			Blob blob = null;
			if (sizeInBytes != -1){
				blob = SystemUtils2018.fileToBlob(is, sizeInBytes);
			}
			Date birthday2 = Date.valueOf(birthday);
			
			MemberOssanBean newBean = new MemberOssanBean(bb.getpKey(),
					memberId, password, name, nickname,
					uid, address, tel, email, birthday2,
					blob ,fileName );
			
			ossanService.updateOssan(newBean, sizeInBytes); 
			RequestDispatcher rd = request.getRequestDispatcher("DisplayMaintainProducts?pageNo=" + pageNo);
			rd.forward(request, response);
			return;
	
		} catch (Exception e) {
			e.printStackTrace();
			errorMsgs.put("errDBMessage", e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("OssanError.jsp");
			rd.forward(request, response);
		}
	}
	private String getFileName(final Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim()
						.replace("\"", "");
			}
		}
		return null;
	}
}
