package propub;

import types.IconType;
import types.PayloadTreeNode;

import javax.swing.*;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.*;
import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.List;

public class GlobalContext {

    private GlobalContext() {

    }

	public RunContext initialLoad(File loadFile) {
        System.out.println("Initial load of file!");
		return this.addTopLevel(loadFile);
	}

	private RunContext addTopLevel(File file) {
		RunContext rc = new RunContext(file);
        rc.setFileLoadContext(true);
		rc.setIconType(IconType.FILE_LOAD);
		topLevelItems.add(rc);
        return rc;
	}

	public void addChild(RunContext rcParent, RunContext rcChild) {
		if (!treeData.containsKey(rcParent)) {
			treeData.put(rcParent, new ArrayList<RunContext>());
		}
		treeData.get(rcParent).add(rcChild);
	}

    public void addChildOfCurrentContext(RunContext runContext) {
        System.out.println("Adding child of current context: child UR is: " + runContext.getUserRequestFile().getAbsolutePath());
        addChild(getCurrentRunContext(), runContext);
    }

    public void setCurrentContext(RunContext currentRunContext) {
        this.currentRunContext = currentRunContext;
        System.out.println("Setting context to: " + GlobalContext.getInstance().getCurrentRunContext().getInstanceId());
    }

    public RunContext getCurrentRunContext() {
        return currentRunContext;
    }

	public RunContext lookupBySessionId(Integer sessionId) {
		RunContext rc = topLevelItems.get(sessionId);
		return rc;
	}

	public List<RunContext> childrenBySessionId(Integer sessionId) {
		RunContext rc = lookupBySessionId(sessionId);
		return treeData.get(rc);
	}

	public RunContext nthChild(RunContext rc, Integer id) {
		return children(rc).get(id);
	}

	public List<RunContext> children(RunContext rc) {
		return treeData.get(rc);
	}


    public static GlobalContext getInstance() {
        if (instance == null) {
            instance = new GlobalContext();
        }
        return instance;
    }

    private static GlobalContext instance = null;
	private List<RunContext> topLevelItems = new ArrayList<RunContext>();
    private Map<RunContext, PayloadTreeNode> nodeCache = new HashMap<RunContext, PayloadTreeNode>();
    private Map<RunContext, List<Integer>> positionCache = new HashMap<RunContext, List<Integer>>();
	private Map<RunContext, List<RunContext>> treeData = new HashMap<RunContext, List<RunContext>>();
    private RunContext currentRunContext = null;

	private class MyTreeCellRenderer extends DefaultTreeCellRenderer {
		@Override
		public Component getTreeCellRendererComponent(JTree jTree, Object o, boolean b, boolean b1, boolean b2, int i, boolean b3) {
			JLabel label = (JLabel) super.getTreeCellRendererComponent(jTree, o, b, b1, b2, i, b3);
			try {
				PayloadTreeNode ptn = (PayloadTreeNode) o;
				RunContext runContext = (RunContext) ptn.getPayload();
				String urlString = null;
				if (runContext.getIconType() == IconType.SWALLOW) {
					urlString = "toolbarButtonGraphics/general/Remove24.gif";
				}
				else if (runContext.getIconType() == IconType.INVENT) {
					urlString = "toolbarButtonGraphics/general/Add24.gif";
				}
				else if (runContext.getIconType() == IconType.FILE_LOAD) {
					urlString = "toolbarButtonGraphics/general/Properties24.gif";
				}
				else {
					urlString = "toolbarButtonGraphics/general/Help24.gif";
				}
				URL url = GlobalContext.class.getClassLoader().getResource(urlString);
				Icon icon = new ImageIcon(url);
				label.setIcon(icon);
				return label;
			}
			catch(ClassCastException cce) {
				String urlString = "toolbarButtonGraphics/general/Copy24.gif";
				URL url = GlobalContext.class.getClassLoader().getResource(urlString);
				Icon icon = new ImageIcon(url);
				label.setIcon(icon);
				return label;
			}
		}
	}

    public JTree buildTree() {
        System.out.println("buildTree()");
        System.out.println("topLevelItems: " + topLevelItems);
        JTree tree = new JTree();

		tree.setCellRenderer(new MyTreeCellRenderer());

        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) model.getRoot();
        rootNode.setUserObject("Root!");

        // Freeze root children myself and remove from model because the handy-
        // dandy MutableTreeNode.removeAllChildren() doesn't actually update
        // the model, and is therefore quite useless.
        {
            List<MutableTreeNode> rootChildren = new ArrayList<MutableTreeNode>();
            for (int i = 0; i < rootNode.getChildCount(); i++) {
                rootChildren.add((MutableTreeNode) rootNode.getChildAt(i));
            }

            for (MutableTreeNode rootChild : rootChildren) {
                model.removeNodeFromParent(rootChild);
            }
        }

        int i = 0;
        for (RunContext rc : topLevelItems) {
            PayloadTreeNode ptn = new PayloadTreeNode("Query " + i++, rc);
            nodeCache.put(rc, ptn);
            positionCache.put(rc, Arrays.asList(i++));
            System.out.println("Inserting " + rc + " at root index: " + rootNode.getChildCount());
            model.insertNodeInto(ptn, rootNode, rootNode.getChildCount());
        }

        Queue<RunContext> queue = new LinkedList<RunContext>();
        queue.addAll(topLevelItems);
        while (!queue.isEmpty()) {
            RunContext rc = queue.poll();
            System.out.println("Popped " + rc);
            // Add rc's children.
            if (!treeData.containsKey(rc)) {
                System.out.println("No children, skipping");
                // Nothing to do.
            }
            else {
                List<RunContext> children = treeData.get(rc);
                System.out.println("Got " + children.size() + " children: " + children);
                int j = 0;
                for (RunContext child : children) {
                    System.out.println("processing child: " + child);
                    List<Integer> parentPosition = positionCache.get(rc);
                    List<Integer> childPosition = new ArrayList<Integer>(parentPosition);
                    childPosition.add(j++);
                    positionCache.put(child, childPosition);

                    String name = _createName(childPosition);

                    PayloadTreeNode ptn = new PayloadTreeNode(name, child);
                    PayloadTreeNode parentContextNode = nodeCache.get(rc);
                    nodeCache.put(child, ptn);

                    model.insertNodeInto(ptn, parentContextNode, parentContextNode.getChildCount());
                }
                queue.addAll(children);
            }


        }

        expandAll(tree);

        return tree;
//        JTree tree = new JTree(new TreeModel() {
//            @Override
//            public Object getRoot() {
//                if (root == null) {
//                    root = new DefaultMutableTreeNode("Root");
//                }
//                return root;
//            }
//
//            TreeNode root = null
//            int index = 0;
//
//            @Override
//            public Object getChild(Object o, int i) {
//                if (o == root) {
//                    if (!nodeCache.containsKey(o)) {
//                        nodeCache.put(o, new HashMap<Integer, PayloadTreeNode>());
//                    }
//                    if (!nodeCache.get(o).containsKey(i)) {
//                        nodeCache.get(o).put(i, new PayloadTreeNode("Node " + (++index),               )))))))))))))
//                    }
//                    if (!nodeCache.containsKey(o) || !nodeCache.get(o).containsKey(i)) {
//                        nodeCache.put(i, new PayloadTreeNode("justanode", topLevelItems.get(i)));
//                    }
//                    return nodeCache.get(i);
//                }
//                else {
//                    return null;
//                }
//            }
//
//            @Override
//            public int getChildCount(Object o) {
//                if (o == root) {
//                    return topLevelItems.size();
//                }
//                else {
//                    return 0;
//                }
//            }
//
//            @Override
//            public boolean isLeaf(Object o) {
//                return o != root;
//            }
//
//            @Override
//            public void valueForPathChanged(TreePath treePath, Object o) {
//                //To change body of implemented methods use File | Settings | File Templates.
//            }
//
//            @Override
//            public int getIndexOfChild(Object o, Object o1) {
//                if (o == root) {
//                    return topLevelItems.indexOf(o1);
//                }
//                else {
//                    return -1;
//                }
//            }
//
//            @Override
//            public void addTreeModelListener(TreeModelListener treeModelListener) {
//                listeners.add(treeModelListener);
//            }
//
//            @Override
//            public void removeTreeModelListener(TreeModelListener treeModelListener) {
//                listeners.remove(treeModelListener);
//            }
//
//            List<TreeModelListener> listeners = new ArrayList<TreeModelListener>();
//        });
//        ((MutableTreeNode) tree.getModel().getRoot()).insertNo;
//        return tree;
    }

    private String _createName(List<Integer> position) {
        return "Query " + _join(position, ".");
    }

    private String _join(List<?> list, String delimiter) {
        if (list.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(list.get(0).toString());
        for (int i = 1; i < list.size(); i++) {
            sb.append(delimiter).append(list.get(i).toString());
        }
        return sb.toString();
    }

    private static void expandAll(JTree tree) {
        int row = 0;
        while (row < tree.getRowCount()) {
            tree.expandRow(row++);
        }
    }

}
