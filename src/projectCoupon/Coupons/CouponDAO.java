package projectCoupon.Coupons;


	import java.util.Set;

	public interface CouponDAO {
		void insertCoupon(Coupon Coupon) throws Exception;

		void removeCoupon(Coupon Coupon) throws Exception;
		
		void removeCouponID(long id) throws Exception;

		void updateCoupon(Coupon Coupon) throws Exception;

		Coupon getCoupon(long id) throws Exception;

		Set<Coupon> getAllCoupons() throws Exception;

		Coupon dropTable() throws Exception;

	}



