//test
package projectCoupon;

import projectCoupon.beans.Company;
import projectCoupon.beans.Coupon;
import projectCoupon.beans.Customer;
import projectCoupon.beans.CouponType;
import projectCoupon.db.Database;
import projectCoupon.dbdao.CompanyDBDAO;
import projectCoupon.dbdao.Company_CouponDBDAO;
import projectCoupon.dbdao.CouponDBDAO;
import projectCoupon.dbdao.CustomerDBDAO;
import projectCoupon.dbdao.Customer_CouponDBDAO;
import projectCoupon.utils.Utile;

public class TestCoupon {

	public static void main(String[] args) throws Exception {

		Class.forName(Database.getDriverData());
		Database.dropTableifNeeded();
		Database.createTables();

		CompanyDBDAO companyDBDAO = new CompanyDBDAO();
		Company s1 = new Company(1, "HP", "1234", "EB@HH");
		Company s2 = new Company(2, "eci", "222", "ff@kk");
		Company s3 = new Company(3, "toki", "456", "fd@ka");
		companyDBDAO.insertCompany(s1);
		companyDBDAO.insertCompany(s2);
		companyDBDAO.insertCompany(s3);
		System.out.println("insert 3 companies\t\t" + companyDBDAO.getAllCompanys());	     
		System.out.println("get company #1(id =1)\t\t" +companyDBDAO.getCompany(1));
		companyDBDAO.removeCompany(s2);
		System.out.println("removed company #2(id =2)\t" + companyDBDAO.getAllCompanys());
		s3.setCompName("MicroTeck");
		companyDBDAO.updateCompany(s3);
		System.out.println("updated company #3(id =3)\t" + companyDBDAO.getAllCompanys());

		System.out.println("------------------------------------------------------------------");
		CouponDBDAO couponDBDAO = new CouponDBDAO();
		Coupon a1 = new Coupon(1, "pizzaHut", Utile.getCurrentDate(), Utile.getDateAfter(12), 50, CouponType.Camping,"40 shekel for pizza", 40.7, "jj");
		Coupon a2 = new Coupon(2, "shoes", Utile.getCurrentDate(), Utile.getDateAfter(4), 50, CouponType.Sports,"sale on shoes", 540.5, "shoes img");
		Coupon a3 = new Coupon(3, "t-shirt", Utile.getCurrentDate(), Utile.getDateAfter(4), 50, CouponType.Sports,"sale on shirts", 350.0, "t-shirt img");
		couponDBDAO.insertCoupon(a1);
		couponDBDAO.insertCoupon(a2);
		couponDBDAO.insertCoupon(a3);
		System.out.println("insert 3 coupons\t\t" + couponDBDAO.getAllCoupons());
		System.out.println("get coupon #1(id =1)\t\t" +couponDBDAO.getCoupon(1));
		couponDBDAO.removeCoupon(a2);
		System.out.println("removed coupon #2(id =2)\t" + couponDBDAO.getAllCoupons());
		a3.setMessage("spacial price for tonight");
		a3.setAmount(20);
		couponDBDAO.updateCoupon(a3);
		System.out.println("updated coupon #3(id =3)\t" + couponDBDAO.getAllCoupons());
		System.out.println("-------------------------------------------------------------");

		CustomerDBDAO customerDBDAO = new CustomerDBDAO();
		Customer c1 = new Customer(1, "URI", "1234");
		Customer c2 = new Customer(2, "Oz", "2345");
		Customer c3 = new Customer(3, "Pazit", "3456");
		customerDBDAO.insertCustomer(c1);
		customerDBDAO.insertCustomer(c2);
		customerDBDAO.insertCustomer(c3);
		System.out.println(customerDBDAO.getCustomer(2));
		customerDBDAO.updateCustomer(c3);
		System.out.println(customerDBDAO.getAllCustomer());
		customerDBDAO.removeCustomer(c3);
		System.out.println("---------------------------------------------------------");

		Customer_CouponDBDAO customer_CouponDBDAO = new Customer_CouponDBDAO();
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
		Company_CouponDBDAO company_CouponDBDAO = new Company_CouponDBDAO();
		company_CouponDBDAO.insertCompany_Coupon(1, 2);
		company_CouponDBDAO.insertCompany_Coupon(1, 1);

		System.out.println("ALL");
		System.out.println(company_CouponDBDAO.getAllCompany_Coupons());
		System.out.println("BY companyId");
		System.out.println(company_CouponDBDAO.getCouponsByCompanyId(1));
		System.out.println("BY couponId");
		System.out.println(company_CouponDBDAO.getCompanysByCouponId(1));

	}
}
