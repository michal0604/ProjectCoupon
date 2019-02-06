package projectCoupon.Coupons;

	import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;
import java.util.TreeSet;

import projectCoupon.Database;

	public class CouponDBDAO implements CouponDAO {
		Connection con;

		@Override
		public void insertCoupon(Coupon coupon) throws Exception {
			con = DriverManager.getConnection(Database.getDBUrl());
			
			String sql = "INSERT INTO Coupon(TITLE,START_DATE,END_DATE,AMOUNT,TYPE,MESSAGE,PRICE,IMAGE) VALUES(?,?,?,?,?,?,?,?)";
			       

			try (PreparedStatement pstmt = con.prepareStatement(sql)) {
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
				con.close();
			}
		}
			

		@Override
		public void removeCoupon(Coupon Coupon) throws Exception {
			con = DriverManager.getConnection(Database.getDBUrl());
			String sql = "DELETE FROM Coupon WHERE id=?";

			//what is the different statement and preparedStatement
			try (PreparedStatement pstm1 = con.prepareStatement(sql);) {
				con.setAutoCommit(false);
		
				pstm1.setLong(1, Coupon.getId());
				pstm1.executeUpdate();
				con.commit();
	
			} catch (SQLException e) {
				try {
					con.rollback();
			
				} catch (SQLException e1) {
					throw new Exception("Database error");
				}
				throw new Exception("failed to remove Coupon");
			} finally {
				con.close();
			}
		}
			
		

		@Override
		public void updateCoupon(Coupon Coupon) throws Exception {
			con = DriverManager.getConnection(Database.getDBUrl());
			try (Statement stm = con.createStatement()) {
				String sql = "UPDATE Coupon SET TITLE=?, START_DATE=?, END_DATE=?, AMOUNT=?,"
						+ " TYPE=?, MESSAGE=?, PRICE=?, IMAGE=? WHERE ID=?";
				PreparedStatement stm1= con.prepareStatement (sql);
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
				
				stm1.executeUpdate(sql);
				System.out.println("update success");
			} catch (SQLException e) {
				throw new Exception("update Coupon failed");
			}con.close();
		}
			
		@Override
		public Coupon getCoupon(long id) throws Exception {
			con = DriverManager.getConnection(Database.getDBUrl());
			Coupon Coupon = new Coupon();
			try (Statement stm = con.createStatement()) {
				String sql = "SELECT * FROM Coupon WHERE ID=" + id;
				ResultSet rs = stm.executeQuery(sql);
				rs.next();
				Coupon.setId(rs.getLong(1));
				Coupon.setTitle(rs.getString(2));
				Coupon.setStart_date(rs.getDate(3));
				Coupon.setEnd_date(rs.getDate(4));
				Coupon.setAmount(rs.getInt(5));
				Coupon.setMessage(rs.getString(6));
				Coupon.setPrice(rs.getDouble(7));
				Coupon.setImage(rs.getString(8));
				couponType type = null ;
				switch (type.getClass().getName()) {
				case "food":
					type=couponType.food;
					break;
				case "Resturans":
					type=couponType.Resturans;
					break;
				case "Electricity":
					type=couponType.Electricity;
					break;
				case "Health":
					type=couponType.Health;
					break;
				case "Sports":
					type=couponType.Sports;
					break;
				case "Camping":
					type=couponType.Camping;
					break;
				case "Traveling":
					type=couponType.Traveling;
					break;
				default:
					System.out.println("Coupon not existent");
					break;
				}
			}
				
			catch (SQLException e) {
				throw new Exception("unable to get Coupon data");
			} finally {
				con.close();
			}
			return Coupon;
		}
		

		@Override
		public Set<Coupon> getAllCoupon() throws Exception {
			con = DriverManager.getConnection(Database.getDBUrl());
			Set<Coupon> set = new TreeSet<>();
			String sql = "SELECT ID FROM Coupon";
			try (Statement stm = con.createStatement(); ResultSet rs = stm.executeQuery(sql)) {
				while (rs.next()) {
					long ID = rs.getLong(1);
					String TITLE = rs.getString(1);
					//insert date start end..
					

					set.add(new Coupon());
				}
			} catch (SQLException e) {
				System.out.println(e);
				throw new Exception("cannot get Coupon data");
			} finally {
				con.close();
			}
			return set;
		}

		@Override
		public Coupon dropTable() throws Exception {
			Connection connection=null;
			try {
				// Create a connection:
				con = DriverManager.getConnection(Database.getDBUrl());

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
			con = DriverManager.getConnection(Database.getDBUrl());
			String sql = "DELETE FROM Coupon WHERE id=?";

			//what is the different statement and preparedStatement
			try (PreparedStatement pstm1 = con.prepareStatement(sql);) {
				con.setAutoCommit(false);
		
				pstm1.setLong(1, id);
				pstm1.executeUpdate();
				con.commit();
	
			} catch (SQLException e) {
				try {
					con.rollback();
			
				} catch (SQLException e1) {
					throw new Exception("Database error");
				}
				throw new Exception("failed to remove Coupon");
			} finally {
				con.close();
			}
			
		}
		}

	