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
import projectCoupon.Coupons.CouponDBDAO;
import projectCoupon.Exception.CompanyException;
import projectCoupon.Exception.CouponException;
import projectCoupon.Exception.CreateException;
import projectCoupon.Exception.RemoveException;
import projectCoupon.Exception.UpdateException;

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
	public void insertCompany(Company Company) throws CreateException{
		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (CouponException e) {
			throw new CreateException("connection failed");
		}
		String sql = "INSERT INTO Company (companyId,compName,PASSWORD,EMAIL) VALUES(?,?,?)";

		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, Company.getCompanyId());
			pstmt.setString(2, Company.getCompName());
			pstmt.setString(3, Company.getPassword());
			pstmt.setString(4, Company.getEmail());
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			throw new CreateException("Company creation failed "+ ex.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new CreateException("connection failed");
			}
			try {
				pool.returnConnection(connection);
			} catch (CouponException e) {
				throw new CreateException("connection failed");
			}
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
	public void removeCompany(Company Company) throws RemoveException{
		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (CouponException e2) {
			throw new RemoveException("connection failed");
		}
		String sql = "DELETE FROM Company WHERE companyId=?";
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
				throw new RemoveException("connection failed");
			}
			throw new RemoveException("failed to remove Company "+e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new RemoveException("connection failed");
			}
			try {
				pool.returnConnection(connection);
			} catch (CouponException e) {
				throw new RemoveException("connection failed");
			}
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
	public void updateCompany(Company Company) throws UpdateException{
		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (CouponException e1) {
			throw new UpdateException("connection failed");
		}
		try {

			Statement stm = connection.createStatement();
			String sql = "UPDATE Company " + " SET compName='" + Company.getCompName() + "', PASSWORD='"
					+ Company.getPassword() + "',EMAIL='" + Company.getEmail() + "' WHERE ID=" + Company.getCompanyId();
			stm.executeUpdate(sql);
		} catch (SQLException e) {
			throw new UpdateException("update failed");
		}
		finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new UpdateException("connection failed");
			}
			try {
				pool.returnConnection(connection);
			} catch (CouponException e) {
				throw new UpdateException("connection failed");
			}
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
	public Company getCompany(long id) throws CompanyException{
		Connection connection = null;
		try {
			connection = pool.getConnection();
		} catch (CouponException e1) {
			throw new CompanyException("connection failed");
		}
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
			throw new CompanyException("unable to get Company data "+e.getMessage());
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
	public List<Company> getAllCompanys() throws CompanyException{
		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (CouponException e1) {
			throw new CompanyException("connection failed");
		}
		List<Company> list = new ArrayList<Company>();
		String sql = "SELECT * FROM Company";
		try {
			Statement stm = connection.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				long id = rs.getLong(1);
				String compName = rs.getString(2);
				String password = rs.getString(3);
				String email = rs.getString(4);
				list.add(new Company(id, compName, password, email));
			}
		} catch (SQLException e) {
			throw new CompanyException("cannot get Company data "+e.getMessage());
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
		return list;
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
	public Company login(String name, String password) throws CompanyException{
		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (CouponException e1) {
			throw new CompanyException("connection failed");
		}
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
				throw new CompanyException("DB ERROR! Failed to get the company data. "+e.getMessage());
		}
		finally {
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

	@Override
	public List<Coupon> getCoupons(long companyId) throws CouponException {
		
		try {
			pool = ConnectionPool.getInstance();
		} catch (CouponException e1) {
			throw new CouponException("connection failed");
		}
		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (Exception e1) {
			throw new CouponException("connection failed");
		}
	
		List<Coupon> coupons = new ArrayList<Coupon>();
		CouponDBDAO couponDBDAO=new CouponDBDAO();
	
			String sql = "SELECT couponId FROM Company WHERE companyId=?";
			PreparedStatement stmt;
			try {
				stmt = connection.prepareStatement(sql);
			} catch (SQLException e1) {
				throw new CouponException("connection failed");
			}
			try {
				stmt.setLong(1, companyId);
			} catch (SQLException e1) {
				throw new CouponException("problems with set");
			}
			ResultSet rs;
			try {
				rs = stmt.executeQuery();
			} catch (SQLException e1) {
				throw new CouponException("problems with set");
			}
        try {
			while (rs.next()) {
				coupons.add(couponDBDAO.getCoupon(rs.getLong("couponId")));
			}
		} catch (Exception e) {
			throw new CouponException("unable to get coupon");
		}
		return coupons;
	}
	}

	
	
	

