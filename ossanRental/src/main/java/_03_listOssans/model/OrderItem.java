package _03_listOssans.model;
//本類別封裝單筆訂單資料
public class OrderItem {
	int pkey;
	String memberId;
	String name;
	String nickname;

	int qty = 0 ; 
	
	Double price = 0.0 ; 
	Double discount = 1.0 ;
	
	public OrderItem() {
	}

	public OrderItem(int pkey, String memberId, String name, String nickname, int qty, Double price, Double discount) {

		this.pkey = pkey;
		this.memberId = memberId;
		this.name = name;
		this.nickname = nickname;
		this.qty = qty;
		this.price = price;
		this.discount = discount;
	}
	
	public OrderItem(int pkey, String memberId, String name, String nickname, int qty) {
		this.pkey = pkey;
		this.memberId = memberId;
		this.name = name;
		this.nickname = nickname;
		this.qty = qty;
	}


	public int getPkey() {
		return pkey;
	}

	public void setPkey(int pkey) {
		this.pkey = pkey;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
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

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}
	
	

	
}