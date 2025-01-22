package core.logic;

import core.commands.PackageUnifier;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.net.URI;

import static core.configs.AppearanceConfigs.*;
import static core.pages.StartPage.displayStartPage;
import static core.ui.DisplayManager.*;

import static core.configs.TextConfigs.*;
import static java.lang.System.exit;
import static java.lang.System.out;

public class CommandManager {

    //HTTP request and additional methods
    public static @Nullable String httpRequest(String userUri, String requestType, String text, String key) {
        try {
            URL url = new URI(userUri).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(requestType);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int statusCode = connection.getResponseCode();
            if (statusCode != 200) {
                message("HTTP error: " + getAnsi256Color(sysRejectionColor)
                        + statusCode, sysLayoutColor, 58, 0, out::print);
                return null;
            }

            String response = readResponse(connection);
            return parseJsonResponse(response, key, text);
        } catch (URISyntaxException | IOException e) {
            message("Request failed: " + getAnsi256Color(sysRejectionColor)
                    + e.getMessage(), sysLayoutColor, 58, 0, out::print);
            return null;
        }
    }

    private static @NotNull String readResponse(HttpURLConnection connection) throws IOException {
        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        }
        return response.toString();
    }

    private static @Nullable String parseJsonResponse(String response, String key, String text) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            String value = jsonResponse.optString(key, "Key not found");
            message(text + " " + value, sysLayoutColor, 58, 0, out::print);
            return response;
        } catch (Exception e) {
            message("JSON parsing error: " + getAnsi256Color(sysRejectionColor)
                    + e.getMessage(), sysLayoutColor, 58, 0, out::print);
            return null;
        }
    }

    //Request user choice
    public static void choice(String title, Runnable action, int mainColor, int layoutColor, int rejectionColor) {
        out.print(alignment(58) + getAnsi256Color(mainColor) + title + RESET + ": " + RESET);

        String choice = scanner.nextLine().toLowerCase();
        switch (choice) {
            case "+", "y":
                try {
                    action.run();
                } catch (Exception e) {
                    message("Error executing action", rejectionColor, 58, 0, out::print);
                    message("Status: " + getAnsi256Color(rejectionColor) + "x", layoutColor, 58, 0, out::print);
                }
                break;

            case "-", "n":
                message("Status: " + getAnsi256Color(rejectionColor) + "x", layoutColor, 58, 0, out::print);
                break;

            default:
                message("Invalid choice", rejectionColor, 58, 0, out::print);
                message("Status: " + getAnsi256Color(rejectionColor) + "x",
                        layoutColor, 58, 0, out::print);
                break;
        }
    }

    public static void switchLogo(String @NotNull [] logo, int alignment) {
        String[] colors = getColorsForLogo();

        for (int i = 0; i < logo.length; i++) {
            String coloredText = colors[i % colors.length] + logo[i] + RESET;
            message(coloredText, sysLayoutColor, alignment, 0, System.out::print);
        }
    }

    private static String @NotNull [] getColorsForLogo() {
        return new String[]{
                getAnsi256Color(219), getAnsi256Color(183),
                getAnsi256Color(147), getAnsi256Color(218),
                getAnsi256Color(182), getAnsi256Color(218)
        };
    }

    //Searching
    public static void searchCommands() {
        PackageUnifier registry = new PackageUnifier();
        try {
            slowMotionText(0, searchingLineAlignment, false,
                    getAnsi256Color(sysLayoutColor) + "> ", "");
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
                    message("\r   Status: " + getAnsi256Color(sysAcceptanceColor) + "✓", sysLayoutColor,58,0,out::print);
                } else {
                    message("Error: Desktop or browse action not supported", sysRejectionColor,
                            58, 0, out::print);
                    message("Status: " + getAnsi256Color(sysRejectionColor) + "x", sysLayoutColor, 58, 0, out::print);
                }
            } catch (URISyntaxException | IOException e) {
                message("Error opening URL", sysLayoutColor, 58, 0, out::print);
                message("Status: " + getAnsi256Color(sysRejectionColor) + "x", sysLayoutColor, 58, 0, out::print);
            }
        };
    }

    public static void processCommandWithHostInput(String command) {
        try {
            modifyMessage('n', 1);
            out.print(alignment(58) + getAnsi256Color(sysLayoutColor) + "Enter host [e.g., google.com]: ");
            String host = scanner.nextLine().trim();
            modifyMessage('n', 1);

            if (host.isEmpty()) {
                message("Host cannot be empty. Please enter a valid host.", sysLayoutColor, 58, 0, out::println);
                return;
            }

            command = command + " ";
            command += host;

            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                message(line, sysLayoutColor, 58, 0, out::print);
            }

            reader.close();
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                message("Command failed with exit code: " + exitCode, sysLayoutColor, 58, 0, out::println);
            } else {
                modifyMessage('n', 1);
                message("Process completed "
                        + getAnsi256Color(sysMainColor) + "successfully" + getAnsi256Color(sysLayoutColor)
                        + ".", sysLayoutColor, 58, 0, out::println);
            }
        } catch (Exception e) {
            message("Execution error: " + e.getMessage(), sysLayoutColor, 58, 0, out::println);
        }
    }

    public static void mainMenuRerun(){
        marginBorder(1,10);
        displayStartPage();
    }

    public static void mainMenuRerunMargin(){
        modifyMessage('n',1);
        mainMenuRerun();
    }

    public static void exitPage(){
        marginBorder(2,2);
        message("\r   Status: " + getAnsi256Color(sysAcceptanceColor) + "✓", sysLayoutColor,58,0,out::print);
        message("Terminated correctly" + getAnsi256Color(sysLayoutColor) + ". "
                        + getAnsi256Color(sysMainColor) + "You are in main menu" + getAnsi256Color(sysLayoutColor) + ".", sysMainColor,
                58,0,out::println);
        marginBorder(1,1);
    }

    public static void terminateProgram() {
        marginBorder(1,2);
        message("\r    Status: " + getAnsi256Color(sysAcceptanceColor) + "✓", sysLayoutColor,58,0,out::print);
        message("Program terminated quickly correctly" + getAnsi256Color(sysLayoutColor) + ".", sysMainColor,
                56,0,out::print);
        modifyMessage('n', 2);
        exit(0);
    }
}