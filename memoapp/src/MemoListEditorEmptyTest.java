/**
 * Title:  class MemoListEditorEmptyTest
 * Description:  Test the creation of a MemoListEditor with an empty
 * MemoList does the correct initialisation.
 * Copyright:    Copyright (c) 2003
 * Organisation: Dept. of Computer Science, University College London
 * @author Graham Roberts
 * @version 0.1 2003
 */

import org.easymock.MockControl;
import junit.framework.TestCase;

public class MemoListEditorEmptyTest extends TestCase
{
  private MockControl control;
  private MemoListView mockView;
  private MemoList memoList;

  protected void setUp()
  {
    memoList = new MemoList();

    control = MockControl.createControl(MemoListView.class);
    mockView = (MemoListView)control.getMock();
  }

  public void testInitialisation()
  {
    mockView.setEditor(null);
    control.setDefaultVoidCallable();
    control.replay();
    new MemoListEditor(memoList,mockView);
    control.verify();
  }
}
