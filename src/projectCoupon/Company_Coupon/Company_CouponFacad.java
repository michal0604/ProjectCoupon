package projectCoupon.Company_Coupon;

import java.util.List;

import projectCoupon.Exception.CouponException;
import projectCoupon.Exception.CreateException;
import projectCoupon.Exception.RemoveException;
import projectCoupon.Exception.UpdateException;

public class Company_CouponFacad {
	Company_CouponDBDAO comp_couponDAO;

	public Company_CouponFacad(Company_Coupon company_Coupon) {
		try {
			this.comp_couponDAO = new Company_CouponDBDAO();
		} catch (CouponException e) {
			// TODO see what is the Exception and fix line
			e.printStackTrace();
		}

	}

	public Company_CouponFacad() {
		try {
			this.comp_couponDAO = new Company_CouponDBDAO();
		} catch (CouponException e) {
			// TODO see what is the Exception and fix line
			e.printStackTrace();
		}
	}

	public void insertCompany_Coupon(long companyId, long couponId) throws CreateException{
		comp_couponDAO.insertCompany_Coupon(companyId,couponId);
	}

	public void removeCompany_Coupon(long companyId, long couponId) throws RemoveException {
		comp_couponDAO.removeCompany_Coupon(companyId,couponId);
	}

	public List<Company_Coupon> getAllCCompany_Coupon() throws CouponException{
		return comp_couponDAO.getAllCompany_Coupons();
	}

	public List<Long> getCompanysByCouponId(long couponId) throws CouponException {
		return comp_couponDAO.getCompanysByCouponId(couponId);
	}

	public List<Long> getCouponsByCompanyId(long companyId) throws CouponException{
		return comp_couponDAO.getCouponsByCompanyId(companyId);
	}

	public void updateCompany_Coupon(long newcompId, long newcouponId) throws UpdateException{
		comp_couponDAO.updateCompany_Coupon(newcompId,newcouponId);
	}

}
