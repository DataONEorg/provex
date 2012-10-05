/*
 * ProPubApp.java
 *
 * Created on __DATE__, __TIME__
 */

package propub;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.Writer;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

import java.util.List;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.net.URL;

import javax.imageio.ImageIO;

//import com.sun.org.apache.xerces.internal.util.DOMUtil;
import parser.Model;

import db.DLVDriver;
import dot.DOTDriver;

import env.EnvInfo;
import file.FileDriver;

import javax.swing.table.TableCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;

import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;

import re.RGrammar;
import types.IconType;
import types.URContext;
import types.PayloadTreeNode;

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
		jPanel_RPQ = new javax.swing.JPanel();
		jScrollPane_QT = new javax.swing.JScrollPane();
		jScrollPane_RPQ = new javax.swing.JScrollPane();
		jTree_QT = GlobalContext.getInstance().buildTree();
		jPanel_QT = new JPanel();
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

		//jPanel_UR.setBorder(javax.swing.BorderFactory.createTitledBorder(
				//javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1),
				//"User Requests", javax.swing.border.TitledBorder.CENTER,
				//javax.swing.border.TitledBorder.DEFAULT_POSITION));

		jTextArea_UR.setColumns(20);
		jTextArea_UR.setRows(5);
		jScrollPane_UR.setViewportView(jTextArea_UR);

		GridBagConstraints labelConstraints = new GridBagConstraints();
		labelConstraints.gridx = 0;
		labelConstraints.gridy = 0;
		labelConstraints.weightx = 1;
		labelConstraints.weighty = 1;

		GridBagConstraints scrollPaneConstraints = new GridBagConstraints();
		scrollPaneConstraints.gridx = 0;
		scrollPaneConstraints.gridy = 1;
		scrollPaneConstraints.weightx = 1;
		scrollPaneConstraints.weighty = 1;

		GridBagConstraints blankConstraints = new GridBagConstraints();
		blankConstraints.gridx = 0;
		blankConstraints.gridy = 2;
		blankConstraints.fill = GridBagConstraints.VERTICAL;
		blankConstraints.weightx = 1;
		blankConstraints.weighty = 20;

		JPanel notStupidPanel = new JPanel();
		notStupidPanel.setLayout(new GridBagLayout());
		notStupidPanel.add(new JLabel("User Requests"), labelConstraints);
		notStupidPanel.add(jScrollPane_UR, scrollPaneConstraints);
		notStupidPanel.add(new JLabel(""), blankConstraints);

		//jPanel_Graph.setBorder(new javax.swing.border.SoftBevelBorder(
				//javax.swing.border.BevelBorder.RAISED));

		jLabel_Graph.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		jLabel_Graph.setFocusable(false);
		jLabel_Graph
				.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

		//org.jdesktop.layout.GroupLayout jPanel_GraphLayout = new org.jdesktop.layout.GroupLayout(
				//jPanel_Graph);
		//jPanel_Graph.setLayout(jPanel_GraphLayout);
		//jPanel_GraphLayout.setHorizontalGroup(jPanel_GraphLayout
				//.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				//.add(jLabel_Graph,
						//org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 772,
						//Short.MAX_VALUE));
		//jPanel_GraphLayout.setVerticalGroup(jPanel_GraphLayout
				//.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				//.add(jLabel_Graph,
						//org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 622,
						//Short.MAX_VALUE));
		jPanel_Graph.setLayout(new GridBagLayout());
		GridBagConstraints graphPanelConstraints = new GridBagConstraints();
		graphPanelConstraints.gridx = 0;
		graphPanelConstraints.gridy = 0;
		graphPanelConstraints.weightx = 1;
		graphPanelConstraints.weighty = 1;
		graphPanelConstraints.fill = GridBagConstraints.BOTH;
		jPanel_Graph.add(jLabel_Graph, graphPanelConstraints);

		jScrollPane_QT.setViewportView(jTree_QT);

		JPanel queryTreePanel = new JPanel();
		queryTreePanel.setLayout(new GridBagLayout());
		
		GridBagConstraints treeLabelConstraints = new GridBagConstraints();
		treeLabelConstraints.gridx = 0;
		treeLabelConstraints.gridy = 0;
		treeLabelConstraints.weightx = 1;
		treeLabelConstraints.weighty = 1;

		GridBagConstraints treeConstraints = new GridBagConstraints();
		treeConstraints.gridx = 0;
		treeConstraints.gridy = 1;
		treeConstraints.fill = GridBagConstraints.HORIZONTAL;
		treeConstraints.weightx = 1;
		treeConstraints.weighty = 1;

		GridBagConstraints queryBlankConstraints = new GridBagConstraints();
		queryBlankConstraints.gridx = 0;
		queryBlankConstraints.gridy = 2;
		queryBlankConstraints.fill = GridBagConstraints.VERTICAL;
		queryBlankConstraints.weightx = 1;
		queryBlankConstraints.weighty = 20;

		queryTreePanel.add(new JLabel("My Queries"), treeLabelConstraints);
		queryTreePanel.add(jScrollPane_QT, treeConstraints);
		queryTreePanel.add(new JLabel(""), queryBlankConstraints);

		//jPanel_QT = new JPanel();

		//org.jdesktop.layout.GroupLayout jPanel_QTLayout = new org.jdesktop.layout.GroupLayout(
				//jPanel_QT);
		//jPanel_QT.setLayout(jPanel_QTLayout);
		//jPanel_QTLayout.setHorizontalGroup(jPanel_QTLayout.createParallelGroup(
				//org.jdesktop.layout.GroupLayout.LEADING).add(jScrollPane_QT,
				//org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 262,
				//Short.MAX_VALUE));
		//jPanel_QTLayout.setVerticalGroup(jPanel_QTLayout.createParallelGroup(
				//org.jdesktop.layout.GroupLayout.LEADING).add(jScrollPane_QT,
				//org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 177,
				//Short.MAX_VALUE));

		//jPanel_QT.setBorder(javax.swing.BorderFactory.createTitledBorder(
				//javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1),
				//"My Queries", javax.swing.border.TitledBorder.CENTER,
				//javax.swing.border.TitledBorder.DEFAULT_POSITION));

		org.jdesktop.layout.GroupLayout jPanel_RPQLayout = new org.jdesktop.layout.GroupLayout(
				jPanel_RPQ);
		jPanel_RPQ.setLayout(jPanel_RPQLayout);
		jPanel_RPQLayout.setHorizontalGroup(jPanel_RPQLayout.createParallelGroup(
				org.jdesktop.layout.GroupLayout.LEADING).add(
				jPanel_RPQLayout
						.createSequentialGroup()
						.addContainerGap()
						.add(jScrollPane_RPQ,
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								243, Short.MAX_VALUE).addContainerGap()));
		jPanel_RPQLayout.setVerticalGroup(jPanel_RPQLayout.createParallelGroup(
				org.jdesktop.layout.GroupLayout.LEADING).add(
				jPanel_RPQLayout
						.createSequentialGroup()
						.addContainerGap()
						.add(jScrollPane_RPQ,
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								362, Short.MAX_VALUE).addContainerGap()));





		artifactTable = new JTable(new ArtifactTableModel());
		artifactTable.addMouseListener(new ArtifactTableDoubleClickListener());

		artifactTable.setPreferredScrollableViewportSize(new Dimension(450, 200));
		JScrollPane artifactTableScrollPane = new JScrollPane(artifactTable);
		JScrollPane panelScrollPane = new JScrollPane(jPanel_Graph);
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelScrollPane, artifactTableScrollPane);

		org.jdesktop.layout.GroupLayout jPanel_MainLayout = new org.jdesktop.layout.GroupLayout(
				jPanel_Main);
		jPanel_Main.setLayout(jPanel_MainLayout);

		JTabbedPane panel = new JTabbedPane();
		panel.addTab("User requests", notStupidPanel);
		panel.addTab("Queries", null, queryTreePanel, "");
		rpqPanel = generateRpqPanel();
		//jScrollPane_RPQ.setViewportView(rpqPanel);
		//panel.addTab("RPQ", jPanel_RPQ);
		panel.addTab("RPQ", rpqPanel);

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

		setPreferredSize(new Dimension(750, 575));
		// NOTE: This value was arrived at experimentally. I'm not sure how
		// GroupLayout decides how large to make the content pane.
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
		textAreaGbc.anchor = GridBagConstraints.PAGE_START;
		textAreaGbc.fill = GridBagConstraints.VERTICAL;
		textAreaGbc.weightx = 1;
		textAreaGbc.weighty = 1;

		JScrollPane sp = new JScrollPane(textArea);
		sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panel.add(sp, textAreaGbc);

		GridBagConstraints buttonGbc = new GridBagConstraints();
		buttonGbc.gridx = 0;
		buttonGbc.gridy = 2;
		buttonGbc.anchor = GridBagConstraints.PAGE_START;
		buttonGbc.fill = GridBagConstraints.VERTICAL;
		buttonGbc.weightx = 1;
		buttonGbc.weighty = 1;

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
		labelGbc.anchor = GridBagConstraints.PAGE_START;
		labelGbc.fill = GridBagConstraints.VERTICAL;
		labelGbc.weightx = 1;
		labelGbc.weighty = 1;

		panel.add(new JLabel("Enter query:"), labelGbc);

		GridBagConstraints labelGbc2 = new GridBagConstraints();
		labelGbc2.gridx = 0;
		labelGbc2.gridy = 3;
		labelGbc2.anchor = GridBagConstraints.PAGE_START;
		labelGbc2.fill = GridBagConstraints.VERTICAL;
		labelGbc2.weightx = 1;
		labelGbc2.weighty = 20;

		placeholder = new JLabel("");

		panel.add(placeholder, labelGbc2);


		return panel;
	}

	public void removePlaceholder() {
		if (rpqPanel == null || placeholder == null) {
			return;
		}

		rpqPanel.remove(placeholder);
	}

	private JPanel rpqPanel = null;
	private JLabel placeholder = null;

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
            resetTree();
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

        resetTree();

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
			File loadFile;
			if (new File("/Users").exists()) {
				loadFile = new File("/Users/sean/propub/propub/data/pg_fpc.dlv");
			}
			else {
				loadFile = new File("/home/sean/propub/propub/data/pg.dlv");
			}
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

        resetTree();
	}


    public void resetTree() {
        jTree_QT = GlobalContext.getInstance().buildTree();
        addListener(jTree_QT);
        jScrollPane_QT.setViewportView(jTree_QT);

    }



    private void jButton_NewActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

    private void displayLatestStageImage() {
        Model model = currentDisplayedModel;
        int latestStage = model.getFinalStateNo();
        displayImage(model.getIntrmediateModel(latestStage));
        currState = latestStage;
		System.out.println("Currstate set to " + currState + " of " + currentDisplayedModel.getFinalStateNo());
    }

	public void displayImage(ArrayList<String> model) {
		DOTDriver dot = new DOTDriver(constants);
		String imgFile = dot.prepareImgage(model);
		try {
			BufferedImage myPicture = ImageIO.read(new File(imgFile));
			jLabel_Graph.setIcon(new ImageIcon(myPicture));
		} catch (Exception e) {
			System.out.println("Errors..");
			e.printStackTrace();
		}

		Map<String, List<Metadatum>> allMetadata = new HashMap<String, List<Metadatum>>();
		Set<String> predicatePrefixes = new HashSet<String>(Arrays.asList("vis_a(", "vis_d("));

		for (String prefix : predicatePrefixes) {
			allMetadata.put(prefix, new ArrayList<Metadatum>());
		}

		for (String modelElement : model) {
			for (String prefix : predicatePrefixes) {
				if (!modelElement.startsWith(prefix)) {
					continue;
				}

				// slice off predicate name
				modelElement = modelElement.substring(prefix.length());
				// slice off period and closing parenthesis
				modelElement = modelElement.substring(0, modelElement.length() - 2);
				String[] parts = modelElement.split(",");
				Metadatum datum = new Metadatum();
				// id, url, description, category
				datum.addKV("id", parts[0]);
				datum.addKV("url", parts[1]);
				datum.addKV("description", parts[2]);
				datum.addKV("category", parts[3]);
				allMetadata.get(prefix).add(datum);
				System.out.println("Adding " + prefix.substring(4, prefix.length() - 1) + " fact");
			}
		}

		((ArtifactTableModel) artifactTable.getModel()).setMetadata(allMetadata);
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
	private javax.swing.JPanel jPanel_RPQ;
	private javax.swing.JPanel jPanel_Main;
	private javax.swing.JPanel jPanel_Menu;
	private javax.swing.JPanel jPanel_UR;
	private javax.swing.JScrollPane jScrollPane_QT;
	private javax.swing.JScrollPane jScrollPane_RPQ;
	private javax.swing.JScrollPane jScrollPane_UR;
	private javax.swing.JTabbedPane jTabbedPane_First;
	private javax.swing.JTextArea jTextArea_UR;
	private JPanel jPanel_QT;
	private javax.swing.JTree jTree_QT;

	private JTable artifactTable;

    private RunContext currentRunContext = null;
    private Model currentDisplayedModel = null;
	// End of variables declaration//GEN-END:variables

	private static ProPubApp instance = null;
}
