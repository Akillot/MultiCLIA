package core.pages;

import org.jline.reader.*;
import org.jline.terminal.*;

public class TemporaryExistingPage {
    public static void displayTemporaryStart() throws Exception {
        Terminal terminal = TerminalBuilder.terminal();
        LineReader reader = LineReaderBuilder.builder().terminal(terminal).build();

        String line;
        while((line = reader.readLine("> ")) != null){
            if("exit".equalsIgnoreCase(line)){
                System.out.println("Goodbye!");
                break;
            }
            System.out.println("You entered: " + line);
        }
    }
}
