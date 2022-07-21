package fpt.edu.vn.component;

import java.util.Date;

public class InsertBookingDto {
	private Long fkId;
	
	private double price;
	
	private String bookingTime;
	
	private Date bookingDate;
	
	public InsertBookingDto(Long fkId, double price, String bookingTime, Date bookingDate) {
		super();
		this.fkId = fkId;
		this.price = price;
		this.bookingTime = bookingTime;
		this.bookingDate = bookingDate;
	}
	public Long getFkId() {
		return fkId;
	}
	public void setFkId(Long fkId) {
		this.fkId = fkId;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getBookingTime() {
		return bookingTime;
	}
	public void setBookingTime(String bookingTime) {
		this.bookingTime = bookingTime;
	}
	public Date getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}
}
