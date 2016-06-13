/**
 * Title: class XMLMemoListReader
 * Description: This class reads an XML file representing a list
 * of Memos and contructs a MemoList.
 * Copyright: Copyright (c) 2003
 * Organisation: Dept. of Computer Science, University College London
 * @author Graham Roberts
 * @version 0.1 2003
 */

import org.xml.sax.InputSource;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import java.io.Reader;

public class XMLMemoListReader extends DefaultHandler
{
  private Reader source;
  private Memo currentMemo;
  private MemoList memoList;
  private StringBuffer buffer;

  public XMLMemoListReader(Reader source)
  {
    this.source = source;
  }

  public MemoList read()
  {
    buffer = new StringBuffer();
    memoList = new MemoList();
    SAXParserFactory factory = SAXParserFactory.newInstance();
    try
    {
      SAXParser saxParser = factory.newSAXParser();
      saxParser.parse(new InputSource(source),this);
    }
    catch (Exception e)
    {
      return null;
    }
    return memoList;
  }

  public void startElement(String uri,
                           String localName,
                           String qName,
                           Attributes attributes)
                           throws SAXException
  {
    if (qName.equals("Memo"))
    {
      currentMemo = new Memo();
    }
    else
    if (qName.equals("Title"))
    {
      buffer.setLength(0);
    }
  }

  public void endElement(String uri,
                         String localName,
                         String qName)
                         throws SAXException
  {
    if (qName.equals("Memo"))
    {
      memoList.add(currentMemo);
    }
    else
    if (qName.equals("Title"))
    {
      currentMemo.setTitle(buffer.toString());
      buffer.setLength(0);
    }
  }

  public void characters(char[] ch,
                         int start,
                         int length)
                         throws SAXException
  {
    buffer.append(ch,start,length);
  }
}
