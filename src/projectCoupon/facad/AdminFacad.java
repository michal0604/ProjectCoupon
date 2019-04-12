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
import projectCoupon.exception.CreateException;
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

	public void createCompany(Company company) throws CouponException {
		if (!isLogedIn) {
			throw new CouponException("the operation was canceled due to not being loged in");
		}
		if (company != null) {
			String compName = company.getCompName();
			if (compName != null) {
				if (company.getPassword() != null) {
					try {
						if (!companyDAO.isCompanyNameExists(compName)) {
							companyDAO.insertCompany(company);

						} else {
							throw new CouponException("create company failed by admin");
						}
					} catch (SQLException e) {
						throw new CouponException("create company failed by admin");
					}
				}
			}
		}
	}

	public void removeCompany(Company company) throws CouponException {
		if (!isLogedIn) {
			throw new CouponException("the operation was canceled due to not being loged in");
		}
		try {
			List<Long> coupoIdList = company_CouponDAO.getCouponsByCompanyId(company.getCompanyId());
			company_CouponDAO.removeCompany_Coupon(company);
			for (Long couponId : coupoIdList) {
				customer_CouponDAO.removeCustomer_CouponByCoupId(couponId);
			}
			companyDAO.removeCompany(company);
		} catch (RemoveException e) {
			throw new CouponException("remove company_coupon failed");
		} catch (SQLException e) {
			throw new CouponException("remove company_coupon failed");
		}

	}

	public void updateCompany(Company Company, String newpassword, String newEmail) throws CouponException {
		if (!isLogedIn) {
			throw new CouponException("the operation was canceled due to not being loged in");
		}

		Company.setPassword(newpassword);
		Company.setEmail(newEmail);
		try {
			companyDAO.updateCompany(Company);
		} catch (CompanyException e) {
			throw new CouponException("update company by admin failed");
		}

	}

	public Company getCompany(long id) throws CouponException {
		if (!isLogedIn) {
			throw new CouponException("the operation was canceled due to not being loged in");
		}

		try {
			return companyDAO.getCompany(id);
		} catch (SQLException e) {
			throw new CouponException("get company by admin failed");
		}

	}

	public List<Company> getAllCompanies() throws CouponException {
		if (!isLogedIn) {
			throw new CouponException("the operation was canceled due to not being loged in");
		}
		try {
			return companyDAO.getAllCompanys();
		} catch (SQLException e) {
			throw new CouponException("get all company by admin failed");

		}
	}

	public void createCustomer(Customer customer) throws CouponException {
		if (!isLogedIn) {
			throw new CouponException("the operation was canceled due to not being loged in");
		}
		if (customer != null) {
			String custName = customer.getCustomerName();
			if (custName != null) {
				if (customer.getPassword() != null) {
					try {
						if (!customerDAO.isCustomerNameExists(custName)) {
							customerDAO.insertCustomer(customer);
						}
					} catch (CustomerException e) {
						throw new CouponException("create customer by admin failed");

					} catch (CreateException e) {
						throw new CouponException("create customer by admin failed");
					}
				}
			}
		}
	}

	public void removeCustomer(Customer customer) throws CouponException {
		if (!isLogedIn) {
			throw new CouponException("the operation was canceled due to not being loged in");
		}
		try {
			customer_CouponDAO.removeCustomer_Coupon(customer);
		} catch (RemoveException e) {
			throw new CouponException("remove customer_coupon by admin failed");
		}
		try {
			customerDAO.removeCustomer(customer);
		} catch (RemoveException e) {
			throw new CouponException("remove customer by admin failed");
		}

	}

	public void updateCustomer(Customer customer, String newpassword) throws CouponException {
		if (!isLogedIn) {
			throw new CouponException("the operation was canceled due to not being loged in");
		}

		customer.setPassword(newpassword);
		try {
			customerDAO.updateCustomer(customer);
		} catch (UpdateException e) {
			throw new CouponException("update customer by admin failed");
		}

	}

	public List<Customer> getAllCustomers() throws CouponException {
		if (!isLogedIn) {
			throw new CouponException("the operation was canceled due to not being loged in");
		}
		try {
			return customerDAO.getAllCustomers();
		} catch (CustomerException e) {
			throw new CouponException("get all customers by admin failed");
		}
	}

	public Customer getCustomer(long id) throws CouponException {
		if (!isLogedIn) {
			throw new CouponException("the operation was canceled due to not being loged in");
		}
		try {
			return customerDAO.getCustomer(id);
		} catch (CustomerException e) {
			throw new CouponException("get customer by admin failed");
		}
	}

}