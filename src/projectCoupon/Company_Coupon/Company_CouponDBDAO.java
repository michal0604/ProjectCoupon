package projectCoupon.Company_Coupon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import projectCoupon.ConnectionPool;
import projectCoupon.Company.Company;
import projectCoupon.Coupons.Coupon;
import projectCoupon.Exception.CouponException;

public class Company_CouponDBDAO implements Company_CouponDAO {
	private static final String AND = null;
	private ConnectionPool pool;

	public Company_CouponDBDAO() throws CouponException {
		pool = ConnectionPool.getInstance();
	}

	@Override
	public void insertCompany_Coupon(Company company, Coupon coupon) throws Exception {
		Connection connection = pool.getConnection();
		// Database.createTables(con);
		String sql = "INSERT INTO Company_Coupon(COMP_ID,COUPON_ID) VALUES(?,?)";

		try {

			PreparedStatement pstmt = connection.prepareStatement(sql);
			{

				pstmt.setLong(1, company.getCompanyId());
				pstmt.setLong(2, coupon.getCouponId());

				pstmt.executeUpdate();
				System.out.println("Company_Coupon added: companyId: " + company.getCompanyId() + " couponId: "
						+ coupon.getCouponId());
			}
		} catch (SQLException ex) {
			System.out.println(ex.getLocalizedMessage());
			throw new Exception("Company_Coupon creation failed");
		} finally {
			connection.close();

			pool.returnConnection(connection);
		}

	}

	@Override
	public void removeCompany_Coupon(Company company, Coupon coupon) throws Exception {
		Connection connection = pool.getConnection();
		String sql = "DELETE FROM COMPANY_COUPON  WHERE COMP_ID=? AND COUPON_ID=?";

		try {
			PreparedStatement stm = connection.prepareStatement(sql);
			{
			}
			connection.setAutoCommit(false);
			stm.setLong(1, company.getCompanyId());
			stm.setLong(2, coupon.getCouponId());
			stm.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				throw new Exception("Database error");
			}
			throw new Exception("failed to remove company_Coupon");
		} finally {
			connection.close();
			pool.returnConnection(connection);
		}
	}

	@Override
	public List<Company_Coupon> getCompanysByCouponId(long couponId) throws Exception {
		Connection connection = pool.getConnection();
		List<Company_Coupon> list = new ArrayList<Company_Coupon>();
		try {
			//Statement stm = connection.createStatement();
			//String sql = "SELECT COMP_ID FROM COMPANY_COUPON WHERE COUPON_ID=?";
			List<Company_Coupon> allList = getAllCompany_Coupons();
			for (Company_Coupon iter : allList) {
				if (iter.getCoupon_Id() == couponId) {
					list.add(iter);
				}

			}
		} catch (Exception e) {
			System.out.println(e);
		}
		pool.closeAllConnections(connection);

		return list;
	}

	@Override
	public List<Company_Coupon> getCouponsByCompanyId(long companyId) throws Exception {
		Connection connection = pool.getConnection();
		List<Company_Coupon> list = new ArrayList<Company_Coupon>();
		try {
			//Statement stm = connection.createStatement();
			//String sql = "SELECT COUPON_ID FROM COMPANY_COUPON WHERE COMP_ID=?";

			List<Company_Coupon> allList = getAllCompany_Coupons();
			for (Company_Coupon iter : allList) {
				if (iter.getComp_Id() == companyId) {
					list.add(iter);
				}
			}
		}

		catch (Exception e) {
			System.out.println(e);
			throw new Exception("cannot get Company_Coupon data");
		} finally {
			pool.closeAllConnections(connection);
		}
		return list;
	}

	@Override
	public List<Company_Coupon> getAllCompany_Coupons() throws Exception {
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
			System.out.println(e);
			throw new Exception("cannot get Company_Coupon data");
		} finally {
			pool.closeAllConnections(connection);
		}
		return set;
	}

	@Override
	public void updateCompany_Coupon(Company_Coupon company_Coupon) throws Exception {
		Connection connection = pool.getConnection();
		try {
			Statement stm = connection.createStatement();
			String sql = "UPDATE Company_Coupon " + " SET comp_Id='" + company_Coupon.getComp_Id() + "', coupon_Id='"
					+ company_Coupon.getCoupon_Id() + "' WHERE ID=" + company_Coupon.getComp_Id() + AND + "' WHERE ID="
					+ company_Coupon.getCoupon_Id();
			stm.executeUpdate(sql);
		} catch (SQLException e) {
			throw new Exception("update error");
		}
		pool.closeAllConnections(connection);
	}

	@Override
	public void removeCompany_Coupon(Company_Coupon company_Coupon) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeCompany_Coupon(Coupon coupon) throws Exception {
		// TODO Auto-generated method stub

	}

	public void insertCompany_Coupon(Company_Coupon company_Coupon) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeCompany_Coupon(Company company) throws Exception {
		// TODO Auto-generated method stub
		
	}
}
