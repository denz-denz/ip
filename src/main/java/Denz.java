import java.util.Scanner;
import java.util.ArrayList;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
public class Denz {
    static String newLine = "--------------------------------------------------";
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
    private static void addTask(TaskList arr, String command) throws AddException{
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
            String dueDateString = segment[1].trim();
            LocalDateTime dueDate = DateTimeUtil.parse(dueDateString);
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
            String startDateString = secondSplit[0].trim();
            String endDateString = secondSplit[1].trim();
            LocalDateTime startDate = DateTimeUtil.parse(startDateString);
            LocalDateTime endDate = DateTimeUtil.parse(endDateString);
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

    public static void main(String[] args) {
        Storage storage = new Storage("./data/denz.txt");
        TaskList tasks = storage.load();
        Ui ui = new Ui();
        ui.showWelcome();
        //Scanner sc = new Scanner(System.in);
        greet();
        while (true) {
            String line = ui.readCommand();
            if (line.isEmpty()) {
                continue;
            }
            String cmd = Parser.commandWord(line);
            String rest = Parser.rest(line);

            if (cmd.equals("delete")) {
                try {
                    int index = Parser.parseDelete(rest);
                    Task removed = tasks.delete(index);
                    ui.show("Alright slacker, i have removed this task:");
                    ui.show(removed.toString());
                    ui.show("Now you have " + tasks.size() + " tasks in the list");
                    storage.save(tasks);
                } catch (DeleteException e) {
                    ui.showError(e.getMessage());
                } catch (IndexException e) {
                    ui.showError(e.getMessage());
                }
            } else if (cmd.equals("mark")) {
                try {
                    int index = Parser.parseMark(rest);
                    tasks.mark(index);
                    ui.showMark(tasks.get(index - 1));
                    storage.save(tasks);
                } catch (MarkException m) {
                    ui.showError(m.getMessage());
                } catch (IndexException e) {
                    ui.showError(e.getMessage());
                } catch (NumberFormatException e) {
                    ui.showError("invalid task index to mark");
                }
            } else if (cmd.equals("unmark")) {
                try {
                    int index = Parser.parseUnmark(rest);
                    tasks.unmark(index);
                    ui.showUnmark(tasks.get(index - 1));
                    storage.save(tasks);
                } catch (NumberFormatException e) {
                    ui.showError("invalid task index to mark");
                } catch (IndexException i) {
                    ui.showError(i.getMessage());
                } catch (MarkException m) {
                    ui.showError(m.getMessage());
                }
            }
            //exit chatbot
            else if (cmd.equals("bye")) {
                try {
                    Parser.validateBye(line);
                    ui.showBye();
                    ui.close();
                    return;
                } catch (ByeException b) {
                    ui.showError(b.getMessage());
                }
            }
            //display list
            else if (line.equalsIgnoreCase("list")) {
                ui.show(tasks.render());
            }

            //add task to list
            else {
                try {
                    Task t = Parser.parseAdd(line);
                    tasks.add(t);
                    ui.showTaskAdded(t, tasks.size());
                    storage.save(tasks);
                } catch (AddException a) {
                    System.out.println(a.getMessage());
                }
            }
        }
    }

}

