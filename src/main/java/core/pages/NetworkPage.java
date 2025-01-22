package core.pages;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static core.configs.AppearanceConfigs.*;
import static core.configs.AppearanceConfigs.sysLayoutColor;
import static core.configs.TextConfigs.*;
import static core.configs.TextConfigs.message;
import static core.logic.CommandManager.exitPage;
import static core.pages.StartPage.mainMenuRerun;
import static core.ui.DisplayManager.clearTerminal;
import static core.ui.DisplayManager.scanner;
import static java.lang.System.out;

public class NetworkPage {

    public static void displayNetworkPage() {
        marginBorder(1, 2);
        message("Network:", sysLayoutColor, 58, 0, out::print);
        displayListOfCommands();

        while (true) {
            slowMotionText(0, searchingLineAlignment, false, getAnsi256Color(sysLayoutColor) + "> ",
                    "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "scan ports", "/sp" -> scanPorts();
                case "trace rout", "/tr" -> pingHost();
                case "rerun", "/rr" -> mainMenuRerun();
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

    private static void displayListOfCommands() {
        modifyMessage('n',1);
        message("·  Scan Ports [" + getAnsi256Color(sysMainColor) + "/sp"
                + getAnsi256Color(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  Trace Rout [" + getAnsi256Color(sysMainColor) + "/tr"
                + getAnsi256Color(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  List Of Commands [" + getAnsi256Color(sysMainColor) + "/lc"
                + getAnsi256Color(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  Exit [" + getAnsi256Color(sysMainColor) + "/e"
                + getAnsi256Color(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::println);
    }

    private static void pingHost() {
        Scanner scanner = new Scanner(System.in);
        try {
            modifyMessage('n',1);
            out.print(alignment(58) + getAnsi256Color(sysLayoutColor) + "Enter host: ");
            String host = scanner.nextLine();
            modifyMessage('n',1);

            if(!host.isEmpty()) {
                Process process = Runtime.getRuntime().exec("ping -c 4 " + host);
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    message(line, sysLayoutColor, 58, 0, out::print);
                }
                reader.close();
                modifyMessage('n', 1);
            }
        } catch (Exception e) {
            message("Ping execution error: " + e.getMessage(), sysLayoutColor, 58, 0, out::println);
        }
    }

    private static void scanPorts() {
        int startPort = 1;
        int endPort = 65535;
        int threads = 100;

        ExecutorService executor = Executors.newFixedThreadPool(threads);

        marginBorder(1,2);
        slowMotionText(0,58,false,
                getAnsi256Color(sysLayoutColor) + "Scanning ports from "
                        + startPort + " to " + endPort + " using " + threads + " threads","");
        modifyMessage('n',2);

        for (int port = startPort; port <= endPort; port++) {
            final int currentPort = port;
            executor.submit(() -> {
                try (Socket socket = new Socket("localhost", currentPort)) {
                    message("· Port " + getAnsi256Color(sysMainColor) + currentPort
                            + getAnsi256Color(sysLayoutColor) + " [" + getAnsi256Color(sysAcceptanceColor) + "OPEN"
                            + getAnsi256Color(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);
                } catch (Exception ignored) {}
            });
        }

        executor.shutdown();
        while (!executor.isTerminated()) {}
        modifyMessage('n',1);
        message("Scanning completed.", sysLayoutColor, 58, 0, out::print);
        marginBorder(2,1);
    }
}

