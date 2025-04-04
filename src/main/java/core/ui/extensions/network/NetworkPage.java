package core.ui.extensions.network;

import core.ui.essential.pages.Page;
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

import static core.ui.essential.configs.appearance.AppearanceConfigs.*;
import static core.ui.essential.configs.DisplayManager.clearTerminal;
import static core.logic.CommandManager.*;
import static core.ui.essential.configs.appearance.TextConfigs.*;
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
            {"Clear", "cl"},
            {"List", "ls"},
            {"Quit", "q"}
    };

    public void displayMenu() {
        marginBorder(1, 2);
        message("Network:", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        displayListOfCommands(commands);

        while (true) {
            slowMotionText(getDefaultDelay(), getSearchingLineAlignment(), false,
                    getColor(layoutColor) + searchingArrow, "");
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
                    mainMenuRerun();
                }
                case "clear", "cl" -> clearTerminal();
                case "list", "ls" -> displayListOfCommands(commands);
                case "quit", "q" -> {
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
        List<Future<?>> futures = new ArrayList<>();

        insertControlChars('n', 1);
        slowMotionText(getDefaultDelay(), getDefaultTextAlignment(), false,
                getColor(layoutColor) + "Scanning open ports from " + startPort + " to " + endPort +
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

                        message("· Port " + getColor(mainColor) + currentPort +
                                        getColor(layoutColor) + " [" + getColor(acceptanceColor) + "OPEN" +
                                        getColor(layoutColor) + "] - " + service +
                                        " | " + responseTime + "ms",
                                layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
                    } catch (IOException ignored) {}
                }));
            }

            for (Future<?> future : futures) {
                future.get();
            }
        } catch (Exception e) {
            message(getBackColor(rejectionColor) + "Error: " + e.getMessage() + RESET,
                    layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
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
        message("Scanning completed.", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
    }


    private static @NotNull Map<Integer, String> getCommonPorts() {
        Map<Integer, String> commonPorts = new HashMap<>();
        commonPorts.put(22, "SSH");
        commonPorts.put(53, "DNS");
        commonPorts.put(80, "HTTP");
        commonPorts.put(443, "HTTPS");
        commonPorts.put(3306, "MySQL");
        commonPorts.put(3389, "Remote Desktop");
        commonPorts.put(5432, "PostgreSQL");
        commonPorts.put(8080, "HTTP Proxy");
        return commonPorts;
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

    private static void displayHttpTesting() {
        insertControlChars('n', 1);
        Scanner scanner = new Scanner(System.in);

        try {
            while (true) {
                out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "Enter a URL [or "
                        + getColor(mainColor) + "/q" + getColor(layoutColor) + " to quit]: ");
                String link = scanner.nextLine().trim();

                if (link.equalsIgnoreCase("/q")) {
                    message("Status: " + getColor(acceptanceColor) + "✓", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
                    message("You are in the network page" + getColor(layoutColor) + ".", mainColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
                    return;
                }

                if (!link.toLowerCase().startsWith("http://") && !link.toLowerCase().startsWith("https://")) {
                    message("Warning: URL should start with http:// or https://. Adding https:// prefix.",
                            mainColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
                    link = "https://" + link;
                }

                out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "Select request type [" +
                        getColor(mainColor) + "GET" + getColor(layoutColor) + "|" + getColor(mainColor) + "1 " + getColor(layoutColor) +
                        getColor(218) + "POST" + getColor(layoutColor) + "|" + getColor(218) + "2 " + getColor(layoutColor) +
                        getColor(206) + "PUT" + getColor(layoutColor) + "|" + getColor(206) + "3 " + getColor(layoutColor) +
                        getColor(204) + "DELETE" + getColor(layoutColor) + "|" + getColor(204) + "4" + getColor(layoutColor)
                        + " or /" + getColor(mainColor) + "q" + getColor(layoutColor) + " to quit]: ");
                String requestType = scanner.nextLine().trim().toUpperCase();

                if (requestType.equalsIgnoreCase("/q")) {
                    insertControlChars('n', 1);
                    message("Status: " + getColor(acceptanceColor) + "✓", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
                    message("You are in the network page" + getColor(layoutColor) + ".", mainColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
                    return;
                }

                Map<String, String> headers = new HashMap<>();

                message("Common headers: Content-Type, Authorization, User-Agent, Accept", layoutColor,
                        getDefaultTextAlignment(), getDefaultDelay(), out::println);

                while (true) {
                    out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "Enter header [key:value] or press '"
                            + getColor(mainColor) + "Enter" + getColor(layoutColor) + "' to continue: ");
                    String headerInput = scanner.nextLine().trim();

                    if (headerInput.isEmpty()) break;
                    if (headerInput.equalsIgnoreCase("/q")) {
                        insertControlChars('n', 1);
                        message("Status: " + getColor(acceptanceColor) + "✓", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
                        message("You are in the network page" + getColor(layoutColor) + ".", mainColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
                        return;
                    }

                    String[] parts = headerInput.split(":", 2);
                    if (parts.length == 2) {
                        headers.put(parts[0].trim(), parts[1].trim());
                        message("Header added: " + getColor(acceptanceColor) + parts[0].trim() + ": " + parts[1].trim(),
                                layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
                    } else {
                        message("Invalid header format. Use key:value", rejectionColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
                    }
                }

                String body = "";
                if (requestType.equalsIgnoreCase("POST")
                        || requestType.equalsIgnoreCase("PUT")
                        || requestType.equalsIgnoreCase("2")
                        || requestType.equalsIgnoreCase("3")) {

                    if (!headers.containsKey("Content-Type")) {
                        message("No Content-Type specified. Using application/json by default.",
                                mainColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
                        headers.put("Content-Type", "application/json");
                    }

                    out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "Enter request body [or leave empty]: ");
                    body = scanner.nextLine();

                    if (headers.getOrDefault("Content-Type", "").contains("json") && !body.trim().startsWith("{") && !body.isEmpty()) {
                        message("Warning: Content-Type is application/json but body doesn't appear to be JSON format.",
                                rejectionColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
                    }
                }

                sendHttpRequest(link, requestType, headers, body);
                insertControlChars('n', 1);

                displayConfirmation("Enter","y","+",
                        "to open and","n","-","to skip",
                        acceptanceColor, rejectionColor, layoutColor,getDefaultTextAlignment());
                out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "Make another request: ");

                String continueChoice = scanner.nextLine().trim().toLowerCase();
                insertControlChars('n', 1);

                if (!continueChoice.equalsIgnoreCase("y") && !continueChoice.equalsIgnoreCase("+")) {
                    insertControlChars('n', 1);
                    message("Status: " + getColor(acceptanceColor) + "✓", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
                    message("You are in the network page" + getColor(layoutColor) + ".", mainColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
                    return;
                }
            }
        } catch (Exception e) {
            insertControlChars('n', 1);
            message("Error: " + e.getMessage(), layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
        }
    }

    private static void sendHttpRequest(String link, String requestType, Map<String, String> headers, String body) {
        try {

            String normalizedRequestType = switch (requestType.toUpperCase()) {
                case "1", "GET" -> "GET";
                case "2", "POST" -> "POST";
                case "3", "PUT" -> "PUT";
                case "4", "DELETE" -> "DELETE";
                default -> {
                    message("Invalid request type. Using GET as default.", mainColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
                    yield "GET";
                }
            };

            message("Sending " + getColor(mainColor) + normalizedRequestType + getColor(layoutColor) + " request to " +
                    getColor(mainColor) + link + getColor(layoutColor) + "...", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);

            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(normalizedRequestType);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);

            if (headers.isEmpty()) {
                connection.setRequestProperty("User-Agent", "NetworkPage HTTP Tester");
                connection.setRequestProperty("Accept", "*/*");
                message("Using default headers: User-Agent: NetworkPage HTTP Tester, Accept: */*",
                        mainColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
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
                statusColor = String.valueOf(acceptanceColor);
            } else if (responseCode >= 300 && responseCode < 400) {
                statusColor = String.valueOf(mainColor);
            } else {
                statusColor = String.valueOf(rejectionColor);
            }

            message("Response Code: " + getColor(Integer.parseInt(statusColor)) + responseCode + getColor(layoutColor) +
                            " [" + getHttpStatusMessage(responseCode) + "]",
                    layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);

            message("Response Time: " + getColor(mainColor) + responseTime + " ms",
                    layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);

            String contentType = connection.getContentType();
            connection.disconnect();
        } catch (MalformedURLException e) {
            message("Error: Invalid URL format. Make sure to include http:// or https://",
                    rejectionColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
        } catch (UnknownHostException e) {
            message("Error: Unknown host. Please check the domain name or your internet connection.",
                    rejectionColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
        } catch (ConnectException e) {
            message("Error: Could not connect to the server. Server may be down or unreachable.",
                    rejectionColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
        } catch (SocketTimeoutException e) {
            message("Error: Connection timed out. The server took too long to respond.",
                    rejectionColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
        } catch (Exception e) {
            message("Error sending request: " + e.getMessage(),
                    rejectionColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
        }
    }

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

    @Contract(pure = true)
    private static @NotNull String getHttpStatusMessage(int statusCode) {
        return switch (statusCode) {
            case 200 -> "OK";
            case 201 -> "Created";
            case 204 -> "No Content";
            case 301 -> "Moved Permanently";
            case 302 -> "Found";
            case 304 -> "Not Modified";
            case 400 -> "Bad Request";
            case 401 -> "Unauthorized";
            case 403 -> "Forbidden";
            case 404 -> "Not Found";
            case 405 -> "Method Not Allowed";
            case 500 -> "Internal Server Error";
            case 502 -> "Bad Gateway";
            case 503 -> "Service Unavailable";
            case 504 -> "Gateway Timeout";
            default -> "Unknown Status";
        };
    }

    private static void displayNetworkInterfaces() {
        try {
            insertControlChars('n', 1);
            message("Network Interfaces:", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);

            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = networkInterfaces.nextElement();

                // Skip non-active interfaces if desired
                if (!netInterface.isUp())
                    continue;

                message("·  " + getColor(mainColor) + netInterface.getDisplayName() + getColor(layoutColor),
                        layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

                // Get MAC address
                byte[] mac = netInterface.getHardwareAddress();
                if (mac != null) {
                    StringBuilder macAddress = new StringBuilder();
                    for (int i = 0; i < mac.length; i++) {
                        macAddress.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
                    }
                    message("   MAC: " + getColor(mainColor) + macAddress + getColor(layoutColor),
                            layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
                } else {
                    message("   MAC: " + getColor(rejectionColor) + "N/A" + getColor(layoutColor),
                            layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
                }

                // Display IP addresses
                Enumeration<InetAddress> inetAddresses = netInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress address = inetAddresses.nextElement();
                    String addressType = address instanceof Inet4Address ? "IPv4" : "IPv6";
                    message("   " + addressType + ": " + getColor(mainColor) + address.getHostAddress() + getColor(layoutColor),
                            layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
                }

                message("   MTU: " + getColor(mainColor) + netInterface.getMTU() + getColor(layoutColor),
                        layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

                message("   Loopback: " + getColor(mainColor) + netInterface.isLoopback() + getColor(layoutColor),
                        layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

                message("   Point-to-point: " + getColor(mainColor) + netInterface.isPointToPoint() + getColor(layoutColor),
                        layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

                message("   Virtual: " + getColor(mainColor) + netInterface.isVirtual() + getColor(layoutColor),
                        layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

                insertControlChars('n', 1);
            }
        } catch (SocketException e) {
            message("Error retrieving network interfaces: " + e.getMessage(), rejectionColor,
                    getDefaultTextAlignment(), getDefaultDelay(), out::println);
        }
    }
}