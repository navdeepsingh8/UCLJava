/**
 * Title:  class XMLMemoListWriterEmptyTest
 * Description: Test writing an empty memo list to an XML file works.
 * Copyright: Copyright (c) 2003
 * Organisation: Dept. of Computer Science, University College London
 * @author Graham Roberts
 * @version 0.1 2003
 */

import org.custommonkey.xmlunit.*;
import java.io.StringWriter;

public class XMLMemoListWriterEmptyTest extends XMLTestCase
{
  private StringWriter destination;
  private XMLMemoListWriter writer;
  private MemoList memoList;

  protected void setUp() throws Exception
  {
    destination = new StringWriter();
    writer = new XMLMemoListWriter(destination);
    memoList = new MemoList();
  }

  public void testWriteEmptyList() throws Exception
  {
    writer.write(memoList);
    assertXMLEqual("Should have written an empty <MemoList>",
      "<MemoList></MemoList>",
      destination.toString());
  }
}
