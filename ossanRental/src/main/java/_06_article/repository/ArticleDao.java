package _06_article.repository;

import java.util.List;
import _01_register.model.MemberOssanBean;
import _06_article.model.Article;

public interface ArticleDao {
	
	int getTotalPages();

	List<Article> getPageArticles(MemberOssanBean ossan);
	
	List<Article> getPageArticles();

	int getPageNo();

	void setPageNo(int pageNo);

	// 計算紀錄總筆數
	long getRecordCounts();

	void setArticleId(int aId);

	Article getArticle();

	int updateArticle(Article article, long sizeInBytes) ;
	
	int updateArticle(Article article) ;

	// 依OssanID來查詢單筆記錄
	Article queryArticle(int aId);

	// 依artNo來刪除單筆記錄
	int deleteArticle(int artNo);

	// 新增一筆記錄
	int saveArticle(Article article);
	
	String getNickName(int seqNo);

	int getTotalPages(String seqNo);
	
	long getRecordCounts(String seqNo);

}