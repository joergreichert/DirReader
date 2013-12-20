/*
 * Created on 06.09.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dirreader;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;

/**
 * @author Jörg
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DRView implements Runnable {
	private DRViewInput   drvi = null;
	private DRViewFilter  drvf = null;
	private DRViewList    drvl = null;
	private DRViewDialogs drvd = null;
	private Shell shell 		= null;
	private Display display 	= null;
	private Label status 		= null;
	private String message		= null;
	
	void startDirDialog() {
		drvd.startDirDialog(drvi);
	}
	
	String startFileDialog() {
		return drvd.startFileDialog();
	}
	
	int startExistsMessageBox(String out) {
		return drvd.startExistsMessageBox(out);
	}
	
	void startNoWriteMessageBox(String out) {
		drvd.startNoWriteMessageBox(out);
	}
	
	private void createStatusBar(Composite parent) {
		status = new Label(parent, SWT.NONE);
		status.setLayoutData(new GridData(
				GridData.FILL_HORIZONTAL));
	}
	
	DRView() {
		display = Display.getDefault();
		shell = new Shell(display);
		shell.setText("Dateierfasser");
		GridLayout layout = new GridLayout();
		shell.setLayout(layout);
		drvi = new DRViewInput(shell);
		drvf = new DRViewFilter(shell);
		drvl = new DRViewList(shell);
		drvd = new DRViewDialogs(shell);
		createStatusBar(shell);
	}
	
	void start() {
		try {
			shell.open();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}			
	}
	
	String getSource() {
		return drvi.getSource();
	}
	
	String getInBtnString() {
		return drvi.getInBtnString();
	}
	
	String [] getSelBtnStrings() {
		return drvl.getSelBtnStrings();
	}
	
	boolean [] getSelectedFilters() {
		return drvf.getSelectedFilters();
	}
	
	String [] getIncludedExtensions() {
		return drvf.getIncludedExtensions();
	}
	
	String [] getExcludedExtensions() {
		return drvf.getExcludedExtensions();
	}	
	
	void setListeners(DREvents events) {	
		drvi.setListeners(events);
		drvl.setListeners(events);
	}
	
	void setMessage(String message) {
		this.message = message;
		final Display display = status.getDisplay(); 
		display.asyncExec(this);
	}
	
	public void run() {
		status.setText(message);
	}
	
	List getList() {
		return drvl.getList();
	}
}
