package Customer_Coupon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import projectCoupon.Database;

public class Customer_CouponDBDAO implements Customer_CouponDAO {

	Connection con;
	private Connection connection;

	@Override
	public void removeCustomer_Coupon(Customer_Coupon customer_coupon) throws Exception {
		con = DriverManager.getConnection(Database.getDBUrl());
		String sql = "DELETE FROM CUSTOMER_COUPON  WHERE CUST_ID=? AND COUPON_ID=?";

		try (PreparedStatement stm = con.prepareStatement(sql);) {
			con.setAutoCommit(false);
			stm.setLong(1, customer_coupon.getCust_id());
			stm.setLong(2, customer_coupon.getCoupon_id());
			stm.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				throw new Exception("Database error");
			}
			throw new Exception("failed to remove customer_Coupon");
		} finally {
			con.close();
		}
	}
	

		
	
	public void insertCustomer_Coupon(Customer_Coupon customer_coupon) throws Exception {
		con = DriverManager.getConnection(Database.getDBUrl());
		//Database.createTables(con);
		String sql = "INSERT INTO Customer_Coupon(CUST_ID,COUPON_ID) VALUES(?,?)";
		
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {

			
			pstmt.setLong(1,customer_coupon.getCust_id());
			pstmt.setLong(2, customer_coupon.getCoupon_id());
			
			
			pstmt.executeUpdate();
			System.out.println("Customer_Coupon created" + customer_coupon.toString());
		} catch (SQLException ex) {
			System.out.println(ex.getLocalizedMessage());
			throw new Exception("Customer_Coupon creation failed");
		} finally
{
			
			con.close();
		}
		
	}
	
	
	
	@Override
	public List<Customer_Coupon> getAllCustomer_Coupon() throws Exception {
		con = DriverManager.getConnection(Database.getDBUrl());
		List<Customer_Coupon> set = new ArrayList<Customer_Coupon>();
		
		try { 
			Statement stm = con.createStatement();
			String sql = "SELECT * FROM CUSTOMER_COUPON"; 
		    ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				long cust_id = rs.getLong(1);
				long coupon_id = rs.getLong(2);
				
				set.add(new Customer_Coupon(cust_id, coupon_id));
			}
		} catch (SQLException e) {
			System.out.println(e);
			throw new Exception("cannot get Customer_Coupon data");
		} finally {
			con.close();
		}
		return set;
	}




	@Override
	public List<Customer_Coupon> getCustomersByCouponId(long couponId) throws Exception {
		con = DriverManager.getConnection(Database.getDBUrl());
		List<Customer_Coupon> list = new ArrayList<Customer_Coupon>();
		try {
			Statement stm = con.createStatement();
			String sql = "SELECT CUST_ID FROM CUSTOMER_COUPON WHERE COUPON_ID=?";
			List<Customer_Coupon> allList = getAllCustomer_Coupon();
			for(Customer_Coupon iter:allList) {
				if(iter.getCoupon_id() == couponId) {
					list.add(iter);
				}
			
		}
		}catch (Exception e) {
			System.out.println(e);
		}
		
		
		return list;
	}





	@Override
	public List<Customer_Coupon> getCouponsByCustomerId(long custId) throws Exception {
		List<Customer_Coupon>list=new ArrayList<Customer_Coupon>();
		List<Customer_Coupon> allList = getAllCustomer_Coupon();
		for(Customer_Coupon iter:allList) {
			if(iter.getCust_id()==custId) {
				list.add(iter);
			}
		}
		return list;
		
	}
	
}