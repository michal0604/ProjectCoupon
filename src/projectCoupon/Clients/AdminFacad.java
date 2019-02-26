package projectCoupon.Clients;

import java.sql.SQLException;
import java.util.List;

import projectCoupon.Company.Company;
import projectCoupon.Company.CompanyDAO;
import projectCoupon.Company.CompanyDBDAO;
import projectCoupon.Customer.Customer;
import projectCoupon.Customer.CustomerDAO;
import projectCoupon.Customer.CustomerDBDAO;
import projectCoupon.Exception.CouponException;

public class AdminFacad implements CouponClientFacade{
	private static final String ADMINUSERNAME = "admin";
	private static final String ADMINPASSWORD = "1234";
	private CompanyDAO companyDAO;
	private CustomerDAO customerDAO;
	//private CouponDAO couponDAO;


	public AdminFacad() throws CouponException {
		this.companyDAO = new CompanyDBDAO();
		this.customerDAO = new CustomerDBDAO();
		//this.couponDAO = new CouponDBDAO();
	}
	@Override
	public CouponClientFacade login(String name, String password, clientType clientType) throws Exception {
		 if ( name.equals(ADMINUSERNAME) && password.equals(ADMINPASSWORD)) { 
			 return this; 
		 }	
		 else {

		return null;
	}
	}

	
	public void createCompany(Company company) throws CouponException, SQLException {
		if (company != null) {
			String compName = company.getCompName();
			if (compName != null) {
				if (company.getPassword() != null) {
					if (!companyDAO.isCompanyNameExists(compName)) {
						try {
							companyDAO.insertCompany(company);
						} catch (Exception e) {
							//TODO see what is the Exception and fix line
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
	public void insertCompany(Company Company) throws Exception {
		//TODO update the exception to the suited one
		companyDAO.insertCompany(Company);
	}
	
	
	public void removeCompany(long compId) throws CouponException {
		if (compId > 0) {
			companyDAO.removeCompany(compId);
		} else {
			throw new CouponException(" Remove Company is Canceled"); 
		}
	}
	
	
	public void updateCompany(Company Company, String newName, String newpassword,String newEmail) throws Exception {
		Company.setCompName(newName);
		Company.setPassword(newpassword);
		Company.setEmail(newEmail);
		companyDAO.updateCompany(Company);
	}
	
	
	public Company getCompany(long id) throws Exception {
		return companyDAO.getCompany(id);
	}

	
	public List<Company> getAllCompanies() throws Exception  {
		return companyDAO.getAllCompanys();
	}
		
	
	public void createCustomer(Customer customer) throws CouponException {
		if (customer != null) {
			String custName = customer.getCustomerName();
			if (custName != null) {
				if (customer.getPassword() != null) {
					if (!customerDAO.isCustomerNameExists(custName)) {
						try {
							customerDAO.insertCustomer(customer);
						} catch (Exception e) {
							//TODO see what is the Exception and fix line
							throw new CouponException(e.getMessage()); 
						}
					} else {
						throw new CouponException("Customer Already Exists! Create New Customer Canceled!"); 
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
	
	
	public void removeCustomer(Customer customer) throws Exception {
		customerDAO.removeCustomer(customer);
	}
	
	public void updateCustomer(Customer customer, String newName, String newpassword) throws Exception {
		customer.setCustomerName(newName);
		customer.setPassword(newpassword);
		customerDAO.updateCustomer(customer);
	}
	
	
	public List<Customer> getAllCustomers() throws Exception {
		return customerDAO.getAllCustomer();
	}

	public Customer getCustomer(long id) throws Exception {
		return customerDAO.getCustomer(id);
	}
	
	
	


	
}


