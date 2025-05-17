//Ian Moore
//OCCC Spring 2025
//Advanced Java
//RegisteredPerson child class with studentID field


public class OCCCPerson extends RegisteredPerson
{
    private String studentID;
    
    
    public OCCCPerson(RegisteredPerson p, String studentID)
    {
        super(p);
        this.studentID = studentID;
    }
    
    public OCCCPerson(OCCCPerson p)
    {
        this(p, p.studentID);
    }
    
    public String getStudentID()
    {
        return studentID;
    }
    
    @Override 
    public String toString()
    {
        return super.toString() + " [ " + "Student ID: " + studentID + " ]"; 
    }
    
    public boolean equals(OCCCPerson p)
    {
        return super.equals(p) && studentID.equalsIgnoreCase(p.studentID);
    }
    
    @Override
    public boolean equals(RegisteredPerson p)
    {
        return super.equals(p);
    }
    
    @Override
    public boolean equals(Person p)
    {
        return super.equals(p);
    }
}

