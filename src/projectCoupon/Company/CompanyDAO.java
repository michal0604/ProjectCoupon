package projectCoupon.Company;

import java.util.Set;

public interface CompanyDAO {
	void insertCompany(Company Company) throws Exception;

	void removeCompany(Company Company) throws Exception;

	void updateCompany(Company Company) throws Exception;

	Company getPCompany(long id) throws Exception;

	Set<Company> getAllCompanys() throws Exception;

	Company dropTable() throws Exception;

}
