package projectCoupon.Customer;

import java.util.List;
import java.util.Set;

import Clients.CouponClientFacade;
import Clients.clientType;
import Exception.CouponException;

public class CustomerFacade implements CouponClientFacade {
	private CustomerDBDAO custDAO=new CustomerDBDAO() ;
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

	public List<Customer> getAllCustomer() throws Exception {
		// ProductDBDAO comDAO=new ProductDBDAO();
		return custDAO.getAllCustomer();
	}


	@Override
	public CouponClientFacade login(String name, String password, clientType clientType) {
		// TODO Auto-generated method stub
		return null;
	}

	


}



