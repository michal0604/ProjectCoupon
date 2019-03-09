//test
package projectCoupon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Date;

import javax.rmi.CORBA.Util;

import org.apache.derby.client.am.Utils;

import projectCoupon.Clients.CompanyFacade;
import projectCoupon.Clients.Utile;
import projectCoupon.Company_Coupon.Company_CouponFacad;
import projectCoupon.Coupons.Coupon;
import projectCoupon.Coupons.CouponDAO;
import projectCoupon.Coupons.couponType;
import projectCoupon.Customer_Coupon.Customer_Coupon;
import projectCoupon.Customer_Coupon.Customer_CouponFacad;

public class TestCoupon {

	public static void main(String[] args) throws Exception {

		Class.forName(Database.getDriverData());
		Connection con=DriverManager.getConnection(Database.getDBUrl());
		Database.dropTableifNeeded(con);
		Database.createTables(con);

	//    CouponFacade couponFacade = new CouponFacade();
	//	Coupon a1=new Coupon(1, "pizzaHut", Utile.getCurrentDate(), Utile.getExpiredDate(), 50, couponType.food, "40 shekel for pizza", 40.7, "C:\\Users\\testlab\\Desktop\\תמונות של פרוייקט");
	//	Coupon a2=new Coupon(2, "shoes", Utile.getCurrentDate(), Utile.getExpiredDate(), 50, couponType.Sports, "sale on shoes", 540.5, "shoes img");
	
	//	couponFacade.insertCoupon(a1);
	  //  couponFacade.insertCoupon(a2);
	//    System.out.println(couponFacade.getAllCoupons());
	//    System.out.println(couponFacade.getCoupon(1));
	  //  couponFacade.updateCoupon(a1, "pizzaHut", Utile.getCurrentDate(), Utile.getExpiredDate(), 89, couponType.food, "piza", 78.9, "pic");
	//    System.out.println(couponFacade.getAllCoupons());
	   // couponFacade.removeCoupon(a2);
	   // System.out.println(couponFacade.getAllCoupons());
	 //   System.out.println("------------------------------------------------------------------"); 
	//	Company p1 = new Company(1, "HP", "HP-1234", "HP@co.il");
	//	Company p2 = new Company(2, "ECI", "ECI1-234", "ECI@co.il");
	//	Company p3 = new Company(4, "Intel", "INTEL-1234", "INTEL@co.il");

	//	CompanyFacade CompanyFacade = new CompanyFacade();
	//	CompanyFacade.insertCompany(p1);
	//	CompanyFacade.insertCompany(p2);
	//	CompanyFacade.insertCompany(p3);
	//	System.out.println(CompanyFacade.getAllCompany());
	//	System.out.println(CompanyFacade.getCompany(1));
	//	CompanyFacade.updateCompany(p3, "AMDOCS", "AMDOCS-1234", "AMDOCS@co.il");
		
     //	System.out.println("-------------------------------------------------------------");
	
	//CustomerFacade customerFacade=new CustomerFacade();
	//	Customer  c1=new Customer(1, "URI", "1234");
	//	Customer  c2=new Customer(2, "Oz", "2345");
	//	Customer  c3=new Customer(3, "Pazit", "3456");	
	//	customerFacade.insertCustomer(c1);
	//	customerFacade.insertCustomer(c2);
	//	customerFacade.insertCustomer(c3);
    //    System.out.println(customerFacade.getAllCustomer());
    //    System.out.println(customerFacade.getCustomer(1));
    //    customerFacade.updateCustomer(c3, "Yossi", "6789");
    //    System.out.println("---------------------------------------------------------");
		
	//	Customer_CouponFacad customer_CouponFacad=new Customer_CouponFacad();
	//	Customer_Coupon cnc1= new Customer_Coupon(1,1);
	//	Customer_Coupon cnc2= new Customer_Coupon(2,1);
	//	Customer_Coupon cnc3= new Customer_Coupon(2,2);
		
	//	customer_CouponFacad.insertCustomer_Coupon(cnc1);
	//	customer_CouponFacad.insertCustomer_Coupon(cnc2);
	//	customer_CouponFacad.insertCustomer_Coupon(cnc3);
		/*
		System.out.println("ALL");
		System.out.println(customer_CouponFacad.getAllCustomer_Coupon());
     	System.out.println("BY customerId");
		System.out.println(customer_CouponFacad.getCouponsByCustomerId(1));
		System.out.println("BY couponId");
		System.out.println(customer_CouponFacad.getCustomersByCouponId(2));
		
		System.out.println("---------------------------------------------------------------");
     	Company_CouponFacad company_CouponFacad=new Company_CouponFacad();
     	//Company_Coupon b1=new Company_Coupon(1, 2);
    // 	Company_Coupon b2=new Company_Coupon(1, 1);
     	
     	company_CouponFacad.insertCompany_Coupon(1,2);
     	company_CouponFacad.insertCompany_Coupon(1,1);
     	System.out.println("ALL");
     	System.out.println(company_CouponFacad.getAllCCompany_Coupon());
     	System.out.println("BY companyId");
     	System.out.println(company_CouponFacad.getCouponsByCompanyId(1));
     	System.out.println("BY couponId");
		System.out.println(company_CouponFacad.getCompanysByCouponId(2));
     */
   
		

	}
}

