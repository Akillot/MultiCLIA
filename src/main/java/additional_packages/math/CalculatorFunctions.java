package additional_packages.math;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import static ui.layout.BasicFunctions.exitApp;
import static ui.layout.ColorWork.*;
import static ui.layout.ThemesWork.displayErrorAscii;

public class CalculatorFunctions {
    public static void calculateOperation(String mathStatement) {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");

        try {
            Object result = engine.eval(mathStatement);
            System.out.print("= " + result);
            System.out.println("\n");

            if(result != null && result.toString().equalsIgnoreCase("exit")) {
                exitApp();
            }
        } catch (ScriptException e) {
            displayErrorAscii();
            displayContent("Invalid input", "red", 0);
        }
    }
}
