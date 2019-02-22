package projectCoupon.Customer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Exception.CouponException;
import projectCoupon.ConnectionPool;
import projectCoupon.Database;

public class CustomerDBDAO implements CustomerDAO {
	private ConnectionPool pool;
	
	public CustomerDBDAO() throws CouponException {
		pool=ConnectionPool.getInstance();
	}

	@Override
	public void insertCustomer(Customer Customer) throws Exception {
		Connection connection = pool.getConnection();
		String sql = "INSERT INTO Customer (CUST_NAME,PASSWORD) VALUES(?,?)";

		try {
			PreparedStatement pstmt = connection.prepareStatement(sql); {
		}

			pstmt.setString(1, Customer.getCustomerName());
			pstmt.setString(2, Customer.getPassword());

			pstmt.executeUpdate();
			System.out.println("Customer created" + Customer.toString());
		} catch (SQLException ex) {
			System.out.println(ex.getLocalizedMessage());
			throw new Exception("Customer creation failed");
		} finally {
			pool.closeAllConnections(connection);
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
			pstm1.setLong(1, Customer.getId());
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
			pool.closeAllConnections(connection);
		}
	}

	@Override
	public void updateCustomer(Customer Customer) throws Exception {
	Connection connection=pool.getConnection();
		try {
		Statement stm = connection.createStatement(); {
			String sql = "UPDATE Customer " + " SET CUST_NAME='" + Customer.getCustomerName() + "', PASSWORD='"
					+ Customer.getPassword() + "' WHERE ID=" + Customer.getId();
			stm.executeUpdate(sql);
		}
		} catch (SQLException e) {
			throw new Exception("update Customer failed");
		}
		pool.closeAllConnections(connection);
	}

	@Override
	public Customer getCustomer(long id) throws Exception {
		Connection connection = pool.getConnection();
		Customer customer = new Customer();
		try {
			Statement stm = connection.createStatement();

			String sql = "SELECT * FROM Customer WHERE ID=" + id;
			ResultSet rs = stm.executeQuery(sql);
			rs.next();
			customer.setId(rs.getLong(1));
			customer.setCustomerName(rs.getString(2));
			customer.setPassword(rs.getString(3));

		} catch (SQLException e) {
			throw new Exception("unable to get Customer data");
		} finally {
			pool.closeAllConnections(connection);
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
				long id = rs.getLong(1);
				String customerName = rs.getString(2);
				String password = rs.getString(3);
				set.add(new Customer(id, customerName, password));
			}
		} catch (SQLException e) {
			System.out.println(e);
			throw new Exception("cannot get Customer data");
		} finally {
			pool.closeAllConnections(connection);
		}
		return set;
	}

}
