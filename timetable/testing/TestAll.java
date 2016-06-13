
package timetable.testing ;

import junit.framework.* ;

public class TestAll
{
    public static Test suite()
    {
       	TestSuite suite = new TestSuite("All Tests") ;
	suite.addTestSuite(TimeTableDataStructureTest.class) ;
	suite.addTestSuite(URLDataStructureTest.class) ;
	suite.addTestSuite(SourceReaderTest.class) ;
	suite.addTestSuite(Table_LexerTest.class) ;
	suite.addTestSuite(URL_LexerTest.class) ;
	return suite ;
    }
    
    public static void main(String[] args)
    {
        junit.textui.TestRunner.run(suite()) ;
    }
}  
