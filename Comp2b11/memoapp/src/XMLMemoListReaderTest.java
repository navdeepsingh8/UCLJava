/**
 * Title:  class XMLMemoListReaderTest
 * Description: Test reading a memo list from an XML file works.
 * Copyright: Copyright (c) 2003
 * Organisation: Dept. of Computer Science, University College London
 * @author Graham Roberts
 * @version 0.1 2003
 */

import junit.framework.TestCase;
import java.io.StringReader;

public class XMLMemoListReaderTest extends TestCase
{
  private String twoMemoList;
  private String invalidList;

  public void setUp()
  {
    twoMemoList =
      "<MemoList>" +
        "<Memo>" +
          "<Title>" +
            "Title1" +
          "</Title>" +
        "</Memo>" +
        "<Memo>" +
          "<Title>" +
            "Title2" +
          "</Title>" +
        "</Memo>" +
      "</MemoList>";
    invalidList = twoMemoList.substring(0,35);
  }

  public void testReadTwoMemoList()
  {
    XMLMemoListReader reader = new XMLMemoListReader(new StringReader(twoMemoList));
    MemoList memoList = reader.read();
    assertEquals("Two memos should have been read", 2, memoList.size());
  }

  public void testReadInvalidList()
  {
    XMLMemoListReader reader = new XMLMemoListReader(new StringReader(invalidList));
    MemoList memoList = reader.read();
    assertNull("List read should have failed",memoList);
  }
}
