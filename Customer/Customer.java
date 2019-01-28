
public class Customer {

	private long id;
	private String CUST_NAME;
	private String PASSWORD;

	public Customer() {

	}

	public Customer(long id, String cUST_NAME, String pASSWORD) {
		super();
		this.id = id;
		CUST_NAME = cUST_NAME;
		PASSWORD = pASSWORD;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCUST_NAME() {
		return CUST_NAME;
	}

	public void setCUST_NAME(String cUST_NAME) {
		CUST_NAME = cUST_NAME;
	}

	public String getPASSWORD() {
		return PASSWORD;
	}

	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", CUST_NAME=" + CUST_NAME + ", PASSWORD=" + PASSWORD + "]";
	}
	
	

	
}
