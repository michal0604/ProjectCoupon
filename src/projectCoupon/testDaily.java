package projectCoupon;

import java.util.List;
import java.util.concurrent.TimeUnit;

import projectCoupon.beans.Company;
import projectCoupon.beans.Coupon;
import projectCoupon.beans.CouponType;
import projectCoupon.db.Database;
import projectCoupon.exception.CouponException;
import projectCoupon.facad.AdminFacad;
import projectCoupon.facad.CompanyFacade;
import projectCoupon.facad.CouponClientFacade;
import projectCoupon.utils.ClientType;
import projectCoupon.utils.CouponSystem;
import projectCoupon.utils.Utile;

public class testDaily {
	private static final TimeUnit TIMEUNIT = TimeUnit.MILLISECONDS;

	public static void main(String[] args) {
		try {
			CouponSystem couponSystem = CouponSystem.getInstance();
			couponSystem.setDebugMode(true);
			CouponClientFacade facade = CouponSystem.login("admin", "1234", ClientType.ADMIN);
			if (facade instanceof AdminFacad) {
				System.out.println("Prepering Test\ndroping and recreating DB tables\n");
				Database.dropTableifNeeded();
				Database.createTables();
				Company company01 = new Company(1, "PIZZAHUT", "111", "pizzahut@gmail.com");
				
				AdminFacad adminFacad = (AdminFacad) facade;
				adminFacad.createCompany(company01);
				System.out.println("\nAdding a company\n" + adminFacad.getAllCompanies().toString());
				
				facade = CouponSystem.login("PIZZAHUT", "111", ClientType.COMPANY);
				if (facade instanceof CompanyFacade) {
					System.out.println("\nLogin in to the Company\n");
					Coupon coupon;
					for(int i = 0 ; i < 5 ; i++ ) {
						coupon = new Coupon(i, "Coupon#"+i, Utile.getCurrentDate(), Utile.getDateAfter(i), i, CouponType.Resturans," this coupon should disapear after "+(i+1)+" Cycles",1+i*2.0,"");
						((CompanyFacade) facade).createCoupon(coupon);
					}
					for(int i = 0 ; i < 5 ; i++ ) {
						TIMEUNIT.sleep(15000);
						List<Coupon> coupons = ((CompanyFacade) facade).getCoupons();
						System.out.println("there are total of "+coupons.size()+" coupons in the system at the moment");
						System.out.println("Show all company coupons\n"+ ((CompanyFacade) facade).getCoupons().toString());
						
					}
					couponSystem.shutdown();
				}
			}
			
		} catch (CouponException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
