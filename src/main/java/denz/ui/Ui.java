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

    public String showGui(String msg) {
        return msg;
    }

    /**
     * Displays an error message.
     *
     * @param error the error message to show
     */
    public void showError(String error) {
        System.out.println(error);
    }

    public String showErrorGui(String error) {
        return error;
    }

    /**
     * Displays a rendered list of tasks for CLI.
     *
     * @param renderedList the string representation of tasks
     */
    public void showList(String renderedList) {
        System.out.println(renderedList);
    }

    /**
     * Displays a rendered list of tasks for GUI.
     *
     * @param renderedList the string representation of tasks
     */
    public String showListGui(String renderedList) {
        return renderedList;
    }

    /**
     * Displays confirmation after a task has been added for CLI.
     *
     * @param t    the task that was added
     * @param size the new size of the task list
     */
    public void showTaskAdded(Task t, int size) {
        System.out.println("Roger! I have added this task:");
        System.out.println(" " + t);
        System.out.println("Now you have " + size + " tasks in the list.");
    }

    /**
     * Displays confirmation after a task has been added for GUI.
     *
     * @param t    the task that was added
     * @param size the new size of the task list
     */
    public String showTaskAddedGui(Task t, int size) {
        return "Roger! I have added this task:\n"
                + " "
                + t.toString()
                + "\n"
                + "Now you have "
                + size
                + " tasks in the list.";
    }

    /**
     * Displays confirmation after a task has been removed for CLI.
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
     * Displays confirmation after a task has been removed for GUI.
     *
     * @param removed the task that was removed
     * @param size    the new size of the task list
     */
    public String showRemovedGui(Task removed, int size) {
        return "Alright slacker, i have removed this task:\n"
                + removed
                + "\n"
                + "Now you have "
                + size
                + " tasks in the list";
    }

    /**
     * Displays confirmation after a task has been marked as done for CLI.
     *
     * @param t the task that was marked
     */
    public void showMark(Task t) {
        System.out.println("Yay! I have successfully marked this task as done:");
        System.out.println(t);
    }

    /**
     * Displays confirmation after a task has been marked as done for GUI.
     *
     * @param t the task that was marked
     */
    public String showMarkGui(Task t) {
        return "Yay! I have successfully marked this task as done:\n"
                + t;
    }

    /**
     * Displays confirmation after a task has been unmarked for CLI.
     *
     * @param t the task that was unmarked
     */
    public void showUnmark(Task t) {
        System.out.println("Yay! I have successfully unmarked this task as done:");
        System.out.println(t);
    }

    /**
     * Displays confirmation after a task has been unmarked for GUI.
     *
     * @param t the task that was unmarked
     */
    public String showUnmarkGui(Task t) {
        return "Yay! I have successfully unmarked this task as done:\n"
                + t;
    }

    /**
     * Displays the farewell message when exiting the program for CLI.
     */
    public void showBye() {
        System.out.println(LINE);
        System.out.println("Finally, time to take a break!");
        System.out.println(LINE);
    }

    /**
     * Displays the farewell message when exiting the program for GUI.
     */
    public String showByeGui() {
        return LINE
                + "\n"
                + "Finally, time to take a break!"
                + "\n"
                + LINE;
    }

    /**
     * Closes the {@link Scanner} resource.
     */
    public void close() {
        sc.close();
    }
}
