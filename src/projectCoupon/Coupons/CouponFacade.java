package projectCoupon.Coupons;

import java.sql.Date;
import java.util.Set;

import projectCoupon.Company.Company;
import projectCoupon.Company.CompanyDBDAO;

public class CouponFacade {
	private CouponDBDAO couponDAO = new CouponDBDAO();
	private Coupon Coupon;

	public CouponFacade(Coupon c) {
		this.Coupon = c;

	}

	public CouponFacade() {
	}

	public void insertCoupon(Coupon Coupon) throws Exception {
		couponDAO.insertCoupon(Coupon);
	}

	public void removeCoupon(Coupon Coupon) throws Exception {
		couponDAO.removeCoupon(Coupon);
	}

	public void updateCoupon(Coupon Coupon, String newtitle, String newstart_date,String newend_date,Integer newamount,String newmessage,Double newprice,String newimage ) throws Exception {
		Coupon.setTitle(newtitle);
		Coupon.setStart_date(newstart_date);
		Coupon.setEnd_date(newend_date);
		Coupon.setAmount(newamount);
		Coupon.setMessage(newmessage);
		Coupon.setPrice(newprice);
		Coupon.setImage(newimage);
	}

	public Coupon getCoupon() {
		return Coupon;
	}

	public Set<Coupon> getAllCoupon() throws Exception {
	
		return couponDAO.getAllCoupon();
	}

	public void dropTable() throws Exception {
		couponDAO.dropTable();

	}

}


