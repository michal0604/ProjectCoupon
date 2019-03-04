package projectCoupon.Customer_Coupon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import projectCoupon.ConnectionPool;
import projectCoupon.Exception.CouponException;
import projectCoupon.Exception.CreateException;
import projectCoupon.Exception.RemoveException;
import projectCoupon.Exception.UpdateException;

public class Customer_CouponDBDAO implements Customer_CouponDAO {

	private static final String AND = null;

	private ConnectionPool pool;

	public Customer_CouponDBDAO() throws CouponException {
		pool = ConnectionPool.getInstance();
	}

	@Override
	public void removeCustomer_Coupon(long custId, long coupId) throws RemoveException {
		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (Exception e) {
			throw new RemoveException("connection failed " + e.getMessage());
		}
		String sql = "DELETE FROM CUSTOMER_COUPON  WHERE customerId=? AND couponId=?";

		try {
			PreparedStatement stm = connection.prepareStatement(sql);
			{
			}
			connection.setAutoCommit(false);
			stm.setLong(1, custId);
			stm.setLong(2, coupId);
			stm.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new RemoveException("Database error " + e1.getMessage());
			}
			throw new RemoveException("failed to remove customer_Coupon " + e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new RemoveException("connection failed " + e.getMessage());
			}
			try {
				pool.returnConnection(connection);
			} catch (CouponException e) {
				throw new RemoveException("return collection failed");
			}
		}
	}

	public void insertCustomer_Coupon(long custId, long coupId) throws CreateException {
		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (Exception e1) {
			throw new CreateException("connection failed");
		}
		String sql = "INSERT INTO Customer_Coupon(customerId,couponId) VALUES(?,?)";

		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			{
			}
			pstmt.setLong(1, custId);
			pstmt.setLong(2, coupId);
			pstmt.executeUpdate();
			System.out.println("Customer_Coupon created" + custId);
		} catch (SQLException ex) {
			throw new CreateException("Customer_Coupon creation failed " + ex.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new CreateException("connection failed " + e.getMessage());
			}
			try {
				pool.returnConnection(connection);
			} catch (CouponException e) {
				throw new CreateException("connection failed " + e.getMessage());
			}
		}

	}

	@Override
	public List<Customer_Coupon> getAllCustomer_Coupon() throws CouponException, CreateException {
		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (Exception e) {
			throw new CouponException("connection failed "+e.getMessage());
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
			throw new CouponException("cannot get Customer_Coupon data " + e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new CouponException("connection failed " + e.getMessage());
			}
			try {
				pool.returnConnection(connection);
			} catch (CouponException e) {
				throw new CreateException("connection failed " + e.getMessage());
			}
		}
		return list;
	}

	@Override
	public List<Long> getCustomersByCouponId(long couponId) throws CouponException, CreateException {
		List<Long> list = new ArrayList<Long>();
		List<Customer_Coupon> allList = getAllCustomer_Coupon();
		for (Customer_Coupon iter : allList) {
			if (iter.getCouponId() == couponId) {
				list.add(iter.getCustomerId());
			}
		}
		return list;
	}

	@Override
	public List<Long> getCouponsByCustomerId(long custId) throws CouponException, CreateException {
		List<Long> list = new ArrayList<Long>();
		List<Customer_Coupon> allList = getAllCustomer_Coupon();
		for (Customer_Coupon iter : allList) {
			if (iter.getCustomerId() == custId) {
				list.add(iter.getCouponId());
			}
		}
		return list;

	}

	@Override
	public void updateCustomer_Coupon(long custId, long coupId) throws UpdateException {
		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (Exception e1) {
			throw new UpdateException("connection failed");
		}
		try {
			Statement stm = connection.createStatement();
			String sql = "UPDATE Customer_Coupon " + " SET customerId='" + custId + "', coupon_Id='" + coupId
					+ "' WHERE ID=" + custId + AND + "' WHERE ID=" + coupId;
			stm.executeUpdate(sql);
		} catch (SQLException e) {
			throw new UpdateException("unable to update table " + e.getMessage());
		}
		try {
			connection.close();
		} catch (SQLException e) {
			throw new UpdateException("connection failed " + e.getMessage());
		}
		try {
			pool.returnConnection(connection);
		} catch (Exception e) {
			throw new UpdateException("connection failed " + e.getMessage());
		}
	}

	@Override
	public void removeCustomer_CouponByCustomerId(long custId) throws RemoveException {

		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (Exception e) {
			throw new RemoveException("connection failed " + e.getMessage());
		}

		String sql = "delete from Customer_Coupon where customerId = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			connection.setAutoCommit(false);
			preparedStatement.setLong(1, custId);
			preparedStatement.executeUpdate();
			connection.commit();

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new RemoveException("DataBase error " + e1.getMessage());
			}
			throw new RemoveException("remove Customer_Coupon by customer Id failed " + e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new RemoveException("connction failed" + e.getMessage());
			}
			try {
				pool.returnConnection(connection);
			} catch (CouponException e) {
				throw new RemoveException("connction failed " + e.getMessage());
			}
		}

	}

	@Override
	public boolean isCouponPurchasedByCustomer(long custId, long coupId) throws CouponException {
		Connection connection = pool.getConnection();
		try {
			String sql = "SELECT couponId FROM Customer_Coupon WHERE customerId = ? AND couponId = ? ";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, custId);
			pstmt.setLong(2, coupId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			}
			return false;

		} catch (SQLException e) {
			throw new CouponException(
					"ERROR! Checking If Coupon Already Exists For Company is Failed. " + e.getMessage());
		} catch (Exception e) {
			throw new CouponException(
					"ERROR! Checking If Coupon Already Exists For Company is Failed. " + e.getMessage());
		} finally {
			pool.returnConnection(connection);
		}
	}

	@Override
	public void removeCustomer_CouponByCoupId(long coupId) throws RemoveException {
		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (Exception e) {
			throw new RemoveException("connection failed " + e.getMessage());
		}

		String sql = "delete from Customer_Coupon where couponId = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			connection.setAutoCommit(false);
			preparedStatement.setLong(1, coupId);
			preparedStatement.executeUpdate();
			connection.commit();

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new RemoveException("DataBase error " + e1.getMessage());
			}
			throw new RemoveException("remove Customer_Coupon by coupon Id failed " + e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new RemoveException("connction failed" + e.getMessage());
			}
			try {
				pool.returnConnection(connection);
			} catch (CouponException e) {
				throw new RemoveException("connction failed " + e.getMessage());
			}
		}

	}
}