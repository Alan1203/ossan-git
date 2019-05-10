package _03_listOssans.service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import _00_init.util.HibernateUtils;
import _01_register.model.MemberOssanBean;
import _03_listOssans.repository.OssanDao;
import _03_listOssans.repository.impl.OssanDaoImpl_Hibernate;
import _03_listOssans.service.OssanService;
import _05_orderProcess.model.OssanOrderDetailBean;

public class OssanServiceImplHibernate implements OssanService {
	
    OssanDao dao;
    SessionFactory factory;
    
	public OssanServiceImplHibernate() {
		this.dao = new OssanDaoImpl_Hibernate();
		factory = HibernateUtils.getSessionFactory();
	}

	@Override
	public int getTotalPages() {
		int n = 0;
		Session session = factory.getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			n = dao.getTotalPages();
			tx.commit();
		} catch (Exception ex) {
			if (tx != null)
				tx.rollback();
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		return n;
	}

	@Override
	public List<MemberOssanBean> getPageOssans() {
		List<MemberOssanBean> list = null;
		Session session = factory.getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			list = dao.getPageOssans();
			tx.commit();
		} catch (Exception ex) {
			if (tx != null)
				tx.rollback();
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
		return list;
	}

	@Override
	public int getPageNo() {
		return dao.getPageNo();
	}

	@Override
	public void setPageNo(int pageNo) {
		dao.setPageNo(pageNo);
	}

	@Override
	public int getRecordsPerPage() {
		return dao.getRecordsPerPage();
	}

	@Override
	public void setRecordsPerPage(int recordsPerPage) {
		dao.setRecordsPerPage(recordsPerPage);
	}

	@Override
	public long getRecordCounts() {
		return dao.getRecordCounts();
	}

	@Override
	public MemberOssanBean getOssan(int OssanId) {
		dao.setOssanId(OssanId);
		return dao.getOssan();
	}

	@Override
	public int updateOssan(MemberOssanBean bean, long sizeInBytes) {
		return dao.updateOssan(bean, sizeInBytes);
	}

	@Override
	public int updateOssan(MemberOssanBean bean) {
		return dao.updateOssan(bean);
	}

	@Override
	public MemberOssanBean queryOssan(int OssanId) {
		return dao.queryOssan(OssanId);
	}

	@Override
	public int deleteOssan(int no) {
		return dao.deleteOssan(no);
	}

	@Override
	public int saveOssan(MemberOssanBean bean) {
		return dao.saveOssan(bean);
	}

	@Override
	public List<OssanOrderDetailBean> getOneOssanOrders(int OssanId) {
		return dao.getOneOssanOrders(OssanId);
	}

	@Override
	public List<String> getQuoteOssan() {
		// TODO Auto-generated method stub
		return dao.getQuoteOssan();
	}

	@Override
	public boolean idExists(String id) {
		// TODO Auto-generated method stub
		return dao.idExists(id);
	}

	@Override
	public List<String> getIntroOssan() {
		// TODO Auto-generated method stub
		return dao.getIntroOssan();
	}

	@Override
	public String queryOneOssanIntro(int OssanId) {
		// TODO Auto-generated method stub
		return dao.queryOneOssanIntro(OssanId);
	}

	@Override
	public int queryOneOssanPKey(String userId) {
		// TODO Auto-generated method stub
		return dao.queryOneOssanPKey(userId);
	}

	@Override
	public int updateOssanQuoteIntro(MemberOssanBean bean) {
		// TODO Auto-generated method stub
		return dao.updateOssanQuoteIntro(bean);
	}

	@Override
	public List<MemberOssanBean> getPageOssansNorth() {
		return dao.getPageOssansNorth();
	}

	@Override
	public int getTotalPagesNorth() {
		// TODO Auto-generated method stub
		return dao.getTotalPagesNorth();
	}

	@Override
	public long getRecordCountsNorth() {
		// TODO Auto-generated method stub
		return dao.getRecordCountsNorth();
	}

	@Override
	public void setPageNoNorth(int pageNoNorth) {
		dao.setPageNoNorth(pageNoNorth);
	}

	@Override
	public List<String> getQuoteOssanNorth() {
		return dao.getQuoteOssanNorth();
	}
	
	
}
