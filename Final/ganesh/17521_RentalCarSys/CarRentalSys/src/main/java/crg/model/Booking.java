package crg.model;


import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Bookings")
@DynamicUpdate
public class Booking {
	
	@Id
	@Column(name = "BOOKID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookId;
	
	@Column(name = "STARTDATE")
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")

    private LocalDate startDate;

	@Column(name = "ENDDATE")
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")

	private LocalDate endDate;
	
//	@Column(name = "DATESSELECTED")
//	@DateTimeFormat(pattern = "d-m-yyyy")
////	@Temporal(TemporalType.DATE)
//	private Date selectDate;
    
	@Column(name = "EXTRACHARGES")
	private int extraCharges;  // daily rate of this Vehicle as at time of hire,
    // rate will be used through out this Hire.
	
//	@Column(name = "DEPOSIT")
//	@NotNull
//	private int deposit;
	
	@Column(name = "BOOKINGFEE")
	@NotNull
	private int bookingFee = 5;

	@Column(name = "ACTUALENDDATE")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate actualEndDate;
	
	private double totalCost;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "CUSTID", nullable = true)
	private Customer customer;
	
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "VEHID", nullable = false)
	private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "EMP_ID", nullable = false)
	private Employee employee;
 
  	public Booking() {	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public int getExtraCharges() {
		return extraCharges;
	}

	public void setExtraCharges(int extraCharges) {
		this.extraCharges = extraCharges;
	}

	public int getBookingFee() {
		return bookingFee;
	}

	public void setBookingFee(int bookingFee) {
		this.bookingFee = bookingFee;
	}

	public double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "Booking [bookId=" + bookId + ", startDate=" + startDate + ", endDate=" + endDate + ", extraCharges="
				+ extraCharges + ", bookingFee=" + bookingFee + ", actualEndDate=" + actualEndDate + ", totalCost="
				+ totalCost + ", customer=" + customer + ", vehicle=" + vehicle + ", employee=" + employee + "]";
	}

	public LocalDate getActualEndDate() {
		return actualEndDate;
	}

	public void setActualEndDate(LocalDate actualEndDate) {
		this.actualEndDate = actualEndDate;
	}

	
	
}
