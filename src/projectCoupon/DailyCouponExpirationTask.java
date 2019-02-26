package projectCoupon;

import projectCoupon.Exception.CouponException;

public class DailyCouponExpirationTask implements Runnable {

	// TODO do we need this argument: private CouponDAO couponDAO;
	private int sleepTime;
	private boolean quit = false;

	public DailyCouponExpirationTask(int sleepTime) throws CouponException {
		// TODO do we need this argument: couponDAO = new CouponDBDAO();
		this.sleepTime = sleepTime;
	}

	@Override
	public void run() {
		while (!this.quit) {
			try {
				// TODO figure out how to creat this thing
				// CouponDAO.removeExpiredCoupons();

				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				System.out.println("Interrupted!");
			} // catch (CouponException e) {
//				System.out.println(e);
//			} 
		}
	}

	public void stopTask() {
		this.quit = true;
	}

}
