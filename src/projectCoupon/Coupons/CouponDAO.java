package projectCoupon.Coupons;


	import java.sql.Date;
import java.util.List;
import java.util.Set;

import projectCoupon.Exception.CouponException;

	public interface CouponDAO {
		void insertCoupon(Coupon Coupon) throws Exception;

		void removeCoupon(long coupId) throws Exception;
		
		void removeCouponID(long id) throws Exception;

		void updateCoupon(Coupon Coupon) throws Exception;

		Coupon getCoupon(long id) throws Exception;

		List<Coupon> getAllCoupons() throws Exception;

		Coupon dropTable() throws Exception;
		public static void removeExpiredCoupons() throws CouponException {
			// TODO Auto-generated method stub
			
		}

		//TODO function empty
		boolean isCouponExistsForCompany(long companyId, long coupId);

		boolean isCouponTitleExists(String coupTitle);

		Set<Coupon> getCoupons(long companyId, int i, int j, boolean b);

		Set<Coupon> getCouponsByType(long companyId, couponType coupType);

		Set<Coupon> getCouponsByMaxCouponPrice(long companyId, double price);

		Set<Coupon> getCouponsByMaxCouponDate(long companyId, Date maxCouponDate);

		void removeCoupon(Coupon Coupon) throws Exception;

		Set<Coupon> getAllPurchasedCouponsByPrice(long customerId, long price);

		Set<Coupon> getAllPurchasedCouponsByType(long customerId, couponType type);

		Set<Coupon> getAllPurchasedCoupons(long customerId);

		void purchaseCoupon(long customerId, long coupId);

		boolean isCouponPurchasedByCustomer(long customerId, long coupId);

		void removeExpiredCoupons(long couponId) throws CouponException;

		

	}



