package denz.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

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
     * @param taskNumber One-based index of the task to retrieve.
     * @return The {@link Task} at the specified index.
     * @throws DenzException If the index is out of range.
     */
    public Task get(int taskNumber) throws DenzException {
        if (taskNumber <= 0 || taskNumber > tasks.size()) {
            throw new DenzException("Invalid number, unable to get task!");
        }
        return tasks.get(taskNumber - 1);
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
     * @param taskNumber One-based index of the task to delete.
     * @return The removed {@link Task}.
     * @throws DeleteException If the index is invalid.
     */
    public Task delete(int taskNumber) throws DeleteException {
        int idx = taskNumber - 1;
        if (idx < 0 || idx >= tasks.size()) {
            throw new DeleteException("invalid task number!!");
        }
        return tasks.remove(idx);
    }

    /**
     * Marks the task at the given one-based index as done.
     *
     * @param taskNumber One-based index of the task to mark.
     * @throws MarkException If the index is invalid or the task is already marked as done.
     */
    public void mark(int taskNumber) throws MarkException {
        int idx = taskNumber - 1;
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
     * @param taskNumber One-based index of the task to unmark.
     * @throws MarkException If the index is invalid or the task is not marked.
     */
    public void unmark(int taskNumber) throws MarkException {
        int idx = taskNumber - 1;
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
    public String displayList() {
        StringBuilder sb = new StringBuilder("Here is everything you have on your plate :(");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append("\n").append(i + 1).append(". ").append(tasks.get(i));
        }
        return sb.toString();
    }

    /**
     * Renders a numbered list for found tasks.
     *
     * @param matches Tasklist to be represented
     * @return String representation of tasklist provided
     */
    public String displayList(List<Task> matches) {
        StringBuilder sb = new StringBuilder("Here are the matching tasks in your list:");
        for (int i = 0; i < matches.size(); i++) {
            sb.append("\n").append(i + 1).append(". ").append(matches.get(i));
        }
        return sb.toString();
    }

    /**
     * Finds a list of task based on a given keyword.
     *
     * @param keywords Keywords to find task by
     * @return A list of task with that given keyword
     */
    public List<Task> find(String ... keywords) {
        return Stream.of(keywords).map(word -> word.toLowerCase()) //convert each keyword into lowercase
                .flatMap(word -> tasks.stream() //for each word, we return a stream of tasks
                        .filter(t -> t.getDescription() //filtered by description
                                .toLowerCase()
                                .contains(word)))
                .distinct().toList();
    }

    /**
     * Returns a list of tasks that fall within the specified reminder window.
     * <p>
     * Iterates over all tasks in the task list and selects:
     * <ul>
     *     <li>{@link denz.model.Deadline} tasks whose due dates are within
     *     the next {@code limit} days.</li>
     *     <li>{@link denz.model.Event} tasks whose start dates are within
     *     the next {@code limit} days.</li>
     * </ul>
     * Other task types (e.g., {@link denz.model.Todo}) are ignored since
     * they do not have a date associated with them.
     * <p>
     * The comparison is done relative to the current system time
     * ({@link java.time.LocalDateTime#now()}).
     *
     * @param limit the number of days ahead to check for deadlines or events
     * @return a list of tasks with dates falling within the reminder window;
     *         the list is empty if no such tasks exist
     */
    public List<Task> remind(int limit) {
        List<Task> output = new ArrayList<>();
        LocalDateTime current = LocalDateTime.now();
        for (Task t : tasks) {
            if (t instanceof Deadline) {
                Deadline deadlineTask = (Deadline) t;
                LocalDateTime taskDueDate = deadlineTask.getDueDate();
                if (taskDueDate.isBefore(current.plusDays(limit))) {
                    output.add(t);
                }
            }
            if (t instanceof Event) {
                Event eventTask = (Event) t;
                LocalDateTime taskStartDate = eventTask.getStartDate();
                if (taskStartDate.isBefore(current.plusDays(limit))) {
                    output.add(t);
                }
            }
        }
        return output;
    }
}
