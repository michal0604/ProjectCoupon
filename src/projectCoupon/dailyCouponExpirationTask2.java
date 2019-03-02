package projectCoupon;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.sql.PooledConnection;

import projectCoupon.Company.Company;
import projectCoupon.Company.CompanyDAO;
import projectCoupon.Company_Coupon.Company_Coupon;
import projectCoupon.Company_Coupon.Company_CouponDAO;
import projectCoupon.Company_Coupon.Company_CouponDBDAO;
import projectCoupon.Coupons.Coupon;
import projectCoupon.Coupons.CouponDAO;
import projectCoupon.Coupons.CouponDBDAO;
import projectCoupon.Customer.CustomerDAO;
import projectCoupon.Customer_Coupon.Customer_Coupon;
import projectCoupon.Customer_Coupon.Customer_CouponDAO;
import projectCoupon.Customer_Coupon.Customer_CouponDBDAO;
import projectCoupon.Exception.CouponException;

public class dailyCouponExpirationTask2 implements Runnable {
	//
	// Attributes
	//
	private CouponDBDAO couponDBDAO=new CouponDBDAO();
	private Customer_CouponDBDAO customer_CouponDBDAO=new Customer_CouponDBDAO();
	private Company_CouponDBDAO company_CouponDBDAO=new Company_CouponDBDAO();
	private boolean running = true;
	private final static TimeUnit TIMEUNIT = TimeUnit.HOURS;
	private final static int SLEEPTIME = 24;
	private Thread dailyTaskThread;
	

	public dailyCouponExpirationTask2( ) throws CouponException{

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
					if (current.getEnd_date().isBefore(LocalDate.now())||current.getEnd_date().equals(LocalDate.now())){
					customer_CouponDBDAO.removeCustomer_Coupon(current);
					company_CouponDBDAO.removeCompany_CouponByCoupon(current);
					couponDBDAO.removeExpiredCoupons();
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
