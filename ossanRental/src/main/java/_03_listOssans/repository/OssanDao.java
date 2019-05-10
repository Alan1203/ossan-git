package _03_listOssans.repository;

import java.util.List;
import _01_register.model.MemberOssanBean;
import _05_orderProcess.model.OssanOrderDetailBean;

public interface OssanDao {
	
	boolean idExists(String id);
	
	int getTotalPages();

	List<MemberOssanBean> getPageOssans();

	int getPageNo();

	void setPageNo(int pageNo);

	int getRecordsPerPage();

	void setRecordsPerPage(int recordsPerPage);

	// 計算紀錄總筆數
	long getRecordCounts();

	void setOssanId(int OssanId);

	MemberOssanBean getOssan();

	int updateOssan(MemberOssanBean bean, long sizeInBytes) ;

	// 修改一筆記錄
	int updateOssan(MemberOssanBean bean) ;
	
	int updateOssanQuoteIntro(MemberOssanBean bean);

	// 依OssanID來查詢單筆記錄
	MemberOssanBean queryOssan(int OssanID);

	// 依OssanID來刪除單筆記錄
	int deleteOssan(int no);

	// 新增一筆記錄
	int saveOssan(MemberOssanBean bean);

	List<OssanOrderDetailBean> getOneOssanOrders(int OssanId);

	void setSelected(String selected);

	String getSelected();

	String getCategoryTag();

	String getTagName();

	void setTagName(String tagName);
	
	//取Quote
//	Clob queryOssanQuote(int OssanID);
	
	List<String> getQuoteOssan();
	
	List<String> getIntroOssan();
	
	public MemberOssanBean checkIDPassword(String userId, String password);	
	
	public MemberOssanBean checkAdmin(String userId, String password);
	
	String queryOneOssanIntro(int OssanId);

	int queryOneOssanPKey(String userId);
	
	List<MemberOssanBean> getPageOssansNorth();
	
	int getTotalPagesNorth();
	
	long getRecordCountsNorth();
	
	void setPageNoNorth(int pageNoNorth);
	
	List<String> getQuoteOssanNorth();
}