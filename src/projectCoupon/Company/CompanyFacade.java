package projectCoupon.Company;

import java.util.List;

public class CompanyFacade {
	private CompanyDBDAO compDAO = new CompanyDBDAO();
	private Company Company;

	/**
	 * cTor that receive a company object 
	 * 
	 * @param company 
	 */
	public CompanyFacade(Company company) {
		this.Company = company;

	}

	/**
	 *  Empty cTOr of the class
	 */
	public CompanyFacade() {}

	/**
	 * this method received a new Company object to add to the data storage 
	 * 
	 * @param Company - company to be added
	 * @throws Exception - when company creation fails
	 */
	public void insertCompany(Company Company) throws Exception {
		//TODO update the exception to the suited one
		compDAO.insertCompany(Company);
	}

	/**
	 * this method received a company object that supposed to be removed from the 
	 * data storage
	 * 
	 * @param Company - company that should be removed
	 * @throws Exception - if there is a DB error or the operation failed.
	 */
	public void removeCompany(Company Company) throws Exception {
		//TODO update the exception to the suited one
		compDAO.removeCompany(Company);
	}

	/**
	 * the method updates the company's name and password  
	 * 
	 * @param Company - company data that should be updated
	 * @param newName - new name of the company
	 * @param newpassword - new password of the company
	 * @throws Exception - when update operation fails
	 */
	public void updateCompany(Company Company, String newName, String newpassword,String newEmail) throws Exception {
		//TODO update the exception to the suited one
		Company.setCompName(newName);
		Company.setPassword(newpassword);
		Company.setEmail(newEmail);
		compDAO.updateCompany(Company);
	}

	/**
	 * @return the company
	 * @throws Exception 
	 */
	//TODO 
	public Company getCompany(long id) throws Exception {
		return compDAO.getCompany(id);
	}

	/**
	 * @return all the companies in the storage 
	 * @throws Exception when the retrieval operation fails
	 */
	public List<Company> getAllCompany() throws Exception {
		//TODO update the exception to the suited one
		// ProductDBDAO comDAO=new ProductDBDAO();
		return compDAO.getAllCompanys();
	}

	/**
	 * @throws Exception
	 */
	public void dropTable() throws Exception {
		//TODO is this suppose to be hear?
		compDAO.dropTable();
	}

}
