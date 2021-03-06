package _10_personalMaintain;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import _00_init.util.GlobalService;
import _01_register.model.MemberOssanBean;
import _03_listOssans.repository.impl.OssanDaoImpl_Jdbc;



@WebServlet("/_10_personalMaintain/PersonalPreUpdate2.do")
public class PersonalPreUpdateServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
//		if (session == null) {
//		     response.sendRedirect(request.getContextPath() + "/index.jsp");
//		     return;
//		}
		Integer OssanId = (Integer) session.getAttribute("OssanSessionPKey");
		
//		if (strOssanId != null) {
//			OssanId = Integer.parseInt(strOssanId);
//		}
		
		OssanDaoImpl_Jdbc OssanDao = new OssanDaoImpl_Jdbc();
		MemberOssanBean bean = OssanDao.queryOssan(OssanId);
		String quote = GlobalService.clobToStringWindows(bean.getQuote());
		String intro = GlobalService.clobToStringWindows(bean.getIntro());
		
		request.setAttribute("quote", quote);
		request.setAttribute("intro", intro);
		
		
//		session.setAttribute("bean", bean);

//		OssanDao.setSelected(bean.getCategory());
//		String categoryTag = OssanDao.getCategoryTag();
//		request.setAttribute("SelectCategoryTag", categoryTag);
//		//System.out.println("categoryTag=" + categoryTag);
//		
//		CompanyService cs = new CompanyServiceImpl();
//		cs.setSelected(bean.getCompanyId());
//		cs.setTagName("companyID");
//		String companyTag = cs.getSelectTag();
//		request.setAttribute("SelectCompanyTag", companyTag);
		//System.out.println("companyTag=" + companyTag);

		RequestDispatcher rd = request.getRequestDispatcher("/_10_personalMaintain/PersonalUpdate2.jsp");
		rd.forward(request, response);
		return;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
