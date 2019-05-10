package _20_productMaintain.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import _03_listOssans.service.OssanService;
import _03_listOssans.service.impl.OssanServiceImpl;

// 依照書籍的書號來刪除一本書籍的紀錄。本控制器會呼叫 BookService介面的deleteBook()方法來完成。
@WebServlet("/_20_productMaintain/OssanDelete.do")
public class OssanDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String pKey = request.getParameter("pKey");
		String name = request.getParameter("name");
		int mId = Integer.parseInt(pKey);
		OssanService service = new OssanServiceImpl();
		int n = service.deleteOssan(mId);
		if (n == 1) {
			session.setAttribute("OssanDeleteMsg", "大叔(" + name + ")刪除成功");
		} else {
			session.setAttribute("OssanDeleteMsg", "大叔(" + name + ")刪除失敗");
		}
		response.sendRedirect("DisplayMaintainProducts");
		return;

	}

}
