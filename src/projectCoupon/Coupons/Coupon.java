package projectCoupon.Coupons;

import java.sql.Date;

public class Coupon {
	private long ID;
	private String Title;
	private Date Start_date;
	private Date End_Date;
	private Integer amount;
	 public enum string {
		 type(); 
	 }
	private String Message;
	private Double Price;
	private String image;
	
	public Coupon() {

	}
	
	public Coupon(long iD, String title, Date start_date, Date end_Date, Integer amount, String message, Double price,
			String image) {
		super();
		ID = iD;
		Title = title;
		Start_date = start_date;
		End_Date = end_Date;
		this.amount = amount;
		Message = message;
		Price = price;
		this.image = image;
	}
	
	
	public long getID() {
		return ID;
	}

	public void setID(long iD) {
		ID = iD;
	}

	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public Date getStart_date() {
		return Start_date;
	}
	public void setStart_date(Date start_date) {
		Start_date = start_date;
	}
	public Date getEnd_Date() {
		return End_Date;
	}
	public void setEnd_Date(Date end_Date) {
		End_Date = end_Date;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	public Double getPrice() {
		return Price;
	}
	public void setPrice(Double price) {
		Price = price;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	

}
