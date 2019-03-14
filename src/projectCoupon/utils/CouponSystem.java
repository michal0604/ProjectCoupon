package projectCoupon.utils;

import java.sql.Connection;

import projectCoupon.exception.CouponException;
import projectCoupon.exception.DailyCouponException;
import projectCoupon.facad.AdminFacad;
import projectCoupon.facad.CompanyFacade;
import projectCoupon.facad.CouponClientFacade;
import projectCoupon.facad.CustomerFacad;

public class CouponSystem {

	private static CouponSystem instance;
	DailyCouponExpirationTask DailyTask;
	Thread thread;
	Connection connection;
	
	public enum clientType {
		Admin, Customer, Company
	};
	
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

	
public CouponClientFacade login(String name, String password, ClientType clientType ) throws Exception {
	CouponClientFacade couponClientFacade = null;

		switch (clientType) {
		case ADMIN:
			couponClientFacade=new AdminFacad();
			break;
		case COMPANY:
			couponClientFacade = new CompanyFacade();
			break;
		case CUSTOMER:
			couponClientFacade = new CustomerFacad();
			break;
		default:
			couponClientFacade = null;
		} 
		
		if (couponClientFacade != null) {
			couponClientFacade = couponClientFacade.login(name,password,clientType);
			if (couponClientFacade != null) {
				return couponClientFacade;
			} else {
				throw new CouponException("STOP! Login Falied! Invalid User or Password!");
			}
		} else {
			throw new CouponException("STOP! Login Falied! Invalid User Type!");
		}
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
