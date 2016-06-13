/**
 * Title:        EBook Application 
 * Description:  Interface for getting data out of the data structure representing
 * storing books. 
 * Copyright:    Copyright (c) 2001
 * @author Navdeep Daheley
 * @version 1.0 13/12/2001
 */
package bookmanager ;  
 
import java.util.*;

public interface BookReaderIF 
{

    /**
     * Return list of book titles currently available.
     * @return List of Strings.
     */
    public List getBookTitles() ; 

    /**
     * Return Book object corresponding to string book.
     * @return Book.
     */
    public BookDataStructure.Book getBook(String book) ;
    
    /**
     * Return author of selected book.
     * @return String.
     */
    public String getAuthor(String book) ;
    
    /**
     * Return the part names.
     * @return List.
     */
    public List getParts(String book) ;
    
    /**
     * Return the chapter numbers.
     * @return List.
     */
    public List getChapters(String book, String part) ;
    
    /**
     * Returns a chapter title.
     * @return String.
     */
    //public String getChapterTitle(String book, String part, String chapnum) ;
    
    /**
     * Return a List of paragraphs.
     * @return List.
     */
    public List getParagraphs(String book, String part, String chapnum) ;
    
}
