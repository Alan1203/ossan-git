package _06_article.controller;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import _06_article.model.Article;
import _06_article.service.ArticleService;
import _06_article.service.imp.ArticleServiceImpl;

@WebServlet("/_06_article/listArticle_admin")
// 本控制器負責讓管理員管理所有大叔的文章，將不合格的文章刪除
public class ListArticle_Admin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int pageNo_li = 1;  //li是list

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 先取出session物件
		HttpSession session = request.getSession(false);
		ArticleService service = new ArticleServiceImpl(); 
		
		String finalDecision = request.getParameter("cmd");
		//檢查finalDecision的內容
		if(finalDecision != null && finalDecision.trim().length() > 0) {
//			System.out.println(finalDecision);
			if(finalDecision.equalsIgnoreCase("del")) {
				int artNo = Integer.parseInt(request.getParameter("artNo").trim());
				service.deleteArticle(artNo);
			}
		}
	
		// 讀取瀏覽送來的 pageNo
		String pageNoStr = request.getParameter("pageNo_li");
		// 如果讀不到，代表剛進來這個頁面
		if (pageNoStr == null) {  
			pageNo_li = 1;
		}else {
			pageNo_li = Integer.parseInt(pageNoStr);
		}
		
		

		// 讀取一頁的文章資料之前，告訴service，現在要讀哪一頁
		service.setPageNo(pageNo_li);
		// service.getPageArticles()方法開始讀取一頁的文章資料
		Collection<Article> coll = service.getPageArticles();
		session.setAttribute("pageNo_li", pageNo_li);
		session.setAttribute("totalPages_li", service.getTotalPages());
		// 將讀到的一頁資料放入request物件內，成為它的屬性物件
		session.setAttribute("products_DPP", coll);
		


//		 -----------------------
//		 交由ListArticle_Admin.jsp來顯示某頁的文章資料，同時準備『第一頁』、
//		 『前一頁』、『下一頁』、『最末頁』等資料
		response.sendRedirect("ListArticle_Admin.jsp");
	}
}