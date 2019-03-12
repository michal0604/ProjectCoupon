package projectCoupon.DAO;

import java.util.List;

import projectCoupon.Exception.CouponException;
import projectCoupon.Exception.CreateException;
import projectCoupon.Exception.RemoveException;
import projectCoupon.Exception.UpdateException;
import projectCoupon.beans.Company_Coupon;

public interface Company_CouponDAO {
	
	void insertCompany_Coupon(long companyId, long couponId) throws CreateException;
	
	void removeCompany_Coupon(long companyId, long couponId) throws RemoveException;
	
    void removeCompany_CouponByCompanyId(long companyId) throws RemoveException;
	
	void removeCompany_CouponByCouponId(long couponId) throws RemoveException, CouponException;

	List<Long> getCompanysByCouponId(long couponId) throws CouponException;
	
	List<Long> getCouponsByCompanyId(long companyId) throws CouponException;
	
	List<Company_Coupon> getAllCompany_Coupons() throws CouponException;
	
	void updateCompany_Coupon(long companyId, long couponId) throws UpdateException;

	boolean isCouponExistsForCompany(long companyId, long couponId) throws CouponException;
	
}