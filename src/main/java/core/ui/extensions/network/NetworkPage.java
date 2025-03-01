package core.ui.extensions.network;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static core.ui.essential.configs.AppearanceConfigs.*;
import static core.ui.essential.configs.DisplayManager.clearTerminal;
import static core.logic.CommandManager.*;
import static core.ui.essential.configs.TextConfigs.*;
import static java.lang.System.out;

public class NetworkPage {

    private static Scanner scanner = new Scanner(System.in);

    public static void displayNetworkPage() {
        marginBorder(1, 2);
        message("Network:", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        displayListOfCommands();

        while (true) {
            slowMotionText(getDefaultDelay(), getSearchingLineAlignment(), false,
                    getColor(layoutColor) + searchingArrow, "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "ip-address", "/ip" -> displayUserIp();
                case "scan ports", "/sp" -> scanPorts();
                case "ping host", "/ph" -> pingHost();
                case "trace rout", "/tr" -> traceRout();
                case "http request testing", "/hrt" -> displayHttpTesting();
                case "look up dns records", "/lr" -> nsLookUp();
                case "network stats", "/ns" -> netStat();
                case "restart", "/rs" -> {
                    insertControlChars('n',1);
                    mainMenuRerun();
                }
                case "clear terminal", "/cl" -> clearTerminal();
                case "list", "/ls" -> displayListOfCommands();
                case "quit", "/q" -> {
                    exitPage();
                    return;
                }
                default -> out.print("");
            }
        }
    }

    private static void displayListOfCommands() {
        insertControlChars('n',1);
        message("·  IP-Address [" + getColor(mainColor) + "/ip"
                + getColor(layoutColor) + "]", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Scan Ports [" + getColor(mainColor) + "/sp"
                + getColor(layoutColor) + "]", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Ping Host [" + getColor(mainColor) + "/ph"
                + getColor(layoutColor) + "]", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Trace Rout [" + getColor(mainColor) + "/tr"
                + getColor(layoutColor) + "]", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Look Up DNS Records [" + getColor(mainColor) + "/lr"
                + getColor(layoutColor) + "]", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  HTTP Request Testing [" + getColor(mainColor) + "/hrt"
                + getColor(layoutColor) + "]", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Network Stats [" + getColor(mainColor) + "/ns"
                + getColor(layoutColor) + "]", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Restart [" + getColor(mainColor)
                + "/rs" + getColor(layoutColor) + "]", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Clear terminal [" + getColor(mainColor)
                + "/cl" + getColor(layoutColor) + "]", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  List [" + getColor(mainColor)
                + "/ls" + getColor(layoutColor) + "]", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

        message("·  Quit [" + getColor(mainColor)
                + "/q" + getColor(layoutColor) + "]", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
    }

    private static void displayUserIp() {
        try {
            insertControlChars('n',1);
            InetAddress localHost = InetAddress.getLocalHost();
            message("Your local IP: " + getColor(mainColor)
                    + localHost, layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
            httpRequest("https://api.ipify.org?format=json", "GET", "Your external IP: "
                    + getColor(mainColor), "ip",null);
            insertControlChars('n',1);
        } catch (UnknownHostException e) {
            message("IP is undefined", rejectionColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
            message("Status: " + getColor(rejectionColor)
                    + "x", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
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
                getColor(layoutColor) + "Scanning ports from "
                        + startPort + " to " + endPort + " using " + threads + " threads","");
        insertControlChars('n',2);

        for (int port = startPort; port <= endPort; port++) {
            final int currentPort = port;
            executor.submit(() -> {
                try (Socket socket = new Socket("localhost", currentPort)) {
                    message("· Port " + getColor(mainColor) + currentPort
                            + getColor(layoutColor) + " [" + getColor(acceptanceColor) + "OPEN"
                            + getColor(layoutColor) + "]", layoutColor,
                            getDefaultTextAlignment(), getDefaultDelay(), out::print);
                } catch (Exception ignored) {}
            });
        }

        executor.shutdown();
        while (!executor.isTerminated()) {}
        insertControlChars('n',1);
        message("Scanning completed.", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
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
                message("BIG AMOUNT OF DATA, BE READY", mainColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);

                displayConfirmation("Enter", "y", "+",
                        "to open and", "n", "-", "to skip",
                        acceptanceColor, rejectionColor, layoutColor, getDefaultTextAlignment());

                out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "Choice: ");
                String choice = scanner.nextLine().toLowerCase();
                insertControlChars('n', 1);

                if (choice.equals("y") || choice.equals("+")) {
                    executeTerminalCommand("netstat -an");
                    return;
                }

                else if (choice.equals("n") || choice.equals("-")) {
                    message("Status: " + getColor(acceptanceColor) + "✓", layoutColor,
                            getDefaultTextAlignment(),getDefaultDelay(),out::print);
                    message("Opening skipped" + getColor(layoutColor) + ". " + getColor(mainColor)
                                    + "You are in network page" + getColor(layoutColor) + ".", mainColor,
                            getDefaultTextAlignment(), getDefaultDelay(), out::println);
                    return;
                } else out.print("");
            }
        }
        catch(Exception e) {
            message("Error: " + e.getMessage(), layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        }
    }

    private static void displayHttpTesting(){
        insertControlChars('n', 1);
        try {
            while (true) {
                out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "Enter a URL [or exit to quit]: ");
                String link = scanner.nextLine().toLowerCase();

                if(link.equalsIgnoreCase("exit")){
                    message("Status: " + getColor(acceptanceColor) + "✓", layoutColor,getDefaultTextAlignment(),getDefaultDelay(),out::print);
                    message("Opening skipped" + getColor(layoutColor) + ". " + getColor(mainColor)
                                    + "You are in network page" + getColor(layoutColor) + ".", mainColor,
                            getDefaultTextAlignment(), getDefaultDelay(), out::println);
                    return;
                }

                out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "Enter a type of request: ");
                String requestType = scanner.nextLine().toUpperCase();

                if(requestType.equalsIgnoreCase("exit")){
                    message("Status: " + getColor(acceptanceColor) + "✓", layoutColor,getDefaultTextAlignment(),getDefaultDelay(),out::print);
                    message("You are in network page" + getColor(layoutColor) + ".", mainColor,
                            getDefaultTextAlignment(), getDefaultDelay(), out::println);
                    return;
                }

                httpRequest(link, requestType, "Response: ", "", null);
                insertControlChars('n', 1);
            }
        }
        catch(Exception e) {
            insertControlChars('n', 1);
            message("Error: " + e.getMessage(), layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
        }
    }
}