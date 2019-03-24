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
import projectCoupon.exception.CouponException;
import projectCoupon.utils.ClientType;

    public class AdminFacad implements CouponClientFacade{
	private static final String ADMIN_USER_NAME = "admin";
	private static final String ADMIN_PASSWORD = "1234";
	private CompanyDAO companyDAO;
	private CustomerDAO customerDAO;
	private Company_CouponDAO company_CouponDAO;
	private Customer_CouponDAO customer_CouponDAO;



	public AdminFacad() throws CouponException{
		this.companyDAO = new CompanyDBDAO();
		this.customerDAO = new CustomerDBDAO();
		this.customer_CouponDAO=new Customer_CouponDBDAO();
		this.company_CouponDAO=new Company_CouponDBDAO();
	}
	
	@Override
	public CouponClientFacade login(String name, String password, ClientType clientType) throws Exception {
		 if ( name.equals(AdminFacad.ADMIN_USER_NAME) && password.equals(AdminFacad.ADMIN_PASSWORD)) { 
			 return this; 
		 }	

		return null;
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
	
	
	
	public void removeCompany(Company company) throws Exception {
		    company_CouponDAO.removeCompany_Coupon(company);
			companyDAO.removeCompany(company);
			
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
		customer_CouponDAO.removeCustomer_Coupon(customer);
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

	
	

	

	
	
	


	



