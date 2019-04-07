package projectCoupon.facad;

import java.sql.SQLException;
import java.util.List;

import projectCoupon.beans.Company;
import projectCoupon.beans.Customer;
import projectCoupon.dao.CompanyDAO;
import projectCoupon.dao.Company_CouponDAO;
import projectCoupon.dao.CustomerDAO;
import projectCoupon.dao.Customer_CouponDAO;
import projectCoupon.dbdao.CompanyDBDAO;
import projectCoupon.dbdao.Company_CouponDBDAO;
import projectCoupon.dbdao.CustomerDBDAO;
import projectCoupon.dbdao.Customer_CouponDBDAO;
import projectCoupon.exception.CompanyException;
import projectCoupon.exception.CouponException;
import projectCoupon.exception.CustomerException;
import projectCoupon.exception.RemoveException;
import projectCoupon.exception.UpdateException;

public class AdminFacad implements CouponClientFacade {
	private static final String ADMIN_USER_NAME = "admin";
	private static final String ADMIN_PASSWORD = "1234";
	private CompanyDAO companyDAO;
	private CustomerDAO customerDAO;
	private Company_CouponDAO company_CouponDAO;
	private Customer_CouponDAO customer_CouponDAO;
	private boolean isLogedIn = false;

	public AdminFacad() throws CouponException {
		this.companyDAO = new CompanyDBDAO();
		this.customerDAO = new CustomerDBDAO();
		this.customer_CouponDAO = new Customer_CouponDBDAO();
		this.company_CouponDAO = new Company_CouponDBDAO();
	}

	public CouponClientFacade login(String name, String password) {
		if (name.equals(AdminFacad.ADMIN_USER_NAME) && password.equals(AdminFacad.ADMIN_PASSWORD)) {
			this.isLogedIn = true;
			return this;
		}

		return null;
	}

	public void createCompany(Company company) throws CouponException, SQLException {
		if (!isLogedIn) {
			throw new CouponException("the operation was canceled due to not being loged in");
		}
		if (company != null) {
			String compName = company.getCompName();
			if (compName != null) {
				if (company.getPassword() != null) {
					if (!companyDAO.isCompanyNameExists(compName)) {
						try {
							companyDAO.insertCompany(company);
						} catch (Exception e) {
							// TODO see what is the Exception and fix line
							throw new CouponException(e.getMessage());
						}
					} else {
						throw new CouponException(" Company Name Already Exists! Create New Company Canceled!");
					}
				} else {
					throw new CouponException(" Password Requiered! Create New Company Canceled!");
				}
			} else {
				throw new CouponException("Company Name Requiered! Create New Company Canceled!");
			}
		} else {
			throw new CouponException("Company Information Not Exist! Create New Company Failed!");
		}
	}

	public void removeCompany(Company company) throws Exception {
		if (!isLogedIn) {
			throw new CouponException("the operation was canceled due to not being loged in");
		}
		company_CouponDAO.removeCompany_Coupon(company);
		companyDAO.removeCompany(company);

	}

	public void updateCompany(Company Company, String newName, String newpassword, String newEmail) throws CompanyException, CouponException{
		if(!isLogedIn) {
			throw new CouponException("the operation was canceled due to not being loged in");
		}
		Company.setCompName(newName);
		Company.setPassword(newpassword);
		Company.setEmail(newEmail);
		companyDAO.updateCompany(Company);
	}

	public Company getCompany(long id) throws CouponException, SQLException{
		if(!isLogedIn) {
			throw new CouponException("the operation was canceled due to not being loged in");
		}
		return companyDAO.getCompany(id);
	}

	public List<Company> getAllCompanies() throws CouponException, SQLException{
		if(!isLogedIn) {
			throw new CouponException("the operation was canceled due to not being loged in");
		}
		return companyDAO.getAllCompanys();
	}

	public void createCustomer(Customer customer) throws CouponException {
	//	if(!isLogedIn) {
		//	throw new CouponException("the operation was canceled due to not being loged in");
		//}
		if (customer != null) {
			String custName = customer.getCustomerName();
			if (custName != null) {
				if (customer.getPassword() != null) {
					try {
						if (!customerDAO.isCustomerNameExists(custName)) {
							try {
								customerDAO.insertCustomer(customer);
							} catch (Exception e) {
								// TODO see what is the Exception and fix line
								throw new CouponException(e.getMessage());
							}
						} else {
							throw new CouponException("Customer Already Exists! Create New Customer Canceled!");
						}
					} catch (CustomerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					throw new CouponException("Password Requiered! Create New Customer Canceled!");
				}
			} else {
				throw new CouponException(" Customer Name Requiered! Create New Customer Canceled!");
			}
		} else {
			throw new CouponException(" Customer Information Not Exists! Create New Customer Canceled!");
		}
	}

	public void removeCustomer(Customer customer) throws RemoveException, CouponException{
		if(!isLogedIn) {
			throw new CouponException("the operation was canceled due to not being loged in");
		}
		customer_CouponDAO.removeCustomer_Coupon(customer);
		customerDAO.removeCustomer(customer);
	}

	public void updateCustomer(Customer customer, String newName, String newpassword) throws UpdateException, CouponException{
		if(!isLogedIn) {
			throw new CouponException("the operation was canceled due to not being loged in");
		}
		customer.setCustomerName(newName);
		customer.setPassword(newpassword);
		customerDAO.updateCustomer(customer);
	}

	public List<Customer> getAllCustomers() throws CustomerException, CouponException{
		if(!isLogedIn) {
			throw new CouponException("the operation was canceled due to not being loged in");
		}
		return customerDAO.getAllCustomers();
	}

	public Customer getCustomer(long id) throws CustomerException, CouponException{
		if(!isLogedIn) {
			throw new CouponException("the operation was canceled due to not being loged in");
		}
		return customerDAO.getCustomer(id);
	}

}