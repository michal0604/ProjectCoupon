package projectCoupon.Customer_Coupon;

import java.nio.channels.NonWritableChannelException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import projectCoupon.ConnectionPool;
import projectCoupon.Coupons.Coupon;
import projectCoupon.Coupons.couponType;
import projectCoupon.Customer.Customer;
import projectCoupon.Exception.CouponException;
import projectCoupon.Exception.CreateException;
import projectCoupon.Exception.RemoveException;
import projectCoupon.Exception.UpdateException;

public class Customer_CouponDBDAO implements Customer_CouponDAO {

	private static final String AND = null;
	
	private ConnectionPool pool;
	public  Customer_CouponDBDAO()throws CouponException {
		pool = ConnectionPool.getInstance();
	}
	

	@Override
	public void removeCustomer_Coupon(Customer customer ,Coupon coupon) throws RemoveException {
		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (Exception e2) {
			throw new RemoveException("connection failed");
		}
		String sql = "DELETE FROM CUSTOMER_COUPON  WHERE customerId=? AND couponId=?";

		try {
			PreparedStatement stm = connection.prepareStatement(sql); {
		}
			connection.setAutoCommit(false);
			stm.setLong(1, customer.getCustomerId());
			stm.setLong(2, coupon.getCouponId());
			stm.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new RemoveException("Database error");
			}
			throw new RemoveException("failed to remove customer_Coupon");
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new RemoveException("connection failed");
			}
			try {
				pool.returnConnection(connection);
			} catch (Exception e) {
				throw new RemoveException("return collection failed");
			}
		}
	}
	

		
	
	public void insertCustomer_Coupon(Customer customer,Coupon coupon) throws CreateException {
		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (Exception e1) {
			throw new CreateException("connection failed");
		}
		String sql = "INSERT INTO Customer_Coupon(customerId,couponId) VALUES(?,?)";
		
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql); {
		}	
			pstmt.setLong(1,customer.getCustomerId());
			pstmt.setLong(2, coupon.getCouponId());	
			pstmt.executeUpdate();
			System.out.println("Customer_Coupon created" + customer.toString());
		} catch (SQLException ex) {
			System.out.println(ex.getLocalizedMessage());
			throw new CreateException("Customer_Coupon creation failed");
		} finally
{
			try {
				connection.close();
			} catch (SQLException e) {
				throw new CreateException("connection failed");
			}
			try {
				pool.returnConnection(connection);
			} catch (Exception e) {
				throw new CreateException("connection failed");
			}
		}
		
	}
	
	// TO DO- do we need to do getallcoupon, gettallcustomer instead??
	
	@Override
	public List<Customer_Coupon> getAllCustomer_Coupon() throws CouponException {
		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (Exception e1) {
			throw new CouponException("connection failed");
		}
		List<Customer_Coupon> list = new ArrayList<Customer_Coupon>();
		
		try { 
			Statement stm = connection.createStatement();
			String sql = "SELECT * FROM CUSTOMER_COUPON"; 
		    ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				long cust_id = rs.getLong(1);
				long coupon_id = rs.getLong(2);
				
				list.add(new Customer_Coupon(cust_id, coupon_id));
			}
		} catch (SQLException e) {
			System.out.println(e);
			throw new CouponException("cannot get Customer_Coupon data");
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new CouponException("connection failed");
			}
			try {
				pool.returnConnection(connection);
			} catch (Exception e) {
				throw new CouponException("connection failed");
			}
		}
		return list;
	}

	@Override
	public List<Customer_Coupon> getCustomersByCouponId(long couponId) throws CouponException {
		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (Exception e1) {
			throw new CouponException("connection failed");
		}
		List<Customer_Coupon> list = new ArrayList<Customer_Coupon>();
		try {
			Statement stm = connection.createStatement();
			String sql = "SELECT customerId FROM CUSTOMER_COUPON WHERE couponId=?";
			List<Customer_Coupon> allList = getAllCustomer_Coupon();
			for(Customer_Coupon iter:allList) {
				if(iter.getCouponId() == couponId) {
					list.add(iter);
				}
			
		}
		}catch (Exception e) {
			throw new CouponException("unable to get customers by couponId");
		}
		try {
			connection.close();
		} catch (SQLException e) {
			throw new CouponException("connection failed");
		}
		try {
			pool.returnConnection(connection);
		} catch (Exception e) {
			throw new CouponException("connection failed");
		}
		
		return list;
	}


	@Override
	public List<Customer_Coupon> getCouponsByCustomerId(long custId) throws CouponException {
		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (Exception e1) {
			throw new CouponException("connection failed");
		}
		List<Customer_Coupon>list=new ArrayList<Customer_Coupon>();
		try {
			Statement stm = connection.createStatement();
			String sql = "SELECT couponId FROM CUSTOMER_COUPON WHERE customerId=?";
			
			List<Customer_Coupon> allList = getAllCustomer_Coupon();
			for(Customer_Coupon iter:allList) {
				if(iter.getCustomerId()==custId) {
					list.add(iter);
				}
		}
		}catch (Exception e) {
			throw new CouponException("unable to get coupon by customerId");
		}
		try {
			connection.close();
		} catch (SQLException e) {
			throw new CouponException("connection failed");
		}
		try {
			pool.returnConnection(connection);
		} catch (Exception e) {
			throw new CouponException("connection failed");
		}
		
		return list;
		
	}




	@Override
	public void updateCustomer_Coupon(Customer customer,Coupon coupon) throws UpdateException {
		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (Exception e1) {
			throw new UpdateException("connection failed");
		}
		try { 
			Statement stm = connection.createStatement();
			String sql = "UPDATE Customer_Coupon " + " SET customerId='" + customer.getCustomerId() + "', coupon_Id='"
					+ coupon.getCouponId()+ "' WHERE ID=" + customer.getCustomerId()+ AND + "' WHERE ID=" + coupon.getCouponId();
			stm.executeUpdate(sql);
		} 
		catch (SQLException e) {
			throw new UpdateException("unable to update table");
		}
		try {
			connection.close();
		} catch (SQLException e) {
			throw new UpdateException("connection failed");
		}
		try {
			pool.returnConnection(connection);
		} catch (Exception e) {
			throw new UpdateException("connection failed");
		}
	}




	


	@Override
	public void removeCustomer_Coupon(Customer customer) throws RemoveException {

		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (Exception e1) {
			throw new RemoveException("connection failed");
		}
		

		String sql = "delete from Customer_Coupon where customerId = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			connection.setAutoCommit(false);
			preparedStatement.setLong(1, customer.getCustomerId());
			preparedStatement.executeUpdate();
			connection.commit();
			System.out.println("Customer_Coupon remove succeeded. customerId: " + customer.getCustomerId());

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (Exception exception) {
				throw new RemoveException("DataBase error");
			}
			throw new RemoveException("remove Customer_Coupon by customerId failed");
			
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
          throw new RemoveException("connction failed");
			}
			try {
				pool.returnConnection(connection);
			} catch (Exception e) {
				 throw new RemoveException("connction failed");
			}
	}
	
	}

	@Override
	public void removeCustomer_Coupon(Coupon coupon) throws RemoveException {
		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (Exception e1) {
			throw new RemoveException("connection failed");
		}

		String sql = "delete from Customer_Coupon where couponId = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			connection.setAutoCommit(false); 
			preparedStatement.setLong(1, coupon.getCouponId());
			preparedStatement.executeUpdate();
			connection.commit();
			System.out.println("Customer_Coupon remove succeeded. couponId: " + coupon.getCouponId());

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (Exception exception) {
				throw new RemoveException("connection failed");
			}
			throw new RemoveException("remove customer_coupon by coupon failed");
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new RemoveException("connection failed");
			}
			try {
				pool.returnConnection(connection);
			} catch (Exception e) {
				throw new RemoveException("connection failed");
			}
		}

	}
		
	}


	
		
	
	
