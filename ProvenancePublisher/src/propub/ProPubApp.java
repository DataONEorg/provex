/*
 * ProPubApp.java
 *
 * Created on __DATE__, __TIME__
 */

package propub;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.Writer;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Hashtable;

import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.net.URL;

import javax.imageio.ImageIO;

import com.sun.org.apache.xerces.internal.util.DOMUtil;
import parser.Model;

import db.DLVDriver;
import dot.DOTDriver;

import env.EnvInfo;
import file.FileDriver;

import javax.swing.tree.DefaultMutableTreeNode;

import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;

import re.RGrammar;
import types.IconType;
import types.URContext;
import types.PayloadTreeNode;

import java.util.List;
import java.util.Random;

/**
 *
 * @author  __USER__
 */
public class ProPubApp extends javax.swing.JFrame {

	/** Creates new form ProPubApp */
	private ProPubApp() {
		super("ProPub: The Provenance Publisher");
		initEnvironment();
		initComponents();
	}

	public static ProPubApp getInstance() {
		if (instance == null) {
			instance = new ProPubApp();
		}
		return instance;
	}

	private void initEnvironment() {
		EnvInfo ei = new EnvInfo();
		ei.setSetupInfo(constants);
	}


	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		jPanel_Main = new javax.swing.JPanel();
		jPanel_Menu = new javax.swing.JPanel();
		jButton_New = new javax.swing.JButton();
		jButton_Open = new javax.swing.JButton();
		jButton_Save = new javax.swing.JButton();
		jButton_SL = new javax.swing.JButton();
		jButton_NI = new javax.swing.JButton();
		jButton_Set = new javax.swing.JButton();
		jButton_Explore = new javax.swing.JButton();
		jButton_First = new javax.swing.JButton();
		jButton_Prev = new javax.swing.JButton();
		jButton_Next = new javax.swing.JButton();
		jButton_Last = new javax.swing.JButton();
		jButton_IG = new javax.swing.JButton();
		jPanel_UR = new javax.swing.JPanel();
		jScrollPane_UR = new javax.swing.JScrollPane();
		jTextArea_UR = new javax.swing.JTextArea();
		jPanel_Graph = new javax.swing.JPanel();
		jLabel_Graph = new javax.swing.JLabel();
		jTabbedPane_First = new javax.swing.JTabbedPane();
		jPanel_MQ = new javax.swing.JPanel();
		jScrollPane_QT = new javax.swing.JScrollPane();
		jTree_QT = GlobalContext.getInstance().buildTree();
		addListener(jTree_QT);

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jPanel_Menu.setBorder(javax.swing.BorderFactory.createCompoundBorder(
				null, new javax.swing.border.SoftBevelBorder(
						javax.swing.border.BevelBorder.RAISED)));


		initButtonGraphic(jButton_New, "New", "general", "New", "newtext");
		//jButton_New.setText("New");
		jButton_New.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton_NewActionPerformed(evt);
			}
		});

		//jButton_Open.setText("Open");
		initButtonGraphic(jButton_Open, "Open", "general", "Open", "opentext");
		jButton_Open.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton_OpenActionPerformed(evt);
			}
		});

		//jButton_Save.setText("Save");
		initButtonGraphic(jButton_Save, "Save", "general", "Save", "savetext");
		jButton_Save.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton_SaveActionPerformed(evt);
			}
		});

		//jButton_SL.setText("SL");
		// SEAN: This is a stretch.
		initButtonGraphic(jButton_SL, "SL", "general", "Remove", "Perform SL");
		jButton_SL.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton_SLActionPerformed(evt);
			}
		});

		//jButton_NI.setText("NI");
		initButtonGraphic(jButton_NI, "NI", "general", "Add", "Perform NI");
		jButton_NI.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton_NIActionPerformed(evt);
			}
		});

		//jButton_Set.setText("Set");
		initButtonGraphic(jButton_Set, "Set", "general", "Bookmarks", "Set");
		jButton_Set.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton_SetActionPerformed(evt);
			}
		});

		//jButton_Explore.setText("Explore");
		initButtonGraphic(jButton_Explore, "Explore", "general", "Zoom", "Explore");
		jButton_Explore.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton_ExploreActionPerformed(evt);
			}
		});

		//jButton_First.setText("<<");
		initButtonGraphic(jButton_First, "First", "media", "Rewind", "Go to first step");
		jButton_First.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton_FirstActionPerformed(evt);
			}
		});

		//jButton_Prev.setText("<");
		initButtonGraphic(jButton_Prev, "Previous", "navigation", "Back", "Back one step");
		jButton_Prev.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton_PrevActionPerformed(evt);
			}
		});

		//jButton_Next.setText(">");
		initButtonGraphic(jButton_Next, "Next", "navigation", "Forward", "Forward one step");
		jButton_Next.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton_NextActionPerformed(evt);
			}
		});

		//jButton_Last.setText(">>");
		initButtonGraphic(jButton_Last, "Last", "media", "FastForward", "Go to last step");
		jButton_Last.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton_LastActionPerformed(evt);
			}
		});

		//jButton_IG.setText("IG");
		initButtonGraphic(jButton_IG, "IG", "general", "ContextualHelp", "IG - Unknown functionality");
		jButton_IG.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton_IGActionPerformed(evt);
			}
		});

		
		org.jdesktop.layout.GroupLayout jPanel_MenuLayout = new org.jdesktop.layout.GroupLayout(
				jPanel_Menu);
		jPanel_Menu.setLayout(jPanel_MenuLayout);
		jPanel_MenuLayout.setHorizontalGroup(jPanel_MenuLayout
				.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(jPanel_MenuLayout
						.createSequentialGroup()
						.addContainerGap()
						.add(jButton_Open)
						.addPreferredGap(
								org.jdesktop.layout.LayoutStyle.RELATED)
						.add(jButton_Save)
						.add(178, 178, 178)
						.add(jButton_SL)
						.addPreferredGap(
								org.jdesktop.layout.LayoutStyle.RELATED)
						.add(jButton_NI)
						.addPreferredGap(
								org.jdesktop.layout.LayoutStyle.RELATED)
						.add(jButton_Set)
						.addPreferredGap(
								org.jdesktop.layout.LayoutStyle.RELATED, 178,
								Short.MAX_VALUE)
						.add(jButton_First)
						.addPreferredGap(
								org.jdesktop.layout.LayoutStyle.RELATED)
						.add(jButton_Prev)
						.addPreferredGap(
								org.jdesktop.layout.LayoutStyle.RELATED)
						.add(jButton_Next)
						.addPreferredGap(
								org.jdesktop.layout.LayoutStyle.RELATED)
						.add(jButton_Last)
						.addPreferredGap(
								org.jdesktop.layout.LayoutStyle.RELATED)
						.addContainerGap()));
		jPanel_MenuLayout
				.setVerticalGroup(jPanel_MenuLayout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(org.jdesktop.layout.GroupLayout.TRAILING,
								jPanel_MenuLayout
										.createSequentialGroup()
										.addContainerGap(
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.add(jPanel_MenuLayout
												.createParallelGroup(
														org.jdesktop.layout.GroupLayout.BASELINE)
												.add(jButton_Open)
												.add(jButton_Save)
												.add(jButton_Last)
												.add(jButton_Prev)
												.add(jButton_Next)
												.add(jButton_First)
												.add(jButton_SL)
												.add(jButton_NI)
												.add(jButton_Set))
										.addContainerGap()));

		jPanel_UR.setBorder(javax.swing.BorderFactory.createTitledBorder(
				javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1),
				"User Requests", javax.swing.border.TitledBorder.CENTER,
				javax.swing.border.TitledBorder.DEFAULT_POSITION));

		jTextArea_UR.setColumns(20);
		jTextArea_UR.setRows(5);
		jScrollPane_UR.setViewportView(jTextArea_UR);

		org.jdesktop.layout.GroupLayout jPanel_URLayout = new org.jdesktop.layout.GroupLayout(
				jPanel_UR);
		jPanel_UR.setLayout(jPanel_URLayout);
		jPanel_URLayout.setHorizontalGroup(jPanel_URLayout.createParallelGroup(
				org.jdesktop.layout.GroupLayout.LEADING).add(jScrollPane_UR,
				org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 262,
				Short.MAX_VALUE));
		jPanel_URLayout.setVerticalGroup(jPanel_URLayout.createParallelGroup(
				org.jdesktop.layout.GroupLayout.LEADING).add(jScrollPane_UR,
				org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 177,
				Short.MAX_VALUE));

		jPanel_Graph.setBorder(new javax.swing.border.SoftBevelBorder(
				javax.swing.border.BevelBorder.RAISED));

		jLabel_Graph.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel_Graph.setFocusable(false);
		jLabel_Graph
				.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

		org.jdesktop.layout.GroupLayout jPanel_GraphLayout = new org.jdesktop.layout.GroupLayout(
				jPanel_Graph);
		jPanel_Graph.setLayout(jPanel_GraphLayout);
		jPanel_GraphLayout.setHorizontalGroup(jPanel_GraphLayout
				.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(jLabel_Graph,
						org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 772,
						Short.MAX_VALUE));
		jPanel_GraphLayout.setVerticalGroup(jPanel_GraphLayout
				.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(jLabel_Graph,
						org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 622,
						Short.MAX_VALUE));

		jScrollPane_QT.setViewportView(jTree_QT);

		org.jdesktop.layout.GroupLayout jPanel_MQLayout = new org.jdesktop.layout.GroupLayout(
				jPanel_MQ);
		jPanel_MQ.setLayout(jPanel_MQLayout);
		jPanel_MQLayout.setHorizontalGroup(jPanel_MQLayout.createParallelGroup(
				org.jdesktop.layout.GroupLayout.LEADING).add(
				jPanel_MQLayout
						.createSequentialGroup()
						.addContainerGap()
						.add(jScrollPane_QT,
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								243, Short.MAX_VALUE).addContainerGap()));
		jPanel_MQLayout.setVerticalGroup(jPanel_MQLayout.createParallelGroup(
				org.jdesktop.layout.GroupLayout.LEADING).add(
				jPanel_MQLayout
						.createSequentialGroup()
						.addContainerGap()
						.add(jScrollPane_QT,
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								362, Short.MAX_VALUE).addContainerGap()));

		jTabbedPane_First.addTab("My Queries", jPanel_MQ);

		JTable artifactTable = new JTable(new ArtifactTableModel());
		JPanel detailDisplay = new JPanel();

		JSplitPane tabularSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, artifactTable, detailDisplay);
		JScrollPane panelScrollPane = new JScrollPane(jPanel_Graph);
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelScrollPane, tabularSplitPane);

		org.jdesktop.layout.GroupLayout jPanel_MainLayout = new org.jdesktop.layout.GroupLayout(
				jPanel_Main);
		jPanel_Main.setLayout(jPanel_MainLayout);

		JTabbedPane panel = new JTabbedPane();
		panel.addTab("User requests", null, jPanel_UR, "");
		panel.addTab("Queries", null, jTabbedPane_First, "");
		JPanel rpqPanel = generateRpqPanel();
		panel.addTab("RPQ", null, rpqPanel, "");

		JSplitPane mainContent = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, panel, splitPane);

		jPanel_MainLayout
				.setHorizontalGroup(jPanel_MainLayout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(jPanel_Menu,
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)
						.add(org.jdesktop.layout.GroupLayout.TRAILING,
								jPanel_MainLayout
										.createSequentialGroup()
										.add(mainContent,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)));
										//.add(jPanel_Graph,
		jPanel_MainLayout
				.setVerticalGroup(jPanel_MainLayout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(jPanel_MainLayout
								.createSequentialGroup()
								.add(jPanel_Menu,
										org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
										org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
										org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										org.jdesktop.layout.LayoutStyle.RELATED)
								.add(jPanel_MainLayout
										.createParallelGroup(
												org.jdesktop.layout.GroupLayout.LEADING,
												false)
										.add(mainContent,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))));
										//.add(jPanel_Graph,

		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				org.jdesktop.layout.GroupLayout.LEADING).add(
				layout.createSequentialGroup()
						.addContainerGap()
						.add(jPanel_Main,
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE).addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(
				org.jdesktop.layout.GroupLayout.LEADING).add(
				layout.createSequentialGroup()
						.addContainerGap()
						.add(jPanel_Main,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));

		pack();
	}// </editor-fold>

	//GEN-END:initComponents

	private void addListener(javax.swing.JTree tree) {
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
                FileDriver fd = new FileDriver();
				TreePath path = e.getPath();
				Object clickedObj = path.getLastPathComponent();

                PayloadTreeNode treeNode = null;
                try {
                    treeNode = (PayloadTreeNode) clickedObj;
                }
                catch(ClassCastException ex) {
                    if (clickedObj.toString().equals("Root!")) {
                        System.out.println("CCE, but it's probably just the root");
						return;
                    }
                    else {
                        System.out.println("Unexplained CCE!");
                    }
                }
				Object payloadObject = treeNode.getPayload();
                RunContext selectedRunContext = (RunContext) payloadObject;
                currentDisplayedModel = selectedRunContext.getModel();
                File userRequestFile = selectedRunContext.getUserRequestFile();
                System.out.println("User request file of item selected: " + userRequestFile.getAbsolutePath());
                System.out.println("UR signature: " + System.identityHashCode(selectedRunContext));
                String userRequests = fd.readFile(userRequestFile.getAbsolutePath()).toString();
                jTextArea_UR.setText(userRequests);
                displayLatestStageImage();
//				currentlySelectedId = treeNode.getId();
                currentlySelectedTreeNode = treeNode;

			}
		});
	}

	private Object currentlySelectedTreeNode = null;
//	private List<URContext> currentlySelectedPathContexts = new ArrayList<URContext>();
//	private Integer currentlySelectedId = -1;
//	private String assembledRequests = null;

	private void jButton_IGActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private JPanel generateRpqPanel() {

		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		final JTextArea textArea = new JTextArea();
		textArea.setRows(5);
		textArea.setColumns(20);

		GridBagConstraints textAreaGbc = new GridBagConstraints();
		textAreaGbc.gridx = 0;
		textAreaGbc.gridy = 1;

		JScrollPane sp = new JScrollPane(textArea);
		sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panel.add(sp, textAreaGbc);

		GridBagConstraints buttonGbc = new GridBagConstraints();
		buttonGbc.gridx = 0;
		buttonGbc.gridy = 2;

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());

		JButton queryButton = new JButton("Query");
		final String dlvPath = constants.RPQ_DLV_PATH;

		RPQQueryButtonAction queryButtonAction = new RPQQueryButtonAction();
		queryButtonAction.setDlvPath(dlvPath);
		queryButtonAction.setTextArea(textArea);
		queryButtonAction.setCallback(this);
		queryButtonAction.setDialog(panel);
		queryButton.setAction(queryButtonAction);

		queryButton.setText("Query");

		buttonPanel.add(queryButton);

		panel.add(buttonPanel, buttonGbc);

		GridBagConstraints labelGbc = new GridBagConstraints();
		labelGbc.gridx = 0;
		labelGbc.gridy = 0;
		panel.add(new JLabel("Enter query:"), labelGbc);

		return panel;
	}

	private void jButton_LastActionPerformed(java.awt.event.ActionEvent evt) {
        Model model = currentDisplayedModel;
		System.out.println("Skipping to state: " + model.getFinalStateNo());
		currState = model.getFinalStateNo();
		System.out.println("Currstate set to " + currState + " of " + currentDisplayedModel.getFinalStateNo());
		displayImage(model.getIntrmediateModel(currState));
		writeModel();
	}
	private void jButton_NextActionPerformed(java.awt.event.ActionEvent evt) {
        Model model = currentDisplayedModel;
		if (currState < model.getFinalStateNo()) {
			currState++;
			System.out.println("Currstate set to " + currState + " of " + currentDisplayedModel.getFinalStateNo());
			displayImage(model.getIntrmediateModel(currState));
			writeModel();
		}
		else {
			System.out.println("Not moving, already at state: " + model.getFinalStateNo());
		}
	}
	private void jButton_PrevActionPerformed(java.awt.event.ActionEvent evt) {
        Model model = currentDisplayedModel;
		if (currState > 0) {
			currState--;
			System.out.println("Currstate set to " + currState + " of " + currentDisplayedModel.getFinalStateNo());
			displayImage(model.getIntrmediateModel(currState));
			writeModel();
		}
		else {
			System.out.println("Not moving, already at state 0");
		}
	}
	private void jButton_FirstActionPerformed(java.awt.event.ActionEvent evt) {
        Model model = currentDisplayedModel;
		currState = 0;
		System.out.println("Currstate set to " + currState + " of " + currentDisplayedModel.getFinalStateNo());
		displayImage(model.getIntrmediateModel(currState));
		writeModel();
	}

	private void writeModel() {
		Model model = currentDisplayedModel;
		ArrayList<String> modelArray = model.getIntrmediateModel(currState);
		File f = writeLinesTo(modelArray);
		System.out.println("Partial model stage " + currState + " written to: " + f.getAbsolutePath());
	}

	private File writeLinesTo(ArrayList<String> a) {
		File f = null;
		Writer w = null;
		try {
			f = File.createTempFile("model", ".txt");
			w = new FileWriter(f);
			for (String i : a) {
				w.write(i + "\n");
			}
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


	private void jButton_ExploreActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void jButton_SetActionPerformed(java.awt.event.ActionEvent evt) {
		if (currentlySelectedTreeNode == null) {
			System.out.println("None selected, aborting set.");
			JOptionPane.showMessageDialog(null, "Please click on an entry in the query tree first.");
		}
		else {

			RunContext rc = (RunContext) ((PayloadTreeNode) currentlySelectedTreeNode).getPayload();
			if (rc.getModel().getFinalStateNo() != currState) {
				JOptionPane.showMessageDialog(null, "Cannot set a non-final state as the basis for further queries", "Error - Can't Set", JOptionPane.ERROR_MESSAGE);
				return;
			}

			GlobalContext.getInstance().setCurrentContext(rc);
			jTextArea_UR.setText("");

			// Could whisper sweet nothings to an update method, or take a sledgehammer to it.
			jTree_QT = GlobalContext.getInstance().buildTree();
			addListener(jTree_QT);
			jScrollPane_QT.setViewportView(jTree_QT);
		}
	}

	private void jButton_NIActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void jButton_SLActionPerformed(java.awt.event.ActionEvent evt) {

        String userRequestString = jTextArea_UR.getText();
        File userRequestFile = writeSafe(userRequestString, ".dlv");
        RunContext newRunContext = GlobalContext.getInstance().getCurrentRunContext().withNewUserRequestFile(userRequestFile, currState);
		newRunContext.setIconType(IconType.SWALLOW);
        System.out.println("Adding child " + newRunContext + " of parent " + currentRunContext);
//        GlobalContext.getInstance().addChild(GlobalContext.getInstance().getCurrentRunContext(), newRunContext);
        GlobalContext.getInstance().addChildOfCurrentContext(newRunContext);

        Model model = newRunContext.getModel();
        currState = model.getFinalStateNo();
        currentDisplayedModel = model;
        displayLatestStageImage();

        jTree_QT = GlobalContext.getInstance().buildTree();
        addListener(jTree_QT);
        jScrollPane_QT.setViewportView(jTree_QT);


        // My code is over.
        if (1 == 1) {
            return;
        }
		id++;
		currState = 0;
		process(sessionId,id,stateNo, null);

		Integer key = new Integer(id);
		//ht_ele.add(key);
		//System.out.println("Added " + id + ", so now ht_ele has: " + ht_ele);
		// add key as a child to base_id
		if (!htQueryTree.containsKey(base_id)) {
			htQueryTree.put(base_id, new ArrayList<Integer>());
		}
		htQueryTree.get(base_id).add(key);
		//htQueryTree.put(new Integer(base_id), ht_ele);
		String userRequests = jTextArea_UR.getText();
		String modelData = model.getModel();
		URContext context = new URContext(userRequests, modelData);
		queryTreeContexts.put(key, context);
		// htQueryTree is a map that maps from Item ID -> ArrayList of child IDs.
		// We need a companion map that maps from Item ID to URContext, which
		// for the moment will just encapsulate User Requests.
		//
		//System.out.println("Added " + base_id + ":" + ht_ele + " to: " + htQueryTree);

		System.out.println("Right before ProPubApp call");
		jTree_QT = bt.getTree(htQueryTree, queryTreeContexts);
		addListener(jTree_QT);
		jScrollPane_QT.setViewportView(jTree_QT);
	}

    private File writeSafe(String string, String suffix) {
        FileDriver fd = new FileDriver();
        File tmpDir = new File(System.getProperty("java.io.tmpdir"));
        Random random = new Random();
        File newFile = new File(tmpDir, random.nextInt() + suffix);
        System.out.println("Writing string of length " + string.length() + ": " + newFile.getAbsolutePath());
        fd.writeFile(new StringBuffer(string), newFile.getAbsolutePath());
        return newFile;
    }

    private void jButton_SaveActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void jButton_OpenActionPerformed(java.awt.event.ActionEvent evt) {
		EnvInfo ei = new EnvInfo();
		ei.setSetupInfo(constants);
		if ("sean".equals(System.getProperty("user.name"))) {
			System.out.println("Welcome, Sean");
			File loadFile = new File("/Users/sean/propub/propub/data/pg.dlv");
			RunContext runContext = GlobalContext.getInstance().initialLoad(loadFile);
			GlobalContext.getInstance().setCurrentContext(runContext);
			Model model = runContext.getModel();
			currentDisplayedModel = model;
			currState = model.getFinalStateNo();
			displayLatestStageImage();
		}
		else {

			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("DLV input file", "dlv");
			chooser.setFileFilter(filter);
			int returnVal = chooser.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File loadFile = chooser.getSelectedFile();
				RunContext runContext = GlobalContext.getInstance().initialLoad(loadFile);
				GlobalContext.getInstance().setCurrentContext(runContext);
				Model model = runContext.getModel();
				currentDisplayedModel = model;
				currState = model.getFinalStateNo();
				displayLatestStageImage();
			}
			else {
				// Do nothing.
			}
		}


        // All this stuff should not be necessary now.
//		String userRequests = jTextArea_UR.getText();
//		String modelData = model.getModel();
//		URContext rootContext = new URContext(userRequests, modelData);
//		// TODO: remove hardwired value 0.
//		queryTreeContexts.put(0, rootContext);
//
//		ht_ele = new ArrayList<Integer>();
//
//		if (!htQueryTree.containsKey(base_id)) {
//			htQueryTree.put(base_id, new ArrayList<Integer>());
//		}
//		jTree_QT = bt.getTree(htQueryTree, queryTreeContexts);
//		addListener(jTree_QT);
//		jScrollPane_QT.setViewportView(jTree_QT);

        jTree_QT = GlobalContext.getInstance().buildTree();
        addListener(jTree_QT);
        jScrollPane_QT.setViewportView(jTree_QT);
	}



    private void jButton_NewActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void process(int sessionId, int id, int stateNo, String file) {
        System.out.println("Call to process, find out where this is from and kill it.");
        if (1 == 1) {
            System.exit(-1);
        }
		//copy pg
		System.out.println("-----------------------------------------------------");
		FileDriver fd = new FileDriver();
		if (file != null) {
			String inPGFile  = file;
			String exePGFile = getFileName(constants.PROPUB_EXE, "pg", "dlv");
			String outPGFile = getFileName(sessionId,id,stateNo, constants.PROPUB_OUT, "pg", "dlv");
			System.out.println("'" + inPGFile + "' -> '" + outPGFile + "' (branch 1)");
			fd.copyFile(inPGFile, outPGFile);
			System.out.println("'" + inPGFile + "' -> '" + exePGFile + "' (branch 1)");
			fd.copyFile(inPGFile, exePGFile);
		} else {
			String outPGFile  = getFileName(sessionId,id-1,stateNo, constants.PROPUB_OUT, "pg", "dlv");
			String exePGFile = getFileName(constants.PROPUB_EXE, "pg", "dlv");
			System.out.println("******* '" + outPGFile + "' -> '" + exePGFile + "' (branch 2)");
			fd.copyFile(outPGFile, exePGFile);
		}

		//read ur and copy
		String exeURFile  = getFileName(constants.PROPUB_EXE, "ur", "dlv");
		String outURFile  = getFileName(sessionId,id,stateNo, constants.PROPUB_OUT, "ur", "dlv");
        String assembledRequests = null;  // SEAN: Just a placeholder to get this dead code to compile.
		if (assembledRequests == null) {
			System.out.println("No assembled requests");
			fd.writeFile(new StringBuffer(jTextArea_UR.getText()), exeURFile);
			System.out.println("UR data -> '" + exeURFile + "' (branch 3)");
			fd.writeFile(new StringBuffer(jTextArea_UR.getText()), outURFile);
			System.out.println("UR data -> '" + outURFile + "' (branch 4)");
		}
		else {
			System.out.println("Some assembled requests");
			fd.writeFile(new StringBuffer(assembledRequests + "\n" + jTextArea_UR.getText()), exeURFile);
			System.out.println("UR data... -> '" + exeURFile + "' (branch 5)");
			fd.writeFile(new StringBuffer(assembledRequests + "\n" + jTextArea_UR.getText()), outURFile);
			System.out.println("UR data... -> '" + outURFile + "' (branch 6)");
		}

		System.out.println("-----------------------------------------------------");
		//call dlv
		DLVDriver dlv = new DLVDriver();
		dlv.exeDLV(new String[] {constants.SL_DLV_PATH}); // Constructed an
		// array so this will compile, but it still won't work.

        // SEAN: At the moment, this is dead code anyway due to the return
        // above. Just commented out to allow compilation.
//		//Set up the model
//		model = new Model(constants);
//
//		//display image
//		displayImage(model.getIntrmediateModel(model.getFinalStateNo()));
		//set query
	}

    private void displayLatestStageImage() {
        Model model = currentDisplayedModel;
        int latestStage = model.getFinalStateNo();
        displayImage(model.getIntrmediateModel(latestStage));
        currState = latestStage;
		System.out.println("Currstate set to " + currState + " of " + currentDisplayedModel.getFinalStateNo());
    }

	private void displayImage(ArrayList<String> model) {
		FileDriver fd = new FileDriver();
		StringBuffer sb = new StringBuffer();
		for (String s : model) {
			sb.append("% " + s + "\n");
		}

		System.out.println("model----");
		System.out.println(model);

		DOTDriver dot = new DOTDriver(constants);
		String imgFile = dot.prepareImgage(model);
		try {
			BufferedImage myPicture = ImageIO.read(new File(imgFile));
			jLabel_Graph.setIcon(new ImageIcon(myPicture));
		} catch (Exception e) {
			System.out.println("Errors..");
			e.printStackTrace();
		}
	}

	private String getFileName(int sessionId, int id, int stateNo, String dir, String file, String extn) {
		String fileName =
				dir +
				constants.ENV_SEPARATOR +
				file +
				"_" +
				sessionId + "." +
				id + "." +
				stateNo + "." +
				extn;

		return fileName;
	}

	private String getFileName(String dir, String file, String extn) {
		String fileName =
				dir +
				constants.ENV_SEPARATOR +
				file + "." +
				extn;

		return fileName;
	}

	Constants constants = new Constants();
	BuildTree bt = new BuildTree();
	Hashtable<String, Query> htQuery = new Hashtable<String, Query>();
//	Model model;

	int sessionId = 0;
	int id        = 0;
	int stateNo   = 0;
	int parentId  = 0;
	int currState = 0;

	Hashtable<Integer, ArrayList> htQueryTree = new Hashtable<Integer, ArrayList>();
	Hashtable<Integer, URContext> queryTreeContexts = new Hashtable<Integer, URContext>();
	ArrayList<Integer> ht_ele;
	int base_id = 0;

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				ProPubApp.getInstance().setVisible(true);

			}
		});
	}

	private static void initButtonGraphic(javax.swing.JButton button, String textLabel, String iconCategory, String iconName, String tooltip) {
		//button.setText(textLabel);		// Only for now.
		String urlString = "toolbarButtonGraphics/" + iconCategory + "/" + iconName + "24.gif";
		URL url = ProPubApp.class.getClassLoader().getResource(urlString);
		Icon icon = new ImageIcon(url);
		button.setIcon(icon);
		button.setToolTipText(tooltip);
	}

	public Model getCurrentDisplayedModel() {
		return currentDisplayedModel;
	}

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JButton jButton_Explore;
	private javax.swing.JButton jButton_First;
	private javax.swing.JButton jButton_IG;
	private javax.swing.JButton jButton_Last;
	private javax.swing.JButton jButton_NI;
	private javax.swing.JButton jButton_New;
	private javax.swing.JButton jButton_Next;
	private javax.swing.JButton jButton_Open;
	private javax.swing.JButton jButton_Prev;
	private javax.swing.JButton jButton_SL;
	private javax.swing.JButton jButton_Save;
	private javax.swing.JButton jButton_Set;
	private javax.swing.JLabel jLabel_Graph;
	private javax.swing.JPanel jPanel_Graph;
	private javax.swing.JPanel jPanel_MQ;
	private javax.swing.JPanel jPanel_Main;
	private javax.swing.JPanel jPanel_Menu;
	private javax.swing.JPanel jPanel_UR;
	private javax.swing.JScrollPane jScrollPane_QT;
	private javax.swing.JScrollPane jScrollPane_UR;
	private javax.swing.JTabbedPane jTabbedPane_First;
	private javax.swing.JTextArea jTextArea_UR;
	private javax.swing.JTree jTree_QT;

    private RunContext currentRunContext = null;
    private Model currentDisplayedModel = null;
	// End of variables declaration//GEN-END:variables

	private static ProPubApp instance = null;
}
