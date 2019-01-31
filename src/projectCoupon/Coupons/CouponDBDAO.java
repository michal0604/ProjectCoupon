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
		public void insertCoupon(Coupon Coupon) throws Exception {
			con = DriverManager.getConnection(Database.getDBUrl());
			createTables(con);
			String sql = "INSERT INTO Coupon (Title,Start_date,End_Date,amount,type()) VALUES(?,?,?,?,?)";

			try (PreparedStatement pstmt = con.prepareStatement(sql)) {

				pstmt.setString(1, Coupon.getTitle());
				pstmt.setDate(2, Coupon.getStart_date());
				pstmt.setDate(2, Coupon.getEnd_Date());
				pstmt.setInt(4, Coupon.getAmount());
				pstmt.setString(5, Coupon.getMessage());
				pstmt.setDouble(6, Coupon.getPrice());
				pstmt.setString(7, Coupon.getImage());
				pstmt.executeUpdate();

				// why 1 2 3

				System.out.println("Coupon created" + Coupon.toString());
			} catch (SQLException ex) {
				System.out.println(ex.getLocalizedMessage());
				throw new Exception("Coupon creation failed");
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
		
				pstm1.setLong(1, Coupon.getID());
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
				String sql = "UPDATE Coupon " + " SET name='" + Coupon.getTitle() + "', Start Date='" + Coupon.getStart_date()+"',End Date='"+Coupon.getEnd_Date()+"',Amount='"+Coupon.getAmount()+"',Message='"+Coupon.getMessage()+"',Price='"+Coupon.getPrice()+"',Image='"+Coupon.getImage()
						+ "' WHERE ID=" + Coupon.getID();
				stm.executeUpdate(sql);
			} catch (SQLException e) {
				throw new Exception("update Coupon failed");
			}con.close();
		}
			
		@Override
		public Coupon getPCoupon(long id) throws Exception {
			con = DriverManager.getConnection(Database.getDBUrl());
			Coupon Coupon = new Coupon();
			try (Statement stm = con.createStatement()) {
				String sql = "SELECT * FROM Coupon WHERE ID=" + id;
				ResultSet rs = stm.executeQuery(sql);
				rs.next();
				Coupon.setID(rs.getLong(1));
				Coupon.setTitle(rs.getString(2));
				Coupon.setStart_date(rs.getDate(3));
				Coupon.setEnd_Date(rs.getDate(4));
				Coupon.setAmount(rs.getInt(5));
				Coupon.setMessage(rs.getString(6));
				Coupon.setPrice(rs.getDouble(7));
				Coupon.setImage(rs.getString(8));
				
				
			} catch (SQLException e) {
				throw new Exception("unable to get Coupon data");
			} finally {
				con.close();
			}
			return Coupon;
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
					

					set.add(new Company());
				}
			} catch (SQLException e) {
				System.out.println(e);
				throw new Exception("cannot get Company data");
			} finally {
				con.close();
			}
			return set;
		}

		@Override
		public Coupon dropTable() throws Exception {
			// TODO Auto-generated method stub
			return null;
		}

	}