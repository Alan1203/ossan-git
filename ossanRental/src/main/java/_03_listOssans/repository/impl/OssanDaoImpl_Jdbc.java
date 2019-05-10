package _03_listOssans.repository.impl;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import _00_init.util.GlobalService;
import _01_register.model.MemberOssanBean;
import _03_listOssans.repository.OssanDao;
import _05_orderProcess.model.OssanOrderDetailBean;

// 本類別使用純JDBC的技術來存取資料庫。
// 所有SQLException都以catch區塊捕捉，然後一律再次丟出RuntimeException。
// 對SQLException而言，即使catch下來，程式依然無法正常執行，所以捕捉SQLException，再次丟出
// RuntimeException。
public class OssanDaoImpl_Jdbc implements Serializable, OssanDao {

	private static final long serialVersionUID = 1L;
	private int OssanId = 0; 	// 查詢單筆商品會用到此代號
	private int pageNo = 0;		// 存放目前顯示之頁面的編號
	private int pageNoNorth = 0;		// 存放目前顯示之頁面的編號
	private int recordsPerPage = GlobalService.RECORDS_PER_PAGE; // 預設值：每頁三筆
	private int totalPages = -1;
	private int totalPagesNorth = -1;
	DataSource ds = null;
	
	private String tagName = "";
	String selected = "";

	public OssanDaoImpl_Jdbc() {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup(GlobalService.JNDI_DB_NAME);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("OssanDaoImpl_Jdbc()#建構子發生例外: " 
										+ ex.getMessage());
		}
	}
	
	// 計算販售的商品總共有幾頁
	@Override
	public int getTotalPages() {
		// 注意下一列敘述的每一個型態轉換
		totalPages = (int) (Math.ceil(getRecordCounts() / (double) recordsPerPage));
		return totalPages;
	}
	
	// 查詢某一頁的商品(書籍)資料，執行本方法前，一定要先設定實例變數pageNo的初值
	@Override
	public List<MemberOssanBean> getPageOssans() {
		List<MemberOssanBean> list = new ArrayList<MemberOssanBean>();
		
		String sql0 = "SELECT  * FROM (SELECT  ROW_NUMBER() OVER (ORDER BY seqNo)\r\n" + 
				"AS RowNum, 			 \r\n" + 
				"seqNo,\r\n" + 
				"memberID,\r\n" + 
				"PASSWORD,\r\n" + 
				"NAME,\r\n" + 
				"nickname,\r\n" + 
				"uid,\r\n" + 
				"address ,\r\n" + 
				"tel,\r\n" + 
				"email,\r\n" + 
				"birthday,\r\n" + 
				"registerTime ,\r\n" + 
				"memberImage,\r\n" + 
				"fileName,\r\n" + 
				"QUOTE,\r\n" + 
				"intro\r\n" + 				
				"FROM memberossan WHERE privilege IS NULL)\r\n" + 
				"AS NewTable WHERE RowNum >= ? AND RowNum <= ? ";
		
		String sql = sql0;
		// 由頁碼推算出該頁是由哪一筆紀錄開始(1 based)
		int startRecordNo = (pageNo - 1) * recordsPerPage + 1;
		int endRecordNo = (pageNo) * recordsPerPage;
		// 由頁碼推算出該頁是由哪一筆紀錄開始(0 based)		
//		int startRecordNo = (pageNo - 1) * recordsPerPage;
//		int endRecordNo = recordsPerPage;
		try (
			Connection connection = ds.getConnection(); 
			PreparedStatement ps = connection.prepareStatement(sql);
		) {
			ps.setInt(1, startRecordNo);
			ps.setInt(2, endRecordNo);
			try (
				ResultSet rs = ps.executeQuery();
			) {
				// 只要還有紀錄未取出，rs.next()會傳回true
				// 迴圈內將逐筆取出ResultSet內的紀錄
				while (rs.next()) {
					// 準備一個新的MemberOssanBean，將ResultSet內的一筆紀錄移植到MemberOssanBean內
					MemberOssanBean bean = new MemberOssanBean();    	
					bean.setpKey(rs.getInt("seqNo"));		
					bean.setMemberId(rs.getString("memberID"));
					bean.setPassword(rs.getString("password"));
					bean.setName(rs.getString("name"));
					bean.setNickname(rs.getString("nickname"));
					bean.setUid(rs.getString("uid"));
					bean.setAddress(rs.getString("address"));
					bean.setTel(rs.getString("tel"));
					bean.setEmail(rs.getString("email"));
					bean.setBirthday(rs.getDate("birthday"));
					bean.setRegisterTime(rs.getTimestamp("registerTime"));
					bean.setMemberImage(rs.getBlob("memberImage"));
					bean.setFileName(rs.getString("fileName"));
					
					bean.setQuote(rs.getClob("quote"));
					bean.setIntro(rs.getClob("intro"));
								
					// 最後將MemberOssanBean物件放入大的容器內
					list.add(bean);
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException("OssanDaoImpl_Jdbc()#getPageOssans()發生例外: " 
										+ ex.getMessage());
		}
		return list;
	}

	@Override
	public long getRecordCounts() {
		long count = 0; // 必須使用 long 型態
		String sql = "SELECT count(*) FROM MemberOssan WHERE privilege IS NULL ";
		try (
			Connection connection = ds.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
		) {
			if (rs.next()) {
				count = rs.getLong(1);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException("MemberDaoImpl_Jdbc()#getRecordCounts()發生例外: " 
										+ ex.getMessage());
		}
		return count;
	}
	
	@Override
	public List<OssanOrderDetailBean> getOneOssanOrders(int OssanId) {
		List<OssanOrderDetailBean> list = new ArrayList<OssanOrderDetailBean>();
		
		String sql = "SELECT (@row_number:=@row_number + 1) AS num,\r\n" + 
				" a.orderno,a.amount,b.totalamount,b.invoicetitle,\r\n" + 
				" b.address,b.tel,b.email, b.comment, b.orderdate\r\n" + 
				" FROM ossanorderitems a JOIN ossanorders b \r\n" + 
				" ON a.orderNo = b.orderNo ,\r\n" + 
				" (SELECT @row_number:=0) AS c\r\n" + 
				" WHERE a.ossanNo = ?";
		
		try (
			Connection connection = ds.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);	
		) {
			ps.setInt(1, OssanId);
			
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					OssanOrderDetailBean bean = new OssanOrderDetailBean();    
					
					bean.setNum(String.valueOf(rs.getInt(1)));
					bean.setOrderno(rs.getString(2));
					bean.setAmount(String.valueOf(rs.getInt(3)));
					bean.setTotalamount(String.valueOf(rs.getDouble(4)));
					bean.setInvoicetitle(rs.getString(5));
					bean.setAddress(rs.getString(6));
					bean.setTel(rs.getString(7));
					bean.setEmail(rs.getString(8));
					bean.setComment(rs.getString(9));
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//					HH:mm:ss
					String orderdate = sdf.format(rs.getDate(10));
					System.out.println(orderdate);
					bean.setOrderdate(orderdate);
					
					list.add(bean);	
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException("MemberDaoImpl_Jdbc()#OneOssanOrders()發生例外: " 
										+ ex.getMessage());
		}
		return list;
	}
	
	@Override
	public String getCategoryTag() {
		String ans = "";
//		List<String> list = getCategory();
//		ans += "<SELECT name='category'>";
//		for (String cate : list) {
//			if (cate.equals(selected)) {
//				ans += "<option value='" + cate + "' selected>" + cate + "</option>";
//			} else {
//				ans += "<option value='" + cate + "'>" + cate + "</option>";
//			}
//		}
//		ans += "</SELECT>";
		return ans;
	}
	
	// 修改一筆書籍資料
	@Override
	public int updateOssan(MemberOssanBean bean, long sizeInBytes) {
		int n = 0;
		String sql = "UPDATE MemberOssan SET "
				+ "memberID = ?, PASSWORD = ?, NAME = ? , nickname= ?, "
				+ "uid = ?, address = ? ,  tel = ?, email = ?, "
				+ "birthday = ?, memberImage = ?, fileName = ?  "
				+ "WHERE seqNo = ?";
//		System.out.println(sql);
		if (sizeInBytes == -1) { // 不修改圖片
			n = updateOssan(bean);
			return n;
		}
		try (
			Connection connection = ds.getConnection(); 
			PreparedStatement pStmt = connection.prepareStatement(sql);
		) {
			pStmt.setString(1, bean.getMemberId());
			pStmt.setString(2, bean.getPassword());
			pStmt.setString(3, bean.getName());
			pStmt.setString(4, bean.getNickname());
			
			pStmt.setString(5, bean.getUid());
			pStmt.setString(6, bean.getAddress());
			pStmt.setString(7, bean.getTel());
			pStmt.setString(8, bean.getEmail());
			pStmt.setDate(9, bean.getBirthday());
			
			//準備以後加入"最後修改時間"
//			java.sql.Timestamp now = new java.sql.Timestamp(System.currentTimeMillis());

			pStmt.setBlob(10, bean.getMemberImage());

			pStmt.setString(11, bean.getFileName());
			pStmt.setInt(12, bean.getpKey());
			pStmt.setClob(13, bean.getQuote());
			pStmt.setClob(14, bean.getIntro());
			
			n = pStmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException("MemberDaoImpl_Jdbc()#updateOssan(MemberOssanBean, long)發生例外: 更新單筆資料" 
										+ ex.getMessage());
		}
		return n;
	}

	// 修改一筆書籍資料，不改圖片
	@Override
	public int updateOssan(MemberOssanBean bean) {
		int n = 0;
		String sql = "UPDATE MemberOssan SET memberID = ?, PASSWORD = ?, NAME = ? , nickname= ?,\r\n" + 
				"				uid = ?, address = ? ,  tel = ?, email = ?, \r\n" + 
				"				birthday = ? WHERE seqNo = ?";
		try (
			Connection connection = ds.getConnection(); 
			PreparedStatement ps = connection.prepareStatement(sql);
		) {
			ps.clearParameters();
			ps.setString(1, bean.getMemberId());
			ps.setString(2, bean.getPassword());
			ps.setString(3, bean.getName());
			ps.setString(4, bean.getNickname());
			
			ps.setString(5, bean.getUid());
			ps.setString(6, bean.getAddress());
			ps.setString(7, bean.getTel());
			ps.setString(8, bean.getEmail());
			ps.setDate(9, bean.getBirthday());
			
			//準備以後加入"最後修改時間"
//			java.sql.Timestamp now = new java.sql.Timestamp(System.currentTimeMillis());

			ps.setInt(10, bean.getpKey());

			n = ps.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException("MemberDaoImpl_Jdbc()#updateOssan(MemberOssanBean)發生例外: " 
										+ ex.getMessage());
		}
		return n;
	}

	// 依OssanID來查詢單筆記錄
	@Override
	public MemberOssanBean queryOssan(int OssanId)  {
		MemberOssanBean bean = null;
		String sql = "SELECT * FROM MemberOssan WHERE seqNo = ? AND privilege IS NULL";

		try (
			Connection connection = ds.getConnection(); 
			PreparedStatement ps = connection.prepareStatement(sql);
		) {
			ps.setInt(1, OssanId);
			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next()) {
					bean = new MemberOssanBean();  	
					bean.setpKey(rs.getInt("seqNo"));		
					bean.setMemberId(rs.getString(2));
					bean.setPassword(rs.getString(3));
					bean.setName(rs.getString(4));
					bean.setNickname(rs.getString(5));
					bean.setUid(rs.getString(6));
					bean.setAddress(rs.getString(7));
					bean.setTel(rs.getString(8));
					bean.setEmail(rs.getString(9));
					bean.setBirthday(rs.getDate(10));
					bean.setRegisterTime(rs.getTimestamp(11));
					bean.setMemberImage(rs.getBlob(12));
					bean.setFileName(rs.getString(13));					
					bean.setQuote(rs.getClob(14));
					bean.setIntro(rs.getClob(15));
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException("MemberDaoImpl_Jdbc()#queryOssan()發生例外: " 
										+ ex.getMessage());
		}
		return bean;
	}

	// 依OssanID來刪除單筆記錄
	@Override
	public int deleteOssan(int no) {
		int n = 0;
		String sql = "DELETE FROM MemberOssan WHERE seqNo = ?";

		try (
			Connection connection = ds.getConnection(); 
			PreparedStatement pStmt = connection.prepareStatement(sql);
		) {
			pStmt.setInt(1, no);
			n = pStmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException("MemberDaoImpl_Jdbc()#deleteOssan()發生例外: " 
										+ ex.getMessage());
		}
		return n;
	}

	// 新增一筆記錄---
//	@Override
	public int saveOssan(MemberOssanBean bean) {
		int n = 0;
//
		String sql = "INSERT INTO MemberOssan (memberId,  PASSWORD,  NAME, nickname, uid, address, tel, email, birthday, registerTime, memberImage, fileName ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";

		try (
			Connection connection = ds.getConnection();
			PreparedStatement pStmt = connection.prepareStatement(sql);
		) {
			pStmt.setString(1, bean.getMemberId());
			pStmt.setString(2, bean.getPassword());
			pStmt.setString(3, bean.getName());
			pStmt.setString(4, bean.getNickname());
			pStmt.setString(5, bean.getUid());
			pStmt.setString(6, bean.getAddress());
			pStmt.setString(7, bean.getTel());
			pStmt.setString(8, bean.getEmail());
			pStmt.setDate(9, bean.getBirthday());
			java.sql.Timestamp now = new java.sql.Timestamp(System.currentTimeMillis());
			pStmt.setTimestamp(10, now);
			pStmt.setBlob(11, bean.getMemberImage());
//			pStmt.setInt(5, bean.getCompanyId());
			pStmt.setString(12, bean.getFileName());
//			pStmt.setString(7, bean.getOssanNo());
//			pStmt.setBlob(8, bean.getCoverImage());
//			pStmt.setInt(9, bean.getStock());
//			pStmt.setString(10, bean.getCategory());
			n = pStmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException("MemberDaoImpl_Jdbc()#saveOssan()發生例外: " 
										+ ex.getMessage());
		}
		return n;
	}

	
	
	@Override
	public String getSelected() {
		return selected;
	}
	
	@Override
	public void setSelected(String selected) {
		this.selected = selected;
	}
	
	
	@Override
	public String getTagName() {
		return tagName;
	}
	
	@Override
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	
	@Override
	public void setOssanId(int OssanId) {
		this.OssanId = OssanId;
	}

	@Override
	public MemberOssanBean getOssan() {
		MemberOssanBean bean = queryOssan(this.OssanId);
		return bean;
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
	public List<String> getQuoteOssan() {
		// TODO Auto-generated method stub
		List<String> quoteList = new ArrayList<String>();
		String sql0 = "SELECT QUOTE FROM (SELECT  ROW_NUMBER() OVER (ORDER BY seqNo)\r\n" + 
				"AS RowNum, 			 \r\n" + 
				"seqNo,\r\n" + 
				"memberID,\r\n" + 
				"PASSWORD,\r\n" + 
				"NAME,\r\n" + 
				"nickname,\r\n" + 
				"uid,\r\n" + 
				"address ,\r\n" + 
				"tel,\r\n" + 
				"email,\r\n" + 
				"birthday,\r\n" + 
				"registerTime ,\r\n" + 
				"memberImage,\r\n" + 
				"fileName,\r\n" + 
				"QUOTE,\r\n" + 
				"intro\r\n" + 
				"FROM memberossan WHERE privilege IS NULL)\r\n" + 
				"AS NewTable WHERE RowNum >= ? AND RowNum <= ?";
		
		String sql = sql0;
		// 由頁碼推算出該頁是由哪一筆紀錄開始(1 based)
		int startRecordNo = (pageNo - 1) * recordsPerPage + 1;
		int endRecordNo = (pageNo) * recordsPerPage;
		// 由頁碼推算出該頁是由哪一筆紀錄開始(0 based)		
		try (
			Connection connection = ds.getConnection(); 
			PreparedStatement ps = connection.prepareStatement(sql);
		) {
			ps.setInt(1, startRecordNo);
			ps.setInt(2, endRecordNo);
			try (
				ResultSet rs = ps.executeQuery();
			) {
				// 只要還有紀錄未取出，rs.next()會傳回true
				// 迴圈內將逐筆取出ResultSet內的紀錄
				while (rs.next()) {
					
					// 準備一個新的MemberOssanBean，將ResultSet內的一筆紀錄移植到MemberOssanBean內
					if (rs.getClob("quote") != null) {
						String quoteString = GlobalService.clobToStringWindows(rs.getClob("quote"));
						quoteList.add(quoteString);
					 } else {
						String quoteString = "無言";	
						quoteList.add(quoteString);
					 }							
					// 最後將MemberOssanBean物件放入大的容器內	
				}
			}
		} catch (SQLException ex) {
			System.out.println("Quote List發生例外");
			ex.printStackTrace();
			throw new RuntimeException("OssanDaoImpl_Jdbc()#getQuoteOssans()發生例外: " 
										+ ex.getMessage());
		}
		return quoteList;
	}

//	private String clobToString(Clob data) {
//	    StringBuilder sb = new StringBuilder();
//	    try {
//	        Reader reader = data.getCharacterStream();
//	        BufferedReader br = new BufferedReader(reader);
//
//	        String line;
//	        while(null != (line = br.readLine())) {
//	            sb.append(line);
//	        }
//	        br.close();
//	    } catch (SQLException e) {
//	    	System.out.println("ClobToString的SQL發生例外");
//	    	e.printStackTrace();
//			throw new RuntimeException("ClobToString的SQL發生例外: " 
//										+ e.getMessage());
//			
//	    } catch (IOException e) {
//	    	System.out.println("ClobToString的IO發生例外");
//	    	e.printStackTrace();
//			throw new RuntimeException("ClobToString的IO發生例外: " 
//										+ e.getMessage());
//	    }
//	    return sb.toString();
//	}

	@Override
	public boolean idExists(String id) {
		// TODO Auto-generated method stub
		boolean exist = false;
		String sql = "SELECT * FROM Memberossan WHERE memberId = ?";
		try (
			Connection connection = ds.getConnection(); 
			PreparedStatement ps = connection.prepareStatement(sql);
		) {
			ps.setString(1, id);
			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next()) {
					exist = true;
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException("MemberDaoImpl_Jdbc類別#idExists()發生例外: " 
					+ ex.getMessage());
		}
		return exist;
	
	}

	@Override
	public MemberOssanBean checkIDPassword(String userId, String password) {
		MemberOssanBean mb = null;
		String sql = "SELECT * FROM MemberOssan m WHERE m.memberId = ? and m.password = ?";
		try (
			Connection con = ds.getConnection(); 
			PreparedStatement ps = con.prepareStatement(sql);
		) {
			ps.setString(1, userId);
			ps.setString(2, password);
			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next()) {
					mb = new MemberOssanBean();
					mb.setMemberId(rs.getString("memberId"));
					mb.setPassword(rs.getString("password"));
					mb.setpKey(rs.getInt("seqNo")); //為了文章查詢
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException("MemberOssanDaoImpl_Jdbc類別#checkIDPassword()發生SQL例外: " 
					+ ex.getMessage());
		}
		return mb;
	}

	@Override
	public MemberOssanBean checkAdmin(String userId, String password) {
		// TODO Auto-generated method stub
		MemberOssanBean mb = null;
		String sql = "SELECT * FROM MemberOssan m WHERE m.privilege = \"Admin\" and m.memberId = ? and m.password = ?";
		try (
			Connection con = ds.getConnection(); 
			PreparedStatement ps = con.prepareStatement(sql);
		) {
			ps.setString(1, userId);
			ps.setString(2, password);
			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next()) {
					mb = new MemberOssanBean();
					mb.setMemberId(rs.getString("memberId"));
					mb.setPassword(rs.getString("password"));
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException("MemberOssanDaoImpl_Jdbc類別#checkIDPassword()發生SQL例外: " 
					+ ex.getMessage());
		}
		return mb;
	}

	@Override
	public List<String> getIntroOssan() {
		// TODO Auto-generated method stub
		List<String> introList = new ArrayList<String>();
		String sql0 = "SELECT Intro FROM (SELECT  ROW_NUMBER() OVER (ORDER BY seqNo)\r\n" + 
				"AS RowNum, 			 \r\n" + 
				"seqNo,\r\n" + 
				"memberID,\r\n" + 
				"PASSWORD,\r\n" + 
				"NAME,\r\n" + 
				"nickname,\r\n" + 
				"uid,\r\n" + 
				"address ,\r\n" + 
				"tel,\r\n" + 
				"email,\r\n" + 
				"birthday,\r\n" + 
				"registerTime ,\r\n" + 
				"memberImage,\r\n" + 
				"fileName,\r\n" + 
				"QUOTE,\r\n" + 
				"intro\r\n" + 
				"FROM memberossan WHERE privilege IS NULL)\r\n" + 
				"AS NewTable WHERE RowNum >= ? AND RowNum <= ?";
		
		String sql = sql0;
		// 由頁碼推算出該頁是由哪一筆紀錄開始(1 based)
		int startRecordNo = (pageNo - 1) * recordsPerPage + 1;
		int endRecordNo = (pageNo) * recordsPerPage;
		// 由頁碼推算出該頁是由哪一筆紀錄開始(0 based)		
		try (
			Connection connection = ds.getConnection(); 
			PreparedStatement ps = connection.prepareStatement(sql);
		) {
			ps.setInt(1, startRecordNo);
			ps.setInt(2, endRecordNo);
			try (
				ResultSet rs = ps.executeQuery();
			) {
				// 只要還有紀錄未取出，rs.next()會傳回true
				// 迴圈內將逐筆取出ResultSet內的紀錄
				while (rs.next()) {
					
					// 準備一個新的MemberOssanBean，將ResultSet內的一筆紀錄移植到MemberOssanBean內
					if (rs.getClob("intro") != null) {
						String introString = GlobalService.clobToStringWindows(rs.getClob("intro"));
						introList.add(introString);
					 } else {
						String introString = "沒有自我介紹";	
						introList.add(introString);
					 }							
					// 最後將MemberOssanBean物件放入大的容器內	
				}
			}
		} catch (SQLException ex) {
			System.out.println("Intro List發生例外");
			ex.printStackTrace();
			throw new RuntimeException("OssanDaoImpl_Jdbc()#getIntroOssans()發生例外: " 
										+ ex.getMessage());
		}
		return introList;
	}

	@Override
	public String queryOneOssanIntro(int OssanId) {
		// TODO Auto-generated method stub
		String intro = "";
		String sql = "SELECT intro FROM MemberOssan WHERE seqNo = ? AND privilege IS NULL";	
		try (
			Connection connection = ds.getConnection(); 
			PreparedStatement ps = connection.prepareStatement(sql);
		) {
			ps.setInt(1, OssanId);
			try (ResultSet rs = ps.executeQuery();
					) {
				while (rs.next()) {

					if (rs.getClob("intro") != null) {
						intro = GlobalService.clobToStringWindows(rs.getClob("intro"));
					 } else {
						intro = "沒有自我介紹";	
					 }			
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException("MemberDaoImpl_Jdbc()#queryOssan()發生例外: " 
										+ ex.getMessage());
		}
		return intro;
		
	}

	@Override
	public int queryOneOssanPKey(String userId) {
		// TODO Auto-generated method stub
		int n = 0;
		String sql = "SELECT seqNo FROM MemberOssan WHERE memberId = ? AND privilege IS NULL";	
		try (
			Connection connection = ds.getConnection(); 
			PreparedStatement ps = connection.prepareStatement(sql);
		) {
			ps.setString(1, userId);
			try (ResultSet rs = ps.executeQuery();
					) {
				while (rs.next()) {
					n = rs.getInt("seqNo");
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException("MemberDaoImpl_Jdbc()#queryOssan()發生例外: " 
										+ ex.getMessage());
		}	
		return n;
	}

	@Override
	public int updateOssanQuoteIntro(MemberOssanBean bean) {
		int n = 0;
		String sql = "UPDATE MemberOssan SET quote = ?, intro = ?, lastModify = ? WHERE seqNo = ?";
		try (
			Connection connection = ds.getConnection(); 
			PreparedStatement ps = connection.prepareStatement(sql);
		) {
			ps.clearParameters();
			ps.setClob(1, bean.getQuote());
			ps.setClob(2, bean.getIntro());
			
			java.sql.Timestamp now = new java.sql.Timestamp(System.currentTimeMillis());
			ps.setTimestamp(3, now);
			ps.setInt(4, bean.getpKey());

			n = ps.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException("MemberDaoImpl_Jdbc()#updateOssanQuote(MemberOssanBean)發生例外: " 
										+ ex.getMessage());
		}
		return n;
	}

	@Override
	public List<MemberOssanBean> getPageOssansNorth() {

		List<MemberOssanBean> list = new ArrayList<MemberOssanBean>();
		
		String sql0 = "SELECT  * FROM (SELECT  ROW_NUMBER() OVER (ORDER BY seqNo)\r\n" + 
				"AS RowNum, 			 \r\n" + 
				"seqNo,\r\n" + 
				"memberID,\r\n" + 
				"PASSWORD,\r\n" + 
				"NAME,\r\n" + 
				"nickname,\r\n" + 
				"uid,\r\n" + 
				"address ,\r\n" + 
				"tel,\r\n" + 
				"email,\r\n" + 
				"birthday,\r\n" + 
				"registerTime ,\r\n" + 
				"memberImage,\r\n" + 
				"fileName,\r\n" + 
				"QUOTE,\r\n" + 
				"intro\r\n" + 				
				"FROM memberossan WHERE twnorth = 1 AND privilege IS NULL)\r\n" + 
				"AS NewTable WHERE RowNum >= ? AND RowNum <= ? ";
		
		String sql = sql0;
		// 由頁碼推算出該頁是由哪一筆紀錄開始(1 based)
		int startRecordNo = (pageNoNorth - 1) * recordsPerPage + 1;
		int endRecordNo = (pageNoNorth) * recordsPerPage;
		// 由頁碼推算出該頁是由哪一筆紀錄開始(0 based)		
//		int startRecordNo = (pageNo - 1) * recordsPerPage;
//		int endRecordNo = recordsPerPage;
		try (
			Connection connection = ds.getConnection(); 
			PreparedStatement ps = connection.prepareStatement(sql);
		) {
			ps.setInt(1, startRecordNo);
			ps.setInt(2, endRecordNo);
			try (
				ResultSet rs = ps.executeQuery();
			) {
				// 只要還有紀錄未取出，rs.next()會傳回true
				// 迴圈內將逐筆取出ResultSet內的紀錄
				while (rs.next()) {
					// 準備一個新的MemberOssanBean，將ResultSet內的一筆紀錄移植到MemberOssanBean內
					MemberOssanBean bean = new MemberOssanBean();    	
					bean.setpKey(rs.getInt("seqNo"));		
					bean.setMemberId(rs.getString("memberID"));
					bean.setPassword(rs.getString("password"));
					bean.setName(rs.getString("name"));
					bean.setNickname(rs.getString("nickname"));
					bean.setUid(rs.getString("uid"));
					bean.setAddress(rs.getString("address"));
					bean.setTel(rs.getString("tel"));
					bean.setEmail(rs.getString("email"));
					bean.setBirthday(rs.getDate("birthday"));
					bean.setRegisterTime(rs.getTimestamp("registerTime"));
					bean.setMemberImage(rs.getBlob("memberImage"));
					bean.setFileName(rs.getString("fileName"));
					
					bean.setQuote(rs.getClob("quote"));
					bean.setIntro(rs.getClob("intro"));
								
					// 最後將MemberOssanBean物件放入大的容器內
					list.add(bean);
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException("OssanDaoImpl_Jdbc()#getPageOssans()發生例外: " 
										+ ex.getMessage());
		}
		return list;
	
	}

	@Override
	public int getTotalPagesNorth() {
		// 注意下一列敘述的每一個型態轉換
		totalPagesNorth = (int) (Math.ceil(getRecordCountsNorth() / (double) recordsPerPage));
		return totalPagesNorth;
	}

	@Override
	public long getRecordCountsNorth() {
		long count = 0; // 必須使用 long 型態
		String sql = "SELECT count(*) FROM MemberOssan WHERE twnorth = 1 AND privilege IS NULL ";
		try (
			Connection connection = ds.getConnection();
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
		) {
			if (rs.next()) {
				count = rs.getLong(1);
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException("MemberDaoImpl_Jdbc()#getRecordCounts()發生例外: " 
										+ ex.getMessage());
		}
		return count;
	}

	@Override
	public void setPageNoNorth(int pageNoNorth) {
		this.pageNoNorth = pageNoNorth;	
	}

	@Override
	public List<String> getQuoteOssanNorth() {
		// TODO Auto-generated method stub
		List<String> quoteList = new ArrayList<String>();
		String sql0 = "SELECT QUOTE FROM (SELECT  ROW_NUMBER() OVER (ORDER BY seqNo)\r\n" + 
				"AS RowNum, 			 \r\n" + 
				"seqNo,\r\n" + 
				"memberID,\r\n" + 
				"PASSWORD,\r\n" + 
				"NAME,\r\n" + 
				"nickname,\r\n" + 
				"uid,\r\n" + 
				"address ,\r\n" + 
				"tel,\r\n" + 
				"email,\r\n" + 
				"birthday,\r\n" + 
				"registerTime ,\r\n" + 
				"memberImage,\r\n" + 
				"fileName,\r\n" + 
				"QUOTE,\r\n" + 
				"intro\r\n" + 
				"FROM memberossan WHERE twnorth = 1 AND privilege IS NULL)\r\n" + 
				"AS NewTable WHERE RowNum >= ? AND RowNum <= ?";
		
		String sql = sql0;
		// 由頁碼推算出該頁是由哪一筆紀錄開始(1 based)
		int startRecordNo = (pageNoNorth - 1) * recordsPerPage + 1;
		int endRecordNo = (pageNoNorth) * recordsPerPage;
		// 由頁碼推算出該頁是由哪一筆紀錄開始(0 based)		
		try (
			Connection connection = ds.getConnection(); 
			PreparedStatement ps = connection.prepareStatement(sql);
		) {
			ps.setInt(1, startRecordNo);
			ps.setInt(2, endRecordNo);
			try (
				ResultSet rs = ps.executeQuery();
			) {
				// 只要還有紀錄未取出，rs.next()會傳回true
				// 迴圈內將逐筆取出ResultSet內的紀錄
				while (rs.next()) {
					
					// 準備一個新的MemberOssanBean，將ResultSet內的一筆紀錄移植到MemberOssanBean內
					if (rs.getClob("quote") != null) {
						String quoteString = GlobalService.clobToStringWindows(rs.getClob("quote"));
						quoteList.add(quoteString);
					 } else {
						String quoteString = "無言";	
						quoteList.add(quoteString);
					 }							
					// 最後將MemberOssanBean物件放入大的容器內	
				}
			}
		} catch (SQLException ex) {
			System.out.println("Quote List發生例外");
			ex.printStackTrace();
			throw new RuntimeException("OssanDaoImpl_Jdbc()#getQuoteOssans()發生例外: " 
										+ ex.getMessage());
		}
		return quoteList;
	}
}