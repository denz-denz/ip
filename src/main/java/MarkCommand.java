public class MarkCommand extends Command {
    private final int oneBased;
    public MarkCommand(int oneBased) { this.oneBased = oneBased; }
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws MarkException {
        tasks.mark(oneBased);                       // may throw MarkException
        ui.showMark(tasks.get(oneBased));
        storage.save(tasks);
    }
}
