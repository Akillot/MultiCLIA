package core.pages;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import static core.logic.BorderConfigs.border;
import static core.logic.BorderConfigs.marginBorder;
import static core.logic.ColorConfigs.*;
import static core.logic.CommandManager.*;
import static core.logic.TextConfigs.*;
import static core.logic.TextConfigs.alignment;
import static java.lang.System.out;

public  class InfoPage {

    @Contract(pure = true)
    public static @NotNull String getVersion() {
        String appVersion = "A-0.8.0";
        return getAnsi256Color(systemMainColor) + appVersion + RESET;
    }

    public static void displayInfo() throws InterruptedException {
        modifyMessage('n', 2);
        message("Current version:", systemLayoutColor,58,0,out::print);
        message(getVersion(), systemMainColor,58,0,out::print);
        message("Author: Nick Zozulia", systemLayoutColor,58,0,out::print);
        modifyMessage('n', 2);

        messageInstruction("Enter","+","to open and","-","to skip");
        choice("Description", displayAppDescription());
        modifyMessage('n', 1);

        messageInstruction("Enter","+","to open and","-","to skip");
        choice("Github", openUri("https://github.com/Akillot/MultiCLIA"));
        modifyMessage('n', 1);

        messageInstruction("Enter","+","to open and","-","to skip");
        choice("MultiCLIA License", openUri("https://github.com/Akillot/MultiCLIA?tab=License-1-ov-file"));
        marginBorder();
    }

    @Contract(pure = true)
    public static @NotNull Runnable displayAppDescription() {
        return () -> {
            modifyMessage('n', 1);
            marginBorder();
            modifyMessage('n', 1);

            message(getAnsi256Color(systemMainColor) + "MultiCLIA " + RESET
                            + getAnsi256Color(systemLayoutColor) + "[" + getAnsi256Color(systemLayoutColor)
                            + ITALIC + "Multi Command Line Interface App" + RESET
                            + getAnsi256Color(systemLayoutColor) + "]\n\n" +
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