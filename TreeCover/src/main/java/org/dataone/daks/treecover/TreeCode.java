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
	
	
	public void addCodeOLD(TreeCode other) {
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
					this.intervals.add(otherInterval);
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
	
	
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for( TreeInterval interval : this.intervals )
			builder.append(interval.toString() + ",");
		String s = builder.toString();
		int len = s.length();
		return s.substring(0, len-1);
	}
	
	
}



