/**
 * @author Rowena Shi
 */

// This class represents the DataItem object stored in the nodes of OrderedDictionary

public class DataItem {
	
	Key theKey;
	String content;
	
	public DataItem(Key k, String data) {
		theKey = k;
		content = data;
	}
	
	public Key getKey() {
		return theKey;
	}
	
	public String getContent() {
		return content;
	}

}
