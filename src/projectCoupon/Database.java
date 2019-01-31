//check if this go to new michal branch
//try to work with merge
//try again to see if merge works

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

	public static void createTables(Connection con) throws SQLException {
		
		String sql;
	/*	
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
	 Statement stmt=con.createStatement();
		sql = "create table coupon ("
				+ "id bigint not null primary key generated always as identity(start with 1, increment by 1), "
				+ "title varchar(50) not null, " + "start_date varchar(50) not null, "+"end_date varchar(50 not null, "+"amount bigint not null,"
						+ "  "+"message varchar(50) not null, "+"price float not null, "+"image varchar(50) not null)";
		stmt.executeUpdate(sql);
		System.out.println("success:" + sql);
	
	
}
}

