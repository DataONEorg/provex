package types;

import javax.swing.tree.DefaultMutableTreeNode;

public class PayloadTreeNode extends DefaultMutableTreeNode {
	public PayloadTreeNode(String name) {
		super(name);
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setPayload(Object payload) {
		this.payload = payload;
	}

	public Object getPayload() {
		return payload;
	}

	private Object payload = null;
	private Integer id = -2;
}
