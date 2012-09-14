package propub;

import javax.swing.table.AbstractTableModel;

import parser.DLVToDot;

import db.DLVDriver;
import dot.DOTDriver;
import env.EnvInfo;

import file.FileDriver;

import java.io.File;
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

			// NOTE: desiredRpqEdges currently contains the fields of all needed rpq4 facts.
			StringBuilder sb = new StringBuilder();
			for (String desiredRpqEdge : desiredRpqEdges) {
				//sb.append("--- ").append(desiredRpqEdge).append("\n");
				
				int fIndex = desiredRpqEdge.indexOf(",");
				int sIndex = desiredRpqEdge.indexOf(",", fIndex+1);
				int tIndex = desiredRpqEdge.indexOf(",", sIndex+1);
				sb.append("g(") 
				  .append(desiredRpqEdge.substring(tIndex+1))
				  .append(").")
				  .append("\n");
				
				//TODO: to be taken to the correct classes
				//1. first the semi-structure model for the selected
				//2. save the file
				//3. call the semi-structure model to OPM translator
				//4. save the model
				//5. call DLVTODOT and the print dot
				
			}
			
			FileDriver fd = new FileDriver();
			fd.writeFile(new StringBuffer(sb), 
						"/Users/scdey/mySpace/propub/propub/exe/rpq4_sel.dlv");
			DLVDriver dd = new DLVDriver();
			dd.exeDLV(new String[]{
					"/Users/scdey/mySpace/propub/propub/exe/rpq4_sel.sh"});
			
			DLVToDot rdt = new DLVToDot();
			rdt.readRPQFile(
					"/Users/scdey/mySpace/propub/propub/exe/rpq_sel_out.dlv",     //model
					"/Users/scdey/mySpace/propub/propub/exe/rpq_sel_out_tmp.dlv"  //DLV Facts
					);
			rdt.prepareDOTFile(
					"/Users/scdey/mySpace/propub/propub/dot/rpq_sel_dot.txt"
					);
			
			Constants c = new Constants();
			EnvInfo ei = new EnvInfo();
			ei.setSetupInfo(c);
			
			DOTDriver dot = new DOTDriver(c);
			dot.write(
					dot.getGraph(
							new File("/Users/scdey/mySpace/propub/propub/dot/rpq_sel_dot.txt")
							), 
							"/Users/scdey/mySpace/propub/propub/dot/rpq_sel_dot.gif"
					);
			
			return sb.toString();
		}

        private Map<String, List<String>> predicateData = null;
        private Map<String, Boolean> checkboxes = null;

    }

