package projectCoupon.Company_Coupon;

import java.util.List;



public class Company_CouponFacad  {
	private Company_CouponDBDAO  comp_couponDAO = new Company_CouponDBDAO();
	private long comp_Id;
	private long coupon_Id;
	private Company_Coupon company_Coupon;
	
	public Company_CouponFacad(Company_Coupon company_Coupon) {
		this.company_Coupon = company_Coupon;

	}
		
		public Company_CouponFacad() {}
		
		public void insertCompany_Coupon(Company_Coupon company_Coupon) throws Exception {
			//TODO update the exception to the suited one
			comp_couponDAO.insertCompany_Coupon(company_Coupon);
		}
		
		public void removeCompany_Coupon(Company_Coupon company_Coupon) throws Exception {
			//TODO update the exception to the suited one
			comp_couponDAO.removeCompany_Coupon(company_Coupon);
		}

		public List<Company_Coupon> getAllCCompany_Coupon() throws Exception {
			//TODO update the exception to the suited one
			// ProductDBDAO comDAO=new ProductDBDAO();
			return comp_couponDAO.getAllCompany_Coupons();
		}

		public List<Company_Coupon> getCompanysByCouponId(long couponId) throws Exception{
			
			 return comp_couponDAO.getCompanysByCouponId(couponId);
		}
		
		public List<Company_Coupon> getCouponsByCompanyId(long companyId) throws Exception{
			
			 return comp_couponDAO.getCouponsByCompanyId(companyId);

}
		public void updateCompany_Coupon(Company_Coupon company_Coupon, long newcompId, long newcouponId) throws Exception {
			//TODO update the exception to the suited one
			company_Coupon.setComp_Id(newcompId);
			company_Coupon.setCoupon_Id(newcouponId);
			comp_couponDAO.updateCompany_Coupon(company_Coupon);
			
		}

}
