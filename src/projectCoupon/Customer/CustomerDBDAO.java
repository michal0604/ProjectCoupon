package projectCoupon.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import projectCoupon.ConnectionPool;

public class CustomerDBDAO implements CustomerDAO {
	private ConnectionPool pool;
	
	public CustomerDBDAO() throws projectCoupon.Exception.CouponException {
		pool=ConnectionPool.getInstance();
	}

	@Override
	public void insertCustomer(Customer Customer) throws Exception {
		Connection connection = pool.getConnection();
		String sql = "INSERT INTO Customer (ID, CUST_NAME,PASSWORD) VALUES(?,?)";

		try {
			PreparedStatement pstmt = connection.prepareStatement(sql); {
		}
			pstmt.setLong(1, Customer.getCustomerId());
			pstmt.setString(2, Customer.getCustomerName());
			pstmt.setString(3, Customer.getPassword());

			pstmt.executeUpdate();
			System.out.println("Customer created" + Customer.toString());
		} catch (SQLException ex) {
			System.out.println(ex.getLocalizedMessage());
			throw new Exception("Customer creation failed");
		} finally {
			connection.close();
			pool.returnConnection(connection);
		}
	}

	@Override
	public void removeCustomer(Customer Customer) throws Exception {
		Connection connection=pool.getConnection();
		String pre1 = "DELETE FROM Customer WHERE id=?";

		try {
			PreparedStatement pstm1 = connection.prepareStatement(pre1); {
		}
			connection.setAutoCommit(false);
			pstm1.setLong(1, Customer.getCustomerId());
			pstm1.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new Exception("Database error");
			}
			throw new Exception("failed to remove customer");
		} finally {
			connection.close();
			pool.returnConnection(connection);
		}
	}

	@Override
	public void updateCustomer(Customer Customer) throws Exception {
	Connection connection=pool.getConnection();
		try {
		Statement stm = connection.createStatement(); {
			String sql = "UPDATE Customer " + " SET CUST_NAME='" + Customer.getCustomerName() + "', PASSWORD='"
					+ Customer.getPassword() + "' WHERE ID=" + Customer.getCustomerId();
			stm.executeUpdate(sql);
		}
		} catch (SQLException e) {
			throw new Exception("update Customer failed");
		}
		connection.close();
		pool.returnConnection(connection);
	}

	@Override
	public Customer getCustomer(String custName) throws Exception {
		Connection connection = pool.getConnection();
		Customer customer = new Customer();
		try {
			Statement stm = connection.createStatement();

			String sql = "SELECT * FROM Customer WHERE ID=" + custName;
			ResultSet rs = stm.executeQuery(sql);
			rs.next();
			customer.setCustomerId(rs.getLong(1));
			customer.setCustomerName(rs.getString(2));
			customer.setPassword(rs.getString(3));

		} catch (SQLException e) {
			throw new Exception("unable to get Customer data");
		} finally {
			connection.close();
			pool.returnConnection(connection);
		}
		return customer;
	}

	@Override
	public List<Customer> getAllCustomer() throws Exception {
		Connection connection = pool.getConnection();
		List<Customer> set = new ArrayList<Customer>();
		try {
			Statement stm = connection.createStatement();
			String sql = "SELECT * FROM CUSTOMER";
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				long customerId = rs.getLong(1);
				String customerName = rs.getString(2);
				String password = rs.getString(3);
				set.add(new Customer(customerId, customerName, password));
			}
		} catch (SQLException e) {
			System.out.println(e);
			throw new Exception("cannot get Customer data");
		} finally {
			connection.close();
			pool.returnConnection(connection);
		}
		return set;
	}

	@Override
	public void removeCustomer(long custId) throws Exception {
		removeCustomer(getCustomer(custId));		
	}

	

	@Override
	public boolean isCustomerNameExists(String custName) throws projectCoupon.Exception.CouponException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Customer getCustomer(long custId) throws Exception {
		pool = ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		Customer customer = new Customer();
		try (Statement statement = connection.createStatement()) {
			String sql = "SELECT * FROM Customer WHERE ID=" + custId;
			ResultSet resultSet = statement.executeQuery(sql);
			resultSet.next();
			customer.setCustomerId(resultSet.getLong(1));
			customer.setCustomerName(resultSet.getString(2));
			customer.setPassword(resultSet.getString(3));

		} catch (SQLException e) {
			throw new Exception("unable to get data, customerId: " + custId);
		} finally {
			connection.close();
			pool.returnConnection(connection);
		}
		return customer;
	}

	

}
