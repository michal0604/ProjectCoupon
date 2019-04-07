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

	public void createCompany(Company company) throws Exception {
		if (!isLogedIn) {
			System.out.println("the operation was canceled due to not being loged in");
		}
		if (company != null) {
			String compName = company.getCompName();
			if (compName != null) {
				if (company.getPassword() != null) {
					if (!companyDAO.isCompanyNameExists(compName)) {
							companyDAO.insertCompany(company);
							System.out.println("new company added by admin");
						}
					else {
						System.out.println("create company failed by admin");
					}
					}
					}
			}
		}
	
	public void removeCompany(Company company) throws Exception {
		if (!isLogedIn) {
			System.out.println("the operation was canceled due to not being loged in");
		}
		company_CouponDAO.removeCompany_Coupon(company);
		companyDAO.removeCompany(company);
		System.out.println("remove company by admin success!!");
		

	}

	public void updateCompany(Company Company, String newName, String newpassword, String newEmail) throws Exception{
		if(!isLogedIn) {
			System.out.println("the operation was canceled due to not being loged in");
		}
		Company.setCompName(newName);
		Company.setPassword(newpassword);
		Company.setEmail(newEmail);
		companyDAO.updateCompany(Company);
		System.out.println("update company by admin success!!");
	}

	public Company getCompany(long id) throws Exception{
		if(!isLogedIn) {
			System.out.println("the operation was canceled due to not being loged in");
		}
		
		return companyDAO.getCompany(id);
		
	
	}
	

	public List<Company> getAllCompanies() throws Exception{
		if(!isLogedIn) {
			System.out.println("the operation was canceled due to not being loged in");
		}
		return companyDAO.getAllCompanys();
	}

	public void createCustomer(Customer customer) throws Exception {
		if(!isLogedIn) {
			System.out.println("the operation was canceled due to not being loged in");
		}
		if (customer != null) {
			String custName = customer.getCustomerName();
			if (custName != null) {
				if (customer.getPassword() != null) {
						if (!customerDAO.isCustomerNameExists(custName)) {
								customerDAO.insertCustomer(customer);
								System.out.println("create customer by admin success!!");
							
							}
						else {
							System.out.println("create customer by admin failed");
						}
						}
			}
		}
	}

	public void removeCustomer(Customer customer) throws Exception{
		if(!isLogedIn) {
			System.out.println("the operation was canceled due to not being loged in");
		}
		customer_CouponDAO.removeCustomer_Coupon(customer);
		customerDAO.removeCustomer(customer);
		System.out.println("remove customer by admin success!!");
	}

	public void updateCustomer(Customer customer, String newName, String newpassword) throws Exception{
		if(!isLogedIn) {
			System.out.println("the operation was canceled due to not being loged in");
		}
		customer.setCustomerName(newName);
		customer.setPassword(newpassword);
		customerDAO.updateCustomer(customer);
		System.out.println("update customer by admin success!!");
	}

	public List<Customer> getAllCustomers() throws Exception{
		if(!isLogedIn) {
			System.out.println("the operation was canceled due to not being loged in");
		}
		return customerDAO.getAllCustomers();
	}

	public Customer getCustomer(long id) throws Exception{
		if(!isLogedIn) {
			System.out.println("the operation was canceled due to not being loged in");
		}
		return customerDAO.getCustomer(id);
	}

}