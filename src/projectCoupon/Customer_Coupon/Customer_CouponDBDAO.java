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
		try {
			pool = ConnectionPool.getInstance();
		} catch (SQLException e) {
			throw new CouponException("connection failed");
		}
	}

	@Override
	public void removeCustomer_Coupon(long customerId, long couponId) throws RemoveException {
		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (Exception e) {
			throw new RemoveException("connection failed " + e.getMessage());
		}
		String sql = "DELETE FROM CUSTOMER_COUPON  WHERE customer_ID=? AND coupon_Id=?";

		try {
			PreparedStatement stm = connection.prepareStatement(sql);
			{
			}
			connection.setAutoCommit(false);
			stm.setLong(1, customerId);
			stm.setLong(2, couponId);
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

	public void insertCustomer_Coupon(long customerId, long couponId) throws CreateException {
		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (Exception e1) {
			throw new CreateException("connection failed");
		}
		String sql = "INSERT INTO Customer_Coupon(Customer_ID, Coupon_ID) VALUES(?,?)";

		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			{
			}
			pstmt.setLong(1, customerId);
			pstmt.setLong(2, couponId);
			pstmt.executeUpdate();
			System.out.println("Customer_Coupon created" + customerId);
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
				long customerId = rs.getLong(1);
				long couponId = rs.getLong(2);
				list.add(new Customer_Coupon(customerId, couponId));
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
	public List<Long> getCouponsByCustomerId(long customerId) throws CouponException, CreateException {
		List<Long> list = new ArrayList<Long>();
		List<Customer_Coupon> allList = getAllCustomer_Coupon();
		for (Customer_Coupon iter : allList) {
			if (iter.getCustomerId() == customerId) {
				list.add(iter.getCouponId());
			}
		}
		return list;

	}

	@Override
	public void updateCustomer_Coupon(long customerId, long couponId) throws UpdateException {
		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (Exception e1) {
			throw new UpdateException("connection failed");
		}
		try {
			Statement stm = connection.createStatement();
			String sql = "UPDATE Customer_Coupon " + " SET customer_Id='" + customerId + "', coupon_Id='" + couponId
					+ "' WHERE ID=" + customerId + AND + "' WHERE ID=" + couponId;
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
	public void removeCustomer_CouponByCustomerId(long customerId) throws RemoveException {

		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (Exception e) {
			throw new RemoveException("connection failed " + e.getMessage());
		}

		String sql = "delete from Customer_Coupon where customer_Id = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			connection.setAutoCommit(false);
			preparedStatement.setLong(1, customerId);
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
	public boolean isCouponPurchasedByCustomer(long customerId, long couponId) throws CouponException {
		Connection connection = pool.getConnection();
		try {
			String sql = "SELECT coupon_Id FROM Customer_Coupon WHERE customer_Id = ? AND coupon_Id = ? ";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, customerId);
			pstmt.setLong(2, couponId);
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
	public void removeCustomer_CouponByCoupId(long couponId) throws RemoveException {
		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (Exception e) {
			throw new RemoveException("connection failed " + e.getMessage());
		}

		String sql = "delete from Customer_Coupon where ID = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			connection.setAutoCommit(false);
			preparedStatement.setLong(1, couponId);
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