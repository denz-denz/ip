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
        String logo = " ____  _____ _   _ _____ \n"
                + "|  _ \\| ____| \\ | |__  / \n"
                + "| | | |  _| |  \\| | / / \n"
                + "| |_| | |___| |\\  |/ /_| \n"
                + "|____/|_____|_| \\_|____|\n";
        String newLine = "--------------------------------------------------";
        ArrayList<Task> userInputs = new ArrayList<>();
        System.out.println("Hello from\n" + logo);
        System.out.println(newLine);
        System.out.println("Hello, I'm Denz");
        System.out.println("What can I do for you?");
        System.out.println(newLine);
        while (sc.hasNextLine()){
            String line = sc.nextLine().trim();
            String[] parts = line.trim().split("\\s+");
            if (parts[0].equalsIgnoreCase("mark") && parts.length == 2){
                if (!isInteger(parts[1])){
                    System.out.println("added: " + newLine);
                    System.out.println("    " + line);
                    System.out.println(newLine);
                    userInputs.add(new Task(line));
                }
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
                if (!isInteger(parts[1])){
                    System.out.println("added: " + newLine);
                    System.out.println("    " + line);
                    System.out.println(newLine);
                    userInputs.add(new Task(line));
                }
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
            else if (line.equalsIgnoreCase("bye")){
                System.out.println(newLine);
                System.out.println("    Finally, time to take a break!");
                System.out.println(newLine);
                System.exit(0);
            }
            else if (line.equalsIgnoreCase("list")){
                for (int i = 0; i < userInputs.size(); i ++){
                    System.out.println(i+1 + ": " + userInputs.get(i));
                }
            }
            else if (line.equals("")){
                continue;
            }


            else{
                System.out.println("added: " + newLine);
                System.out.println("    " + line);
                System.out.println(newLine);
                userInputs.add(new Task(line));
            }

        }
        sc.close();
    }
}
