package propub;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GlobalContext {

    private GlobalContext() {

    }

    public void test() {}

	public RunContext initialLoad(File loadFile) {
		return this.addTopLevel(loadFile);
	}

	private RunContext addTopLevel(File file) {
		RunContext rc = new RunContext(file);
		topLevelItems.add(rc);
        // TODO: Fix!
        return null;
	}

	public RunContext addChildWithSameProgramGraph(RunContext parent, String userRequestString) {
		if (!tree.containsKey(parent)) {
			tree.put(parent, new ArrayList<RunContext>());
		}
//		parent.
//		tree.get(parent).add();
        // TODO: Fix!
        return null;
	}

	private void addChild(RunContext rcParent, RunContext rcChild) {
		if (!tree.containsKey(rcParent)) {
			tree.put(rcParent, new ArrayList<RunContext>());
		}
		tree.get(rcParent).add(rcChild);
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
	private Map<RunContext, List<RunContext>> tree = new HashMap<RunContext, List<RunContext>>();

    public JTree buildTree() {
        // TODO: Write this!
        return null;  //To change body of created methods use File | Settings | File Templates.
    }
}
