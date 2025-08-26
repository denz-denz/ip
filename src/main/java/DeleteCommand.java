public class DeleteCommand extends Command {
    private final int oneBased;
    public DeleteCommand(int oneBased) { this.oneBased = oneBased; }
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DenzException {
        Task removed = tasks.delete(oneBased);      // may throw DeleteException
        ui.showRemoved(removed, tasks.size());
        storage.save(tasks);
    }
}
