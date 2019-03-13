package projectCoupon.dao;

import java.util.List;

import projectCoupon.beans.Company;
import projectCoupon.beans.Company_Coupon;
import projectCoupon.exception.CouponException;
import projectCoupon.exception.CreateException;
import projectCoupon.exception.RemoveException;
import projectCoupon.exception.UpdateException;

public interface Company_CouponDAO {
	
	void insertCompany_Coupon(long companyId, long couponId) throws CreateException;
	
	void removeCompany_Coupon(long companyId, long couponId) throws RemoveException;
	
    void removeCompany_Coupon(Company company) throws RemoveException;
	
	void removeCompany_CouponByCouponId(long couponId) throws RemoveException, CouponException;

	List<Long> getCompanysByCouponId(long couponId) throws CouponException;
	
	List<Long> getCouponsByCompanyId(long companyId) throws CouponException;
	
	List<Company_Coupon> getAllCompany_Coupons() throws CouponException;
	
	void updateCompany_Coupon(long companyId, long couponId) throws UpdateException;

	boolean isCouponExistsForCompany(long companyId, long couponId) throws CouponException;
	
}