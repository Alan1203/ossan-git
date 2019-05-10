package _02_login.service;


import _01_register.model.MemberOssanBean;
// 定義進行登入時系統必須執行的功能
public interface LoginService {
	public MemberOssanBean checkAdmin(String userId, String password);
	public MemberOssanBean checkIDPassword(String userId, String password) ;
	public int queryOneOssanPKey(String userId);
}
