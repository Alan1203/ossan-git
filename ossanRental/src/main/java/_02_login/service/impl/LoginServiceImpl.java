package _02_login.service.impl;
import _01_register.model.MemberOssanBean;
import _02_login.service.LoginService;
import _03_listOssans.repository.OssanDao;
import _03_listOssans.repository.impl.OssanDaoImpl_Jdbc;

// 登入時系統必須執行的checkIDPassword()功能交由 MemberDao來完成 
public class LoginServiceImpl implements LoginService {
	OssanDao  dao ;
	public LoginServiceImpl() {
		this.dao = new OssanDaoImpl_Jdbc();
	}
	// 檢查帳號與密碼是否與某位已登入會員的帳號密碼完全相同。
//	public MemberBean processLogin(String userId, String password) {
//		MemberBean mb = checkIDPassword(userId, password);
//		return mb;
//	}
	
	public MemberOssanBean checkIDPassword(String userId, String password) {
		MemberOssanBean mb = dao.checkIDPassword(userId, password);
		return mb;
	}

	@Override
	public MemberOssanBean checkAdmin(String userId, String password) {
		MemberOssanBean mb = dao.checkAdmin(userId, password);
		return mb;
	}

	@Override
	public int queryOneOssanPKey(String userId) {
		int n = dao.queryOneOssanPKey(userId);
		return n;
	}
}
