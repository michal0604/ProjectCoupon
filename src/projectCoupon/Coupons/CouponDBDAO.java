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
		public void insertCoupon(Coupon coupon)throws CreateException {
			Connection connection = null ;
			try {
				connection = pool.getConnection();
			} catch (Exception e) {
				throw new CreateException("didnt success to connect");
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
				throw new CreateException("Coupon creation failed");
			} finally {
				try {
					connection.close();
				} catch (SQLException e1) {
					throw new CreateException("close connection was failed");
				}
				try {
					pool.returnConnection(connection);
				} catch (Exception e) {
					throw new CreateException("close connection was failed");
				}
			}
		}
				
			

		@Override
          public void removeCoupon(Coupon Coupon)throws RemoveException {
			Connection connection = null;
			try {
				connection = pool.getConnection();
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
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
					throw new RemoveException("Database error");
				}
				throw new RemoveException("failed to remove Coupon");
			} finally {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new RemoveException("Database error");
				}
				try {
					pool.returnConnection(connection);
				} catch (Exception e) {
					throw new RemoveException("Database error");
				}
			}
		}
	

		@Override
		public void updateCoupon(Coupon Coupon) throws UpdateException {
			Connection connection = null;
			try {
				connection = pool.getConnection();
			} catch (Exception e1) {
				throw new UpdateException("Database error");
			}
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
				throw new UpdateException("update Coupon failed "+ e.getMessage());
			}
			finally{
				try {
					connection.close();
				} catch (SQLException e) {
					throw new UpdateException("Database error");
				}
				try {
					pool.returnConnection(connection);
				} catch (Exception e) {
					throw new UpdateException("Database error");
				}
			}
		}
			
		@Override
		public Coupon getCoupon(long couponId) throws CouponException {
			Connection connection = null;
			try {
				connection = pool.getConnection();
			} catch (Exception e1) {
				throw new CouponException("Database error");
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
					throw new CouponException("Database error");
				}
				try {
					pool.returnConnection(connection);
				} catch (Exception e) {
					throw new CouponException("Database error");
				}
			}
			return coupon;
		}
		

		@Override
		public List<Coupon> getAllCoupons() throws CouponException {
			Connection connection;
			try {
				connection = pool.getConnection();
			} catch (Exception e1) {
				throw new CouponException("Database error");
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
				throw new CouponException("cannot get Coupon data");
			} finally {
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
			return set;
		}


		@Override
		public void removeCouponID(long id) throws CouponException {
			Connection connection;
			try {
				connection = pool.getConnection();
			} catch (Exception e2) {
				throw new CouponException("Database error");
			}
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
					throw new CouponException("Database error");
				}
				try {
					throw new Exception("failed to remove Coupon");
				} catch (Exception e1) {
					throw new CouponException("Database error");
				}
			} finally {
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
		//TODO this function should get a DATE and replace current Data
		public List<Long> removeExpiredCoupons() throws CouponException {
			Connection connection;
			List<Long> removedIdList = new ArrayList<Long>();
			try {
				connection = pool.getConnection();
			} catch (Exception e1) {
				throw new CouponException("connection failed"+e1);
			}
			try {
				String sql = "SELECT id FROM app.Coupon WHERE end_date < CURRENT_DATE ";
				Statement pstmt = connection.createStatement();
				ResultSet rs = pstmt.executeQuery(sql); 
				while (rs.next()) {
					removedIdList.add(rs.getLong("id"));
				} 
			} catch (SQLException e) {
				throw new CouponException("DB ERROR! Remove Expired Coupon Failed.");
			} catch (Exception e) {
				throw new CouponException("APP ERROR! Remove Expired Coupon Failed.");
			} finally {
				try {
					pool.returnConnection(connection);
				} catch (Exception e) {
					throw new CouponException("Database error");
				}
			}
			return removedIdList;
		}

		

		@Override
		public List<Coupon> getCouponsByType(long couponId, couponType coupType) throws CouponException {
			pool = ConnectionPool.getInstance();
			Connection connection;
			try {
				connection = pool.getConnection();
			} catch (Exception e1) {
				throw new CouponException("Database error");
			}
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
				throw new CouponException("DB error - unable to get Coupon data. couponId: " + couponId + " couponType: "+ coupType.name());
			}catch (Exception e) {
				throw new CouponException("unable to get Coupon data. couponId: " + couponId + " couponType: "+ coupType.name());
			}finally {
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
			return list;
		}
		
	

		@Override
		public List<Coupon> getAllCouponsByDate(long couponId, String untilDate) throws CouponException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<Coupon> getAllCoupons(long couponId) throws CouponException {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public void removeCoupon(long coupId) throws CouponException {
			// TODO Auto-generated method stub
			
		}

		

		@Override
		public List<Coupon> getAllCouponsByType(long couponId, String typeName) throws CouponException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<Coupon> getAllCouponsByPrice(long couponId, double priceMax) throws CouponException {
			// TODO Auto-generated method stub
			return null;
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

		@Override
		public boolean isCouponExistsForCompany(long companyId, long couponId) throws CouponException {
			Connection connection = pool.getConnection();
			try {
				String sql = "SELECT couponId FROM Company_Coupon WHERE companyId = ? AND couponId = ? "; 
				PreparedStatement pstmt = connection.prepareStatement(sql);
				pstmt.setLong(1, companyId);
				pstmt.setLong(2, couponId);
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					return true;
				} 
				return false;
					
			} catch (SQLException e) {
				throw new CouponException("ERROR! Checking if Coupon Exists For The Company is Failed.");
			} catch (Exception e) {
				throw new CouponException(" ERROR! Checking if Coupon Exists For The Company is Failed.");
			} finally {
				pool.returnConnection(connection);
			}
		}

		@Override
		public boolean isCouponPurchasedByCustomer(long custId, long coupId) throws CouponException {
			Connection connection = pool.getConnection();
			try {
				String sql = "SELECT couponId FROM Customer_Coupon WHERE customerId = ? AND couponId = ? "; 
				PreparedStatement pstmt = connection.prepareStatement(sql);
				pstmt.setLong(1, custId);
				pstmt.setLong(2, coupId);
				ResultSet rs = pstmt.executeQuery(); 
				if (rs.next()) {
					return true;
				} 
				return false;
					
			} catch (SQLException e) {
				throw new CouponException("ERROR! Checking If Coupon Already Exists For Company is Failed.");
			} catch (Exception e) {
				throw new CouponException("ERROR! Checking If Coupon Already Exists For Company is Failed.");
			} finally {
				pool.returnConnection(connection);
			}
		}

		@Override
		public void purchaseCoupon(long custId, long coupId) throws CouponException {
			Connection connection = pool.getConnection();

			try {
				connection.setAutoCommit(false);
				String sql = "INSERT INTO app.Customer_Coupon (customerId,couponId) VALUES (?,?)";
				PreparedStatement pstmt = connection.prepareStatement(sql);

				pstmt.setLong(1, custId);
				pstmt.setLong(2, coupId);

				pstmt.executeUpdate();
				
				sql = "UPDATE app.Coupon SET amount = amount - 1 WHERE id = ?";
				pstmt = connection.prepareStatement(sql);
				pstmt.setLong(1, coupId);
			
				pstmt.executeUpdate();
				
				connection.commit();
				
			} catch (SQLException e) {
				try {
					connection.rollback();
				} catch (SQLException e1) {
					throw new CouponException("DB ERROR! Purchase Coupon is Failed. RollBack Failed!");
				}
				throw new CouponException("DB ERROR! Purchase Coupon is Failed.");
			} catch (Exception e) {
				throw new CouponException("APP ERROR! Purchase Coupon is Failed.");
			} finally {
				pool.returnConnection(connection);
			}
		}

		@Override
		public List<Coupon> getAllCouponsByType(long couponId, couponType coupType) throws CouponException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<Coupon> getAllPurchasedCouponsByPrice(long custId, long price) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<Coupon> getAllPurchasedCoupons(long custId) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<Coupon> getAllPurchasedCouponsByType(long custId, couponType type) {
			// TODO Auto-generated method stub
			return null;
		}
			
		}
		}
		
		
	


	