package projectCoupon;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import projectCoupon.Exception.CouponException;

public class Database {

	public static String getDriverData() {
		return "org.apache.derby.jdbc.ClientDriver";
	}

	public static String getDBUrl() {
		return "jdbc:derby://localhost:3301/JBDB;create=true";
	}

	private static ConnectionPool pool;

	public Database() throws CouponException {
		pool = ConnectionPool.getInstance();
	}

	public static void dropTableifNeeded(Connection connection) throws SQLException, CouponException {
		String sql;
		Statement stmt;
		connection = pool.getConnection();
		stmt = connection.createStatement();
		try {
			sql = "DROP TABLE Customer_Coupon";
			stmt.executeUpdate(sql);
			System.out.println("Droped Customer_Coupon Table");
		} catch (SQLException e) {
			System.out.println("Customer_Coupon Table did not drop: " + e.getMessage());
		}

		try {
			sql = "DROP TABLE Company_Coupon";
			stmt.executeUpdate(sql);
			System.out.println("Droped Company_Coupon Table");
		} catch (SQLException e) {
			System.out.println("Company_Coupon Table did not drop: " + e.getMessage());
		}
		try {
			sql = "DROP TABLE Company";
			stmt.executeUpdate(sql);
			System.out.println("Droped Company Table");
		} catch (SQLException e) {
			System.out.println("Company Table did not drop: " + e.getMessage());
		}
		try {
			sql = "DROP TABLE CUSTOMER";
			stmt.executeUpdate(sql);
			System.out.println("Droped Customer Table");
		} catch (SQLException e) {
			System.out.println("Customer Table did not drop: " + e.getMessage());
		}
		try {
			sql = "DROP TABLE Coupon";
			stmt.executeUpdate(sql);
			System.out.println("Droped COUPON Table");
		} catch (SQLException e) {
			System.out.println("COUPON Table did not exist");
		}
		connection.close();
		pool.returnConnection(connection);

	}

	public static void createTables(Connection connection) throws SQLException, Exception {

		String sql;
		connection = pool.getConnection();
		try {
			Statement stmt = connection.createStatement();

			// create Company table
			sql = "CREATE TABLE Company(ID bigint not null primary key generated always as identity(start with 1, increment by 1),"
					+ "COMP_NAME varchar(50) not null," + "PASSWORD varchar(50) not null,"
					+ "EMAIL varchar(50) not null)";

			stmt.executeUpdate(sql);
			System.out.println("success:" + sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			// throw new CompanyCreationException(Company);
		}
		try {
			Statement stmt2 = connection.createStatement();
			sql = "CREATE TABLE CUSTOMER("
					+ "ID bigint not null primary key generated always as identity(start with 1, increment by 1), "
					+ "CUST_NAME varchar(50) not null, " + "PASSWORD varchar(50) not null)";
			stmt2.executeUpdate(sql);
			System.out.println("success:" + sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		try {
			java.sql.Statement stm = connection.createStatement();

			sql = "CREATE TABLE Coupon("
					+ "ID bigint not null primary key generated always as identity(start with 1, increment by 1),"
					+ "TITLE varchar(50) not null," + "START_DATE DATE not null," + "END_DATE DATE not null,"
					+ "AMOUNT int not null," + "TYPE varchar(30) not null, " + "MESSAGE varchar(50) not null,"
					+ "PRICE float not null," + "IMAGE varchar(50) not null)";

			stm.executeUpdate(sql);
			System.out.println("success:" + sql);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		// create join table Customer_Coupon
		try {
			java.sql.Statement stm = connection.createStatement();
			sql = "CREATE TABLE Customer_Coupon(" + "CUST_ID bigint not null REFERENCES CUSTOMER(ID),"
					+ "COUPON_ID bigint not null REFERENCES COUPON(ID)," + "PRIMARY KEY(COUPON_ID, CUST_ID))";

			stm.executeUpdate(sql);
			System.out.println("success:" + sql);

		} catch (SQLException e) {
			System.out.println("create failed");

		}

		// create join table Company_Coupon
		try {
			java.sql.Statement stm = connection.createStatement();
			sql = "CREATE TABLE Company_Coupon(" + "COMP_ID bigint not null REFERENCES Company(ID),"
					+ "COUPON_ID bigint not null REFERENCES Coupon(ID)," + "PRIMARY KEY(COUPON_ID, COMP_ID))";
			stm.executeUpdate(sql);
			System.out.println("success: " + sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		finally {
			connection.close();
			pool.returnConnection(connection);
		}

	}

}