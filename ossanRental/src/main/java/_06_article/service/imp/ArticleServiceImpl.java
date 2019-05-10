package _06_article.service.imp;

import java.util.List;

import _01_register.model.MemberOssanBean;
import _06_article.model.Article;
import _06_article.repository.ArticleDao;
import _06_article.repository.imp.ArticleDaoImpl;
import _06_article.service.ArticleService;

public class ArticleServiceImpl implements ArticleService{

	ArticleDao dao ;
	
	
	public ArticleServiceImpl() {
		this.dao = new ArticleDaoImpl();
	}
	
	@Override
	public int getTotalPages() {
		return dao.getTotalPages();
	}

	@Override
	public List<Article> getPageArticles( MemberOssanBean ossan) {
		return dao.getPageArticles(ossan);
	}
	@Override
	public List<Article> getPageArticles() {
		return dao.getPageArticles();
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
	public long getRecordCounts() {
		return dao.getRecordCounts();
	}

	@Override
	public int updateArticle(Article article, long sizeInBytes) {
		return dao.updateArticle(article, sizeInBytes);
	}

	@Override
	public int updateArticle(Article article) {
		return dao.updateArticle(article);
	}

	@Override
	public int deleteArticle(int artNo) {
		return dao.deleteArticle(artNo);
	}

	@Override
	public int saveArticle(Article article) {
		return dao.saveArticle(article);
	}

	public Article getArticle(int aId) {
		dao.setArticleId(aId);
		return dao.getArticle();
	}

	@Override
	public String getNickName(int seqNo) {
		
		return dao.getNickName(seqNo);
	}

	@Override
	public int getTotalPages(String seqNo) {
		return dao.getTotalPages(seqNo);
	}

}
