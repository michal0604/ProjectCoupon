package Clients;

import Exception.CouponException;

public interface CouponClientFacade {
	
//	public static String user = null;
//	public static String password = null;
//	public static ClientType type = null;
	
	public CouponClientFacade login(String name, String Password) throws CouponException ;
	
}


