/**
 * Title: class Memo
 * Description: Representation of a memo - a note with a title and text.
 * Text part not yet added.
 * Copyright: Copyright (c) 2003
 * Organisation: Dept. of Computer Science, University College London
 * @author Graham Roberts
 * @version 0.1 2003
 */

public class Memo
{
  private String title;

  public Memo()
  {
    title = "";
  }

  public void setTitle(String title)
  {
    this.title = title; 
  }

  public String getTitle()
  {
    return title;
  }

}
