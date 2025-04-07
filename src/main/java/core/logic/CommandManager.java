package core.logic;

import core.commands.PackageUnifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.*;
import java.net.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import static core.ui.essential.configs.appearance.AppearanceConfigs.*;
import static core.ui.essential.configs.DisplayManager.scanner;
import static core.ui.essential.configs.appearance.TextConfigs.*;
import static core.ui.essential.pages.StartPage.displayMenu;

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
                message("Response:\n" + response, layoutColor, getDefaultTextAlignment(),
                        getDefaultDelay(), out::print);
            }

        } catch (URISyntaxException | IOException e) {
            message("Request failed: " + getColor(rejectionColor) + e.getMessage(),
                    layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
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
            message(text + value, layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        } catch (Exception e) {
            message("JSON parsing error: " + getColor(rejectionColor) + e.getMessage(),
                    layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
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
                    getColor(layoutColor) + searchingArrow, "");
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
                    message("\r   Status: " + getColor(acceptanceColor) + "✓", layoutColor,
                            getDefaultTextAlignment(),getDefaultDelay(),out::print);
                } else {
                    message("Error: Desktop or browse action not supported", rejectionColor,
                            getDefaultTextAlignment(), getDefaultDelay(), out::print);

                    message("Status: " + getColor(rejectionColor) + "x", layoutColor,
                            getDefaultTextAlignment(), getDefaultDelay(), out::print);
                }
            } catch (URISyntaxException | IOException e) {
                message("Error opening URL", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
                message("Status: " + getColor(rejectionColor) + "x", layoutColor, getDefaultTextAlignment(),
                        getDefaultDelay(), out::print);
            }
        };
    }

    public static void executeTerminalCommand(String command) {
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) message(line, layoutColor, getDefaultTextAlignment(),
                    getDefaultDelay(), out::print);

            reader.close();
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                message("Command failed with exit code: " + exitCode, layoutColor, getDefaultTextAlignment(),
                        getDefaultDelay(), out::println);
            } else {
                insertControlChars('n', 1);
                message("Process completed "
                        + getColor(mainColor) + "successfully" + getColor(layoutColor)
                        + ".", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
            }
        } catch (IOException e) {
            message("I/O Error while executing command: " + e.getMessage(), layoutColor, getDefaultTextAlignment(),
                    getDefaultDelay(), out::println);
        } catch (InterruptedException e) {
            message("Process was interrupted: " + e.getMessage(), layoutColor, getDefaultTextAlignment(),
                    getDefaultDelay(), out::println);
            Thread.currentThread().interrupt();
        }
    }

    public static void processCommandWithHostInput(String command) {
        try {
            insertControlChars('n', 1);
            out.print(alignment(getDefaultTextAlignment()) + getColor(layoutColor) + "Enter host [e.g., "
                    + getColor(27) + "g" + getColor(160) + "o" + getColor(220) + "o"
                    + getColor(27) + "g" + getColor(40) + "l" + getColor(160) + "e" + getColor(layoutColor) + ".com]: ");
            String host = scanner.nextLine().trim();
            insertControlChars('n', 1);

            if (host.isEmpty()) {
                message("Host cannot be empty. Please enter a valid host.", layoutColor, getDefaultTextAlignment(),
                        getDefaultDelay(), out::println);
                return;
            }

            command = command + " ";
            command += host;

            executeTerminalCommand(command);
        } catch (Exception e) {
            message("Execution error: " + e.getMessage(), layoutColor, getDefaultTextAlignment(),
                    getDefaultDelay(), out::println);
        }
    }

    public static @NotNull Runnable copyToClipboard(String text) {
        return () -> {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

            try {
                clipboard.setContents(new StringSelection(""), new ClipboardOwner() {
                    @Override
                    public void lostOwnership(Clipboard clipboard, Transferable contents) {
                    }
                });

                Thread.sleep(50);
                StringSelection selection = new StringSelection(text);
                clipboard.setContents(selection, selection);  // Указываем себя как владельца

                try {
                    String copiedText = (String) clipboard.getData(DataFlavor.stringFlavor);
                    if (!text.equals(copiedText)) {
                        throw new IllegalStateException("Clipboard verification failed");
                    }
                    printSuccessMessage();

                } catch (Exception verificationEx) {
                    printErrorMessage("Verification failed: " + verificationEx.getMessage());
                }

            } catch (IllegalStateException e) {
                printErrorMessage("Clipboard is locked. Try again later");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                printErrorMessage("Operation interrupted");
            } catch (Exception e) {
                printErrorMessage("Copy error: " + e.getMessage());
            }
        };
    }

    private static void printSuccessMessage() {
        message("Status: " + getColor(acceptanceColor) + "✓",
                layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::println);
        message("Text copied successfully" + getColor(layoutColor) + ".",
                layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
    }

    private static void printErrorMessage(String error) {
        insertControlChars('n', 1);
        message("Error: " + error,
                layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
    }

    public static void mainMenuRerun(){
        marginBorder(1,2);
        message("Status: " + getColor(acceptanceColor) + "✓", layoutColor,getDefaultTextAlignment(),
                getDefaultDelay(),out::print);

        message("Application restart" + getColor(layoutColor) + ".", mainColor,getDefaultTextAlignment(),
                getDefaultDelay(), out::println);
        marginBorder(1,1);
        displayMenu();
    }

    public static void exitPage(){
        marginBorder(2,2);
        message("Status: " + getColor(acceptanceColor) + "✓", layoutColor,getDefaultTextAlignment(),
                getDefaultDelay(),out::print);

        message("Terminated correctly" + getColor(layoutColor) + ". "
                        + getColor(mainColor) + "You are in main menu" + getColor(layoutColor) + ".", mainColor,
                getDefaultTextAlignment(),getDefaultDelay(),out::println);
        marginBorder(1,1);
    }
}