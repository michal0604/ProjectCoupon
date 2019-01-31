//check if this go to new michal branch
//dsf

package projectCoupon;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

	public static String getDriverData() {
		return "org.apache.derby.jdbc.ClientDriver";
	}

	public static String getDBUrl() {
		return "jdbc:derby://localhost:3301/JBDB;create=true";
	}
/*
	public static void createTables(Connection con) throws SQLException {
		
		String sql;
		
        Statement stmt=con.createStatement();
		sql = "create table MyCompany ("
				+ "ID bigint not null primary key generated always as identity(start with 1, increment by 1), "
				+ "COMP_NAME varchar(50) not null, " + "PASSWORD varchar(50) not null, "+"EMAIL varchar(50) not null)";
		stmt.executeUpdate(sql);
		System.out.println("success:" + sql);
		
		 Statement stmt2=con.createStatement();
		sql = "create table MyCustomer ("
				+ "ID bigint not null primary key generated always as identity(start with 1, increment by 1), "
				+ "CUST_NAME varchar(50) not null, " + "PASSWORD varchar(50) not null)";
		stmt2.executeUpdate(sql);
		System.out.println("success:" + sql);
		
	}
*/
	
	
}


