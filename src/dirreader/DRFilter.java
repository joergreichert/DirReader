package dirreader;
import java.util.StringTokenizer;
import java.util.Vector;

public class DRFilter {
	DRReader reader = null;
	
	private String getExtension(String entry) {
		StringTokenizer st = new StringTokenizer(entry, ".");		
		String ext = null;
		int count = st.countTokens();
		if(count > 0) {
			for(int i=0, j=count-1; i<j; i++) {
				st.nextToken();
			}
			int len = entry.length();
			int off = len - st.nextToken().length();
			ext = entry.substring(off, len);			
		} 
		return ext;		
	}
	
	private boolean testExtension(String entry, String testExt) {
		String ext = getExtension(entry);
		if(ext.length() != 0) {
			if(ext.equals(testExt)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean testExtension(String entry, String [] exts) {
		boolean hasExt = false; 
		int size = exts.length;
		for(int i=0; i<size && hasExt == false; i++) {
			hasExt = testExtension(entry, exts[i]);
		}
		return hasExt;
	}
	
	Vector <String> filterExtensions(Vector <String> entries, 
            String [] incs, String [] excs) {
		Vector <String> filteredEntries = new Vector<String>(); 
		int size = entries.size();
		boolean include;
		for(int i=0; i<size; i++) {
			include = true;
			if(incs.length == 0) {
				if(excs.length != 0) {
					include = !testExtension(entries.get(i).toString(), excs);
				}
			} else {
				include = testExtension(entries.get(i).toString(), incs);
				if(excs.length != 0) {
					include = include && !testExtension(entries.get(i).toString(), excs);
				}
			}
			if(include) {
				filteredEntries.add(entries.get(i));
			}
		}
		return filteredEntries; 
	}
	
	private String cutExtension(String entry) {
		String ext = getExtension(entry);
		int entryLen = entry.length();
		int extLen = ext.length();
		if((extLen != 0) && (entryLen > extLen)) {
			int off = entryLen - extLen - 1;
			entry = entry.substring(0, off);			
		} 
		return entry;
	}
	
	Vector <String> cutExtension(Vector <String> entries) {
		for(int i=0, j=entries.size(); i<j; i++) {
			entries.set(i, 
					cutExtension(entries.get(i).toString()));
		}
		return entries;
	}
	
	private String cutPath(String entry) {
		String sep = System.getProperty("file.separator");
		StringTokenizer st = new StringTokenizer(entry, sep);
		int count = st.countTokens();
		if(count > 0) {
			for(int i=0, j=count-1; i<j; i++) {
				st.nextToken();
			}
			entry = st.nextToken();
		} 
		return entry;
	}
	
	Vector <String> cutPath(Vector <String> entries) {
		for(int i=0, j=entries.size(); i<j; i++) {
			entries.set(i, 
					cutPath(entries.get(i).toString()));
		}
		return entries;
	}	
	
	private Vector <String> useFilters(Vector <String> content, 
            boolean [] filters) {
		if(!filters[1]) {
			content = cutPath(content);
		}
		if(!filters[2]) {
			content = cutExtension(content);
		}
		return content;
	}
	
	Thread getReaderThread(String in, boolean [] filters, 
			String [] fileExts, String [] fileExts2) {
		reader = new DRReader(in, filters[0], fileExts, fileExts2);
		return reader;
	}
	
	Vector <String> getFileStrings(boolean [] filters) {
		Vector <String> content = reader.getFiles();
		content = useFilters(content, filters);
		return content;
	}
	
	Vector <String> getFileStrings(Vector <String> content, 
            boolean [] filters, String [] incs, 
            String [] excs) {
		content = filterExtensions(content, incs, excs);
		content = useFilters(content, filters);
		return content;
	}	
}