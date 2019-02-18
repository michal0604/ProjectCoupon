package Exception;

import projectCoupon.Coupons.Coupon;

// exception may occur when trying to get a specific coupon which is not in the DB

	public class CouponNotAvailableException extends Exception 
	{
		
		private static final long serialVersionUID = 1L;
		private Coupon coupon;
		
		public CouponNotAvailableException(Coupon coupon)
		{
			this.coupon = coupon;
		}
		
		public String getMessage()
		{
			return "Coupon "+coupon.getTitle()+" not available !";
		}
	}

