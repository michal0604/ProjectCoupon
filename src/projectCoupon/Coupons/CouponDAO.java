package projectCoupon.Coupons;

import java.sql.SQLException;
import java.util.List;

import projectCoupon.Exception.ConnectionException;
import projectCoupon.Exception.CouponException;
import projectCoupon.Exception.CreateException;
import projectCoupon.Exception.RemoveException;
import projectCoupon.Exception.UpdateException;

public interface CouponDAO {
	void insertCoupon(Coupon Coupon) throws CreateException, SQLException,ConnectionException;

	void removeCoupon(long coupId) throws RemoveException;

	void removeCouponID(long id) throws CouponException;

	void updateCoupon(Coupon Coupon) throws UpdateException;

	Coupon getCoupon(long id) throws CouponException;

	List<Coupon> getAllCoupons() throws CouponException;

	// TODO function empty
	boolean isCouponExistsForCompany(long companyId, long coupId);

	boolean isCouponTitleExists(String coupTitle);

	List<Coupon> getAllCouponsByType(long couponId, String typeName) throws CouponException;

	List<Coupon> getAllCouponsByPrice(long couponId, double priceMax) throws CouponException;

	List<Coupon> getAllCouponsByDate(long couponId, String untilDate) throws CouponException;

	List<Coupon> getAllCoupons(long couponId) throws CouponException;

	void removeCoupon(Coupon Coupon) throws  CouponException;

	List<Coupon> getAllPurchasedCouponsByPrice(long customerId, long price);

	List<Coupon> getAllPurchasedCouponsByType(long customerId, couponType type);

	List<Coupon> getAllPurchasedCoupons(long customerId);

	void purchaseCoupon(long customerId, long coupId);

	boolean isCouponPurchasedByCustomer(long customerId, long coupId);

	void removeExpiredCoupons() throws CouponException, Exception;

	List<Coupon> getCouponsByType(long couponId, couponType coupType) throws CouponException;

}
