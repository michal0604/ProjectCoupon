package projectCoupon.Customer;

import java.util.Set;

public class CustomerFacade {
	private CustomerDBDAO custDAO = new CustomerDBDAO();
	private Customer customer;

	public CustomerFacade(Customer customer) {
		this.customer = customer;

	}

	public CustomerFacade() {
	}

	public void insertCustomer(Customer customer) throws Exception {
		custDAO.insertCustomer(customer);
	}

	public void removeCustomer(Customer customer) throws Exception {
		custDAO.removeCustomer(customer);
	}

	public void updateCustomer(Customer customer, String newName, String newpassword) throws Exception {
		customer.setCustomerName(newName);
		customer.setPassword(newpassword);
		custDAO.updateCustomer(customer);
	}

	public Customer getCustomer(long id) throws Exception {
		return custDAO.getCustomer(id);
	}

	public Set<Customer> getAllCustomer() throws Exception {
		// ProductDBDAO comDAO=new ProductDBDAO();
		return custDAO.getAllCustomer();
	}

	public void dropTable() throws Exception {
		custDAO.dropTable();

	}


}



