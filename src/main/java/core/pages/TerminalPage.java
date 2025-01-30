package core.pages;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import static core.configs.AppearanceConfigs.*;
import static core.configs.AppearanceConfigs.sysLayoutColor;
import static core.configs.TextConfigs.*;
import static core.configs.TextConfigs.message;
import static core.logic.CommandManager.*;
import static core.ui.DisplayManager.clearTerminal;
import static java.lang.System.out;

public class TerminalPage {

    private static Scanner scanner = new Scanner(System.in);

    public static void displayTerminalPage() {
        marginBorder(1, 2);
        message("Terminal:", sysLayoutColor, 58, 0, out::print);
        displayListOfCommands();

        while (true) {
            slowMotionText(0, searchingLineAlignment, false, getColor(sysLayoutColor) + "> ",
                    "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "enter command", "/ec" -> {
                    modifyMessage('n',1);
                    executeCommand();
                }
                case "rerun", "/rr" -> {
                    modifyMessage('n',1);
                    mainMenuRerun();
                }
                case "clear terminal", "/cl" -> clearTerminal();
                case "list of commands", "/lc" -> displayListOfCommands();
                case "exit", "/e" -> {
                    exitPage();
                    return;
                }
                default -> out.print("");
            }
        }
    }

    private static void displayListOfCommands(){
        modifyMessage('n',1);
        message("·  Enter command [" + getColor(sysMainColor)
                + "/ec" + getColor(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  Clear Terminal [" + getColor(sysMainColor)
                + "/cl" + getColor(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  List Of Commands [" + getColor(sysMainColor)
                + "/lc" + getColor(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  Exit [" + getColor(sysMainColor)
                + "/e" + getColor(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::println);
    }

    private static void executeCommand() {
        while (true) {
            try {
                out.print(alignment(58) + getBackColor(33) + getColor(sysLayoutColor)
                        + "Enter command:" + RESET + getColor(sysLayoutColor) + " ");
                String input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("exit")) {
                    modifyMessage('n',1);
                    return;
                }
                executeTerminalCommandsModified(input);

            } catch (Exception e) {
                message(getBackColor(sysRejectionColor)+ "Error: " + e.getMessage() + RESET, sysLayoutColor, 58, 0, out::println);
            }
        }
    }

    public static void executeTerminalCommandsModified(@NotNull String command) {
        ProcessBuilder processBuilder = new ProcessBuilder();

        if (command.contains(" ")) processBuilder.command(command.split(" "));
        else processBuilder.command(command);
        processBuilder.redirectErrorStream(true);

        try {
            Process process = processBuilder.start();
            Thread outputThread = getOutputThread(process);

            int exitCode = process.waitFor();
            outputThread.join();

            if (exitCode != 0) message(getBackColor(sysRejectionColor) + "Command failed with exit code: " + exitCode + RESET,
                    sysLayoutColor, 58, 0, out::println);
            else {
                modifyMessage('n', 1);
                message(getBackColor(40) + "Process completed successfully." + RESET,
                        sysLayoutColor, 58, 0, out::println);
            }

        } catch (IOException e) {
            message(getBackColor(sysRejectionColor) + "I/O Error while executing command: " + e.getMessage() + RESET,
                    sysLayoutColor, 58, 0, out::println);

        } catch (InterruptedException e) {
            message(getBackColor(sysRejectionColor) + "Process was interrupted: " + e.getMessage() + RESET,
                    sysLayoutColor, 58, 0, out::println);
            Thread.currentThread().interrupt();
        }
    }

    private static @NotNull Thread getOutputThread(Process process) {
        Thread outputThread = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    message(getBackColor(33) + line + RESET, sysLayoutColor, 58, 0, out::print);
                }
            } catch (IOException e) {
                message(getBackColor(sysRejectionColor) + "Error reading process output: " + e.getMessage() + RESET,
                        sysLayoutColor, 58, 0, out::println);
            }
        });

        outputThread.start();
        return outputThread;
    }
}