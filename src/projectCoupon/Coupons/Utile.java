package projectCoupon.Coupons;

import java.sql.Date;
import java.time.LocalDate;

import projectCoupon.Exception.CouponException;

public class Utile {
	
		
	    
	    public static Date getCurrentDate() {
	    LocalDate localDate = LocalDate.now();
		Date date = java.sql.Date.valueOf(localDate);
		return date;
	    }
	    
	    public static Date getExpiredDate() {
	    	
	        LocalDate localDate = LocalDate.now();
	        localDate = localDate.plusYears(1);
	    	Date date = java.sql.Date.valueOf(localDate);
	    	
	    	return date;
	        }
	    public static String getDriverData() {
			return "org.apache.derby.jdbc.ClientDriver";
		}
	    
	    public static String getDBUrl() {
			return "jdbc:derby://localhost:3301/MyDB;create=true";
		}

	    //TODO function empty
		public static java.util.Date toDate(int i) {
			// TODO Auto-generated method stub
			return null;
		}

		public java.util.Date today () throws CouponException {
			return toDate(0);	 
		}

		

}
