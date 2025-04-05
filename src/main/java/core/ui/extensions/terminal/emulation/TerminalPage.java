package core.ui.extensions.terminal.emulation;

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
    private static Path currentDirectory = Paths.get("").toAbsolutePath();
    private String[][] commands = {
            {"Enter command", "ec"},
            {"Restart", "rs"},
            {"Clear terminal", "cl"},
            {"Help", "h"},
            {"Quit", "q"}
    };

    public void displayMenu() {
        marginBorder(1, 2);
        message("Terminal:", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        displayListOfCommands(commands);

        while (true) {
            slowMotionText(getDefaultDelay(), getSearchingLineAlignment(), false,
                    getColor(layoutColor) + searchingArrow, "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "enter command", "ec" -> {
                    insertControlChars('n', 1);
                    executeCommand();
                }
                case "restart", "rst" -> {
                    insertControlChars('n', 1);
                    mainMenuRerun();
                }
                case "clear", "cl" -> clearTerminal();
                case "help", "h" -> displayListOfCommands(commands);
                case "quit", "q", "exit", "e" -> {
                    exitPage();
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
                out.print(alignment(getDefaultTextAlignment()) + getBackColor(33) + getColor(layoutColor)
                        + "Enter command [or q to quit]:" + RESET + getColor(layoutColor) + " ");
                String input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("q")) {
                    insertControlChars('n', 1);
                    return;
                }

                if (input.equals("nano")) {
                    openNano();
                } else {
                    executeTerminalCommandsModified(input);
                }

            } catch (Exception e) {
                message(getBackColor(rejectionColor) + "Error: " + e.getMessage() + "." + RESET,
                        layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
            }
        }
    }

    private static void openNano() {
        try {
            String os = System.getProperty("os.name").toLowerCase();

            if (os.contains("mac")) {
                new ProcessBuilder("open", "-a", "Terminal", "nano").start();
            } else if (os.contains("linux")) {
                new ProcessBuilder("x-terminal-emulator", "-e", "nano").start();
            } else {
                message(getBackColor(rejectionColor) + "Unsupported OS for nano." + RESET,
                        layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
            }

        } catch (IOException e) {
            message(getBackColor(rejectionColor) + "Failed to open nano: " + e.getMessage() + "." + RESET,
                    layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
        }
    }

    private static void executeTerminalCommandsModified(@NotNull String command) {
        try {
            String[] commands = command.split(" ");

            if (commands[0].equals("nano")) {
                blockNano();
                return;
            }

            if (commands[0].equals("cd")) {
                if (commands.length > 1) {
                    Path newPath = currentDirectory.resolve(commands[1]).normalize();
                    if (newPath.toFile().exists() && newPath.toFile().isDirectory()) {
                        currentDirectory = newPath;
                        message(getBackColor(214) + "Directory changed to: " + currentDirectory + "." + RESET,
                                layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
                    } else {
                        message(getBackColor(rejectionColor) + "No such directory: " + commands[1] + "." + RESET,
                                layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
                    }
                } else {
                    message(getBackColor(45) + "Usage: cd <directory>" + RESET, layoutColor,
                            getDefaultTextAlignment(), getDefaultDelay(), out::println);
                }
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
                            layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
                }
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                message(getBackColor(rejectionColor) + "Command failed with exit code: " + exitCode + "." + RESET,
                        layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
            } else {
                insertControlChars('n', 1);
                message(getBackColor(34) + "Process completed successfully." + RESET,
                        layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
            }

        } catch (IOException e) {
            message(getBackColor(rejectionColor) + "I/O Error while executing command: " + e.getMessage() + "." + RESET,
                    layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
        } catch (InterruptedException e) {
            message(getBackColor(rejectionColor) + "Process was interrupted: " + e.getMessage() + "." + RESET,
                    layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
            Thread.currentThread().interrupt();
        }
    }

    private static void blockNano() {
        message(getBackColor(rejectionColor) +
                        "Sorry, but this terminal is intended for command execution only and does not support file editing via nano." + RESET,
                layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
    }


    private static void changeDirectory(String newPath) {
        Path newDir = currentDirectory.resolve(newPath).normalize();
        if (newDir.toFile().exists() && newDir.toFile().isDirectory()) {
            currentDirectory = newDir;
            message(getBackColor(214) + "Directory changed to: " + currentDirectory + "." + RESET,
                    layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
        } else {
            message(getBackColor(rejectionColor) + "No such directory: " + newPath + "." + RESET,
                    layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
        }
    }
}
