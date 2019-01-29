package projectCoupon.Company;


	import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import projectCoupon.Company.Company;
import projectCoupon.Company.CompanyDAO;

	public class CompanyDBDAO implements CompanyDAO {
		Connection con;

		@Override
		public void insertCompany(Company Company) throws Exception {
			con = DriverManager.getConnection(Database.getDBUrl());
		//.createTables(con);
			String sql = "INSERT INTO Company (COMP_NAME,PASSWORD,EMAIL) VALUES(?,?,?)";
			
			try (PreparedStatement pstmt = con.prepareStatement(sql)) {

				
				pstmt.setString(1, Company.getCOMP_NAME());
				pstmt.setString(2, Company.getPASSWORD());
				pstmt.setString(3, Company.getEMAIL());
				pstmt.executeUpdate();
				
				//why 1 2 3 
				
				System.out.println("Company created" + Company.toString());
			} catch (SQLException ex) {
				System.out.println(ex.getLocalizedMessage());
				throw new Exception("Company creation failed");
			} finally {
				con.close();
			}
		}
			
		

		@Override
		public void removeCompany(Company Company) throws Exception {
			con = DriverManager.getConnection(Database.getDBUrl());
			String sql = "DELETE FROM Company WHERE id=?";

			//what is the different statement and preparedStatement
			try (PreparedStatement pstm1 = con.prepareStatement(sql);) {
				con.setAutoCommit(false);
		//autocommit??????
				pstm1.setLong(1, Company.getID());
				pstm1.executeUpdate();
				con.commit();
			//con.commit????
			} catch (SQLException e) {
				try {
					con.rollback();
			//what is rollback??????
				} catch (SQLException e1) {
					throw new Exception("Database error");
				}
				throw new Exception("failed to remove Company");
			} finally {
				con.close();
			}
		}
			
		

		@Override
		public void updateCompany(Company Company) throws Exception {
			con = DriverManager.getConnection(Database.getDBUrl());
			try (Statement stm = con.createStatement()) {
				String sql = "UPDATE Company " + " SET name='" + Company.getCOMP_NAME() + "', Password='" + Company.getPASSWORD()+"',email='"+Company.getEMAIL()
						+ "' WHERE ID=" + Company.getID();
				stm.executeUpdate(sql);
			} catch (SQLException e) {
				throw new Exception("update Company failed");
			}con.close();
		}
			
		

		@Override
		public Company getPCompany(long id) throws Exception {
			con = DriverManager.getConnection(Database.getDBUrl());
			Company Company = new Company();
			try (Statement stm = con.createStatement()) {
				String sql = "SELECT * FROM Company WHERE ID=" + id;
				ResultSet rs = stm.executeQuery(sql);
				rs.next();
				Company.setID(rs.getLong(1));
				Company.setCOMP_NAME(rs.getString(2));
				Company.setPASSWORD(rs.getString(3));
				Company.setEMAIL(rs.getString(4));

			} catch (SQLException e) {
				throw new Exception("unable to get Company data");
			} finally {
				con.close();
			}
			return Company;
		}

		@Override
		public Set<Company> getAllCompanys() throws Exception {
			con = DriverManager.getConnection(Database.getDBUrl());
			Set<Company> set = new HashSet<>();
			String sql = "SELECT id FROM Company";
			try (Statement stm = con.createStatement(); ResultSet rs = stm.executeQuery(sql)) {
				while (rs.next()) {
					long id = rs.getLong(1);
					String COMP_NAME = rs.getString(1);
					String PASSWORD = rs.getString(1);
					String EMAIL = rs.getString(1);

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
		
		public Company dropTable()throws Exception{
			Connection connection=null;
			try {
				// Create a connection:
				con = DriverManager.getConnection(Database.getDBUrl());

				// Create sql command for delete one record:
				String sql = "drop table ",Company;

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



