package propub;

import java.io.File;

// This class encapsulates everything about the current state of the
// graph, including User Requests (both security and explanatory), and
// original graph. At the moment, this is all encapsulated in the
// out.txt of the model. For now, we will use that, and extract stuff as
// needed.
public class RunContext {
	// This constructor is for a context with an input file, but no user
	// requests. Consider renaming this to a public static method so the name
	// can provide some context.
	public RunContext(File file) {
		Constants constants = new Constants();
		DLVDriver dlv = new DLVDriver();
		dlv.exeDLV(new String[] {constants.SL_DLV_PATH, file.getAbsolutePath(), blankFile()});

		model = fd.readFile(constants.PROPUB_EXE + constants.ENV_SEPARATOR + "out.txt").toString();
		model = model.replaceAll("\\),", "\\).").replace("}", ".");

		this.model = new Model(model);

		//display image
//		displayImage(model.getIntrmediateModel(model.getFinalStateNo()));
	}

	public RunContext(RunContext rc,

	private static File blankFile() {
		if (blankFile == null) {
			try {
				blankFile = File.createTempFile("blank", ".dlv");
			}
			catch(IOException ex) {
				ex.printStackTrace();
			}
		}
		return blankFile;
	}
}
