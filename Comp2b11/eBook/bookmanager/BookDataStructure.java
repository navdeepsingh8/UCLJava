/**
 * Title:        EBook Application
 * Description:  Data structure to store books, as a hierachy of Maps and Lists.
 * Copyright:    Copyright (c) 2001
 * @author Navdeep Daheley
 * @version 1.0 13/12/2001
 */
package bookmanager ;  
  
import java.util.* ;
import java.text.* ;

public class BookDataStructure implements BookCreatorIF, BookReaderIF
{
 
  /**
   * Top level data structure class for storing a book.
   * Stores Parts, the next level down, in an ArrayList.
   */
  protected class Book {
  	
  	private String title ;
  	private String author ;
  	private ArrayList parts ;
  	private int index ;
  	
  	public Book(String name)
  	{
  		this.title = name ;
  		this.parts = new ArrayList() ;
  	}
  	
  	public String getTitle()
  	{
  		return title ;
  	}
  	
  	public void setAuthor(String name)
  	{
  		author = name ;
  	}
  	
  	public String getAuthor()
  	{
  		return author ;
  	}
  	 	
  	public void addPart(String part)
  	{
  		parts.add(new Part(part)) ;
  	}
  	
  	public Part getPart(String name)
  	{
  		for (int i = 0; i < parts.size(); i++)
  		{
  			Part temp = (Part)parts.get(i) ;
  			if (temp.getTitle().equals(name)) {
  				return temp ;
  			}
  		}
  		return null ;
  	}
  	
  	public List getParts()
  	{
  		ArrayList listParts = new ArrayList() ;
  		for (int i = 0; i < parts.size(); i++)
  		{
  			Part temp = (Part)parts.get(i) ;
  			listParts.add(temp.getTitle()) ;
  		}
  		return listParts ;
  	}
  	
  }
  
 
  /**
   * Middle level data structure class for storing a part.
   * Stores Chapters, the next level down, in an ArrayList.
   */
  protected class Part {
  	
  	private String title ;
  	private ArrayList chapters ;
  	
  	public Part(String name)
  	{
		this.title = name ;
		this.chapters = new ArrayList() ;
    }
    
  	public String getTitle()
  	{
  		return title ;
  	}
  	
  	public void addChapter(String wholeTitle)
  	{
  		chapters.add(new Chapter(wholeTitle)) ;
  	}
  	
  	public Chapter getChapter(String wholeTitle)
  	{
  		for (int i = 0; i < chapters.size(); i++)
  		{
  			Chapter temp = (Chapter)chapters.get(i) ;
  			if (temp.getWholeTitle().equals(wholeTitle)) {
  				return temp ;
  			}
  		}
  		return null ;
  	}
  	
  	public List getChapters()
  	{
  		ArrayList listChapters = new ArrayList() ;
  		for (int i = 0; i < chapters.size(); i++)
  		{
  			Chapter temp = (Chapter)chapters.get(i) ;
  			listChapters.add(temp.getWholeTitle()) ;
  		}
  		return listChapters ;
  	}
  	
  }
  
  /**
   * Bottom level data structure class for storing a book.
   * Stores paragraphs as an ArrayList of Strings.
   */
  protected class Chapter {
  	
  	private String wholeTitle ;
  	private ArrayList paragraphs ;
  	
  	public Chapter(String wholeTitle)
  	{
  		this.wholeTitle = wholeTitle ;
  		this.paragraphs = new ArrayList() ;
  	}
  	
  	public String getWholeTitle()
  	{
  		return wholeTitle ;
  	}
  	
  	public void addParagraph(String para)
  	{
  		paragraphs.add(para) ;
  	}
  	
  	public List getParagraphs()
  	{
  		return paragraphs ;
  	}
  }	  	
  
  /**
   * Use a hash table to store the books.
   */
  Map books = new HashMap() ;

  public BookDataStructure()
  {
  }


  /**
   * BookCreatorIF methods
   */
  
  public void newBook(String name)
  {
    books.put(name,new Book(name)) ;
  }

  public void setAuthor(String book, String author)
  {
    Book theBook = getBook(book) ;
    if (theBook != null)
    {
      theBook.setAuthor(author) ;
    }
  }
  
  public void addPart(String book, String part)
  {
    Book theBook = getBook(book) ;
    if (theBook != null)
    {
      theBook.addPart(part) ;
    }  
  }
  
  public void addChapterTitle(String book, String part, String chapnum, String title)
  {
    Book theBook = getBook(book) ;
    if (theBook != null)
    {
      if (part.equals(""))
      {
      	part = "Chapters" ;
      	if (theBook.getParts().size() == 0)
      	{
      		theBook.addPart(part) ;
      	}
      }
      
      if (chapnum.equals(""))
      {
      	chapnum = title ;
      	theBook.getPart(part).addChapter(chapnum) ;
      } else {
      	String wholeTitle = chapnum.concat("\n").concat(title) ;
      	theBook.getPart(part).addChapter(wholeTitle) ;
      }
    }    
  }

  public void addParagraph(String book, String part, String chapnum, String chapterTitle, String text)
  {
    Book theBook = getBook(book) ;
    if (theBook != null)
    {
      if (part.equals(""))
      {
      	part = "Chapters" ;
      	if (theBook.getParts().size() == 0)
      	{
      		theBook.addPart(part) ;
      	}
      }
      
      if (chapnum.equals(""))
      {
      	chapnum = chapterTitle ;
      	theBook.getPart(part).getChapter(chapnum).addParagraph(text) ;
      } else {
      	String wholeTitle = chapnum.concat("\n").concat(chapterTitle) ;
      	theBook.getPart(part).getChapter(wholeTitle).addParagraph(text) ;
      }
    }   
  }
  
  
  /**
   * BookReaderIF methods
   */
   
  public List getBookTitles()
  {
    return new ArrayList(books.keySet()) ;
  }
  
  public Book getBook(String book)
  {
    return (Book)books.get(book) ;
  }
  
  public String getAuthor(String book)
  {
  	if (getBook(book).getAuthor() != null)
    {
    	return getBook(book).getAuthor() ;
    }
    return "" ;
  }
  
  public List getParts(String book)
  {
  	return getBook(book).getParts() ;
  }
  
  public List getChapters(String book, String part)
  {
  	return getBook(book).getPart(part).getChapters() ;
  }
  
  public List getParagraphs(String book, String part, String chapTitle)
  {
  	return new ArrayList(getBook(book).getPart(part).getChapter(chapTitle).getParagraphs()) ;
  }
  	
}