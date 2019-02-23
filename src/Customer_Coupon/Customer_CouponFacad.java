package Customer_Coupon;

import java.util.List;



public class Customer_CouponFacad {
	private Customer_CouponDBDAO cust_couponDAO = new Customer_CouponDBDAO();
	private long cust_id;
	private long coupon_id;
	private Customer_Coupon customer_coupon;
	
	public Customer_CouponFacad(Customer_Coupon customer_coupon) {
		this.customer_coupon = customer_coupon;

	}
		
		public Customer_CouponFacad() {}
		
		public void insertCustomer_Coupon(Customer_Coupon customer_coupon) throws Exception {
			//TODO update the exception to the suited one
			cust_couponDAO.insertCustomer_Coupon(customer_coupon);
		}
		
		public void removeCustomer_Coupon(Customer_Coupon customer_coupon) throws Exception {
			//TODO update the exception to the suited one
			cust_couponDAO.removeCustomer_Coupon(customer_coupon);
		}



		/**
		 * @return all the companies in the storage 
		 * @throws Exception when the retrieval operation fails
		 */
		public List<Customer_Coupon> getAllCustomer_Coupon() throws Exception {
			//TODO update the exception to the suited one
			// ProductDBDAO comDAO=new ProductDBDAO();
			return cust_couponDAO.getAllCustomer_Coupon();
		}

		public List<Customer_Coupon> getCustomersByCouponId(long couponId) throws Exception{
			
			 return cust_couponDAO.getCustomersByCouponId(couponId);
		}
		
		public List<Customer_Coupon> getCouponsByCustomerId(long customerId) throws Exception{
			
			 return cust_couponDAO.getCouponsByCustomerId(customerId);

}
}