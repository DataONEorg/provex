package propub;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: sean
 * Date: 9/14/12
 * Time: 4:48 PM
 */

public class Metadatum {

	public Metadatum() {
		metadata = new HashMap<String, String>();
	}
	public void addKV(String k, String v) {
		metadata.put(k, v);
	}

	public String get(String k) {
		return metadata.get(k);
	}

	private final Map<String, String> metadata;
}
