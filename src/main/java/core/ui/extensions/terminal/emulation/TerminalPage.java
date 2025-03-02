package core.ui.extensions.terminal.emulation;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import static core.ui.essential.configs.AppearanceConfigs.*;
import static core.ui.essential.configs.DisplayManager.clearTerminal;
import static core.logic.CommandManager.*;
import static core.ui.essential.configs.TextConfigs.*;
import static core.ui.essential.pages.EasterEggPage.displayEasterEgg;
import static java.lang.System.out;

public class TerminalPage {

    private static Scanner scanner = new Scanner(System.in);
    private static Path currentDirectory = Paths.get("").toAbsolutePath();

    public static void displayTerminalPage() {
        marginBorder(1, 2);
        message("Terminal:", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        displayListOfCommands();

        while (true) {
            slowMotionText(getDefaultDelay(), getSearchingLineAlignment(), false,
                    getColor(layoutColor) + searchingArrow, "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "enter command", "/ec" -> {
                    insertControlChars('n',1);
                    executeCommand();
                }
                case "restart", "/rs" -> {
                    insertControlChars('n',1);
                    mainMenuRerun();
                }
                case "clear terminal", "/cl" -> clearTerminal();
                case "list", "/ls" -> displayListOfCommands();
                case "easteregg", "/ee" -> displayEasterEgg();
                case "quit", "/q" -> {
                    exitPage();
                    return;
                }
                default -> out.print("");
            }
        }
    }

    private static void displayListOfCommands(){
        insertControlChars('n',1);
        message("·  Enter command [" + getColor(mainColor)
                + "/ec" + getColor(layoutColor) + "]", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Restart [" + getColor(mainColor)
                + "/rs" + getColor(layoutColor) + "]", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Clear terminal [" + getColor(mainColor)
                + "/cl" + getColor(layoutColor) + "]", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  List [" + getColor(mainColor)
                + "/ls" + getColor(layoutColor) + "]", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Quit [" + getColor(mainColor)
                + "/q" + getColor(layoutColor) + "]", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
    }

    private static void executeCommand() {
        while (true) {
            try {
                out.print(alignment(getDefaultTextAlignment()) + getBackColor(33) + getColor(layoutColor)
                        + "Enter command [or exit to quit]:" + RESET + getColor(layoutColor) + " ");
                String input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("exit")) {
                    insertControlChars('n',1);
                    return;
                }
                executeTerminalCommandsModified(input);

            } catch (Exception e) {
                message(getBackColor(rejectionColor)+ "Error: " + e.getMessage() + "." + RESET,
                        layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
            }
        }
    }

    private static void executeTerminalCommandsModified(@NotNull String command) {
        try {
            String[] commands = command.split(" ");

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
}