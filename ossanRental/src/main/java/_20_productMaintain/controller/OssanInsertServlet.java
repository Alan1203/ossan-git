package _20_productMaintain.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
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

import _00_init.util.GlobalService;
import _00_init.util.SystemUtils2018;
import _01_register.model.MemberOssanBean;
import _03_listOssans.service.OssanService;
import _03_listOssans.service.impl.OssanServiceImpl;

@WebServlet("/_20_productMaintain/OssanInsert.do")
//
// 啟動檔案上傳的功能：
// 1. <form>標籤的 method屬性必須是"post", 而且
// enctype屬性必須是"multipart/form-data"
// 注意：enctype屬性的預設值為"application/x-www-form-urlencoded"
// 2. 定義可以挑選上傳檔案的表單欄位：
// <input type='file' name='user-defined_name' />
// 註: HTTP multipart request: 由Http客戶端(如瀏覽器)所建構的ㄧ種請求，用來
// 同時上傳表單資料與檔案。
//
// what-should-a-multipart-http-request-with-multiple-files-look-like?
// http://stackoverflow.com/questions/913626/what-should-a-multipart-http-request-with-multiple-files-look-like

// 在Servlet 3.0中，若要能夠處理瀏覽器送來的multipart request, Servlet必須
// 以註釋『javax.servlet.annotation.MultipartConfig』來宣告。
//
// MultipartConfig的屬性說明:
// location: 上傳之表單資料與檔案暫時存放在Server端之路徑。此路徑必須存在。
// fileSizeThreshold: 檔案的大小臨界值，超過此臨界值，上傳檔案會用存放在硬碟，
// 否則存放在主記憶體。
// maxFileSize: 上傳單一檔案之長度限制，如果超過此數值，Container會丟出例外
// maxRequestSize: 上傳所有檔案的總長度限制，如果超過此數值，Container會丟出例外
@MultipartConfig(location = "", fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 500, maxRequestSize = 1024
		* 1024 * 500 * 5)

public class OssanInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Map<String, String> errorMsgs = new HashMap<String, String>();
		Map<String, String> successMsgs = new HashMap<String, String>();
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
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

					} else {  // 表示此份資料是上傳的檔案
						fileName = GlobalService.getFileName(p); // 由變數 p 中取出檔案名稱
						fileName = GlobalService.adjustFileName(fileName, GlobalService.IMAGE_FILENAME_LENGTH);
						if (fileName != null && fileName.trim().length() > 0) {
							sizeInBytes = p.getSize();
							is = p.getInputStream();
						} else {
							errorMsgs.put("errPicture", "必須挑選圖片檔");
						}
					}
				}
				
			} else {
				errorMsgs.put("errTitle", "這個位置在Insert的144");
			}
			
			// 如果輸入資料有錯誤
			if (!errorMsgs.isEmpty()) {
				// 轉交給原輸入資料的網頁來顯示錯誤訊息
				RequestDispatcher rd = request.getRequestDispatcher("OssanInsert.jsp");
				rd.forward(request, response);
				return;
			}
			
		//	int cId = Integer.parseInt(companyID);
			// 將上傳的檔案轉換為 Blob 物件
			Blob blob = SystemUtils2018.fileToBlob(is, sizeInBytes);

			OssanService ossanService = new OssanServiceImpl();
			
//			CompanyServiceImpl cbDao = new CompanyServiceImpl();
//			cbDao.setId(cId);
//			CompanyBean cb = cbDao.getCompanyById();
			
			Date birthday2 = Date.valueOf(birthday);
			
			
			MemberOssanBean mob = new MemberOssanBean(
					memberId, password, name, nickname,
					uid, address, tel, email, birthday2,
					blob ,fileName );

			ossanService.saveOssan(mob);
			successMsgs.put("success", "資料新增成功");
            // 新增成功，通知瀏覽器對新網址發出請求
			response.sendRedirect(response.encodeRedirectURL("OssanInsert.jsp"));
			return;
		} catch (Exception e) {
			e.printStackTrace(); 
			errorMsgs.put("Exception", e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("OssanInsert.jsp");
			rd.forward(request, response);
		}
	}

	public String getFileName(final Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}
	// 本方法於主控台印出 parts 內的重要資訊，純粹為觀察用，沒有功能上的用途。
	public void exploreParts(Collection<Part> parts, HttpServletRequest req) {
		try {
			for (Part part : parts) {
				String name = part.getName();
				String contentType = part.getContentType();
				String value = "";
				long size = part.getSize(); // 上傳資料的大小，即上傳資料的位元組數
				try (InputStream is = part.getInputStream();) {
					if (contentType != null) { // 表示該part為檔案
						// 取出上傳檔案的檔名
						String filename = getFileName(part);
						// 將上傳的檔案寫入到location屬性所指定的資料夾
						part.write(filename);
					} else { // 表示該part為一般的欄位
						// 將上傳的欄位資料寫入到location屬性所指定的資料夾，檔名為"part_"+ name
						part.write("part_" + name);
						value = req.getParameter(name);
					}
					System.out.printf("%-15s %-15s %8d  %-20s \n", name, contentType, size, value);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}