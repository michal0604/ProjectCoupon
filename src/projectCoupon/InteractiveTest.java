package projectCoupon;

import java.util.List;
import java.util.Scanner;

import projectCoupon.Company.Company;
import projectCoupon.Company.CompanyFacade;
import projectCoupon.Customer.Customer;
import projectCoupon.Customer.CustomerFacade;

public class InteractiveTest {
	
	
	public static final int DO_CUSTOMER = 1;
	public static final int DO_COMPANY = 2;
	public static final int DO_COUPON = 3;
	public static final int EXIT = 4;
	
	public static final int OP_ADD = 1;
	public static final int OP_UPDATE = 2;
	public static final int OP_REMOVE = 3;
	public static final int OP_LIST = 4;
	
	public Scanner scanner = new Scanner(System.in);
	
	private void runTest() {
		boolean flag = true;
		System.out.println("Welcome to the interactive Coupon test");
		int scema,operation = 0;
		while(flag) {
			System.out.println("On whom whould you like to preform the operation?");
			System.out.println("1-Customer\n2-Company\n3-Coupon\n4-end test");
			scema = scanner.nextInt();
			if(scema != EXIT) {
				System.out.println("what kind of operation whould you like to perform?");
				System.out.println("1-Add new\n2-Update existing\n3-remove\n4-List all members in a table");
				operation = scanner.nextInt();
			}
			switch (scema) {
			case DO_CUSTOMER:
				doCustomerOperation(operation);
				break;
			case DO_COMPANY:
				doCompanyOperation(operation);
				break;
			case DO_COUPON:
				doCouponOperation(operation);
				break;
			case EXIT:
				flag = false;
				break;
			default:
				break;
			}
		}
	}
	
	private void doCustomerOperation(int operation) {
		CustomerFacade customerFacade=new CustomerFacade();
		Customer newCustomer,oldCustomer;
		switch (operation) {
		case OP_ADD:
			try {
				newCustomer = readCustomer(new Customer(),true);
				customerFacade.insertCustomer(newCustomer);
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case OP_UPDATE:
			try {
				oldCustomer = chooseCustomer(customerFacade.getAllCustomer(),"update",true);
				if(oldCustomer != null) {
					newCustomer = readCustomer(oldCustomer,false);
					customerFacade.updateCustomer(oldCustomer, newCustomer.getCustomerName(), newCustomer.getPassword());
				}
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case OP_REMOVE:
			try {
				oldCustomer = chooseCustomer(customerFacade.getAllCustomer(),"update",true);
				if(oldCustomer !=null) {
					customerFacade.removeCustomer(oldCustomer);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case OP_LIST:
			try {
				chooseCustomer(customerFacade.getAllCustomer(),"show anything",false);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}
		
	private Customer chooseCustomer(List<Customer> allCustomer, String operation,boolean waitReturn) {
		if(allCustomer.isEmpty()) {
			System.out.println("List is empty can't "+operation);
			return null;
		}
		if(waitReturn) {
			System.out.println("Choose which Customer to "+operation+" by id:");
		}
		for(Customer iter: allCustomer) {
			System.out.println(iter.toString());
		}
		Customer customer = null;
		if(!waitReturn) {
			return customer;
		}
		long id = scanner.nextLong();
		for(Customer iter: allCustomer) {
			if(iter.getId() == id){
				customer = iter;
			}
		}
		if(customer == null) {
			System.out.println("the cousen id was illegal the "+operation+" operation will be aborted");
		}
		return customer;
	}

	private Customer readCustomer(Customer customer, boolean isAdd) {
		String name,password;
		boolean runflag = true;
		System.out.println("pleas enter a valid Name");
		while(runflag) {
			name = scanner.nextLine();
			if(isAdd&name.trim().isEmpty()) {
				System.out.println("invalid name pleas enter valid name");
			}
			else if(!isAdd&name.trim().isEmpty()){
				runflag =false;
			}
			else {
				customer.setCustomerName(name);
				runflag = false;
			}
		}
		runflag = true;
		System.out.println("pleas enter a valid Password");
		while(runflag) {
			password = scanner.nextLine();
			if(isAdd&password.trim().isEmpty()) {
				System.out.println("invalid name pleas enter valid name");
			}
			else if(!isAdd&password.trim().isEmpty()){
				runflag =false;
			}
			else {
				customer.setPassword(password);
				runflag = false;
			}
		}			
		return customer;
	}

	private void doCompanyOperation(int operation) {
		CompanyFacade companyFacade=new CompanyFacade();
		Company newCompany,oldCompany;
		switch (operation) {
		case OP_ADD:
			try {
				newCompany = readCompany(new Company(),true);
				companyFacade.insertCompany(newCompany);
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case OP_UPDATE:
			try {
				oldCompany = chooseCompany(companyFacade.getAllCompany(),"update",true);
				if(oldCompany != null) {
					newCompany = readCompany(oldCompany,false);
					companyFacade.updateCompany(oldCompany, newCompany.getCompName(), newCompany.getPassword(),newCompany.getEmail());
				}
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case OP_REMOVE:
			try {
				oldCompany = chooseCompany(companyFacade.getAllCompany(),"update",true);
				if(oldCompany !=null) {
					companyFacade.removeCompany(oldCompany);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case OP_LIST:
			try {
				chooseCompany(companyFacade.getAllCompany(),"show anything",false);
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
		
	}

	private Company chooseCompany(List<Company> allCompany, String operation, boolean waitReturn) {
		if(allCompany.isEmpty()) {
			System.out.println("List is empty can't "+operation);
			return null;
		}
		if(waitReturn) {
			System.out.println("Choose which Customer to "+operation+" by id:");
		}
		for(Company iter: allCompany) {
			System.out.println(iter.toString());
		}
		Company company = null;
		if(!waitReturn) {
			return company;
		}
		long id = scanner.nextLong();
		for(Company iter: allCompany) {
			if(iter.getId() == id){
				company = iter;
			}
		}
		if(company == null) {
			System.out.println("the cousen id was illegal the "+operation+" operation will be aborted");
		}
		return company;
	}

	private Company readCompany(Company company, boolean isAdd) {
		String name,password,email;
		boolean runflag = true;
		System.out.println("pleas enter a valid Name");
		while(runflag) {
			name = scanner.nextLine();
			if(isAdd&name.trim().isEmpty()) {
				System.out.println("invalid name pleas enter valid name");
			}
			else if(!isAdd&name.trim().isEmpty()){
				runflag =false;
			}
			else {
				company.setCompName(name);
				runflag = false;
			}
		}
		runflag = true;
		System.out.println("pleas enter a valid Password");
		while(runflag) {
			password = scanner.nextLine();
			if(isAdd&password.trim().isEmpty()) {
				System.out.println("invalid name pleas enter valid name");
			}
			else if(!isAdd&password.trim().isEmpty()){
				runflag =false;
			}
			else {
				company.setPassword(password);
				runflag = false;
			}
		}
		runflag = true;
		System.out.println("pleas enter a valid Email");
		while(runflag) {
			email = scanner.nextLine();
			if(isAdd&email.trim().isEmpty()) {
				System.out.println("invalid name pleas enter valid name");
			}
			else if(!isAdd&email.trim().isEmpty()){
				runflag =false;
			}
			else {
				company.setEmail(email);
				runflag = false;
			}
		}			
		return company;
	}

	private void doCouponOperation(int operation) {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args) {
		InteractiveTest interactiveTest	= new InteractiveTest();
		interactiveTest.runTest();
	}

}
