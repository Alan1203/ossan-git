package _03_listOssans.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import _01_register.model.MemberOssanBean;
import _03_listOssans.service.OssanService;
import _03_listOssans.service.impl.OssanServiceImpl;

@WebServlet("/_03_listOssans/DisplayOneProduct.do")
// 本控制器負責進行必要的前置作業，以便Dao取回某一頁的商品資料
public class RetrieveOneProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int pageNo = 1;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 先取出session物件
//		HttpSession session = request.getSession(false);

//		// 如果session物件不存在
//		if (session == null) {
//			// 請使用者登入
//			response.sendRedirect(response.encodeRedirectURL(
//					request.getContextPath() + "/_02_login/login.jsp"));
//			return;
//		}
//		// 登入成功後，Session範圍內才會有LoginOK對應的MemberBean物件
//		MemberBean mb = (MemberBean) session.getAttribute("LoginOK");
//		// 取出使用者的memberId，後面的Cookie會用到 
//		String memberId = "nonuser";
//		String memberId = mb.getMemberId();
//		// BookService介面負責讀取資料庫內Book表格內某一頁的書籍資料，並能新增、修改、刪除
//		// 書籍資料等。
//		
		// 讀取瀏覽送來的 pageNo
		String pageNoStr = request.getParameter("pageNo");
		String pKey = request.getParameter("pKey");
		int ossanId = Integer.parseInt(pKey);

		
		OssanService service = new OssanServiceImpl(); 

		// 讀取一頁的書籍資料之前，告訴service，現在要讀哪一頁
		String intro = service.queryOneOssanIntro(ossanId);
		MemberOssanBean ob = service.queryOssan(ossanId);

		request.setAttribute("pageNo", pageNoStr);
		request.setAttribute("pKey", pKey);
		
//		request.setAttribute("totalPages", service.getTotalPages());
		// 將讀到的一頁資料放入request物件內，成為它的屬性物件
		request.setAttribute("intro", intro);
		request.setAttribute("aOssanBean", ob);
		

//		 使用Cookie來儲存目前讀取的網頁編號，Cookie的名稱為memberId + "pageNo"
//		 -----------------------
//		Cookie pnCookie = new Cookie(memberId + "pageNo", String.valueOf(pageNo));
//	    // 設定Cookie的存活期為30天
//		pnCookie.setMaxAge(30 * 24 * 60 * 60);
//	    // 設定Cookie的路徑為 Context Path		
//		pnCookie.setPath(request.getContextPath());
//		// 將Cookie加入回應物件內
//		response.addCookie(pnCookie);
//		 -----------------------
//		 交由listBooks.jsp來顯示某頁的書籍資料，同時準備『第一頁』、
//		 『前一頁』、『下一頁』、『最末頁』等資料
		RequestDispatcher rd = request.getRequestDispatcher("listOneOssan.jsp");
		rd.forward(request, response);
		return;

	}
}