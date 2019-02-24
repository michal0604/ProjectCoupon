package projectCoupon.Clients;


	import java.awt.List;
import java.sql.Connection;
	import java.util.Set;

	
	import projectCoupon.Company.Company;
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
		public CouponClientFacade login(String name, String password, clientType clientType) throws Exception {
			Customer customer = new Customer();
			customer = CustomerDAO.login(name, password, clientType);
			if (customer != null) {
				// initiate customerId to remember in facade.
				this.custId = customer.getId();
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
		
		
		public Set<Coupon> getCouponsByType(couponType coupType) throws CouponException {
			return couponDAO.getCouponsByType(0,coupType);
		}
		
		public Set<Coupon> getAllPurchasedCoupons() throws CouponException{
			return couponDAO.getAllPurchasedCoupons(custId);
		}

		public Set<Coupon> getAllPurchasedCouponsByType (couponType type) throws CouponException {
			return couponDAO.getAllPurchasedCouponsByType (custId,type);
		}
		
		/**
		 * View all customer coupon purchases history be specific argument - max price requested
		 * @param price double 
		 * @return Coupon collection
		 * @throws CouponException
		 */
		public Set<Coupon> getAllPurchasedCouponsByPrice (long price) throws CouponException {
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
		
		public Customer getCustomer(long id) throws Exception {
			return custDAO.getCustomer(id);
		}

		public java.util.List<Customer> getAllCustomer() throws Exception {
			// ProductDBDAO comDAO=new ProductDBDAO();
			return custDAO.getAllCustomer();
		}
	}




