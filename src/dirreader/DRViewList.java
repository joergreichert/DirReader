/*
 * Created on 20.11.2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dirreader;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.SWT;

/**
 * @author Jörg
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DRViewList extends Composite {
	private List list = null;
	private Button [] selBtns = null;
	private final String [] bTags = {
		"Aktualisieren", "Hinzufügen", "Löschen",
		"Alle auswählen", "Alle abwählen", "Auswahl umkehren", 
		"Datei schreiben", "Programm beenden"
	};	
	
	private GridLayout createLayout() {
		GridLayout layout = new GridLayout();
		layout.marginHeight = 5;
		layout.marginWidth = 5;
		layout.verticalSpacing = 5;
		return layout;
	}
	
	private void createList(Group listGroup) {
		list =	new List(listGroup, SWT.H_SCROLL | SWT.V_SCROLL 
				| SWT.BORDER | SWT.MULTI);
		list.setLayoutData(new GridData(GridData.FILL_HORIZONTAL 
				| GridData.FILL_VERTICAL));
	}
	
	private void createButtonBar(Group listGroup) {
		int len = bTags.length;
		selBtns = new Button[len];
		Composite btnCom = new Composite(listGroup, SWT.NONE);
		RowLayout layout = new RowLayout();
		layout.spacing = 10;
		layout.fill = true;
		btnCom.setLayout(layout);
		for(int i=0; i<len; i++) {
			selBtns[i] = new Button(btnCom, SWT.PUSH);
			selBtns[i].setText(bTags[i]);
		}
		Menu menu = new Menu(btnCom.getShell(), SWT.BAR);
		MenuItem group = new MenuItem(menu, SWT.DROP_DOWN);
		group.setText("Hallo");
	}
	
	DRViewList(Composite parent) {
		super(parent, SWT.NONE);
		setLayout(createLayout());
		setLayoutData(new GridData(GridData.FILL_BOTH));		
		Group listGroup = new Group(this, SWT.None);
		listGroup.setText("Auswahl");
		listGroup.setLayout(new GridLayout());
		listGroup.setLayoutData(new GridData(GridData.FILL_BOTH));
		createList(listGroup);
		createButtonBar(listGroup);
	}
	
	String [] getSelBtnStrings() {
		int len = selBtns.length;
		String [] selBtnStrs = new String[len];
		for(int i=0; i<len; i++) {
			selBtnStrs[i] = selBtns[i].toString();
		}
		return selBtnStrs;
	}
	
	List getList() {
		return list;
	}
	
	void setListeners(DREvents events) {
		int len = selBtns.length;				
		for(int i=0; i<len; i++) {
			selBtns[i].addSelectionListener(events);
		}		
	}
}
