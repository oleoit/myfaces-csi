package org.apache.myfaces.examples.facelets;

import java.util.Date;

import javax.faces.context.FacesContext;

public class UpdateStudentBacking
{

    private String id;

    private String name;

    private String lastName;

    private Date dateOfBirth;

    private String phone;

    public UpdateStudentBacking()
    {
        super();
        FacesContext context = FacesContext.getCurrentInstance();
        StudentDataBean sdb = (StudentDataBean) context.getExternalContext()
                .getSessionMap().get("students");

        Student s = sdb.getUpdateStudent();
        if (s != null)
        {
            this.setId(s.getId());
            this.setName(s.getName());
            this.setLastName(s.getLastName());
            this.setDateOfBirth(s.getDateOfBirth());
            this.setPhone(s.getPhone());
        }
    }

    public String update()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        StudentDataBean sdb = (StudentDataBean) context.getExternalContext()
                .getSessionMap().get("students");

        Student s = sdb.getUpdateStudent();

        s.setId(this.getId());
        s.setName(this.getName());
        s.setLastName(this.getLastName());
        s.setDateOfBirth(this.getDateOfBirth());
        s.setPhone(this.getPhone());

        return "success";
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public Date getDateOfBirth()
    {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }

}
