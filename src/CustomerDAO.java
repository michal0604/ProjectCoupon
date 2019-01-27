


	import java.util.Set;

	public interface CustomerDAO{
		void insertCustomer(Customer Customer) throws Exception;

		void removeCustomer(Customer Customer) throws Exception;

		void updateCustomer(Customer Customer) throws Exception;

		Customer getCustomer(long id) throws Exception;

		Set<Customer> getAllCustomer() throws Exception;
		
		Customer dropTable()throws Exception;

	}

