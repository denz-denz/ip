package denz.model;

import denz.app.Denz;
import denz.exception.DenzException;
import denz.exception.MarkException;
import denz.exception.DeleteException;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;
    public TaskList() {
        this.tasks = new ArrayList<>();
    }
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }
    public int size() {
        return tasks.size();
    }
    public Task get(int oneBased) throws DenzException {
        if (oneBased <= 0 || oneBased > tasks.size()) {
            throw new DenzException("Invalid number, unable to get task!");
        }
        return tasks.get(oneBased - 1);
    }

    public ArrayList<Task> getList() {
        return this.tasks;
    }
    public void add(Task t) {
        tasks.add(t);
    }

    public Task delete(int oneBased) throws DeleteException {
        int idx = oneBased - 1;
        if ( idx < 0 || idx >= tasks.size()) {
            throw new DeleteException("invalid task number!!");
        }
        return tasks.remove(idx);
    }

    public void mark(int oneBased) throws MarkException {
        int idx = oneBased - 1;
        if (idx < 0 || idx >= tasks.size()){
            throw new MarkException("invalid task number!!");
        }
        Task t = tasks.get(idx);
        if (t.isDone()) {
            throw new MarkException("the task is already completed!");
        }
        t.mark();
    }

    public void unmark(int oneBased) throws MarkException {
        int idx = oneBased - 1;
        if (idx < 0 || idx >= tasks.size()) {
            throw new MarkException("Invalid task number!!");
        }
        Task t = tasks.get(idx);
        if (!t.isDone()) throw new MarkException("the task is not even completed!");
        t.unmark();
    }

    public String render() {
        StringBuilder sb = new StringBuilder("Here is everything you have on your plate :(");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append("\n").append(i + 1).append(". ").append(tasks.get(i));
        }
        return sb.toString();
    }
}
