package Exception;

import projectCoupon.Coupons.Coupon;

//exception may occur when trying to remove a specific coupon from DB

public class CouponRemovalException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private Coupon coupon;
	
	public CouponRemovalException(Coupon coupon)
	{
		this.coupon = coupon;
	}
	
	public String getMessage()
	{
		return "Removing Coupon "+coupon.getTitle()+" failed !";
	}

}
