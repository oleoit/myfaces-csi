package org.apache.myfaces.examples.facelets;

import java.util.Date;

import javax.faces.context.FacesContext;

public class CreateStudentBacking {

	private String id;

	private String name;

	private String lastName;

	private Date dateOfBirth;

	private String phone;

	public CreateStudentBacking() {
		super();
	}
	
	public String create(){
		FacesContext context = FacesContext.getCurrentInstance();
		StudentDataBean sdb = (StudentDataBean)context.getExternalContext().getSessionMap().get("students");

		Student s = new Student(
				this.getId(),
				this.getName(),
				this.getLastName(),
				this.getDateOfBirth(),
				this.getPhone());
		sdb.createStudent(s);
		return "success";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

}
