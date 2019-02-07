package projectCoupon;

import java.security.PublicKey;
import java.util.List;
import java.util.Scanner;

import org.apache.derby.tools.sysinfo;

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
		int scema,operation;
		while(flag) {
			System.out.println("On whom whould you like to preform the operation?");
			System.out.println("1-Customer\n2-Company\n3-Coupon\n4-end test");
			scema = scanner.nextInt();
			System.out.println("what kind of operation whould you like to perform?");
			System.out.println("1-Add new\n2-Update existing\n3-remove\n4-List all members in a table");
			operation = scanner.nextInt();
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
		switch (operation) {
		case OP_ADD:
			try {
				Customer customer = readCustomer(false);
				customerFacade.insertCustomer(customer);
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case OP_UPDATE:
			try {
				Customer oldCustomer = chooseCustomer(customerFacade.getAllCustomer(),"update");
				Customer newCustomer = readCustomer(true);
				customerFacade.updateCustomer(oldCustomer, newCustomer.getCustomerName(), newCustomer.getPassword());
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case OP_REMOVE:
			
			break;
		case OP_LIST:
			break;
		default:
			break;
		}
	}
		
	private Customer chooseCustomer(List<Customer> allCustomer, String operation) {
		if(allCustomer.isEmpty()) {
			System.out.println("List is empty can't "+operation);
			return null;
		}
		System.out.println("Choose which Customer to "+operation+" :");
		for(Customer iter: allCustomer) {
			System.out.println(iter.toString());
		}
		long id = scanner.nextLong();
		Customer customer = null;
		for(Customer iter: allCustomer) {
			if(iter.getId() == id){
				customer = iter;
			}
		}
		if(customer == null) {
			System.out.println("the cousen id was illegal the "+operation+" operation will be aborted");
		}
		return null;
	}

	private Customer readCustomer(boolean b) {
		// TODO Auto-generated method stub
		return null;
	}

	private void doCompanyOperation(int operation) {
		// TODO Auto-generated method stub
		
	}

	private void doCouponOperation(int operation) {
		// TODO Auto-generated method stub
		
	}

	public static void main(String[] args) {
		InteractiveTest interactiveTest	= new InteractiveTest();
		interactiveTest.runTest();
	}

}
