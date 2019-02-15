package Customer_Coupon;


import java.util.List;

public interface Customer_CouponDAO {
	
		
		void insertCustomer_Coupon(Customer_Coupon customer_coupon) throws Exception;
		
		void removeCustomer_Coupon(Customer_Coupon customer_coupon) throws Exception;
	
		List<Customer_Coupon> getCustomersByCouponId(long couponId) throws Exception;
		
		List<Customer_Coupon> getCouponsByCustomerId(long customerId) throws Exception;
		
		List<Customer_Coupon> getAllCustomer_Coupon() throws Exception;		
		
}