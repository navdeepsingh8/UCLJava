/**
 * Title: class XMLMemoListWriter
 * Description: This class takes a MemoList and writes an XML-based
 * representation to a Writer.
 * Copyright: Copyright (c) 2003
 * Organisation: Dept. of Computer Science, University College London
 * @author Graham Roberts
 * @version 0.1 2003
 */

import java.io.Writer;
import java.io.IOException;

public class XMLMemoListWriter implements MemoListWriter
{
  private Writer destination;

  public XMLMemoListWriter(Writer destination)
  {
    this.destination = destination;
  }

  public void write(MemoList memoList) throws IOException
  {
    destination.write("<MemoList>");
    writeMemoList(memoList);
    destination.write("</MemoList>");
    destination.flush();
  }

  private void writeMemoList(MemoList memoList) throws IOException
  {
    if (memoList.size() > 0)
    {
      for (int n = 0 ; n < memoList.size(); n++)
      {
        String title = "";
        try
        {
          title = memoList.get(n).getTitle();
        }
        catch (MemoListException e)
        {
          // TODO: Invalid memo in list when writing to file
        }
        writeMemo(title);
      }
    }
  }

  private void writeMemo(String title) throws IOException
  {
    destination.write("<Memo>");
    destination.write("<Title>");
    destination.write(title);
    destination.write("</Title>");
    destination.write("</Memo>");
  }
}
