import java.util.*;
import java.io.*;

class KaiM {
    
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out, true);
        
        int taskCount = 0;
        String[] tasks = new String[100];   //To store the items

        pw.println(" Hello! I'm KaiM");
        pw.println(" What can I do for you?");

        while(taskCount < 100) {
            String task = br.readLine();

            if(task.equals("bye")) {
                pw.println(" Bye. Hope to see you again soon!");
                break;
            } else if (task.equals("list")) {
                for (int i = 0; i < taskCount; i++) {
                    pw.println(" " + (i + 1) + ". " + tasks[i]);
                }
            } else {
                tasks[taskCount] = task;
                pw.println(" added: " + task);
                taskCount++;
            }
        }

        pw.close();
    }
}