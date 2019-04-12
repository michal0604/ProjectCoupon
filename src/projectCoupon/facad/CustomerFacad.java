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
import projectCoupon.exception.UpdateException;
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


	public void purchaseCoupon(long coupId) throws CouponException { 
		if(custId == 0) {
			throw new CouponException("the operation was canceled due to not being loged in");
		}
		Coupon coupon = new Coupon();
		try {
			coupon = couponDAO.getCoupon(coupId);
		} catch (CreateException e) {
			throw new CouponException("purchase coupon by customer failed");
		}
		if (coupon != null) {
			if (coupon.getAmount() > 0) {
				if (coupon.getEnd_date().getTime() >= Utile.getCurrentDate().getTime()) {
					if (!customer_CouponDAO.isCouponPurchasedByCustomer(custId, coupId)){
						coupon.setAmount(coupon.getAmount() - 1);
						try {
							couponDAO.updateCoupon(coupon);
						} catch (UpdateException e) {
							throw new CouponException("update coupon by customer failed");
						} catch (CreateException e) {
							throw new CouponException("update coupon by customer failed");
						}
						try {
							customer_CouponDAO.insertCustomer_Coupon(custId, coupId);
						} catch (CreateException e) {
							throw new CouponException("insert coupon by customer failed");
						}
						
					}
				}
			}
		}
	}




	public List<Coupon> getAllCouponsByType(CouponType couponType) throws CouponException {
		if(custId == 0) {
			throw new CouponException("the operation was canceled due to not being loged in");
		}
		return couponDAO.getAllCouponsByType(couponType);
	}

	public List<Coupon> getAllPurchasedCoupons() throws CouponException {
		if(custId == 0) {
			throw new CouponException("the operation was canceled due to not being loged in");
		}
		List<Long> customersCoupons;
		try {
			customersCoupons = customer_CouponDAO.getCouponsByCustomerId(custId);
		} catch (CreateException e) {
			throw new CouponException("get all purchas coupons by customer failed");
		}
		List<Coupon> purchasedCoupons = new ArrayList<Coupon>();
		for (Long id : customersCoupons) {
			try {
				purchasedCoupons.add(couponDAO.getCoupon(id));
			} catch (CreateException e) {
				throw new CouponException("add coupon by customer failed");
			}
		}
		return purchasedCoupons;
	}

	public List<Coupon> getAllPurchasedCouponsByType(CouponType type) throws CouponException {
		if(custId == 0) {
			throw new CouponException("the operation was canceled due to not being loged in");
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
	public List<Coupon> getAllPurchasedCouponsByPrice(long price) throws CouponException {
		if(custId == 0) {
			throw new CouponException("the operation was canceled due to not being loged in");
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
	public Customer getCustomerInstance() throws CouponException {
		if(custId == 0) {
			throw new CouponException("the operation was canceled due to not being loged in");
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
