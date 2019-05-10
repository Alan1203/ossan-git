package _04_ShoppingCart.model;

public class OrderItemBean {
	Integer seqno;
	Integer orderNo;
	Integer pKey;
	Integer quantity;
	Double unitPrice;
	Double discount;
	
	String ossanName;
	String ossanNickname;
	
	
	

	public OrderItemBean() {
	}
	
	

	public OrderItemBean(Integer seqno, Integer orderNo, Integer pKey, Integer quantity, Double unitPrice,
			Double discount, String ossanName, String ossanNickname) {
		
		this.seqno = seqno;
		this.orderNo = orderNo;
		this.pKey = pKey;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.discount = discount;
		this.ossanName = ossanName;
		this.ossanNickname = ossanNickname;
	}



	public OrderItemBean(Integer seqno, Integer orderNo, Integer pKey, Integer quantity, Double unitPrice,
			Double discount) {
		
		this.seqno = seqno;
		this.orderNo = orderNo;
		this.pKey = pKey;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.discount = discount;
	}

	public Integer getSeqno() {
		return seqno;
	}

	public void setSeqno(Integer seqno) {
		this.seqno = seqno;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	
	

	public Integer getpKey() {
		return pKey;
	}

	public void setpKey(Integer pKey) {
		this.pKey = pKey;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}



	public String getOssanName() {
		return ossanName;
	}



	public void setOssanName(String ossanName) {
		this.ossanName = ossanName;
	}



	public String getOssanNickname() {
		return ossanNickname;
	}



	public void setOssanNickname(String ossanNickname) {
		this.ossanNickname = ossanNickname;
	}
	
	

}