package core.logic;

import static core.logic.ColorConfigs.getAnsi256Color;
import static core.logic.ColorConfigs.systemDefaultWhite;
import static core.logic.TextConfigs.modifyMessage;
import static java.lang.System.out;

public class BorderConfigs {
    public static final int borderWidth = 62;
    private static String[] symbolsOfBorder = new String[]{"━"};

    public static void border() {
        out.print(getAnsi256Color(systemDefaultWhite) + symbolsOfBorder[0]);
        for (int i = 0; i < 115; i++) {
            out.print(symbolsOfBorder[0]);
        }
        out.println(symbolsOfBorder[0]);
    }

    public static void marginBorder() {
        modifyMessage('n', 1);
        border();
        modifyMessage('n', 1);
    }
}