package _05_orderProcess.model;

import java.io.Serializable;

public class OssanOrderDetailBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	String num;
	String orderno ;
	String amount;
	String totalamount;
	String invoicetitle;
	String address;
	String tel;
	String email;
	String comment;
	String orderdate;

	public OssanOrderDetailBean(String num, String orderno, String amount, String totalamount, String invoicetitle,
			String address, String tel, String email, String comment, String orderdate) {
		
		this.num = num;
		this.orderno = orderno;
		this.amount = amount;
		this.totalamount = totalamount;
		this.invoicetitle = invoicetitle;
		this.address = address;
		this.tel = tel;
		this.email = email;
		this.comment = comment;
		this.orderdate = orderdate;
	}

	
	public OssanOrderDetailBean() {
		
	}


	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getTotalamount() {
		return totalamount;
	}

	public void setTotalamount(String totalamount) {
		this.totalamount = totalamount;
	}

	public String getInvoicetitle() {
		return invoicetitle;
	}

	public void setInvoicetitle(String invoicetitle) {
		this.invoicetitle = invoicetitle;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(String orderdate) {
		this.orderdate = orderdate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}	