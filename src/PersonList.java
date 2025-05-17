//Ian Moore
//OCCC Spring 2025
//Advanced Java
//Class that acts as a container for a list of persons and handles serialization
import java.util.ArrayList;
import java.io.*;
import java.util.Iterator;

public class PersonList implements Iterable<Person>
{
    private ArrayList<Person> personArray = new ArrayList<>();
    private String nameAsFile = null;
    private boolean hasBeenModified = false;
    
    public void add(Person p)
    {
        personArray.add(p);
        hasBeenModified = true;
    }
    
    public void delete(Person p)
    {
        personArray.remove(p);
        hasBeenModified = true;
    }

    public boolean isEmpty()
    {
       return personArray.isEmpty();
    }
   
    public boolean hasFileName()
    {
       return nameAsFile != null;
    }

    public boolean hasBeenChangedSinceLastSave()
    {
      return hasBeenModified;
    }
    
    public void clear()
    {
      personArray.clear();
      hasBeenModified = true;
    }

    public int size()
    {
       return personArray.size();
    }
    
    public void save() throws IOException
    {
      FileOutputStream fout = new FileOutputStream(nameAsFile);
      ObjectOutputStream oout = new ObjectOutputStream(fout);
        
      for(int i = 0; i < personArray.size(); i++) {
         oout.writeObject(personArray.get(i));
      }

      hasBeenModified = false;
    }

    public void save(String fileName) throws IOException
    {
      nameAsFile = fileName; 
      FileOutputStream fout = new FileOutputStream(nameAsFile);
      ObjectOutputStream oout = new ObjectOutputStream(fout);
        
      for(int i = 0; i < personArray.size(); i++) {
         oout.writeObject(personArray.get(i));
      }

      hasBeenModified = false;
    }

    public void load(File file) throws Exception
    {
      nameAsFile = file.getPath();
      FileInputStream fis = new FileInputStream(file);
      ObjectInputStream ois = new ObjectInputStream(fis);

      Object o;
      while(true) {
         try {
            o = ois.readObject();

            if(o instanceof OCCCPerson) {
               personArray.add((OCCCPerson)o);
            }
            else if(o instanceof RegisteredPerson) {
               personArray.add((RegisteredPerson)o);
            }
            else {
               personArray.add((Person)o);
            }
         }
         catch(EOFException e) {
            ois.close();
            break;
         }

      }
    }
    
    public Person findPerson(String fullName)
    {
        for(int i = 0; i < personArray.size(); i++) {
            if(fullName.equals(personArray.get(i).getFirstName() + " " + personArray.get(i).getLastName())) {
                return personArray.get(i);
            }
        }
        return null;
    }
   
    public class personIterator implements Iterator<Person>
    {
       private int index = 0;

       @Override 
       public boolean hasNext()
       {
         return index + 1 <= personArray.size();
       }

       @Override
       public Person next()
       {
          return personArray.get(index++);
       }
    }

    @Override 
    public Iterator<Person> iterator()
    {
      return new personIterator();
    }
}
