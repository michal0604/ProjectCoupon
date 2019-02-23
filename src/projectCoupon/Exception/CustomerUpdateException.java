package projectCoupon.Exception;

import projectCoupon.Customer.Customer;

//exception may occur when trying to update customer's details

public class CustomerUpdateException extends Exception {

	private static final long serialVersionUID = 1L;
private Customer customer;
	
	public CustomerUpdateException(Customer customer) {
		this.customer = customer;
	}
	
	@Override
	public String getMessage() {
		return "Updating customer "+this.customer.getCustomerName()+" failed !";
	}

}
