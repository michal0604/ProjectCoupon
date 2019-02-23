package projectCoupon.Exception;

import projectCoupon.Coupons.Coupon;

// exception handler for an exception which may occur during company object creation
	

	public class CouponCreationException extends Exception 
	{
		
		private static final long serialVersionUID = 1L;
		private Coupon coupon;
		
		public CouponCreationException(Coupon coupon)
		{
			this.coupon = coupon;
		}
		
		public String getMessage()
		{
			return "Creating coupon "+coupon.getTitle()+" failed !";
		}
	}


