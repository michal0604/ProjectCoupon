package projectCoupon;

import java.util.List;

import projectCoupon.Company_Coupon.Company_CouponDAO;
import projectCoupon.Coupons.CouponDAO;
import projectCoupon.Coupons.CouponDBDAO;
import projectCoupon.Customer_Coupon.Customer_CouponDAO;
import projectCoupon.Exception.CouponException;

public class DailyCouponExpirationTask implements Runnable {

	private CouponDAO couponDAO;
	private Customer_CouponDAO customer_CouponDAO;
	private Company_CouponDAO company_CouponDAO;
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
				//TODO 1)removeExpiredCoupons() =>getCouponsWithEndDateBefore(Date);
				//		2) remove indexes from joint tabels
				//		3)remove indexed from Coupon
				List<Long> idsToRemove = couponDAO.removeExpiredCoupons();

				// TO DO COUPONEXCEPTION THROW
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				System.out.println("Interrupted!");
			} catch (CouponException e) {
				System.out.println("remove failed");
			} catch (Exception e) {

			}
		}
	}

	public void stopTask() {
		this.quit = true;
	}

}
