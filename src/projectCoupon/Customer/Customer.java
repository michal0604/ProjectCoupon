package projectCoupon.Customer;

public class Customer {

	private long customerId;
	private String customerName;
	private String password;

	public Customer() {

	}

	public Customer(long customerId, String customerName, String password) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.password = password;
	}

	

	public long getCustomerId() {
		return customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Customer [customerID=" + customerId+ ", customerName=" + customerName + ", password=" + password + "]";
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	

	
	}


	

	

