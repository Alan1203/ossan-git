package _03_listOssans.service.impl;

import java.util.List;
import _01_register.model.MemberOssanBean;
import _03_listOssans.repository.OssanDao;
import _03_listOssans.repository.impl.OssanDaoImpl_Jdbc;
import _03_listOssans.service.OssanService;
import _05_orderProcess.model.OssanOrderDetailBean;

public class OssanServiceImpl implements OssanService {
	
    OssanDao dao;
    
	public OssanServiceImpl() {
		this.dao = new OssanDaoImpl_Jdbc();
	}

	@Override
	public int getTotalPages() {
		return dao.getTotalPages();
	}

	@Override
	public List<MemberOssanBean> getPageOssans() {
		return dao.getPageOssans();
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
