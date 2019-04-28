package projectCoupon.db;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import projectCoupon.exception.CouponException;
import projectCoupon.exception.CreateException;
import projectCoupon.exception.RemoveException;
import projectCoupon.utils.ConnectionPool;




/**
 * @author Eivy & Michal
 * 
 *   Get driver & URL  and Connect to DB
 *
 */
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

	/**
	 * this method drops all tables :company,coupon,customer,company_coupon,cutomer_coupon.
	 * @throws RemoveException
	 * @throws SQLException
	 */
	public static void dropTableifNeeded() throws RemoveException, SQLException{
		String sql;
		Statement stmt = null;
		try {
			Pool=ConnectionPool.getInstance();
		} catch (Exception e2) {
			throw new RemoveException("connection failed");
		}
		Connection connection;
		try {
			connection = Pool.getConnection();
		} catch (Exception e2) {
			throw new RemoveException("connection failed");
		}
		
		try {
			stmt = connection.createStatement();
		} catch (Exception e1) {
			throw new RemoveException("createstatment is failed");
		}
		
		try {
			sql = "DROP TABLE Customer_Coupon";
			stmt.executeUpdate(sql);
			System.out.println("Droped Customer_Coupon Table");
		} catch (Exception e) {
			throw new RemoveException("Customer_Coupon Table did not drop: " + e.getMessage());
		}

		try {
			sql = "DROP TABLE Company_Coupon";
			stmt.executeUpdate(sql);
			System.out.println("Droped Company_Coupon Table");
		} catch (Exception e) {
			throw new RemoveException("Company_Coupon Table did not drop: " + e.getMessage());
		}

		try {
			sql = "DROP TABLE Company";
			stmt.executeUpdate(sql);
			System.out.println("Droped Company Table");
		} catch (Exception e) {
			throw new RemoveException("Company Table did not drop: " + e.getMessage());
		}
		try {
			sql = "DROP TABLE CUSTOMER";
			stmt.executeUpdate(sql);
			System.out.println("Droped Customer Table");
		} catch (Exception e) {
			throw new RemoveException("Customer Table did not drop: " + e.getMessage());
		}
		try {
			sql = "DROP TABLE Coupon";
			stmt.executeUpdate(sql);
			System.out.println("Droped COUPON Table");
		} catch (Exception e) {
			throw new RemoveException("COUPON Table did not exist");
		}
        finally {
			connection.close();
			try {
				Pool.returnConnection(connection);
			} catch (CouponException e) {
				throw new RemoveException("connection failed");
			}
		}


	}

	/**this method create all tables:company,coupon,customer,company_coupon,cutomer_coupon.
	 * @throws SQLException
	 * @throws CreateException
	 */
	public static void createTables() throws SQLException, CreateException {

		String sql;
		
		try {
			Pool=ConnectionPool.getInstance();
		} catch (CouponException e2) {
			throw new CreateException("connection failed");
		}
		Connection connection;
		try {
			connection = Pool.getConnection();
		} catch (Exception e2) {
			throw new CreateException("connection failed");
		}
		
		try {
			Statement stmt = connection.createStatement();

			// create Company table
			 sql = "create table Company (" + "ID bigint not null primary key, " + "COMP_NAME varchar(50) not null, "
					+ "PASSWORD varchar(50) not null, " + "EMAIL varchar(50) not null)";

			stmt.executeUpdate(sql);
			System.out.println("success:" + sql);
		} catch (SQLException e) {
			throw new CreateException("create company didn't succeed");
		}
		
		try {
			Statement stmt2 = connection.createStatement();
			 sql = "create table Customer (" + "ID bigint not null primary key, " + "CUST_NAME varchar(50) not null, "
					+ "PASSWORD varchar(50) not null)";
			stmt2.executeUpdate(sql);
			System.out.println("success:" + sql);
		} catch (SQLException e) {
			throw new CreateException("create customer didn't succeed");
		}

		try {
			java.sql.Statement stm = connection.createStatement();


			 sql = "create table Coupon (" + "ID bigint not null primary key, " + "TITLE varchar(50) not null, "
					+ "START_DATE date not null, " + "END_DATE date not null, " + "AMOUNT integer not null, "
					+ "TYPE varchar(50) not null, " + "MESSAGE varchar(50) not null, " + " PRICE float not null, "
					+ "IMAGE varchar(200) not null)";

			stm.executeUpdate(sql);
			System.out.println("success:" + sql);
		} catch (SQLException e) {
			throw new CreateException("create coupon didn't succeed");
		}

		// create join table Customer_Coupon
		try {
			java.sql.Statement stm = connection.createStatement();
			 sql = "create table Customer_Coupon (" + "Customer_ID bigint, " + "Coupon_ID bigint, "
					+ "primary key (Customer_ID, Coupon_ID))";

			stm.executeUpdate(sql);
			System.out.println("success:" + sql);

		} catch (SQLException e) {
			throw new CreateException("create customer_coupon didn't succeed");

		}

		// create join table Company_Coupon
		try {
			java.sql.Statement stm = connection.createStatement();
			 sql = "create table Company_Coupon (" + "Company_ID bigint, " + "Coupon_ID bigint, "
					+ "primary key (Company_ID, Coupon_ID))";
			stm.executeUpdate(sql);
			System.out.println("success: " + sql);

		} catch (SQLException e) {
			throw new CreateException("create company_coupon didn't succeed");
		}

		  finally {
				connection.close();
				try {
					Pool.returnConnection(connection);
				} catch (CouponException e) {
					try {
						throw new CreateException("connection failed");
					} catch (CreateException e1) {
						
					}
				}
			}


	}
}

