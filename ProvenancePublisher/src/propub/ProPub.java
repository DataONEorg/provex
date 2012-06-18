/*
 * ProPub.java
 *
 * Created on __DATE__, __TIME__
 */

package propub;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import javax.swing.GroupLayout;

import parser.DLVToDot;

import dot.DOTDriver;

import java.awt.Image;

/**
 *
 * @author  __USER__
 */
public class ProPub extends javax.swing.JFrame {

	/** Creates new form ProPub */
	public ProPub() {
		super("ProPub: The Provenance Publisher");
		initComponents();
		getSetupInfo();
	}

	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

		jTabbedPane_Propub = new javax.swing.JTabbedPane();
		jPanel_Opn = new javax.swing.JPanel();
		jPanelSelector = new javax.swing.JPanel();
		jLabel_FilePath = new javax.swing.JLabel();
		jButton_IG = new javax.swing.JButton();
		jTextFieldInputFilePath = new javax.swing.JTextField();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTree1 = new javax.swing.JTree();
		jButton_Set_Graph = new javax.swing.JButton();
		jButton_Show = new javax.swing.JButton();
		jPanel_OG_SL = new javax.swing.JPanel();
		jButton_Show_OG_SL = new javax.swing.JButton();
		jScrollPane_SL_LT = new javax.swing.JScrollPane();
		jButton_SL_LT = new javax.swing.JButton();
		jPanel_IG = new javax.swing.JPanel();
		jButton_Show_IG = new javax.swing.JButton();
		jPanel_UR = new javax.swing.JPanel();
		jScrollPane_UR = new javax.swing.JScrollPane();
		jButton_Calc_OG = new javax.swing.JButton();
		jTextArea_UR = new javax.swing.JTextArea();
		jPanel_OG_IN = new javax.swing.JPanel();
		jButton_Show_OG_IN = new javax.swing.JButton();
		jScrollPane_IN_LT = new javax.swing.JScrollPane();
		jButton_IN_LT = new javax.swing.JButton();
		jPanel_LT = new javax.swing.JPanel();
		jPanel_IG_LT = new javax.swing.JPanel();
		jButton_SHow_IG_LT = new javax.swing.JButton();
		jPanel2 = new javax.swing.JPanel();
		jTextField_UR_LT = new javax.swing.JTextField();
		jPanel_OG_LT = new javax.swing.JPanel();
		jButton_Show_OG_LT = new javax.swing.JButton();
		jButton_Prev = new javax.swing.JButton();
		jButton_Next = new javax.swing.JButton();
		jPanel_UA_LT = new javax.swing.JPanel();
		jTextField_UA_LT = new javax.swing.JTextField();
		jPanel_PV_LT = new javax.swing.JPanel();
		jTextField_PV_LT = new javax.swing.JTextField();
		jPanel_Setup = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		jTextField_root = new javax.swing.JTextField();
		jLabel2 = new javax.swing.JLabel();
		jTextField_separator = new javax.swing.JTextField();
		jLabel3 = new javax.swing.JLabel();
		jTextField_dot_path = new javax.swing.JTextField();
		jButton_Setup_Save = new javax.swing.JButton();
		jLabel4 = new javax.swing.JLabel();
		jTextField_dlv_path = new javax.swing.JTextField();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jPanel_Opn.setLayout(null);

		jPanelSelector.setBorder(javax.swing.BorderFactory.createTitledBorder(
				javax.swing.BorderFactory.createLineBorder(new java.awt.Color(
						204, 204, 204)), "Selector",
				javax.swing.border.TitledBorder.CENTER,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(102,
						102, 102)));
		jPanelSelector.setToolTipText("Selector");
		jPanelSelector.setName("Selector");

		jLabel_FilePath.setText("File Path:");

		jButton_IG.setText("Input");
		jButton_IG.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton_IGActionPerformed(evt);
			}
		});

		jTextFieldInputFilePath.setText("/Users/scdey/myspace/propub/data/pg.dlv");

		DefaultMutableTreeNode root = new DefaultMutableTreeNode("");
		jTree1 = new JTree(root);
		jScrollPane1.setViewportView(jTree1);

		jButton_Set_Graph.setText("Set");
		jButton_Set_Graph
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						jButton_Set_GraphActionPerformed(evt);
					}
				});

		jButton_Show.setText("Show");
		jButton_Show.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton_ShowActionPerformed(evt);
			}
		});

		org.jdesktop.layout.GroupLayout jPanelSelectorLayout = new org.jdesktop.layout.GroupLayout(
				jPanelSelector);
		jPanelSelector.setLayout(jPanelSelectorLayout);
		jPanelSelectorLayout
				.setHorizontalGroup(jPanelSelectorLayout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(jPanelSelectorLayout
								.createSequentialGroup()
								.addContainerGap()
								.add(jPanelSelectorLayout
										.createParallelGroup(
												org.jdesktop.layout.GroupLayout.LEADING)
										.add(jPanelSelectorLayout
												.createSequentialGroup()
												.add(jButton_IG)
												.addPreferredGap(
														org.jdesktop.layout.LayoutStyle.RELATED,
														41, Short.MAX_VALUE)
												.add(jButton_Show)
												.add(34, 34, 34)
												.add(jButton_Set_Graph))
										.add(jPanelSelectorLayout
												.createSequentialGroup()
												.add(13, 13, 13)
												.add(jLabel_FilePath,
														org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
														61,
														org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(
														org.jdesktop.layout.LayoutStyle.RELATED)
												.add(jTextFieldInputFilePath,
														org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
														167, Short.MAX_VALUE))
										.add(jScrollPane1,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												246, Short.MAX_VALUE))
								.addContainerGap(
										org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));
		jPanelSelectorLayout
				.setVerticalGroup(jPanelSelectorLayout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(jPanelSelectorLayout
								.createSequentialGroup()
								.add(jPanelSelectorLayout
										.createParallelGroup(
												org.jdesktop.layout.GroupLayout.BASELINE)
										.add(jTextFieldInputFilePath,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.add(jLabel_FilePath))
								.addPreferredGap(
										org.jdesktop.layout.LayoutStyle.UNRELATED)
								.add(jScrollPane1,
										org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
										208, Short.MAX_VALUE)
								.addPreferredGap(
										org.jdesktop.layout.LayoutStyle.UNRELATED)
								.add(jPanelSelectorLayout
										.createParallelGroup(
												org.jdesktop.layout.GroupLayout.BASELINE)
										.add(jButton_IG).add(jButton_Set_Graph)
										.add(jButton_Show)).addContainerGap()));

		jPanel_Opn.add(jPanelSelector);
		jPanelSelector.setBounds(10, 0, 280, 320);

		jPanel_OG_SL.setBorder(javax.swing.BorderFactory.createTitledBorder(
				javax.swing.BorderFactory.createLineBorder(new java.awt.Color(
						204, 204, 204)), "Output Graph: Swallow",
				javax.swing.border.TitledBorder.CENTER,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(102,
						102, 102)));
		jPanel_OG_SL.setToolTipText("Selector");
		jPanel_OG_SL.setName("Selector");

		jButton_Show_OG_SL.setBackground(new java.awt.Color(255, 255, 255));
		jButton_Show_OG_SL.setForeground(new java.awt.Color(255, 255, 255));

		jButton_SL_LT.setText("Look Through");
		jButton_SL_LT.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton_SL_LTActionPerformed(evt);
			}
		});
		jScrollPane_SL_LT.setViewportView(jButton_SL_LT);

		org.jdesktop.layout.GroupLayout jPanel_OG_SLLayout = new org.jdesktop.layout.GroupLayout(
				jPanel_OG_SL);
		jPanel_OG_SL.setLayout(jPanel_OG_SLLayout);
		jPanel_OG_SLLayout
				.setHorizontalGroup(jPanel_OG_SLLayout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(jPanel_OG_SLLayout
								.createSequentialGroup()
								.add(jPanel_OG_SLLayout
										.createParallelGroup(
												org.jdesktop.layout.GroupLayout.LEADING)
										.add(jPanel_OG_SLLayout
												.createSequentialGroup()
												.add(82, 82, 82)
												.add(jScrollPane_SL_LT,
														org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
														140,
														org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
										.add(jPanel_OG_SLLayout
												.createSequentialGroup()
												.addContainerGap()
												.add(jButton_Show_OG_SL,
														org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
														286, Short.MAX_VALUE)))
								.addContainerGap()));
		jPanel_OG_SLLayout.setVerticalGroup(jPanel_OG_SLLayout
				.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(jPanel_OG_SLLayout
						.createSequentialGroup()
						.add(jButton_Show_OG_SL,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
								224,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(
								org.jdesktop.layout.LayoutStyle.UNRELATED)
						.add(jScrollPane_SL_LT,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
								40,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(17, Short.MAX_VALUE)));

		jPanel_Opn.add(jPanel_OG_SL);
		jPanel_OG_SL.setBounds(300, 320, 320, 310);

		jPanel_IG.setBorder(javax.swing.BorderFactory.createTitledBorder(
				javax.swing.BorderFactory.createLineBorder(new java.awt.Color(
						204, 204, 204)), "Input Graph",
				javax.swing.border.TitledBorder.CENTER,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(102,
						102, 102)));
		jPanel_IG.setToolTipText("Selector");
		jPanel_IG.setName("Selector");

		jButton_Show_IG.setBackground(new java.awt.Color(255, 255, 255));
		jButton_Show_IG.setForeground(new java.awt.Color(255, 255, 255));

		org.jdesktop.layout.GroupLayout jPanel_IGLayout = new org.jdesktop.layout.GroupLayout(
				jPanel_IG);
		jPanel_IG.setLayout(jPanel_IGLayout);
		jPanel_IGLayout.setHorizontalGroup(jPanel_IGLayout.createParallelGroup(
				org.jdesktop.layout.GroupLayout.LEADING).add(
				jPanel_IGLayout
						.createSequentialGroup()
						.addContainerGap()
						.add(jButton_Show_IG,
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								616, Short.MAX_VALUE).addContainerGap()));
		jPanel_IGLayout.setVerticalGroup(jPanel_IGLayout.createParallelGroup(
				org.jdesktop.layout.GroupLayout.LEADING).add(
				jPanel_IGLayout
						.createSequentialGroup()
						.add(jButton_Show_IG,
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								281, Short.MAX_VALUE).addContainerGap()));

		jPanel_Opn.add(jPanel_IG);
		jPanel_IG.setBounds(304, 0, 650, 320);

		jPanel_UR.setBorder(javax.swing.BorderFactory.createTitledBorder(
				javax.swing.BorderFactory.createLineBorder(new java.awt.Color(
						204, 204, 204)), "User Requests",
				javax.swing.border.TitledBorder.CENTER,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(102,
						102, 102)));
		jPanel_UR.setToolTipText("Selector");
		jPanel_UR.setName("Selector");

		jButton_Calc_OG.setText("Execute");
		jButton_Calc_OG.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton_Calc_OGActionPerformed(evt);
			}
		});
		jScrollPane_UR.setViewportView(jButton_Calc_OG);

		jTextArea_UR.setColumns(20);
		jTextArea_UR.setRows(5);

		org.jdesktop.layout.GroupLayout jPanel_URLayout = new org.jdesktop.layout.GroupLayout(
				jPanel_UR);
		jPanel_UR.setLayout(jPanel_URLayout);
		jPanel_URLayout
				.setHorizontalGroup(jPanel_URLayout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(jPanel_URLayout
								.createSequentialGroup()
								.add(jPanel_URLayout
										.createParallelGroup(
												org.jdesktop.layout.GroupLayout.LEADING)
										.add(jPanel_URLayout
												.createSequentialGroup()
												.add(90, 90, 90)
												.add(jScrollPane_UR,
														org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
														100,
														org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
										.add(jPanel_URLayout
												.createSequentialGroup()
												.add(20, 20, 20)
												.add(jTextArea_UR,
														org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
														246,
														org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
								.addContainerGap(
										org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));
		jPanel_URLayout.setVerticalGroup(jPanel_URLayout.createParallelGroup(
				org.jdesktop.layout.GroupLayout.LEADING).add(
				org.jdesktop.layout.GroupLayout.TRAILING,
				jPanel_URLayout
						.createSequentialGroup()
						.add(jTextArea_UR,
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								223, Short.MAX_VALUE)
						.add(18, 18, 18)
						.add(jScrollPane_UR,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
								40,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));

		jPanel_Opn.add(jPanel_UR);
		jPanel_UR.setBounds(10, 320, 280, 310);

		jPanel_OG_IN.setBorder(javax.swing.BorderFactory.createTitledBorder(
				javax.swing.BorderFactory.createLineBorder(new java.awt.Color(
						204, 204, 204)), "Output Graph: Node Invention",
				javax.swing.border.TitledBorder.CENTER,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(102,
						102, 102)));
		jPanel_OG_IN.setToolTipText("Selector");
		jPanel_OG_IN.setName("Selector");

		jButton_Show_OG_IN.setBackground(new java.awt.Color(255, 255, 255));
		jButton_Show_OG_IN.setForeground(new java.awt.Color(255, 255, 255));

		jButton_IN_LT.setText("Look Through");
		jButton_IN_LT.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton_IN_LTActionPerformed(evt);
			}
		});
		jScrollPane_IN_LT.setViewportView(jButton_IN_LT);

		org.jdesktop.layout.GroupLayout jPanel_OG_INLayout = new org.jdesktop.layout.GroupLayout(
				jPanel_OG_IN);
		jPanel_OG_IN.setLayout(jPanel_OG_INLayout);
		jPanel_OG_INLayout
				.setHorizontalGroup(jPanel_OG_INLayout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(jPanel_OG_INLayout
								.createSequentialGroup()
								.add(jPanel_OG_INLayout
										.createParallelGroup(
												org.jdesktop.layout.GroupLayout.LEADING)
										.add(jPanel_OG_INLayout
												.createSequentialGroup()
												.add(82, 82, 82)
												.add(jScrollPane_IN_LT,
														org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
														140,
														org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
										.add(jPanel_OG_INLayout
												.createSequentialGroup()
												.addContainerGap()
												.add(jButton_Show_OG_IN,
														org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
														286, Short.MAX_VALUE)))
								.addContainerGap()));
		jPanel_OG_INLayout.setVerticalGroup(jPanel_OG_INLayout
				.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(jPanel_OG_INLayout
						.createSequentialGroup()
						.add(jButton_Show_OG_IN,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
								224,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(
								org.jdesktop.layout.LayoutStyle.UNRELATED)
						.add(jScrollPane_IN_LT,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
								40,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(17, Short.MAX_VALUE)));

		jPanel_Opn.add(jPanel_OG_IN);
		jPanel_OG_IN.setBounds(630, 320, 320, 310);

		jTabbedPane_Propub.addTab("Operations", jPanel_Opn);

		jPanel_IG_LT.setBorder(javax.swing.BorderFactory.createTitledBorder(
				javax.swing.BorderFactory.createLineBorder(new java.awt.Color(
						204, 204, 204)), "Initial Graph",
				javax.swing.border.TitledBorder.CENTER,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(102,
						102, 102)));
		jPanel_IG_LT.setForeground(new java.awt.Color(153, 153, 153));

		jButton_SHow_IG_LT.setBackground(new java.awt.Color(255, 255, 255));
		jButton_SHow_IG_LT.setForeground(new java.awt.Color(255, 255, 255));

		org.jdesktop.layout.GroupLayout jPanel_IG_LTLayout = new org.jdesktop.layout.GroupLayout(
				jPanel_IG_LT);
		jPanel_IG_LT.setLayout(jPanel_IG_LTLayout);
		jPanel_IG_LTLayout.setHorizontalGroup(jPanel_IG_LTLayout
				.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(jPanel_IG_LTLayout
						.createSequentialGroup()
						.addContainerGap()
						.add(jButton_SHow_IG_LT,
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								508, Short.MAX_VALUE).addContainerGap()));
		jPanel_IG_LTLayout
				.setVerticalGroup(jPanel_IG_LTLayout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(org.jdesktop.layout.GroupLayout.TRAILING,
								jPanel_IG_LTLayout
										.createSequentialGroup()
										.add(jButton_SHow_IG_LT,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												257, Short.MAX_VALUE)
										.addContainerGap()));

		jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(
				javax.swing.BorderFactory.createLineBorder(new java.awt.Color(
						204, 204, 204)), "User Requests",
				javax.swing.border.TitledBorder.CENTER,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(102,
						102, 102)));

		org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(
				jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout.createParallelGroup(
				org.jdesktop.layout.GroupLayout.LEADING).add(
				jPanel2Layout
						.createSequentialGroup()
						.addContainerGap()
						.add(jTextField_UR_LT,
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								359, Short.MAX_VALUE).addContainerGap()));
		jPanel2Layout.setVerticalGroup(jPanel2Layout.createParallelGroup(
				org.jdesktop.layout.GroupLayout.LEADING).add(
				jPanel2Layout
						.createSequentialGroup()
						.add(jTextField_UR_LT,
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								257, Short.MAX_VALUE).addContainerGap()));

		jPanel_OG_LT.setBorder(javax.swing.BorderFactory.createTitledBorder(
				javax.swing.BorderFactory.createLineBorder(new java.awt.Color(
						204, 204, 204)), "Output Graph",
				javax.swing.border.TitledBorder.CENTER,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(102,
						102, 102)));

		jButton_Show_OG_LT.setBackground(new java.awt.Color(255, 255, 255));
		jButton_Show_OG_LT.setForeground(new java.awt.Color(255, 255, 255));

		jButton_Prev.setText("Previous");
		jButton_Prev.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton_PrevActionPerformed(evt);
			}
		});

		jButton_Next.setText("Next");
		jButton_Next.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton_NextActionPerformed(evt);
			}
		});

		org.jdesktop.layout.GroupLayout jPanel_OG_LTLayout = new org.jdesktop.layout.GroupLayout(
				jPanel_OG_LT);
		jPanel_OG_LT.setLayout(jPanel_OG_LTLayout);
		jPanel_OG_LTLayout
				.setHorizontalGroup(jPanel_OG_LTLayout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(jPanel_OG_LTLayout
								.createSequentialGroup()
								.add(jPanel_OG_LTLayout
										.createParallelGroup(
												org.jdesktop.layout.GroupLayout.LEADING)
										.add(jPanel_OG_LTLayout
												.createSequentialGroup()
												.addContainerGap()
												.add(jButton_Show_OG_LT,
														org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
														508,
														org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
										.add(jPanel_OG_LTLayout
												.createSequentialGroup()
												.add(153, 153, 153)
												.add(jButton_Prev)
												.add(26, 26, 26)
												.add(jButton_Next)))
								.addContainerGap(
										org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));
		jPanel_OG_LTLayout
				.setVerticalGroup(jPanel_OG_LTLayout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(jPanel_OG_LTLayout
								.createSequentialGroup()
								.add(jButton_Show_OG_LT,
										org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
										249,
										org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										org.jdesktop.layout.LayoutStyle.RELATED)
								.add(jPanel_OG_LTLayout
										.createParallelGroup(
												org.jdesktop.layout.GroupLayout.BASELINE)
										.add(jButton_Prev).add(jButton_Next))
								.addContainerGap(
										org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));

		jPanel_UA_LT.setBorder(javax.swing.BorderFactory.createTitledBorder(
				javax.swing.BorderFactory.createLineBorder(new java.awt.Color(
						204, 204, 204)), "Updates Applied",
				javax.swing.border.TitledBorder.CENTER,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(102,
						102, 102)));

		org.jdesktop.layout.GroupLayout jPanel_UA_LTLayout = new org.jdesktop.layout.GroupLayout(
				jPanel_UA_LT);
		jPanel_UA_LT.setLayout(jPanel_UA_LTLayout);
		jPanel_UA_LTLayout.setHorizontalGroup(jPanel_UA_LTLayout
				.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(jPanel_UA_LTLayout
						.createSequentialGroup()
						.addContainerGap()
						.add(jTextField_UA_LT,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
								153,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));
		jPanel_UA_LTLayout.setVerticalGroup(jPanel_UA_LTLayout
				.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(jPanel_UA_LTLayout
						.createSequentialGroup()
						.add(jTextField_UA_LT,
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								281, Short.MAX_VALUE).addContainerGap()));

		jPanel_PV_LT.setBorder(javax.swing.BorderFactory.createTitledBorder(
				javax.swing.BorderFactory.createLineBorder(new java.awt.Color(
						204, 204, 204)), "Policy Violations",
				javax.swing.border.TitledBorder.CENTER,
				javax.swing.border.TitledBorder.DEFAULT_POSITION,
				new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(102,
						102, 102)));

		org.jdesktop.layout.GroupLayout jPanel_PV_LTLayout = new org.jdesktop.layout.GroupLayout(
				jPanel_PV_LT);
		jPanel_PV_LT.setLayout(jPanel_PV_LTLayout);
		jPanel_PV_LTLayout
				.setHorizontalGroup(jPanel_PV_LTLayout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(org.jdesktop.layout.GroupLayout.TRAILING,
								jPanel_PV_LTLayout
										.createSequentialGroup()
										.addContainerGap(17, Short.MAX_VALUE)
										.add(jTextField_PV_LT,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
												160,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.addContainerGap()));
		jPanel_PV_LTLayout.setVerticalGroup(jPanel_PV_LTLayout
				.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
				.add(jPanel_PV_LTLayout
						.createSequentialGroup()
						.add(jTextField_PV_LT,
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								281, Short.MAX_VALUE).addContainerGap()));

		org.jdesktop.layout.GroupLayout jPanel_LTLayout = new org.jdesktop.layout.GroupLayout(
				jPanel_LT);
		jPanel_LT.setLayout(jPanel_LTLayout);
		jPanel_LTLayout
				.setHorizontalGroup(jPanel_LTLayout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(jPanel_LTLayout
								.createSequentialGroup()
								.addContainerGap()
								.add(jPanel_LTLayout
										.createParallelGroup(
												org.jdesktop.layout.GroupLayout.LEADING)
										.add(jPanel_IG_LT,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
										.add(jPanel_OG_LT,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE))
								.addPreferredGap(
										org.jdesktop.layout.LayoutStyle.RELATED)
								.add(jPanel_LTLayout
										.createParallelGroup(
												org.jdesktop.layout.GroupLayout.TRAILING)
										.add(jPanel_LTLayout
												.createSequentialGroup()
												.add(jPanel_UA_LT,
														org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
														org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addPreferredGap(
														org.jdesktop.layout.LayoutStyle.RELATED)
												.add(jPanel_PV_LT,
														org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
														org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
														org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
										.add(jPanel2,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE))
								.addContainerGap()));
		jPanel_LTLayout
				.setVerticalGroup(jPanel_LTLayout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(jPanel_LTLayout
								.createSequentialGroup()
								.addContainerGap()
								.add(jPanel_LTLayout
										.createParallelGroup(
												org.jdesktop.layout.GroupLayout.LEADING,
												false)
										.add(jPanel2,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.add(jPanel_IG_LT,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE))
								.addPreferredGap(
										org.jdesktop.layout.LayoutStyle.RELATED)
								.add(jPanel_LTLayout
										.createParallelGroup(
												org.jdesktop.layout.GroupLayout.LEADING)
										.add(jPanel_PV_LT,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.add(jPanel_UA_LT,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.add(jPanel_OG_LT,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
								.addContainerGap()));

		jTabbedPane_Propub.addTab("Look Through", jPanel_LT);

		jLabel1.setText("Root:");

		jTextField_root.setText("Enter Root");

		jLabel2.setText("Environment Separator:");

		jTextField_separator.setText("Enter Separator");

		jLabel3.setText("DOT Path:");

		jTextField_dot_path.setText("Enter DOT Path");

		jButton_Setup_Save.setText("Save");
		jButton_Setup_Save
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						jButton_Setup_SaveActionPerformed(evt);
					}
				});

		jLabel4.setText("DLV Path:");

		jTextField_dlv_path.setText("Enter DLV Path");

		org.jdesktop.layout.GroupLayout jPanel_SetupLayout = new org.jdesktop.layout.GroupLayout(
				jPanel_Setup);
		jPanel_Setup.setLayout(jPanel_SetupLayout);
		jPanel_SetupLayout
				.setHorizontalGroup(jPanel_SetupLayout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(jPanel_SetupLayout
								.createSequentialGroup()
								.add(jPanel_SetupLayout
										.createParallelGroup(
												org.jdesktop.layout.GroupLayout.LEADING)
										.add(jPanel_SetupLayout
												.createSequentialGroup()
												.add(167, 167, 167)
												.add(jPanel_SetupLayout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.TRAILING)
														.add(jLabel3)
														.add(jLabel2)
														.add(jLabel1)
														.add(jLabel4))
												.addPreferredGap(
														org.jdesktop.layout.LayoutStyle.UNRELATED)
												.add(jPanel_SetupLayout
														.createParallelGroup(
																org.jdesktop.layout.GroupLayout.LEADING,
																false)
														.add(jTextField_dlv_path)
														.add(jTextField_dot_path)
														.add(jTextField_separator)
														.add(jTextField_root,
																org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
																255,
																Short.MAX_VALUE)))
										.add(jPanel_SetupLayout
												.createSequentialGroup()
												.add(274, 274, 274)
												.add(jButton_Setup_Save)))
								.addContainerGap(408, Short.MAX_VALUE)));
		jPanel_SetupLayout
				.setVerticalGroup(jPanel_SetupLayout
						.createParallelGroup(
								org.jdesktop.layout.GroupLayout.LEADING)
						.add(jPanel_SetupLayout
								.createSequentialGroup()
								.add(48, 48, 48)
								.add(jPanel_SetupLayout
										.createParallelGroup(
												org.jdesktop.layout.GroupLayout.BASELINE)
										.add(jLabel1)
										.add(jTextField_root,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
								.add(18, 18, 18)
								.add(jPanel_SetupLayout
										.createParallelGroup(
												org.jdesktop.layout.GroupLayout.BASELINE)
										.add(jLabel2)
										.add(jTextField_separator,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
								.add(18, 18, 18)
								.add(jPanel_SetupLayout
										.createParallelGroup(
												org.jdesktop.layout.GroupLayout.BASELINE)
										.add(jLabel3)
										.add(jTextField_dot_path,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
								.add(18, 18, 18)
								.add(jPanel_SetupLayout
										.createParallelGroup(
												org.jdesktop.layout.GroupLayout.BASELINE)
										.add(jLabel4)
										.add(jTextField_dlv_path,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
												org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
												org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
								.add(26, 26, 26).add(jButton_Setup_Save)
								.addContainerGap(404, Short.MAX_VALUE)));

		jTabbedPane_Propub.addTab("Setup", jPanel_Setup);

		org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				org.jdesktop.layout.GroupLayout.LEADING).add(
				layout.createSequentialGroup()
						.addContainerGap()
						.add(jTabbedPane_Propub,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
								1200,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(
				org.jdesktop.layout.GroupLayout.LEADING).add(
				layout.createSequentialGroup()
						.addContainerGap()
						.add(jTabbedPane_Propub,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
								800,
								org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(
								org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));

		pack();
	}// </editor-fold>
	//GEN-END:initComponents

	private void jButton_NextActionPerformed(java.awt.event.ActionEvent evt) {
		ltFinalStateNo++;
		
		String out_img_file = constants.PROPUB_DOT_OUT + constants.ENV_SEPARATOR + "pg_"
				+ version + "." + ltFinalStateNo + ".gif";

		String ppv_file = constants.PROPUB_OUT + constants.ENV_SEPARATOR + "ppv_"
				+ version + "." + ltFinalStateNo + ".dlv";
		String upd_file = constants.PROPUB_OUT + constants.ENV_SEPARATOR + "upd_"
				+ version + "." + ltFinalStateNo + ".dlv";

		jButton_Show_OG_LT.setIcon(new ImageIcon(out_img_file));
		
		System.out.println("ppv_file: " + ppv_file);
		System.out.println("upd_file: " + upd_file);
				
		try {
			FileReader input = new FileReader(ppv_file);
			BufferedReader urBufRead = new BufferedReader(input);
			String line = urBufRead.readLine();
			StringBuffer sb = new StringBuffer();
			while (line != null) {
				sb.append(line + "\n");
				line = urBufRead.readLine();
			}
		    System.out.println("In sb.toString(): " + sb.toString());
			urBufRead.close();
			jTextField_PV_LT.setText(sb.toString().replace(".", ".\n"));
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			System.out.println(e.getMessage());
		}

		try {
			FileReader input = new FileReader(upd_file);
			BufferedReader urBufRead = new BufferedReader(input);
			String line = urBufRead.readLine();
			StringBuffer sb = new StringBuffer();
			while (line != null) {
				sb.append(line + "\n");
				line = urBufRead.readLine();
			}
			urBufRead.close();
			jTextField_UA_LT.setText(sb.toString().replace(".", ".\n"));
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			System.out.println(e.getMessage());
		}
	}

	private void jButton_PrevActionPerformed(java.awt.event.ActionEvent evt) {
	    
		ltFinalStateNo--;
		
		String out_img_file = constants.PROPUB_DOT_OUT + constants.ENV_SEPARATOR + "pg_"
				+ version + "." + ltFinalStateNo + ".gif";

		String ppv_file = constants.PROPUB_OUT + constants.ENV_SEPARATOR + "ppv_"
				+ version + "." + ltFinalStateNo + ".dlv";
		String upd_file = constants.PROPUB_OUT + constants.ENV_SEPARATOR + "upd_"
				+ version + "." + ltFinalStateNo + ".dlv";

		jButton_Show_OG_LT.setIcon(new ImageIcon(out_img_file));
		
		System.out.println("ppv_file: " + ppv_file);
		System.out.println("upd_file: " + upd_file);
				
		try {
			FileReader input = new FileReader(ppv_file);
			BufferedReader urBufRead = new BufferedReader(input);
			String line = urBufRead.readLine();
			
			StringBuffer sb = new StringBuffer();
			while (line != null) {
				sb.append(line + "\n");
				line = urBufRead.readLine();
			}
			urBufRead.close();
			jTextField_PV_LT.setText(sb.toString());			
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			System.out.println(e.getMessage());
		}

		try {
			FileReader input = new FileReader(upd_file);
			BufferedReader urBufRead = new BufferedReader(input);
			String line = urBufRead.readLine();
			StringBuffer sb = new StringBuffer();
			while (line != null) {
				sb.append(line + "\n");
				line = urBufRead.readLine();
			}
			urBufRead.close();
			jTextField_UA_LT.setText(sb.toString());
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			System.out.println(e.getMessage());
		}

	}

	private void jButton_IN_LTActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	int ltFinalStateNo;
	private void jButton_SL_LTActionPerformed(java.awt.event.ActionEvent evt) {
			
		    ltFinalStateNo = finalStateNo;
			
		    String in_img_file = constants.PROPUB_DOT_OUT + constants.ENV_SEPARATOR + "pg_"
					+ version + "." + 0 + ".gif";
			String out_img_file = constants.PROPUB_DOT_OUT + constants.ENV_SEPARATOR + "pg_"
					+ version + "." + finalStateNo + ".gif";

			String ur_file = constants.PROPUB_OUT + constants.ENV_SEPARATOR + "ur_"
					+ version + "." + 0 + ".dlv";
			String ppv_file = constants.PROPUB_OUT + constants.ENV_SEPARATOR + "ppv_"
					+ version + "." + finalStateNo + ".dlv";
			String upd_file = constants.PROPUB_OUT + constants.ENV_SEPARATOR + "upd_"
					+ version + "." + finalStateNo + ".dlv";

			jButton_SHow_IG_LT.setIcon(new ImageIcon(in_img_file));
			jButton_Show_OG_LT.setIcon(new ImageIcon(out_img_file));
			
			System.out.println("ppv_file: " + ppv_file);
			System.out.println("upd_file: " + upd_file);
			
			jTextField_UR_LT.setText(jTextArea_UR.getText());
			
			try {
				FileReader input = new FileReader(ppv_file);
				BufferedReader urBufRead = new BufferedReader(input);
				String line = urBufRead.readLine();
				StringBuffer sb = new StringBuffer();
				while (line != null) {
					sb.append(line + "\n");
					line = urBufRead.readLine();
				}
			    System.out.println("In sb.toString(): " + sb.toString());
				urBufRead.close();
				jTextField_PV_LT.setText(sb.toString().replace(".", ".\n"));
			} catch (Exception e) {
				System.out.println(e.getStackTrace());
				System.out.println(e.getMessage());
			}

			try {
				FileReader input = new FileReader(upd_file);
				BufferedReader urBufRead = new BufferedReader(input);
				String line = urBufRead.readLine();
				StringBuffer sb = new StringBuffer();
				while (line != null) {
					sb.append(line + "\n");
					line = urBufRead.readLine();
				}
				urBufRead.close();
				jTextField_UA_LT.setText(sb.toString().replace(".", ".\n"));
			} catch (Exception e) {
				System.out.println(e.getStackTrace());
				System.out.println(e.getMessage());
			}
					
	}

	void jButton_ShowActionPerformed(java.awt.event.ActionEvent evt) {

		String outImgNo = jTree1.getSelectionPath().toString().split(":")[jTree1
				.getSelectionPath().toString().split(":").length - 1].replace(
				"]", "");

		String outputImg = constants.PROPUB_DOT_OUT + constants.ENV_SEPARATOR
				+ "pg_" + outImgNo + ".gif";

		String inImgNo = jTree1.getSelectionPath().toString().split(":")[jTree1
				.getSelectionPath().toString().split(":").length - 2]
				.split(",")[0];

		String inPutImg = constants.PROPUB_DOT_OUT + constants.ENV_SEPARATOR
				+ "pg_" + inImgNo + ".gif";

		jButton_Show_IG.setIcon(new ImageIcon(inPutImg));
		jButton_Show_OG_SL.setIcon(new ImageIcon(outputImg));

		//set the UR
		String inPutUR = constants.PROPUB_OUT + constants.ENV_SEPARATOR + "ur_"
				+ htVerMap.get(outImgNo) + ".dlv";
		try {
			FileReader urInput = new FileReader(inPutUR);
			BufferedReader urBufRead = new BufferedReader(urInput);
			String line = urBufRead.readLine();
			StringBuffer sb = new StringBuffer();
			while (line != null) {
				sb.append(line + "\n");
				line = urBufRead.readLine();
			}
			urBufRead.close();
			jTextArea_UR.setText(sb.toString());
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			System.out.println(e.getMessage());
		}
	}

	private void jButton_Set_GraphActionPerformed(java.awt.event.ActionEvent evt) {
		System.out.println("Selected Query: " + jTree1.getSelectionPath());

		String imgNo = jTree1.getSelectionPath().toString().split(":")[jTree1
				.getSelectionPath().toString().split(":").length - 1].replace(
				"]", "");

		System.out.println("Selected Query: " + imgNo);

		String inputPGIMGFileName = constants.PROPUB_DOT_OUT
				+ constants.ENV_SEPARATOR + "pg_" + imgNo + ".gif";

		System.out.println("Selected Query: " + inputPGIMGFileName);

		jButton_Show_IG.setIcon(new ImageIcon(inputPGIMGFileName));
		jButton_Show_OG_SL.setIcon(null);
		jTextArea_UR.setText("");

		base_version = imgNo;

		//also copy the PG and UR files into the exe dir.
		try {
			BufferedWriter pgfile = new BufferedWriter(new FileWriter(
					constants.PROPUB_EXE + constants.ENV_SEPARATOR + "pg.dlv"));

			FileReader pgInput = new FileReader(constants.PROPUB_OUT
					+ constants.ENV_SEPARATOR + "pg_" + imgNo + ".dlv");
			
			System.out.println("pgInput: " + constants.PROPUB_OUT
					+ constants.ENV_SEPARATOR + "pg_" + imgNo + ".dlv");
			
			BufferedReader pgBufRead = new BufferedReader(pgInput);
			String line = pgBufRead.readLine();
			while (line != null) {
				System.out.println("line before format: " + line);
				pgfile.append(line.replace(","+finalStateNo+")", ")") + "\n");
				System.out.println("line after format: " + line.replace(","+finalStateNo+")", ")"));
				line = pgBufRead.readLine();
			}
			pgfile.close();
			pgBufRead.close();
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			System.out.println(e.getMessage());
		}

		try {
			BufferedWriter urfile = new BufferedWriter(new FileWriter(
					constants.PROPUB_EXE + constants.ENV_SEPARATOR + "ur.dlv"));

			FileReader urInput = new FileReader(constants.PROPUB_OUT
					+ constants.ENV_SEPARATOR + "ur_" + htVerMap.get(imgNo) + ".dlv");
			BufferedReader urBufRead = new BufferedReader(urInput);
			String line = urBufRead.readLine();
			while (line != null) {
				urfile.append(line + "\n");
				line = urBufRead.readLine();
			}
			urfile.close();
			urBufRead.close();
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			System.out.println(e.getMessage());
		}

	}

	private void getSetupInfo() {
		try {

			FileReader existingConfig = new FileReader(
					"/Users/scdey/mySpace/propub/exe/config.txt");
			BufferedReader bufRead = new BufferedReader(existingConfig);
			String line = bufRead.readLine();
			while (line != null) {
				if (line.contains("PROPUB_ROOT")) {
					jTextField_root.setText(line.split("=")[1]);
				} else if (line.contains("ENV_SEPARATOR")) {
					jTextField_separator.setText(line.split("=")[1]);
				} else if (line.contains("DOT_PATH")) {
					jTextField_dot_path.setText(line.split("=")[1]);
				} else if (line.contains("DLV_PATH")) {
					jTextField_dlv_path.setText(line.split("=")[1]);
				}
				line = bufRead.readLine();
			}
			bufRead.close();
			setSetupInfo();

		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			System.out.println(e.getMessage());
		}
	}

	private void setSetupInfo() {
		constants.PROPUB_ROOT = jTextField_root.getText();
		constants.ENV_SEPARATOR = jTextField_separator.getText();
		constants.DOT_PATH = jTextField_dot_path.getText();
		//constants.DLV_PATH = jTextField_dlv_path.getText();

		constants.setPaths();
	}

	private void jButton_Setup_SaveActionPerformed(
			java.awt.event.ActionEvent evt) {

		setSetupInfo();

		try {

			BufferedWriter configfile = new BufferedWriter(new FileWriter(
					constants.PROPUB_EXE + constants.ENV_SEPARATOR
							+ "config.txt"));

			configfile.append("PROPUB_ROOT=" + constants.PROPUB_ROOT + "\n");
			configfile
					.append("ENV_SEPARATOR=" + constants.ENV_SEPARATOR + "\n");
			configfile.append("DOT_PATH=" + constants.DOT_PATH + "\n");
			//configfile.append("DLV_PATH=" + constants.DLV_PATH + "\n");

			configfile.close();

		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			System.out.println(e.getMessage());
		}
	}

	private void jButton_Calc_OGActionPerformed(java.awt.event.ActionEvent evt) {

		version++;

		System.out.println("Start prepareINPUT... ");
		prepareINPUT();
		System.out.println("Start exeDLV.. ");
		exeDLV();
		System.out.println("Start prepareOUTPUT_SW...");
		prepareOUTPUT_SW();
		System.out.println("Start prepareOUTPUT_IN...");
		prepareOUTPUT_IN();

		System.out.println("Check the HT data: " + ht.toString());

	}

	public void prepareINPUT() {
		try {
			BufferedWriter pgfile = new BufferedWriter(new FileWriter(
					constants.PROPUB_EXE + constants.ENV_SEPARATOR + "pg.dlv"));
			FileReader pgInput = new FileReader(constants.PROPUB_OUT
					+ constants.ENV_SEPARATOR + "pg_" + base_version + ".dlv");
			BufferedReader pgBufRead = new BufferedReader(pgInput);
			String line = pgBufRead.readLine();
			while (line != null) {
				//pgfile.append(line + "\n");
				System.out.println("line.replace: "+line);
				pgfile.append(line.replace(","+finalStateNo+")", ")") + "\n");
				System.out.println("line.replace: "+line.replace(","+finalStateNo+")", ")"));
				line = pgBufRead.readLine();
			}
			pgfile.close();
			pgBufRead.close();
		} catch (Exception e) {
			System.out.println(e.getStackTrace());
			System.out.println(e.getMessage());
		}
		try {
			
			BufferedWriter outfile = new BufferedWriter(new FileWriter(
					constants.PROPUB_OUT + constants.ENV_SEPARATOR + "ur_"
							+ version + "."+ 0 + ".dlv"));
			outfile.append(jTextArea_UR.getText());
			outfile.close();

			BufferedWriter urfile = new BufferedWriter(new FileWriter(
					constants.PROPUB_EXE + constants.ENV_SEPARATOR + "ur.dlv"));
			urfile.append(jTextArea_UR.getText());
			urfile.close();

		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
	}

	public void exeDLV() {
		try {
			Runtime rt = Runtime.getRuntime();
			/*String[] args = {
					"cmd.exe",
					"/c",
					constants.PROPUB_EXE + constants.ENV_SEPARATOR
							+ "exe_dlv.bat" };
			Process p = rt.exec(args);*/
			Process p = rt.exec(jTextField_dlv_path.getText());
			p.waitFor();

		} catch (Exception ioe) {
			System.err.println("Error in calling external command");
			ioe.printStackTrace();
		}
		try {
			Runtime rt = Runtime.getRuntime();
			Process p = rt.exec("/Users/scdey/mySpace/propub/exe/propub_in.sh");
			p.waitFor();

		} catch (Exception ioe) {
			System.err.println("Error in calling external command");
			ioe.printStackTrace();
		}		
	}

	@SuppressWarnings("unchecked")
	public void prepareOUTPUT_SW() {
		try {

			BufferedWriter outfile_pg;
			BufferedWriter outfile_upd;
			BufferedWriter outfile_ppv;
			
			String outfile_pg_img_file = null;
			
			//RunToDot rdt = new RunToDot();
			DOTDriver dot = new DOTDriver(constants);
			
			FileReader input = new FileReader(constants.PROPUB_EXE
					+ constants.ENV_SEPARATOR + "out.txt");
			BufferedReader bufRead = new BufferedReader(input);
			String line = bufRead.readLine();
			while (line != null) {
				
				int finalStartPos = line.indexOf("final(", 0);
				int finalEndPos = line.indexOf(")", finalStartPos);
				finalStateNo = new Integer(line.substring(finalStartPos+6, finalEndPos)).intValue();
				System.out.println("finalStateNo: " + finalStateNo);
				
				System.out.println("line: " + line);
				String s = line.replaceAll("\\),", "\\).").replace("}", ".");
				System.out.println("s: " + s);
				String[] s1 = s.split(" ");
				System.out.println(s1.length);
								
				for (int j = 0; j <= finalStateNo; j++) {

					System.out.println("Value of J: " + j);
					
					String outfile_pg_file = constants.PROPUB_OUT + constants.ENV_SEPARATOR + "pg_"
							+ version + "." + j + ".dlv";
					String outfile_upd_file = constants.PROPUB_OUT + constants.ENV_SEPARATOR + "upd_"
							+ version + "." + j + ".dlv";
					String outfile_ppv_file = constants.PROPUB_OUT + constants.ENV_SEPARATOR + "ppv_"
							+ version + "." + j + ".dlv";
					
					String outfile_pg_dot_file = constants.PROPUB_DOT_OUT + constants.ENV_SEPARATOR + "pg_"
							+ version + "." + j + ".txt";
				    outfile_pg_img_file = constants.PROPUB_DOT_OUT + constants.ENV_SEPARATOR + "pg_"
							+ version + "." + j + ".gif";
					
					outfile_pg = new BufferedWriter(new FileWriter(outfile_pg_file));
					outfile_upd = new BufferedWriter(new FileWriter(outfile_upd_file));
				    outfile_ppv = new BufferedWriter(new FileWriter(outfile_ppv_file));
					
					for (int i = 0; i < s1.length; i++) {
						
						if (s1[i].contains("," + j + ")")) {
							
							if (s1[i].contains("l_used")) {
								outfile_pg.append(s1[i].replace("l_", "") + "\n");
							} else if (s1[i].contains("l_gen_by")) {
								outfile_pg.append(s1[i].replace("l_", "") + "\n");
							} else if (s1[i].contains("l_data")) {
								outfile_pg.append(s1[i].replace("l_", "") + "\n");
							} else if (s1[i].contains("l_actor")) {
								outfile_pg.append(s1[i].replace("l_", "") + "\n");
							}	

							if (s1[i].contains("nc")) {
								outfile_ppv.append(s1[i] + "\n");
							} else if (s1[i].contains("nfs")) {
								outfile_ppv.append(s1[i] + "\n");
							} else if (s1[i].contains("wc")) {
								outfile_ppv.append(s1[i] + "\n");
							} 

							if (s1[i].contains("del_dep")) {
								outfile_upd.append(s1[i] + "\n");
							} else if (s1[i].contains("ins_dep")) {
								outfile_upd.append(s1[i] + "\n");
							} 	
						}						
					}
					
					outfile_pg.close();
					outfile_upd.close();
					outfile_ppv.close();

					DLVToDot rdt = new DLVToDot();
										
					rdt.readInFile(outfile_pg_file,outfile_ppv_file);
					rdt.prepareDOTFile(outfile_pg_dot_file);
					dot.write(dot.getGraph(new File(outfile_pg_dot_file)), outfile_pg_img_file);
				}
				line = bufRead.readLine();
			}			
			bufRead.close();

			ImageIcon icon = new ImageIcon(outfile_pg_img_file);
			Image img = icon.getImage() ;  
			Image newimg = img.getScaledInstance( jButton_Show_OG_SL.getWidth()-30, jButton_Show_OG_SL.getHeight()-40,  java.awt.Image.SCALE_SMOOTH ) ;  
			icon = new ImageIcon( newimg );
			jButton_Show_OG_SL.setIcon(icon);

			if (ht.containsKey(base_version)) {
				System.out.println("in if");
				ht_ele = (ArrayList<String>) ht.get(base_version);
				ht_ele.add(version+"."+finalStateNo);
				ht.put(base_version, ht_ele);
			} else {
				System.out.println("in else");
				ArrayList<String> ht_ele1 = new ArrayList<String>();
				ht_ele1.add(version+"."+finalStateNo);
				ht.put(base_version, ht_ele1);
			}
			htVerMap.put(version + "." + finalStateNo, version + "." + 0);
			System.out.println("htVerMap: " + htVerMap.toString());

			jTree1 = bt.getTree(ht, null);		// TODO: Note, this gets the
												// code to compile, but this
												// would clearly not run
												// successfully
			jScrollPane1.setViewportView(jTree1);

		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
	}

	@SuppressWarnings("unchecked")
	public void prepareOUTPUT_IN() {
		try {
			
			String outfile_pg_dot_file = constants.PROPUB_DOT_OUT + constants.ENV_SEPARATOR + "pg_in_"
					+ version + ".txt";
			String outfile_pg_img_file = constants.PROPUB_DOT_OUT + constants.ENV_SEPARATOR + "pg_in_"
					+ version + ".gif";
		    
			//RunToDot rdt = new RunToDot();
			DOTDriver dot = new DOTDriver(constants);
			
			String outfile_pg_file = constants.PROPUB_OUT + constants.ENV_SEPARATOR + "pg_in_"
					+ version + ".dlv";
			BufferedWriter outfile_pg = new BufferedWriter(new FileWriter(outfile_pg_file));
		
			FileReader input = new FileReader(constants.PROPUB_EXE
					+ constants.ENV_SEPARATOR + "out_in.txt");
			BufferedReader bufRead = new BufferedReader(input);
			String line = bufRead.readLine();
			while (line != null) {
				
				String s = line.replaceAll("\\),", "\\).").replace("}", ".");
				String[] s1 = s.split(" ");
					
				for (int i = 0; i < s1.length; i++) {					
					if (s1[i].contains("l_used")) {
						outfile_pg.append(s1[i].replace("l_", "") + "\n");
					} else if (s1[i].contains("l_gen_by")) {
						outfile_pg.append(s1[i].replace("l_", "") + "\n");
					} else if (s1[i].contains("l_data")) {
						outfile_pg.append(s1[i].replace("l_", "") + "\n");
					} else if (s1[i].contains("l_actor")) {
						outfile_pg.append(s1[i].replace("l_", "") + "\n");
					}	
				}					
				line = bufRead.readLine();
			}			
			outfile_pg.close();
			bufRead.close();

			DLVToDot rdt = new DLVToDot();
			
			rdt.readInFile(outfile_pg_file);
			rdt.prepareDOTFile(outfile_pg_dot_file);
			dot.write(dot.getGraph(new File(outfile_pg_dot_file)), outfile_pg_img_file);

			ImageIcon icon = new ImageIcon(outfile_pg_img_file);
			Image img = icon.getImage() ;  
			Image newimg = img.getScaledInstance( jButton_Show_OG_IN.getWidth()-30, jButton_Show_OG_IN.getHeight()-40,  java.awt.Image.SCALE_SMOOTH ) ;  
			icon = new ImageIcon( newimg );
			jButton_Show_OG_IN.setIcon(icon);

		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}
		
	}
	
	private void jButton_IGActionPerformed(java.awt.event.ActionEvent evt) {

		DLVToDot rdt = new DLVToDot();
		rdt.readInFile(jTextFieldInputFilePath.getText());

		try {
			BufferedWriter outfile = new BufferedWriter(new FileWriter(
					constants.PROPUB_OUT + constants.ENV_SEPARATOR + "pg_"
							+ version + "." + 0 + ".dlv"));

			FileReader input = new FileReader(jTextFieldInputFilePath.getText());
			BufferedReader bufRead = new BufferedReader(input);
			String line;
			line = bufRead.readLine();
			while (line != null) {
				outfile.append(line + "\n");
				line = bufRead.readLine();
			}
			outfile.close();
			bufRead.close();

			BufferedWriter outURfile = new BufferedWriter(new FileWriter(
					constants.PROPUB_OUT + constants.ENV_SEPARATOR + "ur_"
							+ version + "." + 0 + ".dlv"));
			outURfile.append("\n");
			outURfile.close();

			jTree1 = bt.getRoot();
			jScrollPane1.setViewportView(jTree1);

			System.out.println("new ht");
			//base_version = version + "." + finalStateNo;
			ht_ele = new ArrayList<String>();
			ht_ele.add(version+"."+finalStateNo);
			ht.put(base_version, ht_ele);
			System.out.println(ht.toString());

		} catch (Exception e) {
			System.out.println(e.getStackTrace());
		}

		String inputPGDOTFileName = constants.PROPUB_DOT_OUT
				+ constants.ENV_SEPARATOR + "pg_" + version + "." + finalStateNo + ".txt";
		String inputPGIMGFileName = constants.PROPUB_DOT_OUT
				+ constants.ENV_SEPARATOR + "pg_" + version + "." + finalStateNo + ".gif";
		rdt.prepareDOTFile(inputPGDOTFileName);
		DOTDriver dot = new DOTDriver(constants);
		dot.write(dot.getGraph(new File(inputPGDOTFileName)),
				inputPGIMGFileName);

		System.out.println("Now trying to set the image");
		jButton_Show_IG.setIcon(new ImageIcon(inputPGIMGFileName));
		//jButton_Show_IG.setLabel("");
		//jButton_Show_IG.setVisible(true);
		System.out.println("Done!");

	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new ProPub().setVisible(true);
			}
		});
	}

	//int version = 0;
	//int base_version = 0;
	//int finalStateNo = 0;
	
	int version = 0;
	String base_version = new String("0.0");
	int finalStateNo = 0;
	
	Constants constants = new Constants();
	BuildTree bt = new BuildTree();
	Hashtable ht = new Hashtable();
	Hashtable<String, String> htVerMap = new Hashtable<String, String>();
	ArrayList<String> ht_ele;
	DefaultMutableTreeNode root;

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JButton jButton_Calc_OG;
	private javax.swing.JButton jButton_IG;
	private javax.swing.JButton jButton_IN_LT;
	private javax.swing.JButton jButton_Next;
	private javax.swing.JButton jButton_Prev;
	private javax.swing.JButton jButton_SHow_IG_LT;
	private javax.swing.JButton jButton_SL_LT;
	private javax.swing.JButton jButton_Set_Graph;
	private javax.swing.JButton jButton_Setup_Save;
	private javax.swing.JButton jButton_Show;
	private javax.swing.JButton jButton_Show_IG;
	private javax.swing.JButton jButton_Show_OG_IN;
	private javax.swing.JButton jButton_Show_OG_LT;
	private javax.swing.JButton jButton_Show_OG_SL;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel_FilePath;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanelSelector;
	private javax.swing.JPanel jPanel_IG;
	private javax.swing.JPanel jPanel_IG_LT;
	private javax.swing.JPanel jPanel_LT;
	private javax.swing.JPanel jPanel_OG_IN;
	private javax.swing.JPanel jPanel_OG_LT;
	private javax.swing.JPanel jPanel_OG_SL;
	private javax.swing.JPanel jPanel_Opn;
	private javax.swing.JPanel jPanel_PV_LT;
	private javax.swing.JPanel jPanel_Setup;
	private javax.swing.JPanel jPanel_UA_LT;
	private javax.swing.JPanel jPanel_UR;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane_IN_LT;
	private javax.swing.JScrollPane jScrollPane_SL_LT;
	private javax.swing.JScrollPane jScrollPane_UR;
	private javax.swing.JTabbedPane jTabbedPane_Propub;
	private javax.swing.JTextArea jTextArea_UR;
	private javax.swing.JTextField jTextFieldInputFilePath;
	private javax.swing.JTextField jTextField_PV_LT;
	private javax.swing.JTextField jTextField_UA_LT;
	private javax.swing.JTextField jTextField_UR_LT;
	private javax.swing.JTextField jTextField_dlv_path;
	private javax.swing.JTextField jTextField_dot_path;
	private javax.swing.JTextField jTextField_root;
	private javax.swing.JTextField jTextField_separator;
	private javax.swing.JTree jTree1;
	// End of variables declaration//GEN-END:variables

}
