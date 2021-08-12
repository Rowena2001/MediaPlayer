/**
 * @author Rowena Shi
 */

// This class represents the Key object stored in DataItem

public class Key {
	
	String name;
	String kind;
	
	public Key(String word, String type) {
		name = word.toLowerCase(); // converts string word to lower case letter
		kind = type;
	}
	
	public String getName() {
		return name;
	}
	
	public String getKind() {
		return kind;
	}
	
	public int compareTo(Key k) {
		if(name.equals(k.name) && kind.equals(k.kind)) { // 2 keys are equal
			return 0;
		}
		else if(name.compareTo(k.name) < 0) { // this key is less than k
				return -1;
		}
		else if(name.equals(k.name) && kind.compareTo(k.kind) < 0) { // this key is less than k
				return -1;
		}
		else { // this key is not less than k
			return 1; 
		}
	}
	
}
