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
import projectCoupon.Exception.CreateException;
import projectCoupon.Exception.RemoveException;
import projectCoupon.Exception.UpdateException;

public class Company_CouponDBDAO implements Company_CouponDAO {
	private static final String AND = null;
	private ConnectionPool pool;

	public Company_CouponDBDAO() throws CouponException {
		pool = ConnectionPool.getInstance();
	}

	
	@Override
	public void removeCompany_Coupon(Company company, Coupon coupon) throws RemoveException {
		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (CouponException e2) {
			throw new RemoveException("connection failed");
		}
		String sql = "DELETE FROM COMPANY_COUPON  WHERE companyId=? AND couponId=?";

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
				throw new RemoveException("Database error");
			}
			throw new RemoveException("failed to remove company_Coupon");
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

	@Override
	public List<Company_Coupon> getCompanysByCouponId(long couponId) throws CouponException {
		Connection connection = pool.getConnection();
		List<Company_Coupon> list = new ArrayList<Company_Coupon>();
		try {
			Statement stm = connection.createStatement();
			String sql = "SELECT companyId FROM COMPANY_COUPON WHERE couponId=?";
			List<Company_Coupon> allList = getAllCompany_Coupons();
			for (Company_Coupon iter : allList) {
				if (iter.getCouponId() == couponId) {
					list.add(iter);
				}

			}
		} catch (Exception e) {
			System.out.println(e);
		}
		try {
			connection.close();
		} catch (SQLException e) {
			throw new CouponException("connection failed");
		}
		pool.returnConnection(connection);

		return list;
	}

	@Override
	public List<Company_Coupon> getCouponsByCompanyId(long companyId) throws CouponException {
		Connection connection = pool.getConnection();
		List<Company_Coupon> list = new ArrayList<Company_Coupon>();
		try {
			Statement stm = connection.createStatement();
			String sql = "SELECT couponId FROM COMPANY_COUPON WHERE companyId=?";

			List<Company_Coupon> allList = getAllCompany_Coupons();
			for (Company_Coupon iter : allList) {
				if (iter.getCompanyId() == companyId) {
					list.add(iter);
				}
			}
		}

		catch (Exception e) {
			System.out.println(e);
			throw new CouponException("cannot get Company_Coupon data");
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
			System.out.println(e);
			throw new CouponException("cannot get Company_Coupon data");
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new CouponException("connection failed");
			}
			pool.returnConnection(connection);
		}
		return set;
	}

	@Override
	public void updateCompany_Coupon(Company company,Coupon coupon) throws UpdateException {
		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (CouponException e1) {
			throw new UpdateException("connection failed");
		}
		try {
			Statement stm = connection.createStatement();
			String sql = "UPDATE Company_Coupon " + " SET companyId='" + company.getCompanyId() + "', coupon_Id='"
					+ coupon.getCouponId() + "' WHERE ID=" + company.getCompanyId() + AND + "' WHERE ID="
					+ coupon.getCouponId();
			stm.executeUpdate(sql);
		} catch (SQLException e) {
			throw new UpdateException("update error");
		}
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

	

	@Override
	public void removeCompany_CouponByCoupon(Coupon coupon) throws RemoveException { {
		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (Exception e1) {
			throw new RemoveException("connection failed");
		}

		String sql = "delete from Company_Coupon where couponId = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			connection.setAutoCommit(false); 
			preparedStatement.setLong(1, coupon.getCouponId());
			preparedStatement.executeUpdate();
			connection.commit();
			System.out.println("Company_Coupon remove succeeded. couponId: " + coupon.getCouponId());

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (Exception exception) {
				throw new RemoveException("connection failed");
			}
			throw new RemoveException("remove company_coupon by coupon failed");
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new RemoveException("connection failed");
			}
			try {
				pool.returnConnection(connection);
			} catch (Exception e) {
				throw new RemoveException("connection failed");
			}
		}

	}

	

	@Override
	public void removeCompany_Coupon(Company company) throws RemoveException {
		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (Exception e1) {
			throw new RemoveException("connection failed");
		}
		

		String sql = "delete from Company_Coupon where companyId = ?";

		try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

			connection.setAutoCommit(false);
			preparedStatement.setLong(1, company.getCompanyId());
			preparedStatement.executeUpdate();
			connection.commit();
			System.out.println("Company_Coupon remove succeeded. companyId: " + company.getCompanyId());

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (Exception exception) {
				throw new RemoveException("DataBase error");
			}
			throw new RemoveException("remove Company_Coupon by companyId failed");
			
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
          throw new RemoveException("connction failed");
			}
			try {
				pool.returnConnection(connection);
			} catch (Exception e) {
				 throw new RemoveException("connction failed");
			}
	}
	
	}

	public void insertCompany_Coupon(Company_Coupon company_Coupon) throws CreateException {
		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (CouponException e) {
			throw new CreateException("connection failed");
		}
		
		String sql = "INSERT INTO Company_Coupon(companyId,couponId) VALUES(?,?)";

		try {

			PreparedStatement pstmt = connection.prepareStatement(sql);
			{

				pstmt.setLong(1, company_Coupon.getCompanyId());
				pstmt.setLong(2, company_Coupon.getCouponId());

				pstmt.executeUpdate();
				System.out.println("Company_Coupon added: companyId: " + company_Coupon.getCompanyId() + " couponId: "
						+ company_Coupon.getCouponId());
			}
		} catch (SQLException ex) {
			System.out.println(ex.getLocalizedMessage());
			throw new CreateException("Company_Coupon creation failed");
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


	


	@Override
	public void removeCompany_Coupon(Company_Coupon company_Coupon) throws RemoveException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void removeCompany_CouponByCompany(Company company) throws RemoveException {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void updateCompany_Coupon(Company_Coupon company_Coupon) throws UpdateException {
		// TODO Auto-generated method stub
		
	}

	
		
	}

	

