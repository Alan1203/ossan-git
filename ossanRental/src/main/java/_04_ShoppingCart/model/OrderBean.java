package _04_ShoppingCart.model;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;
// 本類別存放訂單資料
public class OrderBean {
	Integer orderNo;
	String  invoiceTitle;
	String	shippingAddress; 
	String  bno;

	String  tel;
	String  email;
	Double	totalAmount;
	String  comment;
	
	Date  orderDate;
	
	Date  shippingDate;
	String	cancelTag;	
	Set<OrderItemBean> items = new LinkedHashSet<>();
	
	public OrderBean() {
		
	}

	public OrderBean(Integer orderNo, Date orderDate, String invoiceTitle, String tel, String email, String bno,
			String shippingAddress, String comment, Double totalAmount, Date shippingDate, String cancelTag,
			Set<OrderItemBean> items) {

		this.orderNo = orderNo;
		this.orderDate = orderDate;
		this.invoiceTitle = invoiceTitle;
		this.tel = tel;
		this.email = email;
		this.bno = bno;
		this.shippingAddress = shippingAddress;
		this.comment = comment;
		this.totalAmount = totalAmount;
		this.shippingDate = shippingDate;
		this.cancelTag = cancelTag;
		this.items = items;
	}
	
	

	public OrderBean(Integer orderNo, String invoiceTitle, String shippingAddress, String bno, String tel, String email,
			Double totalAmount, String comment, Date orderDate) {
	
		this.orderNo = orderNo;
		this.invoiceTitle = invoiceTitle;
		this.shippingAddress = shippingAddress;
		this.bno = bno;
		this.tel = tel;
		this.email = email;
		this.totalAmount = totalAmount;
		this.comment = comment;
		this.orderDate = orderDate;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getInvoiceTitle() {
		return invoiceTitle;
	}

	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
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

	public String getBno() {
		return bno;
	}

	public void setBno(String bno) {
		this.bno = bno;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Date getShippingDate() {
		return shippingDate;
	}

	public void setShippingDate(Date shippingDate) {
		this.shippingDate = shippingDate;
	}

	public String getCancelTag() {
		return cancelTag;
	}

	public void setCancelTag(String cancelTag) {
		this.cancelTag = cancelTag;
	}

	public Set<OrderItemBean> getItems() {
		return items;
	}

	public void setItems(Set<OrderItemBean> items) {
		this.items = items;
	}

	

//	// 說明多方表格的orderBean欄位為外鍵欄位，對照的主鍵為一方表格的orderno欄
//	public Set<OrderItemBean> getItems() {
//		return items;
//	}
//
//	public void setItems(Set<OrderItemBean> items) {
//		this.items = items;
//	}
	
	
	
}
