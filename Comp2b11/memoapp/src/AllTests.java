/**
 * Title: class AllTests
 * Description: Test Suite for all tests of the MemoApp application.
 * Copyright: Copyright (c) 2003
 * Organisation: Dept. of Computer Science, University College London
 * @author Graham Roberts
 * @version 0.1 2003
 */
 
import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class AllTests
{

  public static void main(String[] args)
  {
    TestRunner.run(suite());
  }

  public static Test suite()
  {
    TestSuite suite = new TestSuite("Memo app tests");
    suite.addTest(new TestSuite(MemoListEmptyTest.class));
    suite.addTest(new TestSuite(MemoListWithOneMemoTest.class));
    suite.addTest(new TestSuite(MemoListWithTwoMemosTest.class));
    suite.addTest(new TestSuite(MemoListEditorInitialisationTest.class));
    suite.addTest(new TestSuite(MemoListEditorEmptyTest.class));
    suite.addTest(new TestSuite(MemoListEditorForwardTest.class));
    suite.addTest(new TestSuite(MemoListEditorBackwardTest.class));
    suite.addTest(new TestSuite(MemoListEditorWriteFileTest.class));
    suite.addTest(new TestSuite(MemoListEditorReadEmptyFileTest.class));
    suite.addTest(new TestSuite(MemoListEditorReadFileTest.class));
    suite.addTest(new TestSuite(SwingMemoListViewInitialisationTest.class));
    suite.addTest(new TestSuite(SwingMemoListViewForwardTest.class));
    suite.addTest(new TestSuite(SwingMemoListViewBackwardTest.class));
    suite.addTest(new TestSuite(SwingMemoListViewSaveTest.class));
    suite.addTest(new TestSuite(SwingMemoListViewLoadTest.class));
    suite.addTest(new TestSuite(XMLMemoListWriterEmptyTest.class));
    suite.addTest(new TestSuite(XMLMemoListWriterTest.class));
    suite.addTest(new TestSuite(XMLMemoListReaderEmptyTest.class));
    suite.addTest(new TestSuite(XMLMemoListReaderTest.class));
    suite.addTest(new TestSuite(MemoTest.class));
    return suite;
  }
}
