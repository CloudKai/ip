import java.util.*;
import java.io.*;

class KaiM {
    
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out, true);

        pw.println(" Hello! I'm KaiM");
        pw.println(" What can I do for you?");

        while(true) {
            String task = br.readLine();
            
            if (!task.equals("bye")) {
                pw.println(" " + task);
                
            } else {
                pw.println(" Bye. Hope to see you again soon!");
                break;
            }
        }

        pw.close();
    }
}