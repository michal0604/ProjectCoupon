package projectCoupon.Customer_Coupon;


import java.util.List;

import projectCoupon.Coupons.Coupon;
import projectCoupon.Customer.Customer;

public interface Customer_CouponDAO {
	
		
		void insertCustomer_Coupon(Customer customer, Coupon coupon) throws Exception;
		
		void removeCustomer_Coupon(Customer customer, Coupon coupon) throws Exception;
		
		void removeCustomer_Coupon(Customer_Coupon customer_coupon) throws Exception;
		
		void removeCustomer_Coupon(Customer customer) throws Exception;
		
		void removeCustomer_Coupon(Coupon coupon) throws Exception;
	
		List<Customer_Coupon> getCustomersByCouponId(long couponId) throws Exception;
		
		List<Customer_Coupon> getCouponsByCustomerId(long customerId) throws Exception;
		
		List<Customer_Coupon> getAllCustomer_Coupon() throws Exception;	
		
		void updateCustomer_Coupon(Customer_Coupon customer_Coupon) throws Exception;
		
}