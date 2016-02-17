package pdu.compositeiterator.dir;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class RecursiveDirTestDrive {

	public static void main(String[] args) {
		File root = new File(System.getProperty("user.dir"));
		ArrayList<File> rootFiles = new ArrayList<File>(Arrays.asList(root.listFiles()));
		CompositeIterator iterator = new CompositeIterator(rootFiles.iterator());

		System.out.println(System.getProperty("user.dir"));
		while(iterator.hasNext()){
			File f = (File)iterator.next();
			System.out.println(f.getPath());
		}
	}

}
