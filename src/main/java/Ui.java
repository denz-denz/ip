import java.util.Scanner;
public class Ui {
    private final Scanner sc = new Scanner(System.in);
    private static final String LINE = "--------------------------------------------------";
    private static final String LOGO =
              " ____  _____ _   _ _____\n" +
              "|  _ \\| ____| \\ | |__  /\n" +
              "| | | |  _| |  \\| | / /\n" +
              "| |_| | |___| |\\  |/ /_|\n" +
              "|____/|_____|_| \\_|____|\n";
    public void showWelcome() {
        System.out.println("Hello from\n" + LOGO);
        System.out.println(LINE);
        System.out.println("Hello, I'm Denz");
        System.out.println("What can I do for you?");
        System.out.println(LINE);
    }

    public String readCommand() {
        return sc.hasNextLine() ? sc.nextLine().trim() : "bye";
    }

    public void showLine() {
        System.out.println(LINE);
    }

    public void show(String msg) {
        System.out.println(msg);
    }

    public void showError(String msg) {
        System.out.println(msg);
    } // keep exact wording for tests

    public void showList(String renderedList) {
        System.out.println(renderedList);
    }

    public void showTaskAdded(Task t, int size) {
        System.out.println("Roger! I have added this task:");
        System.out.println("    " + t);
        System.out.println("Now you have " + size + " tasks in the list.");
    }

    public void showRemoved(Task removed, int size) {
        System.out.println("Alright slacker, i have removed this task:");
        System.out.println(removed);
        System.out.println("Now you have " + size + " tasks in the list");
    }

    public void showMark(Task t) {
        System.out.println("Yay! I have successfully marked this task as done:");
        System.out.println(t);
    }

    public void showUnmark(Task t) {
        System.out.println("Yay! I have successfully unmarked this task as done:");
        System.out.println(t);
    }

    public void showBye() {
        System.out.println(LINE);
        System.out.println("    Finally, time to take a break!");
        System.out.println(LINE);
    }

    public void close() {
        sc.close();
    }
}
