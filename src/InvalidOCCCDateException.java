//Ian Moore
//OCCC Spring 2025
//Advanced Java
//Exception thrown by OCCCDate
 
public class InvalidOCCCDateException extends IllegalArgumentException
{
    OCCCDate correctedDate;
    
    public InvalidOCCCDateException(String invalidDate, OCCCDate correctedDate)
    {
        super("Invalid date: " + "'" + invalidDate + "'");
        this.correctedDate = correctedDate;
    }
}
