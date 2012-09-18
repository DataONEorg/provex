package propub;

import db.DLVDriver;
import env.EnvInfo;
import file.FileDriver;
import parser.Model;
import types.IconType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// This class encapsulates everything about the current state of the
// graph, including User Requests (both security and explanatory), and
// original graph. At the moment, this is all encapsulated in the
// out.txt of the model. For now, we will use that, and extract stuff as
// needed.
public class RunContext {
	// This constructor is for a context with an input file, but no user
	// requests. Consider renaming this to a public static method so the name
	// can provide some context.
	//public RunContext(File pgFile) {
    //    this(pgFile, blankFile(), constants.SL_DLV_PATH);
	//}
   
    private static int instanceNum = 1;

	public void setIconType(IconType type) {
		this.iconType = type;
	}

	public IconType getIconType() {
		return iconType;
	}

	private final int myInstanceNum;
	private IconType iconType = null;

    public RunContext(File pgFile, File urFile, String dlvPath) {
        this.myInstanceNum = instanceNum++;
        FileDriver fd = new FileDriver();
        Constants constants = new Constants();
        EnvInfo ei = new EnvInfo();
        ei.setSetupInfo(constants);
        DLVDriver dlv = new DLVDriver();
        dlv.exeDLV(new String[] {dlvPath, pgFile.getAbsolutePath(), urFile.getAbsolutePath()});

        String modelString = fd.readFile(constants.PROPUB_EXE + constants.ENV_SEPARATOR + "out.txt").toString();

        modelString = modelString.replaceAll("\\),", "\\).").replace("}", ".");

        this.pgFile = pgFile;
        this.urFile = urFile;
        this.model = new Model(modelString);

        //display image
//		displayImage(model.getIntrmediateModel(model.getFinalStateNo()));
    }
    
    public static RunContext forCustomization(File pgFile, File urFile) {
    	Constants constants = new Constants();
    	EnvInfo envInfo = new EnvInfo();
    	envInfo.setSetupInfo(constants);
    	return new RunContext(pgFile, urFile, constants.SL_DLV_PATH);
    }
    
    public static RunContext forCustomization(File pgFile) {
    	return RunContext.forCustomization(pgFile, blankFile());
    }
    
    public static RunContext forRPQ(File pgFile) {
    	Constants constants = new Constants();
    	EnvInfo envInfo = new EnvInfo();
    	envInfo.setSetupInfo(constants);
    	return new RunContext(pgFile, blankFile(), constants.RPQ4_DLV_PATH);
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

    public static File writeOut(String s) {
        File f = null;
        Writer w = null;
        try {
            f = File.createTempFile("blahblah", ".dlv");
            w = new FileWriter(f);
            w.write(s);
        } catch (IOException e) {
            e.printStackTrace();
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

	public RunContext withNewUserRequestFile(File userRequestFile, int state) {
        if (fileLoadContext) {
            return RunContext.forCustomization(pgFile, userRequestFile);
        }
        else {
            File pgFile = processModelOutput();
            return RunContext.forCustomization(pgFile, userRequestFile);
        }
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

    Pattern PATTERN_PREDICATE_WITH_ARGUMENTS = Pattern.compile("([a-zA-Z_]+)\\((.*)\\)\\.");

	private File processModelOutput() {

        Set<String> interestingPredicates = new HashSet<String>(Arrays.asList("l_data", "l_actor", "l_gen_by", "l_used", "l_dep"));

		String modelString = model.getModel();
        if (modelString.charAt(0) == '{') {
            modelString = modelString.substring(1);     // Cut off the last character.
        }
        String[] modelComponents = modelString.split("\\s+");

        Map<Integer, List<String>> dataByState = new HashMap<Integer, List<String>>();
        Map<Integer, List<String>> actorByState = new HashMap<Integer, List<String>>();
        Map<Integer, List<String>> genByByState = new HashMap<Integer, List<String>>();
        Map<Integer, List<String>> usedByState = new HashMap<Integer, List<String>>();
        Map<Integer, List<String>> depByState = new HashMap<Integer, List<String>>();

        for (String modelComp : modelComponents) {
            if (!modelComp.endsWith(".")) {
                continue;       // Throw out weirdness like all_lineage until I find out what it does.
                // At the moment, this just ends up throwing out all zero arity predicates.
            }
            Matcher m = PATTERN_PREDICATE_WITH_ARGUMENTS.matcher(modelComp);
            if (m.matches()) {
                String predicate = m.group(1);
                String arguments = m.group(2);
                if (!interestingPredicates.contains(predicate)) {
                    System.out.println(predicate + " not in group.");
                    // Sorry, not interested.
                    continue;
                }
                String[] argumentPieces = arguments.split(",");
                String stageString = argumentPieces[argumentPieces.length - 1];
                int stageNum = Integer.valueOf(stageString);

                if ("l_data".equals(predicate)) {
                    // add to map, indexed by stage
                    if (!dataByState.containsKey(stageNum)) {
                        dataByState.put(stageNum, new ArrayList<String>());
                    }
                    dataByState.get(stageNum).add("data(" + argumentPieces[0] + "," + argumentPieces[1] + ").");
                }
                else if ("l_dep".equals(predicate)) {
                    if (!depByState.containsKey(stageNum)) {
                        depByState.put(stageNum, new ArrayList<String>());
                    }
                    depByState.get(stageNum).add("dep(" + argumentPieces[0] + "," + argumentPieces[1] + ").");
                }
                else if ("l_actor".equals(predicate)) {
                    if (!actorByState.containsKey(stageNum)) {
                        actorByState.put(stageNum, new ArrayList<String>());
                    }
                    actorByState.get(stageNum).add("actor(" + argumentPieces[0] + "," + argumentPieces[1] + ").");
                }
                else if ("l_gen_by".equals(predicate)) {
                    if (!genByByState.containsKey(stageNum)) {
                        genByByState.put(stageNum, new ArrayList<String>());
                    }
                    genByByState.get(stageNum).add("gen_by(" + argumentPieces[0] + "," + argumentPieces[1] + ").");
                }
                else if ("l_used".equals(predicate)) {
                    if (!usedByState.containsKey(stageNum)) {
                        usedByState.put(stageNum, new ArrayList<String>());
                    }
                    usedByState.get(stageNum).add("used(" + argumentPieces[0] + "," + argumentPieces[1] + ").");
                }
            }

        }

        if (dataByState.isEmpty() && actorByState.isEmpty() && genByByState.isEmpty() && usedByState.isEmpty()) {
            System.out.println("No new information in this model output, nothing to rewrite. Using old program graph file...");
            return pgFile;
        }

        // Use the maximum stage recorded, for now. Preferably, this should be configurable.
        System.out.println("dataByState.keySet(): " + dataByState.keySet());
        int max1 = Collections.max(dataByState.keySet());
        int max2 = Collections.max(actorByState.keySet());
        int max3 = Collections.max(genByByState.keySet());
        int max4 = Collections.max(usedByState.keySet());
        int max5 = Collections.max(depByState.keySet());

        int max = max1 > max2 ? max1 : max2;
        max = max > max3 ? max : max3;
        max = max > max4 ? max : max4;
        max = max > max5 ? max : max5;
        System.out.println("Maximum stage computed: " + max + " (" + max1 + "," + max2 + "," + max3 + "," + max4 + "," + max5 + ")");
       // modelString.split()

        StringBuilder sb = new StringBuilder();

        for (String item : dataByState.get(max)) {
            sb.append(item).append(" ");
        }
        for (String item : actorByState.get(max)) {
            sb.append(item).append(" ");
        }
        for (String item : genByByState.get(max)) {
            sb.append(item).append(" ");
        }
        for (String item : usedByState.get(max)) {
            sb.append(item).append(" ");
        }

        // SEAN: Need to do this to fix up the syntax to be *exactly* what DLV
        // wants.
        // It is very picky and will choke with a syntax error on even an extra
        // period at the end. A more principled way of cleaning the file up, or
        // of contructing it in the first place, would be desirable
        while (sb.charAt(sb.length() - 1) != ')') {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append(".");

        File newPgFile = writeOut(sb.toString());

        return newPgFile;
	}

    public void setFileLoadContext(boolean b) {
        fileLoadContext = b;
    }

    private Model model = null;
    private boolean fileLoadContext = false;
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
