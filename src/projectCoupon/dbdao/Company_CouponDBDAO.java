package projectCoupon.dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import projectCoupon.beans.Company;
import projectCoupon.beans.Company_Coupon;
import projectCoupon.beans.Coupon;
import projectCoupon.dao.Company_CouponDAO;
import projectCoupon.exception.CouponException;
import projectCoupon.exception.CreateException;
import projectCoupon.exception.RemoveException;
import projectCoupon.exception.UpdateException;
import projectCoupon.utils.ConnectionPool;

public class Company_CouponDBDAO implements Company_CouponDAO {
	private static final String AND = null;
	private ConnectionPool pool;

	public Company_CouponDBDAO() throws CouponException {
		try {
			pool = ConnectionPool.getInstance();
		} catch (SQLException e) {
			throw new CouponException("connection failed");
		}
	}

	@Override
	public void insertCompany_Coupon(long companyId, long couponId) throws CreateException {
		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (CouponException e) {
			throw new CreateException("connection failed");
		}

		String sql = "insert into Company_Coupon (Company_ID, Coupon_ID) values (?,?)";

		try {

			PreparedStatement pstmt = connection.prepareStatement(sql);
			{

				pstmt.setLong(1, companyId);
				pstmt.setLong(2, couponId);

				pstmt.executeUpdate();
				System.out.println("Company_Coupon added: companyId: " + companyId + " couponId: " + couponId);
			}
		} catch (SQLException e) {
			throw new CreateException("Company_Coupon creation failed " + e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new CreateException("connection failed " + e.getMessage());
			}

			try {
				pool.returnConnection(connection);
			} catch (CouponException e) {
				throw new CreateException("connection failed " + e.getMessage());
			}
		}

	}

	@Override
	public void removeCompany_Coupon(long companyId, long couponId) throws RemoveException {
		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (CouponException e2) {
			throw new RemoveException("connection failed");
		}
		String sql = "DELETE FROM COMPANY_COUPON  WHERE company_ID=? AND Coupon_ID=?";

		try {
			PreparedStatement stm = connection.prepareStatement(sql);
			{
			}
			connection.setAutoCommit(false);
			stm.setLong(1, companyId);
			stm.setLong(2, couponId);
			stm.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new RemoveException("Database error " + e1.getMessage());
			}
			throw new RemoveException("failed to remove company_Coupon " + e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new RemoveException("connection failed " + e.getMessage());
			}
			try {
				pool.returnConnection(connection);
			} catch (CouponException e) {
				throw new RemoveException("connection failed " + e.getMessage());
			}
		}
	}

	@Override
	public void removeCompany_CouponByCouponId(long couponId) throws CouponException, RemoveException {

		Connection connection;
		connection = pool.getConnection();
		String sql = "delete from Company_Coupon where coupon_ID = ?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			connection.setAutoCommit(false);
			preparedStatement.setLong(1, couponId);
			preparedStatement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new RemoveException("connection failed " + e1.getMessage());
			}
			throw new RemoveException("remove company_coupon by coupon failed " + e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new RemoveException("connection failed " + e.getMessage());
			}
			try {
				pool.returnConnection(connection);
			} catch (CouponException e) {
				throw new RemoveException("connection failed " + e.getMessage());
			}

		}

	}

	@Override
	public void removeCompany_Coupon(Company company) throws RemoveException {
		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (CouponException e) {
			throw new RemoveException("connection failed " + e.getMessage());
		}
		String sql = "delete from Company_Coupon where company_ID = ?";

		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			connection.setAutoCommit(false);
			preparedStatement.setLong(1, company.getCompanyId());
			preparedStatement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new RemoveException("DataBase error " + e1.getMessage());
			}
			throw new RemoveException("remove Company_Coupon by companyId failed " + e.getMessage());

		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new RemoveException("connction failed " + e.getMessage());
			}
			try {
				pool.returnConnection(connection);
			} catch (CouponException e) {
				throw new RemoveException("connction failed " + e.getMessage());
			}
		}

	}

	@Override
	public List<Long> getCompanysByCouponId(long couponId) throws CouponException {
		try {
			pool = ConnectionPool.getInstance();
		} catch (SQLException e) {
			throw new CouponException("connnection failed");
		}
		Connection connection;
		connection = pool.getConnection();
		List<Company_Coupon> allList = getAllCompany_Coupons();
		List<Long> list = new ArrayList<Long>();
		String sql = "select * from Company_Coupon where Coupon_ID = " + couponId;
		try {
			Statement stm = connection.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				long company_Id = rs.getLong(1);
				list.add(company_Id);
			}
		} catch (Exception e) {
			throw new CouponException("didnt success to get company_coupon data");
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new CouponException("connection failed");
			}
			pool.returnConnection(connection);
		}

		return list;

	}

	@Override
	public List<Long> getCouponsByCompanyId(long companyId) throws CouponException {
		try {
			pool = ConnectionPool.getInstance();
		} catch (SQLException e) {
			throw new CouponException("connnection failed");
		}
		Connection connection;
		connection = pool.getConnection();
		List<Company_Coupon> allList = getAllCompany_Coupons();
		List<Long> list = new ArrayList<Long>();
		String sql = "select * from Company_Coupon where company_Id = " + companyId;
		try {
			Statement stm = connection.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				long coupon_Id = rs.getLong(1);
				list.add(coupon_Id);
			}
		} catch (Exception e) {
			throw new CouponException("didnt success to get company_coupon data");
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new CouponException("connection failed");
			}
			pool.returnConnection(connection);
		}

		return list;

	}

	@Override
	public List<Company_Coupon> getAllCompany_Coupons() throws CouponException {
		Connection connection = pool.getConnection();
		List<Company_Coupon> set = new ArrayList<Company_Coupon>();
		try {
			Statement stm = connection.createStatement();
			String sql = "SELECT * FROM COMPANY_COUPON";
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				long comp_Id = rs.getLong(1);
				long coupon_Id = rs.getLong(2);

				set.add(new Company_Coupon(comp_Id, coupon_Id));
			}
		} catch (SQLException e) {
			throw new CouponException("cannot get Company_Coupon data " + e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new CouponException("connection failed " + e.getMessage());
			}
			pool.returnConnection(connection);
		}
		return set;
	}

	@Override
	public void updateCompany_Coupon(long companyId, long couponId) throws UpdateException {
		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (CouponException e) {
			throw new UpdateException("connection failed " + e.getMessage());
		}
		try {
			Statement stm = connection.createStatement();
			String sql = "UPDATE Company_Coupon " + " SET company_ID='" + companyId + "', coupon_ID='" + couponId
					+ "' WHERE ID=" + companyId + AND + "' WHERE ID=" + couponId;
			stm.executeUpdate(sql);
		} catch (SQLException e) {
			throw new UpdateException("update error " + e.getMessage());
		}
		try {
			connection.close();
		} catch (SQLException e) {
			throw new UpdateException("connection failed " + e.getMessage());
		}
		try {
			pool.returnConnection(connection);
		} catch (CouponException e) {
			throw new UpdateException("connection failed " + e.getMessage());
		}
	}

	@Override
	public boolean isCouponExistsForCompany(long companyId, long couponId) throws CouponException {
		Connection connection = pool.getConnection();
		try {
			String sql = "SELECT coupon_Id FROM Company_Coupon WHERE company_Id = ? AND coupon_Id = ? ";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, companyId);
			pstmt.setLong(2, couponId);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			}
			return false;

		} catch (SQLException e) {
			throw new CouponException("ERROR! Checking if Coupon Exists For The Company is Failed. " + e.getMessage());
		} finally {
			pool.returnConnection(connection);
		}
	}

}
