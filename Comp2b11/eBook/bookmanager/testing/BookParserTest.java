/**
 * Title:        EBook Application
 * Description:  JUnit Testing class for book parser.
 * Copyright:    Copyright (c) 2001
 * @author Navdeep Daheley
 * @version 1.0 13/12/2001
 */

package bookmanager.testing;

import junit.framework.* ;
import bookmanager.* ;
import java.util.* ;
import org.xml.sax.* ;
import org.xml.sax.helpers.* ;

public class BookParserTest extends TestCase
{
	
	protected BookDataStructure simpleStructure ;
	protected BookParser parser ;
	protected final String bookTitle = "Book Title" ;
	protected final String bookAuthor = "The Author" ;
	protected final String bookPart = "Part One" ;
	protected final String bookChapter = "Chapter One".concat("\n").concat("The Beginning") ;
	protected final ArrayList bookParagraphs = new ArrayList() ;
	{ bookParagraphs.add("This is the first paragraph") ; bookParagraphs.add("The second...") ; }
	
	protected void setUp()
	{
		simpleStructure = new BookDataStructure() ;
		try {
			parser = new BookParser(simpleStructure) ;
		} catch (SAXException s) {}
	}
	
	public void testParse()
	{
		String title = parser.parse("bookmanager/testing/book1.xml") ;
		assertEquals(title, bookTitle) ;
	}
	
	public void testAuthor()
	{
		testParse() ;
		String author = simpleStructure.getAuthor(bookTitle) ;
		assertEquals(author, bookAuthor) ;
	}
	
	public void testPart()
	{
		testParse() ;
		List parts = simpleStructure.getParts(bookTitle) ;
		String part = (String)parts.get(0) ;
		assertEquals(part, bookPart) ;
	}
	
	public void testChapter()
	{
		testParse() ;
		List chapters = simpleStructure.getChapters(bookTitle, bookPart) ;
		String chapter = (String)chapters.get(0) ;
		assertEquals(chapter, bookChapter) ;
	}
	
	public void testParagraphs()
	{
		testParse() ;
		List paragraphs = simpleStructure.getParagraphs(bookTitle, bookPart, bookChapter) ;
		assertEquals(paragraphs, bookParagraphs) ;
	}
		
	public BookParserTest(String name)
	{
		super(name) ;
	}
}
