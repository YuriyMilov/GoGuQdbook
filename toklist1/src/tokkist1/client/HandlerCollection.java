package tokkist1.client;

import java.util.ArrayList;
import java.util.Iterator;

public class HandlerCollection {
  private static final ArrayList EMPTY_LIST = new ArrayList();
  
  private ArrayList handlers;

  public void add(Object handler) {
    handlers = (handlers == null) ? new ArrayList() : new ArrayList(handlers);
    handlers.add(handler);
  }

  public void remove(Object handler) {
    if (handlers == null || handler == null) {
      return;
    }

    Iterator it = handlers.iterator();
    handlers = new ArrayList();
    while (it.hasNext()) {
      Object obj = it.next();
      if (obj != handler) {
        handlers.add(obj);
      }
    }
  }

  public Iterator iterator() {
    return (handlers == null) ? EMPTY_LIST.iterator()
        : handlers.iterator();
  }

  public int size() {
    return (handlers == null) ? 0 : handlers.size();
  }
  
  public void clear() {
    handlers = null;
  }
}
