package crg.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "employees")
public class Employee {
	@Id
	@Column(name = "emp_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long empId;

	@NotNull
	@Size(min = 2, max = 30)
	@Column(name = "EMP_NAME")
	private String empName;

	@Column(name = "PASSWORD")
	@Size(min = 6, max = 9)
	private String ePassword;

	@Size(min = 8, max = 30)
	@Email(message = " Should be a valid Email Address")
	private String eEmail;

	@Column(name = "PHONENO")
	@Size(min = 8, max = 13)
	private String ePhoneNo;

	@NotNull
	@Column(name = "STARTDATE")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String eStartDate;

	@Column(name = "STATUS")
	private boolean eStatus;

	public Employee() {
	}

	public Employee(@NotNull @Size(min = 2, max = 30) String empName, String ePassword, String eEmail, String ePhoneNo,
			String eStartDate, boolean eStatus) {
		this.empName = empName;
		this.ePassword = ePassword;
		this.eEmail = eEmail;
		this.ePhoneNo = ePhoneNo;
		this.eStartDate = eStartDate;
		this.eStatus = eStatus;

	}

	@ManyToMany
	@JoinTable(name = "e_employee_roles", joinColumns = @JoinColumn(name = "emp_id"), inverseJoinColumns = @JoinColumn(name = "e_role_id"))

	private Set<Role> roles = new HashSet<>();

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Long getEmpId() {
		return empId;
	}

	public void setEmpId(Long empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getePassword() {
		return ePassword;
	}

	public void setePassword(String ePassword) {
		this.ePassword = ePassword;
	}

	public String geteEmail() {
		return eEmail;
	}

	public void seteEmail(String eEmail) {
		this.eEmail = eEmail;
	}

	public String getePhoneNo() {
		return ePhoneNo;
	}

	public void setePhoneNo(String ePhoneNo) {
		this.ePhoneNo = ePhoneNo;
	}

	public String geteStartDate() {
		return eStartDate;
	}

	public void seteStartDate(String eStartDate) {
		this.eStartDate = eStartDate;
	}

	public boolean iseStatus() {
		return eStatus;
	}

	public void seteStatus(boolean eStatus) {
		this.eStatus = eStatus;
	}

	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", empName=" + empName + ", ePassword=" + ePassword + ", eEmail=" + eEmail
				+ ", ePhoneNo=" + ePhoneNo + ", eStartDate=" + eStartDate + ", eStatus=" + eStatus + "]";
	}

}
