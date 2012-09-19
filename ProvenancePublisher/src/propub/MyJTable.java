package propub;

import javax.swing.JTable;
import javax.swing.table.TableModel;
import java.awt.Dimension;

public class MyJTable extends JTable {

	public MyJTable(TableModel model) {
		super(model);
	}

	@Override
	public Dimension getPreferredScrollableViewportSize() {
		return new Dimension(225, 120);
	}

}

