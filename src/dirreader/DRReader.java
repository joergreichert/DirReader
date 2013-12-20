package dirreader;
import java.io.File;
import java.io.FileFilter;
import java.util.Vector;

/**
 * @author Jörg
 */
public class DRReader extends Thread implements FileFilter {
	private Vector <String> vector = null;
	private Vector <File> dirVector = null;
	private boolean read = true;
	private String [] extensions = null, extensions2 = null;
	private int extsLen = 0, extsLen2 = 0;
	
	DRReader(String in, boolean readSubDirs, String [] exts, String [] exts2) {
		vector = new Vector <String> ();
		dirVector = new Vector <File> ();
		read = readSubDirs;
		extensions  = exts;
		extensions2 = exts2;
		extsLen = exts.length;
		extsLen2 = exts2.length;
		dirVector.add(new File(in));
	}	
	
	private void getFiles(File dir) {
		try {
			if (!dir.isDirectory()) {
				throw new IllegalArgumentException(dir.getName() + " ist kein Verzeichnis.");
			}
			File [] entries = dir.listFiles(this);
			for (int i=0, len = entries.length; i<len; ++i) {
				if (!entries[i].isDirectory()) {
					vector.add(entries[i].toString());
				} else {
					if(read) {
						dirVector.add(entries[i]);
					}
				}
			}
		} catch (NullPointerException e) {
		} catch (IllegalArgumentException e) {
			System.out.println("Sie haben kein gültiges " + 
					"Verzeichnis angegeben.");	
		} 
	}
	
	public void run() {
		while(dirVector.size() > 0) {
			getFiles((File) dirVector.get(0));
			dirVector.remove(0);
		}
	}
	
	public boolean accept(File file) {
		if(file.isDirectory()) {
			return true;
		}
		if(extsLen == 0) {
			if(extsLen2 == 0) {
				return true;
			} else {
				return testExclude(file);
			}
		} else {
			boolean include = testInclude(file);
			if(extsLen2 == 0) {
				return include;
			} else {
				return include && testExclude(file);
			}
		}
	}
	
	private boolean testInclude(File file) {
		boolean accept = false;
		for(int i=0; i<extsLen && accept == false; i++) {
			accept = file.getName().endsWith(extensions[i]);
		}
		return accept;		
	}	
	
	private boolean testExclude(File file) {
		boolean accept = false;
		for(int i=0; i<extsLen2 && accept == false; i++) {
			accept = file.getName().endsWith(extensions2[i]);
		}
		return !accept;		
	}
	
	Vector <String> getFiles() {
		return vector;
	}
}