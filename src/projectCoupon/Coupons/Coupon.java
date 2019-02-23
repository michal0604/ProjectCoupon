package projectCoupon.Coupons;

import java.security.Timestamp;
import java.sql.Date;

import org.apache.derby.impl.store.raw.log.LogAccessFile;

public class Coupon {
	private long id;
	private String title;
	private Date startDate;
	private Date endDate;
	private int amount;
	private couponType type;
	private String message;
	private Double price;
	private String image;
	private long companyId;
	

	
	public Coupon() {

	}
	
	public Coupon(long id, String title, Date startDate, Date endDate, int amount, couponType type,
			String message, Double price, String image,long companyId) {
		super();
		this.id = id;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.amount = amount;
		this.type = type;
		this.message = message;
		this.price = price;
		this.image = image;
		this.companyId=companyId;
	}

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}




	public String getTitle() {
		return title;
	}




	public void setTitle(String title) {
		this.title = title;
	}




	public Date getstartDate() {
		return startDate;
	}




	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getendDate() {
		return endDate;
	}




	public void setEnd_date(Date endDate) {
		this.endDate = endDate;
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
	
	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	@Override
	public String toString() {
		return "Coupon [id=" + id + ", title=" + title + ", startDate=" + startDate + ", endDate=" + endDate
				+ ", amount=" + amount + ", type=" + type + ", message=" + message + ", price=" + price + ", image="
				+ image + "]";
	}

}
