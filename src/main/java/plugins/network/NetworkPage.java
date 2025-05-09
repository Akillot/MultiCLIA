package plugins.network;

import core.ui.pages.Page;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static core.ui.configs.AppearanceConfigs.*;
import static core.ui.configs.DisplayManager.clearTerminal;
import static core.logic.CommandManager.*;
import static core.ui.configs.TextConfigs.*;
import static java.lang.System.out;

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
        message("Network:", getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);
        displayListOfCommands(commands);

        while (true) {
            slowMotionText(getDefaultDelay(), getSearchingLineAlignment(), false,
                    getColor(getLayoutColor()) + getSearchingArrow(), "");
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

    //<---ip command realization--->
    private static void displayUserIp() {
        try {
            insertControlChars('n',1);
            InetAddress localHost = InetAddress.getLocalHost();
            message("Your local IP: " + getColor(getMainColor())
                    + localHost, getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);
            httpRequest("https://api.ipify.org?format=json", "GET", "Your external IP: "
                    + getColor(getMainColor()), "ip",null);
            insertControlChars('n',1);
        } catch (UnknownHostException e) {
            message("IP is undefined", getRejectionColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);
            message("Status: " + getColor(getRejectionColor())
                    + "x", getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);
        }
    }

    //<---sp command realization pt:1--->
    private static void scanPorts() {
        int startPort = 1;
        int endPort = 65535;
        int threads = 100;
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        List<Future<?>> futures = new ArrayList<>();

        insertControlChars('n', 1);
        slowMotionText(getDefaultDelay(), getDefaultTextAlignment(), false,
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

                        message("· Port " + getColor(getMainColor()) + currentPort +
                                        getColor(getLayoutColor()) + " [" + getColor(getAcceptanceColor()) + "OPEN" +
                                        getColor(getLayoutColor()) + "] - " + service +
                                        " | " + responseTime + "ms",
                                getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);
                    } catch (IOException ignored) {}
                }));
            }

            for (Future<?> future : futures) {
                future.get();
            }
        } catch (Exception e) {
            message(getBackColor(getRejectionColor()) + "Error: " + e.getMessage() + RESET,
                    getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);
        } finally {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
            }
        }

        insertControlChars('n', 1);
        message("Scanning completed.", getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);
    }

    //<---sp command realization pt:2--->
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

    //<---ph command realization--->
    private static void pingHost() {processCommandWithHostInput("ping -c 4");}

    //<---tr command realization--->
    private static void traceRout(){processCommandWithHostInput("traceroute");}

    //<---lr command realization--->
    private static void nsLookUp(){processCommandWithHostInput("nslookup");}

    //<---ns command realization--->
    private static void netStat() {
        try {
            insertControlChars('n', 1);
            while(true) {
                message("BIG AMOUNT OF DATA, BE READY", 220, getDefaultTextAlignment(), getDefaultDelay(), out::println);

                displayConfirmation("Enter", "y", "+",
                        "to open and", "n", "-", "to skip",
                        getAcceptanceColor(), getRejectionColor(), getLayoutColor(), getDefaultTextAlignment());

                out.print(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor()) + "Choice: ");
                String choice = scanner.nextLine().toLowerCase();
                insertControlChars('n', 1);

                if (choice.equals("y") || choice.equals("+")) {
                    executeTerminalCommand("netstat -an");
                    return;
                }

                else if (choice.equals("n") || choice.equals("-")) {
                    message("Status: " + getColor(getAcceptanceColor()) + "✓", getLayoutColor(),
                            getDefaultTextAlignment(),getDefaultDelay(),out::print);
                    message("Opening skipped" + getColor(getLayoutColor()) + ". " + getColor(getMainColor())
                                    + "You are in network page" + getColor(getLayoutColor()) + ".", getMainColor(),
                            getDefaultTextAlignment(), getDefaultDelay(), out::println);
                    return;
                } else out.print("");
            }
        }
        catch(Exception e) {
            message("Error: " + e.getMessage(), getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);
        }
    }

    //<---htr command realization pt:1--->
    private static void displayHttpTesting() {
        insertControlChars('n', 1);
        Scanner scanner = new Scanner(System.in);

        try {
            while (true) {
                out.print(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor()) + "Enter a URL [or "
                        + getColor(getMainColor()) + "q" + getColor(getLayoutColor()) + " to quit]: ");
                String link = scanner.nextLine().trim();

                if (link.equalsIgnoreCase("q")) {
                    message("Status: " + getColor(getAcceptanceColor()) + "✓", getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);
                    message("You are in the network page" + getColor(getLayoutColor()) + ".", getMainColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);
                    return;
                }

                if (!link.toLowerCase().startsWith("http://") && !link.toLowerCase().startsWith("https://")) {
                    message("Warning: URL should start with http:// or https://. Adding https:// prefix.",
                            getMainColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);
                    link = "https://" + link;
                }

                out.print(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor()) + "Select request type [" +
                        getColor(getMainColor()) + "GET" + getColor(getLayoutColor()) + "|" + getColor(getMainColor()) + "1 " + getColor(getLayoutColor()) +
                        getColor(218) + "POST" + getColor(getLayoutColor()) + "|" + getColor(218) + "2 " + getColor(getLayoutColor()) +
                        getColor(206) + "PUT" + getColor(getLayoutColor()) + "|" + getColor(206) + "3 " + getColor(getLayoutColor()) +
                        getColor(204) + "DELETE" + getColor(getLayoutColor()) + "|" + getColor(204) + "4" + getColor(getLayoutColor())
                        + " or " + getColor(getMainColor()) + "q" + getColor(getLayoutColor()) + " to quit]: ");
                String requestType = scanner.nextLine().trim().toUpperCase();

                if (requestType.equalsIgnoreCase("q")) {
                    insertControlChars('n', 1);
                    message("Status: " + getColor(getAcceptanceColor()) + "✓", getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);
                    message("You are in the network page" + getColor(getLayoutColor()) + ".", getMainColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);
                    return;
                }

                Map<String, String> headers = new HashMap<>();

                message("Common headers: Content-Type, Authorization, User-Agent, Accept", getLayoutColor(),
                        getDefaultTextAlignment(), getDefaultDelay(), out::println);

                while (true) {
                    out.print(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor()) + "Enter header [key:value] or press '"
                            + getColor(getMainColor()) + "Enter" + getColor(getLayoutColor()) + "' to continue: ");
                    String headerInput = scanner.nextLine().trim();

                    if (headerInput.isEmpty()) break;
                    if (headerInput.equalsIgnoreCase("q")) {
                        insertControlChars('n', 1);
                        message("Status: " + getColor(getAcceptanceColor()) + "✓", getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);
                        message("You are in the network page" + getColor(getLayoutColor()) + ".", getMainColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);
                        return;
                    }

                    String[] parts = headerInput.split(":", 2);
                    if (parts.length == 2) {
                        headers.put(parts[0].trim(), parts[1].trim());
                        message("Header added: " + getColor(getAcceptanceColor()) + parts[0].trim() + ": " + parts[1].trim(),
                                getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);
                    } else {
                        message("Invalid header format. Use key:value", getRejectionColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);
                    }
                }

                String body = "";
                if (requestType.equalsIgnoreCase("POST")
                        || requestType.equalsIgnoreCase("PUT")
                        || requestType.equalsIgnoreCase("2")
                        || requestType.equalsIgnoreCase("3")) {

                    if (!headers.containsKey("Content-Type")) {
                        message("No Content-Type specified. Using application/json by default.",
                                getMainColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);
                        headers.put("Content-Type", "application/json");
                    }

                    out.print(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor()) + "Enter request body [or leave empty]: ");
                    body = scanner.nextLine();

                    if (headers.getOrDefault("Content-Type", "").contains("json") && !body.trim().startsWith("{") && !body.isEmpty()) {
                        message("Warning: Content-Type is application/json but body doesn't appear to be JSON format.",
                                getRejectionColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);
                    }
                }

                sendHttpRequest(link, requestType, headers, body);
                insertControlChars('n', 1);

                displayConfirmation("Enter","y","+",
                        "to open and","n","-","to skip",
                        getAcceptanceColor(), getRejectionColor(), getLayoutColor(),getDefaultTextAlignment());
                out.print(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor()) + "Make another request: ");

                String continueChoice = scanner.nextLine().trim().toLowerCase();
                insertControlChars('n', 1);

                if (!continueChoice.equalsIgnoreCase("y") && !continueChoice.equalsIgnoreCase("+")) {
                    insertControlChars('n', 1);
                    message("Status: " + getColor(getAcceptanceColor()) + "✓", getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);
                    message("You are in the network page" + getColor(getLayoutColor()) + ".", getMainColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);
                    return;
                }
            }
        } catch (Exception e) {
            insertControlChars('n', 1);
            message("Error: " + e.getMessage(), getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);
        }
    }

    //<---htr command realization pt:2--->
    private static void sendHttpRequest(String link, String requestType, Map<String, String> headers, String body) {
        try {

            String normalizedRequestType = switch (requestType.toUpperCase()) {
                case "1", "GET" -> "GET";
                case "2", "POST" -> "POST";
                case "3", "PUT" -> "PUT";
                case "4", "DELETE" -> "DELETE";
                default -> {
                    message("Invalid request type. Using GET as default.", getMainColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);
                    yield "GET";
                }
            };

            message("Sending " + getColor(getMainColor()) + normalizedRequestType + getColor(getLayoutColor()) + " request to " +
                    getColor(getMainColor()) + link + getColor(getLayoutColor()) + "...", getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);

            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(normalizedRequestType);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);

            if (headers.isEmpty()) {
                connection.setRequestProperty("User-Agent", "NetworkPage HTTP Tester");
                connection.setRequestProperty("Accept", "*/*");
                message("Using default headers: User-Agent: NetworkPage HTTP Tester, Accept: */*",
                        getMainColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);
            } else {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            if (!body.isEmpty() && (normalizedRequestType.equals("POST") ||
                    normalizedRequestType.equals("PUT"))) {
                connection.setDoOutput(true);
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = body.getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }
            }

            long startTime = System.currentTimeMillis();

            int responseCode = connection.getResponseCode();
            long endTime = System.currentTimeMillis();
            long responseTime = endTime - startTime;

            InputStream responseStream = responseCode >= 400 ? connection.getErrorStream() : connection.getInputStream();
            String response = readStream(responseStream);

            insertControlChars('n', 1);

            String statusColor;
            if (responseCode >= 200 && responseCode < 300) {
                statusColor = String.valueOf(getAcceptanceColor());
            } else if (responseCode >= 300 && responseCode < 400) {
                statusColor = String.valueOf(getMainColor());
            } else {
                statusColor = String.valueOf(getRejectionColor());
            }

            message("Response Code: " + getColor(Integer.parseInt(statusColor)) + responseCode + getColor(getLayoutColor()) +
                            " [" + getHttpStatusMessage(responseCode) + "]",
                    getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);

            message("Response Time: " + getColor(getMainColor()) + responseTime + " ms",
                    getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);

            String contentType = connection.getContentType();
            connection.disconnect();
        } catch (MalformedURLException e) {
            message("Error: Invalid URL format. Make sure to include http:// or https://",
                    getRejectionColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);
        } catch (UnknownHostException e) {
            message("Error: Unknown host. Please check the domain name or your internet connection.",
                    getRejectionColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);
        } catch (ConnectException e) {
            message("Error: Could not connect to the server. Server may be down or unreachable.",
                    getRejectionColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);
        } catch (SocketTimeoutException e) {
            message("Error: Connection timed out. The server took too long to respond.",
                    getRejectionColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);
        } catch (Exception e) {
            message("Error sending request: " + e.getMessage(),
                    getRejectionColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);
        }
    }

    //<---htr command realization pt:3--->
    private static @NotNull String readStream(InputStream stream) throws IOException {
        if (stream == null) return "No response body";

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line).append("\n");
        }
        return response.toString();
    }

    //<---htr command realization pt:4--->
    private static @NotNull String formatJson(String json) {
        if (json == null || json.isEmpty()) return "{}";

        StringBuilder formatted = new StringBuilder();
        int indentLevel = 0;
        boolean inQuotes = false;

        for (char c : json.toCharArray()) {
            if (c == '"' && (formatted.isEmpty() || formatted.charAt(formatted.length() - 1) != '\\')) {
                inQuotes = !inQuotes;
            }

            if (!inQuotes) {
                if (c == '{' || c == '[') {
                    formatted.append(c).append("\n");
                    indentLevel++;
                    formatted.append("  ".repeat(Math.max(0, indentLevel)));
                } else if (c == '}' || c == ']') {
                    formatted.append("\n");
                    indentLevel--;
                    formatted.append("  ".repeat(Math.max(0, indentLevel)));
                    formatted.append(c);
                } else if (c == ',') {
                    formatted.append(c).append("\n");
                    formatted.append("  ".repeat(Math.max(0, indentLevel)));
                } else if (c == ':') {
                    formatted.append(c).append(" ");
                } else {
                    formatted.append(c);
                }
            } else {
                formatted.append(c);
            }
        }
        return formatted.toString();
    }

    //<---htr command realization pt:5--->
    @Contract(pure = true)
    private static @NotNull String getHttpStatusMessage(int statusCode) {
        return switch (statusCode) {
            case 100 -> "Continue";
            case 101 -> "Switching Protocols";
            case 102 -> "Processing (WebDAV)";
            case 103 -> "Early Hints";
            case 200 -> "OK";
            case 201 -> "Created";
            case 202 -> "Accepted";
            case 203 -> "Non-Authoritative Information";
            case 204 -> "No Content";
            case 205 -> "Reset Content";
            case 206 -> "Partial Content";
            case 207 -> "Multi-Status (WebDAV)";
            case 208 -> "Already Reported (WebDAV)";
            case 226 -> "IM Used";
            case 300 -> "Multiple Choices";
            case 301 -> "Moved Permanently";
            case 302 -> "Found";
            case 303 -> "See Other";
            case 304 -> "Not Modified";
            case 305 -> "Use Proxy";
            case 307 -> "Temporary Redirect";
            case 308 -> "Permanent Redirect";
            case 400 -> "Bad Request";
            case 401 -> "Unauthorized";
            case 402 -> "Payment Required";
            case 403 -> "Forbidden";
            case 404 -> "Not Found";
            case 405 -> "Method Not Allowed";
            case 406 -> "Not Acceptable";
            case 407 -> "Proxy Authentication Required";
            case 408 -> "Request Timeout";
            case 409 -> "Conflict";
            case 410 -> "Gone";
            case 411 -> "Length Required";
            case 412 -> "Precondition Failed";
            case 413 -> "Payload Too Large";
            case 414 -> "URI Too Long";
            case 415 -> "Unsupported Media Type";
            case 416 -> "Range Not Satisfiable";
            case 417 -> "Expectation Failed";
            case 418 -> "I'm a Teapot";
            case 421 -> "Misdirected Request";
            case 422 -> "Unprocessable Entity (WebDAV)";
            case 423 -> "Locked (WebDAV)";
            case 424 -> "Failed Dependency (WebDAV)";
            case 425 -> "Too Early";
            case 426 -> "Upgrade Required";
            case 428 -> "Precondition Required";
            case 429 -> "Too Many Requests";
            case 431 -> "Request Header Fields Too Large";
            case 451 -> "Unavailable For Legal Reasons";
            case 500 -> "Internal Server Error";
            case 501 -> "Not Implemented";
            case 502 -> "Bad Gateway";
            case 503 -> "Service Unavailable";
            case 504 -> "Gateway Timeout";
            case 505 -> "HTTP Version Not Supported";
            case 506 -> "Variant Also Negotiates";
            case 507 -> "Insufficient Storage (WebDAV)";
            case 508 -> "Loop Detected (WebDAV)";
            case 510 -> "Not Extended";
            case 511 -> "Network Authentication Required";
            default -> "Unknown Status";
        };
    }

    //<---ni command realization--->
    private static void displayNetworkInterfaces() {
        try {
            insertControlChars('n', 1);
            message("Network Interfaces:", getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::println);

            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = networkInterfaces.nextElement();

                // Skip non-active interfaces if desired
                if (!netInterface.isUp())
                    continue;

                message("·  " + getColor(getMainColor()) + netInterface.getDisplayName() + getColor(getLayoutColor()),
                        getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);

                // Get MAC address
                byte[] mac = netInterface.getHardwareAddress();
                if (mac != null) {
                    StringBuilder macAddress = new StringBuilder();
                    for (int i = 0; i < mac.length; i++) {
                        macAddress.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                    }
                    message("   MAC: " + getColor(getMainColor()) + macAddress + getColor(getLayoutColor()),
                            getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);
                } else {
                    message("   MAC: " + getColor(getRejectionColor()) + "N/A" + getColor(getLayoutColor()),
                            getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);
                }

                // Display IP addresses
                Enumeration<InetAddress> inetAddresses = netInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress address = inetAddresses.nextElement();
                    String addressType = address instanceof Inet4Address ? "IPv4" : "IPv6";
                    message("   " + addressType + ": " + getColor(getMainColor()) + address.getHostAddress() + getColor(getLayoutColor()),
                            getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);
                }

                message("   MTU: " + getColor(getMainColor()) + netInterface.getMTU() + getColor(getLayoutColor()),
                        getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);

                message("   Loopback: " + getColor(getMainColor()) + netInterface.isLoopback() + getColor(getLayoutColor()),
                        getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);

                message("   Point-to-point: " + getColor(getMainColor()) + netInterface.isPointToPoint() + getColor(getLayoutColor()),
                        getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);

                message("   Virtual: " + getColor(getMainColor()) + netInterface.isVirtual() + getColor(getLayoutColor()),
                        getLayoutColor(), getDefaultTextAlignment(), getDefaultDelay(), out::print);

                insertControlChars('n', 1);
            }
        } catch (SocketException e) {
            message("Error retrieving network interfaces: " + e.getMessage(), getRejectionColor(),
                    getDefaultTextAlignment(), getDefaultDelay(), out::println);
        }
    }
}