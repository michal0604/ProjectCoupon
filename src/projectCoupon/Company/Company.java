//delete
package projectCoupon.Company;

/**
 * this calls is for an object representing Company data
 * 
 * @author Eivy & Michal
 *
 */
public class Company {
	
	private long id;
	private String compName;
	private String password;
	private String email;
	//private List<Coupon> coupons;

	/**
	 *  empty cTor for Company
	 */
	public Company() {}

	/**
	 * full cTor for the company object
	 * 
	 * @param id = company id
	 * @param compName = company name
	 * @param password = company password 
	 * @param email = company email
	 */
	public Company(long id, String compName, String password, String email) {
		this.id = id;
		this.compName = compName;
		this.password = password;
		this.email = email;
	}

	/**
	 * @return the id of the company.
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id  Sets the id of company.
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the company name.
	 */
	public String getCompName() {
		return compName;
	}

	/**
	 * @param compName set the company name.
	 */
	public void setCompName(String compName) {
		this.compName = compName;
	}

	/**
	 * @return the company password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password sets the password for the company.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the company email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email set the companey's email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "Company (id=" + id + ", compName=" + compName + ", password=" + password + ", email=" + email + ")";
	}

	/**
	 * @return the list of coupons endorsed by the company
	 */
//	public List<Coupon> getCoupons() {
//		return coupons;
//	}

	/**
	 * @param coupons - sets list of coupons endorsed by the company
	 */
//	public void setCoupons(List<Coupon> coupons) {
//		this.coupons = coupons;
//	}


}
