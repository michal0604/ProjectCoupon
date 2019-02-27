package projectCoupon.Company;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import projectCoupon.ConnectionPool;
import projectCoupon.Coupons.Coupon;
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
public class CompanyDBDAO implements CompanyDAO {

	private ConnectionPool pool;

	public CompanyDBDAO() throws CouponException {
		pool = ConnectionPool.getInstance();
	}

	/**
	 * Inserts a company data set to the Database
	 * 
	 * @see projectCoupon.Company.CompanyDAO#insertCompany
	 */
	@Override
	public void insertCompany(Company Company) throws Exception {
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
			System.out.println(ex.getLocalizedMessage());
			// TODO Crate an suited Exception
			throw new Exception("Company creation failed");
		} finally {
			connection.close();
			pool.returnConnection(connection);
		}
	}

	/**
	 * remove a company data set from the Database
	 * @throws CouponException 
	 * @throws CompanyRemovalException 
	 * @throws SQLException 
	 * 
	 * @see projectCoupon.Company.CompanyDAO#removeCompany
	 */
	@Override
	public void removeCompany(Company Company) throws CouponException, CompanyRemovalException, SQLException{
		Connection connection = pool.getConnection();
		String sql = "DELETE FROM Company WHERE id=?";

		try {

			PreparedStatement pstm1 = connection.prepareStatement(sql);
			{
				connection.setAutoCommit(false);
				System.out.println(Company.getCompanyId());
				pstm1.setLong(1, Company.getCompanyId());
				pstm1.executeUpdate();
				connection.commit();
			}
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new CompanyRemovalException(Company);
			}
			throw new SQLException("failed to remove Company");
		} finally {
			connection.close();
			pool.returnConnection(connection);
		}
	}

	/**
	 * updates a company data set in the Database
	 * @throws CouponException 
	 * @throws CompanyUpdateException 
	 * @throws SQLException 
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
	 * @see projectCoupon.Company.CompanyDAO#getCompany
	 */
	@Override
	public Company getCompany(long id) throws Exception {
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
			throw new Exception("unable to get Company data");
		} finally {
			connection.close();
			pool.returnConnection(connection);
		}
		return company;
	}

	/**
	 * get all the Companies from the Database.
	 * 
	 * @see projectCoupon.Company.CompanyDAO#getAllCompanys
	 */
	@Override
	public List<Company> getAllCompanys() throws Exception {
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
			System.out.println(e);
			throw new Exception("cannot get Company data");
		} finally {
			connection.close();
			pool.returnConnection(connection);
		}
		return set;
	}

	@Override
	public boolean isCompanyNameExists(String compName) throws CouponException, SQLException {
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
			throw new CouponException("DB ERROR! Failed to checking if Company name already exists.");
		} catch (Exception e) {
			throw new CouponException("APP ERROR! Failed to checking if Company name already exists.");
		} finally {
			connection.close();
			pool.returnConnection(connection);
		}
	}

	@Override
	public Company login(String name, String password) throws CouponException, SQLException{
		Connection connection = pool.getConnection();
		Company company = new Company();
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
	}

	@Override
	public void removeCompany(long compId) throws Exception {
		removeCompany(getCompany(compId));

	}

	@Override
	public List<Coupon> getCoupons(long compId) throws Exception {
		return null;
	}
}
