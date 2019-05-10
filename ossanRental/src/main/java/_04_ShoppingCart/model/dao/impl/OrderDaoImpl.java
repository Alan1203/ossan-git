package _04_ShoppingCart.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import _00_init.util.GlobalService;
import _04_ShoppingCart.model.OrderBean;
import _04_ShoppingCart.model.OrderItemBean;
import _04_ShoppingCart.model.dao.OrderDao;

// 本類別
//   1.新增一筆訂單到orders表格
//   2.查詢orders表格內的單筆訂單
//   3.查詢orders表格內的所有訂單
public class OrderDaoImpl implements OrderDao {
	
	private String memberId = null;
	private Connection con;
	int orderNo = 0;


	public OrderDaoImpl() {
	}

	@Override
	public void insertOrder(OrderBean ob) {
		String sqlOrder = "Insert Into ossanorders "
				+ " (invoiceTitle, address, BNO,tel,email,TotalAmount,Comment,"
				+ " orderDate) "
				+ " values(?, ?, ?, ?, ?, ?, ?, ?) ";

		String sqlItem = "Insert Into ossanorderitems (orderNo, ossanNo,"
				+ " amount, unitPrice, discount) "
				+ " values(?, ?, ?, ?, ? ) ";

		ResultSet generatedKeys = null;

		try (
			PreparedStatement ps = con.prepareStatement(sqlOrder, 
				Statement.RETURN_GENERATED_KEYS);
		) {
			ps.setString(1, ob.getInvoiceTitle());
			System.out.println("getInvoiceTitle:"+ob.getInvoiceTitle().toString());
			ps.setString(2, ob.getShippingAddress());		
			System.out.println("getShippingAddress:"+ob.getShippingAddress().toString());
			ps.setString(3, ob.getBno());
			System.out.println("getBno:"+ob.getBno().toString());
			ps.setString(4, ob.getTel());
			System.out.println("getTel:"+ob.getTel().toString());
			ps.setString(5, ob.getEmail());
			System.out.println("getEmail:"+ob.getEmail().toString());
			
			ps.setDouble(6, ob.getTotalAmount());
			System.out.println("getTotalAmount:"+ob.getTotalAmount().toString());
			
			ps.setString(7, ob.getComment());
			System.out.println("getComment:"+ob.getComment().toString());
			
			Timestamp ts = new Timestamp(ob.getOrderDate().getTime());
			ps.setTimestamp(8, ts);
			System.out.println("OrderDate:"+ts.toString());
			
//			ps.setString(9, ob.getCancelTag());
//			System.out.println("getCancelTag:"+ob.getCancelTag().toString());
//			
//			Timestamp ts2 = new Timestamp(ob.getShippingDate().getTime());
//			ps.setTimestamp(10, ts2);
//			System.out.println("ShippingDate:"+ts2.toString());
		
			ps.executeUpdate();
			int id = 0;
			// 取回剛才新增之訂單的主鍵值
			generatedKeys = ps.getGeneratedKeys();
			if (generatedKeys.next()) {
				id = generatedKeys.getInt(1);
			} else {
				throw new RuntimeException("OrderDaoImpl類別#insertOrder()無法取得新增之ossanorders表格的主鍵");
				
			}
//            int n = 0 ; 
			Set<OrderItemBean> items = ob.getItems();
			try (PreparedStatement ps2 = con.prepareStatement(sqlItem);) {
				for (OrderItemBean oib : items) {
					 //下列四個敘述為交易測試而編寫
//					 n++;
//					 if (n > 1) {
//					 	  System.out.println("發生例外 n>2");
//					 	  throw new RuntimeException("JDBC交易測試用");
//					 }
					ps2.setInt(1, id);
					System.out.println("orderId: "+String.valueOf(id));
					
					ps2.setInt(2, oib.getpKey());
					System.out.println("getpKey: "+String.valueOf(oib.getpKey()));
					
					ps2.setDouble(3, oib.getQuantity());
					System.out.println("getQuantity: "+String.valueOf(oib.getQuantity()));
					
					ps2.setDouble(4, oib.getUnitPrice());
					System.out.println("getUnitPrice: "+String.valueOf(oib.getUnitPrice()));
					
					ps2.setDouble(5, oib.getDiscount());
					System.out.println("getDiscount: "+String.valueOf(oib.getDiscount()));
					
					ps2.executeUpdate();
					ps2.clearParameters();
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new RuntimeException("OrderDaoImpl類別#insertOrder()發生SQL例外: " + ex.getMessage());
		}
	}

	public OrderBean getOrder(int orderNo) {
		OrderBean ob = null;
		DataSource ds = null;
		Set<OrderItemBean> set = null;
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup(GlobalService.JNDI_DB_NAME);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("OrderDaoImpl類別#getOrder()-1發生例外: " + ex.getMessage());
		}

		String sql = "SELECT * FROM ossanorders WHERE orderno = ? ";
		String sql1 = "SELECT a.seqno, a.orderno, a.ossanNo, a.amount, a.unitprice, a.discount , b.name, b.nickname FROM ossanorderitems a LEFT JOIN memberossan b ON a.ossanNo = b.seqno WHERE a.orderno = ? ";
		try (
			Connection con = ds.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			PreparedStatement ps1 = con.prepareStatement(sql1);
		) {
			ps.setInt(1, orderNo);
			try (
				ResultSet rs = ps.executeQuery();
			) {
				if (rs.next()) {
					Integer no = rs.getInt("orderNo");
					//String cancel = rs.getString("cancelTag");
					String invoiceTitle = rs.getString("invoiceTitle");
					String address = rs.getString("address");
					String BNO = rs.getString("BNO");
					
					String tel = rs.getString("tel");
					String email = rs.getString("email");
					double totalAmount = rs.getDouble("totalAmount");
					String Comment = rs.getString("Comment");
					
					Timestamp orderDate = rs.getTimestamp("orderDate");
					//JayTsao:尚未執行的功能
//					Date shipDate = rs.getDate("shippingDate");
					
					ob = new OrderBean(no, invoiceTitle, address, BNO, tel, email, totalAmount, Comment, orderDate);
				}
			}
			ps1.setInt(1, orderNo);
			try (
				ResultSet rs = ps1.executeQuery();
			) {
				set = new HashSet<>();
				while (rs.next()) {
					int seqNo = rs.getInt("a.seqNo");
					int orderNo2 = rs.getInt("a.orderNo");
					int ossanNo = rs.getInt("a.ossanNo");
					
					Integer amount = rs.getInt("a.amount");
					Double uPrice = rs.getDouble("a.unitPrice");
					Double discount = rs.getDouble("a.discount");
					
					String ossanName = rs.getString("b.name");
					String ossanNickname = rs.getString("b.nickname");
					
					OrderItemBean oi = new OrderItemBean(seqNo, orderNo2, ossanNo, 
							 amount, uPrice, discount, ossanName, ossanNickname);
					set.add(oi);
				}
				ob.setItems(set);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("OrderDaoImpl類別#getOrder()-2發生例外: " + ex.getMessage());
		}
		return ob;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public void setConnection(Connection con) {
		this.con = con;
	}

	@Override
	public List<OrderBean> getAllOrders() {
		DataSource ds = null;
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup(GlobalService.JNDI_DB_NAME);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("OrderDaoImpl類別#getOrder()-1發生例外: " + ex.getMessage());
		}
		List<OrderBean> list = new ArrayList<OrderBean>();
		String sql = "SELECT OrderNo FROM ossanorders";
		try (
			Connection con = ds.getConnection();
			PreparedStatement ps = con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
		) {
			while (rs.next()) {
				Integer no = rs.getInt(1);
				list.add(getOrder(no));
			}
		} catch(SQLException ex){
			throw new RuntimeException(ex);
		}
		return list;
	}

	@Override
	public List<OrderBean> getMemberOrders(String memberId) {
//		DataSource ds = null;
//		try {
//			Context ctx = new InitialContext();
//			ds = (DataSource) ctx.lookup(GlobalService.JNDI_DB_NAME);
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			throw new RuntimeException("OrderDaoImpl類別#getOrder()-1發生例外: " + ex.getMessage());
//		}
		List<OrderBean> list = new ArrayList<OrderBean>();
//		String sql = "SELECT OrderNo FROM ossanorders where memberId = ? Order by orderDate desc ";
//		try (
//				Connection con = ds.getConnection();
//				PreparedStatement ps = con.prepareStatement(sql);
//			) {
//				ps.setString(1, memberId);
//				try (
//					ResultSet rs = ps.executeQuery();
//				) {
//					while (rs.next()) {
//						Integer no = rs.getInt(1);
//						list.add(getOrder(no));
//					}
//				}
//		} catch(SQLException ex){
//			throw new RuntimeException(ex);
//		}
		return list;
	}
	
}