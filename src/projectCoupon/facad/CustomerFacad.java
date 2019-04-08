package projectCoupon.facad;

import java.util.ArrayList;
import java.util.List;

import projectCoupon.beans.Coupon;
import projectCoupon.beans.CouponType;
import projectCoupon.beans.Customer;
import projectCoupon.dao.CouponDAO;
import projectCoupon.dao.Customer_CouponDAO;
import projectCoupon.dbdao.CouponDBDAO;
import projectCoupon.dbdao.CustomerDBDAO;
import projectCoupon.dbdao.Customer_CouponDBDAO;
import projectCoupon.exception.CouponException;
import projectCoupon.exception.CreateException;
import projectCoupon.utils.Utile;

public class CustomerFacad implements CouponClientFacade {

	private CouponDAO couponDAO;
	private Customer_CouponDAO customer_CouponDAO;
	private long custId = 0;
	private Customer customer;

	public CustomerFacad() throws CouponException {
		couponDAO = new CouponDBDAO();
		customer_CouponDAO = new Customer_CouponDBDAO();
	}


	public void purchaseCoupon(long coupId) throws Exception { 
		if(custId == 0) {
			System.out.println("the operation was canceled due to not being loged in");
		}
		Coupon coupon = new Coupon();
		coupon = couponDAO.getCoupon(coupId);
		if (coupon != null) {
			if (coupon.getAmount() > 0) {
				if (coupon.getEnd_date().getTime() >= Utile.getCurrentDate().getTime()) {
					if (!customer_CouponDAO.isCouponPurchasedByCustomer(custId, coupId)){
						coupon.setAmount(coupon.getAmount() - 1);
						couponDAO.updateCoupon(coupon);
						customer_CouponDAO.insertCustomer_Coupon(custId, coupId);
						System.out.println("customer succsess to buy coupon!");
					} else {
						System.out.println("customer buy this coupon! Purchase Canceled!");
					}
				}
			}
		}
	}




	public List<Coupon> getAllCouponsByType(CouponType couponType) throws Exception {
		if(custId == 0) {
			System.out.println("the operation was canceled due to not being loged in");
		}
		return couponDAO.getAllCouponsByType(couponType);
	}

	public List<Coupon> getAllPurchasedCoupons() throws Exception {
		if(custId == 0) {
			System.out.println("the operation was canceled due to not being loged in");
		}
		List<Long> customersCoupons = customer_CouponDAO.getCouponsByCustomerId(custId);
		List<Coupon> purchasedCoupons = new ArrayList<Coupon>();
		for (Long id : customersCoupons) {
			purchasedCoupons.add(couponDAO.getCoupon(id));
		}
		return purchasedCoupons;
	}

	public List<Coupon> getAllPurchasedCouponsByType(CouponType type) throws Exception {
		if(custId == 0) {
			System.out.println("the operation was canceled due to not being loged in");
		}
		List<Coupon> coupons = getAllPurchasedCoupons();
		List<Coupon> couponByType = new ArrayList<Coupon>();
		for (Coupon coupon : coupons) {
			if (coupon.getType().name().equals(type.name())) {
				couponByType.add(coupon);
			}
		}
		return couponByType;
	}

	/**
	 * View all customer coupon purchases history be specific argument - max price
	 * requested
	 * 
	 * @param price
	 *            double
	 * @return Coupon collection
	 * @throws CouponException
	 * @throws CreateException
	 */
	public List<Coupon> getAllPurchasedCouponsByPrice(long price) throws Exception {
		if(custId == 0) {
			System.out.println("the operation was canceled due to not being loged in");
		}
		List<Coupon> coupons = getAllPurchasedCoupons();
		List<Coupon> couponByType = new ArrayList<Coupon>();
		for (Coupon coupon : coupons) {
			if (coupon.getPrice() <= price) {
				couponByType.add(coupon);
			}
		}
		return couponByType;
	}


	/**
	 * @return Customer
	 * @throws CouponException 
	 */
	public Customer getCustomerInstance() throws Exception {
		if(custId == 0) {
			System.out.println("the operation was canceled due to not being loged in");
		}
		return customer;
	}

	


	public CouponClientFacade login(String name, String password) throws Exception {
		Customer customer = new CustomerDBDAO().login(name, password);
		if (customer != null) {
			this.custId = customer.getCustomerId();
			this.customer = customer;
			return this;
		} else {
			return null;
		}

	}
}
