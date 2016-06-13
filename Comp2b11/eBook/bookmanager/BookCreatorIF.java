/**
 * Title:        EBook Application
 * Description:  Interface for writing to the data structure storing a book in memory.
 * Copyright:    Copyright (c) 2001
 * @author Navdeep Daheley
 * @version 1.0 13/12/2001
 */
package bookmanager ;  
  
import java.util.* ;

public interface BookCreatorIF
{
  /**
   * Create new book in the data structure.
   */
  public void newBook(String title) ;
  
  /**
   * Set author for a book.
   */
  public void setAuthor(String book, String author) ;
  
  /**
   * Add part information to a book.
   */
  public void addPart(String book, String part) ;
  
  /**
   * Add chapter number and title information to a book.
   */
  public void addChapterTitle(String book, String part, String chapnum, String title) ;
  
  /**
   * Add paragraph to the relevent part and chapter of a book.
   */
  public void addParagraph(String book, String part, String chapnum, String chapterTitle, String text) ;
 
}