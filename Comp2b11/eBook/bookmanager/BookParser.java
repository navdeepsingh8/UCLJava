/**
 * Title:        EBook Application
 * Description:  Class to parse a book and store result into books data structure,
 * Uses SAX2.0 compliant parser (such as Xerces 1.4.3)
 * To compiler this file the xerces.jar file must be in the classpath.
 * Get books from http://gutenberg.hwg.org/checkdoc2.html
 * Copyright:    Copyright (c) 2001
 * Company:      Dept. of Computer Science, University College London
 * @author Graham Roberts
 * @version 1.0 01/10/01
 */
package bookmanager ;

import java.io.* ;
import java.util.* ;
// Package org.xml.sax provides a parser implementing the SAX 2.0 standard.
import org.xml.sax.* ;
import org.xml.sax.helpers.* ;


public class BookParser extends DefaultHandler implements BookParserIF
{

  /** 
   * Default parser to use 
   */
  private static final String vendorParserClass = "org.apache.xerces.parsers.SAXParser" ;  
  private XMLReader reader ;
  
  private BookCreatorIF bookdata ;
    
  /**
   * Initialise a book parser.
   */
  public BookParser(BookCreatorIF bookDataStructure) throws SAXException
  {
    reader = XMLReaderFactory.createXMLReader(vendorParserClass) ;
    reader.setFeature("http://xml.org/sax/features/validation", true) ;
    reader.setContentHandler(this) ;   
    bookdata = bookDataStructure ;
    initialiseCallbacks() ;
  }

  /**
   * Parse an xml book.
   * @param file File object for file to be parsed.
   * @return name of book or null
   */
  public String parse(String file)
  {
    // Now do the parse.
    try
    {
        reader.parse(new InputSource(file)) ;
    }
    catch (IOException e)
    {
      System.err.println("Parsing failed with IO error") ;
      e.printStackTrace() ;
    }
    catch (org.xml.sax.SAXException e)
    {
      System.err.println("Parsing failed with SAX error") ;
      e.printStackTrace() ;
    }
    System.out.println("Parsing succeeded") ;
    if (!bookTitle.equals(""))
    {
      return bookTitle ;
    }
    else
    {
      return null ;
    }
  }

  // This part of the class implements the callback code from the SAX parser.
  // Every time an element tag is recognised a callback is made.

  /**
   * Implement callbacks as methods in a callback object. This class
   * declares the basic callback structure and default methods.
   */
  private abstract class Callback
  {
    public void start() {}
    public void end() {}
  }

  /**
   * Hash table used to map from element names to callback objects.
   */
  private Map elementMap = new HashMap() ;
  /**
   * Element stack to keep track of element nesting in the XML file.
   */
  Stack stack = new Stack() ;
   
  private StringBuffer text = new StringBuffer() ;
  private String bookTitle = "" ;
  private String partTitle = "" ;
  private String chapterTitle = "" ;
  private String chapnum = "" ;
    
  private void initialiseCallbacks()
  {
    elementMap.put("frontmatter", new Callback()
    {
      public void start()
      {
        stack.push("frontmatter") ;
        text.setLength(0) ;
      }
      public void end()
      {
        stack.pop() ;
      }
    }) ;
    
    elementMap.put("title", new Callback()
    {
      public void end()
      {
        if (!stack.empty() && stack.peek().equals("frontmatter"))
        {
          bookTitle = text.toString() ;
          text.setLength(0) ;
          bookdata.newBook(bookTitle) ;
        }
        if (stack.peek().equals("part"))
        {
          partTitle = text.toString() ;
          text.setLength(0) ;
          bookdata.addPart(bookTitle,partTitle) ;        
        }
        if (stack.peek().equals("chapter"))
        {
          chapterTitle = text.toString() ;
          text.setLength(0) ;
          bookdata.addChapterTitle(bookTitle,partTitle,chapnum,chapterTitle) ;        
        }
      }
    }) ;
    
    elementMap.put("author", new Callback()
    {
      public void end()
      {
        String author = text.toString() ;
        text.setLength(0) ;
        bookdata.setAuthor(bookTitle,author) ;
      }
    }) ;

    elementMap.put("toc", new Callback()
    {
      public void start()
      {
        stack.push("toc") ;
        text.setLength(0) ;
      }
      public void end()
      {
        stack.pop() ;
        text.setLength(0) ;
      }
    }) ;
    
    elementMap.put("part", new Callback()
    {
      public void start()
      {
        stack.push("part") ;
        text.setLength(0) ;
      }
      public void end()
      {
        stack.pop() ;
        text.setLength(0) ;
      }
    }) ;

    elementMap.put("chapter", new Callback()
    {
      public void start()
      {
        stack.push("chapter") ;
        text.setLength(0) ;
      }
      public void end()
      {
        stack.pop() ;
        text.setLength(0) ;
      }
    }) ;
    
    elementMap.put("chapnum", new Callback()
    {
      public void end()
      {
        chapnum = text.toString() ;
        //bookdata.setChapterNumber(bookTitle,partTitle,chapnum) ;
        text.setLength(0) ;
      }
    }) ;
    
    elementMap.put("para", new Callback()
    {
      public void end()
      {
        if (!stack.empty() && stack.peek().equals("chapter"))
        {
          if (bookTitle.equals("") || (chapterTitle.equals("") && chapnum.equals(""))) return ;
          String paratext = text.toString() ;
          bookdata.addParagraph(bookTitle,partTitle,chapnum,chapterTitle,paratext) ;
          text.setLength(0) ;
        }
      }
    }) ;

  }


  /**
   * SAX callback method. Start of an element seen (opening tag).
   */
  public void startElement(String namespaceURI, String localName,
                           String qName, Attributes atts)
  {
    Callback callBack = (Callback)elementMap.get(localName) ;
    if (callBack != null)
    {
      callBack.start() ;
    } 
  }

  /**
   * Sax callback method. End of an element seen (closing tag).
   */
  public void endElement(String namespaceURI, String localName, String qName)
  {    
    Callback callBack = (Callback)elementMap.get(localName) ;
    if (callBack != null)
    {
      callBack.end() ;
    } 
  }
    
  /**
   * SAX callback method giving the last piece of text read from the XML file.
   * Text is accumulated in the StringBuffer text until used when an element
   * is processed.
   */
  public void characters(char[] buffer, int start, int length)
  {
    text.append(buffer,start,length) ;
  }
}