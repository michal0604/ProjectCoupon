package Clients;

import java.sql.Connection;
import java.util.Set;

import Exception.CouponException;
import projectCoupon.Company.Company;
import projectCoupon.Coupons.Coupon;
import projectCoupon.Coupons.CouponDAO;
import projectCoupon.Coupons.CouponDBDAO;
import projectCoupon.Coupons.Utile;
import projectCoupon.Coupons.couponType;
import projectCoupon.Customer.Customer;
import projectCoupon.Customer.CustomerDAO;
import projectCoupon.Customer.CustomerDBDAO;

public class CustomerFacad implements CouponClientFacade {
	
	private CustomerDAO customerDAO;
	private CouponDAO couponDAO;
	private Utile utile;
	private long customerId = 0;
	private Customer customer;
	
	public CustomerFacad() throws CouponException {
		
		customerDAO = new CustomerDBDAO();
		couponDAO = new CouponDBDAO();
		// Application utility
		utile = new Utile();
	}

	@Override
	public CouponClientFacade login(String name, String password, clientType clientType) throws Exception {
		Customer customer = new Customer();
		customer = CustomerDAO.login(name, password,clientType);
		if (customer != null) {
			// initiate customerId to remember in facade.
			this.customerId = customer.getId();
			this.customer = customer;
			return this; 
		} else {
			return null;
		}
	}
	public void purchaseCoupon(long coupId) throws Exception {
		Coupon coupon = new Coupon();
		coupon = couponDAO.getCoupon(coupId);
		if (coupon != null) {
			if (coupon.getAmount() > 0) {
				if (coupon.getendDate().getTime() >= utile.today().getTime()) {
					if (!couponDAO.isCouponPurchasedByCustomer(customerId,coupId)) {
						couponDAO.purchaseCoupon(customerId, coupId);
					} else {
						throw new CouponException("STOP! Coupon Was Already Purchased by Customer! Purchase Canceled!"); 
					}
				} else {
					throw new CouponException("STOP! Coupon Expired! Purchase Canceled!"); 
				}
			} else {
				throw new CouponException("STOP! No More Coupons In Stock! Purchase Canceled!"); 
			}
		} else {
			throw new CouponException("STOP! Coupon Information Not Exist! Purchase Failed!"); 
		}
	}

	/**
	 * View one customer coupon - No specific company (company id = 0)
	 * @param coupId long coupon Id
	 * @return Coupon
	 * @throws Exception 
	 */
	public Coupon getCoupon(long coupId) throws Exception {
		return couponDAO.getCoupon(coupId);
	}
	
	/**
	 * View all customer coupons - No specific company (company id = 0)
	 * @return Coupon collection
	 * @throws CouponException
	 */
	public Set<Coupon> getCoupons() throws CouponException {
		return couponDAO.getCoupons(0,0,0,false);
	}
	
	/**
	 * View all customer coupons by type - No specific company (company id = 0)
	 * @param coupType CouponType 
	 * @return Coupon collection
	 * @throws CouponException
	 */
	public Set<Coupon> getCouponsByType(couponType coupType) throws CouponException {
		return couponDAO.getCouponsByType(0,coupType);
	}
	

	/**
	 * View all customer coupon purchases history (not by type or price)
	 * @return Coupon collection
	 * @throws CouponException
	 */
	public Set<Coupon> getAllPurchasedCoupons() throws CouponException{
		return couponDAO.getAllPurchasedCoupons(customerId);
	}
	
	/**
	 * View all customer coupon purchases history by Coupon Type
	 * @param type CouponType 
	 * @return Coupon collection
	 * @throws CouponException
	 */
	public Set<Coupon> getAllPurchasedCouponsByType (couponType type) throws CouponException {
		return couponDAO.getAllPurchasedCouponsByType (customerId,type);
	}
	
	/**
	 * View all customer coupon purchases history be specific argument - max price requested
	 * @param price double 
	 * @return Coupon collection
	 * @throws CouponException
	 */
	public Set<Coupon> getAllPurchasedCouponsByPrice (long price) throws CouponException {
		return couponDAO.getAllPurchasedCouponsByPrice (customerId,price);
	}
	
	/**
	 * @return the customerId
	 */
	public long getCustomerId() {
		return customerId;
	}
	
	/**
	 * @return Customer
	 */
	public Customer getCustomerInstance() {
		return customer;
	}
}


