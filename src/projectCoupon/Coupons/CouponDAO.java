package projectCoupon.Coupons;


	import java.util.List;

import Exception.CouponException;

	public interface CouponDAO {
		void insertCoupon(Coupon Coupon) throws Exception;

		void removeCoupon(Coupon Coupon) throws Exception;
		
		void removeCouponID(long id) throws Exception;

		void updateCoupon(Coupon Coupon) throws Exception;

		Coupon getCoupon(long id) throws Exception;

		List<Coupon> getAllCoupons() throws Exception;

		Coupon dropTable() throws Exception;
		public void removeExpiredCoupons() throws CouponException;

	}



