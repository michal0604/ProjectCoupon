package projectCoupon;

import java.util.ArrayList;
import java.util.List;

import projectCoupon.beans.Company;
import projectCoupon.beans.Coupon;
import projectCoupon.beans.Customer;
import projectCoupon.beans.couponType;
import projectCoupon.db.Database;
import projectCoupon.exception.CouponException;
import projectCoupon.facad.AdminFacad;
import projectCoupon.facad.CompanyFacade;
import projectCoupon.facad.CouponClientFacade;
import projectCoupon.utils.ClientType;
import projectCoupon.utils.CouponSystem;
import projectCoupon.utils.Utile;

public class test2 {

	public static void main(String[] args) throws Exception {
		List<Company> companies = new ArrayList<Company>();
		List<Customer> customers = new ArrayList<Customer>();
		List<Coupon> coupons = new ArrayList<Coupon>();
		CouponClientFacade facade;
	
			CouponSystem couponSystem=CouponSystem.getInstance();
			Utile utile=new Utile();
			
			String line = "---------------------------------------------------------------\n";
			
			/*****************************
			 * Administrator Facade test *
			 *****************************/	
			

			// Login as Admin
			facade = couponSystem.login("admin","1234",ClientType.ADMIN);
			if (facade instanceof AdminFacad) {

				System.out.println("========  Login(admin, 1234, AdminFacade) ========\n");
				Database.dropTableifNeeded();
				Database.createTables();
				
				// Create new 5 companies.
				Company company01 = new Company(1, "PIZZA-HUT", "1002", "holmesplaceþ@gmail.com");
				Company company02 = new Company(2,"HOLMCE-PLACE","1002","holmesplaceþ@gmail.com"); 
				Company company03 = new Company(3,"BUG","1003","bug@bug.com"); 
				Company company04 = new Company(4,"ISSTA","1004","issta@issta.com"); 
				Company company05 = new Company(5,"ILANS","1005","ilans@yahoo.com"); 
			    
				AdminFacad adminFacad=new AdminFacad();
			    adminFacad.createCompany(company01);
			    adminFacad.createCompany(company02);
			    adminFacad.createCompany(company03);
			    adminFacad.createCompany(company04);
			    adminFacad.createCompany(company05);
				
				// Show all companies (After creating new companies - before update).
				companies = adminFacad.getAllCompanies();
				System.out.println("After creating new companies - Show all new companies\n" + line + companies.toString());				

				// Update Companies (Id=5,2,3).
				// Update company RIKUSHET with email
				Company company06 = new Company();
			//	adminFacad.updateCompany(company06, "superfarm", "3333", "farm@gmail.com");

				// Show One updated company id=5
				Company company07 = adminFacad.getCompany(5);
				System.out.println("Show One updated company\n" + company07.toString());
			
				Company company08 = new Company();
			//	adminFacad.updateCompany(company02, "tadiran", "444", "tadiran@walla.com");  // Update company HOLMESPLACEþ with password

				
				
				// Show all companies (after update).
				companies = adminFacad.getAllCompanies();
				System.out.println("Show all companies (after update)" + companies.toString());
			
				// Add new 5 customers
				Customer customer01 = new Customer(1, "michal", "223");
				Customer customer02 = new Customer(2,"Shiran","2002"); 
				Customer customer03 = new Customer(3,"Hadar","2003");
				Customer customer04 = new Customer(4,"Karin","2004"); 
				Customer customer05 = new Customer(5,"Gili","2005"); 
			    
				adminFacad.createCustomer(customer01); 
				adminFacad.createCustomer(customer02); 
				adminFacad.createCustomer(customer03); 
				adminFacad.createCustomer(customer04); 
				adminFacad.createCustomer(customer05); 

				// Show all customers (After creating new customers - before update).
				customers = adminFacad.getAllCustomers();
				System.out.println("After creating new customers - Show all customers (before update)\n" + customers.toString());
			
				// Update 2 Customers (id=3,2).
				Customer customer06 = new Customer();
			//	adminFacad.updateCustomer(customer02, "dany", "444");

				// Show One updated customer.
				Customer customer07 = adminFacad.getCustomer(4);
				System.out.println("Show One updated customer\n" + customer07.toString());
				

			
				//adminFacad.updateCustomer(customer05, "shely", "233"); 

				// Show One updated customer
				Customer customer09 = adminFacad.getCustomer(2);
				System.out.println("Show One updated customer" + customer09.toString());
				
				// Show all customers (after update).
				customers = adminFacad.getAllCustomers();
				System.out.println("Show all customers (after update)" + customers.toString());
	
				
				/*****************************************************************
				 * Company Facade TEST: id: 1, user: "PIZZAHUT", password:"1001" *
				 *****************************************************************/
				facade = couponSystem.login("PIZZAHUT","1001",ClientType.COMPANY);
				if (facade instanceof CompanyFacade) {
					System.out.println("========  Login(PIZZAHUT, 1001, CompanyFacade) ========\n");

		
					Coupon coupon01 = new Coupon(1,"Deal-01",Utile.getCurrentDate(),Utile.getDateAfter(10), 5, couponType.food, "Best Deal",  69.90, "http//www.pizzahatCoupon.co.il/?1"); 
					Coupon coupon02 = new Coupon(2,"Deal-02", Utile.getCurrentDate(),Utile.getDateAfter(12), 4, couponType.Resturans, "Best Deal",  99.90, "http//www.pizzahatCoupon.co.il/?2"); 
					Coupon coupon03 = new Coupon(3,"Deal-03", Utile.getCurrentDate(),Utile.getDateAfter(2), 10, couponType.food, "Best Deal", 50.90, "http//www.pizzahatCoupon.co.il/?3"); 
				    
					((CompanyFacade) facade).createCoupon(coupon01); 
					((CompanyFacade) facade).createCoupon(coupon02); 
					((CompanyFacade) facade).createCoupon(coupon03); 

					// Show all new PIZZAHUT coupons (before update).
					coupons = ((CompanyFacade) facade).getCoupons();
					System.out.println("After creating 3 new PIZZAHUT coupons - Show all company coupons (before update)" + coupons.toString());
					
					// TO DO Update Coupons
					
					

					Coupon coupon05 = ((CompanyFacade) facade).getCoupon(3);
					System.out.println("Show One PIZZAHUT updated coupon (3)" + coupon05.toString());
				
					// getCouponsByType(CouponType.FOOD)
					coupons = ((CompanyFacade) facade).getCouponsByType(couponType.food);
					System.out.println("View all PIZZAHUT company coupons by Type FOOD" + coupons.toString());
				
		
					// getCouponsByMaxCouponPrice(100 nis)
					coupons = ((CompanyFacade) facade).getCouponsByMaxCouponPrice(100.00);
					System.out.println("View all PIZZAHUT company Coupons by Max Price 100 nis:\n" + coupons.toString());

					//TO DO getCouponsByMaxCouponDate(X days from today)
					

				}
			}
	}
}
			