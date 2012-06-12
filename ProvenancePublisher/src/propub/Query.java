package propub;

public class Query {
	
	int    id;
	int    noState;
	int    sessionId;
	int    parentId;
	
	public Query() {
		// TODO Auto-generated constructor stub
	}
	
	public Query(int id, int noState, int sessionId, int parentId) {
		this.id        = id;
		this.noState   = noState;
		this.sessionId = sessionId;
		this.parentId  = parentId;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNoState() {
		return noState;
	}
	public void setNoState(int noState) {
		this.noState = noState;
	}	
	public int getSessionId() {
		return sessionId;
	}

	public void setSessionId(int sessionId) {
		this.sessionId = sessionId;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	
}
