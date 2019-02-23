//try to work with merge
//try again to see if merge works
package projectCoupon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

	private static final String Company = null;
	public static String getDriverData() {
		return "org.apache.derby.jdbc.ClientDriver";
	}

	public static String getDBUrl() {
		return "jdbc:derby://localhost:3301/JBDB;create=true";
	}

	public static void dropTableifNeeded(Connection con) throws SQLException {
		String sql;
		Statement stmt;
	//	con = DriverManager.getConnection(Database.getDBUrl());
		stmt = con.createStatement();
		try {
			sql = "DROP TABLE Customer_Coupon";
			stmt.executeUpdate(sql);
			System.out.println("Droped Customer_Coupon Table");
		} 
		catch (SQLException e) {
			System.out.println("Customer_Coupon Table did not drop: "+e.getMessage());
		}
		
		try {
			sql = "DROP TABLE Company_Coupon";
			stmt.executeUpdate(sql);
			System.out.println("Droped Company_Coupon Table");
		} 
		catch (SQLException e) {
			System.out.println("Company_Coupon Table did not drop: "+e.getMessage());
		}
		try {
			sql = "DROP TABLE Company";
			stmt.executeUpdate(sql);
			System.out.println("Droped Company Table");
		} 
		catch (SQLException e) {
			System.out.println("Company Table did not drop: "+e.getMessage());
		}
		try {
			sql = "DROP TABLE CUSTOMER";
			stmt.executeUpdate(sql);
			System.out.println("Droped Customer Table");
		} 
		catch (SQLException e) {
			System.out.println("Customer Table did not drop: "+e.getMessage());
		}
		try {
			sql = "DROP TABLE Coupon";
			stmt.executeUpdate(sql);
			System.out.println("Droped COUPON Table");
		} 
		catch (SQLException e) {
			System.out.println("COUPON Table did not exist");
		}
		
	
	}

	public static void createTables(Connection con) throws SQLException {

		String sql;
		
		try {
		Statement stmt = con.createStatement();

		// create Company table
		sql = "CREATE TABLE Company(ID bigint not null primary key generated always as identity(start with 1, increment by 1),"
				+ "COMP_NAME varchar(50) not null," + "PASSWORD varchar(50) not null," + "EMAIL varchar(50) not null)";

		stmt.executeUpdate(sql);
		System.out.println("success:" + sql);
		}catch (SQLException e) {
			System.out.println(e.getMessage());
			//throw new CompanyCreationException(Company);
		}
		try {
		Statement stmt2 = con.createStatement();
		sql = "CREATE TABLE CUSTOMER("
				+ "ID bigint not null primary key generated always as identity(start with 1, increment by 1), "
				+ "CUST_NAME varchar(50) not null, " 
				+ "PASSWORD varchar(50) not null)";
		stmt2.executeUpdate(sql);
		System.out.println("success:" + sql);
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		try {
			java.sql.Statement stm = con.createStatement();
		
		sql = "CREATE TABLE Coupon("
				+ "ID bigint not null primary key generated always as identity(start with 1, increment by 1),"
				+ "TITLE varchar(50) not null," 
				+ "START_DATE DATE not null," 
				+ "END_DATE DATE not null,"
				+ "AMOUNT int not null," 
				+ "TYPE varchar(30) not null, " 
				+ "MESSAGE varchar(50) not null,"
				+ "PRICE float not null," 
				+ "IMAGE varchar(50) not null)";

		stm.executeUpdate(sql);
		System.out.println("success:" + sql);
		}catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	
		
		// create join table Customer_Coupon
				try {
				java.sql.Statement stm = con.createStatement();
				sql = "CREATE TABLE Customer_Coupon("
						+ "CUST_ID bigint not null REFERENCES CUSTOMER(ID),"
						+ "COUPON_ID bigint not null REFERENCES COUPON(ID),"
						+ "PRIMARY KEY(COUPON_ID, CUST_ID))";
						
						
				stm.executeUpdate(sql);
				System.out.println("create customer_coupon success");
				
				} catch (SQLException e) {
					System.out.println("create failed");
					
				}


			//	  create join table Company_Coupon
				try {
				java.sql.Statement stm = con.createStatement();
				sql = "CREATE TABLE Company_Coupon("
						+ "COMP_ID bigint not null REFERENCES Company(ID),"
						+ "COUPON_ID bigint not null REFERENCES Coupon(ID),"
						+ "PRIMARY KEY(COUPON_ID, COMP_ID))";
				stm.executeUpdate(sql);
				System.out.println("create company coupon success");
				
				}catch (SQLException e) {
					e.printStackTrace();
				}
				
				finally {
					con.close();
				}
		
		
	}
	

	public static void main(String[] args) throws SQLException {
		Connection con = DriverManager.getConnection(Database.getDBUrl());
		Database.dropTableifNeeded(con);
		Database.createTables(con);
	}

	

	
	
}
