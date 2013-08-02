package org.dataone.daks.treecover;

import java.util.List;
import java.util.ArrayList;

public class TreeCode {

	List<TreeInterval> intervals;
	
	public TreeCode(int left, int right) {
		TreeInterval interval = new TreeInterval(left, right);
		this.intervals = new ArrayList<TreeInterval>();
		this.intervals.add(interval);
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
			//the ith other interval is subsumed by this jth interval
			//so just ignore the ith other interval
			else if( thisInterval.subsumes(otherInterval) ) {
				;
			} 
		}
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



