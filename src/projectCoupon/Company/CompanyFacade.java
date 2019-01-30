package projectCoupon.Company;

import java.util.Set;

public class CompanyFacade {
	private CompanyDBDAO compDAO = new CompanyDBDAO();
	private Company Company;

	public CompanyFacade(Company c) {
		this.Company = c;

	}

	public CompanyFacade() {
	}

	public void insertCompany(Company Company) throws Exception {
		compDAO.insertCompany(Company);
	}

	public void removeCompany(Company Company) throws Exception {
		compDAO.removeCompany(Company);
	}

	public void updateCompany(Company Company, String newName, String newpassword) throws Exception {
		Company.setCOMP_NAME(newName);
		Company.setPASSWORD(newpassword);
		compDAO.updateCompany(Company);
	}

	public Company getCompany() {
		return Company;
	}

	public Set<Company> getAllCompany() throws Exception {
		// ProductDBDAO comDAO=new ProductDBDAO();
		return compDAO.getAllCompanys();
	}

	public void dropTable() throws Exception {
		compDAO.dropTable();

	}


}
