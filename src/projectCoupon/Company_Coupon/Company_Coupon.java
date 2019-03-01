package projectCoupon.Company_Coupon;

public class Company_Coupon {
	private long companyId;
	private long couponId;
	
	
	public Company_Coupon() {
		
	}


	public Company_Coupon(long companyId, long couponId) {
		super();
		this.companyId = companyId;
		this.couponId = couponId;
	}


	public long getCompanyId() {
		return companyId;
	}


	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}


	public long getCouponId() {
		return couponId;
	}


	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}


	@Override
	public String toString() {
		return "Company_Coupon [companyId=" + companyId + ", couponId=" + couponId + "]";
	}


	

}
