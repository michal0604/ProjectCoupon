package projectCoupon.DAO;

import java.util.List;

import projectCoupon.Exception.CouponException;
import projectCoupon.Exception.CreateException;
import projectCoupon.Exception.CustomerException;
import projectCoupon.Exception.RemoveException;
import projectCoupon.Exception.UpdateException;
import projectCoupon.beans.Customer;

public interface CustomerDAO {
	void insertCustomer(Customer Customer) throws CreateException;

	void removeCustomer(long customerId) throws RemoveException;

	void updateCustomer(Customer Customer) throws UpdateException;

	void removeCustomer(Customer Customer) throws RemoveException;

	List<Customer> getAllCustomer() throws CustomerException;

	public Customer login(String customerName, String password) throws CustomerException;

	Customer getCustomer(String customerName) throws CustomerException;

	Customer getCustomer(long customerId) throws CustomerException;

	boolean isCustomerNameExists(String customerName) throws CouponException;

}
