package projectCoupon.db;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import projectCoupon.exception.CouponException;
import projectCoupon.exception.CreateException;
import projectCoupon.exception.RemoveException;
import projectCoupon.utils.ConnectionPool;

public class Database {

	public static String getDriverData() {
		return "org.apache.derby.jdbc.ClientDriver";
	}

	public static String getDBUrl() {
		return "jdbc:derby://localhost:3301/JBDB;create=true";
	}

	private static ConnectionPool Pool;

	public Database() throws CouponException, SQLException {
		Pool = ConnectionPool.getInstance();
	}

	public static void dropTableifNeeded() throws RemoveException, SQLException{
		String sql;
		Statement stmt = null;
		try {
			Pool=ConnectionPool.getInstance();
		} catch (CouponException e2) {
			throw new RemoveException("connection failed");
		}
		Connection connection;
		try {
			connection = Pool.getConnection();
		} catch (CouponException e2) {
			throw new RemoveException("connection failed");
		}
		
		try {
			stmt = connection.createStatement();
		} catch (SQLException e1) {
			throw new RemoveException("createstatment is failed");
		}
		
		try {
			sql = "DROP TABLE Customer_Coupon";
			stmt.executeUpdate(sql);
			System.out.println("Droped Customer_Coupon Table");
		} catch (SQLException e) {
			throw new RemoveException("Customer_Coupon Table did not drop: " + e.getMessage());
		}

		try {
			sql = "DROP TABLE Company_Coupon";
			stmt.executeUpdate(sql);
			System.out.println("Droped Company_Coupon Table");
		} catch (SQLException e) {
			throw new RemoveException("Company_Coupon Table did not drop: " + e.getMessage());
		}
		try {
			sql = "DROP TABLE Company";
			stmt.executeUpdate(sql);
			System.out.println("Droped Company Table");
		} catch (SQLException e) {
			throw new RemoveException("Company Table did not drop: " + e.getMessage());
		}
		try {
			sql = "DROP TABLE CUSTOMER";
			stmt.executeUpdate(sql);
			System.out.println("Droped Customer Table");
		} catch (SQLException e) {
			throw new RemoveException("Customer Table did not drop: " + e.getMessage());
		}
		try {
			sql = "DROP TABLE Coupon";
			stmt.executeUpdate(sql);
			System.out.println("Droped COUPON Table");
		} catch (SQLException e) {
			throw new RemoveException("COUPON Table did not exist");
		}
		try {
			connection.close();
		} catch (SQLException e) {
			throw new RemoveException("connection close failed");
		}
		try {
			Pool.returnConnection(connection);
		} catch (Exception e) {
			throw new RemoveException("return connection doesnt excess");
		}

	}

	public static void createTables() throws SQLException, CreateException {

		String sql;
		
		try {
			Pool=ConnectionPool.getInstance();
		} catch (CouponException e2) {
			throw new CreateException("connection failed");
		}
		Connection con;
		try {
			con = Pool.getConnection();
		} catch (CouponException e2) {
			throw new CreateException("connection failed");
		}
		try {
			Statement stmt = con.createStatement();

			// create Company table
			sql = "CREATE TABLE Company(companyId bigint not null primary key generated always as identity(start with 1, increment by 1),"
					+ "compName varchar(50) not null," + "PASSWORD varchar(50) not null,"
					+ "EMAIL varchar(50) not null)";

			stmt.executeUpdate(sql);
			System.out.println("success:" + sql);
		} catch (SQLException e) {
			throw new CreateException("create company didn't succeed");
		}
		try {
			Statement stmt2 = con.createStatement();
			sql = "CREATE TABLE CUSTOMER("
					+ "ID bigint not null primary key generated always as identity(start with 1, increment by 1), "
					+ "CUST_NAME varchar(50) not null, " + "PASSWORD varchar(50) not null)";
			stmt2.executeUpdate(sql);
			System.out.println("success:" + sql);
		} catch (SQLException e) {
			throw new CreateException("create customer didn't succeed");
		}

		try {
			java.sql.Statement stm = con.createStatement();

			sql = "CREATE TABLE Coupon("
					+ "ID bigint not null primary key generated always as identity(start with 1, increment by 1),"
					+ "TITLE varchar(50) not null," + "START_DATE DATE not null," + "END_DATE DATE not null,"
					+ "AMOUNT int not null," + "TYPE varchar(30) not null, " + "MESSAGE varchar(50) not null,"
					+ "PRICE float not null," + "IMAGE varchar(50) not null)";

			stm.executeUpdate(sql);
			System.out.println("success:" + sql);
		} catch (SQLException e) {
			throw new CreateException("create coupon didn't succeed");
		}

		// create join table Customer_Coupon
		try {
			java.sql.Statement stm = con.createStatement();
			sql = "CREATE TABLE Customer_Coupon(" + "CUST_ID bigint not null REFERENCES CUSTOMER(ID),"
					+ "COUPON_ID bigint not null REFERENCES COUPON(ID)," + "PRIMARY KEY(COUPON_ID, CUST_ID))";

			stm.executeUpdate(sql);
			System.out.println("success:" + sql);

		} catch (SQLException e) {
			throw new CreateException("create customer_coupon didn't succeed");

		}

		// create join table Company_Coupon
		try {
			java.sql.Statement stm = con.createStatement();
			sql = "CREATE TABLE Company_Coupon(" + "companyId bigint not null REFERENCES Company(companyId),"
					+ "couponId bigint not null REFERENCES Coupon(couponId)," + "PRIMARY KEY(couponId, companyId))";
			stm.executeUpdate(sql);
			System.out.println("success: " + sql);

		} catch (SQLException e) {
			throw new CreateException("create company_coupon didn't succeed");
		}

		finally {
			con.close();
			try {
				Pool.returnConnection(con);
			} catch (Exception e) {
				throw new CreateException(" didn't succeed in close connection");
			}
		}

	}

}