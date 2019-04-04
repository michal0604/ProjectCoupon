package projectCoupon.utils;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.rmi.CORBA.Util;

import projectCoupon.beans.Coupon;
import projectCoupon.dao.Company_CouponDAO;
import projectCoupon.dao.CouponDAO;
import projectCoupon.dao.Customer_CouponDAO;
import projectCoupon.dbdao.Company_CouponDBDAO;
import projectCoupon.dbdao.CouponDBDAO;
import projectCoupon.dbdao.Customer_CouponDBDAO;
import projectCoupon.exception.CouponException;
import projectCoupon.facad.AdminFacad;

public class DailyCouponExpirationTask implements Runnable {

	
	private static final boolean DEBUG = true;
	private static final TimeUnit TIMEUNIT = TimeUnit.MILLISECONDS;
	
	private static int DEBUG_DAY_ADDER = 0;
	private static int SLEEPTIME = 24 * 1000 * 3600;
	
	private CouponDAO couponDAO = new CouponDBDAO();
	private Customer_CouponDAO customer_CouponDAO = new Customer_CouponDBDAO();
	private Company_CouponDAO company_CouponDAO = new Company_CouponDBDAO();
	private boolean running = true;
	
	
	private Thread dailyTaskThread;
	

	public DailyCouponExpirationTask(int SLEEPTIME) throws CouponException {
		if (DEBUG) {
			DailyCouponExpirationTask.SLEEPTIME = 1000 * 15;
		} else {
			DailyCouponExpirationTask.SLEEPTIME = SLEEPTIME;
		}

	}

	public void startTask() throws CouponException {
		try {
			dailyTaskThread = new Thread(this);
			dailyTaskThread.start();
			System.out.println("daily task is start");

		} catch (Exception e) {
			throw new CouponException("daily task failed");
		}

	}

	@Override
	public void run() {
		while (running) {
			try {
				TIMEUNIT.sleep(SLEEPTIME);
			} catch (InterruptedException e) {
				System.out.println("bye bye");
				System.exit(0);
			}
			try {
				Date date;
				if (DEBUG) {
					date = Utile.getDateAfter(DEBUG_DAY_ADDER);
					DEBUG_DAY_ADDER++;
				} else {
					date = Utile.getCurrentDate();
				}
				System.out.println(date.toString() + " - Daily Task Running...");
				List<Coupon> allCoupons = couponDAO.getAllCoupons();
				for(Coupon coupon: allCoupons) {
					if (date.compareTo(coupon.getEnd_date()) > 0) {
						customer_CouponDAO.removeCustomer_CouponByCoupId(coupon.getCouponId());
						company_CouponDAO.removeCompany_CouponByCouponId(coupon.getCouponId());
						couponDAO.removeCoupon(coupon);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("daily task was failed");
			}
		}

	}

	/**
	 * Gracefully stops the Daily Task
	 */
	public void stop() {
		running = false;
	}

}
