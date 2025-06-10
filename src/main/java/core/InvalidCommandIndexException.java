package core;

import static core.CommandManager.terminateProgram;
import static core.ui.configs.AppearanceConfigs.*;
import static core.ui.configs.AppearanceConfigs.getLayoutColor;
import static core.ui.configs.TextConfigs.alignment;
import static java.lang.System.out;

public class InvalidCommandIndexException extends RuntimeException {
    public InvalidCommandIndexException() {
            out.print(alignment(getDefaultLogoAlignment() + 4)
              + getBackColor(getRejectionColor()) + getColor(getLayoutColor()) + " Invalid command index "
              + RESET + getColor(getLayoutColor()) + " Go to core/commands/CommandHandler and check the ExecuteCommands()");
          terminateProgram();
    }
}
