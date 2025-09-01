package denz.model;

import java.util.ArrayList;
import java.util.List;

import denz.exception.DeleteException;
import denz.exception.DenzException;
import denz.exception.MarkException;

/**
 * Represents a list of {@link Task} objects.
 * Provides methods to add, retrieve, delete, mark, unmark, and render tasks.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Creates an empty {@code TaskList}.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a {@code TaskList} initialized with a given list of tasks.
     *
     * @param tasks List of tasks to initialize this {@code TaskList} with.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return Number of tasks.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Retrieves the task at the given one-based index.
     *
     * @param oneBased One-based index of the task to retrieve.
     * @return The {@link Task} at the specified index.
     * @throws DenzException If the index is out of range.
     */
    public Task get(int oneBased) throws DenzException {
        if (oneBased <= 0 || oneBased > tasks.size()) {
            throw new DenzException("Invalid number, unable to get task!");
        }
        return tasks.get(oneBased - 1);
    }

    /**
     * Returns the underlying list of tasks.
     *
     * @return List of tasks.
     */
    public ArrayList<Task> getList() {
        return this.tasks;
    }

    /**
     * Adds a new task to the list.
     *
     * @param t The task to add.
     */
    public void add(Task t) {
        tasks.add(t);
    }

    /**
     * Deletes and returns the task at the given one-based index.
     *
     * @param oneBased One-based index of the task to delete.
     * @return The removed {@link Task}.
     * @throws DeleteException If the index is invalid.
     */
    public Task delete(int oneBased) throws DeleteException {
        int idx = oneBased - 1;
        if (idx < 0 || idx >= tasks.size()) {
            throw new DeleteException("invalid task number!!");
        }
        return tasks.remove(idx);
    }

    /**
     * Marks the task at the given one-based index as done.
     *
     * @param oneBased One-based index of the task to mark.
     * @throws MarkException If the index is invalid or the task is already marked as done.
     */
    public void mark(int oneBased) throws MarkException {
        int idx = oneBased - 1;
        if (idx < 0 || idx >= tasks.size()) {
            throw new MarkException("invalid task number!!");
        }
        Task t = tasks.get(idx);
        if (t.isDone()) {
            throw new MarkException("the task is already completed!");
        }
        t.mark();
    }

    /**
     * Unmarks the task at the given one-based index (sets it to not done).
     *
     * @param oneBased One-based index of the task to unmark.
     * @throws MarkException If the index is invalid or the task is not marked.
     */
    public void unmark(int oneBased) throws MarkException {
        int idx = oneBased - 1;
        if (idx < 0 || idx >= tasks.size()) {
            throw new MarkException("Invalid task number!!");
        }
        Task t = tasks.get(idx);
        if (!t.isDone()) {
            throw new MarkException("the task is not even completed!");
        }
        t.unmark();
    }

    /**
     * Renders the task list as a formatted string, showing task numbers and descriptions.
     *
     * @return A string representation of all tasks in the list.
     */
    public String render() {
        StringBuilder sb = new StringBuilder("Here is everything you have on your plate :(");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append("\n").append(i + 1).append(". ").append(tasks.get(i));
        }
        return sb.toString();
    }

    /**
     * Renders a numbered list for found tasks.
     * @param matches Tasklist to be represented
     * @return String representation of tasklist provided
     * */
    public String render(List<Task> matches) {
        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:");
        for (int i = 0; i < matches.size(); i++) {
            sb.append("\n").append(i + 1).append(". ").append(matches.get(i));
        }
        return sb.toString();
    }

    /**
     * Finds a list of task based on a given keyword.
     *
     * @param keyword Keyword to find task by
     * @return A list of task with that given keyword
     */
    public List<Task> find(String keyword) {
        List<Task> out = new ArrayList<>();
        String k = keyword.toLowerCase();
        for (Task t : tasks) {
            if (t.getDescription().toLowerCase().contains(k)) {
                out.add(t);
            }
        }
        return out;
    }
}
