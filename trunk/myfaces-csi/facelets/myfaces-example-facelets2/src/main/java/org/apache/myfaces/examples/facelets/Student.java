package org.apache.myfaces.examples.facelets;

import java.io.Serializable;
import java.util.Date;

public class Student implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -2814441871447686286L;

    private String id;

    private String name;

    private String lastName;

    private Date dateOfBirth;

    private String phone;

    public Student(String id, String name, String lastName, Date dateOfBirth,
            String phone)
    {
        super();
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
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

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Student)
        {
            if (((Student) obj).getId().equals(this.getId()))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }

}
