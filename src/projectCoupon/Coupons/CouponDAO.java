package projectCoupon.Coupons;

import java.util.List;

import projectCoupon.Exception.CouponException;

public interface CouponDAO {
	void insertCoupon(Coupon Coupon) throws Exception;

	void removeCoupon(long coupId) throws Exception;

	void removeCouponID(long id) throws Exception;

	void updateCoupon(Coupon Coupon) throws Exception;

	Coupon getCoupon(long id) throws Exception;

	List<Coupon> getAllCoupons() throws Exception;

	// TODO function empty
	boolean isCouponExistsForCompany(long companyId, long coupId);

	boolean isCouponTitleExists(String coupTitle);

	List<Coupon> getAllCouponsByType(long couponId, String typeName) throws Exception;

	List<Coupon> getAllCouponsByPrice(long couponId, double priceMax) throws Exception;

	List<Coupon> getAllCouponsByDate(long couponId, String untilDate) throws Exception;

	List<Coupon> getAllCoupons(long couponId) throws Exception;

	void removeCoupon(Coupon Coupon) throws Exception;

	List<Coupon> getAllPurchasedCouponsByPrice(long customerId, long price);

	List<Coupon> getAllPurchasedCouponsByType(long customerId, couponType type);

	List<Coupon> getAllPurchasedCoupons(long customerId);

	void purchaseCoupon(long customerId, long coupId);

	boolean isCouponPurchasedByCustomer(long customerId, long coupId);

	void removeExpiredCoupons(long couponId) throws CouponException, Exception;

	List<Coupon> getCouponsByType(long couponId, couponType coupType) throws Exception;

}
