package core.ui.extensions.terminal_emulation;

import core.ui.essential.pages.Page;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static core.ui.essential.configs.appearance.AppearanceConfigs.*;
import static core.ui.essential.configs.DisplayManager.clearTerminal;
import static core.logic.CommandManager.*;
import static core.ui.essential.configs.appearance.TextConfigs.*;
import static java.lang.System.out;

public class TerminalPage extends Page {

    private static final Scanner scanner = new Scanner(System.in);
    public static Path currentDirectory = Paths.get("").toAbsolutePath();
    private String[][] commands = {
            {"Enter command", "ec"},
            {"Restart", "rst"},
            {"Restart clear", "rcl"},
            {"Clear", "cl"},
            {"Help", "h"},
            {"Quit", "q"}
    };

    public void displayMenu() {
        marginBorder(1, 2);
        message("Terminal [Read-Only Mode]:", getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);
        displayListOfCommands(commands);

        while (true) {
            slowMotionText(getDefaultDelay(), getSearchingLineAlignment(), false,
                    getColor(getLayoutColor()) + getSearchingArrow(), "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "enter command", "ec" -> {
                    insertControlChars('n', 1);
                    executeCommand();
                }
                case "restart", "rst" -> {
                    insertControlChars('n', 1);
                    mainMenuRestart();
                }
                case "restart clear", "rcl" -> mainMenuRestartWithClearing();
                case "clear", "cl" -> clearTerminal();
                case "help", "h" -> displayListOfCommands(commands);
                case "quit", "q", "exit", "e" -> {
                    exitPage("You are in main menu");
                    return;
                }
                default -> out.print("");
            }
        }
    }

    @Override
    protected void displayListOfCommands(String[][] commands) {
        super.displayListOfCommands(commands);
    }

    private static void executeCommand() {
        while (true) {
            try {
                out.print(alignment(getDefaultTextAlignment()) + getBackColor(33) + getColor(getLayoutColor())
                        + "Enter command [or q to quit]:" + RESET + getColor(getLayoutColor()) + " ");
                String input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("q")) {
                    insertControlChars('n', 1);
                    return;
                }

                executeReadOnlyCommand(input);

            } catch (Exception e) {
                message(getBackColor(getRejectionColor()) + "Error: " + e.getMessage() + "." + RESET,
                        getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);
            }
        }
    }

    private static void executeReadOnlyCommand(@NotNull String command) {
        try {
            String[] commands = command.split(" ");

            if (isEditingCommand(commands[0])) {
                blockEditingCommand(commands[0]);
                return;
            }

            if (commands[0].equals("cd")) {
                if (commands.length > 1) {
                    changeDirectory(commands[1]);
                } else {
                    message(getBackColor(45) + "Usage: cd <directory>" + RESET, getLayoutColor(),
                            getDefaultTextAlignment(), getDefaultDelay(), out::println);
                }
                return;
            }

            if (isViewingCommand(commands[0])) {
                executeViewingCommand(commands);
                return;
            }

            ProcessBuilder processBuilder = new ProcessBuilder(commands);
            processBuilder.directory(currentDirectory.toFile());
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    message(getBackColor(67) + line + RESET,
                            getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);
                }
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                message(getBackColor(getRejectionColor()) + "Command failed with exit code: " + exitCode + "." + RESET,
                        getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);
            } else {
                insertControlChars('n', 1);
                message(getBackColor(34) + "Process completed successfully." + RESET,
                        getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);
            }

        } catch (IOException e) {
            message(getBackColor(getRejectionColor()) + "I/O Error while executing command: " + e.getMessage() + "." + RESET,
                    getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);
        } catch (InterruptedException e) {
            message(getBackColor(getRejectionColor()) + "Process was interrupted: " + e.getMessage() + "." + RESET,
                    getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);
            Thread.currentThread().interrupt();
        }
    }

    private static boolean isEditingCommand(String command) {
        String[] editingCommands = {"nano", "vim", "vi", "emacs", "gedit", "pico", "ed", "sed", "awk", ">>", ">"};
        for (String cmd : editingCommands) {
            if (command.equals(cmd)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isViewingCommand(String command) {
        String[] viewingCommands = {"cat", "less", "more", "head", "tail", "grep", "find", "ls", "dir"};
        for (String cmd : viewingCommands) {
            if (command.equals(cmd)) {
                return true;
            }
        }
        return false;
    }

    private static void executeViewingCommand(String[] commands) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(commands);
        processBuilder.directory(currentDirectory.toFile());
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                message(getBackColor(67) + line + RESET,
                        getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            message(getBackColor(getRejectionColor()) + "Command failed with exit code: " + exitCode + "." + RESET,
                    getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);
        }
    }

    private static void blockEditingCommand(String command) {
        message(getBackColor(getRejectionColor()) +
                        "Sorry, this terminal is in read-only mode and does not support the '" + command + "' command." + RESET,
                getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);
        message(getBackColor(33) + "You can only view files using commands like cat, less, more, etc." + RESET,
                getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);
    }

    private static void changeDirectory(String newPath) {
        Path newDir = currentDirectory.resolve(newPath).normalize();
        if (newDir.toFile().exists() && newDir.toFile().isDirectory()) {
            currentDirectory = newDir;
            message(getBackColor(214) + "Directory changed to: " + currentDirectory + "." + RESET,
                    getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);
        } else {
            message(getBackColor(getRejectionColor()) + "No such directory: " + newPath + "." + RESET,
                    getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);
        }
    }
}