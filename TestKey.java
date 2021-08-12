import java.io.*;

public class TestKey {

    public static void main(String[] args) {
	
	Key key1 = new Key("name","kind");
	Key key2 = new Key("mine","class");
	
	try {
	    if (key1.compareTo(key2) > 0)
			System.out.println("Test 1 passed");
	    else System.out.println("Test 1 failed");
	}
	catch(Exception e) {
	    System.out.println("Test 1 failed");
	}

	try {
	    if (key2.compareTo(key1) < 0)
			System.out.println("Test 2 passed");
	    else System.out.println("Test 2 failed");
	}
	catch(Exception e) {
	    System.out.println("Test 2 failed");
	}

	key1 = new Key("mine","kind");
	
	try {
	    if (key1.compareTo(key2) > 0)
			System.out.println("Test 3 passed");
	    else System.out.println("Test 3 failed");
	}
	catch(Exception e) {
	    System.out.println("Test 3 failed");
	}
	
	try {
	    if (key2.compareTo(key1) < 0)
			System.out.println("Test 4 passed");
	    else System.out.println("Test 4 failed");
	}
	catch(Exception e) {
	    System.out.println("Test 4 failed");
	}	
	
	key1 = new Key("mine","class");
	
	try {
	    if (key1.compareTo(key2) == 0)
			System.out.println("Test 5 passed");
	    else System.out.println("Test 5 failed");
	}
	catch(Exception e) {
	    System.out.println("Test 5 failed");
	}	
    }
}