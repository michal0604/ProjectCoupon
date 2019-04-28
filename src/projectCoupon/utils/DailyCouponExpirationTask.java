package projectCoupon.utils;

import java.sql.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import projectCoupon.beans.Coupon;
import projectCoupon.dao.Company_CouponDAO;
import projectCoupon.dao.CouponDAO;
import projectCoupon.dao.Customer_CouponDAO;
import projectCoupon.dbdao.Company_CouponDBDAO;
import projectCoupon.dbdao.CouponDBDAO;
import projectCoupon.dbdao.Customer_CouponDBDAO;
import projectCoupon.exception.CouponException;

/**
 * @author Eivy & Michal
 * Daily Thread is delete expired coupons in coupon table and join table
 * (coupon,CompanyCoupon,CustomerCoupon)
 *
 */
public class DailyCouponExpirationTask implements Runnable {

	
	
	private static final TimeUnit TIMEUNIT = TimeUnit.MILLISECONDS;
	
	private static boolean DEBUG = false;
	private static int DEBUG_DAY_ADDER = 0;
	private static int SLEEPTIME = 24 * 1000 * 3600;
	
	private CouponDAO couponDAO = new CouponDBDAO();
	private Customer_CouponDAO customer_CouponDAO = new Customer_CouponDBDAO();
	private Company_CouponDAO company_CouponDAO = new Company_CouponDBDAO();
	private boolean running = true;
	private int sleepingTime = DailyCouponExpirationTask.SLEEPTIME;
	
	
	private Thread dailyTaskThread;
	

	/**
	 * constructor
	 * @param sleepTime
	 * @throws CouponException
	 */
	public DailyCouponExpirationTask(int sleepTime) throws CouponException {
		DailyCouponExpirationTask.SLEEPTIME = sleepTime;
		this.sleepingTime = sleepTime;
	}

	/**
	 * this method activate the Daily Thread
	 * @throws CouponException
	 */
	public void startTask() throws CouponException {
		try {
			dailyTaskThread = new Thread(this);
			dailyTaskThread.start();
			System.out.println("daily task is start");

		} catch (Exception e) {
			throw new CouponException("daily task failed");
		}

	}

	/** 
	 * The Thread run action - delete expired Coupons
	 */
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
				Set<Coupon> allCoupons = couponDAO.getAllCoupons();
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

	/**
	 * @param debugMode
	 */
	public void setDebugMode(boolean debugMode) {
		DEBUG = debugMode;
		if (debugMode) {
			DailyCouponExpirationTask.SLEEPTIME = 1000 * 15;
		} else {
			DailyCouponExpirationTask.SLEEPTIME = sleepingTime;
		}
	}

}
