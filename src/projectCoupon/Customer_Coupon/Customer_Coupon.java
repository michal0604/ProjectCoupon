package projectCoupon.Customer_Coupon;

public class Customer_Coupon {
	private long cust_id;
	private long coupon_id;
	
	public Customer_Coupon() {

	}

	public Customer_Coupon(long cust_id, long coupon_id) {
		super();
		this.cust_id = cust_id;
		this.coupon_id = coupon_id;
	}

	public long getCust_id() {
		return cust_id;
	}
	public void setCust_id(long cust_id) {
		this.cust_id = cust_id;
	}
	

	public long getCoupon_id() {
		return coupon_id;
	}

	public void setCoupon_id(long coupon_id) {
		this.coupon_id = coupon_id;
	}

	@Override
	public String toString() {
		return "Customer_Coupon [cust_id=" + cust_id + ", coupon_id=" + coupon_id + "]";
	}
	
	

}
