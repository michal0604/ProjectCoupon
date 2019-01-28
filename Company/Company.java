
public class Company {
	private long ID;
	private String COMP_NAME;
	private String PASSWORD;
	private String EMAIL;

	public Company() {

	}

	public Company(long iD, String cOMP_NAME, String pASSWORD, String eMAIL) {
		ID = iD;
		COMP_NAME = cOMP_NAME;
		PASSWORD = pASSWORD;
		EMAIL = eMAIL;
	}

	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public String getCOMP_NAME() {
		return COMP_NAME;
	}

	public void setCOMP_NAME(String cOMP_NAME) {
		COMP_NAME = cOMP_NAME;
	}

	public String getPASSWORD() {
		return PASSWORD;
	}

	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}

	public String getEMAIL() {
		return EMAIL;
	}

	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}

	@Override
	public String toString() {
		return "Company [ID=" + ID + ", COMP_NAME=" + COMP_NAME + ", PASSWORD=" + PASSWORD + ", EMAIL=" + EMAIL + "]";
	}

	

}
