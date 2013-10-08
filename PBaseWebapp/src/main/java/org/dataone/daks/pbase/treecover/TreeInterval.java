package org.dataone.daks.pbase.treecover;

public class TreeInterval {

	
	int left;
	int right;
	
	
	public TreeInterval(int left, int right) {
		this.left = left;
		this.right = right;
	}
	
	
	public TreeInterval copy() {
		return new TreeInterval(this.left, this.right);
	}
	
	
	public boolean subsumes(TreeInterval other) {
		boolean retVal = false;
		if( this.left <= other.left && this.right >= other.right )
			retVal = true;
		return retVal;
	}
	
	
	public String toString() {
		return "[" + this.left + ":" + this.right + "]";
	}
	
	
}


