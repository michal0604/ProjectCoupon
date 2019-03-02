package projectCoupon.Clients;



import java.util.ArrayList;
import java.util.List;

import projectCoupon.Coupons.Coupon;
import projectCoupon.Coupons.CouponDAO;
import projectCoupon.Coupons.CouponDBDAO;
import projectCoupon.Coupons.Utile;
import projectCoupon.Coupons.couponType;
import projectCoupon.Customer.Customer;
import projectCoupon.Customer.CustomerDAO;
import projectCoupon.Customer.CustomerDBDAO;
import projectCoupon.Exception.CouponException;

	public class CustomerFacad implements CouponClientFacade {
		
		private CustomerDAO custDAO;
		private CouponDAO couponDAO;
		private Utile utile;
		private long custId = 0;
		private Customer customer;
		
		public CustomerFacad(Customer customer) {
			this.customer = customer;

		}
		
		public CustomerFacad() throws CouponException {
			
			custDAO = new CustomerDBDAO();
			couponDAO = new CouponDBDAO();
			utile = new Utile();
		}
		
		@Override
		public CouponClientFacade login(String name, String password) throws Exception {
			Customer customer = new Customer();
			customer = custDAO.login(name, password);
			if (customer != null) {
				// initiate customerId to remember in facade.
				this.custId = customer.getCustomerId();
				this.customer = customer;
				return this; 
			} else {
				return null;
			}
		}
		
		public void insertCustomer(Customer customer) throws Exception {
			custDAO.insertCustomer(customer);
		}

		public void removeCustomer(Customer customer) throws Exception {
			custDAO.removeCustomer(customer);
		}

		public void updateCustomer(Customer customer, String newName, String newpassword) throws Exception {
			customer.setCustomerName(newName);
			customer.setPassword(newpassword);
			custDAO.updateCustomer(customer);
		}

		
		public void purchaseCoupon(long coupId) throws Exception {
			Coupon coupon = new Coupon();
			coupon = couponDAO.getCoupon(coupId);
			if (coupon != null) {
				if (coupon.getAmount() > 0) {
					if (coupon.getEnd_date().getTime() >= utile.today().getTime()) {
			if (!couponDAO.isCouponPurchasedByCustomer(custId,coupId)) {
							couponDAO.purchaseCoupon(custId, coupId);
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
		
		 public List<Coupon> getAllCouponsByType(long couponId, couponType couponType) throws CouponException{
			 List<Coupon> coupons = new ArrayList<Coupon>();
				for (Coupon coupon : custDAO.getCoupons(custId)) {
					if (coupon.getType().equals(couponType) ) {
						coupons.add(coupon);
					}
				}
				return coupons;
		 }
		
		
		
		public List<Coupon> getAllPurchasedCoupons() throws CouponException{
			return couponDAO.getAllPurchasedCoupons(custId);
		}

		public List<Coupon> getAllPurchasedCouponsByType (couponType type) throws CouponException {
			return couponDAO.getAllPurchasedCouponsByType (custId,type);
		}
		
		/**
		 * View all customer coupon purchases history be specific argument - max price requested
		 * @param price double 
		 * @return Coupon collection
		 * @throws CouponException
		 */
		public List<Coupon> getAllPurchasedCouponsByPrice (long price) throws CouponException {
			return couponDAO.getAllPurchasedCouponsByPrice (custId,price);
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
		
		public Customer getCustomer() throws Exception {
			try {
				System.out.println(custDAO.getCustomer(this.customer.getCustomerId()));
				return custDAO.getCustomer(this.customer.getCustomerId());
			} catch (Exception e) {
				throw new Exception("Cusstomer failed to get customer details. customerId: " + this.customer.getCustomerId());
			}
		}

		

		public java.util.List<Customer> getAllCustomer() throws Exception {
			// ProductDBDAO comDAO=new ProductDBDAO();
			return custDAO.getAllCustomer();
		}
	}




