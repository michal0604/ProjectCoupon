package projectCoupon.Customer_Coupon;

import java.util.List;

import projectCoupon.Exception.CouponException;



public class Customer_CouponFacad {
	private Customer_CouponDBDAO cust_couponDAO;
	// TODO do we need this argument: private long cust_id;
	// TODO do we need this argument: private long coupon_id;
	// TODO do we need this argument: private Customer_Coupon customer_coupon;
	
	public Customer_CouponFacad(Customer_Coupon customer_coupon) {
		// TODO do we need this argument: this.customer_coupon = customer_coupon;
		try {
			this.cust_couponDAO = new Customer_CouponDBDAO();
		} catch (CouponException e) {
			// TODO see what is the Exception and fix line
			e.printStackTrace();
		}

	}
		
		public Customer_CouponFacad() {
			// TODO do we need this argument: this.customer_coupon = new Customer_Coupon();
			try {
				this.cust_couponDAO = new Customer_CouponDBDAO();
			} catch (CouponException e) {
				// TODO see what is the Exception and fix line
				e.printStackTrace();
			}
		}
		
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