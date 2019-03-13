package projectCoupon.utils;

import java.sql.Connection;

import projectCoupon.exception.CouponException;
import projectCoupon.exception.DailyCouponException;
import projectCoupon.facad.AdminFacad;
import projectCoupon.facad.CouponClientFacade;

public class CouponSystem {

	private static CouponSystem instance;
	DailyCouponExpirationTask DailyTask;
	Thread thread;
	Connection connection;
	
	/**
	 * Thread timer - 1000*3600*24 is every 24 hours
	 */
	private static final int DAY = 1000 * 3600 * 24;
	private static final int SLEEPTIME = 1 * DAY;

	private CouponSystem() throws CouponException {
		// Activate the daily Coupons Deletion Demon (Thread)
		DailyTask = new DailyCouponExpirationTask(SLEEPTIME);
		thread = new Thread(DailyTask);
		thread.start();
	}
	

	public static CouponSystem getInstance() throws CouponException {
		if (instance == null)
			instance = new CouponSystem();
		return instance;
	}

	
	/**
	 * Shutdown system. Close all Connection Pool connections. Stop daily coupon
	 * expiration task deletion Thread.
	 **/
	public void shutdown() throws DailyCouponException {

		try {
			ConnectionPool connectionPool = ConnectionPool.getInstance();
			try {
				connectionPool.closeAllConnections(connection);
			} catch (Exception e) {
				throw new DailyCouponException("connection failed");
			}
		} catch (Exception e) {
			throw new DailyCouponException("ERROR! Properly Shut Down Application Failed!");
		}
		DailyTask.stop();
	}
}
