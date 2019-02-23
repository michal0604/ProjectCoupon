package projectCoupon.Customer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import projectCoupon.Database;

public class CustomerDBDAO implements CustomerDAO {
	Connection con;

	@Override
	public void insertCustomer(Customer Customer) throws Exception {
		con = DriverManager.getConnection(Database.getDBUrl());
		// Database.createTables(con);
		String sql = "INSERT INTO Customer (CUST_NAME,PASSWORD) VALUES(?,?)";

		try (PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setString(1, Customer.getCustomerName());
			pstmt.setString(2, Customer.getPassword());

			pstmt.executeUpdate();
			System.out.println("Customer created" + Customer.toString());
		} catch (SQLException ex) {
			System.out.println(ex.getLocalizedMessage());
			throw new Exception("Customer creation failed");
		} finally {
			con.close();
		}
	}

	@Override
	public void removeCustomer(Customer Customer) throws Exception {
		con = DriverManager.getConnection(Database.getDBUrl());
		String pre1 = "DELETE FROM Customer WHERE id=?";

		try (PreparedStatement pstm1 = con.prepareStatement(pre1);) {
			con.setAutoCommit(false);
			pstm1.setLong(1, Customer.getId());
			pstm1.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				throw new Exception("Database error");
			}
			throw new Exception("failed to remove customer");
		} finally {
			con.close();
		}
	}

	@Override
	public void updateCustomer(Customer Customer) throws Exception {
		con = DriverManager.getConnection(Database.getDBUrl());
		try (Statement stm = con.createStatement()) {
			String sql = "UPDATE Customer " + " SET CUST_NAME='" + Customer.getCustomerName() + "', PASSWORD='"
					+ Customer.getPassword() + "' WHERE ID=" + Customer.getId();
			stm.executeUpdate(sql);
		} catch (SQLException e) {
			throw new Exception("update Customer failed");
		}
		con.close();
	}

	@Override
	public Customer getCustomer(long id) throws Exception {
		con = DriverManager.getConnection(Database.getDBUrl());
		Customer customer = new Customer();
		try {
			Statement stm = con.createStatement();

			String sql = "SELECT * FROM Customer WHERE ID=" + id;
			ResultSet rs = stm.executeQuery(sql);
			rs.next();
			customer.setId(rs.getLong(1));
			customer.setCustomerName(rs.getString(2));
			customer.setPassword(rs.getString(3));

		} catch (SQLException e) {
			throw new Exception("unable to get Customer data");
		} finally {
			con.close();
		}
		return customer;
	}

	@Override
	public List<Customer> getAllCustomer() throws Exception {
		con = DriverManager.getConnection(Database.getDBUrl());
		List<Customer> set = new ArrayList<Customer>();
		try {
			Statement stm = con.createStatement();
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
			con.close();
		}
		return set;
	}

}
