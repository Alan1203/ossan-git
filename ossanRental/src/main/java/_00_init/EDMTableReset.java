package _00_init;

/*  
    程式說明：建立表格與設定初始測試資料。
    表格包括：Book, BookCompany, Member, Orders, OrderItems
      
(1) Book欄位說明:
    bookId      	: 書籍代號    			整數     	主鍵, 自動遞增
    title       	: 書名           			字串      
    author      	: 作者               		字串
    price       	: 價格               		DECIMAL(10,1)
    discount    	: 折扣               		DECIMAL(5,3)
    CoverImage  	: 封面照片           		Blob
    fileName    	: 封面圖檔檔名       	字串      	20
    bookNo      	: 書號               		字串      	20
    stock			: 庫存數量			整數
    companyId   	: 出版社代號         		整數      
     
(2) BookCompany欄位說明:
    id 		     	: 出版社代號			整數      	主鍵, 自動遞增
    name         	: 出版社名稱			字串      	60
    address      	: 出版社地址			字串      	60
    url          	: 出版社URL			字串      	120      
      
(3) Member欄位說明:
    seqNo       	: 會員流水號			整數      	主鍵, 自動遞增
    memberId    	: 會員編號           		字串      
    name        	: 姓名               		字串      	
    password    	: 密碼               		字串      	
    address     	: 地址               		字串      	
    email       	: email             字串      	
    tel         	: 電話日             		字串      	
    userType    	: 會員類別           		字串      	
    registerTime	: 註冊時間          		Timestamp
    totalAmount 	: 訂購總金額         		DECIMAL
    memberImage 	: 照片               		Blob
    fileName    	: 封面圖檔檔名      	字串
    comment			: 客戶側寫(註解)		Clob	      	
    unpaid_amount	: 未付款餘額			DECIMAL  
    

    
(4) Orders欄位說明:
    orderNo        	: 訂單編號			整數     	主鍵, 自動遞增
    userId         	: 客戶編號           		字串      	20
    totalAmount    	: 總金額             		DECIMAL(10,1)
    shippingAddress	: 出貨地址           		字串      	64    
    BNO            	: 統一編號           		字串       	8
    invoiceTitle  	: 發票抬頭           		字串      	72
    orderDate      	: 訂單日期			Datetime 
    ShippingDate   	: 出貨日期			Datetime
    CancelTag      	: 取消               		字串       	1
    
(5) OrderItems欄位說明:
    seqNo			: 明細編號			整數      	主鍵, 自動遞增
    orderNo			: 訂單編號			整數		外鍵		
    bookId			: 書籍代號			整數      	外鍵
    Description  	: 說明				字串      	72
    amount       	: 數量				整數
    unitPrice    	: 單價                 		DECIMAL(10,1)
    Discount     	: 折扣                  		DECIMAL(5,3)
 
*/
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import _00_init.util.DBService;
import _00_init.util.GlobalService;
import _00_init.util.RandomDates;
import _00_init.util.SystemUtils2018;
import _01_register.model.MemberOssanBean;
import _06_article.model.Article;

 
public class EDMTableReset {
	public static final String UTF8_BOM = "\uFEFF"; // 定義 UTF-8的BOM字元
	

	public static void main(String args[]) {
		

		
		String line = "";
		
		int count = 0;
		int countArticle = 0;
		try (
			Connection con = DriverManager.getConnection(
								DBService.getDbUrl(), 
								DBService.getUser(),
								DBService.getPassword()); 
			Statement stmt = con.createStatement();
		) {
			//3. Member表格
			stmt.executeUpdate(DBService.getDropMemberOssan());
			System.out.println("MemberOssan表格刪除成功");
			stmt.executeUpdate(DBService.getCreateMemberOssan());
			System.out.println("MemberOssan表格產生成功");
			//4. OssanOrders表格
			stmt.executeUpdate(DBService.getDropOssanOrders());
			System.out.println("OssanOrders表格刪除成功");
			stmt.executeUpdate(DBService.getCreateOssanOrders());
			System.out.println("OssanOrders表格產生成功");
			//5. 表格
			stmt.executeUpdate(DBService.getDropOssanOrderItems());
			System.out.println("OssanOrderItems表格刪除成功");
			stmt.executeUpdate(DBService.getCreateOssanOrderItems());
			System.out.println("OssanOrderItems表格產生成功");
			
			//6.article表格
			stmt.executeUpdate(DBService.getDropArticle());
			System.out.println("Article表格刪除成功");
			stmt.executeUpdate(DBService.getCreateArticle());
			System.out.println("Article表格產生成功");
			
			//這裡插入Admin會員字串
			stmt.executeUpdate(DBService.addColumnPrivilege());
			System.out.println("新增admin 用欄位 privilege");
			stmt.executeUpdate(DBService.addOneAdmin());
			System.out.println("新增admin 一名");
			stmt.executeUpdate(DBService.addColumnLocation());
			System.out.println("新增地區選項");
					
			try (
				FileInputStream fis = new FileInputStream("data/Inputossan.txt");
				InputStreamReader isr0 = new InputStreamReader(fis, "UTF-8");
				BufferedReader br = new BufferedReader(isr0);
			) {
				while ((line = br.readLine()) != null) {
					String[] sa = line.split("\\|");
					MemberOssanBean bean = new MemberOssanBean();
					bean.setMemberId(sa[0]);	
					String pswd = GlobalService.getMD5Endocing(GlobalService.encryptString(sa[1]));
					bean.setPassword(pswd);
					bean.setName(sa[2]);
					bean.setNickname(sa[3]);
					bean.setUid(sa[4]);	
					bean.setAddress(sa[5]);
					bean.setTel(sa[6]);
					bean.setEmail(sa[7]);
					Blob sb = SystemUtils2018.fileToBlob(sa[8]);
					bean.setMemberImage(sb);
					String imageFileName = sa[8].substring(sa[8].lastIndexOf("/") + 1);
					bean.setFileName(imageFileName);
					Clob clob1 = SystemUtils2018.fileToClob(sa[9]);
					bean.setQuote(clob1);
					Clob clob2 = SystemUtils2018.fileToClob(sa[10]);
					bean.setIntro(clob2);
					
					saveMember(bean, con);
					count++;
					System.out.println("新增" + count + "筆記錄:" + sa[2]);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try (
					FileInputStream fis = new FileInputStream("data/Inputarticle.txt");
					InputStreamReader isr0 = new InputStreamReader(fis, "UTF-8");
					BufferedReader br = new BufferedReader(isr0);
				) {
					while ((line = br.readLine()) != null) {
						String[] sb = line.split("\\|");
						Article article = new Article();
						Blob ab = SystemUtils2018.fileToBlob(sb[3]);
						article.setArticleImage(ab);	
						article.setTitle(sb[0]);
						Clob content = SystemUtils2018.stringToClob(sb[1]);
						article.setArticle(content);
						String FileName = sb[3].substring(sb[3].lastIndexOf("/") + 1);
						article.setFileName(FileName);
						article.setSeqNo(sb[2]);
						saveArticle(article, con);
						countArticle++;
						System.out.println("新增" + countArticle + "筆記錄:" );
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		} catch (SQLException e) {
			System.err.println("新建表格時發生SQL例外: " + e.getMessage());
			e.printStackTrace();
		}
		////////////////////////////////////////////////////////
		try (
				Connection con = DriverManager.getConnection(
									DBService.getDbUrl(), 
									DBService.getUser(),
									DBService.getPassword()); 
				Statement stmt = con.createStatement();
			) {
			
			for(int n = 0 ; n < 20 ; n++) {
			stmt.executeUpdate(DBService.updateRandomColumnLocationNorth());
			stmt.executeUpdate(DBService.updateRandomColumnLocationMiddle());
			stmt.executeUpdate(DBService.updateRandomColumnLocationSouth());
			stmt.executeUpdate(DBService.updateRandomColumnLocationOther());
			}		
			System.out.println("地區選項隨選安排完成");
			
		}catch (SQLException e) {
			System.err.println("地區選項隨選安排發生SQL例外: " + e.getMessage());
			e.printStackTrace();
		}
	}

	static public int saveArticle(Article article , Connection con) {
		String sql = "insert into Article (" 
				+ "title , UpdateTime, articleImage, "
				+ "article,fileName , seqNo"
				+ " ) values (?, ?, ?, ?, ?, ?)";
		int n = 0;
		try(
			PreparedStatement ps = 	con.prepareStatement(sql);
		) {
			ps.setString(1, article.getTitle());
			java.sql.Timestamp now = new java.sql.Timestamp(System.currentTimeMillis());
			ps.setTimestamp(2, now);
			ps.setBlob(3, article.getArticleImage());
			ps.setClob(4, article.getArticle());
			ps.setString(5, article.getFileName());
			ps.setString(6, article.getSeqNo());
			n = ps.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		}	
		return n;
	}
			
	
		
		static public int saveMember(MemberOssanBean mb, Connection con) {
		String sql = "insert into MemberOssan (" 
				+ "memberID, password, name, nickname, "
				+ "uid, address, tel, email, "
				+ "birthday, registerTime, "				
				+ "memberImage, fileName, "
				+ "quote, intro "
				+ " ) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		int n = 0;
		try(
			PreparedStatement ps = 	con.prepareStatement(sql);
		) {
			ps.setString(1, mb.getMemberId());
			ps.setString(2, mb.getPassword());
			ps.setString(3, mb.getName());
			ps.setString(4, mb.getNickname());
			
			ps.setString(5, mb.getUid());
			ps.setString(6, mb.getAddress());
			ps.setString(7, mb.getTel());
			ps.setString(8, mb.getEmail());
			Date date = Date.valueOf(RandomDates.createRandomDate(1940, 1990));
			ps.setDate(9, date);
			
			java.sql.Timestamp now = new java.sql.Timestamp(System.currentTimeMillis());
			ps.setTimestamp(10, now);

			ps.setBlob(11, mb.getMemberImage());
			ps.setString(12, mb.getFileName());
			ps.setClob(13, mb.getQuote());			
			ps.setClob(14, mb.getIntro());
			n = ps.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		}	
		return n;
	}
}