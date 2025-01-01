package core.pages;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static core.logic.AppearanceConfigs.*;
import static core.logic.CommandManager.*;
import static core.logic.TextConfigs.*;
import static core.logic.TextConfigs.alignment;
import static java.lang.System.out;

public  class InfoPage {

    @Contract(pure = true)
    public static @NotNull String getVersion() {
        String appVersion = "A-0.8.1.1";
        return getAnsi256Color(systemFirstColor) + appVersion;
    }

    public static void displayInfo() throws InterruptedException {
        modifyMessage('n', 2);
        message("Current version: " + getVersion(), systemLayoutColor,58,0,out::print);
        message("Author: Nick Zozulia", systemLayoutColor,58,0,out::print);
        modifyMessage('n', 1);

        displayOs();
        displayCpuInfo();
        displayHomeDirectory();

        modifyMessage('n', 1);
        displayJavaInfo();

        modifyMessage('n', 2);
        displayConfirmation("Enter","to open and","to skip",
                systemAcceptanceColor, systemRejectionColor, systemLayoutColor);
        choice("Description", displayAppDescription(),
                systemFirstColor, systemRejectionColor, systemLayoutColor);
        modifyMessage('n', 2);
        choice("Important links", InfoPage::displayImportantLinks,
                systemFirstColor, systemRejectionColor, systemLayoutColor);

        marginBorder(2,1);
    }

    private static void displayOs(){
        String operatingSystem = System.getProperty("os.name");
        message("OS: " + getAnsi256Color(systemFirstColor) + operatingSystem, systemLayoutColor,58,0,out::print);
    }

    private static void displayCpuInfo() {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        message("CPU Cores: " + getAnsi256Color(systemFirstColor) + availableProcessors,
                systemLayoutColor, 58, 0, out::print);
    }

    private static void displayJavaInfo() {
        String javaVersion = System.getProperty("java.version");
        String javaVendor = System.getProperty("java.vendor");
        message("Java Version: " + getAnsi256Color(systemFirstColor) + javaVersion,
                systemLayoutColor, 58, 0, out::print);

        message("Java Vendor: " + getAnsi256Color(systemFirstColor) + javaVendor,
                systemLayoutColor, 58, 0, out::print);
    }

    private static void displayHomeDirectory() {
        String userHome = System.getProperty("user.home");
        message("Home Directory: " + getAnsi256Color(systemFirstColor) + userHome,
                systemLayoutColor, 58, 0, out::print);
    }


    private static void displayImportantLinks(){
        modifyMessage('n', 1);

        choice(getAnsi256Color(27) + "G" + getAnsi256Color(160) + "m" + getAnsi256Color(220)
                + "a" + getAnsi256Color(27) + "i"
                + getAnsi256Color(47) + "l",
                openUri("mailto:" + "nickzozulia@gmail.com?subject=Hello&body=I%20have%20a%20question."),
                systemFirstColor, systemRejectionColor, systemLayoutColor);

        modifyMessage('n', 2);

        choice(getAnsi256Color(systemLayoutColor) + "Github", openUri("https://github.com/Akillot/MultiCLIA"),
                systemFirstColor, systemRejectionColor, systemLayoutColor);

        modifyMessage('n', 2);

        choice(getAnsi256Color(systemLayoutColor) +"License", openUri("https://github.com/Akillot/MultiCLIA?tab=License-1-ov-file"),
                systemFirstColor, systemRejectionColor, systemLayoutColor);
    }

    @Contract(pure = true)
    private static @NotNull Runnable displayAppDescription() {
        return () -> {
            marginBorder(2,2);
            message( "MultiCLIA " + getAnsi256Color(systemLayoutColor) + "[" + getAnsi256Color(systemLayoutColor)
                            + "Multi Command Line Interface App" + getAnsi256Color(systemLayoutColor) + "]\n\n" +
                            alignment(58) + "is an open-source application designed for \n" +
                            alignment(58) + "streamlined command-line interaction.\n\n" +
                            alignment(58) + "It provides a flexible, modular interface where\n" +
                            alignment(58) + "functionality is built on extensible components.\n\n" +
                            alignment(58) + "Users will soon be able to add new extensions\n" +
                            alignment(58) + "with any functionality, allowing limitless customization\n" +
                            alignment(58) + "and adaptation to specific workflows.",
                    systemLayoutColor,58,0,out::print);
            modifyMessage('n', 2);
            border();
        };
    }
}