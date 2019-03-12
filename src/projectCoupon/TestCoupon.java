//test
package projectCoupon;

import java.sql.Connection;
import java.sql.DriverManager;

import projectCoupon.DB.Database;
import projectCoupon.DBDAO.CompanyDBDAO;
import projectCoupon.DBDAO.Company_CouponDBDAO;
import projectCoupon.DBDAO.CouponDBDAO;
import projectCoupon.DBDAO.CustomerDBDAO;
import projectCoupon.DBDAO.Customer_CouponDBDAO;
import projectCoupon.beans.Company;
import projectCoupon.beans.Company_Coupon;
import projectCoupon.beans.Coupon;
import projectCoupon.beans.Customer;
import projectCoupon.beans.Customer_Coupon;
import projectCoupon.beans.couponType;
import projectCoupon.facad.AdminFacad;
import projectCoupon.utils.Utile;

public class TestCoupon {

	public static void main(String[] args) throws Exception {

		Class.forName(Database.getDriverData());
		Connection con=DriverManager.getConnection(Database.getDBUrl());
		Database.dropTableifNeeded();
		Database.createTables();
		
		
		CompanyDBDAO companyDBDAO=new CompanyDBDAO();
		Company s1=new Company(1, "HP", "1234", "EB@HH");
		Company s2=new Company(2, "eci", "222", "ff@kk");
		companyDBDAO.insertCompany(s1);
		companyDBDAO.insertCompany(s2);
	
	//	companyDBDAO.removeCompany(s2);

		CouponDBDAO couponDBDAO=new CouponDBDAO();
	  
		Coupon a1=new Coupon(1, "pizzaHut",Utile.getCurrentDate(), Utile.getDateAfter(12), 50, couponType.Camping, "40 shekel for pizza", 40.7, "jj");
		Coupon a2=new Coupon(2, "shoes", Utile.getCurrentDate(), Utile.getDateAfter(4), 50, couponType.Sports, "sale on shoes", 540.5, "shoes img");
	
		couponDBDAO.insertCoupon(a1);
		couponDBDAO.insertCoupon(a2);
	  System.out.println(couponDBDAO.getAllCoupons()); 
	 // couponDBDAO.updateCoupon(a2);??????
	
	 
	 //   System.out.println("------------------------------------------------------------------"); 
	

		
     //	System.out.println("-------------------------------------------------------------");
	
	CustomerDBDAO customerDBDAO=new CustomerDBDAO();
		Customer  c1=new Customer(1, "URI", "1234");
		Customer  c2=new Customer(2, "Oz", "2345");
		Customer  c3=new Customer(3, "Pazit", "3456");	
	customerDBDAO.insertCustomer(c1);
	customerDBDAO.insertCustomer(c2);
	customerDBDAO.insertCustomer(c3);
    System.out.println(customerDBDAO.getAllCustomer());
    //    System.out.println("---------------------------------------------------------");
		
	Customer_CouponDBDAO customer_CouponDBDAO=new Customer_CouponDBDAO();
		Customer_Coupon cnc1= new Customer_Coupon(1,1);
		Customer_Coupon cnc2= new Customer_Coupon(2,1);
		Customer_Coupon cnc3= new Customer_Coupon(2,2);
		
		customer_CouponDBDAO.insertCustomer_Coupon(1, 1);
		customer_CouponDBDAO.insertCustomer_Coupon(2, 1);
		customer_CouponDBDAO.insertCustomer_Coupon(2, 2);
	
		System.out.println("ALL");
		System.out.println(customer_CouponDBDAO.getAllCustomer_Coupon());
     	System.out.println("BY customerId");
		System.out.println(customer_CouponDBDAO.getCouponsByCustomerId(1));
		System.out.println("BY couponId");
		System.out.println(customer_CouponDBDAO.getCustomersByCouponId(2));
		
		System.out.println("---------------------------------------------------------------");
     	Company_CouponDBDAO company_CouponDBDAO=new Company_CouponDBDAO();
     	Company_Coupon b1=new Company_Coupon(1, 2);
     	Company_Coupon b2=new Company_Coupon(1, 1);
     	
     	company_CouponDBDAO.insertCompany_Coupon(1,2);
     	company_CouponDBDAO.insertCompany_Coupon(1,1);
     	System.out.println("ALL");
     	System.out.println(company_CouponDBDAO.getAllCompany_Coupons());
     	System.out.println("BY companyId");
     	System.out.println(company_CouponDBDAO.getCouponsByCompanyId(1));
     	System.out.println("BY couponId");
		System.out.println(company_CouponDBDAO.getCompanysByCouponId(1));
     
   
		

	}
}

