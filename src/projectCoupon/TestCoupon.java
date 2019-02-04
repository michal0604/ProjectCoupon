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
import projectCoupon.Coupons.couponType;

public class TestCoupon {

	public static void main(String[] args) throws Exception {

		Class.forName(Database.getDriverData());
		Connection con=DriverManager.getConnection(Database.getDBUrl());
		Database.dropTableifNeeded(con);
		Database.createTables(con);
		
		SimpleDateFormat Date = new SimpleDateFormat("yyyy-MM-dd");
		CouponFacade couponFacade = new CouponFacade();
		Date d = new Date(0);
		Coupon U1 = new Coupon(1, "HH", "2011-09-04", "2013-11-11", 44, couponType.Camping, "jj", 77.9, "f");
		Coupon U2 = new Coupon(2, "gg", "2011-09-03", "2011-10-18", 77, couponType.Electricity, "hh", 89.7, "tt");
		
		couponFacade.insertCoupon(U1);
		couponFacade.insertCoupon(U2);

		Company p1 = new Company(1, "hp", "123", "aaa");
		Company p2 = new Company(2, "tadiran", "234", "bbb");
		Company p3 = new Company(3, "eci", "333", "ccc");
		Company p4 = new Company(4, "intel", "451", "abc");
		Company p5 = new Company(5, "linoy", "6293", "aaa");
		Company p6 = new Company(6, "lea", "7000", "avvv");
		


		CompanyFacade CompanyFacade = new CompanyFacade();
		CompanyFacade.insertCompany(p1);
		CompanyFacade.insertCompany(p2);
		CompanyFacade.insertCompany(p3);
		CompanyFacade.insertCompany(p4);
		
		System.out.println(CompanyFacade.getAllCompany());
		
		CompanyFacade.removeCompany(p2);
     	CompanyFacade.removeCompany(p4);
     	CompanyFacade.removeCompany(p6);
     	
     	System.out.println(CompanyFacade.getAllCompany());
     	
     	CompanyFacade.insertCompany(p5);
		CompanyFacade.insertCompany(p6);
     	
		System.out.println(CompanyFacade.getAllCompany());
		
		
		
		
//		Customer  c1=new Customer(1, "uri", "222");
//		Customer  c2=new Customer(2, "Oz", "272");
//		Customer  c3=new Customer(3, "Pazit", "777");
//		
		
//		Database.createTables(con);
//		CustomerFacade customerFacade=new CustomerFacade();
//		customerFacade.insertCustomer(c1);
//		customerFacade.insertCustomer(c2);
//		customerFacade.insertCustomer(c3);
//		 System.out.println(customerFacade.getAllCustomer());
//		
		

     	
     	



	}
}

