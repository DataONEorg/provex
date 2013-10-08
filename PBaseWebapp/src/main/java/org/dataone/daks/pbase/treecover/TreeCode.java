package org.dataone.daks.pbase.treecover;

import java.util.List;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class TreeCode {

	List<TreeInterval> intervals;
	
	public TreeCode(int left, int right) {
		TreeInterval interval = new TreeInterval(left, right);
		this.intervals = new ArrayList<TreeInterval>();
		this.intervals.add(interval);
	}
	
	
	public TreeCode(String str) {
		this.intervals = new ArrayList<TreeInterval>();
		String s1 = str.replace('[', ' ');
		String s2 = s1.replace(']', ' ');
		StringTokenizer tok = new StringTokenizer(s2, ",");
		while( tok.hasMoreTokens() ) {
			String intervalStr = tok.nextToken().trim();
			String left = intervalStr.substring(0, intervalStr.indexOf(':'));
			String right = intervalStr.substring(intervalStr.indexOf(':')+1, intervalStr.length());
			TreeInterval interval = new TreeInterval(Integer.valueOf(left), Integer.valueOf(right));
			this.intervals.add(interval);
		}
	}
	
	
	public void addCode(TreeCode other) {
		//Process all of the intervals in the TreeCode other
		for( int i = 0; i < other.intervals.size(); i++ ) {
			TreeInterval otherInterval = other.intervals.get(i);
			//Check if the ith interval in other subsumes or is subsumed
			//by some jth interval in this TreeCode
			TreeInterval thisInterval = this.intervals.get(0);
			//the ith other interval subsumes this jth interval
			//so replace this jth interval with the other ith interval
			if( otherInterval.subsumes(thisInterval) ) {
				thisInterval.left = otherInterval.left;
				thisInterval.right = otherInterval.right;
				int j = 1; 
				while( j < this.intervals.size() ) {
					thisInterval = this.intervals.get(j);
					if( otherInterval.subsumes(thisInterval) )
						this.intervals.remove(j);
					else
						j++;
				}
			}
			//the ith other interval is subsumed by this 0th interval
			//so just ignore the ith other interval
			else if( thisInterval.subsumes(otherInterval) ) {
				;
			}
			//otherwise find if there is some this jth interval that subsumes
			//the ith other interval, if not then add the ith other interval to this list
			//also check if the ith other interval subsumes some this jth interval
			//and if that is the case then replace the values of this jth interval
			else {
				boolean thisSubsumesOther = false;
				boolean otherSubsumesThis = false;
				int thisJpos = -1;
				for(int j = 1; j < this.intervals.size(); j++) {
					thisInterval = this.intervals.get(j);
					if( thisInterval.subsumes(otherInterval) ) {
						thisSubsumesOther = true;
						break;
					}
					else if( otherInterval.subsumes(thisInterval) ) {
						otherSubsumesThis = true;
						thisJpos = j;
						break;
					}
				}
				if( ! thisSubsumesOther && ! otherSubsumesThis )
					this.intervals.add(otherInterval.copy());
				else if( otherSubsumesThis ) {
					this.intervals.get(thisJpos).left = otherInterval.left;
					this.intervals.get(thisJpos).right = otherInterval.right;
					int k = thisJpos + 1;
					while( k < this.intervals.size() ) {
						thisInterval = this.intervals.get(k);
						if( otherInterval.subsumes(thisInterval) )
							this.intervals.remove(k);
						else
							k++;
					}
				}
			}
		}
	}
	
	
	//Let the postorder number of a node n be j, and let the index associated with n be i.
	//There exists a direct path from node n to some other node m with the postorder number
	//k iff i <= k < j
	//Note: change to i <= k <= j, even if in the original paper it is as stated above
	public boolean reachable(int postorder) {
		boolean retVal = false;
		for(TreeInterval interval: this.intervals) {
			if( interval.left <= postorder && postorder <= interval.right ) {
				retVal = true;
				break;
			}
		}
		return retVal;
	}
	
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for( TreeInterval interval : this.intervals )
			builder.append(interval.toString() + ",");
		String s = builder.toString();
		int len = s.length();
		return s.substring(0, len-1);
	}
	
	
}



