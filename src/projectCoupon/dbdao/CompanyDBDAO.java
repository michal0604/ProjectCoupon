package projectCoupon.dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import projectCoupon.beans.Company;
import projectCoupon.dao.CompanyDAO;
import projectCoupon.exception.CompanyException;
import projectCoupon.exception.CouponException;
import projectCoupon.utils.ConnectionPool;

/**
 * this class implement the DB operations associated with the Company's data
 * access requirements.
 * 
 * @author Eivy & Michal
 *
 */
public class CompanyDBDAO implements CompanyDAO {

	private ConnectionPool pool;

	/**
	 * cTor for the DBDAO that initiate the resource required for the class
	 * 
	 * @throws CouponException
	 *             for problems from creation.
	 */
	public CompanyDBDAO() throws CouponException {
		try {
			pool = ConnectionPool.getInstance();
		} catch (SQLException e) {
			throw new CouponException("connection failed");
		}
	}

	/**
	 * Inserts a company data set to the Database
	 * 
	 * @throws CouponException
	 *             for problems in inserting the company to the DB
	 * @throws SQLException
	 *             for DB related failures
	 * @throws ConnectionException
	 *             for connection problems
	 * 
	 * @see projectCoupon.dao.CompanyDAO#insertCompany
	 */
	@Override
	public void insertCompany(Company Company) throws CouponException, SQLException {
		pool=ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		String sql  = "insert into Company(ID, COMP_NAME, PASSWORD, EMAIL) values (?,?,?,?)";

		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, Company.getCompanyId());
			pstmt.setString(2, Company.getCompName());
			pstmt.setString(3, Company.getPassword());
			pstmt.setString(4, Company.getEmail());
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			throw new CouponException("Company creation failed " + ex.getMessage());
		} finally {
			connection.close();
			pool.returnConnection(connection);
		}
	}

	/**
	 * remove a company from the Database
	 * 
	 * @param company
	 *            company to be remove
	 * @throws CouponException
	 * @throws CompanyRemovalException
	 *             for problems regarding the removal of company from DB
	 * @throws SQLException
	 *             SQLException for DB related failures
	 * 
	 * @see projectCoupon.dao.CompanyDAO#removeCompany
	 */
	@Override
	public void removeCompany(Company Company) throws CouponException, SQLException {
		Connection connection = pool.getConnection();
		String sql = "delete from Company where ID = ?";
		try {
			PreparedStatement pstm1 = connection.prepareStatement(sql);
			connection.setAutoCommit(false);
			pstm1.setLong(1, Company.getCompanyId());
			pstm1.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			connection.rollback();
			throw new SQLException("failed to remove Company " + e.getMessage());
		} finally {
			connection.close();
			pool.returnConnection(connection);
		}
	}

	/**
	 * updates a company into the Database
	 * 
	 * @param company
	 *            company to update
	 * @throws CompanyException 
	 * @throws CouponException
	 *             regarding the connection problem
	 * @throws CompanyUpdateException
	 *             or problems in updating the company to the DB
	 * @throws SQLException
	 *             for DB related failures
	 * 
	 * @see projectCoupon.dao.CompanyDAO#updateCompany
	 */
	@Override
	public void updateCompany(Company Company) throws CompanyException {
		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (CouponException e1) {
			throw new CompanyException("connection failed");
		}
		try {

			Statement stm = connection.createStatement();
			String sql = String.format("update Company set COMP_NAME= '%s',PASSWORD = '%s', EMAIL= '%s' where ID = %d",
					Company.getCompName(), Company.getPassword(), Company.getEmail(),
					Company.getCompanyId());
			stm.executeUpdate(sql);
		} catch (Exception e) {
			throw new CompanyException("update failed");

		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new  CompanyException("connection failed");
			}
			try {
				pool.returnConnection(connection);
			} catch (CouponException e) {
			throw new CompanyException("return connection failed");
			
			}
		}
	}

	/**
	 * get a company data set by the company's id.
	 * 
	 * @param id
	 *            representing the id of the required company
	 * @throws CouponException
	 *             for errors happing due to trying to get a company from DB
	 * @throws SQLException
	 *             for DB related failures
	 * 
	 * @see projectCoupon.dao.CompanyDAO#getCompany
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see projectCoupon.Company.CompanyDAO#getCompany(long)
	 */
	@Override
	public Company getCompany(long companyId) throws CouponException, SQLException {
		pool=ConnectionPool.getInstance();
		Connection connection = pool.getConnection();
		Company company = new Company();
		try {
			Statement stm = connection.createStatement();

			String sql = "SELECT * FROM Company WHERE ID=" + companyId;
			ResultSet rs = stm.executeQuery(sql);
			rs.next();
			company.setCompanyId(rs.getLong(1));
			company.setCompName(rs.getString(2));
			company.setPassword(rs.getString(3));
			company.setEmail(rs.getString(4));

		} catch (SQLException e) {
			throw new CouponException("unable to get Company data " + e.getMessage());
		} finally {
			connection.close();
			pool.returnConnection(connection);
		}
		return company;
	}

	/**
	 * get all the Companies from the Database.
	 * 
	 * @throws CouponException
	 *             for errors occurring due to trying to get all companies from DB
	 * @throws SQLException
	 *             for DB related failures
	 * @throws ConnectionException
	 *             error occurring due to connection problems
	 * 
	 * @see projectCoupon.dao.CompanyDAO#getAllCompanys
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see projectCoupon.Company.CompanyDAO#getAllCompanys()
	 */
	@Override
	public List<Company> getAllCompanys() throws CouponException, SQLException {
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
			throw new CouponException("cannot get Company data " + e.getMessage());
		} finally {
			connection.close();
			pool.returnConnection(connection);
		}
		return set;
	}

	
	

	/**
	 * this method returns a company iff the user password is correct.
	 * 
	 * @param name
	 *            company's name of the logged in company
	 * @param password
	 *            company's password of the logged in company
	 * @throws CouponException
	 *             for problem retrieving the company data.
	 * @throws SQLException
	 *             for DB related failures
	 * @throws CompanyException
	 * @throws ConnectionException
	 *             error occurring due to connection problems
	 * 
	 * @see projectCoupon.dao.CompanyDAO#login(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public Company login(String compName, String password) throws CouponException, SQLException, CompanyException {
		Connection connection = pool.getConnection();
		Company company = new Company();
		try {
			String sql = "SELECT ID FROM Company WHERE COMP_NAME = ? ";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, compName);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			company.setCompanyId(rs.getLong(1));
			company.setCompName(rs.getString(2));
			company.setPassword(rs.getString(3));
			company.setEmail(rs.getString(4));
			if (company.getPassword().equals(password)) {
				return company;
			}
			return null;
		} catch (SQLException e) {
			throw new CouponException("DB ERROR! Failed to get the company data. " + e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new CompanyException("connection failed");
			}
			try {
				pool.returnConnection(connection);
			} catch (CouponException e) {
				throw new CompanyException("connection failed");
			}
		}
	}
	/**
	 * returns if a company identified by the name exist in the DB records.
	 * 
	 * @param compName
	 *            name that should be checked for existing
	 * @throws CouponException
	 *             for error related to the retrieval of the company
	 * @throws SQLException
	 *             for DB related failures
	 * @throws ConnectionException
	 *             error occurring due to connection problems
	 * 
	 * @see projectCoupon.dao.CompanyDAO#getAllCompanys
	 */

	@Override
	public boolean isCompanyNameExists(String compName) throws CouponException {
		Connection connection = pool.getConnection();
		try {
			String sql = "SELECT ID FROM Company WHERE COMP_NAME = ? ";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, compName);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			}
			return false;

		} catch (SQLException e) {
			throw new CouponException("Failed to checking if Company name already exists.");
		} catch (Exception e) {
			throw new CouponException("Failed to checking if Company name already exists.");
		} finally {
			pool.returnConnection(connection);
		}

	}

	/**
	 * remove a company identified by its id from the Database
	 * 
	 * @param compId
	 *            the id of the company that should be deleted
	 * @throws CouponException
	 * @throws CompanyRemovalException
	 *             for problems regarding the removal of company from DB
	 * @throws SQLException
	 *             SQLException for DB related failures
	 * 
	 * @see projectCoupon.DAO.CompanyDAO#removeCompany(long)
	 */
	

}