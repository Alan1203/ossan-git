package _06_article.controller;

import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;

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
import _06_article.model.Article;
import _06_article.service.ArticleService;
import _06_article.service.imp.ArticleServiceImpl;
//要用HTTP multipart request 就必須設定下面這行
@MultipartConfig(location = "", fileSizeThreshold = 5 * 1024 * 1024, maxFileSize = 1024 * 1024
* 500, maxRequestSize = 1024 * 1024 * 500 * 5)
@WebServlet("/_06_article/updateArticle")
//此控制器用來處理大叔進行發文及編輯文章
public class UpdateArticle extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8"); // 文字資料轉內碼
		// session物件來存放共用資料。
		HttpSession session = request.getSession();
		ArticleService service = new ArticleServiceImpl();
		
		MemberOssanBean mob = (MemberOssanBean) session.getAttribute("LoginOK");
//		讀取是哪位大叔發文的
		String seqNo = String.valueOf(mob.getpKey());
//		要編輯文章才會用到文章編號
		String artNo = request.getParameter("artNo");
		String title = "";
		String article = "";
		String fileName = "";
		long sizeInBytes = 0;
		InputStream is = null;
		// 取出HTTP multipart request內所有的parts
		Collection<Part> arc_Parts = request.getParts();
		GlobalService.exploreParts(arc_Parts, request);
		// 由parts != null來判斷此上傳資料是否為HTTP multipart request
		if (arc_Parts != null) { // 如果這是一個上傳資料的表單
			for (Part p : arc_Parts) {
				String fldName = p.getName();
				String value = request.getParameter(fldName);

				// 1. 讀取使用者輸入資料
				if (p.getContentType() == null) {
					if (fldName.equals("title")) {
						title = value;
					} else if (fldName.equals("article")) {
						article = value;
					} 
				} else {
					// 取出圖片檔的檔名
					fileName = GlobalService.getFileName(p);
					// 調整圖片檔檔名的長度，需要檔名中的附檔名，所以調整主檔名以免檔名太長無法寫入表格
					fileName = GlobalService.adjustFileName(fileName, GlobalService.IMAGE_FILENAME_LENGTH);
//					發新文章有上傳圖片跑這行
					if (fileName != null && fileName.trim().length() > 0 && artNo == null) {
						sizeInBytes = p.getSize();
						is = p.getInputStream();
//					編輯舊文章要更新圖片跑這行
					}else if(fileName != null && fileName.trim().length() > 0) {
//						System.out.println(fileName);
						sizeInBytes = p.getSize();
						is = p.getInputStream();
						try {
							Blob blob = SystemUtils2018.fileToBlob(is, sizeInBytes);
							Article artUpdate = new Article() ;
							artUpdate.setsArticle(article);
							artUpdate.setArtNo(Integer.parseInt(artNo));
							artUpdate.setTitle(title);
							artUpdate.setArticleImage(blob);
							service.updateArticle(artUpdate, sizeInBytes);
							session.setAttribute("article", null);
							response.sendRedirect(response.encodeRedirectURL("listArticle"));
							return;
						} catch (SQLException e) {
							e.printStackTrace();
						}
//					編輯舊文章不更新圖片跑這行
					}else if(artNo != null && artNo.trim().length() > 0){
						Article artUpdate = new Article() ;
						artUpdate.setsArticle(article);
						artUpdate.setArtNo(Integer.parseInt(artNo));
						artUpdate.setTitle(title);
						service.updateArticle(artUpdate);
						session.setAttribute("article", null);
						response.sendRedirect(response.encodeRedirectURL("listArticle"));
						return ;
//					發新文章不上傳圖片跑這行
					}else {
						File f = new File("C:\\_JSP\\eclipse-workspaceJDBC\\ossanRental\\data\\images\\null.jpg");
						sizeInBytes = f.length();
						is = new FileInputStream(f);
					}
				}
			}

		try {
		
				Timestamp ts = new java.sql.Timestamp(System.currentTimeMillis());
				Blob blob = SystemUtils2018.fileToBlob(is, sizeInBytes);
				
				Clob clob = SystemUtils2018.stringToClob(article);
				// 將所有文章資料封裝到Article物件
				Article art = new Article(title, ts, blob, clob, seqNo, fileName, article);

				// 呼叫ArticleDao的saveArticle方法
				int n = service.saveArticle(art);
				if (n == 1) {
					response.sendRedirect("listArticle");
					return;
				} 
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}