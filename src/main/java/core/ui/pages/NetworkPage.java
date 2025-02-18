package core.ui.pages;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
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
        message("Network:", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        displayListOfCommands();

        while (true) {
            slowMotionText(getDefaultDelay(), getSearchingLineAlignment(), false,
                    getColor(sysLayoutColor) + searchingArrow, "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "ip-address", "/ip" -> displayUserIp();
                case "scan ports", "/sp" -> scanPorts();
                case "ping host", "/ph" -> pingHost();
                case "trace rout", "/tr" -> traceRout();
                case "http request testing", "/hrt" -> displayHttpTesting();
                case "look up dns records", "/lr" -> nsLookUp();
                case "network stats", "/ns" -> netStat();
                case "rerun", "/rr" -> {
                    insertControlChars('n',1);
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

    private static void displayListOfCommands() {
        insertControlChars('n',1);
        message("·  IP-Address [" + getColor(sysMainColor) + "/ip"
                + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Scan Ports [" + getColor(sysMainColor) + "/sp"
                + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Ping Host [" + getColor(sysMainColor) + "/ph"
                + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Trace Rout [" + getColor(sysMainColor) + "/tr"
                + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Look Up DNS Records [" + getColor(sysMainColor) + "/lr"
                + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  HTTP Request Testing [" + getColor(sysMainColor) + "/hrt"
                + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Network Stats [" + getColor(sysMainColor) + "/ns"
                + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  List Of Commands [" + getColor(sysMainColor) + "/lc"
                + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Exit [" + getColor(sysMainColor) + "/e"
                + getColor(sysLayoutColor) + "]", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
    }

    private static void displayUserIp() {
        try {
            insertControlChars('n',1);
            InetAddress localHost = InetAddress.getLocalHost();
            message("Your local IP: " + getColor(sysMainColor)
                    + localHost, sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
            httpRequest("https://api.ipify.org?format=json", "GET", "Your external IP: "
                    + getColor(sysMainColor), "ip",null);
            insertControlChars('n',1);
        } catch (UnknownHostException e) {
            message("IP is undefined", sysRejectionColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
            message("Status: " + getColor(sysRejectionColor)
                    + "x", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        }
    }

    // /sp
    private static void scanPorts() {
        int startPort = 1;
        int endPort = 65535;
        int threads = 100;

        ExecutorService executor = Executors.newFixedThreadPool(threads);

        insertControlChars('n',1);
        slowMotionText(getDefaultDelay(),getDefaultTextAlignment(),false,
                getColor(sysLayoutColor) + "Scanning ports from "
                        + startPort + " to " + endPort + " using " + threads + " threads","");
        insertControlChars('n',2);

        for (int port = startPort; port <= endPort; port++) {
            final int currentPort = port;
            executor.submit(() -> {
                try (Socket socket = new Socket("localhost", currentPort)) {
                    message("· Port " + getColor(sysMainColor) + currentPort
                            + getColor(sysLayoutColor) + " [" + getColor(sysAcceptanceColor) + "OPEN"
                            + getColor(sysLayoutColor) + "]", sysLayoutColor,
                            getDefaultTextAlignment(), getDefaultDelay(), out::print);
                } catch (Exception ignored) {}
            });
        }

        executor.shutdown();
        while (!executor.isTerminated()) {}
        insertControlChars('n',1);
        message("Scanning completed.", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
    }

    // /ph
    private static void pingHost() {processCommandWithHostInput("ping -c 4");}

    // /tr
    private static void traceRout(){processCommandWithHostInput("traceroute");}

    // /lr
    private static void nsLookUp(){processCommandWithHostInput("nslookup");}

    // /ns
    private static void netStat() {
        try {
            insertControlChars('n', 1);
            while(true) {
                message("BIG AMOUNT OF DATA, BE READY", sysMainColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);

                displayConfirmation("Enter", "y", "+",
                        "to open and", "n", "-", "to skip",
                        sysAcceptanceColor, sysRejectionColor, sysLayoutColor, getDefaultTextAlignment());

                out.print(alignment(getDefaultTextAlignment()) + getColor(sysLayoutColor) + "Choice: ");
                String choice = scanner.nextLine().toLowerCase();
                insertControlChars('n', 1);

                if (choice.equals("y") || choice.equals("+")) {
                    executeTerminalCommand("netstat -an");
                    return;
                }

                else if (choice.equals("n") || choice.equals("-")) {
                    message("Status: " + getColor(sysAcceptanceColor) + "✓", sysLayoutColor,
                            getDefaultTextAlignment(),getDefaultDelay(),out::print);
                    message("Opening skipped" + getColor(sysLayoutColor) + ". " + getColor(sysMainColor)
                                    + "You are in network page" + getColor(sysLayoutColor) + ".", sysMainColor,
                            getDefaultTextAlignment(), getDefaultDelay(), out::println);
                    return;
                } else out.print("");
            }
        }
        catch(Exception e) {
            message("Error: " + e.getMessage(), sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        }
    }

    private static void displayHttpTesting(){
        insertControlChars('n', 1);
        try {
            while (true) {
                out.print(alignment(getDefaultTextAlignment()) + getColor(sysLayoutColor) + "Enter a URL [or exit to quit]: ");
                String link = scanner.nextLine().toLowerCase();

                if(link.equalsIgnoreCase("exit")){
                    message("Status: " + getColor(sysAcceptanceColor) + "✓", sysLayoutColor,getDefaultTextAlignment(),getDefaultDelay(),out::print);
                    message("Opening skipped" + getColor(sysLayoutColor) + ". " + getColor(sysMainColor)
                                    + "You are in network page" + getColor(sysLayoutColor) + ".", sysMainColor,
                            getDefaultTextAlignment(), getDefaultDelay(), out::println);
                    return;
                }

                out.print(alignment(getDefaultTextAlignment()) + getColor(sysLayoutColor) + "Enter a type of request: ");
                String requestType = scanner.nextLine().toUpperCase();

                if(requestType.equalsIgnoreCase("exit")){
                    message("Status: " + getColor(sysAcceptanceColor) + "✓", sysLayoutColor,getDefaultTextAlignment(),getDefaultDelay(),out::print);
                    message("You are in network page" + getColor(sysLayoutColor) + ".", sysMainColor,
                            getDefaultTextAlignment(), getDefaultDelay(), out::println);
                    return;
                }

                httpRequest(link, requestType, "Response: ", "", null);
                insertControlChars('n', 1);
            }
        }
        catch(Exception e) {
            insertControlChars('n', 1);
            message("Error: " + e.getMessage(), sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
        }
    }
}