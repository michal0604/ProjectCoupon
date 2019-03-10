package projectCoupon.Clients;

import java.sql.SQLException;

import projectCoupon.Exception.CompanyException;
import projectCoupon.Exception.CouponException;
import projectCoupon.Exception.CustomerException;

public abstract class CouponClientFacade {


	public static CouponClientFacade login(String name, String password) throws CouponException , SQLException, CompanyException,CustomerException{return null;};

	public static CouponClientFacade login(ClientType type,String name, String password) throws CouponException {
		if(type.equals(ClientType.ADMIN)) {
			return AdminFacad.login(name, password);
		}
		else if(type.equals(ClientType.COMPANY)){
			return AdminFacad.login(name, password);
		}
		else if(type.equals(ClientType.CUSTOMER)){
			return AdminFacad.login(name, password);
		}
		return null;
	}
	
}


