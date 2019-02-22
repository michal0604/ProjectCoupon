package projectCoupon;

import Clients.CouponClientFacade;
import Clients.clientType;
import Exception.CouponException;

public class CouponSystem {
	
	
	private static CouponSystem instance;
	DailyCouponExpirationTask DailyTask;
	Thread thread;
	
	/**
	 * Thread timer - 1000*3600*24 is every 24 hours
	 */
	private static final int DAY = 1000*3600*24;
	private static final int SLEEPTIME = 1*DAY; 
	
	
	/**
	 * Constructor: Start Daily Coupon Expiration Task thread.
	 * @throws CouponException
 	 **/
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

	public CouponClientFacade login(String user,String pass,clientType clientType) throws CouponException {

		CouponClientFacade couponClientFacade = null;
		
		switch (clientType) {
		case ADMIN:
			couponClientFacade = new AdminFacade();
			break;
		case COMPANY:
			couponClientFacade = new CompanyFacade();
			break;
		case CUSTOMER:
			couponClientFacade = new CustomerFacade();
			break;
		default:
			couponClientFacade = null;
		} 
		
		if (couponClientFacade != null) {
			couponClientFacade = couponClientFacade.login(user,pass);
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


