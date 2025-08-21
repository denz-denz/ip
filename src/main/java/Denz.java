import java.util.Scanner;
import java.util.ArrayList;
public class Denz {
    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //String formatting
        String logo = " ____  _____ _   _ _____ \n"
                + "|  _ \\| ____| \\ | |__  / \n"
                + "| | | |  _| |  \\| | / / \n"
                + "| |_| | |___| |\\  |/ /_| \n"
                + "|____/|_____|_| \\_|____|\n";
        String newLine = "--------------------------------------------------";
        System.out.println("Hello from\n" + logo);
        System.out.println(newLine);
        System.out.println("Hello, I'm Denz");
        System.out.println("What can I do for you?");
        System.out.println(newLine);
        //real code starts here
        ArrayList<Task> userInputs = new ArrayList<>();
        while (sc.hasNextLine()){
            String line = sc.nextLine().trim(); //trim all white spaces
            String[] parts = line.trim().split("\\s+"); //split by white spaces
            if (parts[0].equalsIgnoreCase("mark") && parts.length == 2){
                //if task is like mark xxxx, we add as a real task
                if (!isInteger(parts[1])){
                    System.out.println("added: " + newLine);
                    System.out.println("    " + line);
                    System.out.println(newLine);
                    userInputs.add(new Task(line));
                }
                //mark by index as done
                else{
                    try {
                        int index = Integer.parseInt(parts[1]);
                        Task task = userInputs.get(index-1);
                        task.mark();
                        System.out.println("Yay! I have successfully marked this task as done:");
                        System.out.println(task);
                    }
                    catch (NumberFormatException e){
                        System.out.println("invalid task index to mark");
                    }
                }
            }
            else if (parts[0].equalsIgnoreCase("unmark") && parts.length == 2){
                //if task is like unmark xxxx, we add as real task
                if (!isInteger(parts[1])){
                    System.out.println("added: " + newLine);
                    System.out.println("    " + line);
                    System.out.println(newLine);
                    userInputs.add(new Task(line));
                }
                //unmark by index
                else {
                    try {
                        int index = Integer.parseInt(parts[1]);
                        Task task = userInputs.get(index-1);
                        task.unmark();
                        System.out.println("Aw man! Okay I have helped unmark this task. Hang in there:");
                        System.out.println(task);
                    }
                    catch (NumberFormatException e){
                        System.out.println("invalid task index to mark");
                    }
                }
            }
            //exit chatbot
            else if (line.equalsIgnoreCase("bye")){
                System.out.println(newLine);
                System.out.println("    Finally, time to take a break!");
                System.out.println(newLine);
                System.exit(0);
            }
            //display list
            else if (line.equalsIgnoreCase("list")){
                System.out.println("Here is everything you have on your plate :(");
                for (int i = 0; i < userInputs.size(); i ++){
                    System.out.println(i+1 + ". " + userInputs.get(i));
                }
            }
            //ignore if no words
            else if (line.equals("")){
                continue;
            }
            //add task to list
            else{
                Task t = new Task("");
                if (line.startsWith("todo")){
                    String description = line.substring(4).trim();
                    t = new Todo(description);
                }
                else if (line.startsWith("deadline")){
                    String fullInput = line.substring(8).trim();
                    String[] segment = fullInput.split("/by", 2);
                    String description = segment[0].trim();
                    String dueDate = segment[1].trim();
                    t = new Deadline(description, dueDate);
                }
                else if (line.startsWith("event")){
                    String fullInput = line.substring(5).trim();
                    String[] firstSplit = fullInput.split("/from",2);
                    String description = firstSplit[0].trim();
                    String[] secondSplit = firstSplit[1].split("/to",2);
                    String startDate = secondSplit[0].trim();
                    String endDate = secondSplit[1].trim();
                    t = new Event(description, startDate, endDate);
                }
                System.out.println("Roger! I have added this task: " + newLine);
                System.out.println("    " + t);
                System.out.println(newLine);
                userInputs.add(t);
                System.out.println("Oh NO!!!! Now you have " + userInputs.size() + " tasks in the list");
            }

        }
        sc.close();
    }
}
