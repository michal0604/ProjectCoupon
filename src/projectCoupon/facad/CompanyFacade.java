package projectCoupon.facad;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import projectCoupon.beans.Company;
import projectCoupon.beans.Coupon;
import projectCoupon.beans.CouponType;
import projectCoupon.dao.CompanyDAO;
import projectCoupon.dao.Company_CouponDAO;
import projectCoupon.dao.CouponDAO;
import projectCoupon.dbdao.CompanyDBDAO;
import projectCoupon.dbdao.Company_CouponDBDAO;
import projectCoupon.dbdao.CouponDBDAO;
import projectCoupon.exception.CompanyException;
import projectCoupon.exception.CouponException;
import projectCoupon.exception.CreateException;
import projectCoupon.exception.RemoveException;
import projectCoupon.exception.UpdateException;
import projectCoupon.utils.ConnectionPool;
import projectCoupon.utils.Utile;

public class CompanyFacade implements CouponClientFacade {

	private CompanyDAO companyDAO;
	private CouponDAO couponDAO;
	private Company_CouponDAO company_CouponDAO;
	//private long companyId = 0;
	private long companyId;
	private Company company;

	
	/**
	 * cTor for company handling system
	 * 
	 * @throws CouponException
	 *             for errors in creation of the resources needed for the system
	 */
	public CompanyFacade() throws CouponException {
		companyDAO = new CompanyDBDAO();
		couponDAO = new CouponDBDAO();
		company_CouponDAO = new Company_CouponDBDAO();
	}

	/**
	 * this method returns a company iff the user password is correct.
	 * 
	 * @param name
	 *            name for login
	 * @param password
	 *            password for login
	 * @throws CouponException
	 *             for problem retrieving the company data.
	 * @throws SQLException
	 *             for DB related failures
	 * @throws CompanyException
	 * @throws ConnectionException
	 *
	 */

	@Override
	public CouponClientFacade login(String name, String password) throws CouponException, SQLException, CompanyException{
		Company company = new Company();
		company = new CompanyDBDAO().login(name, password);
		if (company != null) {
			this.companyId = company.getCompanyId();
			this.company = company;
			return this;
		} else {
			return null;
		}	
	}

	/**
	 * this method adds a coupon to the system
	 * 
	 * @param coupon
	 *            the coupon to be added.
	 * @throws ConnectionException
	 *             errors due to connection problems
	 * @throws SQLException
	 *             errors due to SQL problems
	 * @throws CreateException
	 *             errors in creation
	 * @throws CouponException
	 */
	public void createCoupon(Coupon coupon) throws CreateException, SQLException, CouponException {
		if(companyId == 0) {
			throw new CouponException("the operation was canceled due to not being loged in");
		}
		if (coupon != null) {
			String CoupTitle = coupon.getTitle();
			if (CoupTitle != null) {
				Date startDate = (Date) coupon.getStart_date();
				Date endDate = (Date) coupon.getEnd_date();
				if (startDate.getTime() <= endDate.getTime()) {
					if (startDate.getTime() >= Utile.getCurrentDate().getTime()) {
						if (!couponDAO.isCouponTitleExists(CoupTitle)) {
							couponDAO.insertCoupon(coupon);
							company_CouponDAO.insertCompany_Coupon(companyId, coupon.getCouponId());
						} else {
							throw new CouponException("Coupon Title is Already Exists! Create New Coupon is Canceled!");
						}
					} else {
						throw new CouponException(
								"Coupon Start Date Cannot Be In The Past! Create New Coupon is Canceled!");
					}
				} else {
					throw new CouponException(
							"Coupon Start Date Cannot Be Greater then End Date! Create New Coupon is Canceled!");
				}
			} else {
				throw new CouponException(" No Coupons Information! Create New Coupon is Canceled!");
			}
		} else {
			throw new CouponException("Coupon Information Not Exist! Create New Coupon is Failed!");
		}
	}

	/**
	 * @param coupId
	 * @throws CouponException 
	 * @throws RemoveException 
	 * @throws Exception
	 */
	public void removeCouponID(long coupId) throws CouponException, RemoveException{
		if(companyId == 0) {
			throw new CouponException("the operation was canceled due to not being loged in");
		}
		if (coupId > 0) {
			if (company_CouponDAO.isCouponExistsForCompany(companyId, coupId)) {
				company_CouponDAO.removeCompany_Coupon(companyId, coupId);
				couponDAO.removeCouponID(coupId);
			} else {
				throw new CouponException("Coupon Not Exist for Company! Remove Coupon is Canceled!");
			}
		} else {
			throw new CouponException("No Coupon Was Chosen! Remove Coupon is Canceled!");
		}
	}

	/**
	 * @param coupon
	 * @throws CouponException 
	 * @throws CreateException 
	 * @throws UpdateException 
	 * @throws Exception
	 */
	public void updateCoupon(Coupon coupon) throws CouponException, CreateException, UpdateException{
		if(companyId == 0) {
			throw new CouponException("the operation was canceled due to not being loged in");
		}
		if (coupon != null) {
			long couponId = coupon.getCouponId();
			if (company_CouponDAO.isCouponExistsForCompany(companyId, couponId)) {
				Double CoupPrice = coupon.getPrice();
				if (CoupPrice > 0) {
					Date startDate = (Date) couponDAO.getCoupon(couponId).getStart_date();
					Date endDate = (Date) coupon.getEnd_date();
					if (startDate.getTime() <= endDate.getTime()) {
						couponDAO.updateCoupon(coupon);

					} else {
						throw new CouponException(
								"Coupon Start Date Cannot Be Greater then End Date! Update Coupon is Canceled!");
					}
				} else {
					throw new CouponException("Invalid Price For Coupon! Update Coupon is Canceled!");
				}
			} else {
				throw new CouponException("Coupon Not Exist for Company! Update Coupon is Canceled!");
			}
		} else {
			throw new CouponException("Coupon Information Not Exist! Update Coupon is Failed!");
		}
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public Company getCompany() throws Exception {
		return companyDAO.getCompany(companyId);
	}

	/**
	 * @param coupId
	 * @return
	 * @throws CouponException 
	 * @throws CreateException 
	 * @throws Exception
	 */
	public Coupon getCoupon(long coupId) throws CouponException, CreateException {
		if(companyId == 0) {
			throw new CouponException("the operation was canceled due to not being loged in");
		}
		return couponDAO.getCoupon(coupId);
	}

	/**
	 * @return
	 * @throws CouponException 
	 * @throws Exception
	 */
	public List<Coupon> getCoupons() throws CouponException {
		if(companyId == 0) {
			throw new CouponException("the operation was canceled due to not being loged in");
		}
		List<Coupon> allCoupons = new ArrayList<Coupon>();
		allCoupons = couponDAO.getAllCoupons();
		return allCoupons;
	}

	/**
	 * @param coupType
	 * @return
	 * @throws CouponException 
	 * @throws CompanyException 
	 * @throws CreateException 
	 * @throws Exception
	 */
	
	/*delete????
	public List<Coupon> getCouponsByType(couponType coupType) throws CouponException, CreateException {
		if(companyId == 0) {
			throw new CouponException("the operation was canceled due to not being loged in");
		}
		List<Coupon> coupons = new ArrayList<Coupon>();
		Coupon coupon;
		List<Long> companyCouponList = company_CouponDAO.getCouponsByCompanyId(companyId);
		for (Long couponId : companyCouponList) {
			coupon = couponDAO.getCoupon(couponId);
			if (coupon.getType().equals(coupType)) {
				coupons.add(coupon);
			}
		}
		return coupons;
	}
	*/
	
	public List<Coupon> getAllCouponsByType(CouponType coupType) throws CouponException, CompanyException{
		if(companyId == 0) {
			throw new CouponException("the operation was canceled due to not being loged in");
		}
		List<Coupon> list = new ArrayList<Coupon>();
		List<Coupon> allCouponsList = companyDAO.getAllCoupons(companyId);
		for (Coupon coupon : allCouponsList) {
			if (coupon.getType().equals(coupType)) {
				list.add(coupon);
			}
		}
		return list;
	}
	
	/**
	 * @param price
	 * @return
	 * @throws CouponException 
	 * @throws CreateException 
	 * @throws CompanyException 
	 * @throws Exception
	 */
	
	public List<Coupon> getCouponsByMaxCouponPrice(double price) throws CouponException, CreateException, CompanyException{
		if(companyId == 0) {
			throw new CouponException("the operation was canceled due to not being loged in");
		}
		List<Coupon> list = new ArrayList<Coupon>();
		List<Coupon> allCouponsList = companyDAO.getAllCoupons(companyId);
		for (Coupon coupon : allCouponsList) {
			
			if (coupon.getPrice() <= price) {
				list.add(coupon);
			}
		}
		return list;
	}


	/**
	 * @param endDate
	 * @return
	 * @throws CouponException 
	 * @throws CreateException 
	 * @throws CompanyException 
	 * @throws Exception
	 */
	
	
	public List<Coupon> getCouponsByMaxCouponDate(Date endDate) throws CouponException, CreateException, CompanyException{
		if(companyId == 0) {
			throw new CouponException("the operation was canceled due to not being loged in");
		}
		List<Coupon> list = new ArrayList<Coupon>();
		List<Coupon> allCouponsList = companyDAO.getAllCoupons(companyId);
		for (Coupon coupon : allCouponsList) {
			if (coupon.getEnd_date().equals(endDate) || coupon.getEnd_date().before(endDate)) {
				list.add(coupon);
			}
		}
		return list;
	}
	

	


	/**
	 * @return
	 * @throws CouponException 
	 */
	public long getCompanyId() throws CouponException {
		if(companyId == 0) {
			throw new CouponException("the operation was canceled due to not being loged in");
		}
		return companyId;
	}

	/**
	 * @return
	 * @throws CouponException 
	 */
	public Company getCompanyInstance() throws CouponException {
		if(companyId == 0) {
			throw new CouponException("the operation was canceled due to not being loged in");
		}
		return company;
	}


	

}
