package kodlamaio.hrms.entities.concretes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import kodlamaio.hrms.entities.abstracts.User;

@Entity
@Table(name="job_seekers")
public class JobSeeker extends User {

	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="identity_number")
	private String identityNumber;
	
	@Column(name="birth_year")
	private int birthYear;
	
	public JobSeeker() {
		
	}
	public JobSeeker(String firstName, String lastName, String identityNumber, int birthYear) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.identityNumber = identityNumber;
		this.birthYear = birthYear;
	}
	public String getFirstName(){return firstName;}
	public void setFirstName(String firstName){this.firstName = firstName;}
	public String getLastName(){return lastName;}
	public void setLastName(String lastName){this.lastName = lastName;}
	public String getIdentityNumber(){return identityNumber;}
	public void setIdentityNumber(String identityNumber){this.identityNumber = identityNumber;}
	public int getBirthYear(){return birthYear;}
	public void setBirthYear(int birthYear){this.birthYear = birthYear;}

	
}
