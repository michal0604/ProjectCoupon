package projectCoupon.DAO;


import java.util.List;

import projectCoupon.Exception.CouponException;
import projectCoupon.Exception.CreateException;
import projectCoupon.Exception.RemoveException;
import projectCoupon.Exception.UpdateException;
import projectCoupon.beans.Customer;
import projectCoupon.beans.Customer_Coupon;

public interface Customer_CouponDAO {
	
		
		void insertCustomer_Coupon(long customerId, long couponId) throws CreateException;
		
		void removeCustomer_Coupon(long customerId, long couponId) throws RemoveException;
		
		void removeCustomer_Coupon(Customer customer) throws RemoveException;
		
		void removeCustomer_CouponByCoupId(long couponId) throws RemoveException;
	
		List<Long> getCustomersByCouponId(long couponId) throws CouponException, CreateException;
		
		List<Long> getCouponsByCustomerId(long customerId) throws CouponException, CreateException;
		
		List<Customer_Coupon> getAllCustomer_Coupon() throws CouponException, CreateException;	
		
		void updateCustomer_Coupon(long customerId, long couponId) throws UpdateException;

		boolean isCouponPurchasedByCustomer(long customerId, long couponId) throws CouponException;
		
}