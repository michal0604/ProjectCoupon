package projectCoupon.Company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import projectCoupon.ConnectionPool;
import projectCoupon.Exception.CompanyRemovalException;
import projectCoupon.Exception.CompanyUpdateException;
import projectCoupon.Exception.CouponException;

/**
 * this class implement the DB operations associated with the Company's data
 * access requirements.
 * 
 * @author Eivy & Michal
 *
 */
/**
 * @author eivy
 *
 */
public class CompanyDBDAO implements CompanyDAO {

	private ConnectionPool pool;

	/**
	 * cTor for the DBDAO that initiate the resource required for the class
	 * 
	 * @throws CouponException for problems from creation.
	 */
	public CompanyDBDAO() throws CouponException {
		pool = ConnectionPool.getInstance();
	}

	/**
	 * Inserts a company data set to the Database
	 * 
	 * @throws CouponException for problems in inserting the company to the DB 
	 * @throws SQLException for DB related failures 
	 * 
	 * @see projectCoupon.Company.CompanyDAO#insertCompany
	 */
	@Override
	public void insertCompany(Company Company) throws CouponException, SQLException{
		Connection connection = pool.getConnection();
		String sql = "INSERT INTO Company (ID,COMP_NAME,PASSWORD,EMAIL) VALUES(?,?,?)";

		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, Company.getCompanyId());
			pstmt.setString(2, Company.getCompName());
			pstmt.setString(3, Company.getPassword());
			pstmt.setString(4, Company.getEmail());
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			throw new CouponException("Company creation failed "+ ex.getMessage());
		} finally {
			connection.close();
			pool.returnConnection(connection);
		}
	}

	/**
	 * remove a company  from the Database
	 * 
	 * @throws CouponException  
	 * @throws CompanyRemovalException for problems regarding the removal of company from DB
	 * @throws SQLException SQLException for DB related failures
	 * 
	 * @see projectCoupon.Company.CompanyDAO#removeCompany
	 */
	@Override
	public void removeCompany(Company Company) throws CouponException, CompanyRemovalException, SQLException{
		Connection connection = pool.getConnection();
		String sql = "DELETE FROM Company WHERE id=?";
		try {
			PreparedStatement pstm1 = connection.prepareStatement(sql);
			connection.setAutoCommit(false);
			pstm1.setLong(1, Company.getCompanyId());
			pstm1.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new CompanyRemovalException(Company);
			}
			throw new SQLException("failed to remove Company "+e.getMessage());
		} finally {
			connection.close();
			pool.returnConnection(connection);
		}
	}

	/**
	 * updates a company into the Database
	 * 
	 * @throws CouponException regarding the connection problem
	 * @throws CompanyUpdateException or problems in updating the company to the DB 
	 * @throws SQLException for DB related failures 
	 * 
	 * @see projectCoupon.Company.CompanyDAO#updateCompany
	 */
	@Override
	public void updateCompany(Company Company) throws CouponException, CompanyUpdateException, SQLException{
		Connection connection = pool.getConnection();
		try {

			Statement stm = connection.createStatement();
			String sql = "UPDATE Company " + " SET COMP_NAME='" + Company.getCompName() + "', PASSWORD='"
					+ Company.getPassword() + "',EMAIL='" + Company.getEmail() + "' WHERE ID=" + Company.getCompanyId();
			stm.executeUpdate(sql);
		} catch (SQLException e) {
			throw new CompanyUpdateException(Company);
		}
		finally {
			connection.close();
			pool.returnConnection(connection);
		}
	}

	/**
	 * get a company data set by the company's id.
	 * 
	 * @throws CouponException  for errors happing due to trying to get a company from DB
	 * @throws SQLException for DB related failures
	 * 
	 * @see projectCoupon.Company.CompanyDAO#getCompany
	 */
	@Override
	public Company getCompany(long id) throws CouponException, SQLException{
		Connection connection = pool.getConnection();
		Company company = new Company();
		try {
			Statement stm = connection.createStatement();

			String sql = "SELECT * FROM Company WHERE ID=" + id;
			ResultSet rs = stm.executeQuery(sql);
			rs.next();
			company.setCompanyId(rs.getLong(1));
			company.setCompName(rs.getString(2));
			company.setPassword(rs.getString(3));
			company.setEmail(rs.getString(4));

		} catch (SQLException e) {
			throw new CouponException("unable to get Company data "+e.getMessage());
		} finally {
			connection.close();
			pool.returnConnection(connection);
		}
		return company;
	}

	/**
	 * get all the Companies from the Database.
	 * 
	 * @throws CouponException  for errors happing due to trying to get all companies from DB
	 * @throws SQLException for DB related failures
	 * 
	 * @see projectCoupon.Company.CompanyDAO#getAllCompanys
	 */
	@Override
	public List<Company> getAllCompanys() throws CouponException, SQLException{
		Connection connection = pool.getConnection();
		List<Company> set = new ArrayList<Company>();
		String sql = "SELECT * FROM Company";
		try {
			Statement stm = connection.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				long id = rs.getLong(1);
				String compName = rs.getString(2);
				String password = rs.getString(3);
				String email = rs.getString(4);
				set.add(new Company(id, compName, password, email));
			}
		} catch (SQLException e) {
			throw new CouponException("cannot get Company data "+e.getMessage());
		} finally {
			connection.close();
			pool.returnConnection(connection);
		}
		return set;
	}

	/**
	 * returns if a company identified by the name exist in the DB records.
	 * 
	 * @throws CouponException for error related to the retrieval of the company 
	 * @throws SQLException for DB related failures
	 * 
	 * @see projectCoupon.Company.CompanyDAO#getAllCompanys
	 */
	public boolean isCompanyNameExists(String compName) throws CouponException, SQLException{
		Connection connection = pool.getConnection();
		try {
			String sql = "SELECT id FROM Company WHERE company_name = ? ";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, compName);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			}
			return false;

		} catch (SQLException e) {
			throw new CouponException("DB ERROR! Failed to checking if Company name already exists. "+e.getMessage());
		}
		finally {
			connection.close();
			pool.returnConnection(connection);
		}
	}

	/**
	 * this method returns a company iff the user password is correct.
	 * 
	 * @throws CouponException for problem retrieving the company data.
	 * @throws SQLException for DB related failures
	 * 
	 * @see projectCoupon.Company.CompanyDAO#login(java.lang.String, java.lang.String)
	 */
	@Override
	public Company login(String name, String password) throws CouponException, SQLException{
		Connection connection = pool.getConnection();
		Company company = new Company();
		try {
			String sql = "SELECT id FROM Company WHERE company_name = ? ";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			company.setCompanyId(rs.getLong(1));
			company.setCompName(rs.getString(2));
			company.setPassword(rs.getString(3));
			company.setEmail(rs.getString(4));
			if(company.getPassword().equals(password)) {
				return company;
			}
			return null;
		} catch (SQLException e) {
				throw new CouponException("DB ERROR! Failed to get the company data. "+e.getMessage());
		}
		finally {
			connection.close();
			pool.returnConnection(connection);
		}

	}

	
	/**
	 * remove a company identified by its id from the Database
	 * 
	 * @throws CouponException  
	 * @throws CompanyRemovalException for problems regarding the removal of company from DB
	 * @throws SQLException SQLException for DB related failures
	 * 
	 * @see projectCoupon.Company.CompanyDAO#removeCompany(long)
	 */
	@Override
	public void removeCompany(long compId) throws CouponException, CompanyRemovalException, SQLException{
		removeCompany(getCompany(compId));

	}

}