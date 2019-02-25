package projectCoupon.Exception;

import projectCoupon.Customer.Customer;

//exception may occur when trying to create a new customer

public class CustomerCreationException extends Exception 
{
	
	private static final long serialVersionUID = 1L;
	private Customer customer;
	
	public CustomerCreationException(Customer customer) {
		this.customer = customer;
	}
	
	@Override
	public String getMessage() {
		return "Creating customer "+this.customer.getCustomerName()+" failed !";
	}
}


