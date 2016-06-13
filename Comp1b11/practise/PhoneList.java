/**
 * Title: class PhoneList
 * Description: A simple phone list class that managers a collection of
 * Person objects..
 * Copyright: Copyright (c) 2000
 * Company: Dept. of Computer Science, University College London
 * @author Graham Roberts
 * @version 1.0
 */
import java.util.ArrayList ;
public class PhoneList
{
  ArrayList phoneList ;
  public PhoneList()
  {
    phoneList = new ArrayList() ;
  }
  public void add(String name, String phoneNumber)
  {
    phoneList.add(new Person(name,phoneNumber)) ;
  }  
  public void remove(String name)
  {
    for (int i = 0 ; i < phoneList.size() ; i++)
    {
      Person p = (Person)phoneList.get(i) ;
      if (p.getName().equals(name))
      {
        phoneList.remove(i) ;
      }
    }
  }
  public String getPhoneNumber(String name)
  {
    for (int i = 0 ; i < phoneList.size() ; i++)
    {
      Person p = (Person)phoneList.get(i) ;
      if (p.getName().equals(name))
      {
        return p.getNumber() ;
      }
    }
    // No match found.
    return "" ;    
  }
  public void writeToFile(FileOutput file)
  {
    for (int i = 0 ; i < phoneList.size() ; i++)
    {
      Person p = (Person)phoneList.get(i) ;
      file.writeString(p.getName()) ;
      file.writeNewline() ;
      file.writeString(p.getNumber()) ;
      file.writeNewline() ;
    }    
  }
  public void readFromFile(FileInput file)
  {
    String name = file.readString() ;
    String phone = file.readString() ;
    while (!file.eof())
    {
      add(name,phone) ;
      name = file.readString() ;
      phone = file.readString() ;
    }
  }
}