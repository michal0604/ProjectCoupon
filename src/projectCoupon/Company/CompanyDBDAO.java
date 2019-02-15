package projectCoupon.Company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import javax.swing.plaf.TableUI;
import javax.swing.table.TableColumn;

import projectCoupon.Database;
import projectCoupon.Customer.Customer;

/**
 * this class implement the DB operations associated with the Company's data
 * access requirements.
 * 
 * @author Eivy & Michal
 *
 */
public class CompanyDBDAO implements CompanyDAO {

	Connection con;
	private Connection connection;
	

	/**
	 * Inserts a company data set to the Database
	 * 
	 * @see projectCoupon.Company.CompanyDAO#insertCompany
	 */
	@Override
	public void insertCompany(Company Company) throws Exception {
		con = DriverManager.getConnection(Database.getDBUrl());
		//.createTables(con);
		String sql = "INSERT INTO Company (COMP_NAME,PASSWORD,EMAIL) VALUES(?,?,?)";

		try (PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setString(1, Company.getCompName());
			pstmt.setString(2, Company.getPassword());
			pstmt.setString(3, Company.getEmail());
			pstmt.executeUpdate();

			// why 1 2 3

		} 
		catch (SQLException ex) {
			System.out.println(ex.getLocalizedMessage());
			//TODO Crate an suited Exception
			throw new Exception("Company creation failed");
		} 
		finally {
			con.close();
		}
	}

	/**
	 * remove a company data set from the Database
	 * 
	 * @see projectCoupon.Company.CompanyDAO#removeCompany
	 */
	@Override
	public void removeCompany(Company Company) throws Exception {
		con = DriverManager.getConnection(Database.getDBUrl());
		String sql = "DELETE FROM Company WHERE id=?";

		//TODO what is the different statement and preparedStatement
		try (PreparedStatement pstm1 = con.prepareStatement(sql);) {
			con.setAutoCommit(false);
			//TODO  autocommit??????
			System.out.println(Company.getId());
			pstm1.setLong(1, Company.getId());
			pstm1.executeUpdate();
			con.commit();
			// TODO con.commit????
		} 
		catch (SQLException e) {
			try {
				con.rollback();
				//TODO what is rollback??????
			} 
			catch (SQLException e1) {
				throw new Exception("Database error");
			}
			throw new Exception("failed to remove Company");
		} 
		finally {
			con.close();
		}
	}

	/**
	 * updates a company data set in the Database
	 * 
	 * @see projectCoupon.Company.CompanyDAO#updateCompany
	 */
	@Override
	public void updateCompany(Company Company) throws Exception {
		con = DriverManager.getConnection(Database.getDBUrl());
		try (Statement stm = con.createStatement()) {
			String sql = "UPDATE Company " + " SET COMP_NAME='" + Company.getCompName() + "', PASSWORD='"
					+ Company.getPassword() + "',EMAIL='" + Company.getEmail() + "' WHERE ID=" + Company.getId();
			stm.executeUpdate(sql);
		} 
		catch (SQLException e) {
			throw new Exception("update Company failed");
		}
		con.close();
	}

	/**
	 * get a company data set by the company's id.
	 * 
	 * @see projectCoupon.Company.CompanyDAO#getCompany
	 */
	@Override
	public Company getCompany(long id) throws Exception {
		con = DriverManager.getConnection(Database.getDBUrl());
		Company company = new Company();
		try {
			Statement stm = con.createStatement();
		
			String sql = "SELECT * FROM Company WHERE ID=" + id;
			ResultSet rs = stm.executeQuery(sql);
			rs.next();
			company.setId(rs.getLong(1));
			company.setCompName(rs.getString(2));
			company.setPassword(rs.getString(3));
			company.setEmail(rs.getString(4));
			

		} catch (SQLException e) {
			throw new Exception("unable to get Company data");
		} finally {
			con.close();
		}
		return company;
	}

	/**
	 * get all the Companies from the Database.
	 * 
	 * @see projectCoupon.Company.CompanyDAO#getAllCompanys
	 */
	@Override
	public Set<Company> getAllCompanys() throws Exception {
		con = DriverManager.getConnection(Database.getDBUrl());
		Set<Company> set = new HashSet<>();
		String sql = "SELECT * FROM Company";
		try {
			Statement stm = con.createStatement(); 
			ResultSet rs = stm.executeQuery(sql); 
			while (rs.next()) {
				long id = rs.getLong(1);
				String compName = rs.getString(2);
				String password = rs.getString(3);
				String email = rs.getString(4);
				set.add(new Company(id, compName, password, email));
			}
		} 
		catch (SQLException e) {
			System.out.println(e);
			throw new Exception("cannot get Company data");
		} 
		finally {
			con.close();
		}
		return set;
	}

	/**
	 * dropes the company table from Database
	 * 
	 * @see projectCoupon.Company.CompanyDAO#dropTable()
	 */
	//TODO i don't know if it is a good design to allow this method from hear.
	public Company dropTable() throws Exception {
		Connection connection = null;
		try {
			// Create a connection:
			con = DriverManager.getConnection(Database.getDBUrl());

			// Create sql command for delete one record:
			String sql = "drop table ", Company;

			// Create an object for executing the above command:
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			// Execute:
			preparedStatement.executeUpdate();

			System.out.println("drop succeeded.");
		} 
		catch (Exception e) {
			System.out.println("error");
		} 
		finally {
			try {
				connection.close();
			} 
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;

	}


	
		
	}


