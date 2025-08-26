public class UnmarkCommand extends Command {
    private final int oneBased;
    public UnmarkCommand(int oneBased) { this.oneBased = oneBased; }
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws MarkException {
        tasks.unmark(oneBased);
        ui.showUnmark(tasks.get(oneBased));
        storage.save(tasks);
    }
}
