package propub;

import types.IconType;
import types.PayloadTreeNode;

import javax.swing.*;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.*;

import env.EnvInfo;

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
    	RunContext rc = RunContext.forCustomization(file);
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
        System.out.println("Adding child of current context: " + runContext.getInstanceId());
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
				RunContext currentRunContext = GlobalContext.getInstance().getCurrentRunContext();
				PayloadTreeNode ptn = (PayloadTreeNode) o;
				RunContext runContext = (RunContext) ptn.getPayload();
				String urlString = null;
				if (currentRunContext.equals(runContext)) {
					urlString = "toolbarButtonGraphics/general/Stop24.gif";
				}
				else if (runContext.getIconType() == IconType.SWALLOW) {
					urlString = "toolbarButtonGraphics/general/Remove24.gif";
				}
				else if (runContext.getIconType() == IconType.INVENT) {
					urlString = "toolbarButtonGraphics/general/Add24.gif";
				}
				else if (runContext.getIconType() == IconType.FILE_LOAD) {
					urlString = "toolbarButtonGraphics/general/Properties24.gif";
				}
                else if (runContext.getIconType() == IconType.RPQ) {
                    urlString = "toolbarButtonGraphics/general/Find24.gif";
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
        JTree tree = new JTree();

		tree.setCellRenderer(new MyTreeCellRenderer());

        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) model.getRoot();
        rootNode.setUserObject("");
		//tree.expandRow(0);
		//tree.setRootVisible(false);
		//tree.setShowsRootHandles(true);

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
            int thisNode = i;
            i++;
            PayloadTreeNode ptn = new PayloadTreeNode("Query " + thisNode, rc);
            nodeCache.put(rc, ptn);
            positionCache.put(rc, Arrays.asList(thisNode));
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
