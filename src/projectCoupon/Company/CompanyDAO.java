package projectCoupon.Company;

import java.sql.SQLException;
import java.util.List;

import projectCoupon.Exception.CompanyRemovalException;
import projectCoupon.Exception.CompanyUpdateException;
import projectCoupon.Exception.CouponException;

public interface CompanyDAO {
	
	/**
	 * Inserts a company data set to the Database
	 * 
	 * @throws CouponException for problems in inserting the company to the DB 
	 * @throws SQLException for DB related failures 
	 */
	void insertCompany(Company company) throws CouponException, SQLException;

	/**
	 * remove a company  from the Database
	 * 
	 * @throws CouponException  
	 * @throws CompanyRemovalException for problems regarding the removal of company from DB
	 * @throws SQLException SQLException for DB related failures
	 */
	void removeCompany(Company company) throws CouponException, CompanyRemovalException, SQLException;

	/**
	 * updates a company into the Database
	 * 
	 * @throws CouponException regarding the connection problem
	 * @throws CompanyUpdateException or problems in updating the company to the DB 
	 * @throws SQLException for DB related failures 
	 */
	void updateCompany(Company company) throws CouponException, CompanyUpdateException, SQLException;

	/**
	 * get a company data set by the company's id.
	 * 
	 * @throws CouponException  for errors happing due to trying to get a company from DB
	 * @throws SQLException for DB related failures
	 */
	Company getCompany(long id) throws CouponException, SQLException;

	/**
	 * get all the Companies from the Database.
	 * 
	 * @throws CouponException  for errors happing due to trying to get all companies from DB
	 * @throws SQLException for DB related failures
	 */
	List<Company> getAllCompanys() throws CouponException, SQLException;

	/**
	 * returns if a company identified by the name exist in the DB records.
	 * 
	 * @throws CouponException for error related to the retrieval of the company 
	 * @throws SQLException for DB related failures
	 */
	public boolean isCompanyNameExists(String compName) throws CouponException, SQLException;

	/**
	 * this method returns a company iff the user password is correct.
	 * 
	 * @throws CouponException for problem retrieving the company data.
	 * @throws SQLException for DB related failures
	 */
	Company login(String name, String password) throws CouponException, SQLException;

	
	/**
	 * remove a company identified by its id from the Database
	 * 
	 * @throws CouponException  
	 * @throws CompanyRemovalException for problems regarding the removal of company from DB
	 * @throws SQLException SQLException for DB related failures
	 */
	void removeCompany(long compId) throws CouponException, CompanyRemovalException, SQLException;

}
