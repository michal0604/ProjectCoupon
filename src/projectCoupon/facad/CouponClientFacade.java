package projectCoupon.facad;

import projectCoupon.utils.ClientType;

public interface CouponClientFacade {


	public boolean login(String name, String password,ClientType clientType) throws Exception;

	
	
}


