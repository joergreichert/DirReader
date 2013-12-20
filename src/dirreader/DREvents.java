package dirreader;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.SelectionEvent;
import java.util.Vector;
import java.io.IOException;

public class DREvents implements SelectionListener, Runnable {
	private String inBtn = null;
	private String [] selBtns = null;
	private int selsCount = 0;
	private DRView view         	= null;
	private DRFilter filter     	= null;
	private DRWriter writer     	= null;
	private DRList list         	= null;
	private boolean [] filters		= null;
	private String in 				= null;
	private Thread readerThread		= null;
	
	DREvents(DRView drv) {
		view = drv;
		inBtn = view.getInBtnString();
		selBtns = view.getSelBtnStrings();
		selsCount = selBtns.length;		
		filter = new DRFilter();
		list = new DRList(view.getList());
		writer = new DRWriter();
		view.setListeners(this);		
	}
	
	private void readIn() {
		in = view.getSource();
		filters = view.getSelectedFilters();
		new Thread(this).start();
	}
	
	public void run() {
		Thread thisThread = Thread.currentThread();
       	try {
       		Vector <String> content = new Vector<String>();
       		String [] fileExts  = view.getIncludedExtensions();
       		String [] fileExts2 = view.getExcludedExtensions();
       		readerThread = filter.getReaderThread(in, filters, fileExts, fileExts2);
       		readerThread.start();
       		while(readerThread.isAlive()) {  }
       		Vector <String> vector = filter.getFileStrings(filters);
       		list.setListEntries(vector);
       		view.setMessage(vector.size() + " Dateien eingelesen.");
       	} catch(Exception e) {
       		view.setMessage(e.getMessage());
       		e.printStackTrace();
       	}
	}
	
	private void writeFile(String out) throws IOException {
		boolean canWrite = 
			writer.canWriteFile(out);
		if(canWrite) {
			String [] content = list.getListEntries();
			int len = content.length;
			StringBuffer sb = new StringBuffer();
			for(int i=0; i<len; i++) {
				sb.append(content[i].concat("\n"));
			}
			writer.writeFile(sb.toString(), out);
		} else {
			view.startNoWriteMessageBox(out);
		}
	}
	
	private void writeOut() throws Exception {
		String out = view.startFileDialog();
		try {
			if((out == null) || (out.length() == 0)) {
				throw new DRException("keine Datei angegeben");
			} else {
				try {
					boolean exists = writer.existsFile(out);
					if(exists) {
						int answer = view.startExistsMessageBox(out);
						if(answer == 0) {
							writeOut();
						} else if(answer == 1) {
							writeFile(out);
						}
					} else {
						writeFile(out);
					}
				} catch(IOException ioe) {
					throw new DRException("Fehler mit Ausgabe: " + ioe.getMessage());
				}
			}
		} catch(DRException dre) {
			System.out.println(dre.getMessage());
		}
	}
	
	public void widgetSelected(SelectionEvent se) {
		String source = se.getSource().toString();
		try {
			if(source.equals(selBtns[0])) {
				Vector <String> content = list.getAllListEntries();
				filters = view.getSelectedFilters();
				String [] incs = view.getIncludedExtensions();
				String [] excs = view.getExcludedExtensions();
				content = filter.getFileStrings(content, filters, incs, excs);
				list.deleteListEntries();
				list.setListEntries(content);
			} else if(source.equals(selBtns[1])) {
				readIn();
			} else if(source.equals(selBtns[2])) {
				list.deleteSelectedListEntries();
			} else if(source.equals(selBtns[3])) {
				list.selectAllEntries();
			} else if(source.equals(selBtns[4])) {
				list.deselectAllEntries();			
			} else if(source.equals(selBtns[5])) {
				list.reverseSelection();
			} else if(source.equals(selBtns[6])) {
				writeOut();
			} else if(source.equals(selBtns[7])) {
				System.exit(0);
			} else if(source.equals(inBtn)) {
				view.startDirDialog();
			}
		} catch(Exception e) {
			view.setMessage(e.getMessage());
		}
	}
	
	public void widgetDefaultSelected(SelectionEvent se) {	}
}
