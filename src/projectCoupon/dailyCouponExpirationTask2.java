package projectCoupon;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import projectCoupon.Company.Company;
import projectCoupon.Company.CompanyDAO;
import projectCoupon.Company_Coupon.Company_Coupon;
import projectCoupon.Company_Coupon.Company_CouponDAO;
import projectCoupon.Coupons.Coupon;
import projectCoupon.Coupons.CouponDAO;
import projectCoupon.Customer.CustomerDAO;
import projectCoupon.Customer_Coupon.Customer_Coupon;
import projectCoupon.Customer_Coupon.Customer_CouponDAO;

public class dailyCouponExpirationTask2 implements Runnable{
	//
	// Attributes
	//
	private CompanyDAO compDao = null;
	private CustomerDAO custDao = null;
	private CouponDAO couponDao = null;
	private Customer_CouponDAO customer_CouponDAO=null;
	private Company_CouponDAO company_CouponDAO=null;
	private boolean running = true;
	private final static TimeUnit TIMEUNIT = TimeUnit.HOURS;
	private final static int SLEEPTIME = 24;
	

	//
	// Constructors
	//
	public dailyCouponExpirationTask2(CompanyDAO compDao, CustomerDAO custDao, CouponDAO couponDao,Customer_Coupon coupon,Customer_CouponDAO customer_CouponDAO,Company_CouponDAO company_CouponDAO) {
		this.compDao = compDao;
		this.custDao = custDao;
		this.couponDao = couponDao;
		this.company_CouponDAO=company_CouponDAO;
		this.customer_CouponDAO=customer_CouponDAO;
	}

	//
	// Functions
	//
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
				System.out.println(LocalDateTime.now() + " - Daily Task Running...");
				// Loop through every company's coupons and delete expired ones
				for (Company company : compDao.getAllCompanys()){
					for (Coupon coupon : compDao.getCoupons()) {
						if (coupon.getEnd_date().isBefore(LocalDate.now())) {
							deleteCoupon(company, coupon);
						}
					}
				}
			} catch (Exception e) {
				System.out.println(LocalDateTime.now() + " delete coupon failed");
				System.out.println(e.getMessage());
			}
			
		}
	}

	// Delete expired coupon
	private void deleteCoupon(Company company, Coupon coupon) throws Exception {
		// Remove coupon from company
		compDao.removeCoupon(company.getCompanyId(), coupon.getCouponId());
		// Remove coupon from all customers
		for (long custId : couponDao.getCustomersId(coupon)) {
			custDao.removeCoupon(custId, coupon.getCouponId());
		}
		// Delete coupon
		System.out.println(LocalDateTime.now() + " Deleted expired coupon: " + coupon);
		couponDao.deleteCoupon(coupon);
	}

	/**
	 * Gracefully stops the Daily Task
	 */
	public void stop() {
		running = false;
	}
}


