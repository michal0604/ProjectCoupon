package projectCoupon.facad;

import java.util.ArrayList;
import java.util.List;

import projectCoupon.beans.Coupon;
import projectCoupon.beans.Customer;
import projectCoupon.beans.couponType;
import projectCoupon.dao.CouponDAO;
import projectCoupon.dao.CustomerDAO;
import projectCoupon.dao.Customer_CouponDAO;
import projectCoupon.dbdao.CouponDBDAO;
import projectCoupon.dbdao.CustomerDBDAO;
import projectCoupon.dbdao.Customer_CouponDBDAO;
import projectCoupon.exception.CouponException;
import projectCoupon.exception.CreateException;
import projectCoupon.exception.CustomerException;
import projectCoupon.exception.UpdateException;
import projectCoupon.utils.ClientType;
import projectCoupon.utils.Utile;

public class CustomerFacad implements CouponClientFacade {

	private CustomerDAO custDAO;
	private CouponDAO couponDAO;
	private Customer_CouponDAO customer_CouponDAO;
	private long custId = 0;
	private Customer customer;

	public CustomerFacad() throws CouponException {
		custDAO = new CustomerDBDAO();
		couponDAO = new CouponDBDAO();
		customer_CouponDAO = new Customer_CouponDBDAO();
	}



	public void insertCustomer(Customer customer) throws Exception {
		custDAO.insertCustomer(customer);
	}

	public void removeCustomer(Customer customer) throws Exception {
		customer_CouponDAO.removeCustomer_Coupon(customer);
		custDAO.removeCustomer(customer);
	}

	public void updateCustomer(Customer customer, String newName, String newpassword) throws Exception {
		customer.setCustomerName(newName);
		customer.setPassword(newpassword);
		custDAO.updateCustomer(customer);
	}

	public void purchaseCoupon(long coupId) throws CouponException, CreateException, UpdateException{
		Coupon coupon = new Coupon();
		coupon = couponDAO.getCoupon(coupId);
		if (coupon != null) {
			if (coupon.getAmount() > 0) {
				if (coupon.getEnd_date().getTime() >= Utile.getCurrentDate().getTime()) {
					if (!customer_CouponDAO.isCouponPurchasedByCustomer(coupId, coupId)){
						coupon.setAmount(coupon.getAmount() - 1);
						couponDAO.updateCoupon(coupon);
						customer_CouponDAO.insertCustomer_Coupon(custId, coupId);
					} else {
						throw new CouponException("Coupon Was Already Purchased by Customer! Purchase Canceled!");
					}
				} else {
					throw new CouponException("Coupon Expired! Purchase Canceled!");
				}
			} else {
				throw new CouponException("No More Coupons In Stock! Purchase Canceled!");
			}
		} else {
			throw new CouponException("Coupon Information Not Exist! Purchase Failed!");
		}
	}

	public Coupon getCoupon(long coupId) throws Exception {
		return couponDAO.getCoupon(coupId);
	}

	public List<Coupon> getAllCouponsByType(couponType couponType) throws CouponException {
		return couponDAO.getAllCouponsByType(couponType);
	}

	public List<Coupon> getAllPurchasedCoupons() throws CouponException, CreateException {
		List<Long> customersCoupons = customer_CouponDAO.getCouponsByCustomerId(custId);
		List<Coupon> purchasedCoupons = new ArrayList<Coupon>();
		for(Long id:customersCoupons) {
			purchasedCoupons.add(couponDAO.getCoupon(id));
		}
		return purchasedCoupons;
	}

	public List<Coupon> getAllPurchasedCouponsByType(couponType type) throws CouponException, CreateException {
		List<Coupon> coupons = getAllPurchasedCoupons();
		List<Coupon> couponByType = new ArrayList<Coupon>();
		for (Coupon coupon : coupons) {
			if (coupon.getType().name().equals(type.name())){
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
	public List<Coupon> getAllPurchasedCouponsByPrice(long price) throws CouponException, CreateException {
		List<Coupon> coupons = getAllPurchasedCoupons();
		List<Coupon> couponByType = new ArrayList<Coupon>();
		for (Coupon coupon : coupons) {
			if (coupon.getPrice() == price){
				couponByType.add(coupon);
			}
		}
		return couponByType;
	}

	public long getCustomerId() {
		return custId;
	}

	/**
	 * @return Customer
	 */
	public Customer getCustomerInstance() {
		return customer;
	}

	public Customer getCustomer() throws CustomerException{
			System.out.println(custDAO.getCustomer(this.customer.getCustomerId()));
			return custDAO.getCustomer(this.customer.getCustomerId());
	}

	public java.util.List<Customer> getAllCustomer() throws Exception {
		return custDAO.getAllCustomer();
	}

	public static CouponClientFacade login(String name, String password) throws Exception {
		Customer customer = new CustomerDBDAO().login(name, password);
		if (customer != null) {
			CustomerFacad customerFacad = new CustomerFacad();
			customerFacad.custId = customer.getCustomerId();
			customerFacad.customer = customer;
			return customerFacad;
		} else {
			return null;
		}
		
		
	}
}
