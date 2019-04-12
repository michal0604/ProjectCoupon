package projectCoupon.dao;

import java.util.List;

import projectCoupon.beans.Coupon;
import projectCoupon.beans.Customer;
import projectCoupon.exception.CompanyException;
import projectCoupon.exception.CouponException;
import projectCoupon.exception.CreateException;
import projectCoupon.exception.CustomerException;
import projectCoupon.exception.RemoveException;
import projectCoupon.exception.UpdateException;

public interface CustomerDAO {
	void insertCustomer(Customer Customer) throws CreateException;

	void updateCustomer(Customer Customer) throws UpdateException;

	void removeCustomer(Customer Customer) throws RemoveException;

	List<Customer> getAllCustomers() throws CustomerException;

	public Customer login(String customerName, String password) throws CustomerException;

	Customer getCustomer(String customerName) throws CustomerException;

	Customer getCustomer(long customerId) throws CustomerException;

	boolean isCustomerNameExists(String customerName) throws CouponException, CustomerException;
	
	List<Coupon> getAllCoupons(long customerId) throws CouponException;
	

}
