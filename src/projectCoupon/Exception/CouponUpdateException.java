package projectCoupon.Exception;

import projectCoupon.Coupons.Coupon;

//exception may occur when trying to update a specific coupon from DB

public class CouponUpdateException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private Coupon coupon;
	
	public CouponUpdateException(Coupon coupon)
	{
		this.coupon = coupon;
	}
	
	public String getMessage()
	{
		return "Updating Coupon "+coupon.getTitle()+" failed !";
	}

}
