package projectCoupon.Company_Coupon;

import java.util.List;

import projectCoupon.Company.Company;
import projectCoupon.Coupons.Coupon;
import projectCoupon.Exception.CouponException;
import projectCoupon.Exception.CreateException;
import projectCoupon.Exception.RemoveException;
import projectCoupon.Exception.UpdateException;

public interface Company_CouponDAO {
	
	void insertCompany_Coupon(Company_Coupon company_Coupon) throws CreateException;
	
	void removeCompany_Coupon(Company_Coupon company_Coupon) throws RemoveException;
	
    void removeCompany_CouponByCompany(Company company) throws RemoveException;
	
	void removeCompany_CouponByCoupon(Coupon coupon) throws RemoveException;

	List<Company_Coupon> getCompanysByCouponId(long couponId) throws CouponException;
	
	List<Company_Coupon> getCouponsByCompanyId(long companyId) throws CouponException;
	
	List<Company_Coupon> getAllCompany_Coupons() throws CouponException;
	
	void updateCompany_Coupon(Company_Coupon company_Coupon) throws UpdateException;

	void removeCompany_Coupon(Company company) throws RemoveException;

	void removeCompany_CouponByCoupon(Company company) throws RemoveException;

	boolean isCouponExistsForCompany(long companyId, long couponId) throws CouponException;

	
	
}


