package projectCoupon.Customer;


	import java.util.Set;

import projectCoupon.Exception.CouponException;

	public interface CustomerDAO{
		void insertCustomer(Customer Customer) throws Exception;

		void removeCustomer(long custId) throws Exception;

		void updateCustomer(Customer Customer) throws Exception;

		Customer getCustomer(long id) throws Exception;

		Set<Customer> getAllCustomers() throws Exception;
		public Customer login(String custName,String password) throws CouponException ;
		
		public boolean isCustomerNameExists(String custName) throws CouponException;

		//TODO function empty
		void removeCustomer(Customer Customer) throws Exception;
		
	}

