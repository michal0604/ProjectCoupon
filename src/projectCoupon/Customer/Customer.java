package projectCoupon.Customer;

import java.util.Collection;

import projectCoupon.Coupons.Coupon;

public class Customer {

	private long id;
	private String custName;
	private String password;
	private Collection<Coupon>coupons;

	public Customer() {

	}

	public Customer(long id, String custName, String password) {
		super();
		this.id = id;
		this.custName = custName;
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCustomerName() {
		return custName;
	}

	public void setCustomerName(String customerName) {
		this.custName = customerName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Collection<Coupon>getCoupons(){
		return coupons;
	}
	public void setCoupons(Collection<Coupon>coupons) {
		this.coupons=coupons;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", customerName=" + custName + ", password=" + password + "]";
	}


	

	
}
