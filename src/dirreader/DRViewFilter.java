/*
 * Created on 19.11.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dirreader;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.SWT;
import java.util.StringTokenizer;

/**
 * @author Jörg
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DRViewFilter extends Composite {
	private Button [] filterBtns = null;
	private Text [] filterFields = null;
	private String exts          = null;
	
	private GridLayout createLayout() {
		GridLayout layout = new GridLayout();
		layout.marginHeight = 5;
		layout.marginWidth = 5;
		layout.verticalSpacing = 5;
		return layout;
	}
	
	private void createExtensionFilter(Group filterGroup) {
		String [] [] filterStrs = {
				{"einzubeziehende Dateitypen", ""},  
				{"auszuschließende Dateitypen", ""}
		};
		int len = len = filterStrs.length;
		Composite fileType = new Composite(filterGroup, SWT.None);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		gridLayout.horizontalSpacing = 5;
		fileType.setLayout(gridLayout);
		fileType.setLayoutData(
				new GridData(GridData.FILL_HORIZONTAL));
		Label [] filterLabels = new Label[len]; 
		filterFields = new Text[len];
		for(int i=0; i<len; i++) {
			filterLabels[i] = new Label(fileType, SWT.None);
			filterLabels[i].setText(filterStrs[i][0]);
			filterFields[i] = new Text(fileType, SWT.BORDER);
			filterFields[i].setLayoutData(
					new GridData(GridData.FILL_HORIZONTAL));
			filterFields[i].setText(filterStrs[i][1]);
		}		
	}
	
	private void createAdditionalFilters(Group filterGroup) {
		String [] filterStrs = {
				"Unterordner miteinbeziehen", "Pfad anzeigen", 
				"Dateierweiterung miterfassen"
		};
		int len = filterStrs.length;
		filterBtns = new Button[len];
		for(int i=0; i<len; i++) {
			filterBtns[i] = new Button(filterGroup, SWT.CHECK);
			filterBtns[i].setText(filterStrs[i]);
			filterBtns[i].setSelection(true);
		}
	}
	
	DRViewFilter(Composite parent) {
		super(parent, SWT.NONE);
		setLayout(createLayout());
		setLayoutData(new GridData(GridData.FILL_HORIZONTAL));		
		Group filterGroup = new Group(this, SWT.None);
		filterGroup.setText("Filter");
		filterGroup.setLayout(new GridLayout());
		filterGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));		
		createExtensionFilter(filterGroup);
		createAdditionalFilters(filterGroup);
	}
	
	private String [] getExtensions(String exts) {
		StringTokenizer st = new StringTokenizer(exts, ",");
		int len = st.countTokens();
		String [] fileExts = new String[len];
		for(int i=0; i<len; i++) {
			fileExts[i] = st.nextToken().trim();
		}
		return fileExts;
	}
	
	synchronized String [] getIncludedExtensions() {
		final Display display = filterFields[1].getDisplay(); 
		display.syncExec(new Runnable() {
			public void run() {
				exts = filterFields[0].getText().trim();		
			}
		});
		return getExtensions(exts);
	}
	
	synchronized String [] getExcludedExtensions() {
		final Display display = filterFields[1].getDisplay(); 
		display.syncExec(new Runnable() {
			public void run() {
				exts = filterFields[1].getText().trim();		
			}
		});
		return getExtensions(exts);
	}
	
	boolean [] getSelectedFilters() {
		int len = filterBtns.length;
		boolean [] filters = new boolean[len];
		for(int i=0; i<len; i++) {
			filters[i] = filterBtns[i].getSelection();
		}
		return filters;
	}
}
