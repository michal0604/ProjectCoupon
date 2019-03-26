package projectCoupon.facad;

import projectCoupon.utils.ClientType;

public interface CouponClientFacade {

	public CouponClientFacade login(String name, String password, ClientType clientType) throws Exception;

}
