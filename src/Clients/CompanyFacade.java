package Clients;

import java.sql.Date;
import java.util.Set;

import Clients.Client.Clients;
import Exception.CouponException;
import projectCoupon.Company.Company;
import projectCoupon.Company.CompanyDAO;
import projectCoupon.Company.CompanyDBDAO;
import projectCoupon.Coupons.Coupon;
import projectCoupon.Coupons.CouponDAO;
import projectCoupon.Coupons.CouponDBDAO;
import projectCoupon.Coupons.Utile;
import projectCoupon.Coupons.couponType;

public class CompanyFacade extends Clients implements CouponClientFacade {

	private CompanyDAO companyDAO;
	private CouponDAO couponDAO ;
	private Utile Utils;
	private long companyId = 0;
	private Company company;
	
	/**
	 * Constructor
	 * @throws CouponException 
	 */
	public CompanyFacade() throws CouponException  {
		companyDAO = new CompanyDBDAO();
		couponDAO = new CouponDBDAO();
		Utils = new Utile();
	}

	@Override
	public CouponClientFacade login(String name, String Password) throws CouponException  {
		Company company = new Company();
		company = companyDAO.login(name, Password);
		if (company != null) {
			// initiate companyId to remember facade.
			this.companyId = company.getId();
			this.company = company;
			return this;
		} else {
			return null;
		}
	}
	

	public void createCoupon(Coupon coupon) throws Exception {
		if (coupon != null) {
			String CoupTitle = coupon.getTitle();
			if (CoupTitle != null) {
				Date startDate = coupon.getstartDate();
				Date endDate = coupon.getendDate();
				if (startDate.getTime() <= endDate.getTime()) {
					if (startDate.getTime() >= Utile.toDate(0).getTime()) {//ts.getTime()) { //new Timestamp(System.currentTimeMillis()).getTime()) {
						if (!couponDAO.isCouponTitleExists(CoupTitle)) {
							couponDAO.insertCoupon(coupon);
						} else {
							throw new CouponException("STOP! Coupon Title is Already Exists! Create New Coupon is Canceled!"); 
						}
					} else {
						throw new CouponException("STOP! Coupon Start Date Cannot Be In The Past! Create New Coupon is Canceled!"); 
					}
				} else {
					throw new CouponException("STOP! Coupon Start Date Cannot Be Greater then End Date! Create New Coupon is Canceled!"); 
				}
			} else {
				throw new CouponException("STOP! No Coupons Information! Create New Coupon is Canceled!"); 
			}
		} else {
			throw new CouponException("STOP! Coupon Information Not Exist! Create New Coupon is Failed!"); 
		}
	}
	
	/**
	 * Delete all coupons from related to company id include those 
	 * how bought by customers (customerCoupon).
	 * @param coupId long 
	 * @throws Exception 
	 */
	public void removeCoupon(long coupId) throws Exception {
		if (coupId > 0) {
			if (couponDAO.isCouponExistsForCompany(companyId, coupId)) {
				couponDAO.removeCoupon(coupId);
			} else {
				throw new CouponException("STOP! Coupon Not Exist for Company! Remove Coupon is Canceled!");
			}
		} else {
			throw new CouponException("STOP! No Coupon Was Chosen! Remove Coupon is Canceled!");
		}
	}

	/**
	 * Update Coupon - only end date and price.
	 * @param coupon Coupon 
	 * @throws Exception 
	 */
	public void updateCoupon(Coupon coupon) throws Exception {
		if (coupon != null) {
			long couponId = coupon.getId();
			if (couponDAO.isCouponExistsForCompany(companyId, couponId)) {
				Double CoupPrice = coupon.getPrice();
				if (CoupPrice > 0) {
					Date startDate = couponDAO.getCoupon(couponId).getstartDate();
					Date endDate = coupon.getendDate();
					if (startDate.getTime() <= endDate.getTime()) {
						//if (startDate.getTime() >= new Timestamp(System.currentTimeMillis()).getTime()) {
							couponDAO.updateCoupon(coupon);
						//} else {
						//	throw new CouponException("STOP! Coupon Start Date Cannot Be In The Past! Update Coupon is Canceled!"); 
						//}
					} else {
						throw new CouponException("STOP! Coupon Start Date Cannot Be Greater then End Date! Update Coupon is Canceled!"); 
					}
				} else {
					throw new CouponException("STOP! Invalid Price For Coupon! Update Coupon is Canceled!"); 
				}
			} else {
				throw new CouponException("STOP! Coupon Not Exist for Company! Update Coupon is Canceled!");
			}
		} else {
			throw new CouponException("STOP! Coupon Information Not Exist! Update Coupon is Failed!"); 
		}
	}
	
	/**
	 * View specific company by id
	 * @return Company
	 * @throws Exception 
	 */
	public Company getCompany() throws Exception {
		return companyDAO.getCompany(companyId);
	}
	
	/**
	 * View specific coupon
	 * @param coupId long 
	 * @return Coupon
	 * @throws Exception 
	 */
	public Coupon getCoupon(long coupId) throws Exception {
		return couponDAO.getCoupon(coupId);
	}	
	
	/**
	 * Get all company coupons.
	 * @return Coupons collection.
	 * @throws CouponException
	 * false - not expired coupons.
	 */
	public Set<Coupon> getCoupons() throws CouponException {
		return couponDAO.getCoupons(companyId,0,0,false);
	}

	/**
	 * View all coupons related to specific company by type.
	 * @param coupType CouponType 
	 * @return Coupons collection.
	 * @throws CouponException
	 */
	public Set<Coupon> getCouponsByType(couponType coupType) throws CouponException {
		return couponDAO.getCouponsByType(companyId, coupType);
	}

	/**
	 * View all coupons related to specific company By Max Coupon Price
	 * @param price double 
	 * @return Coupons collection.
	 * @throws CouponException
	 */
	public Set<Coupon> getCouponsByMaxCouponPrice(double price) throws CouponException {
		return couponDAO.getCouponsByMaxCouponPrice(companyId,price);
	}

	/**
	 * View all coupons related to specific company by Max Coupon Date
	 * @param maxCouponDate Timestamp 
	 * @return Coupons collection.
	 * @throws CouponException
	 */
	public Set<Coupon> getCouponsByMaxCouponDate(Date maxCouponDate)  throws CouponException{
		return couponDAO.getCouponsByMaxCouponDate(companyId, maxCouponDate);		
	}
	
	/**
	 * @return the companyId
	 * Set in login()
	 */
	public long getCompanyId() {
		return companyId;
	}
	
	/**
	 * @return company
	 * Set in login()
	 */
	public Company getCompanyInstance() {
		return company;
	}

}


