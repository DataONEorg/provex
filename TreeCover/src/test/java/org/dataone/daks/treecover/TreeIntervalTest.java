package org.dataone.daks.treecover;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Unit test for TreeInterval class.
 */
public class TreeIntervalTest {
    
    
    public void testTreeIntervalToString() {
    	//should print [3,5]
    	TreeInterval interval = new TreeInterval(3,5);
    	assertTrue(interval.toString().equals("[3,5]"));
    }
    
    @Test
    public void testTreeIntervalSubsumes1() {
    	// [2,6] should subsume [3,5]
    	TreeInterval interval1 = new TreeInterval(2,6);
    	TreeInterval interval2 = new TreeInterval(3,5);
    	assertTrue(interval1.subsumes(interval2));
    }
    
    @Test
    public void testTreeIntervalSubsumes2() {
    	// [3,5] should subsume [3,5]
    	TreeInterval interval1 = new TreeInterval(3,5);
    	TreeInterval interval2 = new TreeInterval(3,5);
    	assertTrue(interval1.subsumes(interval2));
    }
    
    @Test
    public void testTreeIntervalSubsumes3() {
    	// [4,9] should NOT subsume [3,7]
    	TreeInterval interval1 = new TreeInterval(4,9);
    	TreeInterval interval2 = new TreeInterval(3,7);
    	assertFalse(interval1.subsumes(interval2));
    }
    
    
    
}



