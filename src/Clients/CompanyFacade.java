package Clients;

import java.sql.Date;
import java.util.Set;
import projectCoupon.Clients.Client;
import projectCoupon.Clients.CouponClientFacade;
import projectCoupon.Clients.clientType;
import projectCoupon.Company.Company;
import projectCoupon.Company.CompanyDAO;
import projectCoupon.Company.CompanyDBDAO;
import projectCoupon.Coupons.Coupon;
import projectCoupon.Coupons.CouponDAO;
import projectCoupon.Coupons.CouponDBDAO;
import projectCoupon.Coupons.Utile;
import projectCoupon.Coupons.couponType;

public class CompanyFacade extends Client implements CouponClientFacade{

	private CompanyDAO companyDAO=new CompanyDBDAO();
	private CouponDAO couponDAO=new CouponDBDAO() ;
	private Utile Utils=new Utile();
	private long companyId = 0;
	private Company company;
	

	public CompanyFacade()  {
	}

	public void createCoupon(Coupon coupon) throws Exception {
		if (coupon != null) {
			String CoupTitle = coupon.getTitle();
			if (CoupTitle != null) {
				Date startDate = coupon.getStart_date();
				Date endDate = coupon.getEnd_date();
				if (startDate.getTime() <= endDate.getTime()) {
					if (startDate.getTime() >= Utile.toDate(0).getTime()) {//ts.getTime()) { //new Timestamp(System.currentTimeMillis()).getTime()) {
						if (!couponDAO.isCouponTitleExists(CoupTitle)) {
							couponDAO.insertCoupon(coupon);
						} else {
							throw new projectCoupon.Exception.CouponException("STOP! Coupon Title is Already Exists! Create New Coupon is Canceled!"); 
						}
					} else {
						throw new projectCoupon.Exception.CouponException("STOP! Coupon Start Date Cannot Be In The Past! Create New Coupon is Canceled!"); 
					}
				} else {
					throw new projectCoupon.Exception.CouponException("STOP! Coupon Start Date Cannot Be Greater then End Date! Create New Coupon is Canceled!"); 
				}
			} else {
				throw new projectCoupon.Exception.CouponException("STOP! No Coupons Information! Create New Coupon is Canceled!"); 
			}
		} else {
			throw new projectCoupon.Exception.CouponException("STOP! Coupon Information Not Exist! Create New Coupon is Failed!"); 
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
				throw new projectCoupon.Exception.CouponException("STOP! Coupon Not Exist for Company! Remove Coupon is Canceled!");
			}
		} else {
			throw new projectCoupon.Exception.CouponException("STOP! No Coupon Was Chosen! Remove Coupon is Canceled!");
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
					Date startDate = couponDAO.getCoupon(couponId).getStart_date();
					Date endDate = coupon.getEnd_date();
					if (startDate.getTime() <= endDate.getTime()) {
					
							couponDAO.updateCoupon(coupon);
	
					} else {
						throw new projectCoupon.Exception.CouponException("update coupon is Canceled!"); 
					}
				} else {
					throw new projectCoupon.Exception.CouponException("Update Coupon is Canceled!"); 
				}
			} else {
				throw new projectCoupon.Exception.CouponException("Update Coupon is Canceled!");
			}
		} else {
			throw new projectCoupon.Exception.CouponException("Update Coupon is Failed!"); 
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
	
	public Coupon getCoupon(long coupId) throws Exception {
		return couponDAO.getCoupon(coupId);
	}	
	
	
	public Set<Coupon> getCoupons() throws projectCoupon.Exception.CouponException {
		return couponDAO.getCoupons(companyId,0,0,false);
	}

	
	public Set<Coupon> getCouponsByType(couponType coupType) throws projectCoupon.Exception.CouponException {
		return couponDAO.getCouponsByType(companyId, coupType);
	}

	public Set<Coupon> getCouponsByMaxCouponPrice(double price) throws projectCoupon.Exception.CouponException {
		return couponDAO.getCouponsByMaxCouponPrice(companyId,price);
	}

	
	public Set<Coupon> getCouponsByMaxCouponDate(Date maxCouponDate)  throws projectCoupon.Exception.CouponException{
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

	@Override
	public CouponClientFacade login(String name, String password, clientType clientType) {
		Company company = new Company();
		company = companyDAO.login(name, password,clientType);
		if (company != null) {
			// initiate companyId to remember facade.
			this.companyId = company.getId();
			this.company = company;
			return this;
		} else {
			return null;
		}
	}

	@Override
	public CouponClientFacade login(String name, String Password) throws projectCoupon.Exception.CouponException {
		// TODO Auto-generated method stub
		return null;
	}

}


