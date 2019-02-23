package projectCoupon.Exception;

import projectCoupon.Customer.Customer;

//exception may occur when trying to remove a customer

public class CustomerRemovalException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Customer customer;
	
	public CustomerRemovalException(Customer customer) {
		this.customer = customer;
	}
	
	@Override
	public String getMessage() {
		return "Removing customer "+this.customer.getCustomerName()+" failed !";
	}

}
