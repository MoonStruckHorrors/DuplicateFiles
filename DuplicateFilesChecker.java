import java.io.File;
import java.util.Iterator;
import java.util.ArrayList;

public class DuplicateFilesChecker {
	ArrayList<FileC> distinctFileList;
	ArrayList<DuplicateFiles> duplicateFileList;
	FileC baseDir;
	
	public DuplicateFilesChecker(String s) {
		FileC f = new FileC(s);
		if(f.isDirectory() == false)
			System.out.println("Need a directory here");
		else {
			distinctFileList = new ArrayList<FileC>();
			duplicateFileList = new ArrayList<DuplicateFiles>();
			baseDir = f;
			checkDuplicates(baseDir);
		}
	}
	
	public void checkDuplicates(FileC f) {
		FileC[] fList = f.listFiles();
		int counter = fList.length;
		while(counter-- > 0) {
			FileC temp = fList[counter];
			if(temp.isDirectory()) //Ah, a subdirectory!
				checkDuplicates(temp);
			else
				addIt(temp);
		}
	}
	
	public void addIt(FileC f) {
		int loc = distinctFileList.indexOf(f);
		if(loc == -1) { // If not found in distinctFileList
			distinctFileList.add(f);
		} else {
			int in = indexInDuplicates(f);
			if(in == -1) {
				DuplicateFiles df = new DuplicateFiles(distinctFileList.get(loc), f);
				duplicateFileList.add(df);
			} else {
				duplicateFileList.get(in).addToList(f);
			}
		}	
	}
	
	public void displayDistinct() {
		Iterator i = distinctFileList.iterator();
		while(i.hasNext()) {
			String name = ((FileC)i.next()).getName();
			System.out.println(name);
		}
		System.out.println("Number of unique files : " + distinctFileList.size());
	}
	
	public void displayDuplicates() {
		Iterator i = duplicateFileList.iterator();
		while(i.hasNext()) {
			System.out.println("\nDuplicate Files : \n******************");
			DuplicateFiles df = (DuplicateFiles)i.next();
			df.displayList();
			System.out.println("******************");
		}
		System.out.println("Number of duplicates : " + duplicateFileList.size());
	}
	
	//Worst apporach
	public int indexInDuplicates(FileC f) {
		int result = -1;
		String fName = f.getName();
		int count = duplicateFileList.size();
		while(count-- > 0) {
			if(fName.equals(duplicateFileList.get(count).dfList.get(0).getName())) {
				result = count;
				break;
			}
		}
		return result;
	}
}

class DuplicateFiles {
	ArrayList<FileC> dfList;
	public DuplicateFiles(FileC f1, FileC f2) {
		dfList = new ArrayList<FileC>();
		dfList.add(f1);
		dfList.add(f2);
	}
	public void addToList(FileC f) {
		dfList.add(f);
	}
	public void displayList() {
		Iterator i = dfList.iterator();
		while(i.hasNext()) {
			FileC df = (FileC)i.next();
			System.out.println(df.getPath());
		}
	}
	/*public boolean equals(Object obj) { // << Raises conflicts with FileC's equals, using custom method instead
		boolean result = false;
		String f2Name = ((FileC)obj).getName();
		if(dfList.get(0).getName().equals(f2Name))
			result = true;
		
		return result;
    }
	//Custom method which was supposed to work.
   public int exists(FileC f) { // << Now this is just embarrasing. Taking the worst approach now.
		int result = -1;
		String fName = f.getName();
		int count = duplicateFileList.size();
		while(count-- > 0) {
			if(fName.equals(duplicateFilesList.get(count).dfList.get(0))) {
				result = count;
			}
		}
		return result;
	}*/
}

class FileC extends File {
	public FileC(String path) {
		super(path);
	}
	public boolean equals(Object obj) {
		boolean result = false;
		String f2Name = ((FileC)obj).getName();
		if(getName().equals(f2Name))
			result = true;
		return result;
	}
	public FileC[] listFiles() {
		File[] fList = super.listFiles();
		FileC[] fListC = new FileC[fList.length];
		int count = fList.length;
		while(count-- > 0) {
			fListC[count] = new FileC(fList[count].getPath());
		}
		return fListC;
	}
}