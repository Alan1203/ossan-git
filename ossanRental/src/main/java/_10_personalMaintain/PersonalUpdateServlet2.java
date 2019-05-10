package _10_personalMaintain;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Clob;
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

import _00_init.util.GlobalService;
import _00_init.util.SystemUtils2018;
import _01_register.model.MemberOssanBean;
import _03_listOssans.service.OssanService;
import _03_listOssans.service.impl.OssanServiceImpl;

@WebServlet("/_10_personalMaintain/PersonalUpdate2.do")
@MultipartConfig(location = "", fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 500, maxRequestSize = 1024 * 1024 * 500 * 5)
// 更新書籍資料，邏輯上與新增書籍資料類似
public class PersonalUpdateServlet2 extends HttpServlet {
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
//		String pageNo = "1";
		Map<String, String> errorMsgs = new HashMap<String, String>();
//		Map<String, String> successMsgs = new HashMap<String, String>();
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		request.setAttribute("ErrMsg", errorMsgs);
//		session.setAttribute("successMsg", successMsgs);
		try {
			String quote = "";
			String intro = "";
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
						if (fldName.equals("quote")) {
							quote = value;
							if (value == null || quote.trim().length() == 0) {
								errorMsgs.put("errQuote", "必須輸入格言");
							} else {
								request.setAttribute("quote", quote);
							}
							
						} else if (fldName.equals("intro")) {
							intro = value;
							if (intro == null || intro.trim().length() == 0) {
								errorMsgs.put("errIntro", "必須輸入自我介紹");
							} else {
								request.setAttribute("intro", intro);
							}	
						}
					} 
				}
				
			} else {
				errorMsgs.put("errTitle", "這個位置在PersonalUpdate2的94");
			}
			
			// 如果輸入資料有錯誤
			if (!errorMsgs.isEmpty()) {
				// 轉交給原輸入資料的網頁來顯示錯誤訊息
				RequestDispatcher rd = request.getRequestDispatcher("PersonalUpdate2.jsp");
				rd.forward(request, response);
				return;
			}
			
		    //
			OssanService ossanService = new OssanServiceImpl();
			
			Clob quoteClob = GlobalService.stringToClob(quote);
			Clob introClob = GlobalService.stringToClob(intro);
						
			MemberOssanBean newBean = new MemberOssanBean(bb.getpKey(),
					quoteClob,introClob );
			
			ossanService.updateOssanQuoteIntro(newBean);
			//測試
			//JayTsao因為上面更新完後, 在把變更過後的全bean放到session裡看還有沒有誰會用到
			MemberOssanBean newBeanRetrieve = ossanService.queryOssan(bb.getpKey());
			session.setAttribute("bean",newBeanRetrieve);
			request.setAttribute("updateSuccess", "自我介紹變更成功");
			
			RequestDispatcher rd = request.getRequestDispatcher("PersonalUpdate.jsp");
			rd.forward(request, response);
			return;
	
		} catch (Exception e) {
			e.printStackTrace();
			errorMsgs.put("errDBMessage", e.getMessage());
			RequestDispatcher rd = request.getRequestDispatcher("OssanError.jsp");
			rd.forward(request, response);
		}
	}
}
