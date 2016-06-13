/**
 * Title:        EBook Application
 * Description:  Data class to hold current position in book.
 * Copyright:    Copyright (c) 2001
 * @author Navdeep Daheley
 * @version 1.0 17/12/2001
 */
package bookmanager;

public class BookMark {
	
	public String bookTitle ;
	public String partTitle ;
	public String chapTitle ;
	public boolean firstPage ;
	
	public BookMark(String title)
	{
		this.bookTitle = title ;
		firstPage = true ;
	}
	
	/**
	 * Read title page.
	 */
	public void doneFirst()
	{
		firstPage = false ;
	}
	
	public void setPart(String partTitle)
	{
		this.partTitle = partTitle ;
	}
	
	public void setChapter(String partTitle, String chapTitle)
	{
		this.partTitle = partTitle ;		
		this.chapTitle = chapTitle ;
	}
	
	public boolean getFirstPage()
	{
		return firstPage ;
	}
	
	public String getTitle()
	{
		return bookTitle ;
	}
	
	public String getPart()
	{
		return partTitle ;
	}
	
	public String getChap()
	{
		return chapTitle ;
	}
	
}
