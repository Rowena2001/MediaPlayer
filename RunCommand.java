/*
 * @author CS2210
 */

import java.lang.ProcessBuilder;

public class RunCommand {

    public  RunCommand() {
    }

    public void run (String command) {
		String os = System.getProperty("os.name").toLowerCase();
		try {
			String args[] = command.split(" ");
			if (os.indexOf("win") == -1) {// Not Windows OS; convert exe to sh
				args[0] = args[0].substring(0,args[0].length()-3) + "sh";
				args[0] = "./" + args[0];
			}
			ProcessBuilder pb = new ProcessBuilder(args);
			Process p = pb.start();
		}
		catch (Exception e) {
			System.out.println("Error executing command: "+e.getMessage());
		}
    }

}
