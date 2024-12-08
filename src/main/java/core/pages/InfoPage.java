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
    public static String version = "A-0.7.5";

    public static void displayInfo() throws InterruptedException {
        modifyMessage('n', 2);
        message("Current version:",systemDefaultWhite,58,0,out::print);
        message(version,systemDefaultColor,58,0,out::print);
        message("Author: Nick Zozulia",systemDefaultWhite,58,0,out::print);
        modifyMessage('n', 1);

        messageInstruction("Enter","+","to open and","-","to skip");
        choice("Description", displayAppDescription());
        modifyMessage('n', 1);

        messageInstruction("Enter","+","to open and","-","to skip");
        choice("Github", openUri("https://github.com/Akillot/MultiCLIA"));
        marginBorder();
    }

    @Contract(pure = true)
    public static @NotNull Runnable displayAppDescription() {
        return () -> {
            modifyMessage('n', 1);
            marginBorder();
            modifyMessage('n', 1);

            message(getAnsi256Color(systemDefaultColor) + "MultiCLIA " + RESET
                            + getAnsi256Color(systemDefaultWhite) + "[" + getAnsi256Color(systemDefaultWhite)
                            + ITALIC + "Multi Command Line Interface App" + RESET
                            + getAnsi256Color(systemDefaultWhite) + "]\n\n" +
                            alignment(58) + "is an open-source application designed for \n" +
                            alignment(58) + "streamlined command-line interaction.\n\n" +
                            alignment(58) + "It provides a flexible, modular interface where\n" +
                            alignment(58) + "functionality is built on extensible components.\n\n" +
                            alignment(58) + "Users will soon be able to add new extensions\n" +
                            alignment(58) + "with any functionality, allowing limitless customization\n" +
                            alignment(58) + "and adaptation to specific workflows.",
                    systemDefaultWhite,58,0,out::print);
            modifyMessage('n', 2);
            border();
        };
    }
}