/*
 * Created on 20.11.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dirreader;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.MessageBox;

/**
 * @author Jörg
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DRViewDialogs {
	private Shell shell = null;
	
	DRViewDialogs(Shell s) {
		shell = s;
	}
	
	void startDirDialog(DRViewInput drvi) {
		DirectoryDialog dialog = new DirectoryDialog(shell, 
				SWT.OPEN);
		String source = dialog.open();
		if (source != null) {
			drvi.setSource(source);
		}
	}
	
	String startFileDialog() {
		String [] fileExts = new String [] {"*.txt"};
		FileDialog dialog = new FileDialog(shell, SWT.SAVE);
		dialog.setFilterExtensions(fileExts);
		String result = dialog.open();
		return result;
	}
	
	int startExistsMessageBox(String out) {
		MessageBox box = new MessageBox(shell, SWT.YES | 
				SWT.NO | SWT.CANCEL | SWT.ICON_WARNING);
		box.setText("Speichern unter");
		box.setMessage(out + " besteht bereits.\n" + 
				" Möchten Sie sie ersetzen?");
		int result = box.open();
		if(result == SWT.NO) {
			return 0; 
		} else if(result == SWT.YES) {
			return 1;
		} else {
			return 2;
		}
	}
	
	void startNoWriteMessageBox(String out) {
		MessageBox box = new MessageBox(shell, SWT.OK | 
				SWT.NO | SWT.CANCEL | SWT.ICON_ERROR);
		box.setText(shell.getText());
		box.setMessage("Zugriff auf " + out + " wurde verwehrt");
		box.open();
	}
}
