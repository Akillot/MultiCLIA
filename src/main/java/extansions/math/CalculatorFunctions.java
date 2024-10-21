package extansions.math;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import static ui.layout.CommandManager.exitExtension;
import static ui.layout.DisplayManager.errorAscii;


public class CalculatorFunctions {
    public static void calculateOperation(String mathStatement) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");

        try {
            Object result = engine.eval(mathStatement);
            System.out.print("= " + result);
            System.out.println("\n");

            if(result != null && result.toString().equalsIgnoreCase("exit")) {
                exitExtension();
            }
        } catch (ScriptException e) {
            errorAscii();
        }
    }
}
