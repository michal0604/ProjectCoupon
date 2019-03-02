package projectCoupon.Company_Coupon;

import java.util.List;

import projectCoupon.Company.Company;
import projectCoupon.Coupons.Coupon;
import projectCoupon.Exception.CouponException;

public class Company_CouponFacad {
	Company_CouponDBDAO comp_couponDAO;
	// TODO do we need this argument: private long comp_Id;
	// TODO do we need this argument: private long coupon_Id;
	// TODO do we need this argument: private Company_Coupon company_Coupon;

	public Company_CouponFacad(Company_Coupon company_Coupon) {
		// TODO do we need this argument: this.company_Coupon = company_Coupon;
		try {
			this.comp_couponDAO = new Company_CouponDBDAO();
		} catch (CouponException e) {
			// TODO see what is the Exception and fix line
			e.printStackTrace();
		}

	}

	public Company_CouponFacad() {
		// TODO do we need this argument: this.company_Coupon = new Company_Coupon();
		try {
			this.comp_couponDAO = new Company_CouponDBDAO();
		} catch (CouponException e) {
			// TODO see what is the Exception and fix line
			e.printStackTrace();
		}
	}

	public void insertCompany_Coupon(Company company, Coupon coupon) throws Exception {
		Company_Coupon company_Coupon = new Company_Coupon(company.getCompanyId(), coupon.getCouponId());
		comp_couponDAO.insertCompany_Coupon(company_Coupon);
	}

	public void removeCompany_Coupon(Company_Coupon company_Coupon) throws Exception {
		// TODO update the exception to the suited one
		comp_couponDAO.removeCompany_Coupon(company_Coupon);
	}

	public List<Company_Coupon> getAllCCompany_Coupon() throws Exception {
		// TODO update the exception to the suited one
		// ProductDBDAO comDAO=new ProductDBDAO();
		return comp_couponDAO.getAllCompany_Coupons();
	}

	public List<Company_Coupon> getCompanysByCouponId(long couponId) throws Exception {

		return comp_couponDAO.getCompanysByCouponId(couponId);
	}

	public List<Company_Coupon> getCouponsByCompanyId(long companyId) throws Exception {

		return comp_couponDAO.getCouponsByCompanyId(companyId);

	}

	public void updateCompany_Coupon(Company_Coupon company_Coupon, long newcompId, long newcouponId) throws Exception {
		// TODO update the exception to the suited one
		company_Coupon.setComp_Id(newcompId);
		company_Coupon.setCoupon_Id(newcouponId);
		comp_couponDAO.updateCompany_Coupon(company_Coupon);

	}

}
