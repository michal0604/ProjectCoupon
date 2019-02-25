package projectCoupon.Coupons;

	import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import projectCoupon.ConnectionPool;
import projectCoupon.Database;
import projectCoupon.Exception.CouponException;

	public class CouponDBDAO implements CouponDAO {
		private ConnectionPool pool;
		
		public CouponDBDAO() throws CouponException {
			pool = ConnectionPool.getInstance();
		}

		@Override
		public void insertCoupon(Coupon coupon) throws Exception {
			Connection connection=pool.getConnection();
			String sql = "INSERT INTO Coupon(TITLE,START_DATE,END_DATE,AMOUNT,TYPE,MESSAGE,PRICE,IMAGE) VALUES(?,?,?,?,?,?,?,?)";
			try {			       
    			PreparedStatement pstmt = connection.prepareStatement(sql);
				pstmt.setString(1, coupon.getTitle());
				pstmt.setDate(2, (Date) coupon.getStart_date());
				pstmt.setDate(3, (Date) coupon.getEnd_date());
				pstmt.setInt(4, coupon.getAmount());
				pstmt.setString(5, coupon.getType().name());
				pstmt.setString(6, coupon.getMessage());
				pstmt.setDouble(7, coupon.getPrice());
				pstmt.setString(8, coupon.getImage());
				pstmt.executeUpdate();

			} catch (SQLException ex) {
				System.out.println(ex.getLocalizedMessage());
				throw new SQLException("Coupon creation failed");
			} finally {
				pool.closeAllConnections(connection);
			}
		}
			

		@Override
		public void removeCoupon(Coupon Coupon) throws Exception {
			Connection connection=pool.getConnection();
			String sql = "DELETE FROM Coupon WHERE id=?";

			//what is the different statement and preparedStatement
			try (PreparedStatement pstm1 = connection.prepareStatement(sql);) {
				connection.setAutoCommit(false);
		
				pstm1.setLong(1, Coupon.getId());
				pstm1.executeUpdate();
				connection.commit();
	
			} catch (SQLException e) {
				try {
					connection.rollback();
			
				} catch (SQLException e1) {
					throw new Exception("Database error");
				}
				throw new Exception("failed to remove Coupon");
			} finally {
				pool.closeAllConnections(connection);
			}
		}
			
		

		@Override
		public void updateCoupon(Coupon Coupon) throws Exception {
			Connection connection=pool.getConnection();
			try { 
				Statement stm = connection.createStatement();
				String sql = "UPDATE Coupon SET TITLE=?, START_DATE=?, END_DATE=?, AMOUNT=?,"
						+ " TYPE=?, MESSAGE=?, PRICE=?, IMAGE=? WHERE ID=?";
				PreparedStatement stm1= connection.prepareStatement (sql);
				stm1.setString(1, Coupon.getTitle());
				stm1.setDate(2, Coupon.getStart_date());
				stm1.setDate(3, Coupon.getEnd_date());
				stm1.setInt(4, Coupon.getAmount());
				stm1.setString(5, Coupon.getType().toString());
				stm1.setString(6, Coupon.getMessage());
				stm1.setDouble(7, Coupon.getPrice());
				stm1.setString(8, Coupon.getImage());
				stm1.setLong(9, Coupon.getId());
				stm1.executeUpdate();
				//stm1.executeUpdate(sql);
				System.out.println("update success");
			} 
			catch (SQLException e) {
				throw new Exception("update Coupon failed "+ e.getMessage());
			}
			finally{
				pool.closeAllConnections(connection);
			}
		}
			
		@Override
		public Coupon getCoupon(long id) throws Exception {
			Connection connection=pool.getConnection();
			Coupon coupon = new Coupon();
			try {
				Statement stm = connection.createStatement();
				String sql = "SELECT * FROM Coupon WHERE ID=" + id;
				ResultSet rs = stm.executeQuery(sql);
				rs.next();
				coupon.setId(rs.getLong(1));
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
				throw new Exception("unable to get Coupon data " + e.getMessage());
			} finally {
				pool.closeAllConnections(connection);
			}
			return coupon;
		}
		

		@Override
		public List<Coupon> getAllCoupons() throws Exception {
			Connection connection = pool.getConnection();
			List<Coupon> set = new ArrayList<Coupon>();
			Coupon coupon;
			String sql = "SELECT * FROM Coupon";
			try {
				Statement stm = connection.createStatement(); 
				ResultSet rs = stm.executeQuery(sql);
				while (rs.next()) {
					coupon = new Coupon();
					coupon.setId(rs.getLong(1));
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
				throw new Exception("cannot get Coupon data");
			} finally {
				connection.close();
			}
			return set;
		}

		@Override
		public Coupon dropTable() throws Exception {
			Connection connection=null;
			try {
				// Create a connection:
				connection = pool.getConnection();

				// Create sql command for delete one record:
				String sql = "drop table ",Coupon;

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
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			return null;

			
		}


		@Override
		public void removeCouponID(long id) throws Exception {
			Connection connection=pool.getConnection();
			String sql = "DELETE FROM Coupon WHERE id=?";

			//what is the different statement and preparedStatement
			try (PreparedStatement pstm1 = connection.prepareStatement(sql);) {
				connection.setAutoCommit(false);
		
				pstm1.setLong(1, id);
				pstm1.executeUpdate();
				connection.commit();
	
			} catch (SQLException e) {
				try {
					connection.rollback();
			
				} catch (SQLException e1) {
					throw new Exception("Database error");
				}
				throw new Exception("failed to remove Coupon");
			} finally {
				pool.closeAllConnections(connection);
			}
			
		}


		@Override
		public void removeExpiredCoupons(long coupId) throws CouponException {
			Connection connection = pool.getConnection();
			try {
				connection.setAutoCommit(false);
				String sql = "DELETE FROM app.CompanyCoupon WHERE coupon_id = ?";
				PreparedStatement pstmt = connection.prepareStatement(sql);
				pstmt.setLong(1, coupId);
				pstmt.executeUpdate();
				
				sql = "DELETE FROM app.CustomerCoupon WHERE coupon_id = ?";
				pstmt = connection.prepareStatement(sql);
				pstmt.setLong(1, coupId);
				pstmt.executeUpdate();
				
				sql = "DELETE FROM app.Coupon WHERE id = ?";
				pstmt = connection.prepareStatement(sql);
				pstmt.setLong(1, coupId);
				pstmt.executeUpdate();
				
				connection.commit();
				
			} catch (SQLException e) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					throw new CouponException("DB ERROR! Remove Coupon Failed. RollBack Transaction Failed!");
				}
				throw new CouponException("DB ERROR! Remove Coupon Failed.");
			} catch (Exception e) {
				throw new CouponException("APP ERROR! Remove Coupon Failed.");
			} finally {
				pool.returnConnection(connection);
			}
		}

		//TODO function empty
		@Override
		public boolean isCouponExistsForCompany(long companyId, long coupId) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void removeCoupon(long coupId) throws Exception {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean isCouponTitleExists(String coupTitle) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public Set<Coupon> getCoupons(long companyId, int i, int j, boolean b) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Set<Coupon> getCouponsByType(long companyId, couponType coupType) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Set<Coupon> getCouponsByMaxCouponPrice(long companyId, double price) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Set<Coupon> getCouponsByMaxCouponDate(long companyId, Date maxCouponDate) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Set<Coupon> getAllPurchasedCouponsByPrice(long customerId, long price) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Set<Coupon> getAllPurchasedCouponsByType(long customerId, couponType type) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Set<Coupon> getAllPurchasedCoupons(long customerId) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void purchaseCoupon(long customerId, long coupId) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean isCouponPurchasedByCustomer(long customerId, long coupId) {
			// TODO Auto-generated method stub
			return false;
		}
			
	}


	