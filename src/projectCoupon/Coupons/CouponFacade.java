package projectCoupon.Coupons;

import java.awt.Window.Type;
import java.sql.Date;
import java.util.Set;

import projectCoupon.Company.Company;
import projectCoupon.Company.CompanyDBDAO;

public class CouponFacade {
	private CouponDBDAO couponDAO = new CouponDBDAO();
	private Coupon coupon;
	private long couponId;

	public CouponFacade(Coupon c) {
		this.coupon = c;

	}

	public CouponFacade() {
	}

	public void insertCoupon(Coupon Coupon) throws Exception {
		couponDAO.insertCoupon(Coupon);
	}

	public void removeCoupon(Coupon Coupon) throws Exception {
		couponDAO.removeCoupon(Coupon);
	}

	public void updateCoupon(Coupon coupon, String newtitle, Date newstart_date,Date newend_date,Integer newamount,couponType type,String newmessage,Double newprice,String newimage ) throws Exception {
		coupon.setTitle(newtitle);
		coupon.setStart_date(newstart_date);
		coupon.setEnd_date(newend_date);
		coupon.setAmount(newamount);
		coupon.setType(type);
		coupon.setMessage(newmessage);
		coupon.setPrice(newprice);
		coupon.setImage(newimage);
		couponDAO.updateCoupon(coupon);
	}

	public Coupon getCoupon(long id) throws Exception {
		return coupon;
	}

	public Set<Coupon> getAllCoupons() throws Exception {
	
		return couponDAO.getAllCoupons();
	}

	public void dropTable() throws Exception {
		couponDAO.dropTable();

	}

}


