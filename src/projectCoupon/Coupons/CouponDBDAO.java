package projectCoupon.Coupons;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import projectCoupon.ConnectionPool;
import projectCoupon.Exception.CouponException;
import projectCoupon.Exception.CreateException;
import projectCoupon.Exception.RemoveException;
import projectCoupon.Exception.UpdateException;

public class CouponDBDAO implements CouponDAO {
	private ConnectionPool pool;

	public CouponDBDAO() throws CouponException {
		pool = ConnectionPool.getInstance();
	}

	@Override
	public void insertCoupon(Coupon coupon) throws CreateException {
		Connection connection = null;
		try {
			connection = pool.getConnection();
		} catch (CouponException e) {
			throw new CreateException("didnt success to connect "+e.getMessage());
		}
		String sql = "INSERT INTO Coupon(ID,TITLE,START_DATE,END_DATE,AMOUNT,TYPE,MESSAGE,PRICE,IMAGE) VALUES(?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, coupon.getCouponId());
			pstmt.setString(2, coupon.getTitle());
			pstmt.setDate(3, (Date) coupon.getStart_date());
			pstmt.setDate(4, (Date) coupon.getEnd_date());
			pstmt.setInt(5, coupon.getAmount());
			pstmt.setString(6, coupon.getType().name());
			pstmt.setString(7, coupon.getMessage());
			pstmt.setDouble(8, coupon.getPrice());
			pstmt.setString(9, coupon.getImage());
			pstmt.executeUpdate();

		} catch (SQLException ex) {
			throw new CreateException("Coupon creation failed "+ex.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e1) {
				throw new CreateException("close connection was failed "+e1.getMessage());
			}
			try {
				pool.returnConnection(connection);
			} catch (Exception e) {
				throw new CreateException("close connection was failed "+e.getMessage());
			}
		}
	}

	@Override
	public void removeCoupon(Coupon Coupon) throws CreateException, RemoveException{
		Connection connection = null;
		try {
			connection = pool.getConnection();
		} catch (CouponException e) {
			throw new CreateException("didnt success to connect "+e.getMessage());
		}
		String sql = "DELETE FROM Coupon WHERE id=?";
		try { 
			PreparedStatement pstm1 = connection.prepareStatement(sql);
			connection.setAutoCommit(false);
			pstm1.setLong(1, Coupon.getCouponId());
			pstm1.executeUpdate();
			connection.commit();
		} 
		catch (SQLException e) {
			try {
				connection.rollback();
			} 
			catch (SQLException e1) {
				throw new RemoveException("Database error "+e1.getMessage());
			}
			throw new RemoveException("failed to remove Coupon "+e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new RemoveException("Database error "+e.getMessage());
			}
			try {
				pool.returnConnection(connection);
			} catch (Exception e) {
				throw new RemoveException("Database error "+e.getMessage());
			}
		}
	}

	@Override
	public void updateCoupon(Coupon Coupon) throws UpdateException, CreateException {
		Connection connection = null;
		try {
			connection = pool.getConnection();
		} catch (CouponException e) {
			throw new CreateException("didnt success to connect "+e.getMessage());
		}
		try {
			String sql = "UPDATE Coupon SET TITLE=?, START_DATE=?, END_DATE=?, AMOUNT=?,"
					+ " TYPE=?, MESSAGE=?, PRICE=?, IMAGE=? WHERE ID=?";
			PreparedStatement stm1 = connection.prepareStatement(sql);
			stm1.setString(1, Coupon.getTitle());
			stm1.setDate(2,  (Date)Coupon.getStart_date());
			stm1.setDate(3, (Date)Coupon.getEnd_date());
			stm1.setInt(4, Coupon.getAmount());
			stm1.setString(5, Coupon.getType().toString());
			stm1.setString(6, Coupon.getMessage());
			stm1.setDouble(7, Coupon.getPrice());
			stm1.setString(8, Coupon.getImage());
			stm1.setLong(9, Coupon.getCouponId());
			stm1.executeUpdate();
		} catch (SQLException e) {
			throw new UpdateException("update Coupon failed " + e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new UpdateException("Database error "+ e.getMessage());
			}
			try {
				pool.returnConnection(connection);
			} catch (CouponException e) {
				throw new CreateException("didnt success to return connection "+e.getMessage());
			}
		}
	}

	@Override
	public Coupon getCoupon(long couponId) throws CreateException, CouponException{
		Connection connection = null;
		try {
			connection = pool.getConnection();
		} catch (CouponException e) {
			throw new CreateException("didnt success to connect "+e.getMessage());
		}
		Coupon coupon = new Coupon();
		try {
			Statement stm = connection.createStatement();
			String sql = "SELECT * FROM Coupon WHERE ID=" + couponId;
			ResultSet rs = stm.executeQuery(sql);
			rs.next();
			coupon.setCouponId(rs.getLong(1));
			coupon.setTitle(rs.getString(2));
			coupon.setStart_date(rs.getDate(3));
			coupon.setEnd_date(rs.getDate(4));
			coupon.setAmount(rs.getInt(5));
			coupon.setMessage(rs.getString(7));
			coupon.setPrice(rs.getDouble(8));
			coupon.setImage(rs.getString(9));
			switch (rs.getString(6)) {
			case "Food":
				coupon.setType(couponType.food);
				break;
			case "Resturans":
				coupon.setType(couponType.Resturans);
				break;
			case "Electricity":
				coupon.setType(couponType.Electricity);
				break;
			case "Health":
				coupon.setType(couponType.Health);
				break;
			case "Sports":
				coupon.setType(couponType.Sports);
				break;
			case "Camping":
				coupon.setType(couponType.Camping);
				break;
			case "Traveling":
				coupon.setType(couponType.Traveling);
				break;
			default:
				System.out.println("Coupon not existent");
				break;
			}
		}

		catch (SQLException e) {
			throw new CouponException("unable to get Coupon data " + e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new CouponException("Database error "+e.getMessage());
			}
			try {
				pool.returnConnection(connection);
			} catch (Exception e) {
				throw new CouponException("Database error "+e.getMessage());
			}
		}
		return coupon;
	}

	@Override
	public List<Coupon> getAllCoupons() throws CouponException {
		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (Exception e) {
			throw new CouponException("Database error "+e.getMessage());
		}
		List<Coupon> set = new ArrayList<Coupon>();
		Coupon coupon;
		String sql = "SELECT * FROM Coupon";
		try {
			Statement stm = connection.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				coupon = new Coupon();
				coupon.setCouponId(rs.getLong(1));
				coupon.setTitle(rs.getString(2));
				coupon.setStart_date(rs.getDate(3));
				coupon.setEnd_date(rs.getDate(4));
				coupon.setAmount(rs.getInt(5));
				coupon.setMessage(rs.getString(7));
				coupon.setPrice(rs.getDouble(8));
				coupon.setImage(rs.getString(9));
				switch (rs.getString(6)) {
				case "food":
					coupon.setType(couponType.food);
					break;
				case "Resturans":
					coupon.setType(couponType.Resturans);
					break;
				case "Electricity":
					coupon.setType(couponType.Electricity);
					break;
				case "Health":
					coupon.setType(couponType.Health);
					break;
				case "Sports":
					coupon.setType(couponType.Sports);
					break;
				case "Camping":
					coupon.setType(couponType.Camping);
					break;
				case "Traveling":
					coupon.setType(couponType.Traveling);
					break;
				default:
					System.out.println("Coupon not existent");
					break;
				}
				set.add(coupon);
			}
		} catch (SQLException e) {
			System.out.println(e);
			throw new CouponException("cannot get Coupon data "+e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new CouponException("Database error "+e.getMessage());
			}
			try {
				pool.returnConnection(connection);
			} catch (Exception e) {
				throw new CouponException("Database error "+e.getMessage());
			}
		}
		return set;
	}

	@Override
	public void removeCouponID(long id) throws CouponException {
		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (Exception e) {
			throw new CouponException("Database error "+e.getMessage());
		}
		String sql = "DELETE FROM Coupon WHERE id=?";
		try { 
			PreparedStatement pstm1 = connection.prepareStatement(sql);
			connection.setAutoCommit(false);
			pstm1.setLong(1, id);
			pstm1.executeUpdate();
			connection.commit();

		} catch (SQLException e) {
			try {
				connection.rollback();

			} catch (SQLException e1) {
				throw new CouponException("Database error "+e1.getMessage());
			}
			throw new CouponException("failed to remove Coupon "+e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new CouponException("Database error "+e.getMessage());
			}
			try {
				pool.returnConnection(connection);
			} catch (Exception e) {
				throw new CouponException("Database error "+e.getMessage());
			}
		}

	}
//we need this ???
	@Override
	public List<Long> removeExpiredCoupons() throws CouponException {
		Connection connection;
		List<Long> expieredList = new ArrayList<Long>();
		try {
			connection = pool.getConnection();
		} catch (Exception e) {
			throw new CouponException("connection failed " + e.getMessage());
		}
		try {
			String sql = "SELECT id FROM app.Coupon WHERE end_date < CURRENT_DATE ";
			Statement pstmt = connection.createStatement();
			ResultSet rs = pstmt.executeQuery(sql);
			while (rs.next()) {
				expieredList.add(rs.getLong(1));
			}
			return expieredList;
		} catch (SQLException e) {
			throw new CouponException("DB ERROR! Remove Expired Coupon Failed. "+e.getMessage());
		}  finally {
			pool.returnConnection(connection);
		}
	}

	@Override
	public List<Coupon> getAllCouponsByType(couponType coupType) throws CouponException {
		pool = ConnectionPool.getInstance();
		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (Exception e1) {
			throw new CouponException("Database error");
		}
		List<Coupon> list = new ArrayList<Coupon>();
		String sql = String.format("select * from Coupon where TYPE = '%s'", coupType.name());

		try {
			Statement statement = connection.createStatement(); 
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				Coupon coupon = new Coupon();
				coupon.setCouponId(resultSet.getLong(1));
				coupon.setTitle(resultSet.getString(2));
				coupon.setStart_date(resultSet.getDate(3));
				coupon.setEnd_date(resultSet.getDate(4));
				coupon.setAmount(resultSet.getInt(5));
				coupon.setType(coupType);
				coupon.setMessage(resultSet.getString(7));
				coupon.setPrice(resultSet.getDouble(8));
				coupon.setImage(resultSet.getString(9));
				list.add(coupon);
			}
			return list;
		} catch (SQLException e) {
			System.out.println(e);
			throw new CouponException(
					"DB error - unable to get Coupon data. couponType: " + coupType.name());
		}  finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new CouponException("Database error");
			}
			try {
				pool.returnConnection(connection);
			} catch (Exception e) {
				throw new CouponException("Database error");
			}
		}
	}

	@Override
	public List<Coupon> getAllCouponsByDate(String untilDate) throws CouponException {
		// TODO generated getAllCouponsByDate
		return null;
	}

	@Override
	public void removeCoupon(long coupId) throws CouponException, CreateException, RemoveException {
		removeCoupon(getCoupon(coupId));
	}

	@Override
	public List<Coupon> getAllCouponsByPrice(double priceMax) throws CouponException {
		Connection connection;
		try {
			connection = pool.getConnection();
		} catch (Exception e) {
			throw new CouponException("Database error "+e.getMessage());
		}
		List<Coupon> set = new ArrayList<Coupon>();
		Coupon coupon;
		String sql = String.format("select * from Coupon where Price = '%f'", priceMax);
		try {
			Statement stm = connection.createStatement();
			ResultSet rs = stm.executeQuery(sql);
			while (rs.next()) {
				coupon = new Coupon();
				coupon.setCouponId(rs.getLong(1));
				coupon.setTitle(rs.getString(2));
				coupon.setStart_date(rs.getDate(3));
				coupon.setEnd_date(rs.getDate(4));
				coupon.setAmount(rs.getInt(5));
				coupon.setMessage(rs.getString(7));
				coupon.setPrice(rs.getDouble(8));
				coupon.setImage(rs.getString(9));
				switch (rs.getString(6)) {
				case "food":
					coupon.setType(couponType.food);
					break;
				case "Resturans":
					coupon.setType(couponType.Resturans);
					break;
				case "Electricity":
					coupon.setType(couponType.Electricity);
					break;
				case "Health":
					coupon.setType(couponType.Health);
					break;
				case "Sports":
					coupon.setType(couponType.Sports);
					break;
				case "Camping":
					coupon.setType(couponType.Camping);
					break;
				case "Traveling":
					coupon.setType(couponType.Traveling);
					break;
				default:
					System.out.println("Coupon not existent");
					break;
				}
				set.add(coupon);
			}
		} catch (SQLException e) {
			System.out.println(e);
			throw new CouponException("cannot get Coupon data "+e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new CouponException("Database error "+e.getMessage());
			}
			try {
				pool.returnConnection(connection);
			} catch (Exception e) {
				throw new CouponException("Database error "+e.getMessage());
			}
		}
		return set;
	}

	@Override
	public boolean isCouponTitleExists(String Title) throws CouponException {
		Connection connection = null;
		try {
			connection = pool.getConnection();
		} catch (Exception e1) {
			throw new CouponException("connection failed");
		}
		try {
			String sql = "SELECT couponId FROM Coupon WHERE title = ? ";
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, Title);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return true;
			}
			return false;

		} catch (SQLException e) {

			throw new CouponException(" ERROR! Checking if Coupon Title Exists Failed.");

		} catch (Exception e) {

			throw new CouponException("ERROR! Checking if Coupon Title Exists Failed.");

		} finally {
			try {
				pool.returnConnection(connection);
			} catch (CouponException e) {
			}

		}
	}
}