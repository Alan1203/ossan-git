package _03_listOssans.repository.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import _00_init.util.GlobalService;
import _00_init.util.HibernateUtils;
import _01_register.model.MemberOssanBean;
import _03_listOssans.repository.OssanDao;
import _05_orderProcess.model.OssanOrderDetailBean;

// 本類別使用純JDBC的技術來存取資料庫。
// 所有SQLException都以catch區塊捕捉，然後一律再次丟出RuntimeException。
// 對SQLException而言，即使catch下來，程式依然無法正常執行，所以捕捉SQLException，再次丟出
// RuntimeException。
public class OssanDaoImpl_Hibernate implements Serializable, OssanDao {

	private static final long serialVersionUID = 1L;
	private int OssanId = 0; 	// 查詢單筆商品會用到此代號
	private int pageNo = 0;		// 存放目前顯示之頁面的編號
	private int pageNoNorth = 0;		// 存放目前顯示之頁面的編號
	private int recordsPerPage = GlobalService.RECORDS_PER_PAGE; // 預設值：每頁三筆
	private int totalPages = -1;
	private int totalPagesNorth = -1;
//	DataSource ds = null;
	
	private String tagName = "";
	String selected = "";
	
	SessionFactory factory;

	public OssanDaoImpl_Hibernate() {
		factory = HibernateUtils.getSessionFactory();
	}
	
	// 計算販售的商品總共有幾頁
	@Override
	public int getTotalPages() {
		// 注意下一列敘述的每一個型態轉換
		totalPages = (int) (Math.ceil(getRecordCounts() / (double) recordsPerPage));
		return totalPages;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MemberOssanBean> getPageOssans() {
		List<MemberOssanBean> list = new ArrayList<MemberOssanBean>();
		String hql = "FROM MemberOssanBean WHERE privilege is NULL ";
		Session session = factory.getCurrentSession();

		int startRecordNo = (pageNo - 1) * recordsPerPage;

		list = session.createQuery(hql)
					  .setFirstResult(startRecordNo)
					  .setMaxResults(recordsPerPage)
					  .list();
		return list;
	}
	
	@Override
	public int getPageNo() {
		return pageNo;
	}

	@Override
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	@Override
	public int getRecordsPerPage() {
		return recordsPerPage;
	}

	@Override
	public void setRecordsPerPage(int recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}
	
	
	
	@Override
	public boolean idExists(String id) {
		// TODO Auto-generated method stub
		return false;
	}



	@SuppressWarnings("unchecked")
	@Override
	public long getRecordCounts() {
		long count = 0; // 必須使用 long 型態
		String hql = "SELECT count(*) FROM MemberOssanBean";
		Session session = factory.getCurrentSession();
		List<Long> list = session.createQuery(hql).list();
		if (list.size() > 0) {
			count = list.get(0);
		}
		return count;
	}

	@Override
	public void setOssanId(int OssanId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MemberOssanBean getOssan() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateOssan(MemberOssanBean bean, long sizeInBytes) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateOssan(MemberOssanBean bean) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateOssanQuoteIntro(MemberOssanBean bean) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public MemberOssanBean queryOssan(int OssanID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteOssan(int no) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int saveOssan(MemberOssanBean bean) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<OssanOrderDetailBean> getOneOssanOrders(int OssanId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSelected(String selected) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSelected() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCategoryTag() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTagName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTagName(String tagName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> getQuoteOssan() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getIntroOssan() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MemberOssanBean checkIDPassword(String userId, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MemberOssanBean checkAdmin(String userId, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String queryOneOssanIntro(int OssanId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int queryOneOssanPKey(String userId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<MemberOssanBean> getPageOssansNorth() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTotalPagesNorth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getRecordCountsNorth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPageNoNorth(int pageNoNorth) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> getQuoteOssanNorth() {
		// TODO Auto-generated method stub
		return null;
	}
	
	// 查詢某一頁的商品(書籍)資料，執行本方法前，一定要先設定實例變數pageNo的初值
	
}