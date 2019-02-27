package projectCoupon.Clients;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import projectCoupon.Company.Company;
import projectCoupon.Company.CompanyDAO;
import projectCoupon.Company.CompanyDBDAO;
import projectCoupon.Company_Coupon.Company_Coupon;
import projectCoupon.Company_Coupon.Company_CouponDAO;
import projectCoupon.Company_Coupon.Company_CouponDBDAO;
import projectCoupon.Coupons.Coupon;
import projectCoupon.Coupons.CouponDAO;
import projectCoupon.Coupons.CouponDBDAO;
import projectCoupon.Coupons.Utile;
import projectCoupon.Coupons.couponType;
import projectCoupon.Exception.CouponException;

public class CompanyFacade  implements CouponClientFacade {

	private CompanyDAO companyDAO;
	private CouponDAO couponDAO ;
	private Company_CouponDAO company_CouponDAO;
	private long companyId = 0;
	private Company company;
	
	/**
	 * Constructor
	 * @throws CouponException 
	 */
	public CompanyFacade() throws CouponException  {
		companyDAO = new CompanyDBDAO();
		couponDAO = new CouponDBDAO();
		company_CouponDAO = new Company_CouponDBDAO();
	}

	@Override
	public CouponClientFacade login(String name, String password) throws Exception {
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
				throw new CouponException("Coupon Not Exist for Company! Remove Coupon is Canceled!");
			}
		} else {
			throw new CouponException("No Coupon Was Chosen! Remove Coupon is Canceled!");
		}
	}

	
	public void updateCoupon(Coupon coupon) throws Exception {
		if (coupon != null) {
			long couponId = coupon.getCouponId();
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
	
	
	public List<Coupon> getCoupons() throws Exception {
		List<Coupon>allCoupons=new ArrayList<Coupon>();
		allCoupons=couponDAO.getAllCoupons();
		return allCoupons;
	}

	
	public List<Coupon> getCouponsByType(couponType coupType) throws Exception {
		List<Coupon> coupons = new ArrayList<Coupon>();
		Coupon coupon;
		List<Company_Coupon> companyCouponList = company_CouponDAO.getCouponsByCompanyId(companyId);
		for (Company_Coupon companyCoupon : companyCouponList) {
			coupon = couponDAO.getCoupon(companyCoupon.getCoupon_Id());
			if (coupon.getType().equals(coupType) ) {
				coupons.add(coupon);
			}
		}
		return coupons;
	}
	

	
	public List<Coupon> getCouponsByMaxCouponPrice(double price) throws Exception {
		List<Coupon> coupons = new ArrayList<Coupon>();
		Coupon coupon;
		List<Company_Coupon> companyCouponList = company_CouponDAO.getCouponsByCompanyId(companyId);
		for (Company_Coupon companyCoupon : companyCouponList) {
			coupon = couponDAO.getCoupon(companyCoupon.getCoupon_Id());
			if (coupon.getPrice() <= price ) {
				coupons.add(coupon);
			}
		}
		return coupons;
	}


	
	public List<Coupon> getCouponsByMaxCouponDate(Date endDate)  throws Exception{
		List<Coupon> coupons = new ArrayList<Coupon>();
		Coupon coupon;
		List<Company_Coupon> companyCouponList = company_CouponDAO.getCouponsByCompanyId(companyId);
		for (Company_Coupon companyCoupon : companyCouponList) {
			coupon = couponDAO.getCoupon(companyCoupon.getCoupon_Id());
			if (coupon.getEnd_date().equals(endDate) || coupon.getEnd_date().before(endDate) ) {
				coupons.add(coupon);
			}
		}
		return coupons;
	}		
	
	
	
	public long getCompanyId() {
		return companyId;
	}
	
	
	public Company getCompanyInstance() {
		return company;
	}


	}




