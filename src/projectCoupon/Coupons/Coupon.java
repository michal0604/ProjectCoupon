package projectCoupon.Coupons;

import java.sql.Date;

public class Coupon {
	private long couponId;
	private String title;
	private Date start_date;
	private Date end_date;
	private int amount;
	private couponType type;
	private String message;
	private Double price;
	private String image;
	
	public Coupon() {

	}
	

	public Coupon(long couponId, String title, Date start_date, Date end_date, int amount, couponType type,
			String message, Double price, String image) {
		super();
		this.couponId = couponId;
		this.title = title;
		this.start_date = start_date;
		this.end_date = end_date;
		this.amount = amount;
		this.type = type;
		this.message = message;
		this.price = price;
		this.image = image;
	}



	public long getCouponId() {
		return couponId;
	}


	public void setCouponId(long couponId) {
		this.couponId = couponId;
	}


	public String getTitle() {
		return title;
	}




	public void setTitle(String title) {
		this.title = title;
	}




	public Date getStart_date() {
		return start_date;
	}




	public void setStart_date(java.util.Date date) {
		this.start_date = (Date) date;
	}




	public Date getEnd_date() {
		return end_date;
	}




	public void setEnd_date(java.util.Date date) {
		this.end_date = (Date) date;
	}




	public int getAmount() {
		return amount;
	}




	public void setAmount(int amount) {
		this.amount = amount;
	}




	public couponType getType() {
		return type;
	}

   
	public void setType(couponType type) {
		this.type = type;
	}




	public String getMessage() {
		return message;
	}




	public void setMessage(String message) {
		this.message = message;
	}




	public Double getPrice() {
		return price;
	}




	public void setPrice(Double price) {
		this.price = price;
	}




	public String getImage() {
		return image;
	}




	public void setImage(String image) {
		this.image = image;
	}




	@Override
	public String toString() {
		return "Coupon [couponId=" + couponId + ", title=" + title + ", start_date=" + start_date + ", end_date=" + end_date
				+ ", amount=" + amount + ", type=" + type + ", message=" + message + ", price=" + price + ", image="
				+ image + "]";
	}

}
