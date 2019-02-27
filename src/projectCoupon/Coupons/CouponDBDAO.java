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
import projectCoupon.Exception.ConnectionException;
import projectCoupon.Exception.CouponException;
import projectCoupon.Exception.CreateException;

	public class CouponDBDAO implements CouponDAO {
		private ConnectionPool pool;
		
		public CouponDBDAO() throws CouponException {
			pool = ConnectionPool.getInstance();
		}

		@Override
		public void insertCoupon(Coupon coupon)throws CreateException, SQLException {
			Connection connection = null ;
			try {
				connection = pool.getConnection();
			} catch (ConnectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
				System.out.println(ex.getLocalizedMessage());
				throw new CreateException("Coupon creation failed");
			} finally {
				connection.close();
				try {
					pool.returnConnection(connection);
				} catch (ConnectionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
				
				
			
			
			
		
			

		@Override
		public void removeCoupon(Coupon Coupon) throws Exception {
			Connection connection=pool.getConnection();
			String sql = "DELETE FROM Coupon WHERE id=?";

			//what is the different statement and preparedStatement
			try (PreparedStatement pstm1 = connection.prepareStatement(sql);) {
				connection.setAutoCommit(false);
		
				pstm1.setLong(1, Coupon.getCouponId());
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
				connection.close();
				pool.returnConnection(connection);
			}
		}
			
		

		@Override
		public void updateCoupon(Coupon Coupon) throws Exception {
			Connection connection=pool.getConnection();
			try { 
				//Statement stm = connection.createStatement();
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
				stm1.setLong(9, Coupon.getCouponId());
				stm1.executeUpdate();
				//stm1.executeUpdate(sql);
				System.out.println("update success");
			} 
			catch (SQLException e) {
				throw new Exception("update Coupon failed "+ e.getMessage());
			}
			finally{
				connection.close();
				pool.returnConnection(connection);
			}
		}
			
		@Override
		public Coupon getCoupon(long couponId) throws Exception {
			Connection connection=pool.getConnection();
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
				throw new Exception("unable to get Coupon data " + e.getMessage());
			} finally {
				connection.close();
				pool.returnConnection(connection);
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
				throw new Exception("cannot get Coupon data");
			} finally {
				connection.close();
				pool.returnConnection(connection);
			}
			return set;
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
				connection.close();
				pool.returnConnection(connection);
			}
			
		}


		@Override
		public void removeExpiredCoupons() throws CouponException {
			Connection connection;
			try {
				connection = pool.getConnection();
			} catch (ConnectionException e1) {
				throw new ConnectionException("connection failed");
			}
			try {
				String sql = "SELECT id FROM app.Coupon WHERE end_date < CURRENT_DATE ";
				Statement pstmt = connection.createStatement();
				ResultSet rs = pstmt.executeQuery(sql); 
				while (rs.next()) {
					this.removeCoupon(rs.getLong("id"));			
				} 
			} catch (SQLException e) {
				throw new CouponException("DB ERROR! Remove Expired Coupon Failed.");
			} catch (Exception e) {
				throw new CouponException("APP ERROR! Remove Expired Coupon Failed.");
			} finally {
				pool.returnConnection(connection);
			}
		}

		

		@Override
		public List<Coupon> getCouponsByType(long couponId, couponType coupType) throws Exception {
			pool = ConnectionPool.getInstance();
			Connection connection =pool.getConnection();
			List<Coupon> list = new ArrayList<>();
			String sql = String.format("select * from Coupon where ID = %d and TYPE = '%s'", couponId, coupType.name());

			try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(sql)) {

				while (resultSet.next()) {
					Coupon coupon = new Coupon();
					coupon.setCouponId(resultSet.getLong(1));
					coupon.setTitle(resultSet.getString(2));
					coupon.setStart_date(resultSet.getDate(3));
					coupon.setEnd_date(resultSet.getDate(4));
					coupon.setAmount(resultSet.getInt(5));
					switch (resultSet.getString(6)) {
					case "Resturants":
						coupon.setType(couponType.Resturans);
						break;
					case "Health":
						coupon.setType(couponType.Health);
						break;
					case "Sports":
						coupon.setType(couponType.Sports);
						break;
					case "Traveling":
						coupon.setType(couponType.Traveling);
						break;
					default:
						break;
					}
					coupon.setMessage(resultSet.getString(7));
					coupon.setPrice(resultSet.getDouble(8));
					coupon.setImage(resultSet.getString(9));

					list.add(coupon);
				}

			} catch (SQLException e) {
				System.out.println(e);
				throw new Exception("DB error - unable to get Coupon data. couponId: " + couponId + " couponType: "+ coupType.name());
			}catch (Exception e) {
				throw new Exception("unable to get Coupon data. couponId: " + couponId + " couponType: "+ coupType.name());
			}finally {
				connection.close();
				pool.returnConnection(connection);
			}
			return list;
		}
		
		@Override
		public List<Coupon> getAllPurchasedCouponsByPrice(long customerId, long price) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<Coupon> getAllPurchasedCouponsByType(long customerId, couponType type) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<Coupon> getAllPurchasedCoupons(long customerId) {
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

		

		@Override
		public List<Coupon> getAllCouponsByPrice(long couponId, double priceMax) throws Exception {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<Coupon> getAllCouponsByDate(long couponId, String untilDate) throws Exception {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<Coupon> getAllCoupons(long couponId) throws Exception {
			// TODO Auto-generated method stub
			return null;
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
		public List<Coupon> getAllCouponsByType(long couponId, String typeName) throws Exception {
			// TODO Auto-generated method stub
			return null;
		}
	
	}


	