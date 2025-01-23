import java.util.*;
import java.io.*;

class KaiM {

    public static class Task {
        protected String description;
        protected boolean isDone;
    
        public Task(String description) {
            this.description = description;
            this.isDone = false;
        }
    
        public String getStatusIcon() {
            return (isDone ? "X" : " "); // mark done task with X
        }
    
        public void markAsDone() {
            this.isDone = true;
        }
    
        public void markAsNotDone() {
            this.isDone = false;
        }

        @Override
        public String toString() {
            return "[" + getStatusIcon() + "] " + description;
        }
    }
    
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out, true);
        
        int taskCount = 0;
        Task[] tasks = new Task[100];   //To store the items

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

            } else if (task.split(" ")[0].equals("mark")){
                int taskNumber = Integer.parseInt(task.split(" ")[1]) - 1;
                tasks[taskNumber].markAsDone();
                pw.println(" Nice! I've marked this task as done:");
                pw.println("   " + tasks[taskNumber]);

            } else if (task.split(" ")[0].equals("unmark")){
                int taskNumber = Integer.parseInt(task.split(" ")[1]) - 1;
                tasks[taskNumber].markAsNotDone();
                pw.println(" OK, I've marked this task as not done yet:");
                pw.println("   " + tasks[taskNumber]);

            } else {
                tasks[taskCount] = new Task(task);
                pw.println(" added: " + task);
                taskCount++;

            }
        }

        pw.close();
    }
}