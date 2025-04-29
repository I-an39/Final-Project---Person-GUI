//Ian Moore
//OCCC Spring 2025
//Advanced Java
//Parent class for all Person subclasses

import java.io.Serializable;

public class Person implements Serializable, Comparable<Person>
{
    private String firstName;
    private String lastName;
    private OCCCDate dob;
    
    public Person(String firstName, String lastName, OCCCDate dob)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
    }
 
    public Person(Person p)
    {
        firstName = p.firstName;
        lastName = p.lastName;
        dob = p.dob;
    }
    
    public String getFirstName()
    {
        return firstName;
    }
    
    public String getLastName() 
    {
        return lastName;
    }
    
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }
    
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }
    
    public OCCCDate getDOB() 
    {
        return dob;
    }
    
    @Override
    public String toString()
    {
        return lastName + ", " + firstName + " [ " + dob.toString() + " ] ";
    }
    
    @Override 
    public int compareTo(Person p)
    {   
        if(lastName.compareTo(p.getLastName()) > 0) {
            return 1;
        }
        else if(lastName.compareTo(p.getLastName()) < 0) {
            return -1;
        }
        
        return 0;
    }
    
    public boolean equals(Person p)
    {
        return firstName.equalsIgnoreCase(p.firstName) && lastName.equalsIgnoreCase(p.lastName);
    }
    
    public void eat()
    {
        System.out.println(getClass().getName() + " " + toString() + " is eating...");
    }
    
    public void sleep()
    {
        System.out.println(getClass().getName() + " " + toString() + " is sleeping...");
    }
    
    public void play()
    {
        System.out.println(getClass().getName() + " " + toString() + " is playing...");
    }
    
    public void run()
    {
        System.out.println(getClass().getName() + " " + toString() + " is running...");
    }
}
