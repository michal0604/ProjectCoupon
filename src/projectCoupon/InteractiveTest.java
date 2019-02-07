package projectCoupon;

import java.util.Scanner;

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
			System.out.println("1-Add new\n2-Update current\n3-remove\n4-List all members in a table");
			operation = scanner.nextInt();
			switch (scema) {
			case DO_CUSTOMER:
				break;
			case DO_COMPANY:
				break;
			case DO_COUPON:
				break;
			case EXIT:
				flag = false;
				break;
			default:
				break;
			}
		}
		
		System.out.println("1-Add to Data\n2-Update data member\n3-remove from Data member\n4-List all members in a table");
	}
	
	public static void main(String[] args) {
		InteractiveTest interactiveTest	= new InteractiveTest();
		interactiveTest.runTest();
	}

}
