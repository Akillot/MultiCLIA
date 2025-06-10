package core.commands;

import core.InvalidCommandIndexException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static core.commands.CommandHandler.*;
import static org.junit.jupiter.api.Assertions.*;

class CommandHandlerTest {

    private Map<String, Runnable> commandMap;

    @BeforeEach
    void setUp() {
        commandMap = new HashMap<>();
        registerCommands(commandMap);
    }

    @Test
    void testCommandRegistration() {
        for (String cmd : fullCmds) {
            assertTrue(commandMap.containsKey(cmd), "Missing command: " + cmd);
        }
        for (String cmd : shortCmds) {
            assertTrue(commandMap.containsKey(cmd), "Missing short command: " + cmd);
        }
    }

    @Test
    void testCommandExecution() {
        for (String cmd : fullCmds) {
            assertNotNull(commandMap.get(cmd), "Command " + cmd + " should have an action");
        }
    }

    @Test
    void testInvalidCommandIndex() {
        Exception exception = assertThrows(InvalidCommandIndexException.class, () -> ExecuteCommand(999).run());
        assertTrue(true);
    }
}
