package projectCoupon.Customer_Coupon;


import java.util.List;

import projectCoupon.Company_Coupon.Company_Coupon;

public interface Customer_CouponDAO {
	
		
		void insertCustomer_Coupon(Customer_Coupon customer_coupon) throws Exception;
		
		void removeCustomer_Coupon(Customer_Coupon customer_coupon) throws Exception;
	
		List<Customer_Coupon> getCustomersByCouponId(long couponId) throws Exception;
		
		List<Customer_Coupon> getCouponsByCustomerId(long customerId) throws Exception;
		
		List<Customer_Coupon> getAllCustomer_Coupon() throws Exception;	
		
		void updateCustomer_Coupon(Customer_Coupon customer_Coupon) throws Exception;
		
}