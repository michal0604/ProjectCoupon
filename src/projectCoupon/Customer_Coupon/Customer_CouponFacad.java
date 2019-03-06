package projectCoupon.Customer_Coupon;

import java.util.List;

import projectCoupon.Exception.CouponException;

public class Customer_CouponFacad {
	private Customer_CouponDBDAO cust_couponDAO;

	public Customer_CouponFacad(Customer_Coupon customer_coupon) {
		try {
			this.cust_couponDAO = new Customer_CouponDBDAO();
		} catch (CouponException e) {
			e.printStackTrace();
		}

	}

	public Customer_CouponFacad() {
		try {
			this.cust_couponDAO = new Customer_CouponDBDAO();
		} catch (CouponException e) {
			e.printStackTrace();
		}
	}

	public void insertCustomer_Coupon(Customer_Coupon customer_coupon) throws Exception {
		cust_couponDAO.insertCustomer_Coupon(customer_coupon.getCustomerId(), customer_coupon.getCouponId());
	}

	public void removeCustomer_Coupon(Customer_Coupon customer_coupon) throws Exception {
		cust_couponDAO.removeCustomer_Coupon(customer_coupon.getCustomerId(), customer_coupon.getCouponId());
	}

	/**
	 * @return all the companies in the storage
	 * @throws Exception
	 *             when the retrieval operation fails
	 */
	public List<Customer_Coupon> getAllCustomer_Coupon() throws Exception {
		return cust_couponDAO.getAllCustomer_Coupon();
	}

	public List<Long> getCustomersByCouponId(long couponId) throws Exception {
		return cust_couponDAO.getCustomersByCouponId(couponId);
	}

	public List<Long> getCouponsByCustomerId(long customerId) throws Exception {
		return cust_couponDAO.getCouponsByCustomerId(customerId);

	}
}