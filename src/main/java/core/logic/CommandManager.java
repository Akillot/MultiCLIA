package core.logic;

import core.commands.PackageUnifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.*;
import java.net.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static core.ui.essential.configs.essential.AppearanceConfigs.*;
import static core.ui.essential.configs.essential.DisplayManager.scanner;
import static core.ui.essential.configs.essential.TextConfigs.*;
import static core.ui.essential.essential.pages.StartPage.displayStartPage;

import static java.lang.System.out;

public class CommandManager {

    //HTTP request and additional methods
    public static void httpRequest(String userUri, @NotNull String requestType, @NotNull String text,
                                   String key, Map<String, String> headers) {
        try {
            URL url = new URI(userUri).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(requestType.toUpperCase());
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setDoOutput(!text.isEmpty());

            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            if (!text.isEmpty() && ("POST".equalsIgnoreCase(requestType)
                    || "PUT".equalsIgnoreCase(requestType) || "PATCH".equalsIgnoreCase(requestType))) {
                try (OutputStream os = connection.getOutputStream()) {
                    os.write(text.getBytes(StandardCharsets.UTF_8));
                }
            }

            int statusCode = connection.getResponseCode();
            InputStream responseStream = (statusCode >= 200 && statusCode < 300)
                    ? connection.getInputStream()
                    : connection.getErrorStream();

            String response = (responseStream != null) ? readResponse(responseStream) : "No response";

            String contentType = connection.getContentType();
            if (contentType != null && contentType.contains("application/json")) {
                parseJsonResponse(response, key, text);
            } else {
                message("Response:\n" + response, sysLayoutColor, getDefaultTextAlignment(),
                        getDefaultDelay(), out::print);
            }

        } catch (URISyntaxException | IOException e) {
            message("Request failed: " + getColor(sysRejectionColor) + e.getMessage(),
                    sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        }
    }

    private static @NotNull String readResponse(InputStream stream) throws IOException {
        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        }
        return response.toString();
    }

    private static void parseJsonResponse(String response, String key, String text) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            String value = jsonResponse.optString(key, "Key not found");
            message(text + value, sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        } catch (Exception e) {
            message("JSON parsing error: " + getColor(sysRejectionColor) + e.getMessage(),
                    sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        }
    }


    //Request user choice
    public static void choice(String title, Runnable action, int mainColor, int layoutColor, int rejectionColor) {
        out.print(alignment(getDefaultTextAlignment()) + getColor(mainColor) + title + RESET + ": " + RESET);

        String choice = scanner.nextLine().toLowerCase();
        switch (choice) {
            case "+", "y":
                try {
                    action.run();
                } catch (Exception e) {
                    message("Error executing action", rejectionColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
                    message("Status: " + getColor(rejectionColor) + "x", layoutColor,
                            getDefaultTextAlignment(), getDefaultDelay(), out::print);
                }
                break;

            case "-", "n":
                message("Status: " + getColor(rejectionColor) + "x", layoutColor, getDefaultTextAlignment(),
                        getDefaultDelay(), out::print);
                break;

            default:
                message("Invalid choice", rejectionColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
                message("Status: " + getColor(rejectionColor) + "x",
                        layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
                break;
        }
    }

    //Searching
    public static void searchCommands() {
        PackageUnifier registry = new PackageUnifier();
        try {
            slowMotionText(getDefaultDelay(), getSearchingLineAlignment(), false,
                    getColor(sysLayoutColor) + searchingArrow, "");
            String nameOfFunction = scanner.nextLine().toLowerCase();

            if (!registry.executeCommand(nameOfFunction)) {
                searchCommands();
            }
        }
        catch(Exception e){
            out.print("");
        }
    }

    @Contract(pure = true)
    public static @NotNull Runnable openUri(String userSite) {
        return () -> {
            try {
                URI uri = new URI(userSite);
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(uri);
                    message("\r   Status: " + getColor(sysAcceptanceColor) + "✓", sysLayoutColor,
                            getDefaultTextAlignment(),getDefaultDelay(),out::print);
                } else {
                    message("Error: Desktop or browse action not supported", sysRejectionColor,
                            getDefaultTextAlignment(), getDefaultDelay(), out::print);

                    message("Status: " + getColor(sysRejectionColor) + "x", sysLayoutColor,
                            getDefaultTextAlignment(), getDefaultDelay(), out::print);
                }
            } catch (URISyntaxException | IOException e) {
                message("Error opening URL", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
                message("Status: " + getColor(sysRejectionColor) + "x", sysLayoutColor, getDefaultTextAlignment(),
                        getDefaultDelay(), out::print);
            }
        };
    }

    public static void executeTerminalCommand(String command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) message(line, sysLayoutColor, getDefaultTextAlignment(),
                    getDefaultDelay(), out::print);

            reader.close();
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                message("Command failed with exit code: " + exitCode, sysLayoutColor, getDefaultTextAlignment(),
                        getDefaultDelay(), out::println);
            } else {
                insertControlChars('n', 1);
                message("Process completed "
                        + getColor(sysMainColor) + "successfully" + getColor(sysLayoutColor)
                        + ".", sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
            }
        } catch (IOException e) {
            message("I/O Error while executing command: " + e.getMessage(), sysLayoutColor, getDefaultTextAlignment(),
                    getDefaultDelay(), out::println);
        } catch (InterruptedException e) {
            message("Process was interrupted: " + e.getMessage(), sysLayoutColor, getDefaultTextAlignment(),
                    getDefaultDelay(), out::println);
            Thread.currentThread().interrupt();
        }
    }

    public static void processCommandWithHostInput(String command) {
        try {
            insertControlChars('n', 1);
            out.print(alignment(getDefaultTextAlignment()) + getColor(sysLayoutColor) + "Enter host [e.g., "
                    + getColor(27) + "g" + getColor(160) + "o" + getColor(220) + "o"
                    + getColor(27) + "g" + getColor(40) + "l" + getColor(160) + "e" + getColor(sysLayoutColor) + ".com]: ");
            String host = scanner.nextLine().trim();
            insertControlChars('n', 1);

            if (host.isEmpty()) {
                message("Host cannot be empty. Please enter a valid host.", sysLayoutColor, getDefaultTextAlignment(),
                        getDefaultDelay(), out::println);
                return;
            }

            command = command + " ";
            command += host;

            executeTerminalCommand(command);
        } catch (Exception e) {
            message("Execution error: " + e.getMessage(), sysLayoutColor, getDefaultTextAlignment(),
                    getDefaultDelay(), out::println);
        }
    }

    public static @NotNull Runnable copyToClipboard(String text) {
        return () -> {
            try {
                StringSelection selection = new StringSelection(text);
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
                message("Status: " + getColor(sysAcceptanceColor) + "✓", sysLayoutColor, getDefaultTextAlignment(),
                        getDefaultDelay(), out::println);
                message("Password copied " + getColor(sysAcceptanceColor) + "successfully" + getColor(sysLayoutColor) + ".",
                        sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
            } catch (Exception ex) {
                insertControlChars('n', 1);
                message("Error: " + ex.getMessage(), sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
            }
        };
    }

    public static void mainMenuRerun(){
        marginBorder(1,2);
        message("Status: " + getColor(sysAcceptanceColor) + "✓", sysLayoutColor,getDefaultTextAlignment(),
                getDefaultDelay(),out::print);

        message("Application restart" + getColor(sysLayoutColor) + ".", sysMainColor,getDefaultTextAlignment(),
                getDefaultDelay(), out::println);
        marginBorder(1,1);
        displayStartPage();
    }

    public static void exitPage(){
        marginBorder(2,2);
        message("Status: " + getColor(sysAcceptanceColor) + "✓", sysLayoutColor,getDefaultTextAlignment(),
                getDefaultDelay(),out::print);

        message("Terminated correctly" + getColor(sysLayoutColor) + ". "
                        + getColor(sysMainColor) + "You are in main menu" + getColor(sysLayoutColor) + ".", sysMainColor,
                getDefaultTextAlignment(),getDefaultDelay(),out::println);
        marginBorder(1,1);
    }

    public static void secretCommand() {
        try {
            openUri("https://www.youtube.com/watch?v=xvFZjo5PgG0");
        }
        catch (Exception ex){
            insertControlChars('n', 1);
            message("Error: " + ex.getMessage(), sysLayoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
        }
    }
}