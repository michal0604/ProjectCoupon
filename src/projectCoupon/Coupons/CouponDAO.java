package projectCoupon.Coupons;

import java.sql.SQLException;
import java.util.List;
import projectCoupon.Exception.CouponException;
import projectCoupon.Exception.CreateException;
import projectCoupon.Exception.RemoveException;
import projectCoupon.Exception.UpdateException;

public interface CouponDAO {
	void insertCoupon(Coupon Coupon) throws CreateException, SQLException;

	void removeCoupon(long coupId) throws CouponException;

	void updateCoupon(Coupon Coupon) throws UpdateException, CreateException;

	Coupon getCoupon(long id) throws CouponException, CreateException;

	List<Coupon> getAllCoupons() throws CouponException;

	List<Coupon> getAllCouponsByType(couponType coupType) throws CouponException;

	List<Coupon> getAllCouponsByPrice(double priceMax) throws CouponException;

	List<Coupon> getAllCouponsByDate(String untilDate) throws CouponException;

	List<Coupon> getAllCoupons(long couponId) throws CouponException;

	void removeCoupon(Coupon Coupon) throws  RemoveException, CreateException;

	void removeExpiredCoupons() throws CouponException, Exception;

	public boolean isCouponTitleExists(String coupTitle) throws CouponException;

	void purchaseCoupon(long custId, long coupId)throws CouponException;

	List<Coupon> getAllPurchasedCouponsByPrice(long custId, long price);

	List<Coupon> getAllPurchasedCoupons(long custId);

	List<Coupon> getAllPurchasedCouponsByType(long custId, couponType type);

	void removeCouponID(long id) throws CouponException;

	

	//List<Coupon> getCouponsByType(long couponId, couponType coupType) throws CouponException;

}
