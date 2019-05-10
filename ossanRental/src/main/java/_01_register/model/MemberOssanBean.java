package _01_register.model;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="memberossan")
public class MemberOssanBean implements Serializable {
	private static final long serialVersionUID = 1L;
	Integer pKey;
	String memberId;
	String password;
	String name;
	String nickname;
	String uid;
	String address;
	String tel;
	String email;
	Date birthday;
	Timestamp registerTime;
	Blob memberImage;
	Clob quote;
	Clob intro;
	String fileName;
	
	
	public MemberOssanBean(String memberId, String password, String name, String nickname, String uid, String address,
			String tel, String email, Date birthday, Blob memberImage, String fileName) {

		this.memberId = memberId;
		this.password = password;
		this.name = name;
		this.nickname = nickname;
		this.uid = uid;
		this.address = address;
		this.tel = tel;
		this.email = email;
		this.birthday = birthday;
		this.memberImage = memberImage;
		this.fileName = fileName;
	}
	
	
	public MemberOssanBean(Integer pKey, String memberId, String password, String name, String nickname, String uid,
			String address, String tel, String email, Date birthday, Blob memberImage, String fileName) {
		
		this.pKey = pKey;
		this.memberId = memberId;
		this.password = password;
		this.name = name;
		this.nickname = nickname;
		this.uid = uid;
		this.address = address;
		this.tel = tel;
		this.email = email;
		this.birthday = birthday;
		this.memberImage = memberImage;
		this.fileName = fileName;
	}


	public MemberOssanBean(Integer pKey, String memberId, String password, String name, String nickname, String uid,
			String address, String tel, String email, Date birthday, Timestamp registerTime, Blob memberImage,
			Clob quote, Clob intro, String fileName) {
		
		this.pKey = pKey;
		this.memberId = memberId;
		this.password = password;
		this.name = name;
		this.nickname = nickname;
		this.uid = uid;
		this.address = address;
		this.tel = tel;
		this.email = email;
		this.birthday = birthday;
		this.registerTime = registerTime;
		this.memberImage = memberImage;
		this.quote = quote;
		this.intro = intro;
		this.fileName = fileName;
	}
	
	



	public MemberOssanBean(Integer pKey, Clob quote, Clob intro) {
		this.pKey = pKey;
		this.quote = quote;
		this.intro = intro;
	}


	public MemberOssanBean() {
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="seqNo")
	public Integer getpKey() {
		return pKey;
	}

	public void setpKey(Integer pKey) {
		this.pKey = pKey;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Timestamp getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Timestamp registerTime) {
		this.registerTime = registerTime;
	}

	public Blob getMemberImage() {
		return memberImage;
	}

	public void setMemberImage(Blob memberImage) {
		this.memberImage = memberImage;
	}

	public Clob getQuote() {
		return quote;
	}

	public void setQuote(Clob quote) {
		this.quote = quote;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Clob getIntro() {
		return intro;
	}

	public void setIntro(Clob intro) {
		this.intro = intro;
	}
	
	
	
}	