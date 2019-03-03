package projectCoupon.Customer_Coupon;


import java.util.List;

import projectCoupon.Coupons.Coupon;
import projectCoupon.Customer.Customer;
import projectCoupon.Exception.CouponException;
import projectCoupon.Exception.CreateException;
import projectCoupon.Exception.RemoveException;
import projectCoupon.Exception.UpdateException;

public interface Customer_CouponDAO {
	
		
		void insertCustomer_Coupon(Customer customer, Coupon coupon) throws CreateException;
		
		void removeCustomer_Coupon(Customer customer, Coupon coupon) throws RemoveException;
		
		void removeCustomer_Coupon(Customer customer) throws RemoveException;
		
		void removeCustomer_Coupon(Coupon coupon) throws RemoveException;
	
		List<Customer_Coupon> getCustomersByCouponId(long couponId) throws CouponException;
		
		List<Customer_Coupon> getCouponsByCustomerId(long customerId) throws CouponException;
		
		List<Customer_Coupon> getAllCustomer_Coupon() throws CouponException;	
		
		void updateCustomer_Coupon(Customer customer, Coupon coupon) throws UpdateException;

		boolean isCouponPurchasedByCustomer(long custId, long coupId) throws CouponException;

		void purchaseCoupon(long custId, long coupId) throws CouponException;
		
}