/**
 * Title: class Person
 * Description: A simple class for objects representing a phone book entry
 * consisting of a person's name and phone number.
 * Copyright: Copyright (c) 2000
 * Company: Dept. of Computer Science, University College London
 * @author Graham Roberts
 * @version 1.0
 */ 
public class Person
{
  private String name ;
  private String phoneNumber ; 
  public Person(String aName, String aNumber)
  {
    name = aName ;
    phoneNumber = aNumber ;
  }
  public String getName()
  {
    return name ;
  }
  public String getNumber()
  {
    return phoneNumber ;
  }
}