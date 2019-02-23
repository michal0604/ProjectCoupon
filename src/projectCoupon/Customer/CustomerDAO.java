package projectCoupon.Customer;


	import java.util.List;
import java.util.Set;

import Exception.CouponException;

	public interface CustomerDAO{
		void insertCustomer(Customer Customer) throws Exception;

		void removeCustomer(long custId) throws Exception;

		void updateCustomer(Customer Customer) throws Exception;

		Customer getCustomer(long id) throws Exception;

		Set<Customer> getAllCustomers() throws Exception;
		public Customer login(String custName,String password) throws CouponException ;
		
		public boolean isCustomerNameExists(String custName) throws CouponException;
		
	}

