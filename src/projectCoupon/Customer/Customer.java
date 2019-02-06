package projectCoupon.Customer;

public class Customer {

	private long id;
	private String customerName;
	private String password;

	public Customer() {

	}

	public Customer(long id, String customerName, String password) {
		super();
		this.id = id;
		this.customerName = customerName;
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
		return "Customer [id=" + id + ", customerName=" + customerName + ", password=" + password + "]";
	}


	

	
}
