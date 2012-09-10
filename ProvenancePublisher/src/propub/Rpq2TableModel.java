package propub;

import javax.swing.table.AbstractTableModel;

import java.util.*;

public class Rpq2TableModel extends AbstractTableModel {
	public Rpq2TableModel(Map<String, List<String>> predicateData) {
		this.predicateData = predicateData;
		this.checkboxes = new HashMap<String, Boolean>();
		for (String argSet : predicateData.get("rpq2")) {
			checkboxes.put(argSet, false);
		}
	}

	@Override
	public int getRowCount() {
		return predicateData.get("rpq2").size();
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public void setValueAt(Object o, int i, int i1) {
		if (i1 != 0) {
			System.out.println("This shouldn't happen!");
			System.exit(1);
		}

		String args = predicateData.get("rpq2").get(i);
		if (o == Boolean.TRUE) {
			checkboxes.put(args, true);
		}
		else {
			checkboxes.put(args, false);
		}
	}

// NOTE: Reformat the rest of this if and when it matters.

        @Override
        public Object getValueAt(int i, int i1) {
            String args = predicateData.get("rpq2").get(i);
            if (i1 == 1 || i1 == 2) {
                String[] splitArgs = args.split(",");
                String firstArg = splitArgs[0];
                String lastArg = splitArgs[splitArgs.length - 1];
                if (i1 == 1) return firstArg;
                if (i1 == 2) return lastArg;
            }
            if (i1 == 0) {
                return checkboxes.get(args);
            }
            return null;  //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public String getColumnName(int i) {
            if (i == 0) return "Checkbox";
            if (i == 1) return "Node 1";
            if (i == 2) return "Node 2";
            return "Huh?";
        }

        @Override
        public Class<?> getColumnClass(int i) {
            if (i == 0) return Boolean.class;
            if (i == 1) return String.class;
            if (i == 2) return String.class;
            System.out.println("Unknown i: " + i);
            return String.class;
        }

        @Override
        public boolean isCellEditable(int i, int i1) {
            return i1 == 0; // Only the first column, the checkboxes, is editable.
        }

        public String getSelectedPairs() {
            StringBuilder sb = new StringBuilder();
            for (String argSet : predicateData.get("rpq2")) {
                if (checkboxes.get(argSet)) {
                    sb.append("rpq2(").append(argSet).append(").\n");
                }
            }
            return sb.toString();
        }
		public String getSelectedPairsAsRpq4() {

			// Calculate involved rpq2 elements
			Set<String> involvedRpq2 = new HashSet<String>();
			for (String val : predicateData.get("rpq2")) {
				if (checkboxes.get(val)) {
					involvedRpq2.add(val);
				}
			}

			Set<String> desiredRpqEdges = new HashSet<String>();
			for (String val : predicateData.get("rpq4")) {
				for (String prefixPossibility : involvedRpq2) {
					if (val.startsWith(prefixPossibility)) {
						desiredRpqEdges.add(val);
						break;
					}
				}
			}

			StringBuilder sb = new StringBuilder();
			for (String desiredRpqEdge : desiredRpqEdges) {
				sb.append("--- ").append(desiredRpqEdge).append("\n");
			}
			return sb.toString();
		}

        private Map<String, List<String>> predicateData = null;
        private Map<String, Boolean> checkboxes = null;

    }

