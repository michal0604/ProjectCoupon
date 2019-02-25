package projectCoupon.Company_Coupon;

import java.util.List;

import projectCoupon.Company.Company;
import projectCoupon.Coupons.Coupon;
import projectCoupon.Customer_Coupon.Customer_Coupon;

public interface Company_CouponDAO {
	
	void insertCompany_Coupon(Company company, Coupon coupon) throws Exception;
	
	void removeCompany_Coupon(Company company, Coupon coupon) throws Exception;
	
    void removeCompany_Coupon(Company company) throws Exception;
	
	void removeCompany_Coupon(Coupon coupon) throws Exception;

	List<Company_Coupon> getCompanysByCouponId(long couponId) throws Exception;
	
	List<Company_Coupon> getCouponsByCompanyId(long companyId) throws Exception;
	
	List<Company_Coupon> getAllCompany_Coupons() throws Exception;
	
	void updateCompany_Coupon(Company_Coupon company_Coupon) throws Exception;
	
}


