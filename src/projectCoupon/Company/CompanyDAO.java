package projectCoupon.Company;

import java.sql.SQLException;
import java.util.List;

import projectCoupon.Coupons.Coupon;
import projectCoupon.Exception.CompanyException;
import projectCoupon.Exception.CouponException;
import projectCoupon.Exception.CreateException;
import projectCoupon.Exception.RemoveException;
import projectCoupon.Exception.UpdateException;

public interface CompanyDAO {
	
	/**
	 * Inserts a company data set to the Database
	 * 
	 * @throws CouponException for problems in inserting the company to the DB 
	 * @throws SQLException for DB related failures 
	 */
	void insertCompany(Company company) throws CreateException;

	/**
	 * remove a company  from the Database
	 * 
	 * @throws CouponException  
	 * @throws CompanyRemovalException for problems regarding the removal of company from DB
	 * @throws SQLException SQLException for DB related failures
	 */
	void removeCompany(Company company) throws RemoveException;

	/**
	 * updates a company into the Database
	 * 
	 * @throws CouponException regarding the connection problem
	 * @throws CompanyUpdateException or problems in updating the company to the DB 
	 * @throws SQLException for DB related failures 
	 */
	void updateCompany(Company company) throws UpdateException;

	/**
	 * get a company data set by the company's id.
	 * 
	 * @throws CouponException  for errors happing due to trying to get a company from DB
	 * @throws SQLException for DB related failures
	 */
	Company getCompany(long id) throws CompanyException;

	/**
	 * get all the Companies from the Database.
	 * 
	 * @throws CouponException  for errors happing due to trying to get all companies from DB
	 * @throws SQLException for DB related failures
	 */
	List<Company> getAllCompanys() throws CompanyException;

	/**
	 * this method returns a company iff the user password is correct.
	 * 
	 * @throws CouponException for problem retrieving the company data.
	 * @throws SQLException for DB related failures
	 */
	Company login(String name, String password) throws CompanyException;

	List<Coupon> getCoupons(long custId) throws CouponException;
	

}
