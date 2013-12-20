/*
 * Created on 19.11.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dirreader;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * @author Jörg
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DRViewInput extends Composite {
	private Text field   = null;
	private Button inBtn = null;
	
	private GridLayout createLayout() {
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.marginHeight = 5;
		layout.marginWidth = 5;
		layout.verticalSpacing = 5;
		return layout;
	}
	
	DRViewInput(Composite parent) {
		super(parent, SWT.NONE);
		setLayout(createLayout());
		setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		Label label = new Label(this, SWT.LEFT);
		label.setText("Verzeichnis");
		label.setLayoutData(new GridData(GridData.BEGINNING));
		field = new Text(this, SWT.BORDER);
		field.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		inBtn = new Button(this, SWT.PUSH);
		inBtn.setText("...");
		inBtn.setLayoutData(new GridData(GridData.END));
	}
	
	String getSource() {
		return field.getText().trim();
	}
	
	void setSource(String source) {
		field.setText(source);
	}
	
	String getInBtnString() {
		return inBtn.toString();
	}
	
	void setListeners(DREvents events) {
		inBtn.addSelectionListener(events);		
	}
}
