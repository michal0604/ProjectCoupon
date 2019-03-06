package projectCoupon.Clients;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import projectCoupon.Company.Company;
import projectCoupon.Company.CompanyDAO;
import projectCoupon.Company.CompanyDBDAO;
import projectCoupon.Company_Coupon.Company_CouponDAO;
import projectCoupon.Company_Coupon.Company_CouponDBDAO;
import projectCoupon.Coupons.Coupon;
import projectCoupon.Coupons.CouponDAO;
import projectCoupon.Coupons.CouponDBDAO;
import projectCoupon.Coupons.Utile;
import projectCoupon.Coupons.couponType;
import projectCoupon.Exception.CompanyException;
import projectCoupon.Exception.CouponException;
import projectCoupon.Exception.CreateException;

public class CompanyFacade  implements CouponClientFacade {

	private CompanyDAO companyDAO;
	private CouponDAO couponDAO ;
	private Company_CouponDAO company_CouponDAO;
	private long companyId = 0;
	private Company company;
	

	/**
	 * cTor for company handling system
	 * 
	 * @throws CouponException for errors in creation of the resources needed for the system
	 */
	public CompanyFacade() throws CouponException{
		companyDAO = new CompanyDBDAO();
		couponDAO = new CouponDBDAO();
		company_CouponDAO = new Company_CouponDBDAO();
	}

	
	/**
	 * this method returns a company iff the user password is correct.
	 * 
	 * @param name  name for login
	 * @param password  password for login
	 * @throws CouponException for problem retrieving the company data.
	 * @throws SQLException for DB related failures
	 * @throws CompanyException 
	 * @throws ConnectionException 
	 *
	 * @see projectCoupon.Clients.CouponClientFacade#login(java.lang.String, java.lang.String)
	 */
	@Override
	public CouponClientFacade login(String name, String password) throws CouponException, SQLException, CompanyException{
		Company company = new Company();
		company = companyDAO.login(name, password);
		if (company != null) {
			// initiate companyId to remember facade.
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
	 * @param coupon the coupon to be added.
	 * @throws ConnectionException errors due to connection problems
	 * @throws SQLException  errors due to SQL problems
	 * @throws CreateException errors in creation
	 * @throws CouponException 
	 */
	public void createCoupon(Coupon coupon) throws CreateException, SQLException, CouponException {
		if (coupon != null) {
			String CoupTitle = coupon.getTitle();
			if (CoupTitle != null) {
				Date startDate = (Date) coupon.getStart_date();
				Date endDate = (Date) coupon.getEnd_date();
				if (startDate.getTime() <= endDate.getTime()) {
					if (startDate.getTime() >= Utile.toDate(0).getTime()) {
						if (!couponDAO.isCouponTitleExists(CoupTitle)) {
							couponDAO.insertCoupon(coupon);
						} else {
							throw new CouponException("Coupon Title is Already Exists! Create New Coupon is Canceled!"); 
						}
					} else {
						throw new CouponException("Coupon Start Date Cannot Be In The Past! Create New Coupon is Canceled!"); 
					}
				} else {
					throw new CouponException("Coupon Start Date Cannot Be Greater then End Date! Create New Coupon is Canceled!"); 
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
	 * @throws Exception
	 */
	public void removeCoupon(long coupId) throws Exception {
		if (coupId > 0) {
			if (company_CouponDAO.isCouponExistsForCompany(companyId, coupId)) {
				company_CouponDAO.removeCompany_Coupon(companyId, coupId);
				couponDAO.removeCoupon(coupId);
			} else {
				throw new CouponException("Coupon Not Exist for Company! Remove Coupon is Canceled!");
			}
		} else {
			throw new CouponException("No Coupon Was Chosen! Remove Coupon is Canceled!");
		}
	}

	
	/**
	 * @param coupon
	 * @throws Exception
	 */
	public void updateCoupon(Coupon coupon) throws Exception {
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
						throw new CouponException("Coupon Start Date Cannot Be Greater then End Date! Update Coupon is Canceled!"); 
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
	 * @throws Exception
	 */
	public Coupon getCoupon(long coupId) throws Exception {
		return couponDAO.getCoupon(coupId);
	}	
	
	
	/**
	 * @return
	 * @throws Exception
	 */
	public List<Coupon> getCoupons() throws Exception {
		List<Coupon>allCoupons=new ArrayList<Coupon>();
		allCoupons=couponDAO.getAllCoupons();
		return allCoupons;
	}

	
	/**
	 * @param coupType
	 * @return
	 * @throws Exception
	 */
	public List<Coupon> getCouponsByType(couponType coupType) throws Exception {
		List<Coupon> coupons = new ArrayList<Coupon>();
		Coupon coupon;
		List<Long> companyCouponList = company_CouponDAO.getCouponsByCompanyId(companyId);
		for (Long couponId : companyCouponList) {
			coupon = couponDAO.getCoupon(couponId);
			if (coupon.getType().equals(coupType) ) {
				coupons.add(coupon);
			}
		}
		return coupons;
	}
	

	
	/**
	 * @param price
	 * @return
	 * @throws Exception
	 */
	public List<Coupon> getCouponsByMaxCouponPrice(double price) throws Exception {
		List<Coupon> coupons = new ArrayList<Coupon>();
		Coupon coupon;
		List<Long> companyCouponList = company_CouponDAO.getCouponsByCompanyId(companyId);
		for (Long couponId : companyCouponList) {
			coupon = couponDAO.getCoupon(couponId);
			if (coupon.getPrice() <= price ) {
				coupons.add(coupon);
			}
		}
		return coupons;
	}


	
	/**
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	public List<Coupon> getCouponsByMaxCouponDate(Date endDate)  throws Exception{
		List<Coupon> coupons = new ArrayList<Coupon>();
		Coupon coupon;
		List<Long> companyCouponList = company_CouponDAO.getCouponsByCompanyId(companyId);
		for (Long couponId : companyCouponList) {
			coupon = couponDAO.getCoupon(couponId);
			if (coupon.getEnd_date().equals(endDate) || coupon.getEnd_date().before(endDate) ) {
				coupons.add(coupon);
			}
		}
		return coupons;
	}		
	
	
	
	/**
	 * @return
	 */
	public long getCompanyId() {
		return companyId;
	}
	
	
	/**
	 * @return
	 */
	public Company getCompanyInstance() {
		return company;
	}


	}




