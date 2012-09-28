package propub;

import javax.swing.*;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.*;
import java.awt.*;
import java.util.Enumeration;

public class MyJTable extends JTable {

	public MyJTable(TableModel model) {
		super(model);
		TableColumnModel columnModel = this.getColumnModel();
		columnModel.getColumn(0).setPreferredWidth(10);
		_center(columnModel.getColumn(0));
	}

	private void _center(TableColumn column) {

		MyTableCellRenderer renderer = new MyTableCellRenderer(this);
		column.setHeaderRenderer(renderer);
	}

	@Override
	public Dimension getPreferredScrollableViewportSize() {
		return new Dimension(225, 120);
	}

	public static class MyTableCellRenderer implements TableCellRenderer {
		public MyTableCellRenderer(JTable table) {
			this.myTable = table;
		}

		public Component getTableCellRendererComponent(JTable table, Object o, boolean isS, boolean hasF, int row, int col) {
			TableCellRenderer tcr = myTable.getTableHeader().getDefaultRenderer();
			JLabel l = (JLabel) tcr.getTableCellRendererComponent(myTable, null, isS, hasF, row, col);
			l.setText("\u2714");
			l.setHorizontalAlignment(SwingConstants.CENTER);
			return l;
		}

		final private JTable myTable;
	}
}

