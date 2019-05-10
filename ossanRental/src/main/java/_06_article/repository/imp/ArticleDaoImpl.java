package _06_article.repository.imp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import _00_init.util.GlobalService;
import _00_init.util.SystemUtils2018;
import _01_register.model.MemberOssanBean;
import _06_article.model.Article;
import _06_article.repository.ArticleDao;

public class ArticleDaoImpl implements ArticleDao{
	
	
	private int aId = 0; 	// 查詢單筆商品會用到此代號
	private int pageNo = 1;		// 存放目前顯示之頁面的編號
	private int recordsPerPage = GlobalService.RECORDS_PER_PAGE; // 預設值：每頁三筆
	private int totalPages = -1;
	DataSource ds = null;
	
	//負責連資料庫
	public ArticleDaoImpl() {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup(GlobalService.JNDI_DB_NAME);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("ArticleDaoImpl()#建構子發生例外: " 
										+ ex.getMessage());
		}
	}

	@Override
	public int getTotalPages() {
		// 注意下一列敘述的每一個型態轉換
				totalPages = (int) (Math.ceil(getRecordCounts() / (double) recordsPerPage));

				return totalPages;
	}
	//取出大叔自己的文章(ossan用，一頁)
	@Override
	public List<Article> getPageArticles(MemberOssanBean ossan) {
		List<Article> list = new ArrayList<Article>();
		
		String seqNo = String.valueOf(ossan.getpKey());
//		System.out.println(seqNo);
		String sql0 = "SELECT  * FROM (SELECT  ROW_NUMBER() OVER (ORDER BY artNo desc)\r\n" + 
				"AS RowNum, 			 \r\n" + 
				"artNo,\r\n" + 
				"title,\r\n" + 
				"UpdateTime,\r\n" + 
				"articleImage,\r\n" + 
				"article,\r\n" + 
				"fileName,\r\n" + 
				"seqNo\r\n" + 
				"FROM article where seqNo = ?)\r\n" + 
				"AS NewTable WHERE RowNum >= ? AND RowNum <= ? ";
		
		String sql = sql0;
		// 由頁碼推算出該頁是由哪一筆紀錄開始(1 based)
		int startRecordNo = (pageNo - 1) * recordsPerPage + 1;
		int endRecordNo = (pageNo) * recordsPerPage;
		try (
			Connection connection = ds.getConnection(); 
			PreparedStatement ps = connection.prepareStatement(sql);
		) {
			ps.setString(1,seqNo);
			ps.setInt(2, startRecordNo);
			ps.setInt(3, endRecordNo);
			try (
				ResultSet rs = ps.executeQuery();
			) {
				// 只要還有紀錄未取出，rs.next()會傳回true
				// 迴圈內將逐筆取出ResultSet內的紀錄
				while (rs.next()) {
					// 準備一個新的article，將ResultSet內的一筆紀錄移植到article內
					Article article = new Article();    	
					article.setArtNo(rs.getInt("artNo"));		
					article.setSeqNo(rs.getString("seqNo"));
					article.setArticle(rs.getClob("article"));
					article.setArticleImage(rs.getBlob("articleImage"));
					article.setFileName(rs.getString("fileName"));
					article.setTitle(rs.getString("title"));
					article.setUpdateTime(rs.getTimestamp("UpdateTime"));
					
					article.setsArticle(SystemUtils2018.clobToString(article.getArticle()));
								
					// 最後將article物件放入大的容器內
					list.add(article);
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException("ArticleDaoImpl()#getPageArticles()發生例外: " 
										+ ex.getMessage());
		}
		return list;
	}
	//取出所有的文章(visitor用，一頁)
	@Override
	public List<Article> getPageArticles() {
		List<Article> list = new ArrayList<Article>();
		
		String sql0 = "SELECT  * FROM (SELECT  ROW_NUMBER() OVER (ORDER BY artNo desc)\r\n" + 
				"AS RowNum, 			 \r\n" + 
				"artNo,\r\n" + 
				"title,\r\n" + 
				"UpdateTime,\r\n" + 
				"articleImage,\r\n" + 
				"article,\r\n" + 
				"fileName,\r\n" + 
				"seqNo\r\n" + 
				"FROM article )\r\n" + 
				"AS NewTable WHERE RowNum >= ? AND RowNum <= ? ";
		
		String sql = sql0;
		// 由頁碼推算出該頁是由哪一筆紀錄開始(1 based)
		int startRecordNo = (pageNo - 1) * recordsPerPage + 1;
		int endRecordNo = (pageNo) * recordsPerPage;
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
					// 準備一個新的article，將ResultSet內的一筆紀錄移植到article內
					Article article = new Article();    	
					article.setArtNo(rs.getInt("artNo"));		
					article.setSeqNo(rs.getString("seqNo"));
					article.setArticle(rs.getClob("article"));
					article.setArticleImage(rs.getBlob("articleImage"));
					article.setFileName(rs.getString("fileName"));
					article.setTitle(rs.getString("title"));
					article.setUpdateTime(rs.getTimestamp("UpdateTime"));
					
					article.setsArticle(SystemUtils2018.clobToString(article.getArticle()));
								
					// 最後將article物件放入大的容器內
					list.add(article);
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException("ArticleDaoImpl()#getPageArticles()發生例外: " 
										+ ex.getMessage());
		}
		return list;
	}


	@Override
	public int getPageNo() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	@Override
	public long getRecordCounts() {
		long count = 0; // 必須使用 long 型態
		String sql = "SELECT count(*) FROM article";
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
	public void setArticleId(int aId) {
		this.aId = aId;
		
	}

	@Override
	public Article getArticle() {
		Article article = queryArticle(this.aId);
		return article;
	}

	@Override
	public int updateArticle(Article article, long sizeInBytes) {
		int n = 0;
		String sql = "UPDATE article SET title = ? , UpdateTime = ? ,article = ?, articleImage = ?  " + 
				 " WHERE artNo = ?";

		if (sizeInBytes == -1) { // 不修改圖片
			n = updateArticle(article);
			return n;
		}
		try (
			Connection connection = ds.getConnection(); 
			PreparedStatement pStmt = connection.prepareStatement(sql);
		) {
			pStmt.setString(1, article.getTitle());
			java.sql.Timestamp now = new java.sql.Timestamp(System.currentTimeMillis());
			pStmt.setTimestamp(2, now);
			pStmt.setClob(3, SystemUtils2018.stringToClob(article.getsArticle()));
			pStmt.setBlob(4, article.getArticleImage());
			pStmt.setInt(5, article.getArtNo());
		
			
			
			n = pStmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException("ArticleDaoImpl()#updateArticle(Article , sizeInBytes)發生例外: " 
					+ ex.getMessage());
		}
		return n;
	}
	@Override
	public int updateArticle(Article article) {
		int n = 0;
		String sql = "UPDATE article SET title = ? , UpdateTime = ? ,article = ?  " + 
					 " WHERE artNo = ?";
		try (
			Connection connection = ds.getConnection(); 
			PreparedStatement ps = connection.prepareStatement(sql);
		) {
//			ps.clearParameters();
			ps.setString(1, article.getTitle());
			java.sql.Timestamp now = new java.sql.Timestamp(System.currentTimeMillis());
			ps.setTimestamp(2, now);
			ps.setClob(3, SystemUtils2018.stringToClob(article.getsArticle()));
			ps.setInt(4, article.getArtNo());
			
			n = ps.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException("ArticleDaoImpl()#updateArticle(Article)發生例外: " 
										+ ex.getMessage());
		}
		return n;
	}

	@Override
	public Article queryArticle(int aId) {
		Article article = null ;
		String sql = "SELECT * FROM article WHERE artNo = ?";

		try (
			Connection connection = ds.getConnection(); 
			PreparedStatement ps = connection.prepareStatement(sql);
		) {
			ps.setInt(1, aId);
			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next()) {
					article = new Article();  	
					article.setArtNo(rs.getInt("artNo"));		
					article.setTitle(rs.getString(2));
					article.setArticleImage(rs.getBlob(4));
					article.setArticle(rs.getClob(5));
					article.setFileName(rs.getString(6));
					article.setSeqNo(rs.getString(7));
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException("ArticleDaoImpl_Jdbc()#queryArticle()發生例外: " 
										+ ex.getMessage());
		}
		return article;
	}
	@Override
	public int deleteArticle(int artNo) {
		int n = 0;
		String sql = "DELETE FROM article WHERE artNo = ?";

		try (
			Connection connection = ds.getConnection(); 
			PreparedStatement pStmt = connection.prepareStatement(sql);
		) {
			pStmt.setInt(1, artNo);
			n = pStmt.executeUpdate();
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException("ArticleDaoImpl()#deleteArticle()發生例外: " 
										+ ex.getMessage());
		}
		return n;
	}

	@Override
	public int saveArticle(Article article) {
		int n = 0;
			String sql = "INSERT INTO Article (title, UpdateTime, articleImage, article, filename, seqNo) VALUES (?, ?, ?, ?, ?, ? )";

			try (
				Connection connection = ds.getConnection();
				PreparedStatement pStmt = connection.prepareStatement(sql);
			) {
				pStmt.setString(1, article.getTitle());
				pStmt.setTimestamp(2, article.getUpdateTime());
				pStmt.setBlob(3, article.getArticleImage());
				pStmt.setClob(4, article.getArticle());
				pStmt.setString(5, article.getFileName());
				pStmt.setString(6, article.getSeqNo());
				n = pStmt.executeUpdate();
			} catch (SQLException ex) {
				ex.printStackTrace();
				throw new RuntimeException("ArticleDaoImpl()#saveArticle()發生例外: " 
											+ ex.getMessage());
			}
		return n;
	}

	@Override
	public String getNickName(int seqNo) {
		MemberOssanBean ossan = null ;
		String sql = "SELECT nickname FROM article WHERE seqNo = ?";

		try (
			Connection connection = ds.getConnection(); 
			PreparedStatement ps = connection.prepareStatement(sql);
		) {
			ps.setInt(1, seqNo);
			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next()) {
					ossan = new MemberOssanBean();	
					ossan.setNickname(rs.getString("nickname"));		
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException("ArticleDaoImpl_Jdbc()#queryArticle()發生例外: " 
										+ ex.getMessage());
		}
		return ossan.getNickname();
	}
	@Override
	public int getTotalPages(String seqNo) {
		totalPages = (int) (Math.ceil(getRecordCounts(seqNo) / (double) recordsPerPage));
		if(totalPages < 1) { //預防大叔還沒寫文章
			return 1 ;
		}else {
			return totalPages;
		}
	}
	@Override
	public long getRecordCounts(String seqNo) {
		long count = 0; // 必須使用 long 型態
		String sql = "SELECT count(*) FROM article where seqNo = ?";
		try (
				Connection connection = ds.getConnection(); 
				PreparedStatement ps = connection.prepareStatement(sql);
			) {
				ps.setString(1, seqNo);
				try (ResultSet rs = ps.executeQuery();) {
					if (rs.next()) {
						count = rs.getLong(1);	
					}
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
				throw new RuntimeException("ArticleDaoImpl()#getRecordCounts()發生例外: " 
											+ ex.getMessage());
			}
			return count;
	}

}
