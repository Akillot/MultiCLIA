package core.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CommandHandlerTest {

    private Map<String, Runnable> commandMap;

    @BeforeEach
    void setUp() {
        commandMap = new HashMap<>();
        CommandHandler.registerCommands(commandMap);
    }

    @Test
    void testCommandRegistration() {
        for (String cmd : CommandHandler.fullCmds) {
            assertTrue(commandMap.containsKey(cmd), "Missing command: " + cmd);
        }
        for (String cmd : CommandHandler.shortCmds) {
            assertTrue(commandMap.containsKey(cmd), "Missing short command: " + cmd);
        }
    }

    @Test
    void testCommandExecution() {
        for (String cmd : CommandHandler.fullCmds) {
            assertNotNull(commandMap.get(cmd), "Command " + cmd + " should have an action");
        }
    }

    @Test
    void testInvalidCommandIndex() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            CommandHandler.getCommandAction(999).run();
        });

        assertTrue(exception.getMessage().contains("Invalid command index"));
    }
}
