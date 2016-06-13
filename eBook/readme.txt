/**
 * Title:        EBook Application
 * Description:  Readme file.
 * Copyright:    Copyright (c) 2001
 * @author Navdeep Daheley
 * @version 1.0 18/12/2001
 */

Compilation Notes:

The full package/class names of the eBook classes and interfaces:

bookmanager.BookDataStructure
bookmanager.BookMark
bookmanager.BookModel
bookmanager.BookParser
bookmanager.BookCreatorIF
bookmanager.BookParserIF
bookmanager.BookReaderIF
bookmanager.testing.BookDataStructureTest
bookmanager.testing.BookParserTest
ebookapp.AppFrame
ebookapp.AppFrame_AboutBox
ebookapp.EBook
ebookapp.JTextAreaWriter
ebookapp.Model

These are the additional files included:

bookmanager/testing/book1.xml	small test xml file for bookmanager.testing.BookParserTest class
ebookapp/images/back.gif		toolbar gif
ebookapp/images/forward.gif		toolbar gif
ebookapp/images/open.gif		toolbar gif
ebookapp/images/booktree.gif	toolbar gif
ebookapp/images/chaptree.gif	toolbar gif
ebookapp/images/ebook.gif		toolbar gif


Known Issues:

All xml e-books provided in the books directory of the example application were tried.
For two books, hfinn10.xml and sawyr10.xml, the title and author information does not parse correctly.

Certain combinations of loading different e-books can cause the application to give run time errors,
and the application has to be restarted before another e-book can be loaded.

I was not able to run any successful tests in the bookmanager.testing.BookParserTest class, this was
because my test xml file could not succesfully be loaded.

I was looking to resolve these issues as my next development step but due to my timescaling I was unable to do so.


Contact:

If you have any queries or questions please do not hesitate to contact me at my CS Department e-mail address.
n.daheley@cs.ucl.ac.uk