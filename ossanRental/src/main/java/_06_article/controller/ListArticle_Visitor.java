package _06_article.controller;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import _06_article.model.Article;
import _06_article.service.ArticleService;
import _06_article.service.imp.ArticleServiceImpl;

@WebServlet("/_06_article/listArticle_visitor")
// 本控制器負責顯示所有文章給瀏覽者看
public class ListArticle_Visitor extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int pageNo_li = 1;  //li是list

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ArticleService service = new ArticleServiceImpl(); 

		String pageNoStr = request.getParameter("pageNo_li");

		if (pageNoStr == null) {  
			pageNo_li = 1;
		}else {
			pageNo_li = Integer.parseInt(pageNoStr);
		}
		
		// 讀取一頁的文章資料之前，告訴service，現在要讀哪一頁
		service.setPageNo(pageNo_li);
		// service.getPageArticles()方法開始讀取一頁的文章
		Collection<Article> coll = service.getPageArticles();
		request.setAttribute("pageNo_li", pageNo_li);
		request.setAttribute("totalPages_li", service.getTotalPages());
		// 將讀到的一頁資料放入request物件內，成為它的屬性物件
		request.setAttribute("products_DPP", coll);
		
//		 -----------------------

//		 『前一頁』、『下一頁』、『最末頁』等資料
		RequestDispatcher rd = request.getRequestDispatcher("ListArticle.jsp");
		rd.forward(request, response);
		return;

	}
}