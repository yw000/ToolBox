// IOnNewBookArriverListener.aidl
package com.yang.toolbox.aidlDemo;

// Declare any non-default types here with import statements
import com.yang.toolbox.aidlDemo.Book;
interface IOnNewBookArrivedListener {

 void onNewBookArrived(in Book book);

}
