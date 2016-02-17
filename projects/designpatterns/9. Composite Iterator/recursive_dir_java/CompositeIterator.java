package pdu.compositeiterator.dir;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Stack;
  
public class CompositeIterator implements Iterator {
	Stack stack = new Stack();
   
	public CompositeIterator(Iterator iterator) {
		stack.push(iterator);
	}
   
	public Object next() {
		if (hasNext()) {
			Iterator iterator = (Iterator) stack.peek();
			File file = (File) iterator.next();
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				if(files != null) stack.push(Arrays.asList(files).iterator());
			} 
			return file;
		} else {
			return null;
		}
	}
  
	public boolean hasNext() {
		if (stack.empty()) {
			return false;
		} else {
			Iterator iterator = (Iterator) stack.peek();
			if (!iterator.hasNext()) {
				stack.pop();
				return hasNext();
			} else {
				return true;
			}
		}
	}
}


