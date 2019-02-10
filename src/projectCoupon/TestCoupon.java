//test
package projectCoupon;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;

import projectCoupon.Company.Company;
import projectCoupon.Company.CompanyFacade;
import projectCoupon.Coupons.Coupon;
import projectCoupon.Coupons.CouponFacade;
import projectCoupon.Coupons.Utile;
import projectCoupon.Coupons.couponType;
import projectCoupon.Customer.Customer;
import projectCoupon.Customer.CustomerFacade;

public class TestCoupon {

	public static void main(String[] args) throws Exception {

		Class.forName(Database.getDriverData());
		Connection con=DriverManager.getConnection(Database.getDBUrl());
	//Database.dropTableifNeeded(con);
	//	Database.createTables(con);
		
		CouponFacade couponFacade = new CouponFacade();
		Coupon a1=new Coupon(1, "super", Utile.getCurrentDate(), Utile.getExpiredDate(), 67, couponType.Camping, "hhh", 78.9, "hhh");
		Coupon a2=new Coupon(2, "pizza", Utile.getCurrentDate(), Utile.getExpiredDate(), 56, couponType.Electricity, "hhh", 887.9, "httt");
		//couponFacade.updateCoupon(a2, "EEE", Utile.getCurrentDate(), Utile.getExpiredDate(), 77, couponType.food, "hello", 89.77, "nnn");
		//couponFacade.insertCoupon(a1);
	//	couponFacade.insertCoupon(a2);
	//	System.out.println(couponFacade.getAllCoupons());
	
	//	System.out.println(couponFacade.getCoupon(2));
//couponFacade.removeCoupon(a1);
//couponFacade.dropTable();
		


		Company p1 = new Company(1, "hp", "123", "aaa");
		Company p2 = new Company(2, "tadiran", "234", "bbb");
		Company p3 = new Company(3, "eci", "333", "ccc");
		Company p4 = new Company(4, "intel", "451", "abc");
//		Company p5 = new Company(5, "linoy", "6293", "aaa");
//		Company p6 = new Company(6, "lea", "7000", "avvv");
		


		CompanyFacade CompanyFacade = new CompanyFacade();
//		CompanyFacade.insertCompany(p1);
//		CompanyFacade.insertCompany(p2);
//		CompanyFacade.insertCompany(p3);
//		CompanyFacade.insertCompany(p4);
	//	CompanyFacade.updateCompany(p3, "tadiran", "111", "mn116@walla.co.il");
		
	//	System.out.println(CompanyFacade.getAllCompany());
	//	System.out.println(CompanyFacade.getCompany(2));
		
	//CompanyFacade.removeCompany(p2);
//     	CompanyFacade.removeCompany(p4);
//     	CompanyFacade.removeCompany(p6);
//     	
//     	System.out.println(CompanyFacade.getAllCompany());
//     	
//     	CompanyFacade.insertCompany(p5);
//		CompanyFacade.insertCompany(p6);
//     	
//		System.out.println(CompanyFacade.getAllCompany());
//		
		
		
		CustomerFacade customerFacade=new CustomerFacade();
		Customer  c1=new Customer(1, "uri", "222");
		Customer  c2=new Customer(2, "Oz", "272");
		Customer  c3=new Customer(3, "Pazit", "777");	
		//System.out.println(customerFacade.getCustomer(1));
		//System.out.println(customerFacade.getAllCustomer());
//customerFacade.updateCustomer(c1, "daniel", "454");
//customerFacade.removeCustomer(c3);
	//	customerFacade.insertCustomer(c1);
	//	customerFacade.insertCustomer(c2);
	//	customerFacade.insertCustomer(c3);
//		 System.out.println(customerFacade.getAllCustomer());
//		
		

     	
     	



	}
}

