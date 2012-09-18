package propub;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created with IntelliJ IDEA.
 * User: sean
 * Date: 9/17/12
 * Time: 3:25 PM
 */

public class ArtifactTableDoubleClickListener implements MouseListener {
	public void mouseClicked(MouseEvent mouseEvent) {
		if (mouseEvent.getClickCount() == 2) {
			int rowIndex = ((JTable) mouseEvent.getSource()).getSelectedRow();
			System.out.println("Double-click detected on line " + rowIndex + ". To customize behavior, see propub.ArtifactTableDoubleClickListener:18");
		}
	}

	public void mousePressed(MouseEvent mouseEvent) {
	}

	public void mouseReleased(MouseEvent mouseEvent) {
	}

	public void mouseEntered(MouseEvent mouseEvent) {
	}

	public void mouseExited(MouseEvent mouseEvent) {
	}
}
