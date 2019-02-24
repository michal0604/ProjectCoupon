package projectCoupon.Clients;

import java.sql.Date;
import java.util.Set;
import projectCoupon.Clients.CouponClientFacade;
import projectCoupon.Company.Company;
import projectCoupon.Company.CompanyDAO;
import projectCoupon.Company.CompanyDBDAO;
import projectCoupon.Coupons.Coupon;
import projectCoupon.Coupons.CouponDAO;
import projectCoupon.Coupons.CouponDBDAO;
import projectCoupon.Coupons.Utile;
import projectCoupon.Coupons.couponType;
import projectCoupon.Exception.CouponException;

public class CompanyFacade  implements CouponClientFacade {

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
	public CouponClientFacade login(String name, String password, clientType clientType) throws Exception {
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
	
	
	public Company getCompany() throws Exception {
		return companyDAO.getCompany(companyId);
	}
	

	public Coupon getCoupon(long coupId) throws Exception {
		return couponDAO.getCoupon(coupId);
	}	
	
	
	public Set<Coupon> getCoupons() throws CouponException {
		return couponDAO.getCoupons(companyId,0,0,false);
	}

	
	public Set<Coupon> getCouponsByType(couponType coupType) throws CouponException {
		return couponDAO.getCouponsByType(companyId, coupType);
	}

	
	public Set<Coupon> getCouponsByMaxCouponPrice(double price) throws CouponException {
		return couponDAO.getCouponsByMaxCouponPrice(companyId,price);
	}

	
	public Set<Coupon> getCouponsByMaxCouponDate(Date maxCouponDate)  throws CouponException{
		return couponDAO.getCouponsByMaxCouponDate(companyId, maxCouponDate);		
	}
	
	
	public long getCompanyId() {
		return companyId;
	}
	
	
	public Company getCompanyInstance() {
		return company;
	}


	}




