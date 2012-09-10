package propub;

import javax.swing.AbstractAction;
import javax.swing.JTextArea;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashSet;

import java.io.File;
import java.io.Writer;
import java.io.FileWriter;
import java.io.IOException;

import file.FileDriver;
import db.DLVDriver;
import re.RGrammar;
import re.REAdapter;
import env.EnvInfo;
import parser.Model;

import java.awt.GridBagConstraints;
import java.awt.FlowLayout;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;

public class RPQQueryButtonAction extends AbstractAction {

	public RPQQueryButtonAction() {
		constants = new Constants();
		EnvInfo ei = new EnvInfo();
		ei.setSetupInfo(constants);
	}

	public void setTextArea(JTextArea textArea) {
	    this.textArea = textArea;
	}

	public void setDlvPath(String dlvPath) {
	    this.dlvPath = dlvPath;
	}

	public void setCallback(ProPubApp appInstance) {
		this.appInstance = appInstance;
	}

	public void setDialog(JDialog dialog) {
		this.dialogHook = dialog;
	}


	private static File writeToFile(String model) {
		File f = null;
		Writer w = null;
		try {
			f = File.createTempFile("model", ".txt");
			w = new FileWriter(f);
			w.write(model);
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
		finally {
			if (w != null) {
				try {
					w.close();
				}
				catch(IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		return f;
	}
	

	public void actionPerformed(ActionEvent actionEvent) {
	    Set<String> interestingPredicates = new HashSet(Arrays.asList("rpq2", "rpq4"));
	    Map<String, List<String>> interestingPredicateData = new HashMap<String, List<String>>();
	    RGrammar grammar = new RGrammar();
	    FileDriver fd = new FileDriver();
	    File factsFile = grammar.reGrammar(textArea.getText());
	    DLVDriver dlv = new DLVDriver();
	
	    Model currentDisplayedModel = appInstance.getCurrentDisplayedModel();
	    File modelFile = writeToFile(currentDisplayedModel.getModel().substring(1));


	    dlv.exeDLV(new String[]{dlvPath, modelFile.getAbsolutePath(), factsFile.getAbsolutePath()});
	    String modelString = fd.readFile(constants.PROPUB_EXE + constants.ENV_SEPARATOR + "rpq_out.dlv").toString();

	    // Process the raw output from DLV.

	    // Cut off the braces surrounding.
	    modelString = modelString.trim();
	    modelString = modelString.substring(1, modelString.length() - 1);

	    // Split on tuples
	    String[] tuples = modelString.split(", ");

	    for (String tuple : tuples) {
		Matcher m = PATTERN_PREDICATE_WITH_ARGUMENTS.matcher(tuple);
		if (m.matches()) {
		    String predicateName = m.group(1);
		    String predicateArgs = m.group(2);
		    if (interestingPredicates.contains(predicateName)) {
			if (!interestingPredicateData.containsKey(predicateName)) {
			    interestingPredicateData.put(predicateName, new ArrayList<String>());
			}
			interestingPredicateData.get(predicateName).add(predicateArgs);

		    }
		}
		else if ("all_lineage".equals(tuple)) {
		    System.out.println("I halfway expected this.");
		}
		else {
		    System.out.println("Error on processing tuple: '" + tuple + "'");
		}
	    }

	    Writer writerRpq2 = null;
	    Writer writerRpq4 = null;

	    predicateData = interestingPredicateData;

	    // At this point,all the rpq2 and rpq4 data is available. I will dump it into separate files
	    try {
		rpq2File = File.createTempFile("rpq2_", ".dlv");
		rpq4File = File.createTempFile("rpq4_", ".dlv");

		// Initialize writer
		writerRpq2 = new FileWriter(rpq2File);
		writerRpq4 = new FileWriter(rpq4File);


		for (String argGroup : interestingPredicateData.get("rpq2")) {
		    writerRpq2.write("rpq2(" + argGroup + ").\n");
		}

		for (String argGroup : interestingPredicateData.get("rpq4")) {
		    writerRpq4.write("rpq4(" + argGroup + ").\n");
		}
	    }
	    catch(IOException ex) {
		ex.printStackTrace();
	    }
	    finally {
		if (writerRpq2 != null) {
		    try {
			writerRpq2.close();
		    }
		    catch(IOException ex) {
			ex.printStackTrace();
		    }
		}
		if (writerRpq4 != null) {
		    try {
			writerRpq4.close();
		    }
		    catch(IOException ex) {
			ex.printStackTrace();
		    }
		}
	    }

	    addTableAndPopulate(predicateData);

	    REAdapter adapter = new REAdapter();
	    adapter.OPMToSDM();
	}

	public File getRpq2File() {
	    return rpq2File;
	}

	public File getRpq4File() {
	    return rpq4File;
	}

	public Map<String, List<String>> getPredicateData() {
	    return predicateData;
	}

	///////////////////////////////////////////////////////////////////////////////

	private JTextArea textArea = null;
	private String dlvPath = null;
	private Constants constants;
	private JDialog dialogHook = null;
	private ProPubApp appInstance = null;

	private Map<String, List<String>> predicateData;
	private File rpq2File = null;
	private File rpq4File = null;

	final Pattern PATTERN_PREDICATE_WITH_ARGUMENTS = Pattern.compile("([a-zA-Z_0-9]+)\\((.*)\\)");

	///////////////////////////////////////////////////////////////////////////////

	private void addTableAndPopulate(final Map<String, List<String>> predicateData) {
		final GridBagConstraints tableGbc = new GridBagConstraints();
		tableGbc.gridx = 1;
		tableGbc.gridy = 2;

		final GridBagConstraints buttonGbc = new GridBagConstraints();
		buttonGbc.gridx = 1;
		buttonGbc.gridy = 3;

		Rpq2TableModel model = new Rpq2TableModel(predicateData);
		//        interrogateModel(model);
		JTable table = new JTable(model);
		final JScrollPane tablePane = new JScrollPane(table);

		JButton displayButton = new JButton("Display");
		final JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());

		displayButton.setAction(new DisplayAction(model));
		displayButton.setText("Display");
		buttonPanel.add(displayButton);

		SwingUtilities.invokeLater(new Runnable() {
		    @Override
		    public void run() {
			dialogHook.add(tablePane, tableGbc);
			dialogHook.add(buttonPanel, buttonGbc);
			dialogHook.validate();
			dialogHook.repaint();
			dialogHook.setSize(dialogHook.getPreferredSize());
			dialogHook.validate();
			dialogHook.repaint();
		    }
		});

		//dialogHook.pack();
	}

	private static void interrogateModel(TableModel model) {
		System.out.println("--------------------------");
		System.out.println("There are " + model.getColumnCount() + " columns and " + model.getRowCount() + " rows.");
		for (int i = 1; i <= model.getColumnCount(); i++) {
			System.out.println("Column name=" + model.getColumnName(i-1) + ", columnClass=" + model.getColumnClass(i-1).getName() + ".");
		}
		for (int r = 1; r <= model.getRowCount(); r++) {
			for (int c = 1; c <= model.getColumnCount(); c++) {
				Object value = model.getValueAt(r-1, c-1);
				if (value == null) {
					System.out.println("(" + r + "," + c + "): NULL");
				}
				else {
					System.out.println("(" + r + "," + c +"): " + value + " (" + value.getClass().getName() + ")");
				}
			}
		}
		System.out.println("--------------------------");
	}
}


