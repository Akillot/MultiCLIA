package core.pages;

import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static core.configs.AppearanceConfigs.*;
import static core.configs.AppearanceConfigs.sysLayoutColor;
import static core.configs.TextConfigs.*;
import static core.configs.TextConfigs.message;
import static core.logic.CommandManager.*;
import static core.ui.DisplayManager.clearTerminal;
import static java.lang.System.out;

public class NetworkPage {

    private static Scanner scanner = new Scanner(System.in);

    public static void displayNetworkPage() {
        marginBorder(1, 2);
        message("Network:", sysLayoutColor, 58, 0, out::print);
        displayListOfCommands();

        while (true) {
            slowMotionText(0, searchingLineAlignment, false, getColor(sysLayoutColor) + "> ",
                    "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "scan ports", "/sp" -> scanPorts();
                case "ping host", "/ph" -> pingHost();
                case "trace rout", "/tr" -> traceRout();
                case "look up dns records", "/lr" -> nsLookUp();
                case "network stats", "/ns" -> netStat();
                case "ssh connection", "/ssh" -> setConnectionToServer();
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
        message("·  Scan Ports [" + getColor(sysMainColor) + "/sp"
                + getColor(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  Ping Host [" + getColor(sysMainColor) + "/ph"
                + getColor(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  Trace Rout [" + getColor(sysMainColor) + "/tr"
                + getColor(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  Look Up DNS Records [" + getColor(sysMainColor) + "/lr"
                + getColor(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  Network Stats [" + getColor(sysMainColor) + "/ns"
                + getColor(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  SSH Connection [" + getColor(sysMainColor) + "/ssh"
                + getColor(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  List Of Commands [" + getColor(sysMainColor) + "/lc"
                + getColor(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  Exit [" + getColor(sysMainColor) + "/e"
                + getColor(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::println);
    }

    // /sp
    private static void scanPorts() {
        int startPort = 1;
        int endPort = 65535;
        int threads = 100;

        ExecutorService executor = Executors.newFixedThreadPool(threads);

        modifyMessage('n',1);
        slowMotionText(0,58,false,
                getColor(sysLayoutColor) + "Scanning ports from "
                        + startPort + " to " + endPort + " using " + threads + " threads","");
        modifyMessage('n',2);

        for (int port = startPort; port <= endPort; port++) {
            final int currentPort = port;
            executor.submit(() -> {
                try (Socket socket = new Socket("localhost", currentPort)) {
                    message("· Port " + getColor(sysMainColor) + currentPort
                            + getColor(sysLayoutColor) + " [" + getColor(sysAcceptanceColor) + "OPEN"
                            + getColor(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);
                } catch (Exception ignored) {}
            });
        }

        executor.shutdown();
        while (!executor.isTerminated()) {}
        modifyMessage('n',1);
        message("Scanning completed.", sysLayoutColor, 58, 0, out::println);
    }

    // /ph
    private static void pingHost() {
        processCommandWithHostInput("ping -c 4");
    }

    // /tr
    private static void traceRout(){
        processCommandWithHostInput("traceroute");
    }

    // /lr
    private static void nsLookUp(){
        processCommandWithHostInput("nslookup");
    }

    // /ns
    private static void netStat() {
        modifyMessage('n', 1);
        message("BIG AMOUNT OF DATA, BE READY", sysMainColor, 58, 0, out::print);
        modifyMessage('n',1);
        executeTerminalCommand("netstat -an");
    }

    // /ssh
    private static void setConnectionToServer() {
        modifyMessage('n', 1);

        out.print(alignment(58) + getColor(sysLayoutColor) + "Enter " +
                getColorText("username", sysMainColor) + getColor(sysLayoutColor) +
                " [user name on the remote computer]: ");
        String username = scanner.nextLine().trim().toLowerCase();

        out.print(alignment(58) + getColor(sysLayoutColor) + "Enter " +
                getColorText("hostname", sysMainColor) + getColor(sysLayoutColor) +
                " [IP address or domain name]: ");
        String userHostname = scanner.nextLine().trim().toLowerCase();
        modifyMessage('n', 1);

        if (userHostname.isBlank()) {
            out.println(alignment(58) + getColorText("Hostname cannot be empty. Connection aborted.", sysLayoutColor));
            return;
        }

        out.print(alignment(58) + getColor(sysLayoutColor) + "Select port: " +
                getColorText("Default", sysMainColor) + getColorText("/", sysLayoutColor) +
                getColorText("Custom", sysMainColor) + getColor(sysLayoutColor) + ": ");
        String userPort = scanner.nextLine().trim().toLowerCase();
        modifyMessage('n', 1);

        int port;
        String connectionString;
        switch (userPort) {
            case "default" -> {
                port = 22;
                connectionString = "ssh " + username + "@" + userHostname + " -p " + port;
                out.println(alignment(58) + getColor(sysLayoutColor) +
                        "Using default port: " + getColorText(String.valueOf(port), sysMainColor));
                executeTerminalCommand(connectionString);
            }
            case "custom" -> {
                out.print(alignment(58) + getColor(sysLayoutColor) +
                        "Enter custom port number: ");
                try {
                    port = Integer.parseInt(scanner.nextLine().trim());
                    if (port < 1 || port > 65535) {
                        throw new NumberFormatException("Invalid port range");
                    }
                    connectionString = "ssh " + username + "@" + userHostname + " -p " + port;
                    out.println(alignment(58) + getColor(sysLayoutColor) +
                            "Using custom port: " + getColorText(String.valueOf(port), sysMainColor));
                    executeTerminalCommand(connectionString);
                } catch (NumberFormatException e) {
                    out.println(alignment(58) + getColorText("Invalid port entered. Connection aborted.", sysLayoutColor));
                    return;
                }
            }
            default -> {
                out.println(alignment(58) + getColorText("Invalid option. Connection aborted.", sysLayoutColor));
                return;
            }
        }

        try {
            modifyMessage('n', 1);
            out.println(alignment(58) + getColor(sysLayoutColor) +
                    "Connection string: " + getColorText(connectionString, sysMainColor));
        } catch (Exception e) {
            out.println(alignment(58) + getColorText("Failed to execute SSH command. " + e.getMessage(), sysLayoutColor));
        }
    }
}