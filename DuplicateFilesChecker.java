import java.io.File;
import java.util.Iterator;
import java.util.ArrayList;

public class DuplicateFilesChecker {
	ArrayList<File> distinctFileList;
	ArrayList<DuplicateFiles> duplicateFileList;
	File baseDir;
	
	public DuplicateFilesChecker(File f) {
		if(f.isDirectory() == false)
			System.out.println("Need a directory here");
		else {
			distinctFileList = new ArrayList<File>();
			duplicateFileList = new ArrayList<DuplicateFiles>();
			baseDir = f;
			checkDuplicates(baseDir);
		}
	}
	
	public void checkDuplicates(File f) {
		File[] fList = f.listFiles();
		int counter = fList.length;
		while(counter-- > 0) {
			File temp = fList[counter];
			if(temp.isDirectory())
				checkDuplicates(temp);
			else
				addIt(temp);
		}
	}
	
	public void addIt(File f) {
		int loc = distinctFileList.indexOf(f);
		if(loc == -1)
			distinctFileList.add(f);
		else {
			DuplicateFiles df = new DuplicateFiles(distinctFileList.get(loc), f);
		}	
	}
	
	public void displayDistinct() {
		Iterator i = distinctFileList.iterator();
		while(i.hasNext()) {
			String name = ((File)i.next()).getName();
			System.out.println(name);
		}
		System.out.println("Number of unique files : " + distinctFileList.size());
	}
	
	public void displayDuplicates() {
		Iterator i = duplicateFileList.iterator();
		while(i.hasNext()) {
			DuplicateFiles df = (DuplicateFiles)i.next();
			System.out.println("Duplicate Files : \n" + df.f1.getName() + "\n" + df.f2.getName());
		}
	}
}
class DuplicateFiles {
	public File f1, f2;
	public DuplicateFiles(File f1, File f2) {
		this.f1 = f1;
		this.f2 = f2;
	}
}
