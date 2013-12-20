package dirreader;
import java.util.Vector;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Display;

public class DRList implements Runnable {
	private List list = null;
	private Vector <String> content = null;
	
	DRList(List l) {
		list = l;
	}
	
	public synchronized void setListEntries(
            Vector <String> data) {
		content = data;
		final Display display = list.getDisplay(); 
		display.asyncExec(this);
	}
	
	public void run() {
		for(int i=0, len = content.size(); i<len; i++) {
			list.add((String) content.get(i));
		}		
	}
	
	void deleteListEntries() {
		list.removeAll();
	}
	
	void deleteSelectedListEntries() {
		int [] sels = list.getSelectionIndices();
		list.remove(sels);
	}
	
	String [] getSelectedListEntries() {
		return list.getSelection();
	}
	
	Vector <String> getAllListEntries() {
		String [] items = list.getItems();
		int len = items.length;
		Vector <String> entries = new Vector<String>(len);
		for(int i=0; i<len; i++) {
			entries.add(items[i]);
		}
		return entries;
	}
	
	String [] getListEntries() {
		return list.getItems();
	}
	
	void selectAllEntries() {
		int count = list.getItemCount();
		for(int i=0; i<count; i++) {
			list.select(i);
		}
	}
	
	void deselectAllEntries() {
		int [] sels = list.getSelectionIndices();
		int count = sels.length;
		for(int i=0; i<count; i++) {
			list.deselect(sels[i]);
		}		
	}
	
	void reverseSelection() {
		int [] sels = list.getSelectionIndices();
		int count = sels.length;
		selectAllEntries();
		for(int i=0; i<count; i++) {
			list.deselect(sels[i]);
		}		
	}
}
