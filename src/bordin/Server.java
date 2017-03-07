package bordin;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class Server {

	protected Shell shlTabellone;
	private Table table;
	private TableColumn tblclmnNewColumn;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Server window = new Server();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlTabellone.open();
		shlTabellone.layout();
		while (!shlTabellone.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlTabellone = new Shell();
		shlTabellone.setSize(450, 400);
		shlTabellone.setText("Tabellone");

		Label lblServerone = new Label(shlTabellone, SWT.NONE);
		lblServerone.setAlignment(SWT.CENTER);
		lblServerone.setBounds(10, 10, 414, 15);
		lblServerone.setText("Serverone");

		table = new Table(shlTabellone, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(20, 49, 404, 199);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableItem item;
		for (int i = 1; i <= 10; i++) {
			tblclmnNewColumn = new TableColumn(table, SWT.NONE);
			tblclmnNewColumn.setWidth(40);
			tblclmnNewColumn.setText(i + "");
			tblclmnNewColumn.setResizable(false);
		}
		
		int l = 0;
		for (int j = 0; j < 9; j++) {
			item = new TableItem(table, SWT.NONE);
			for (int k = 0; k < 10; k++) {
				int s = (k + 1) + l;
				item.setText(k, s + "");
			}
			l += 10;
		}

		ThreadServer s = new ThreadServer(this);
		s.start();
		
		Button btnEstraiNumero = new Button(shlTabellone, SWT.NONE);
		btnEstraiNumero.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int random = (int) (Math.random()*90+1);
				/*for(){
					
				}*/
			}
		});
		btnEstraiNumero.setBounds(328, 10, 96, 25);
		btnEstraiNumero.setText("Estrai numero");

	}
}
