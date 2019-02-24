package projectCoupon.Clients;

import projectCoupon.Exception.CouponException;

public interface CouponClientFacade {
	
//	public static String user = null;
//	public static String password = null;
//	public static ClientType type = null;
	
	

	CouponClientFacade login(String name, String password, clientType clientType) throws Exception;

	
	
}


