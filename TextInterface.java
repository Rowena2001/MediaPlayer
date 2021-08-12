import java.io.*;

/**
 * @author Rowena Shi
 */

// This class implements the user interface and it contains the main method

public class TextInterface {
	
	public static void main(String[] args) {
		
		OrderedDictionary od = new OrderedDictionary();
		
		try { // tries to input and store data from file
			inputAndStore(od, args[0]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// loop: asks the user to enter a command, process the command, repeated until the user enters "end" that will terminate the program
		while(true) {
			
			try { 
				StringReader keyboard = new StringReader();
				String line = keyboard.read("Please enter next command: ");
				
				if(line.strip().startsWith("get")) { // command get w
					String[] words = line.split(" ");
					String name = words[1];
					getW(od, name);
				}
				else if(line.strip().startsWith("remove")) { // command r w k
					String[] words = line.split(" ");
					String w = words[1];
					String k = words[2];
					removeWK(od, w, k);
				}
				else if(line.strip().startsWith("add")) { // command add w k c
					String[] words = line.split(" ");
					String w = words[1];
					String k = words[2];
					String c = words[3];
					addWKC(od, w, k, c);
				}
				else if(line.startsWith("list")) { // command list prefix
					String[] words = line.split(" ");
					String prefix = words[1];
					listPrefix(od, prefix);
				}
				else if(line.strip().equals("first")) { // command first
					first(od);
				}
				else if(line.strip().equals("last")) { // command last
					last(od);
				}
				else if(line.strip().equals("end")) { // break
					System.out.println("Goodbye :)\n");
					break;
				}
				else {
					System.out.println("Invalid input, please try again.\n");
				}
			}
			catch(Exception e) {
				System.out.println("Invalid input, please try again.\n");
			}
		}
	}
	
	// reads in file and stores into ordered dictionary
	private static void inputAndStore(OrderedDictionary od, String file) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(file));
	    String word = in.readLine();
	    String kind;
	    String content;
		while (word != null) {
		    try {
				content = in.readLine();
				kind = identifyKind(content); // identifies the kind based on content
				od.put(new DataItem(new Key(word, kind), content));
				word = in.readLine();
		    }
		    catch (Exception e) {
		   }
	    }
	}
	
	// Identifies the "kind" stored in data item (based on content)
	private static String identifyKind(String content) {
		String kind = null;
		if(content.endsWith("wav") || content.endsWith("mid")) {
			kind = "sound";
		}
		else if(content.endsWith("jpg") || content.endsWith("gif")) {
			kind = "picture";
		}
		else if(content.endsWith("exe") || content.endsWith("program")) {
			kind = "program";
		}
		else if(content.endsWith("html")) {
			kind = "url";
		}
		else {
			kind = "definition";
		}
		return kind;
	}
	
	// Processes DataItem D (command get w)
	private static void getW(OrderedDictionary od, String w) {
		
		boolean flag = false;
		
		// creates a key object for each kind possibility with name w
		Key definition = new Key(w, "definition");
		Key sound = new Key(w, "sound");
		Key picture = new Key(w, "picture");
		Key url = new Key(w, "url");
		Key program = new Key(w, "program");
		
		DataItem D = od.get(definition); // gets definition
		if(D != null) {
			System.out.println(D.getContent());
			flag = true;
		}
		
		DataItem S = od.get(sound); // gets sound
		if(S != null) {
			SoundPlayer sp = new SoundPlayer();
			try {
				sp.play(S.getContent());
			} catch (MultimediaException e) {
				e.printStackTrace();
			}
			flag = true;
		}
		
		DataItem Pic = od.get(picture); // gets picture
		if(Pic != null) {
			PictureViewer pv = new PictureViewer();
			try {
				pv.show(Pic.getContent());
			} catch (MultimediaException e) {
				e.printStackTrace();
			}
			flag = true;
		}
		
		DataItem U = od.get(url); // gets url
		if(U != null) {
			ShowHTML sh = new ShowHTML();
			sh.show(U.getContent());
			flag = true;
		}
		
		DataItem P = od.get(program); // gets program
		if(P != null) {
			RunCommand rc = new RunCommand();
			rc.run(P.getContent());
			flag = true;
		}
		
		if(flag == false) { // no DataItem object in the ordered dictionary contains w as their name attribute 
			System.out.println("The word " + w + " is not in the ordered dictionary\n");
			if(od.predecessor(definition) != null) {
				System.out.println("Preceding word: " + w + " is: " + od.predecessor(definition).getKey().getName() + "\n");
			}
			else {
				System.out.println("Preceding word: " + w + " is:\n");
			}
			if(od.successor(definition) != null) {
				System.out.println("Following word: " + w + " is: " + od.successor(definition).getKey().getName() + "\n");
			}
			else {
				System.out.println("Following word: " + w + " is:\n");
			}
		}
	}
	
	// Removes from the ordered dictionary the DataItem object with key (w,k)
	private static void removeWK(OrderedDictionary od, String w, String k) {
		
		boolean flag = false;
		
		Key current = new Key(w, k);
		try {
			od.remove(current);
			flag = true;
		} catch (DictionaryException e) {
			e.printStackTrace();
		}
		if(flag == false) { // no such record exists
			System.out.println("No record in the ordered dictionary has key (" + w + ", " + k + ").\n");
		}
	}
	
	// Inserts a DataItem object ((w,k),c) into the ordered dictionary if there is no record with key (w,k) already there
	private static void addWKC(OrderedDictionary od, String w, String k, String c) {
		
		boolean flag = false;
		
		Key newKey = new Key(w, k);
		DataItem newData = new DataItem(newKey, c);
		try {
			od.put(newData);
			flag = true;
		} catch (DictionaryException e) { // key (w,k) already exists in od
			e.printStackTrace();
		}
		if(flag == false) {
			System.out.println("A record with the given key (" + w + ", " + k + ") is already in the ordered dictionary.\n");
		}
	}
	
	// Prints names that begin with prefix
	private static void listPrefix(OrderedDictionary od, String prefix) {
		
		Key k = new Key(prefix, "");
		DataItem current = od.get(k); // attempts to get a node with key k
		
		try {
			boolean flag = false;
			String output = "";
			
			if(current != null) {
				output += current.getKey().getName() + ", "; // appends to output if name exists with substring prefix
				flag = true;
			}
			else {
				current = od.successor(k); // sets current to successor
			}
			while(current.getKey().getName().startsWith(prefix) || (current.getKey().getName()).equals(prefix)) { // continues traversing through successors if they have prefix
				output += current.getKey().getName() + ", ";
				flag = true;
				current = od.successor(current.getKey());
			}
			if(flag == true) {
				output = output.substring(0, output.length()-2); // deletes the ", " at the end
				System.out.println(output);
			}
			else {
				System.out.println("No name attributes in the ordered dictionary start with prefix " + prefix + ".\n");
			}
		}
		catch(Exception e) {
			System.out.println(current.getKey().getName());
		}
	}
	
	// prints the first node of the tree
	private static void first(OrderedDictionary od) {
		DataItem first = od.smallest();
		System.out.println(first.getKey().getName() + ", " + first.getKey().getKind() + ", " + first.getContent());
	}
	
	// prints the last node of the tree
	private static void last(OrderedDictionary od) {
		DataItem last = od.largest();
		System.out.println(last.getKey().getName() + ", " + last.getKey().getKind() + ", " + last.getContent());
	}	
}
