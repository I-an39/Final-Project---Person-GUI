//Ian Moore
//OCCC Spring 2025
//Advanced Java
//Person child class with govID field

public class RegisteredPerson extends Person 
{
    private String govID;
    
    public RegisteredPerson(String firstName, String lastName, OCCCDate dob, String govID)
    {
        super(firstName, lastName, dob);
        this.govID = govID;
    }
    
    public RegisteredPerson(Person p, String govID)
    {
        super(p);
        this.govID = govID;
    }
    
    public RegisteredPerson(RegisteredPerson p)
    {
        this(p, p.govID);
    }
    
    public String getGovernmentID()
    {
        return govID;
    }
    
    @Override
    public String toString()
    {
        return super.toString() + " [ " + govID + " ]";
    }
    
    public boolean equals(RegisteredPerson p)
    {
        return super.equals(p) && govID.equalsIgnoreCase(p.govID);
    }
    
    @Override
    public boolean equals(Person p)
    {
        return super.equals(p);
    }
}
