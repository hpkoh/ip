import exceptions.InvalidInputException;

import commands.Command;

import tasks.TaskList;

import utils.Parser;
import utils.Storage;
import utils.Ui;

public class Duke {

    private Storage storage;
    private TaskList taskList;
    private Ui ui;

    public Duke(String filePath) {
        this.storage = new Storage(filePath);
        this.ui = new Ui();
        this.taskList = new TaskList(this.storage.load());

    }

    public static void main(String[] args) {
        new Duke("./data/duke.txt").run();
    }

    /**
     * Run Duke ChatBot
     */
    public void run() {
        ui.printWelcomeMessage();

        boolean isExitCommand = false;

        while (!isExitCommand) {
            try {
                String userCommand = ui.readCommand();
                Command command = Parser.parse(userCommand);
                isExitCommand = command.execute(this.taskList, this.ui, this.storage);
                this.storage.save(this.taskList.getFormattedData());

            } catch (InvalidInputException e) {
                ui.printError(e.getMessage());
            }
        }
        ui.printExitMessage();
    }
}
