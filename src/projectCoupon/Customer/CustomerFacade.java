package projectCoupon.Customer;

import java.util.Set;

public class CustomerFacade {
	private CustomerDBDAO custDAO = new CustomerDBDAO();
	private Customer Customer;

	public CustomerFacade(Customer c) {
		this.Customer = c;

	}

	public CustomerFacade() {
	}

	public void insertCustomer(Customer Customer) throws Exception {
		custDAO.insertCustomer(Customer);
	}

	public void removeCustomer(Customer Customer) throws Exception {
		custDAO.removeCustomer(Customer);
	}

	public void updateCustomer(Customer Customer, String newName, String newpassword) throws Exception {
		Customer.setCUST_NAME(newName);
		Customer.setPASSWORD(newpassword);
		custDAO.updateCustomer(Customer);
	}

	public Customer getCustomer() {
		return Customer;
	}

	public Set<Customer> getAllCustomer() throws Exception {
		// ProductDBDAO comDAO=new ProductDBDAO();
		return custDAO.getAllCustomer();
	}

	public void dropTable() throws Exception {
		custDAO.dropTable();

	}


}



