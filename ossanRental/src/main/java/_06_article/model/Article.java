package _06_article.model;

import java.sql.Clob;
import java.sql.Timestamp;
import java.io.Serializable;
import java.sql.Blob;

public class Article implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	int artNo ; 
	String title ;
	Timestamp updateTime ;
	Blob articleImage;
	Clob article ; 
	String seqNo ;
	String fileName ;
	String sArticle ;
	String nick ;
	public Article( String title, Timestamp updateTime, Blob articleImage, Clob article, String seqNo , String fileName,String sArticle) {
		this.title = title;
		this.updateTime = updateTime;
		this.articleImage = articleImage;
		this.article = article;
		this.seqNo = seqNo;
		this.fileName = fileName;
		this.sArticle = sArticle;
	}
	public Article() {
	
	}
	public int getArtNo() {
		return artNo;
	}
	public void setArtNo(int artNo) {
		this.artNo = artNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public Blob getArticleImage() {
		return articleImage;
	}
	public void setArticleImage(Blob articleImage) {
		this.articleImage = articleImage;
	}
	public Clob getArticle() {
		return article;
	}
	public void setArticle(Clob article) {
		this.article = article;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getsArticle() {
		return sArticle;
	}
	public void setsArticle(String sArticle) {
		this.sArticle = sArticle;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	
	
}
