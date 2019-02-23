package Company_Coupon;

import java.util.List;

public interface Company_CouponDAO {
	
	void insertCompany_Coupon(Company_Coupon company_Coupon) throws Exception;
	
	void removeCompany_Coupon(Company_Coupon company_Coupon) throws Exception;

	List<Company_Coupon> getCompanysByCouponId(long couponId) throws Exception;
	
	List<Company_Coupon> getCouponsByCompanyId(long companyId) throws Exception;
	
	List<Company_Coupon> getAllCompany_Coupons() throws Exception;		
	
}


