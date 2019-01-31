package projectCoupon.Coupons;

	import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

import projectCoupon.Database;

	public class CouponDBDAO implements CouponDAO {
		Connection con;

		@Override
		public void insertCoupon(Coupon Coupon) throws Exception {
			con = DriverManager.getConnection(Database.getDBUrl());
			//createTables(con);
			String sql = "INSERT INTO Coupon (Title,Start_date,End_Date,amount,type()) VALUES(?,?,?,?,?)";

			try (PreparedStatement pstmt = con.prepareStatement(sql)) {

				pstmt.setString(1, Coupon.getTitle());
				pstmt.setDate(2, Coupon.getStart_date());
				pstmt.setDate(2, Coupon.getEnd_Date());
				pstmt.setInt(4, Coupon.getAmount());
				pstmt.setString(5, Coupon.get
				pstmt.setInt(4, Coupon.getAmount());
				pstmt.setInt(4, Coupon.getAmount());
				pstmt.setInt(4, Coupon.getAmount());
				pstmt.executeUpdate();

				// why 1 2 3

				System.out.println("Company created" + Company.toString());
			} catch (SQLException ex) {
				System.out.println(ex.getLocalizedMessage());
				throw new Exception("Company creation failed");
			} finally {
				con.close();
			}
		}
			
		}

		@Override
		public void removeCoupon(Coupon Coupon) throws Exception {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void updateCoupon(Coupon Coupon) throws Exception {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Coupon getPCoupon(long id) throws Exception {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Set<Coupon> getAllCoupon() throws Exception {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Coupon dropTable() throws Exception {
			// TODO Auto-generated method stub
			return null;
		}

	}