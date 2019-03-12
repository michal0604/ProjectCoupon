package projectCoupon.beans;

public class Customer_Coupon {
	private long customerId;
	private long couponId;
	
	public Customer_Coupon() {

	}

	public Customer_Coupon(long customerId, long couponId) {
		super();
		this.customerId = customerId;
		this.couponId = couponId;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public long getCouponId() {
		return couponId;
	}

	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}

	@Override
	public String toString() {
		return "Customer_Coupon [customerId=" + customerId + ", couponId=" + couponId + "]";
	}

	
	

}
