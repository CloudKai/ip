
import java.util.*;
import java.io.*;


class KaiM {
    
    
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter pw = new PrintWriter(System.out, true);
        
        int taskCount = 0;
        Task[] tasks = new Task[100];   //To store the items

        pw.println(" Hello! I'm KaiM");
        pw.println(" What can I do for you?");

        while(taskCount < 100) {
            String task = br.readLine();
            try {
                if (task.equals(null)){
                    continue;
                }

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
                
                } else if (task.split(" ")[0].equals("todo")) {
                    String[] taskDescription = task.split("todo");
                    if (taskDescription.length == 0) {
                        throw new KaiMException("The description of a todo cannot be empty.");
                    }
                    tasks[taskCount] = new Todo(taskDescription[1].trim());
                    pw.println(" Got it. I've added this task:");
                    pw.println("   " + tasks[taskCount]);
                    taskCount++;
                    pw.println(" Now you have " + taskCount + " tasks in the list.");
                    
                } else if (task.split(" ")[0].equals("deadline")) {
                    String[] parts = task.split("/by");
                    String[] taskDescription = parts[0].split("deadline");
                    tasks[taskCount] = new Deadline(taskDescription[1].trim(), parts[1].trim());
                    pw.println(" Got it. I've added this task:");
                    pw.println("   " + tasks[taskCount]);
                    taskCount++;
                    pw.println(" Now you have " + taskCount + " tasks in the list.");
                    

                } else if (task.split(" ")[0].equals("event")) {
                    String[] parts = task.split("/from");
                    String[] taskDescription = parts[0].split("event");
                    String[] taskTime = parts[1].split("/to");
                    tasks[taskCount] = new Event(taskDescription[1].trim(), taskTime[0].trim(), taskTime[1].trim());
                    pw.println(" Got it. I've added this task:");
                    pw.println("   " + tasks[taskCount]);
                    taskCount++;
                    pw.println(" Now you have " + taskCount + " tasks in the list.");

                } else {
                    if (task.split(" ").length == 1) {
                        throw new KaiMException("I'm sorry, unknown input please try again");
                    }
                    tasks[taskCount] = new Task(task);
                    pw.println(" added: " + task);
                    taskCount++;

                }
            } catch (KaiMException e) {
                pw.println(e.getMessage());
            }
        }

        pw.close();
    }
}