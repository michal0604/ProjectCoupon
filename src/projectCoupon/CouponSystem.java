package projectCoupon;

import java.sql.Connection;

import projectCoupon.Clients.AdminFacad;
import projectCoupon.Clients.CouponClientFacade;
import projectCoupon.Clients.CustomerFacad;
import projectCoupon.Clients.clientType;
import projectCoupon.Exception.CouponException;

public class CouponSystem {
	
	
	private static CouponSystem instance;
	DailyCouponExpirationTask DailyTask;
	Thread thread;
	Connection connection;
	
	/**
	 * Thread timer - 1000*3600*24 is every 24 hours
	 */
	private static final int DAY = 1000*3600*24;
	private static final int SLEEPTIME = 1*DAY; 
	
	
	public CouponSystem() throws CouponException {
		// Activate the daily Coupons Deletion Demon (Thread)
		DailyTask = new DailyCouponExpirationTask(SLEEPTIME);
		thread = new Thread(DailyTask);
		thread.start();
	}
	
	public static CouponSystem getInstance() throws CouponException {
		if (instance==null)instance = new CouponSystem();
		return instance;
	}

	public CouponClientFacade login(String user,String pass,clientType clientType) throws Exception {

		CouponClientFacade couponClientFacade = null;
		
		switch (clientType) {
		case ADMIN:
			couponClientFacade = new AdminFacad();
			break;
		case COMPANY:
			couponClientFacade = new projectCoupon.Clients.CompanyFacade();
			break;
		case CUSTOMER:
			couponClientFacade = new CustomerFacad();
			break;
		default:
			couponClientFacade = null;
		} 
		
		if (couponClientFacade != null) {
			couponClientFacade = couponClientFacade.login(user,pass);
			if (couponClientFacade != null) {
				return couponClientFacade;
			} else {
		throw new CouponException("Login Falied! Invalid User or Password!");
			}
		} else {
			throw new CouponException("Login Falied! Invalid User Type!");
		}
	}
	
	/**
	 * Shutdown system.                          
	 * Close all Connection Pool connections.	
	 * Stop daily coupon expiration task deletion Thread. 
	 **/
	public void shutdown() throws CouponException{

		try {
			ConnectionPool connectionPool = ConnectionPool.getInstance();
			connectionPool.closeAllConnections(connection);
		} catch (CouponException e) {
			throw new CouponException("ERROR! Properly Shut Down Application Failed!");
		}
		DailyTask.stopTask();
	}
}


