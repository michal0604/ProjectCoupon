import java.sql.Connection;
import java.sql.DriverManager;

public class TestCoupon {

	public static void main(String[] args) throws Exception {

	Class.forName("org.apache.derby.jdbc.ClientDriver");

	
		Company p1 = new Company(1, "hp", "123", "aaa");
		Company p2 = new Company(2, "tadiran", "234", "bbb");
		Company p3 = new Company(3, "eci", "333", "ccc");
		Company p4 = new Company(4, "intel", "451", "abc");
		
		Connection con=DriverManager.getConnection(Database.getDBUrl());
	
	//	Database.createTables(con);

		CompanyFacade CompanyFacade = new CompanyFacade();
//		CompanyFacade.insertCompany(p1);
//		CompanyFacade.insertCompany(p2);
//		CompanyFacade.insertCompany(p3);
//		CompanyFacade.insertCompany(p4);
		Company p5 = new Company(5, "linoy", "6293", "aaa");
		CompanyFacade.insertCompany(p5);
		
//		CompanyFacade.removeCompany(p3);
		
		
		
//		Customer  c1=new Customer(1, "uri", "222");
//		Customer  c2=new Customer(2, "Oz", "272");
//		Customer  c3=new Customer(3, "Pazit", "777");
//		
//		
//		Database.createTables(con);
//		CustomerFacade customerFacade=new CustomerFacade();
//		customerFacade.insertCustomer(c1);
//		customerFacade.insertCustomer(c2);
//		customerFacade.insertCustomer(c3);
//		 System.out.println(customerFacade.getAllCustomer());
//		
		

		
		
		



	}
}

