package projectCoupon.beans;

/**
 * this is the object representing the association of a coupon with a company
 * 
 * @author Eivy & Michal
 *
 */
public class Company_Coupon {
	private long companyId;
	private long couponId;
	
	
	/**
	 *  empty cTor
	 */
	public Company_Coupon() {}


	/**
	 * full cTor for the representation of the link between a company and coupon
	 * 
	 * @param companyId the id of the company
	 * @param couponId
	 */
	public Company_Coupon(long companyId, long couponId) {
		super();
		this.companyId = companyId;
		this.couponId = couponId;
	}


	/**
	 * @return the id of the company
	 */
	public long getCompanyId() {
		return companyId;
	}


	/**
	 * @param companyId id of the company to be set.
	 */
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}


	/**
	 * @return the id of the coupon
	 */
	public long getCouponId() {
		return couponId;
	}


	/**
	 * @param couponId id of the coupon to be set.
	 */
	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}


	/**
	 *  @return a String  with the id of the company and the associated coupon id
	 */
	@Override
	public String toString() {
		return "Company_Coupon [companyId=" + companyId + ", couponId=" + couponId + "]";
	}


	

}
