package projectCoupon.Coupons;

import java.sql.Date;
import java.time.LocalDate;

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

}
