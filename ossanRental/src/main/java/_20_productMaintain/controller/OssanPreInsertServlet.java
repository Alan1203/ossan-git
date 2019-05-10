package _20_productMaintain.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import _03_listOssans.repository.impl.OssanDaoImpl_Jdbc;
import _03_listOssans.service.OssanService;
import _03_listOssans.service.impl.OssanServiceImpl;

@WebServlet("/_20_productMaintain/OssanPreInsert.do")
public class OssanPreInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 準備新增書籍資料所需要的出版社與書籍類型的資料
//		CompanyService cs = new CompanyServiceImpl();
//		cs.setTagName("companyID");
//		String companyTag = cs.getSelectTag();
//		request.setAttribute("SelectCompanyTag", companyTag);
//		System.out.println("companyTag=" + companyTag);
		// 本類別負責讀取資料庫內Book表格內某一頁的紀錄，並能新增紀錄、修改紀錄、刪除記錄等

		OssanDaoImpl_Jdbc ossanDao = new OssanDaoImpl_Jdbc();
//		String categoryTag = bookDao.getCategoryTag();
//		request.setAttribute("SelectCategoryTag", categoryTag);
//		System.out.println("categoryTag=" + categoryTag);

		OssanService service = new OssanServiceImpl();
		request.setAttribute("baBean", service);
		RequestDispatcher rd = request.getRequestDispatcher("/_20_productMaintain/OssanInsert.jsp");
		rd.forward(request, response);
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
