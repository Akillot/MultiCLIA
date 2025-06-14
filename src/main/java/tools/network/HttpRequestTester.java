package tools.network;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static core.ui.configs.AppearanceConfigs.getAcceptanceColor;
import static core.ui.configs.AppearanceConfigs.getColor;
import static core.ui.configs.AppearanceConfigs.getDefaultDelay;
import static core.ui.configs.AppearanceConfigs.getDefaultTextAlignment;
import static core.ui.configs.AppearanceConfigs.getLayoutColor;
import static core.ui.configs.AppearanceConfigs.getMainColor;
import static core.ui.configs.AppearanceConfigs.getRejectionColor;
import static core.ui.configs.TextConfigs.*;
import static java.lang.System.out;

public class HttpRequestTester {

    static void displayHttpTesting() {
        Scanner scanner = new Scanner(System.in);

        try {
            while (true) {
                insertControlChars('n', 1);
                out.print(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor()) + "Enter a URL [or "
                        + getColor(getMainColor()) + "q" + getColor(getLayoutColor()) + " to quit]: ");
                String link = scanner.nextLine().trim();

                if (link.equalsIgnoreCase("q")) {
                    insertControlChars('n', 1);
                    return;
                }

                if (!link.toLowerCase().startsWith("http://") && !link.toLowerCase().startsWith("https://"))
                    link = "https://" + link;

                out.print(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor()) + "Select request type [" +
                        getColor(getMainColor()) + "GET" + getColor(getLayoutColor()) + "|" + getColor(getMainColor()) + "1 " + getColor(getLayoutColor()) +
                        getColor(218) + "POST" + getColor(getLayoutColor()) + "|" + getColor(218) + "2 " + getColor(getLayoutColor()) +
                        getColor(206) + "PUT" + getColor(getLayoutColor()) + "|" + getColor(206) + "3 " + getColor(getLayoutColor()) +
                        getColor(204) + "DELETE" + getColor(getLayoutColor()) + "|" + getColor(204) + "4" + getColor(getLayoutColor()) + "]: ");

                if (link.equalsIgnoreCase("q")) {
                    insertControlChars('n', 1);
                    return;
                }

                String requestType = scanner.nextLine().trim().toUpperCase();

                Map<String, String> headers = new HashMap<>();

                insertControlChars('n', 1);
                message("Common headers: Content-Type, Authorization, User-Agent, Accept",
                        getLayoutColor(),
                        getDefaultTextAlignment(),
                        getDefaultDelay(),
                        out::println);

                while (true) {
                    out.print(alignment(getDefaultTextAlignment()) + getColor(getLayoutColor()) + "Enter header [key:value] or press "
                            + getColor(getMainColor()) + "Enter" + getColor(getLayoutColor()) + " to continue: ");

                    String headerInput = scanner.nextLine().trim();

                    if (headerInput.isEmpty()) break;
                    if (headerInput.equalsIgnoreCase("q")) return;

                    String[] parts = headerInput.split(":", 2);
                    if (parts.length == 2) {
                        headers.put(parts[0].trim(), parts[1].trim());
                        message("Header added: " + getColor(getAcceptanceColor()) + parts[0].trim() + ": " + parts[1].trim(),
                                getLayoutColor(),
                                getDefaultTextAlignment(),
                                getDefaultDelay(),
                                out::println);
                    } else {
                        insertControlChars('n', 1);
                        message("Invalid header format" + getColor(getLayoutColor()) + ". Use key:value.",
                                getRejectionColor(),
                                getDefaultTextAlignment(),
                                getDefaultDelay(),
                                out::println);
                    }
                }

                String body = "";
                if (requestType.equalsIgnoreCase("POST")
                        || requestType.equalsIgnoreCase("PUT")
                        || requestType.equalsIgnoreCase("2")
                        || requestType.equalsIgnoreCase("3")) {

                    if (!headers.containsKey("Content-Type")) {
                        message("No Content-Type specified" + getColor(getLayoutColor()) + ". Using application/json by default.",
                                getRejectionColor(),
                                getDefaultTextAlignment(),
                                getDefaultDelay(),
                                out::println);

                        headers.put("Content-Type", "application/json");
                    }

                    out.print(alignment(getDefaultTextAlignment())
                            + getColor(getLayoutColor()) + "Enter request body [or press " + getColor(getMainColor()) + "Enter"
                            + getColor(getLayoutColor()) + " leave empty]: ");
                    body = scanner.nextLine();

                    if (headers.getOrDefault("Content-Type", "").contains("json")
                            && !body.trim().startsWith("{")
                            && !body.isEmpty()) {
                        message("Warning: Content-Type is application/JSON, but body doesn't appear to be JSON format.",
                                getLayoutColor(),
                                getDefaultTextAlignment(),
                                getDefaultDelay(),
                                out::println);
                    }
                }

                sendHttpRequest(link, requestType, headers, body);
                insertControlChars('n', 1);

                displayConfirmation("Enter", "y", "+",
                        "to open and", "n", "-", "to skip",
                        getAcceptanceColor(),
                        getRejectionColor(),
                        getLayoutColor(),
                        getDefaultTextAlignment());

                out.print(alignment(getDefaultTextAlignment())
                        + getColor(getLayoutColor()) + "Make another request: ");

                String continueChoice = scanner.nextLine().trim().toLowerCase();
                insertControlChars('n', 1);

                if (!continueChoice.equalsIgnoreCase("y")
                        && !continueChoice.equalsIgnoreCase("+")) {
                    return;
                }
            }
        } catch (Exception e) {
            insertControlChars('n', 1);
            message("Error: " + getColor(getLayoutColor()) + e.getMessage(),
                    getRejectionColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::println);
        }
    }

    private static void sendHttpRequest(String link,
                                        String requestType,
                                        Map<String, String> headers,
                                        String body) {
        try {

            String normalizedRequestType = switch (requestType.toUpperCase()) {
                case "1", "GET" -> "GET";
                case "2", "POST" -> "POST";
                case "3", "PUT" -> "PUT";
                case "4", "DELETE" -> "DELETE";
                default -> {
                    message("Invalid request type." + getColor(getLayoutColor()) + " Using GET as default.",
                            getRejectionColor(),
                            getDefaultTextAlignment(),
                            getDefaultDelay(),
                            out::println);
                    yield "GET";
                }
            };

            message("Sending " + getColor(getMainColor()) + normalizedRequestType
                            + getColor(getLayoutColor()) + " request to "
                            + getColor(getMainColor()) + link + getColor(getLayoutColor()) + "...",
                    getLayoutColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::println);

            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(normalizedRequestType);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);

            if (headers.isEmpty()) {
                connection.setRequestProperty("User-Agent", "NetworkPage HTTP Tester");
                connection.setRequestProperty("Accept", "*/*");
                message("Using default headers: User-Agent: NetworkPage HTTP Tester, Accept: */*",
                        getLayoutColor(),
                        getDefaultTextAlignment(),
                        getDefaultDelay(),
                        out::println);
            } else for (Map.Entry<String, String> entry : headers.entrySet())
                connection.setRequestProperty(entry.getKey(), entry.getValue());

            if (!body.isEmpty()
                    && (normalizedRequestType.equals("POST")
                    || normalizedRequestType.equals("PUT"))) {

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

            InputStream responseStream = responseCode >= 400
                    ? connection.getErrorStream()
                    : connection.getInputStream();

            String response = readStream(responseStream);
            insertControlChars('n', 1);

            String statusColor;
            if (responseCode >= 200 && responseCode < 300) statusColor = String.valueOf(getAcceptanceColor());
            else if (responseCode >= 300 && responseCode < 400) statusColor = String.valueOf(getMainColor());
            else statusColor = String.valueOf(getRejectionColor());

            message("Response Code: " + getColor(Integer.parseInt(statusColor))
                            + responseCode + getColor(getLayoutColor()) +
                            " [" + getHttpStatusMessage(responseCode) + "]",
                    getLayoutColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::print);

            message("Response Time: " + getColor(getMainColor()) + responseTime + " ms",
                    getLayoutColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::println);

            String contentType = connection.getContentType();
            connection.disconnect();

        } catch (MalformedURLException e) {
            message("Error" + getColor(getLayoutColor())
                            + ": Invalid URL format. Make sure to include http:// or https://",
                    getRejectionColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::println);

        } catch (UnknownHostException e) {
            message("Error" + getColor(getLayoutColor())
                            + ": Unknown host. Please check the domain name or your internet connection.",
                    getRejectionColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::println);

        } catch (ConnectException e) {
            message("Error" + getColor(getLayoutColor())
                            + ": Could not connect to the server. Server may be down or unreachable.",
                    getRejectionColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::println);

        } catch (SocketTimeoutException e) {
            message("Error" + getColor(getLayoutColor())
                            + ": Connection timed out. The server took too long to respond.",
                    getRejectionColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::println);

        } catch (Exception e) {
            message("Error" + getColor(getLayoutColor())
                            + " sending request: " + e.getMessage(),
                    getRejectionColor(),
                    getDefaultTextAlignment(),
                    getDefaultDelay(),
                    out::println);
        }
    }

    private static @NotNull String readStream(InputStream stream) throws IOException {
        if (stream == null) return alignment(getDefaultTextAlignment())
                + getColor(getAcceptanceColor()) + "No response body" + getColor(getLayoutColor()) + ".";

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) response.append(line).append("\n");
        return response.toString();
    }

    private static @NotNull String formatJson(String json) {
        if (json == null || json.isEmpty()) return "{}";

        StringBuilder formatted = new StringBuilder();
        int indentLevel = 0;
        boolean inQuotes = false;

        for (char c : json.toCharArray()) {
            if (c == '"' && (formatted.isEmpty()
                    || formatted.charAt(formatted.length() - 1)
                    != '\\')) inQuotes = !inQuotes;

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
                } else if (c == ':') formatted.append(c).append(" ");
                else formatted.append(c);

            } else formatted.append(c);
        }

        return formatted.toString();
    }

    @Contract(pure = true)
    private static @NotNull String getHttpStatusMessage(int statusCode) {
        return switch (statusCode) {
            case 100 -> "Continue";
            case 101 -> "Switching Protocols";
            case 102 -> "Processing [WebDAV]";
            case 103 -> "Early Hints";
            case 200 -> "OK";
            case 201 -> "Created";
            case 202 -> "Accepted";
            case 203 -> "Non-Authoritative Information";
            case 204 -> "No Content";
            case 205 -> "Reset Content";
            case 206 -> "Partial Content";
            case 207 -> "Multi-Status [WebDAV]";
            case 208 -> "Already Reported [WebDAV]";
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
            case 422 -> "Unprocessable Entity [WebDAV]";
            case 423 -> "Locked [WebDAV]";
            case 424 -> "Failed Dependency [WebDAV]";
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
            case 507 -> "Insufficient Storage [WebDAV]";
            case 508 -> "Loop Detected [WebDAV]";
            case 510 -> "Not Extended";
            case 511 -> "Network Authentication Required";
            default -> "Unknown Status";
        };
    }
}