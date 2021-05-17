package crg.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;

import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
@Table(name="Customers")
@DynamicUpdate
public class Customer {
	
	@Id
	@Column(name = "CUSTID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long custId;
	
	@Column(name = "CUSTNAME")
	@Size (min = 5, max = 45)
	private String custName;
	
	@Column(unique=true)
	@Size (min = 4, max = 4, message="Enter a 4-char code")
	@NotNull
	private String nric;
	
	@Column(name = "EMAIL")
	@Email
	@NotNull
	@Size (min = 5, max = 30)
	private String custEmail;
	
	@Column(name = "PHONENO")
	@Size (min = 7, max = 15)
	private String custPhoneNo;
	
	
	@Column(name = "ADDRESS")
	@Size (min = 2, max = 15)
	@NotNull (message = "Should not be null")
	private String address;
	
	
	@Column(name = "CITY")
	@Size (min = 2, max = 15)
	@NotNull
	private String city;
	
	@Column(name = "DOB")
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String dob;
	
	@Column(name = "CUSTSTATUS")
	private boolean custStatus;
	
	@Column(name = "DEPOSIT")
	@NotNull
	private int deposit;
	
	@Column(name = "LASTUPDATE")
	//@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String lastUpd; //Last Update of Cust Details

	public Customer() {	}

	public long getCustId() {
		return custId;
	}

	public void setCustId(long custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getNric() {
		return nric;
	}

	public void setNric(String nric) {
		this.nric = nric;
	}

	public String getCustEmail() {
		return custEmail;
	}

	public void setCustEmail(String custEmail) {
		this.custEmail = custEmail;
	}

	public String getCustPhoneNo() {
		return custPhoneNo;
	}

	public void setCustPhoneNo(String custPhoneNo) {
		this.custPhoneNo = custPhoneNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public boolean isCustStatus() {
		return custStatus;
	}

	public void setCustStatus(boolean custStatus) {
		this.custStatus = custStatus;
	}

	public String getLastUpd() {
		return lastUpd;
	}

	public void setLastUpd(String lastUpd) {
		this.lastUpd = lastUpd;
	}

	public int getDeposit() {
		return deposit;
	}

	public void setDeposit(int deposit) {
		this.deposit = deposit;
	}

	@Override
	public String toString() {
		return "Customer [custId=" + custId + ", custName=" + custName + ", nric=" + nric + ", custEmail=" + custEmail
				+ ", custPhoneNo=" + custPhoneNo + ", address=" + address + ", city=" + city + ", dob=" + dob
				+ ", custStatus=" + custStatus + ", deposit=" + deposit + ", lastUpd=" + lastUpd + "]";
	}

	}
