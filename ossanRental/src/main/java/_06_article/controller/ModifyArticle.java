package _06_article.controller;


import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import _00_init.util.SystemUtils2018;
import _06_article.model.Article;
import _06_article.service.ArticleService;
import _06_article.service.imp.ArticleServiceImpl;
//本控制器負責大叔文章的刪除及編輯
@WebServlet("/_06_article/modifyArticle")
public class ModifyArticle extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8"); // 文字資料轉內碼
		HttpSession session = request.getSession();
		
		ArticleService service = new ArticleServiceImpl();
		String cmd = request.getParameter("cmd");
//		讀取要修改的文章編號
		int artNo = Integer.parseInt(request.getParameter("artNo"));
		
		//判斷是刪除還是編輯
		if(cmd.equalsIgnoreCase("del")) {
			service.deleteArticle(artNo);
			response.sendRedirect(response.encodeRedirectURL("listArticle"));
			return;
		}else if(cmd.equalsIgnoreCase("mod")) {
//			將要編輯的內容放到原本發文的留言板裡
			Article art = service.getArticle(artNo);
			art.setsArticle(SystemUtils2018.clobToString(art.getArticle()));
			session.setAttribute("article", art);
		}
		response.sendRedirect(response.encodeRedirectURL("PersonalArticle.jsp"));
	}
}
