package crg.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table (name = "vehicles")
@DynamicUpdate
public class Vehicle {
	@Id // this is the primary key for this record
	@GeneratedValue(strategy = GenerationType.IDENTITY)  // defines how this id is to be generated....??
	@Column(name="VEHID")  // The following attribute is linked to this column in the db table
	private long vehId;

	@Column(name="VEHICLETYPE")
	@Size (min = 1, max = 10)
	@NotNull
	private String vehType;

	@Column(name="VEHICLEBRAND")
	@Size (min = 3, max = 15)
	@NotNull
	private String vehBrand;

	@Column (name = "VEHREGNO")
	@Size (min = 3, max = 8)
	@NotNull
	private String vehRegNo;

	@Column(name="DAILYRATE")
	@Min(value=10)
	@Max(value=300)
	@NotNull
	private int dailyRate;
	
//	@Column(name="HOURLYRATE")
//	@Min(value=10)
//	@Max(value=30)
//	@NotNull
//	private float hourlyRate;
    
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "VEHSTTSID", nullable = false)
    private VehStatus vehStatus;

	public Vehicle() {		}

	public long getVehId() {
		return vehId;
	}

	public void setVehId(long vehId) {
		this.vehId = vehId;
	}

	public String getVehType() {
		return vehType;
	}

	public void setVehType(String vehType) {
		this.vehType = vehType;
	}

	public String getVehBrand() {
		return vehBrand;
	}

	public void setVehBrand(String vehBrand) {
		this.vehBrand = vehBrand;
	}

	public String getVehRegNo() {
		return vehRegNo;
	}

	public void setVehRegNo(String vehRegNo) {
		this.vehRegNo = vehRegNo;
	}

	public int getDailyRate() {
		return dailyRate;
	}

	public void setDailyRate(int dailyRate) {
		this.dailyRate = dailyRate;
	}

//	public float getHourlyRate() {
//		return hourlyRate;
//	}
//
//	public void setHourlyRate(float hourlyRate) {
//		this.hourlyRate = hourlyRate;
//	}
	
	public VehStatus getVehStatus() {
		return vehStatus;
	}

	public void setVehStatus(VehStatus vehStatus) {
		this.vehStatus = vehStatus;
	}

	@Override
	public String toString() {
		return "Vehicle [vehId=" + vehId + ", vehType=" + vehType + ", vehBrand=" + vehBrand + ", vehRegNo=" + vehRegNo
				+ ", dailyRate=" + dailyRate + ", vehStatus=" + vehStatus + "]";
	}
	
	
}