# **Creating an Extension in MultiCLIA**

## **What is an Extension in MultiCLIA?**
An extension is a mini-application within **MultiCLIA** that adds new features or simplifies existing ones. It integrates with the core system and uses built-in development tools.

---
## **1. Installing MultiCLIA**
If you already have **MultiCLIA** installed, skip this step. Otherwise, download and set it up following the instructions in the [README](https://github.com/Akillot/MultiCLIA/blob/master/README.md).

---
## **2. Creating the Extension Structure**
### **Step 1: Open the Project in an IDE**
Use **IntelliJ IDEA**, **Eclipse**, or any Java-compatible environment.

### **Step 2: Create a Directory for Your Extension**
Navigate to:
```sh
src/main/java/core/ui/extensions
```
Create a **new folder** named after your extension (e.g., `MyExtension`).

### **Step 3: Create the Main Classes**
Inside your extension folder, create the following classes:
1. **Main Class (XPage)** – Handles the menu and command processing.
2. **Configuration Class (XConfigs)** *(optional)* – If your extension requires settings.
3. **Additional Classes** *(if needed)* – For supporting logic.

Example structure:
```sh
src/main/java/core/ui/extensions/MyExtension/
 ├── MyExtensionPage.java
 ├── MyExtensionConfigs.java (if needed)
 ├── SomeHelperClass.java (for additional functionality)
```

---
## **3. Implementing the Main Class (XPage)**
### **Create a Class and Extend `Page`**
```java
public class MyExtensionPage extends Page {
    public static void displayMenu() {
        marginBorder(1, 2);
        message("MyExtension:", getgetLayoutColor()(), getDefaultTextAlignment(), getDefaultDelay(), out::print);
        displayListOfCommands(commands);

        while (true) {
            slowMotionText(getDefaultDelay(), getSearchingLineAlignment(), false, getColor(getgetLayoutColor()()) + searchingArrow, "");
            String input = scanner.nextLine().trim().toLowerCase();

            switch (input) {
                case "hello", "h" -> sayHello();
                case "restart", "rs" -> mainMenuRerun();
                case "clear", "cl" -> clearTerminal();
                case "list", "ls" -> displayListOfCommands(commands);
                case "quit", "q" -> { exitPage(); return; }
                default -> insertControlChars('n', 1);
            }
        }
    }

    private static void sayHello() {
        insertControlChars('n', 1);
        message("Hello from MyExtension!", getgetLayoutColor()(), getDefaultTextAlignment(), getDefaultDelay(), out::print);
    }

    private static final String[][] commands = {
        {"Say Hello", "sh"},
        {"Restart", "rs"},
        {"Clear", "cl"},
        {"List", "ls"},
        {"Quit", "q"}
    };

    @Override
    protected void displayListOfCommands(String[][] commands) {
        super.displayListOfCommands(commands);
    }
}
```

### **What's Happening Here?**
- The `displayMenu()` method shows the extension menu and handles commands.
- The `sayHello()` method prints a welcome message.
- The `commands` field stores available commands.
- The `displayListOfCommands()` method displays the list of commands.

---
## **4. Connecting the Extension to `CommandHandler`**
Navigate to:
```sh
src/main/java/core/commands/CommandHandler.java
```
### **Add the Command to Arrays**
Find the `fullCmds` array and add your command:
```java
"myextension"
```
Find the `shortCmds` array and add a shortcut:
```java
"me"
```

### **Register the Command in the Handler**
In the `getCommandAction()` method, add:
```java
case Y -> MyExtensionPage::displayMenu;
```
Where `Y` is the index of your command in the `fullCmds` and `shortCmds` arrays.

---
## **5. Adding a Description to the `help` Command (Optional)**
Navigate to:
```sh
src/main/java/core/ui/essential/configs/DisplayManager.java
```
Add a description in the `rules` array:
```java
formatCommandWithDescription(fullCmds[Y], shortCmds[Y], "Launches MyExtension"),
```
---
## **6. Running and Testing**
### **Run the Extension**
1. Build and run the project.
2. Enter `myextension` or `me` to launch your extension.
3. Enter `h` to see the message `Hello from MyExtension!`.
4. Test other commands (`rs`, `cl`, `ls`, `q`).

### **Debugging Tips**
If something isn't working:
- Check that `MyExtensionPage` is correctly added to `CommandHandler`.
- Ensure all necessary imports are included (`import static ...`).
- Use `System.out.println()` for debugging.

---
## **Conclusion**
Now you have a working extension for **MultiCLIA**! You can add new commands and expand functionality as needed.

