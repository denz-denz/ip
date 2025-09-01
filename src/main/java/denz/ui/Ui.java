package denz.ui;

import java.util.Scanner;

import denz.model.Task;

/**
 * Handles all interactions with the user.
 * <p>
 * The {@code Ui} class provides methods to display messages,
 * read user input, and print formatted responses such as
 * task additions, deletions, marking, unmarking, errors, and exit messages.
 */
public class Ui {
    private static final String LINE = "--------------------------------------------------";
    private static final String LOGO =
                      " ____  _____ _   _ _____" + System.lineSeparator()
                    + "|  _ \\| ____| \\ | |__  /" + System.lineSeparator()
                    + "| | | |  _| |  \\| | / /" + System.lineSeparator()
                    + "| |_| | |___| |\\  |/ /_|" + System.lineSeparator()
                    + "|____/|_____|_| \\_|____|" + System.lineSeparator();

    private final Scanner sc = new Scanner(System.in);

    /**
     * Displays the welcome message and ASCII logo when the program starts.
     */
    public void showWelcome() {
        System.out.println("Hello from\n" + LOGO);
        System.out.println(LINE);
        System.out.println("Hello, I'm your dawg Denz");
        System.out.println("What can I do for you?");
        System.out.println(LINE);
    }

    /**
     * Reads the next line of user input.
     * If no line is available, returns {@code "bye"} to signal exit.
     *
     * @return the trimmed user input, or "bye" if no input is available
     */
    public String readCommand() {
        return sc.hasNextLine() ? sc.nextLine().trim() : "bye";
    }

    /**
     * Displays a separator line.
     */
    public void showLine() {
        System.out.println(LINE);
    }

    /**
     * Displays a given message.
     *
     * @param msg the message to show
     */
    public void show(String msg) {
        System.out.println(msg);
    }

    /**
     * Displays an error message.
     *
     * @param msg the error message to show
     */
    public void showError(String msg) {
        System.out.println(msg);
    }

    /**
     * Displays a rendered list of tasks.
     *
     * @param renderedList the string representation of tasks
     */
    public void showList(String renderedList) {
        System.out.println(renderedList);
    }

    /**
     * Displays confirmation after a task has been added.
     *
     * @param t    the task that was added
     * @param size the new size of the task list
     */
    public void showTaskAdded(Task t, int size) {
        System.out.println("Roger! I have added this task:");
        System.out.println("    " + t);
        System.out.println("Now you have " + size + " tasks in the list.");
    }

    /**
     * Displays confirmation after a task has been removed.
     *
     * @param removed the task that was removed
     * @param size    the new size of the task list
     */
    public void showRemoved(Task removed, int size) {
        System.out.println("Alright slacker, i have removed this task:");
        System.out.println(removed);
        System.out.println("Now you have " + size + " tasks in the list");
    }

    /**
     * Displays confirmation after a task has been marked as done.
     *
     * @param t the task that was marked
     */
    public void showMark(Task t) {
        System.out.println("Yay! I have successfully marked this task as done:");
        System.out.println(t);
    }

    /**
     * Displays confirmation after a task has been unmarked.
     *
     * @param t the task that was unmarked
     */
    public void showUnmark(Task t) {
        System.out.println("Yay! I have successfully unmarked this task as done:");
        System.out.println(t);
    }

    /**
     * Displays the farewell message when exiting the program.
     */
    public void showBye() {
        System.out.println(LINE);
        System.out.println("    Finally, time to take a break!");
        System.out.println(LINE);
    }

    /**
     * Closes the {@link Scanner} resource.
     */
    public void close() {
        sc.close();
    }
}
