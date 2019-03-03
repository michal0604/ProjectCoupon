package projectCoupon.Customer;

import java.util.List;

import projectCoupon.Exception.CouponException;
import projectCoupon.Exception.CreateException;
import projectCoupon.Exception.CustomerException;
import projectCoupon.Exception.RemoveException;
import projectCoupon.Exception.UpdateException;

public interface CustomerDAO {
	void insertCustomer(Customer Customer) throws CreateException;

	void removeCustomer(long custId) throws RemoveException;

	void updateCustomer(Customer Customer) throws UpdateException;

	void removeCustomer(Customer Customer) throws RemoveException;

	List<Customer> getAllCustomer() throws CustomerException;

	public Customer login(String name, String password) throws CustomerException;

	Customer getCustomer(String custName) throws CustomerException;

	Customer getCustomer(long cusomerId) throws CustomerException;

	boolean isCustomerNameExists(String custName) throws CouponException;

}
