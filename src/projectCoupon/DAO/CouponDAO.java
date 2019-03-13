package projectCoupon.DAO;

import java.sql.SQLException;
import java.util.List;

import projectCoupon.Exception.CouponException;
import projectCoupon.Exception.CreateException;
import projectCoupon.Exception.RemoveException;
import projectCoupon.Exception.UpdateException;
import projectCoupon.beans.Coupon;
import projectCoupon.beans.couponType;

public interface CouponDAO {
	void insertCoupon(Coupon Coupon) throws CreateException, SQLException;

	void updateCoupon(Coupon Coupon) throws UpdateException, CreateException;

	Coupon getCoupon(long couponId) throws CouponException, CreateException;

	List<Coupon> getAllCoupons() throws CouponException;

	List<Coupon> getAllCouponsByType(couponType coupType) throws CouponException;

	List<Coupon> getAllCouponsByPrice(double priceMax) throws CouponException;

	List<Coupon> getAllCouponsByDate(String untilDate) throws CouponException;

	void removeCoupon(Coupon Coupon) throws RemoveException, CreateException;

	List<Long> removeExpiredCoupons() throws CouponException, Exception;

	public boolean isCouponTitleExists(String coupTitle) throws CouponException;

	void removeCouponID(long couponId) throws CouponException;

	

}
