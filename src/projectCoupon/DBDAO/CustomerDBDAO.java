package projectCoupon.DBDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import projectCoupon.DAO.CustomerDAO;
import projectCoupon.Exception.CouponException;
import projectCoupon.Exception.CreateException;
import projectCoupon.Exception.CustomerException;
import projectCoupon.Exception.RemoveException;
import projectCoupon.Exception.UpdateException;
import projectCoupon.beans.Customer;
import projectCoupon.utils.ConnectionPool;

public class CustomerDBDAO implements CustomerDAO {
	private ConnectionPool pool;

	public CustomerDBDAO() throws CouponException {
		try {
			pool = ConnectionPool.getInstance();
		} catch (SQLException e) {
			throw new CouponException("connection failed");
		}
	}

	@Override
	public void insertCustomer(Customer Customer) throws CreateException {
		Connection connection = null;
		try {
			connection = pool.getConnection();
		} catch (CouponException e) {
			throw new CreateException("connection failed " + e.getMessage());
		}
		String sql = "insert into Customer(ID, CUST_NAME, PASSWORD) values (?,?,?)";

		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, Customer.getCustomerId());
			pstmt.setString(2, Customer.getCustomerName());
			pstmt.setString(3, Customer.getPassword());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new CreateException("Customer creation failed " + e.getMessage());
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
	public void removeCustomer(long customerId) throws RemoveException {
		Connection connection = null;
		try {
			connection = pool.getConnection();
		} catch (CouponException e) {
			throw new RemoveException("connection failed " + e.getMessage());
		}
		String pre1 = "DELETE FROM Customer WHERE ID=?";

		try {
			PreparedStatement pstm1 = connection.prepareStatement(pre1);
			connection.setAutoCommit(false);
			pstm1.setLong(1, customerId);
			pstm1.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new RemoveException("Database error " + e1.getMessage());
			}
			throw new RemoveException("failed to remove customer " + e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new RemoveException("connection failed " + e.getMessage());
			}
			try {
				pool.returnConnection(connection);
			} catch (CouponException e) {
				throw new RemoveException("connection failed " + e.getMessage());
			}

		}
	}

	@Override
	public void removeCustomer(Customer customer) throws RemoveException {
		removeCustomer(customer.getCustomerId());
	}

	@Override
	public void updateCustomer(Customer Customer) throws UpdateException {
		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (CouponException e) {
			throw new UpdateException("connection failed " + e.getMessage());
		}
		try {
			Statement stm = connection.createStatement();
			String sql = "UPDATE Customer " + " SET CUST_NAME='" + Customer.getCustomerName() + "', PASSWORD='"
					+ Customer.getPassword() + "' WHERE ID=" + Customer.getCustomerId();
			stm.executeUpdate(sql);
		} catch (SQLException e) {
			throw new UpdateException("update Customer failed " + e.getMessage());
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
	public Customer getCustomer(String customerName) throws CustomerException {
		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (CouponException e) {
			throw new CustomerException("connection failed " + e.getMessage());
		}
		Customer customer = new Customer();
		try {
			Statement stm = connection.createStatement();

			String sql = "SELECT * FROM Customer WHERE ID=" + customerName;
			ResultSet rs = stm.executeQuery(sql);
			rs.next();
			customer.setCustomerId(rs.getLong(1));
			customer.setCustomerName(rs.getString(2));
			customer.setPassword(rs.getString(3));

		} catch (SQLException e) {
			throw new CustomerException("unable to get Customer data " + e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new CustomerException("connection failed " + e.getMessage());
			}
			try {
				pool.returnConnection(connection);
			} catch (CouponException e) {
				throw new CustomerException("connection failed " + e.getMessage());
			}

		}
		return customer;
	}

	@Override
	public List<Customer> getAllCustomer() throws CustomerException {
		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (CouponException e) {
			throw new CustomerException("connection failed " + e.getMessage());
		}

		List<Customer> list = new ArrayList<Customer>();
		try {
			Statement stm = connection.createStatement();
			String sql = "SELECT * FROM CUSTOMER";
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				long customerId = rs.getLong(1);
				String customerName = rs.getString(2);
				String password = rs.getString(3);
				list.add(new Customer(customerId, customerName, password));
			}
		} catch (SQLException e) {
			throw new CustomerException("cannot get Customer data " + e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new CustomerException("connection failed " + e.getMessage());
			}
			try {
				pool.returnConnection(connection);
			} catch (Exception e) {
				throw new CustomerException("connection failed" + e.getMessage());
			}
		}
		return list;
	}

	@Override
	public Customer getCustomer(long CustomerId) throws CustomerException {
		try {
			try {
				pool = ConnectionPool.getInstance();
			} catch (SQLException e) {
				throw new CouponException("connection failed");
			}
		} catch (CouponException e) {
			throw new CustomerException("connection failed " + e.getMessage());
		}
		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (CouponException e) {
			throw new CustomerException("connection failed " + e.getMessage());
		}
		Customer customer = new Customer();
		try (Statement statement = connection.createStatement()) {
			String sql = "SELECT * FROM Customer WHERE ID=" + CustomerId;
			ResultSet resultSet = statement.executeQuery(sql);
			resultSet.next();
			customer.setCustomerId(resultSet.getLong(1));
			customer.setCustomerName(resultSet.getString(2));
			customer.setPassword(resultSet.getString(3));

		} catch (SQLException e) {
			throw new CustomerException("unable to get data, customerId: " + CustomerId);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new CustomerException("connection failed " + e.getMessage());
			}
			try {
				pool.returnConnection(connection);
			} catch (CouponException e) {
				throw new CustomerException("connection failed " + e.getMessage());
			}
		}
		return customer;
	}

	@Override
	public Customer login(String customerName, String password) throws CustomerException {
		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (CouponException e) {
			throw new CustomerException("connection failed " + e.getMessage());
		}
		Customer customer = null;
		try {
			String sql = "SELECT id, CUST_NAME, password FROM app.Customer WHERE CUST_NAME = ? and password = ?";
			PreparedStatement pstmt = connection.prepareStatement(sql);

			pstmt.setString(1, customerName);
			pstmt.setString(2, password);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				long customerId = rs.getLong("id");
				if (customerId > 0) {
					customer = new Customer();
					customer.setCustomerId(customerId);
					customer.setCustomerName(customerName);
					customer.setPassword(password);
					return customer;
				}
			}
			return null;

		} catch (SQLException e) {
			throw new CustomerException(" Login Failed " + e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new CustomerException("connection failed " + e.getMessage());
			}
			try {
				pool.returnConnection(connection);
			} catch (CouponException e) {
				throw new CustomerException("connection failed " + e.getMessage());
			}
		}
	}

	@Override
	public boolean isCustomerNameExists(String customerName) throws CouponException {
		Connection connection = pool.getConnection();
		try {
			String sql = "SELECT ID FROM Customer WHERE CUST_NAME = ? ";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, customerName);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			}
			return false;

		} catch (SQLException e) {
			throw new CouponException(" Failed to checking if Customer name already exists. " + e.getMessage());
		} finally {
			pool.returnConnection(connection);
		}
	}

}
