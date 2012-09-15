package propub;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: sean
 * Date: 9/10/12
 * Time: 11:55 AM
 */

public class ArtifactTableModel extends DefaultTableModel {
	public int getRowCount() {
		if (orderedData == null) {
			return 0;
		}
		else {
			return orderedData.size();
		}
	}

	public int getColumnCount() {
		if (fieldNames == null) {
			return 0;
		}
		else {
			return fieldNames.size();
		}
	}

	public Object getValueAt(int r, int c) {
		return orderedData.get(r).get(c);
	}

	public void setMetadata(Map<String, List<Metadatum>> metadataMap) {
		orderedData = new ArrayList<List<String>>();

		for (String type : metadataMap.keySet()) {
			List<Metadatum> metadata = metadataMap.get(type);
			for (Metadatum metadatum : metadata) {
				orderedData.add(Arrays.asList(type, metadatum.get("id"), metadatum.get("url"), metadatum.get("description"), metadatum.get("category")));
			}
		}

		fieldNames = Arrays.asList("type", "id", "url", "description", "category");
		
		this.fireTableStructureChanged();
	}

	private List<List<String>> orderedData = null;
	private List<String> fieldNames = null;
	
	
}
