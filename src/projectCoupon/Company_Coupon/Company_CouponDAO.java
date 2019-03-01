package projectCoupon.Company_Coupon;

import java.util.List;

import projectCoupon.Company.Company;
import projectCoupon.Coupons.Coupon;
import projectCoupon.Exception.CouponException;
import projectCoupon.Exception.CreateException;
import projectCoupon.Exception.RemoveException;
import projectCoupon.Exception.UpdateException;

public interface Company_CouponDAO {
	
	void insertCompany_Coupon(Company company, Coupon coupon) throws CreateException;
	
	void removeCompany_Coupon(Company company, Coupon coupon) throws RemoveException;
	
    void removeCompany_Coupon(Company company) throws RemoveException;
	
	void removeCompany_Coupon(Coupon coupon) throws RemoveException;

	List<Company_Coupon> getCompanysByCouponId(long couponId) throws CouponException;
	
	List<Company_Coupon> getCouponsByCompanyId(long companyId) throws CouponException;
	
	List<Company_Coupon> getAllCompany_Coupons() throws CouponException;
	
	void updateCompany_Coupon(Company company, Coupon coupon) throws UpdateException;
	
}


