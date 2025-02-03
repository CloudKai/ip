public class KaiM {
    
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public KaiM(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.loadTasks());
        } catch (KaiMException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.showWelcomeMessage();
        Parser parser = new Parser();

        while (true) {
            String input = ui.readCommand();
            try {
                if (parser.isExitCommand(input)) {
                    ui.showExitMessage();
                    break;
                }
                String response = parser.handleCommand(input, tasks);
                ui.showMessage(response);
                storage.saveTasks(tasks);
            } catch (KaiMException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new KaiM("data/KaiM.txt").run();
    }
}
