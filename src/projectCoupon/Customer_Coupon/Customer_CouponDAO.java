package projectCoupon.Customer_Coupon;


import java.util.List;

import projectCoupon.Exception.CouponException;
import projectCoupon.Exception.CreateException;
import projectCoupon.Exception.RemoveException;
import projectCoupon.Exception.UpdateException;

public interface Customer_CouponDAO {
	
		
		void insertCustomer_Coupon(long custId, long coupId) throws CreateException;
		
		void removeCustomer_Coupon(long custId, long coupId) throws RemoveException;
		
		void removeCustomer_CouponByCustomerId(long custId) throws RemoveException;
		
		void removeCustomer_CouponByCoupId(long coupId) throws RemoveException;
	
		List<Long> getCustomersByCouponId(long couponId) throws CouponException, CreateException;
		
		List<Long> getCouponsByCustomerId(long customerId) throws CouponException, CreateException;
		
		List<Customer_Coupon> getAllCustomer_Coupon() throws CouponException, CreateException;	
		
		void updateCustomer_Coupon(long custId, long coupId) throws UpdateException;

		boolean isCouponPurchasedByCustomer(long custId, long coupId) throws CouponException;

		void purchaseCoupon(long custId, long coupId) throws CouponException;

		boolean isPairInData(long custId, long coupId);
		
}