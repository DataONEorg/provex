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

    private static int instanceNum = 1;
    private final int myInstanceNum;

    public RunContext(File pgFile, File urFile) {
        this.myInstanceNum = instanceNum++;
        FileDriver fd = new FileDriver();
        Constants constants = new Constants();
        EnvInfo ei = new EnvInfo();
        ei.setSetupInfo(constants);
        DLVDriver dlv = new DLVDriver();
        if (!urFile.getName().startsWith("blank")) {
            System.out.println("Whoa");
        }
        dlv.exeDLV(new String[] {constants.SL_DLV_PATH, pgFile.getAbsolutePath(), urFile.getAbsolutePath()});

        String modelString = fd.readFile(constants.PROPUB_EXE + constants.ENV_SEPARATOR + "out.txt").toString();
        modelString = modelString.replaceAll("\\),", "\\).").replace("}", ".");

        this.pgFile = pgFile;
        this.urFile = urFile;
        this.model = new Model(modelString);

        //display image
//		displayImage(model.getIntrmediateModel(model.getFinalStateNo()));
    }

    @Override
    public String toString() {
        return "rcInst" + myInstanceNum;
    }

    public int getInstanceId() {
        return myInstanceNum;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RunContext that = (RunContext) o;

        if (model != null ? !model.equals(that.model) : that.model != null) return false;
        if (pgFile != null ? !pgFile.equals(that.pgFile) : that.pgFile != null) return false;
        if (urFile != null ? !urFile.equals(that.urFile) : that.urFile != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = model != null ? model.hashCode() : 0;
        result = 31 * result + (pgFile != null ? pgFile.hashCode() : 0);
        result = 31 * result + (urFile != null ? urFile.hashCode() : 0);
        return result;
    }
}
