package projectCoupon.Coupons;


	import java.util.Set;

	public interface CouponDAO {
		void insertCoupon(Coupon Coupon) throws Exception;

		void removeCoupon(Coupon Coupon) throws Exception;

		void updateCoupon(Coupon Coupon) throws Exception;

		Coupon getPCoupon(long id) throws Exception;

		Set<Coupon> getAllCoupon() throws Exception;

		Coupon dropTable() throws Exception;

	}


