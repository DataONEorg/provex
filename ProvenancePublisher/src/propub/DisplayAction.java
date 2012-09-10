package propub;

import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.Writer;
import java.io.FileWriter;
import java.io.IOException;

    public class DisplayAction extends AbstractAction {

        public DisplayAction(Rpq2TableModel model) {
            this.model = model;
        }

        @Override
        public void actionPerformed(ActionEvent evt) {
//            String selectedItemsAsFacts = model.getSelectedPairs();
//            System.out.println("Wrote out checked pairs as Datalog facts in the following file: " + writeToFile(selectedItemsAsFacts).getAbsolutePath());
			String s = model.getSelectedPairsAsRpq4();
			System.out.println(s);
        }

	private static File writeToFile(String model) {
		File f = null;
		Writer w = null;

		try {
			f = File.createTempFile("model", ".txt");
			w = new FileWriter(f);
			w.write(model);
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
		finally {
			if (w != null) {
				try {
					w.close();
				}
				catch(IOException ex) {
					ex.printStackTrace();
				}
			}
		}
		return f;
	}


        private Rpq2TableModel model;
    }

