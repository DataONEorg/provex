package propub;

import types.PayloadTreeNode;

import javax.swing.*;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.*;
import java.io.File;
import java.util.*;

public class GlobalContext {

    private GlobalContext() {

    }

	public RunContext initialLoad(File loadFile) {
        System.out.println("Initial load of file!");
		return this.addTopLevel(loadFile);
	}

	private RunContext addTopLevel(File file) {
		RunContext rc = new RunContext(file);
		topLevelItems.add(rc);
        return rc;
	}

	public void addChild(RunContext rcParent, RunContext rcChild) {
		if (!tree.containsKey(rcParent)) {
			tree.put(rcParent, new ArrayList<RunContext>());
		}
		tree.get(rcParent).add(rcChild);
	}

    public void setCurrentContext(RunContext currentRunContext) {
        System.out.println("Setting current context!");
        this.currentRunContext = currentRunContext;
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
		return tree.get(rc);
	}

	public RunContext nthChild(RunContext rc, Integer id) {
		return children(rc).get(id);
	}

	public List<RunContext> children(RunContext rc) {
		return tree.get(rc);
	}


    public static GlobalContext getInstance() {
        if (instance == null) {
            instance = new GlobalContext();
        }
        return instance;
    }

    private static GlobalContext instance = null;
	private List<RunContext> topLevelItems = new ArrayList<RunContext>();
    private Map<Object, Map<Integer, PayloadTreeNode>> nodeCache = new HashMap<Object, Map<Integer, PayloadTreeNode>>();
	private Map<RunContext, List<RunContext>> tree = new HashMap<RunContext, List<RunContext>>();
    private RunContext currentRunContext = null;

    public JTree buildTree() {
        JTree tree = new JTree();
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) model.getRoot();
        rootNode.setUserObject("Root!");

        // Freeze root children myself and remove from model because the handy-
        // dandy MutableTreeNode.removeAllChildren() doesn't actually update
        // the model, and is therefore quite useless.
        {
            List<MutableTreeNode> rootChildren = new ArrayList<MutableTreeNode>();
            System.out.println("Root node has " + rootChildren.size() + " children.");
            for (int i = 0; i < rootNode.getChildCount(); i++) {
                rootChildren.add((MutableTreeNode) rootNode.getChildAt(i));
                System.out.println("Added: " + rootNode.getChildAt(i) + " for removal");
            }

            for (MutableTreeNode rootChild : rootChildren) {
                model.removeNodeFromParent(rootChild);
                System.out.println("Removed: " + rootChild);
            }
        }

        System.out.println("There are " + topLevelItems.size() + " TLIs");
        int i = 1;
        for (RunContext rc : topLevelItems) {
            PayloadTreeNode ptn = new PayloadTreeNode("Query " + i++, rc);
            System.out.println("Inserting at root index: " + rootNode.getChildCount());
            model.insertNodeInto(ptn, rootNode, rootNode.getChildCount());
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

    private static void expandAll(JTree tree) {
        int row = 0;
        while (row < tree.getRowCount()) {
            tree.expandRow(row++);
        }
    }
}
