/**
 * Title:  class XMLMemoListReaderEmptyList
 * Description: Test reading an empty memo list from file works.
 * Copyright: Copyright (c) 2003
 * Organisation: Dept. of Computer Science, University College London
 * @author Graham Roberts
 * @version 0.1 2003
 */

import junit.framework.TestCase;
import java.io.StringReader;

public class XMLMemoListReaderEmptyTest extends TestCase
{
  private String emptyList;

  public void setUp()
  {
    emptyList = "<MemoList></MemoList>";
  }

  public void testReadEmptyList()
  {
    XMLMemoListReader reader = new XMLMemoListReader(new StringReader(emptyList));
    MemoList memoList = reader.read();
    assertEquals("No memos should have been read", 0, memoList.size());
  }
}
