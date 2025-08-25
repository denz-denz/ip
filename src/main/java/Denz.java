import java.util.Scanner;
import java.util.ArrayList;
import java.nio.file.Paths;
public class Denz {
    static String newLine = "--------------------------------------------------";
    //check if a string is a valid integer
    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private static void greet() {
        String logo = " ____  _____ _   _ _____\n"
                + "|  _ \\| ____| \\ | |__  /\n"
                + "| | | |  _| |  \\| | / /\n"
                + "| |_| | |___| |\\  |/ /_|\n"
                + "|____/|_____|_| \\_|____|\n";
        System.out.println("Hello from\n" + logo);
        System.out.println(newLine);
        System.out.println("Hello, I'm Denz");
        System.out.println("What can I do for you?");
        System.out.println(newLine);
    }
    private static void bye(String line) throws ByeException {
        if (!line.startsWith("bye")) {
            throw new ByeException("invalid command to exit");
        }
        else if (!line.substring(3).isEmpty()) {
            throw new ByeException("just say bye, dont add anything after bye");
        }
        else{
            System.out.println(newLine);
            System.out.println("    Finally, time to take a break!");
            System.out.println(newLine);
            System.exit(0);
        }
    }
    private static void printList(ArrayList<Task> arr){
        System.out.println("Here is everything you have on your plate :(");
        for (int i = 0; i < arr.size(); i ++){
            System.out.println(i+1 + ". " + arr.get(i));
        }
    }
    private static void addTask(ArrayList<Task> arr, String command) throws AddException{
        Task t = new Task("");
        if (command.startsWith("todo")){
            String description = command.substring(4).trim();
            if (description.isEmpty()) {
                throw new AddException("wthelly, you're trolling me right. You are missing the task description");
            }
            t = new Todo(description);
        }
        else if (command.startsWith("deadline")){
            String fullInput = command.substring(8).trim();
            String[] segment = fullInput.split("/by", 2);
            String description = segment[0].trim();
            if (description.isEmpty()) {
                throw new AddException("wthelly, you're trolling me right. You are missing the task description");
            }
            String dueDate = segment[1].trim();
            t = new Deadline(description, dueDate);
        }
        else if (command.startsWith("event")){
            String fullInput = command.substring(5).trim();
            String[] firstSplit = fullInput.split("/from",2);
            String description = firstSplit[0].trim();
            if (description.isEmpty()) {
                throw new AddException("wthelly, you're trolling me right. You are missing the task description");
            }
            String[] secondSplit = firstSplit[1].split("/to",2);
            String startDate = secondSplit[0].trim();
            String endDate = secondSplit[1].trim();
            t = new Event(description, startDate, endDate);
        }
        else {
            throw new AddException("I do not have a clue what you want me to add");
        }
        arr.add(t);
        System.out.println("Roger! I have added this task:");
        System.out.println("    " + t);
        System.out.println("Now you have " + arr.size() + " tasks in the list.");
    }

    private static void markTask(ArrayList<Task> arr, int index) throws MarkException {
        if (index < 0 || index >= arr.size()){
            throw new MarkException("invalid task number!!");
        }
        else if (arr.get(index).isDone()) {
            throw new MarkException("the task is already completed!");
        }
        else {
            Task task = arr.get(index);
            task.mark();
            System.out.println("Yay! I have successfully marked this task as done:");
            System.out.println(task);
        }
    }
    private static void unmarkTask(ArrayList<Task> arr, int index) throws MarkException {
        if (index < 0 || index >= arr.size()){
            throw new MarkException("Invalid task number!!");
        }
        else if (!arr.get(index).isDone()) {
            throw new MarkException("the task is not even completed!");
        }
        else {
            Task task = arr.get(index);
            task.unmark();
            System.out.println("Yay! I have successfully unmarked this task as done:");
            System.out.println(task);
        }
    }
    private static void deleteTask(ArrayList<Task> arr, int index) throws DeleteException {
        if (index < 0 || index >= arr.size()) {
            throw new DeleteException("invalid task number!!");
        }
        else {
            Task t = arr.get(index);
            System.out.println("Alright slacker, i have removed this task:");
            System.out.println(t);
            arr.remove(index);
            System.out.println("Now you have " + arr.size() + " tasks in the list");
        }
    }
    public static void main(String[] args) {
        Storage storage = new Storage("./data/denz.txt");
        ArrayList<Task> tasks = storage.load();
        Scanner sc = new Scanner(System.in);
        greet();
        while (sc.hasNextLine()){
            String line = sc.nextLine().trim(); //trim all white spaces
            String[] parts = line.trim().split("\\s+");//split by white spaces
            if (parts[0].equalsIgnoreCase("delete") && parts.length == 2) {
                try {
                    int index = Integer.parseInt(parts[1]);
                    deleteTask(tasks, index-1);
                    storage.save(tasks);
                } catch (NumberFormatException e) {
                    System.out.println("invalid task index to delete");
                } catch (DeleteException d) {
                    System.out.println(d.getMessage());
                }
            }
            else if (parts[0].equalsIgnoreCase("mark") && parts.length == 2){
                try {
                    int index = Integer.parseInt(parts[1]);
                    markTask(tasks, index-1);
                    storage.save(tasks);
                } catch (NumberFormatException e){
                    System.out.println("invalid task index to mark");
                } catch (MarkException m) {
                    System.out.println(m.getMessage());
                }
            }
            else if (parts[0].equalsIgnoreCase("unmark") && parts.length == 2){
                try {
                    int index = Integer.parseInt(parts[1]);
                    unmarkTask(tasks, index-1);
                    storage.save(tasks);
                } catch (NumberFormatException e){
                    System.out.println("invalid task index to mark");
                } catch (MarkException m){
                    System.out.println(m.getMessage());
                }
            }
            //exit chatbot
            else if (line.startsWith("bye")){
                try {
                    bye(line);
                } catch (ByeException b){
                    System.out.println(b.getMessage());
                }
            }
            //display list
            else if (line.equalsIgnoreCase("list")){
                printList(tasks);
            }
            //ignore if no words
            else if (line.equals("")){
                continue;
            }
            //add task to list
            else{
                try{
                    addTask(tasks, line);
                    storage.save(tasks);
                } catch (AddException a) {
                    System.out.println(a.getMessage());
                }
            }
        }
        sc.close();
    }
}
