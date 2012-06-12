/*
 * ProPubApp.java
 *
 * Created on __DATE__, __TIME__
 */

package propub;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import parser.Model;

import db.DLVDriver;
import dot.DOTDriver;

import env.EnvInfo;
import file.FileDriver;

/**
 *
 * @author  __USER__
 */
public class ProPubApp extends javax.swing.JFrame {

	/** Creates new form ProPubApp */
	public ProPubApp() {
		super("ProPub: The Provenance Publisher");
		initComponents();
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
		jTree_QT = new javax.swing.JTree();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jPanel_Menu.setBorder(javax.swing.BorderFactory.createCompoundBorder(
				null, new javax.swing.border.SoftBevelBorder(
						javax.swing.border.BevelBorder.RAISED)));

		jButton_New.setText("New");
		jButton_New.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton_NewActionPerformed(evt);
			}
		});

		jButton_Open.setText("Open");
		jButton_Open.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton_OpenActionPerformed(evt);
			}
		});

		jButton_Save.setText("Save");
		jButton_Save.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton_SaveActionPerformed(evt);
			}
		});

		jButton_SL.setText("SL");
		jButton_SL.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton_SLActionPerformed(evt);
			}
		});

		jButton_NI.setText("NI");
		jButton_NI.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton_NIActionPerformed(evt);
			}
		});

		jButton_Set.setText("Set");
		jButton_Set.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton_SetActionPerformed(evt);
			}
		});

		jButton_Explore.setText("Explore");
		jButton_Explore.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton_ExploreActionPerformed(evt);
			}
		});

		jButton_First.setText("<<");
		jButton_First.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton_FirstActionPerformed(evt);
			}
		});

		jButton_Prev.setText("<");
		jButton_Prev.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton_PrevActionPerformed(evt);
			}
		});

		jButton_Next.setText(">");
		jButton_Next.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton_NextActionPerformed(evt);
			}
		});

		jButton_Last.setText(">>");
		jButton_Last.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton_LastActionPerformed(evt);
			}
		});

		jButton_IG.setText("IG");
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
						.add(jButton_New)
						.addPreferredGap(
								org.jdesktop.layout.LayoutStyle.RELATED)
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
						.add(jButton_Explore)
						.addPreferredGap(
								org.jdesktop.layout.LayoutStyle.RELATED)
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
						.add(jButton_IG).addContainerGap()));
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
												.add(jButton_New)
												.add(jButton_Open)
												.add(jButton_Save)
												.add(jButton_IG)
												.add(jButton_Last)
												.add(jButton_Explore)
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

		org.jdesktop.layout.GroupLayout jPanel_MainLayout = new org.jdesktop.layout.GroupLayout(
				jPanel_Main);
		jPanel_Main.setLayout(jPanel_MainLayout);
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
										.add(jPanel_MainLayout
												.createParallelGroup(
														org.jdesktop.layout.GroupLayout.LEADING)
												.add(jTabbedPane_First,
														org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
														272, Short.MAX_VALUE)
												.add(org.jdesktop.layout.GroupLayout.TRAILING,
														jPanel_UR,
														org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
														org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE))
										.addPreferredGap(
												org.jdesktop.layout.LayoutStyle.RELATED)
										.add(jPanel_Graph,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)));
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
										.add(jPanel_MainLayout
												.createSequentialGroup()
												.add(jPanel_UR,
														org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
														org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
														org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(
														org.jdesktop.layout.LayoutStyle.RELATED)
												.add(jTabbedPane_First, 0, 0,
														Short.MAX_VALUE))
										.add(jPanel_Graph,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))));

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

	private void jButton_IGActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void jButton_LastActionPerformed(java.awt.event.ActionEvent evt) {
		displayImage(model.getIntrmediateModel(model.getFinalStateNo()));
	}
	private void jButton_NextActionPerformed(java.awt.event.ActionEvent evt) {
		if (currState < model.getFinalStateNo()) {
			currState++;
			displayImage(model.getIntrmediateModel(currState));	
		}
	}
	private void jButton_PrevActionPerformed(java.awt.event.ActionEvent evt) {
		if (currState > 0) {
			currState--;
			displayImage(model.getIntrmediateModel(currState--));	
		}
	}
	private void jButton_FirstActionPerformed(java.awt.event.ActionEvent evt) {
		displayImage(model.getIntrmediateModel(0));
	}

	private void jButton_ExploreActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void jButton_SetActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void jButton_NIActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void jButton_SLActionPerformed(java.awt.event.ActionEvent evt) {	
		id++;
		currState = 0;
		process(sessionId,id,stateNo, null);
		
		ht_ele.add(new Integer(id));
		htQueryTree.put(new Integer(base_id), ht_ele);
		
		jTree_QT = bt.getTree(htQueryTree);
		jScrollPane_QT.setViewportView(jTree_QT);
	}

	private void jButton_SaveActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void jButton_OpenActionPerformed(java.awt.event.ActionEvent evt) {		
		EnvInfo ei = new EnvInfo();
		ei.setSetupInfo(constants);
		// SEAN: Make this no longer hardcoded, ask with a JFileChooser.
		process(sessionId,id,stateNo, "/Users/sean/ProPubProj/propub/data/pg.dlv");			
		
		ht_ele = new ArrayList<Integer>();

	}
	

	private void jButton_NewActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void process(int sessionId, int id, int stateNo, String file) {
		//copy pg
		FileDriver fd = new FileDriver();
		if (file != null) {
			String inPGFile  = file;
			String exePGFile = getFileName(constants.PROPUB_EXE, "pg", "dlv");	
			String outPGFile = getFileName(sessionId,id,stateNo, constants.PROPUB_OUT, "pg", "dlv");	
			fd.copyFile(inPGFile, outPGFile);
			fd.copyFile(inPGFile, exePGFile);			
		} else {
			String outPGFile  = getFileName(sessionId,id-1,stateNo, constants.PROPUB_OUT, "pg", "dlv");
			String exePGFile = getFileName(constants.PROPUB_EXE, "pg", "dlv");
			fd.copyFile(outPGFile, exePGFile);			
		}

		//read ur and copy
		String exeURFile  = getFileName(constants.PROPUB_EXE, "ur", "dlv");
		String outURFile  = getFileName(sessionId,id,stateNo, constants.PROPUB_OUT, "ur", "dlv");
		fd.writeFile(new StringBuffer(jTextArea_UR.getText()), exeURFile);
		fd.writeFile(new StringBuffer(jTextArea_UR.getText()), outURFile);
				
		//call dlv
		DLVDriver dlv = new DLVDriver();
		dlv.exeDLV(constants.SL_DLV_PATH);
		
		//Set up the model
		model = new Model(constants);
		
		//display image
		displayImage(model.getIntrmediateModel(model.getFinalStateNo()));
		//set query		
	}
	

	private void displayImage(ArrayList<String> model) {
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
	Model model;
	
	int sessionId = 0;
	int id        = 0;
	int stateNo   = 0;
	int parentId  = 0;
	int currState = 0;
	
	Hashtable<Integer, ArrayList> htQueryTree = new Hashtable<Integer, ArrayList>();
	ArrayList<Integer> ht_ele;
	int base_id = 0;
	
	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new ProPubApp().setVisible(true);
				
			}
		});
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
	// End of variables declaration//GEN-END:variables

}
