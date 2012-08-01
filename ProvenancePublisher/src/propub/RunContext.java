package propub;

import db.DLVDriver;
import env.EnvInfo;
import file.FileDriver;
import parser.Model;

import java.io.File;
import java.io.IOException;

// This class encapsulates everything about the current state of the
// graph, including User Requests (both security and explanatory), and
// original graph. At the moment, this is all encapsulated in the
// out.txt of the model. For now, we will use that, and extract stuff as
// needed.
public class RunContext {
	// This constructor is for a context with an input file, but no user
	// requests. Consider renaming this to a public static method so the name
	// can provide some context.
	public RunContext(File pgFile) {
        this(pgFile, blankFile());
	}

    public RunContext(File pgFile, File urFile) {
        FileDriver fd = new FileDriver();
        Constants constants = new Constants();
        EnvInfo ei = new EnvInfo();
        ei.setSetupInfo(constants);
        DLVDriver dlv = new DLVDriver();
        dlv.exeDLV(new String[] {constants.SL_DLV_PATH, pgFile.getAbsolutePath(), urFile.getAbsolutePath()});

        String modelString = fd.readFile(constants.PROPUB_EXE + constants.ENV_SEPARATOR + "out.txt").toString();
        modelString = modelString.replaceAll("\\),", "\\).").replace("}", ".");

        this.pgFile = pgFile;
        this.urFile = blankFile();
        this.model = new Model(modelString);

        //display image
//		displayImage(model.getIntrmediateModel(model.getFinalStateNo()));
    }

    public File getUserRequestFile() {
        return urFile;
    }

    public File getProgramGraphFile() {
        return pgFile;
    }

	public RunContext withNewUserRequestFile(File userRequestFile) {
        File pgFile = this.getProgramGraphFile();
        return new RunContext(pgFile, userRequestFile);
    }

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

    private Model model = null;
    private File pgFile = null;
    private File urFile = null;
    private static File blankFile = null;

    public Model getModel() {
        return model;
    }
}
