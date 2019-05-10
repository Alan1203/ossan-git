package _04_ShoppingCart.model;
import java.util.*;

import _03_listOssans.model.OrderItem;

public class ShoppingCart {   
	
	private Map<Integer, OrderItem> cart = new LinkedHashMap< >();
	
	public ShoppingCart() {
	}
	
	public Map<Integer, OrderItem>  getContent() { // ${ShoppingCart.content}
		return cart;
	}
	
	public void addToCart(int pKey, OrderItem  oi) {
		if (oi.getQty() <= 0 ) {
			return;
		}
		// 如果客戶在伺服器端沒有此項商品的資料，則客戶第一次購買此項商品
		if ( cart.get(pKey) == null ) {
		    cart.put(pKey, oi);
		} else {
	        // 如果客戶在伺服器端已有此項商品的資料，則客戶『加購』此項商品
			OrderItem oib = cart.get(pKey);
			// 加購的數量：oi.getQty()
			// 原有的數量：oib.getQty()			
			oib.setQty(oi.getQty() + oib.getQty());
		}
	}

	public boolean modifyQty(int pKey, int  newQty) {
		if ( cart.get(pKey) != null ) {
		   OrderItem  oi = cart.get(pKey);
		   oi.setQty(newQty);
	       //cart.put(pKey, oi);
	       return true;
		} else {
		   return false;
		}
	}
	// 刪除某項商品
	public int deleteBook(int pKey) {
		if ( cart.get(pKey) != null ) {
	       cart.remove(pKey);  // Map介面的remove()方法
	       return 1;
		} else {
		   return 0;
		}
	}
	public int getItemNumber(){   // ShoppingCart.itemNumber
		return cart.size();
	}
	//計算購物車內所有商品的合計金額(每項商品的單價*數量的總和)
	public double getSubtotal(){
		double subTotal = 0 ;
		Set<Integer> set = cart.keySet();
		for(int n : set){
			double price    = 300;
			double discount = 1;
			int    qty      = cart.get(n).getQty();
			subTotal +=  price * discount * qty;
		}
		return subTotal;
	}
	public void listCart() {
		Set<Integer> set = cart.keySet();
		for(Integer k : set){
			System.out.printf("pKey=%3d,  Qty=%3d,  price=%5.2f,  discount=%6.2f\n" , k , cart.get(k).getQty(), cart.get(k).getPrice(), cart.get(k).getDiscount());
		}
		System.out.println("------------------");
	}
}
