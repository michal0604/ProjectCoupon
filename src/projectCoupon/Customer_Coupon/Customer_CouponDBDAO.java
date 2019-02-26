package projectCoupon.Customer_Coupon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import projectCoupon.ConnectionPool;
import projectCoupon.Coupons.Coupon;
import projectCoupon.Customer.Customer;
import projectCoupon.Exception.CouponException;

public class Customer_CouponDBDAO implements Customer_CouponDAO {

	private static final String AND = null;
	
	private ConnectionPool pool;
	public  Customer_CouponDBDAO()throws CouponException {
		pool = ConnectionPool.getInstance();
	}
	

	@Override
	public void removeCustomer_Coupon(Customer_Coupon customer_coupon) throws Exception {
		Connection connection=pool.getConnection();
		String sql = "DELETE FROM CUSTOMER_COUPON  WHERE CUST_ID=? AND COUPON_ID=?";

		try {
			PreparedStatement stm = connection.prepareStatement(sql); {
		}
			connection.setAutoCommit(false);
			stm.setLong(1, customer_coupon.getCust_id());
			stm.setLong(2, customer_coupon.getCoupon_id());
			stm.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new Exception("Database error");
			}
			throw new Exception("failed to remove customer_Coupon");
		} finally {
			pool.closeAllConnections(connection);
		}
	}
	

		
	
	public void insertCustomer_Coupon(Customer_Coupon customer_coupon) throws Exception {
		Connection connection=pool.getConnection();
		//Database.createTables(con);
		String sql = "INSERT INTO Customer_Coupon(CUST_ID,COUPON_ID) VALUES(?,?)";
		
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql); {
		}	
			pstmt.setLong(1,customer_coupon.getCust_id());
			pstmt.setLong(2, customer_coupon.getCoupon_id());	
			pstmt.executeUpdate();
			System.out.println("Customer_Coupon created" + customer_coupon.toString());
		} catch (SQLException ex) {
			System.out.println(ex.getLocalizedMessage());
			throw new Exception("Customer_Coupon creation failed");
		} finally
{
			
			pool.closeAllConnections(connection);
		}
		
	}
	
	
	
	@Override
	public List<Customer_Coupon> getAllCustomer_Coupon() throws Exception {
		Connection connection=pool.getConnection();
		List<Customer_Coupon> set = new ArrayList<Customer_Coupon>();
		
		try { 
			Statement stm = connection.createStatement();
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
			pool.closeAllConnections(connection);
		}
		return set;
	}

	@Override
	public List<Customer_Coupon> getCustomersByCouponId(long couponId) throws Exception {
		Connection connection=pool.getConnection();
		List<Customer_Coupon> list = new ArrayList<Customer_Coupon>();
		try {
			//Statement stm = connection.createStatement();
			//String sql = "SELECT CUST_ID FROM CUSTOMER_COUPON WHERE COUPON_ID=?";
			List<Customer_Coupon> allList = getAllCustomer_Coupon();
			for(Customer_Coupon iter:allList) {
				if(iter.getCoupon_id() == couponId) {
					list.add(iter);
				}
			
		}
		}catch (Exception e) {
			System.out.println(e);
		}
		pool.closeAllConnections(connection);
		
		return list;
	}


	@Override
	public List<Customer_Coupon> getCouponsByCustomerId(long custId) throws Exception {
		Connection connection=pool.getConnection();
		List<Customer_Coupon>list=new ArrayList<Customer_Coupon>();
		try {
			//Statement stm = connection.createStatement();
			//String sql = "SELECT COUPON_ID FROM CUSTOMER_COUPON WHERE CUST_ID=?";
			
			List<Customer_Coupon> allList = getAllCustomer_Coupon();
			for(Customer_Coupon iter:allList) {
				if(iter.getCust_id()==custId) {
					list.add(iter);
				}
		}
		}catch (Exception e) {
			System.out.println(e);
		}
		pool.closeAllConnections(connection);
		
		return list;
		
	}




	@Override
	public void updateCustomer_Coupon(Customer_Coupon customer_Coupon) throws Exception {
		Connection connection=pool.getConnection();
		try { 
			Statement stm = connection.createStatement();
			String sql = "UPDATE Customer_Coupon " + " SET cust_Id='" + customer_Coupon.getCust_id() + "', coupon_Id='"
					+ customer_Coupon.getCoupon_id()+ "' WHERE ID=" + customer_Coupon.getCust_id()+ AND + "' WHERE ID=" + customer_Coupon.getCoupon_id();
			stm.executeUpdate(sql);
		} 
		catch (SQLException e) {
			throw new Exception("update error");
		}
		pool.closeAllConnections(connection);
	}


	@Override
	public void insertCustomer_Coupon(Customer customer, Coupon coupon) throws Exception {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void removeCustomer_Coupon(Customer customer, Coupon coupon) throws Exception {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void removeCustomer_Coupon(Customer customer) throws Exception {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void removeCustomer_Coupon(Coupon coupon) throws Exception {
		// TODO Auto-generated method stub
		
	}
		
	}
	
