package Company_Coupon;

public class Company_Coupon {
	private long comp_Id;
	private long coupon_Id;
	
	
	public Company_Coupon() {
		
	}


	public Company_Coupon(long comp_Id, long coupon_Id) {
		super();
		this.comp_Id = comp_Id;
		this.coupon_Id = coupon_Id;
	}


	public long getComp_Id() {
		return comp_Id;
	}


	public void setComp_Id(long comp_Id) {
		this.comp_Id = comp_Id;
	}


	public long getCoupon_Id() {
		return coupon_Id;
	}


	public void setCoupon_Id(long coupon_Id) {
		this.coupon_Id = coupon_Id;
	}


	@Override
	public String toString() {
		return "Company_Coupon [comp_Id=" + comp_Id + ", coupon_Id=" + coupon_Id + "]";
	}
	
	
	

}
