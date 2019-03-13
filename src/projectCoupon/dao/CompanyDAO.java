package projectCoupon.dao;

import java.sql.SQLException;
import java.util.List;

import projectCoupon.beans.Company;
import projectCoupon.exception.CompanyException;
import projectCoupon.exception.CouponException;

/**
 * this interface lists the data access object operations Company's requirements.
 * 
 *  @author Eivy & Michal
 *
 */
public interface CompanyDAO {
	
	/**
	 * Inserts a company data set to the Database
	 * 
	 * @param company company to be inserted
	 * @throws CouponException for problems in inserting the company to the DB 
	 * @throws SQLException for DB related failures 
	 * @throws ConnectionException for connection problems
	 */
	void insertCompany(Company company) throws CouponException, SQLException;

	/**
	 * remove a company  from the Database
	 * 
	 * @param company company to be removed
	 * @throws CouponException  
	 * @throws CompanyRemovalException for problems regarding the removal of company from DB
	 * @throws SQLException SQLException for DB related failures
	 */
	void removeCompany(Company company) throws CouponException, SQLException;

	/**
	 * updates a company into the Database
	 * 
	 * @param company company to update
	 * @throws CouponException regarding the connection problem
	 * @throws CompanyUpdateException or problems in updating the company to the DB 
	 * @throws SQLException for DB related failures 
	 * @throws CompanyException 
	 */
	void updateCompany(Company company) throws   CompanyException;

	/**
	 * get a company data set by the company's id.
	 * 
	 * @param id representing the id of the required company
	 * @throws CouponException  for errors happing due to trying to get a company from DB
	 * @throws SQLException for DB related failures
	 */
	Company getCompany(long companyId) throws CouponException, SQLException;

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
	 * @param compName name that should be checked for existing
	 * @throws CouponException for error related to the retrieval of the company 
	 * @throws SQLException for DB related failures
	 * @throws ConnectionException error occurring due to connection problems
	 */
	public boolean isCompanyNameExists(String compName) throws CouponException, SQLException;

	/**
	 * this method returns a company iff the user password is correct.
	 * 
	 * @param name company's name of the logged in company
	 * @param password company's password of the logged in company
	 * @throws CouponException for problem retrieving the company data.
	 * @throws SQLException for DB related failures
	 * @throws CompanyException 
	 * @throws ConnectionException error occurring due to connection problems
	 */
	Company login(String compName, String password) throws CouponException, SQLException, CompanyException;

	/**
	 * remove a company identified by its id from the Database
	 * 
	 * @param compId the id of the company that should be deleted 
	 * @throws CouponException  
	 * @throws CompanyRemovalException for problems regarding the removal of company from DB
	 * @throws SQLException SQLException for DB related failures
	 */
	

}