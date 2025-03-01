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
import static java.lang.System.out;

public class TerminalPage {

    private static Scanner scanner = new Scanner(System.in);
    private static Path currentDirectory = Paths.get("").toAbsolutePath();

    public static void displayTerminalPage() {
        marginBorder(1, 2);
        message("Terminal:", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        displayListOfCommands();

        while (true) {
            slowMotionText(getDefaultDelay(), getSearchingLineAlignment(), false, getColor(sysLayoutColor) + searchingArrow,
                    "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "enter command", "/ec" -> {
                    insertControlChars('n',1);
                    executeCommand();
                }
                case "rerun", "/rr" -> {
                    insertControlChars('n',1);
                    mainMenuRerun();
                }
                case "clear terminal", "/cl" -> clearTerminal();
                case "list", "/ls" -> displayListOfCommands();
                case "exit", "/e" -> {
                    exitPage();
                    return;
                }
                default -> out.print("");
            }
        }
    }

    private static void displayListOfCommands(){
        insertControlChars('n',1);
        message("·  Enter command [" + getColor(sysMainColor)
                + "/ec" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Clear Terminal [" + getColor(sysMainColor)
                + "/cl" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  List [" + getColor(sysMainColor)
                + "/ls" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Exit [" + getColor(sysMainColor)
                + "/e" + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
    }

    private static void executeCommand() {
        while (true) {
            try {
                out.print(alignment(getDefaultTextAlignment()) + getBackColor(33) + getColor(sysLayoutColor)
                        + "Enter command [or exit to quit]:" + RESET + getColor(sysLayoutColor) + " ");
                String input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("exit")) {
                    insertControlChars('n',1);
                    return;
                }
                executeTerminalCommandsModified(input);

            } catch (Exception e) {
                message(getBackColor(sysRejectionColor)+ "Error: " + e.getMessage() + "." + RESET,
                        sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
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
                                sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
                    } else {
                        message(getBackColor(sysRejectionColor) + "No such directory: " + commands[1] + "." + RESET,
                                sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
                    }
                } else {
                    message(getBackColor(45) + "Usage: cd <directory>" + RESET, sysLayoutColor,
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
                            sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
                }
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                message(getBackColor(sysRejectionColor) + "Command failed with exit code: " + exitCode + "." + RESET,
                        sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
            } else {
                insertControlChars('n', 1);
                message(getBackColor(34) + "Process completed successfully." + RESET,
                        sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
            }

        } catch (IOException e) {
            message(getBackColor(sysRejectionColor) + "I/O Error while executing command: " + e.getMessage() + "." + RESET,
                    sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
        } catch (InterruptedException e) {
            message(getBackColor(sysRejectionColor) + "Process was interrupted: " + e.getMessage() + "." + RESET,
                    sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
            Thread.currentThread().interrupt();
        }
    }
}