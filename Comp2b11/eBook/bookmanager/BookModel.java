/**
 * Title:        EBook Application
 * Description:  Model used to co-ordinates various components of the program.
 * The aim is to reduce dependencies between components and classes.
 * Copyright:    Copyright (c) 2001
 * @author Navdeep Daheley
 * @version 1.0 13/12/2001
 */
package bookmanager ;

import java.io.* ;
import java.util.* ; 
import ebookapp.Model ;
import ebookapp.JTextAreaWriter ;

public class BookModel implements Model
{
  
  BookParserIF parser = null ;
  JTextAreaWriter writer = null ;
  BookReaderIF data = null ;
  BookMark bookMark = null ;
  ListIterator partIterator = null ;
  ListIterator chapIterator = null ;
  int prevCounter = 0 ;
  int nextCounter = 0 ;

  /**
   * Prepare model.
   * @param parser Parser to be used to parse a book.
   * @param data readable data structure storing book information.
   * @param writer a Writer used to get output to the GUI.
   */
  public BookModel(BookParserIF parser, BookReaderIF data, JTextAreaWriter writer)
  {
    this.parser = parser ;
    this.data = data ;
    this.writer = writer ;
  }


  /**
   * Attempt to load and parse a book file.
   */
  public String loadFile(File file)
  {
    String title = parser.parse(file.getAbsolutePath()) ;
    bookMark = new BookMark(title) ;
    partIterator = data.getParts(title).listIterator() ;
    String firstPart = (String)partIterator.next() ;
    bookMark.setPart(firstPart) ;
    chapIterator = data.getChapters(title, firstPart).listIterator() ;
    writeNext() ;
    return title ;
  }

  
  /**
   * Write a chapter as specified by the navigation tree.
   * @return String
   */
  public String writeChap(String title, String part, String chap)
  {
  	bookMark.setChapter(part, chap) ;
  	return write() ;
  }

  /**
   * Update part and chapter iterators after tree selection.
   */
  protected void catchUp()
  {
  	String title = bookMark.getTitle() ;
  	nextCounter = 1 ;
  	prevCounter = 0 ;
  	
  	List parts = data.getParts(title) ;
  	int partIndex = parts.indexOf(bookMark.getPart()) + 1 ;
  	partIterator = parts.listIterator(partIndex) ;
  	
  	List chapters = data.getChapters(title, bookMark.getPart()) ;
  	int chapIndex = chapters.indexOf(bookMark.getChap()) + 1;
  	chapIterator = chapters.listIterator(chapIndex) ;
  }
  
  /**
   * Write the previous chapter.
   * @return String
   */
  public String writePrev()
  {
  	String title = bookMark.getTitle() ;
  	if (!bookMark.getFirstPage())
  	{
  		catchUp() ;
  		if (chapIterator.hasPrevious())
  		{
  			while (prevCounter < 1)
  			{
  				chapIterator.previous() ;
  				prevCounter++ ;
  			}
  			bookMark.setChapter(bookMark.getPart(), (String)chapIterator.previous()) ;
  			nextCounter = 0 ;
  		}
  		if (partIterator.hasPrevious())
  		{
  			String prevPart = (String)partIterator.previous() ;
  			chapIterator = data.getChapters(title, prevPart).listIterator() ;
  			bookMark.setPart(prevPart) ;
  			while(chapIterator.hasNext())
  			{
  				chapIterator.next() ;
  			}
  			chapIterator.previous() ;
  		}
  		return write() ;
  	}
  	return null ;
  }

    
  /**
   * Write the next chapter.
   * @return String
   */
  public String writeNext()
  {
  	String title = bookMark.getTitle() ;
  	if (bookMark.getFirstPage())
  	{
  		try {
      		writer.writeHeader(title) ;
      		writer.writePara(data.getAuthor(title) + "\n") ;
      	}
      	catch (IOException o)
      	{}
 		bookMark.doneFirst() ;
  	} else {
  		
  		catchUp() ;
  		if (chapIterator.hasNext())
   		{
   			while (nextCounter < 1)
   			{
   				chapIterator.next() ;
   				nextCounter++ ;
   			}
   			bookMark.setChapter(bookMark.getPart(), (String)chapIterator.next()) ;
   			prevCounter = 0 ;
   		} 
   		else if (partIterator.hasNext())
    	{
		  	String nextPart = (String)partIterator.next() ;
			chapIterator = data.getChapters(title, nextPart).listIterator() ;
		  	bookMark.setPart(nextPart) ;
    	}
    	return write() ;
   	 }
   	 return null ;
  }

   	
  /**
   * Write the current chapter.
   * @return String a concatentation of the part and chapter number.
   */
  public String write()
  {
  		String title = bookMark.getTitle() ;
   		String currentPart = bookMark.getPart() ;
  		String currentChap = bookMark.getChap() ;
  		//String currentChapTitle = data.getChapterTitle(title, currentPart, currentChap) ;
  		try {
  			writer.writeHeader(currentChap + "\n") ;
  		}
  		catch (IOException o)
  		{}
  			
  		List paragraphs = data.getParagraphs(title, currentPart, currentChap) ;
      	
      	for (Iterator paraIterator = paragraphs.iterator(); paraIterator.hasNext(); )
      	{
      		try {
      			writer.writePara((String)paraIterator.next() + "\n") ;
      		}
      		catch (IOException o)
      		{}
      	}
    	return bookMark.getPart().concat(" - ").concat(bookMark.getChap()) ;
	}

	
	/**
	 * Return a list of parts for a title for the navigation tree.
	 * @return List of Strings representing part titles within a book.
	 */
	public List treeParts(String title)
	{
		return data.getParts(title) ;
	}

	
	/**
	 * Return a list of chapters for a book part for the navigation tree.
	 * @return List of Strings representing chapter numbers within a book.
	 */
	public List treeChapters(String title, String part)
	{
		return data.getChapters(title, part) ;
	}
}