package projectCoupon.Customer;


	import java.util.List;
import java.util.Set;

import Clients.clientType;
import Exception.CouponException;

	public interface CustomerDAO{
		void insertCustomer(Customer Customer) throws Exception;

		void removeCustomer(long custId) throws Exception;

		void updateCustomer(Customer Customer) throws Exception;
		
		public boolean isCustomerNameExists(String custName) throws CouponException;

		void removeCustomer(Customer Customer) throws Exception;

		List<Customer> getAllCustomer() throws Exception;

		static Customer login(String name, String password, clientType clientType) {
			// TODO Auto-generated method stub
			return null;
		}

		Customer getCustomer(String custName) throws Exception;

		Customer getCustomer(long custId);
		
	}

