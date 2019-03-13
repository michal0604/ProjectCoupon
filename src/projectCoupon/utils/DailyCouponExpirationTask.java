package projectCoupon.utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import projectCoupon.beans.Coupon;
import projectCoupon.dao.CouponDAO;
import projectCoupon.dbdao.Company_CouponDBDAO;
import projectCoupon.dbdao.CouponDBDAO;
import projectCoupon.dbdao.Customer_CouponDBDAO;
import projectCoupon.exception.CouponException;

public class DailyCouponExpirationTask implements Runnable {
	
	private CouponDAO coupDAO;
	private int sleepTime;
	//
	// Attributes
	//
	private CouponDBDAO couponDBDAO=new CouponDBDAO();
	private Customer_CouponDBDAO customer_CouponDBDAO=new Customer_CouponDBDAO();
	private Company_CouponDBDAO company_CouponDBDAO=new Company_CouponDBDAO();
	private boolean running = true;
	private final static TimeUnit TIMEUNIT = TimeUnit.HOURS;
	private static int SLEEPTIME = 24;
	private Thread dailyTaskThread;
	

	public DailyCouponExpirationTask(int SLEEPTIME  ) throws CouponException{
		coupDAO=new CouponDBDAO();
		this.SLEEPTIME=SLEEPTIME;
		

	}

	//
	// Functions
	//
	
	public void startTask()throws CouponException{
		try {
			dailyTaskThread=new Thread(this);
		    dailyTaskThread.start();
		    System.out.println("daily task is start");
			
		} catch (Exception e) {
			throw new CouponException("daily task failed");
		}
		
		
	}
	
	
	@Override
	public void run()  {
		while (running) {
			try {
				TIMEUNIT.sleep(SLEEPTIME);
			} catch (InterruptedException e) {
				System.out.println("bye bye");
				System.exit(0);
			}
			try {
				System.out.println(LocalDateTime.now() + " - Daily Task Running...");
				List<Coupon>allCoupons=new ArrayList<Coupon>();
				Iterator<Coupon>itr=allCoupons.iterator();
				while(itr.hasNext()) {
					Coupon current=itr.next();
					if (current.getStart_date().compareTo(current.getEnd_date())<0){
					customer_CouponDBDAO.removeCustomer_CouponByCoupId(current.getCouponId());
					company_CouponDBDAO.removeCompany_CouponByCouponId(current.getCouponId());
					couponDBDAO.removeCoupon(current);
						}
					}
				}
			 catch (Exception e) {
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
