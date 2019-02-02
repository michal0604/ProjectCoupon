package projectCoupon.Coupons;

	import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import projectCoupon.Database;
import projectCoupon.Company.Company;

	public class CouponDBDAO implements CouponDAO {
		Connection con;

		@Override
		public void insertCoupon(Coupon coupon) throws Exception {
			con = DriverManager.getConnection(Database.getDBUrl());
			
			String sql = "INSERT INTO Coupon (title,start_date,end_date,amount,type,message,price,image) VALUES(?,?,?,?,?,?,?,?)";

			try (PreparedStatement pstmt = con.prepareStatement(sql)) {

				pstmt.setString(1, coupon.getTitle());
				pstmt.setString(2, coupon.getStart_date());
				pstmt.setString(3, coupon.getEnd_date());
				pstmt.setInt(4, coupon.getAmount());
				pstmt.setString(5, coupon.getType().name());
				pstmt.setString(6, coupon.getMessage());
				pstmt.setDouble(7, coupon.getPrice());
				pstmt.setString(8, coupon.getImage());
				pstmt.executeUpdate();
				System.out.println("Coupon created" + coupon.toString());
			} catch (SQLException ex) {
				System.out.println(ex.getLocalizedMessage());
				throw new Exception("Coupon creation failed");
			} finally {
				con.close();
			}
		}
			

		@Override
		public void removeCoupon(Coupon coupon) throws Exception {
			con = DriverManager.getConnection(Database.getDBUrl());
			String sql = "DELETE FROM Coupon WHERE id=?";

			//what is the different statement and preparedStatement
			try (PreparedStatement pstm1 = con.prepareStatement(sql);) {
				con.setAutoCommit(false);
		
				pstm1.setLong(1, coupon.getId());
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
		public void updateCoupon(Coupon coupon) throws Exception {
			con = DriverManager.getConnection(Database.getDBUrl());
			try (Statement stm = con.createStatement()) {
				String sql = "UPDATE Coupon " + " SET name='" + coupon.getTitle() + "', Start Date='" + coupon.getStart_date()+"',End Date='"+coupon.getEnd_date()+"',Amount='"+coupon.getAmount()+"',Message='"+coupon.getMessage()+"',Price='"+coupon.getPrice()+"',Image='"+coupon.getImage()
						+ "' WHERE ID=" + coupon.getId();
				stm.executeUpdate(sql);
			} catch (SQLException e) {
				throw new Exception("update Coupon failed");
			}con.close();
		}
			
		@Override
		public Coupon getCoupon(long id) throws Exception {
			con = DriverManager.getConnection(Database.getDBUrl());
			Coupon coupon = new Coupon();
			try (Statement stm = con.createStatement()) {
				String sql = "SELECT * FROM Coupon WHERE ID=" + id;
				ResultSet rs = stm.executeQuery(sql);
				rs.next();
				coupon.setId(rs.getLong(1));
				coupon.setTitle(rs.getString(2));
				coupon.setStart_date(rs.getString(3));
				coupon.setEnd_date(rs.getString(4));
				coupon.setAmount(rs.getInt(5));
				coupon.setMessage(rs.getString(6));
				coupon.setPrice(rs.getDouble(7));
				coupon.setImage(rs.getString(8));
				couponType type = null ;
				switch (type.getClass().getName()) {
				case "food":
					type=couponType.FOOD;
					break;
				case "Resturans":
					type=couponType.HOLIDAY;
					break;
				case "Electricity":
					type=couponType.ELECTRICITY;
					break;
				case "Health":
					type=couponType.HOLIDAY;
					break;
				case "Sports":
					type=couponType.LEISURE;
					break;
				case "Camping":
					type=couponType.ELECTRICITY;
					break;
				case "Traveling":
					type=couponType.LEISURE;
					break;
				default:
					System.out.println("Coupon not existent");
					break;
				}
			} catch (SQLException e) {
				throw new Exception("unable to get Coupon data");
			} finally {
				con.close();
			}
			return coupon;
		}
		

		@Override
		public Set<Coupon> getAllCoupon() throws Exception {
			con = DriverManager.getConnection(Database.getDBUrl());
			Set<Coupon> set = new HashSet<>();
			String sql = "SELECT id FROM Coupon";
			try (Statement stm = con.createStatement(); ResultSet rs = stm.executeQuery(sql)) {
				while (rs.next()) {
					long id = rs.getLong(1);
					String Title = rs.getString(1);
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
		}

	