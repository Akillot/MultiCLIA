package tools.network;

import core.Page;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static core.ui.configs.AppearanceConfigs.*;
import static core.ui.configs.DisplayManager.clearTerminal;
import static core.CommandManager.*;
import static core.ui.configs.TextConfigs.*;
import static java.lang.System.out;
import static tools.network.HttpRequestTester.displayHttpTesting;

public class NetworkPage extends Page {

    private static Scanner scanner = new Scanner(System.in);
    private String[][] commands = {
            {"IP-address", "ip"},
            {"Scan ports", "sp"},
            {"Ping host", "ph"},
            {"Trace rout", "tr"},
            {"Look up DNS records", "lr"},
            {"Http request testing", "hrt"},
            {"Network stats", "ns"},
            {"Network interfaces", "ni"},
            {"Restart", "rst"},
            {"Restart clear", "rcl"},
            {"Clear", "cl"},
            {"Help", "h"},
            {"Quit", "q"}
    };

    public void displayMenu() {
        marginBorder(1, 2);
        message("Network:",
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::print);

        displayListOfCommands(commands);

        while (true) {
            slowMotionText(getDefaultDelay(),
                    getSearchingLineAlignment(),
                    false,
                    getColor(getLayoutColor()) + getSearchingArrow(),
                    "");

            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "ip-address", "ip" -> displayUserIp();
                case "scan ports", "sp" -> scanPorts();
                case "ping host", "ph" -> pingHost();
                case "trace rout", "tr" -> traceRout();
                case "http request testing", "hrt" -> displayHttpTesting();
                case "look up dns records", "lr" -> nsLookUp();
                case "network stats", "ns" -> netStat();
                case "network interfaces", "ni" -> displayNetworkInterfaces();
                case "restart", "rst" -> {
                    insertControlChars('n',1);
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

    // IP
    private static void displayUserIp() {
        try {
            insertControlChars('n',1);
            InetAddress localHost = InetAddress.getLocalHost();
            message("Your local IP: " + getColor(getMainColor())
                    + localHost,
                    getLayoutColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::print);

            httpRequest("https://api.ipify.org?format=json",
                    "GET",
                    "Your external IP: " + getColor(getMainColor()),
                    "ip",
                    null);

            insertControlChars('n',1);
        } catch (UnknownHostException e) {
            message("IP is undefined",
                    getRejectionColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::print);

            message("Status: " + getColor(getRejectionColor()) + "x",
                    getLayoutColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::print);
        }
    }

    // Scan ports 1
    private static void scanPorts() {
        int startPort = 1;
        int endPort = 65535;
        int threads = 100;
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        List<Future<?>> futures = new ArrayList<>();

        insertControlChars('n', 1);
        slowMotionText(getDefaultDelay(),
                getDefaultTextAlignment(),
                false,
                getColor(getLayoutColor()) + "Scanning open ports from " + startPort + " to " + endPort +
                        " using " + threads + " threads...", "");

        insertControlChars('n', 2);

        try {
            InetAddress localHost = InetAddress.getLocalHost();
            Map<Integer, String> commonPorts = getCommonPorts();

            for (int port = startPort; port <= endPort; port++) {
                final int currentPort = port;
                futures.add(executor.submit(() -> {
                    long startTime = System.currentTimeMillis();

                    try (Socket socket = new Socket(localHost, currentPort)) {
                        long responseTime = System.currentTimeMillis() - startTime;
                        String service = commonPorts.getOrDefault(currentPort, "Unknown");

                        message("· Port " + getColor(getMainColor()) + currentPort
                                        + getColor(getLayoutColor()) + " [" + getColor(getAcceptanceColor())
                                        + "OPEN" + getColor(getLayoutColor()) + "] - " + service
                                        + " | " + responseTime + "ms",
                                getLayoutColor(),
                                getDefaultTextAlignment(),
                                getDefaultDelay(),
                                out::print);

                    } catch (IOException ignored) {}
                }));
            }

            for (Future<?> future : futures) future.get();

        } catch (Exception e) {
            message(getBackColor(getRejectionColor()) + "Error: " + e.getMessage() + RESET,
                    getLayoutColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::println);
        } finally {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) executor.shutdownNow();
            } catch (InterruptedException e) {
                executor.shutdownNow();
            }
        }

        insertControlChars('n', 1);
        message("Scanning completed.",
                getLayoutColor(),
                getDefaultTextAlignment(),
                getDefaultDelay(),
                out::println);
    }

    // Scan ports 2
    private static @NotNull Map<Integer, String> getCommonPorts() {
        Map<Integer, String> commonPorts = new HashMap<>();
        commonPorts.put(20, "FTP (Data)");
        commonPorts.put(21, "FTP (Control)");
        commonPorts.put(22, "SSH");
        commonPorts.put(23, "Telnet");
        commonPorts.put(25, "SMTP");
        commonPorts.put(53, "DNS");
        commonPorts.put(67, "DHCP Server");
        commonPorts.put(68, "DHCP Client");
        commonPorts.put(69, "TFTP");
        commonPorts.put(80, "HTTP");
        commonPorts.put(110, "POP3");
        commonPorts.put(123, "NTP");
        commonPorts.put(137, "NetBIOS Name Service");
        commonPorts.put(138, "NetBIOS Datagram Service");
        commonPorts.put(139, "NetBIOS Session Service");
        commonPorts.put(143, "IMAP");
        commonPorts.put(161, "SNMP");
        commonPorts.put(162, "SNMP Trap");
        commonPorts.put(179, "BGP");
        commonPorts.put(194, "IRC");
        commonPorts.put(389, "LDAP");
        commonPorts.put(443, "HTTPS");
        commonPorts.put(445, "SMB");
        commonPorts.put(465, "SMTPS");
        commonPorts.put(514, "Syslog");
        commonPorts.put(515, "LPD");
        commonPorts.put(587, "SMTP (Submission)");
        commonPorts.put(636, "LDAPS");
        commonPorts.put(873, "rsync");
        commonPorts.put(990, "FTPS");
        commonPorts.put(993, "IMAPS");
        commonPorts.put(995, "POP3S");
        commonPorts.put(1080, "SOCKS Proxy");
        commonPorts.put(1194, "OpenVPN");
        commonPorts.put(1433, "Microsoft SQL Server");
        commonPorts.put(1521, "Oracle DB");
        commonPorts.put(1723, "PPTP");
        commonPorts.put(2049, "NFS");
        commonPorts.put(2082, "cPanel");
        commonPorts.put(2083, "cPanel (SSL)");
        commonPorts.put(2086, "WHM");
        commonPorts.put(2087, "WHM (SSL)");
        commonPorts.put(2095, "Webmail");
        commonPorts.put(2096, "Webmail (SSL)");
        commonPorts.put(2181, "ZooKeeper");
        commonPorts.put(2375, "Docker");
        commonPorts.put(2376, "Docker (SSL)");
        commonPorts.put(3000, "Node.js");
        commonPorts.put(3306, "MySQL");
        commonPorts.put(3389, "Remote Desktop");
        commonPorts.put(3690, "SVN");
        commonPorts.put(4333, "mSQL");
        commonPorts.put(4369, "EPMD (Erlang)");
        commonPorts.put(4789, "VXLAN");
        commonPorts.put(4848, "GlassFish");
        commonPorts.put(5000, "UPnP");
        commonPorts.put(5001, "Synergy");
        commonPorts.put(5060, "SIP");
        commonPorts.put(5061, "SIP (TLS)");
        commonPorts.put(5432, "PostgreSQL");
        commonPorts.put(5601, "Kibana");
        commonPorts.put(5672, "AMQP");
        commonPorts.put(5900, "VNC");
        commonPorts.put(5938, "TeamViewer");
        commonPorts.put(5984, "CouchDB");
        commonPorts.put(6379, "Redis");
        commonPorts.put(6443, "Kubernetes API");
        commonPorts.put(6660, "IRC");
        commonPorts.put(6881, "BitTorrent");
        commonPorts.put(6969, "BitTorrent Tracker");
        commonPorts.put(8000, "HTTP Alt");
        commonPorts.put(8008, "HTTP Alt 2");
        commonPorts.put(8080, "HTTP Proxy");
        commonPorts.put(8081, "HTTP Proxy Alt");
        commonPorts.put(8088, "cPanel Alternate");
        commonPorts.put(8443, "HTTPS Alt");
        commonPorts.put(8888, "HTTP Alt 3");
        commonPorts.put(9000, "PHP-FPM");
        commonPorts.put(9001, "Tor");
        commonPorts.put(9042, "Cassandra");
        commonPorts.put(9090, "Prometheus");
        commonPorts.put(9092, "Kafka");
        commonPorts.put(9100, "Printer (RAW)");
        commonPorts.put(9200, "Elasticsearch");
        commonPorts.put(9300, "Elasticsearch Cluster");
        commonPorts.put(9418, "Git");
        commonPorts.put(9999, "JIRA");
        commonPorts.put(10000, "Webmin");
        commonPorts.put(11211, "Memcached");
        commonPorts.put(27017, "MongoDB");
        commonPorts.put(27018, "MongoDB Shard");
        commonPorts.put(28015, "RethinkDB");
        commonPorts.put(32400, "Plex");
        commonPorts.put(49152, "Windows RPC");
        return commonPorts;
    }

    // Ping host
    private static void pingHost() {processCommandWithHostInput("ping -c 4");}

    // Trace rout
    private static void traceRout(){processCommandWithHostInput("traceroute");}

    // Look up DNS records
    private static void nsLookUp(){processCommandWithHostInput("nslookup");}

    // Network stats
    private static void netStat() {
        try {
            insertControlChars('n', 1);
            while(true) {
                message("BIG AMOUNT OF DATA, BE READY",
                        220, getDefaultTextAlignment(),
                        getDefaultDelay(),
                        out::println);

                displayConfirmation("Enter", "y", "+",
                        "to open and", "n", "-", "to skip",
                        getAcceptanceColor(),
                        getRejectionColor(),
                        getLayoutColor(),
                        getDefaultTextAlignment());

                out.print(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor()) + "Choice: ");
                String choice = scanner.nextLine().toLowerCase();
                insertControlChars('n', 1);

                if (choice.equals("y") || choice.equals("+")) {
                    executeTerminalCommand("netstat -an");
                    return;
                }

                else if (choice.equals("n") || choice.equals("-")) {
                    message("Status: " + getColor(getAcceptanceColor()) + "✓",
                            getLayoutColor(),
                            getDefaultTextAlignment(),
                            getDefaultDelay(),
                            out::print);

                    message("Opening skipped" + getColor(getLayoutColor()) + ". " + getColor(getMainColor())
                                    + "You are in network page" + getColor(getLayoutColor()) + ".",
                            getMainColor(),
                            getDefaultTextAlignment(),
                            getDefaultDelay(),
                            out::println);
                    return;
                } else out.print("");
            }
        }
        catch(Exception e) {
            message("Error: " + e.getMessage(),
                    getLayoutColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::print);
        }
    }

    // Network Interfaces
    private static void displayNetworkInterfaces() {
        try {
            insertControlChars('n', 1);
            message("Network Interfaces:",
                    getLayoutColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::println);

            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = networkInterfaces.nextElement();

                // Skip non-active interfaces if desired
                if (!netInterface.isUp()) continue;
                message("·  " + getColor(getMainColor())
                                + netInterface.getDisplayName() + getColor(getLayoutColor()),
                        getLayoutColor(),
                        getDefaultTextAlignment(),
                        getDefaultDelay(),
                        out::print);

                // Get MAC address
                byte[] mac = netInterface.getHardwareAddress();
                if (mac != null) {
                    StringBuilder macAddress = new StringBuilder();
                    for (int i = 0; i < mac.length; i++) {
                        macAddress.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                    }
                    message("   MAC: " + getColor(getMainColor()) + macAddress + getColor(getLayoutColor()),
                            getLayoutColor(),
                            getDefaultTextAlignment(),
                            getDefaultDelay(),
                            out::print);
                } else {
                    message("   MAC: " + getBackColor(getRejectionColor())
                                    + getColor(getLayoutColor()) + "N/A" + getColor(getLayoutColor()),
                            getLayoutColor(),
                            getDefaultTextAlignment(),
                            getDefaultDelay(),
                            out::print);
                }

                // Display IP addresses
                Enumeration<InetAddress> inetAddresses = netInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress address = inetAddresses.nextElement();
                    String addressType = address instanceof Inet4Address ? "IPv4" : "IPv6";
                    message("   " + addressType + ": " + getColor(getMainColor())
                                    + address.getHostAddress() + getColor(getLayoutColor()),
                            getLayoutColor(),
                            getDefaultTextAlignment(),
                            getDefaultDelay(),
                            out::print);
                }

                message("   MTU: " + getColor(getMainColor()) + netInterface.getMTU() + getColor(getLayoutColor()),
                        getLayoutColor(),
                        getDefaultTextAlignment(),
                        getDefaultDelay(),
                        out::print);

                message("   Loopback: " + getColor(getMainColor()) + netInterface.isLoopback()
                                + getColor(getLayoutColor()),
                        getLayoutColor(),
                        getDefaultTextAlignment(),
                        getDefaultDelay(),
                        out::print);

                message("   Point-to-point: " + getColor(getMainColor()) + netInterface.isPointToPoint()
                                + getColor(getLayoutColor()),
                        getLayoutColor(),
                        getDefaultTextAlignment(),
                        getDefaultDelay(),
                        out::print);

                message("   Virtual: " + getColor(getMainColor()) + netInterface.isVirtual()
                                + getColor(getLayoutColor()),
                        getLayoutColor(),
                        getDefaultTextAlignment(),
                        getDefaultDelay(),
                        out::print);

                insertControlChars('n', 1);
            }
        } catch (SocketException e) {
            message("Error retrieving network interfaces: " + e.getMessage(),
                    getRejectionColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::println);
        }
    }
}