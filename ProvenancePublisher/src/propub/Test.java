package propub;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

/*
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Polygon;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JToggleButton;
*/

public class Test {
	
/*	
	static class TriangleIcon implements Icon {
		
		String name;
	
		static class State {
			public static final int NORMAL   = 0;
			public static final int PRESSED  = 1;
			public static final int ROLLOVER = 2;
			public static final int SELECTED = 3;
			private State() {
			}
		}
		int state;
		Color color;

	    public TriangleIcon(Color c, int state) {
			color = c;
			this.state = state;
	    }

	    public int getIconWidth() {
	    	return 20;
	    }

	    public int getIconHeight() {
	    	return 20;
	    }
	
	    public void paintIcon(Component c, Graphics g, int x, int y) {
			g.setColor(color);
			Polygon p = new Polygon();
			if (state == State.NORMAL) {
				p.addPoint(x + (getIconWidth() / 2), y);
				p.addPoint(x, y + getIconHeight() - 1);
				p.addPoint(x + getIconWidth() - 1, y + getIconHeight() - 1);
			} else if (state == State.PRESSED) {
				p.addPoint(x, y);
				p.addPoint(x + getIconWidth() - 1, y);
				p.addPoint(x + (getIconWidth() / 2), y + getIconHeight() - 1);
			} else if (state == State.SELECTED) {
				p.addPoint(x + getIconWidth() - 1, y);
				p.addPoint(x + getIconWidth() - 1, y + getIconHeight() - 1);
				p.addPoint(x, y + (getIconHeight() / 2));
			} else {
			p.addPoint(x, y);
				p.addPoint(x, y + getIconHeight() - 1);
				p.addPoint(x + getIconWidth() - 1, y + (getIconHeight() / 2));
			}
			g.fillPolygon(p);
		}
	}

	public static void main(String args[]) {
		JFrame frame = new JFrame();
		Container contentPane = frame.getContentPane();
		contentPane.setLayout(new GridLayout(0, 1));
		
		Icon normalIcon   = new TriangleIcon(Color.red, TriangleIcon.State.NORMAL);
		Icon pressedIcon  = new TriangleIcon(Color.red, TriangleIcon.State.PRESSED);
		Icon selectedIcon = new TriangleIcon(Color.red, TriangleIcon.State.SELECTED);
		Icon rolloverIcon = new TriangleIcon(Color.red, TriangleIcon.State.ROLLOVER);
		
		//JToggleButton b = new JToggleButton("Initially UnSelected");
		//contentPane.add(b);
		//b = new JToggleButton("Initially Selected", true);
		//contentPane.add(b);
		JToggleButton b = new JToggleButton(normalIcon);
		b.setPressedIcon(pressedIcon);
		b.setSelectedIcon(selectedIcon);
		b.setRolloverIcon(rolloverIcon);
		b.setRolloverEnabled(true);
		contentPane.add(b);
		frame.setSize(300, 150);
		frame.show();
	}
*/	

	Hashtable<String, String> invoc = new Hashtable<String, String>();
	Hashtable<String, String> data = new Hashtable<String, String>();
	/*
	 * Write a file with StringBuffer data
	 */
	public void writeFile(StringBuffer data, String outfileName) {
		try {
			FileWriter fw = new FileWriter(outfileName);
			BufferedWriter pgfile = new BufferedWriter(fw);
			pgfile.append(data.toString());
			pgfile.close();
		} catch (Exception e) {
			System.out.println("writeFile:--> ");
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	/*
	 * Reads the file line by line and add them into the ArrayList
	 */
	public StringBuffer readFile(String fileName) {
		StringBuffer sb = new StringBuffer();
		int i = 0;
		try {
			BufferedReader br = getFileHandle(fileName);
			String line = br.readLine();
			while (line != null) {
				i++;
				if (i < 20) {
					line = line.replace("e(", "").replace(").", "").replace("\"", "");
					sb.append("used(" + line.split(",")[1] + "," + line.split(",")[0] + ")." + "\n");
					sb.append("gen_by(" + line.split(",")[2] + "," + line.split(",")[1] + ")." + "\n");
					invoc.put(line.split(",")[1], line.split(",")[1]);
					data.put(line.split(",")[0], line.split(",")[0]);
					data.put(line.split(",")[2], line.split(",")[2]);	
				}
				line = br.readLine();
			}
			
			br.close();
			
			Enumeration<String> iKeys = invoc.elements();
			String iS;
			while (iKeys.hasMoreElements()) {
				iS=iKeys.nextElement();
				sb.append("actor(" + iS + "," + iS + ")."  + "\n");
			}
			
			Enumeration<String> dKeys = data.elements();
			String dS;
			while (dKeys.hasMoreElements()) {
				dS=dKeys.nextElement();
				sb.append("data(" + dS + "," + dS + ")."  + "\n");
			}
			
		} catch (Exception e) {
			System.out.println("readFile:--> " + i);
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return sb;
	}
	
	private BufferedReader getFileHandle(String fileName) throws Exception{
		FileReader fr = new FileReader(fileName);
		BufferedReader br = new BufferedReader(fr);
		return br;
	}
	
	public static void main(String args[]) {
		String inFile  = "/Users/scdey/mySpace/propub/data/hm_edb.dlv";
		String outFile = "/Users/scdey/mySpace/propub/data/hm.dlv";
		Test test = new Test();
		test.writeFile(test.readFile(inFile), outFile);
	}
}

