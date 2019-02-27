package projectCoupon.Company;

import java.sql.SQLException;
import java.util.List;

import projectCoupon.Clients.clientType;
import projectCoupon.Coupons.Coupon;
import projectCoupon.Exception.CouponException;

public interface CompanyDAO {
	/**
	 * @param company - company to be insert to the Data Object
	 * @throws Exception
	 */
	void insertCompany(Company company) throws Exception;

	/**
	 * @param company - company to be removed from the Data Object
	 * @throws Exception
	 */
	void removeCompany(Company company) throws Exception;

	/**
	 * @param company - company to be updated in the Data Object
	 * @throws Exception
	 */

	void updateCompany(Company company) throws Exception;

	/**
	 * @param id of the required company
	 * @return the requested company
	 * @throws Exception when there is a problem connecting the data object
	 */
	Company getCompany(long id) throws Exception;

	/**
	 * @return all the companies stored in the data acces
	 * @throws Exceptionwhen there is a problem connecting the data object
	 */
	List<Company> getAllCompanys() throws Exception;

	/**
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */

	public boolean isCompanyNameExists(String compName) throws CouponException, SQLException;

	// TODO function empty
	Company login(String name, String password) throws CouponException, SQLException;

	void removeCompany(long compId) throws Exception;

	List<Coupon> getCoupons(long compId) throws Exception;

}
