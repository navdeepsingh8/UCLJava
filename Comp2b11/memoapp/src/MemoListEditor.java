/**
 * Title: class MemoListEditor
 * Description: A mediator between a MemoList and a MemoListView. It recieves
 * requests from the view caused by user actions (e.g., clicking a button),
 * tells the view when to update its display, and sends information to/from
 * the MemoList. Neither the MemoList or the MemoListView know about each other.
 * This class deals with all interaction between them that is needed.
 * Copyright: Copyright (c) 2003
 * Organisation: Dept. of Computer Science, University College London
 * @author Graham Roberts
 * @version 0.1 2003
 */

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;

public class MemoListEditor
{
  private MemoList list;
  private MemoListView view;
  private int position;
  
  public MemoListEditor(MemoList list, MemoListView view)
  {
    this.list = list;
    this.view = view;
    position = 0;
    view.setEditor(this);
    setMemoTitle(position);
  }

  private void setMemoTitle(int n)
  {
    Memo memo = null;
    try
    {
      memo = list.get(n);
      position = n;
      view.setMemoTitle(memo.getTitle());
    }
    catch (MemoListException e)
    {
    }
  }
  
  public void forward()
  {
    setMemoTitle(position+1);
  }
  
  public void backward()
  {
    setMemoTitle(position-1);
  }
  
  public void save()
  {
    File destination = view.getFileToSave();
    if (destination != null)
    {
      writeToFile(destination);
    }
  }

  public void writeToFile(File outputFile)
  {
    try
    {
      FileWriter destination = new FileWriter(outputFile);
      XMLMemoListWriter writer = new XMLMemoListWriter(destination);
      writer.write(list);
      destination.close();
    }
    catch (IOException e)
    {
      // TODO: deal with failed save
    }
  }

  public void load()
  {
    File source = view.getFileToLoad();
    if (source != null)
    {
      readFromFile(source);
    }
  }

  public void readFromFile(File sourceFile)
  {
    MemoList newList = null;
    try
    {
      FileReader source = new FileReader(sourceFile);
      XMLMemoListReader reader = new XMLMemoListReader(source);
      newList = reader.read();
      source.close();
    }
    catch (IOException e)
    {
      // TODO: deal with failed save
    }

    if (newList != null)
    {
      list = newList;
      position = 0;
      view.setMemoTitle("");
      setMemoTitle(position);
    }
  }
}