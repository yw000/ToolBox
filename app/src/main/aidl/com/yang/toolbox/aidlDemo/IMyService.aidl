// IMyService.aidl
package com.yang.toolbox.aidlDemo;

// Declare any non-default types here with import statements
import com.yang.toolbox.aidlDemo.Book;
import com.yang.toolbox.aidlDemo.IOnNewBookArrivedListener;
interface IMyService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
  List<Book> getBookList();
  void addBook(in Book book);
  void registerListener(IOnNewBookArrivedListener listener);
  void unregisterListener(IOnNewBookArrivedListener listener);
}
