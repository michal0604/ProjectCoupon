package projectCoupon;

import Exception.CouponException;
import projectCoupon.Coupons.CouponDAO;
import projectCoupon.Coupons.CouponDBDAO;

public class DailyCouponExpirationTask implements Runnable {
	
	private CouponDAO couponDAO;
	private int sleepTime;
	private boolean quit = false;
		
	
	public DailyCouponExpirationTask(int sleepTime) throws CouponException {
		couponDAO = new CouponDBDAO();
		this.sleepTime = sleepTime;
	}

	
	@Override
	public void run() {
		while (!this.quit) {
			try {
				CouponDAO.removeExpiredCoupons();
				
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				System.out.println("Interrupted!");
			} catch (CouponException e) {
				System.out.println(e);
			} 
		}
	}

	
	public void stopTask() {
		this.quit = true;
	}

}
