package core.pages;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

import static core.configs.AppearanceConfigs.*;
import static core.configs.AppearanceConfigs.sysLayoutColor;
import static core.configs.TextConfigs.*;
import static core.configs.TextConfigs.message;
import static core.logic.CommandManager.exitPage;
import static core.pages.StartPage.mainMenuRerun;
import static core.ui.DisplayManager.clearTerminal;
import static core.ui.DisplayManager.scanner;
import static java.lang.System.out;

public class NetworkPage {
    public static void displayNetworkPage() {
        marginBorder(1, 2);
        message("Network:", sysLayoutColor, 58, 0, out::println);
        displayListOfCommands();

        while (true) {
            slowMotionText(0, searchingLineAlignment, false, getAnsi256Color(sysLayoutColor) + "> ",
                    "");
            String input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "internet speed test", "speed test", "/ist" -> testDownloadSpeed();
                //case "scan ports", "/sp" -> passwordCreatorMenu();
                case "trace rout", "/tr" -> pingHost();
                case "rerun", "/rr" -> mainMenuRerun();
                case "clear terminal", "/cl" -> clearTerminal();
                case "list of commands", "/lc" -> displayListOfCommands();
                case "exit", "/e" -> {
                    exitPage();
                    return;
                }
                default -> modifyMessage('n', 1);
            }
        }
    }

    public static void testDownloadSpeed() {
        String fileUrl = "https://speed.hetzner.de/100MB.bin";
        try {
            long startTime = System.currentTimeMillis();
            URL url = new URL(fileUrl);
            InputStream inputStream = url.openStream();
            byte[] buffer = new byte[8192];
            int bytesRead;
            long totalBytes = 0;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                totalBytes += bytesRead;
            }
            inputStream.close();
            long endTime = System.currentTimeMillis();

            double timeTakenInSeconds = (endTime - startTime) / 1000.0;
            double speedMbps = (totalBytes * 8) / (timeTakenInSeconds * 1_000_000);
            System.out.printf("Download Speed: %.2f Mbps%n", speedMbps);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void displayListOfCommands() {
        message("·  Internet Speed Test [" + getAnsi256Color(sysMainColor) + "/ist"
                + getAnsi256Color(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  Scan Ports [" + getAnsi256Color(sysMainColor) + "/sp"
                + getAnsi256Color(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  Trace Rout [" + getAnsi256Color(sysMainColor) + "/tr"
                + getAnsi256Color(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  List Of Commands [" + getAnsi256Color(sysMainColor) + "/lc"
                + getAnsi256Color(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::print);

        message("·  Exit [" + getAnsi256Color(sysMainColor) + "/e"
                + getAnsi256Color(sysLayoutColor) + "]", sysLayoutColor, 58, 0, out::println);
    }

    private static void pingHost(){
        Scanner scanner = new Scanner(System.in);
        try {
            out.print(alignment(58) + getAnsi256Color(sysLayoutColor) + "Enter host: ");
            String host = scanner.nextLine();

            Process process = Runtime.getRuntime().exec("ping -c 4 " + host);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                message(line, sysLayoutColor, 58, 0, out::print);
            }
            reader.close();
            modifyMessage('n', 1);
        } catch (Exception e) {
            message("Ping execution error: " + e.getMessage(),sysLayoutColor, 58, 0, out::println);
        }
    }
}
