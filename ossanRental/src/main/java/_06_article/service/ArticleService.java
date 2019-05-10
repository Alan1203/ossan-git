package _06_article.service;

import java.util.List;

import _01_register.model.MemberOssanBean;
import _06_article.model.Article;

public interface ArticleService {
	
	int getTotalPages();

	List<Article> getPageArticles();
	
	List<Article> getPageArticles(MemberOssanBean ossan);
	
	Article getArticle(int aId) ;
	
	int getPageNo();

	void setPageNo(int pageNo);


	// 計算紀錄總筆數
	long getRecordCounts();


	int updateArticle(Article article, long sizeInBytes) ;

	// 修改一筆記錄
	int updateArticle(Article article) ;


	// 依artNo來刪除單筆記錄
	int deleteArticle(int artNo);

	// 新增一筆記錄
	int saveArticle(Article article);

	String getNickName(int seqNo);

	int getTotalPages(String seqNo);
	
}