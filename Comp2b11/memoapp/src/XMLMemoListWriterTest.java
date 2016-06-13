/**
 * Title:  class XMLMemoListWriterTest
 * Description: Test writing a memo list to an XML file works.
 * Copyright: Copyright (c) 2003
 * Organisation: Dept. of Computer Science, University College London
 * @author Graham Roberts
 * @version 0.1 2003
 */

import org.custommonkey.xmlunit.*;
import java.io.StringWriter;

public class XMLMemoListWriterTest extends XMLTestCase
{
  private StringWriter destination;
  private XMLMemoListWriter writer;
  private MemoList memoList;

  protected void setUp() throws Exception
  {
    destination = new StringWriter();
    writer = new XMLMemoListWriter(destination);
    memoList = new MemoList();
    Memo memo1 = new Memo();
    memo1.setTitle("Title1");
    memoList.add(memo1);
  }

  public void testWriteListWithOneMemo() throws Exception
  {
    String expectedOutput =
      "<MemoList>" +
        "<Memo>" +
          "<Title>" +
             "Title1" +
          "</Title>" +
        "</Memo>" +
      "</MemoList>";
    writer.write(memoList);
    assertXMLEqual("Should have written one <Memo>",
      expectedOutput,
      destination.toString());
  }

  public void testWriteListWithTwoMemos() throws Exception
  {
    Memo memo2 = new Memo();
    memo2.setTitle("Title2");
    memoList.add(memo2);
    String expectedOutput =
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
    writer.write(memoList);
    assertXMLEqual("Should have written two <Memo>s",
      expectedOutput,
      destination.toString());
  }
}