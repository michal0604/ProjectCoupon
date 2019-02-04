//try to work with merge
//try again to see if merge works
package projectCoupon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

	public static String getDriverData() {
		return "org.apache.derby.jdbc.ClientDriver";
	}

	public static String getDBUrl() {
		return "jdbc:derby://localhost:3301/JBDB;create=true";
	}

	public static void dropTableifNeeded(Connection con) throws SQLException {
		String sql;
		Statement stmt;
		stmt = con.createStatement();
		try {
			sql = "DROP table Company";
			stmt.executeUpdate(sql);
			System.out.println("Droped Company Table");
		} 
		catch (SQLException e) {
			System.out.println("Company Table did not exist");
		}
		try {
			sql = "DROP table Customer";
			stmt.executeUpdate(sql);
			System.out.println("Droped Customer Table");
		} 
		catch (SQLException e) {
			System.out.println("Customer Table did not exist");
		}
		try {
			sql = "DROP table Coupon";
			stmt.executeUpdate(sql);
			System.out.println("Droped Mycoupon Table");
		} 
		catch (SQLException e) {
			System.out.println("coupon Table did not exist");
		}
	}

	public static void createTables(Connection con) throws SQLException {

		String sql;
		Statement stmt = con.createStatement();

		// create Company table
		sql = "create table Company (ID bigint not null primary key generated always as identity(start with 1, increment by 1),"
				+ "COMP_NAME varchar(50) not null," + "PASSWORD varchar(50) not null," + "EMAIL varchar(50) not null)";

		stmt.executeUpdate(sql);
		System.out.println("success:" + sql);
		Statement stmt2 = con.createStatement();
		sql = "create table Customer ("
				+ "ID bigint not null primary key generated always as identity(start with 1, increment by 1), "
				+ "CUST_NAME varchar(50) not null, " 
				+ "PASSWORD varchar(50) not null)";
		stmt2.executeUpdate(sql);
		System.out.println("success:" + sql);

		sql = "create table Coupon("
				+ "ID bigint not null primary key generated always as identity(start with 1, increment by 1),"
				+ "TITLE varchar(50) not null," 
				+ "START_DATE varchar(50) not null," 
				+ "END_DATE varchar(50) not null,"
				+ "AMOUNT int not null," 
				+ "TYPE varchar(30) not null, " 
				+ "MESSAGE varchar(50) not null,"
				+ "PRICE float not null," 
				+ "IMAGE varchar(50) not null)";

		stmt.executeUpdate(sql);
		System.out.println("success:" + sql);

	}

	public static void main(String[] args) throws SQLException {
		Connection con = DriverManager.getConnection(Database.getDBUrl());
		Database.dropTableifNeeded(con);
		Database.createTables(con);
	}
}
