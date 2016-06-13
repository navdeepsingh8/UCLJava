/**
 * Title: class MemoListWriter
 * Description: A writer is any class that writes, prints or stores a
 * MemoList in any form. MemoList is unaware of how the writer works
 * or what form a list is written in.
 * Copyright: Copyright (c) 2003
 * Organisation: Dept. of Computer Science, University College London
 * @author Graham Roberts
 * @version 0.1 2003
 */

import java.io.IOException;

public interface MemoListWriter
{
  public void write(MemoList memoList) throws IOException;
}
