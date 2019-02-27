package projectCoupon.Customer;


	import java.util.List;

import projectCoupon.Clients.clientType;



	public interface CustomerDAO{
		void insertCustomer(Customer Customer) throws Exception;

		void removeCustomer(long custId) throws Exception;

		void updateCustomer(Customer Customer) throws Exception;
		
		public boolean isCustomerNameExists(String custName) throws projectCoupon.Exception.CouponException;

		void removeCustomer(Customer Customer) throws Exception;

		List<Customer> getAllCustomer() throws Exception;

		static Customer login(String name, String password) {
			// TODO Auto-generated method stub
			return null;
		}

		Customer getCustomer(String custName) throws Exception;

		Customer getCustomer(long custId) throws Exception;

		
		
		
	}

