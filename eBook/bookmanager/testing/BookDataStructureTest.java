/**
 * Title:        EBook Application
 * Description:  JUnit Testing class for book data structure.
 * Copyright:    Copyright (c) 2001
 * @author Navdeep Daheley
 * @version 1.0 13/12/2001
 */

package bookmanager.testing;

import bookmanager.BookDataStructure;
import java.util.ArrayList;
import junit.framework.Assert;
import junit.framework.TestCase;

public class BookDataStructureTest extends TestCase
{
	protected BookDataStructure books;
    protected final String bookTitleA = "Book A";
    protected final String bookTitleB = "Book B";
    protected final String bookAuthor = "Navdeep Daheley";
    protected final String bookPartOneA = "Part One";
    protected final String bookPartTwoA = "Part Two";
    protected final String bookChapterOneNum = "Chap 1";
    protected final String bookChapterTwoNum = "Chap 2";
    protected final String bookChapterThreeNum = "Chap 3";
    protected final String bookChapterOneA = "Chapter One A";
    protected final String bookChapterTwoA = "Chapter Two A";
    protected final String bookChapterThreeA = "Chapter Three A";
    protected final String paragraphOne = "This is the beginning...";
    protected final String paragraphTwo = "This is the continuation...";
    protected final String paragraphThree = "This is another chapter..";
	
    
    protected void setUp()
    {
        books = new BookDataStructure();
    }

    public void testNewBookA()
    {
        books.newBook(bookTitleA);
    }

    public void testSetAuthorA()
    {
        testNewBookA();
        books.setAuthor(bookTitleA, bookAuthor);
    }

    public void testGetAuthor()
    {
        testSetAuthorA();
        String author = books.getAuthor(bookTitleA);
        assertEquals(author, bookAuthor);
    }

    public void testAddPart()
    {
        testSetAuthorA();
        books.addPart(bookTitleA, bookPartOneA);
        books.addPart(bookTitleA, bookPartTwoA);
    }

    public void testGetParts()
    {
        testAddPart();
        ArrayList parts = new ArrayList();
        parts.add(bookPartOneA);
        parts.add(bookPartTwoA);
        assertEquals(parts, books.getParts(bookTitleA));
    }

    public void testAddChaptersA()
    {
        testAddPart();
        books.addChapterTitle(bookTitleA, bookPartOneA, bookChapterOneNum, bookChapterOneA);
        books.addChapterTitle(bookTitleA, bookPartOneA, bookChapterTwoNum, bookChapterTwoA);
        books.addChapterTitle(bookTitleA, bookPartTwoA, bookChapterThreeNum, bookChapterThreeA);
    }

    public void testGetChaptersA()
    {
        testAddChaptersA();
        ArrayList chaptersOne = new ArrayList();
        ArrayList chaptersTwo = new ArrayList();
        chaptersOne.add(bookChapterOneNum.concat("\n").concat(bookChapterOneA));
        chaptersOne.add(bookChapterTwoNum.concat("\n").concat(bookChapterTwoA));
        chaptersTwo.add(bookChapterThreeNum.concat("\n").concat(bookChapterThreeA));
        assertEquals(chaptersOne, books.getChapters(bookTitleA, bookPartOneA));
        assertEquals(chaptersTwo, books.getChapters(bookTitleA, bookPartTwoA));
    }

    public void testAddParagraphsA()
    {
        testAddChaptersA();
        books.addParagraph(bookTitleA, bookPartOneA, bookChapterOneNum, bookChapterOneA, paragraphOne);
        books.addParagraph(bookTitleA, bookPartOneA, bookChapterOneNum, bookChapterOneA, paragraphTwo);
        books.addParagraph(bookTitleA, bookPartOneA, bookChapterTwoNum, bookChapterTwoA, paragraphThree);
    }

    public void testGetParagraphsA()
    {
        testAddParagraphsA();
        ArrayList paragraphsOne = new ArrayList();
        ArrayList paragraphsTwo = new ArrayList();
        paragraphsOne.add(paragraphOne);
        paragraphsOne.add(paragraphTwo);
        paragraphsTwo.add(paragraphThree);
        Assert.assertEquals(paragraphsOne, books.getParagraphs(bookTitleA, bookPartOneA, bookChapterOneNum.concat("\n").concat(bookChapterOneA)));
        Assert.assertEquals(paragraphsTwo, books.getParagraphs(bookTitleA, bookPartOneA, bookChapterTwoNum.concat("\n").concat(bookChapterTwoA)));
    }
    
    public void testNewBookB()
    {
    	books.newBook(bookTitleB) ;
    }
    
    public void testAddChaptersB()
    {
    	testNewBookB() ;
    	books.addChapterTitle(bookTitleB, "", "", bookChapterOneA) ;
    	books.addChapterTitle(bookTitleB, "", "", bookChapterTwoA) ;
    	books.addChapterTitle(bookTitleB, "", "", bookChapterThreeA) ;
    }
    
    public void testGetPartsB()
    {
    	testAddChaptersB() ;
    	ArrayList parts = new ArrayList() ;
    	parts.add("Chapters") ;
    	assertEquals(parts, books.getParts(bookTitleB)) ;
    }
    
    public void testGetChaptersB()
    {
    	testAddChaptersB() ;
        ArrayList chapters = new ArrayList() ;
        chapters.add(bookChapterOneA) ;
        chapters.add(bookChapterTwoA) ;
        chapters.add(bookChapterThreeA) ;
        assertEquals(chapters, books.getChapters(bookTitleB, "Chapters")) ;
    }
    

    public BookDataStructureTest(String name)
    {
        super(name);
    }

   
}
