# What is an extension in MultiCLIA?

An extension is an additional mini-application inside MultiCLIA that utilizes the core development tools of the main software.

Extensions introduce new features for users or simplify certain aspects of existing functionalities, enhancing the overall experience of using MultiCLIA.

---

# How to Create an extension in MultiCLIA?

## Step 0: Download MultiCLIA (If not installed)

If you already have MultiCLIA installed, you can skip this step.  
Otherwise, follow the instructions in the [README](https://github.com/Akillot/MultiCLIA/blob/master/README.md) to download and set up the application.

---

## Step 1: Open MultiCLIA in your IDE
Open MultiCLIA in your preferred IDE, such as **IntelliJ IDEA**, **Eclipse**, or any other Java-compatible environment.

---

## Step 2: Navigate to the extensions folder

Go to:
```sh
src/main/java/core/ui/extensions
```
Create a **new folder** with the name of your extension.

---

## Step 3: Create the necessary classes

Inside your new folder, create the following classes:

1. **Main Class**
    - Name your first class following this format:
      ```sh
      XPage
      ```
      Where `X` is the name of your extension.

2. **Configuration Class (If Needed)**
    - If your extension requires configurations, create another class:
      ```sh
      XConfigs
      ```
      Where `X` is the name of your extension.

3. **Additional Classes (If Required)**
    - If you need extra classes for specific functionalities, name them following the same pattern for clarity and structure.

---

## Step 4: Structure of the main class `XPage`

### Creating a method for displaying the page

Your `XPage` class should include a `displayXPage()` method to handle the main logic.

```java
public static void displayXPage() {
    marginBorder(1, 2);
    message("X:", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);

    displayListOfCommands();

    while (true) {
        slowMotionText(getDefaultDelay(), getSearchingLineAlignment(), false,
                getColor(layoutColor) + searchingArrow, "");
        String input = scanner.nextLine().trim().toLowerCase();

        switch (input) {
            case "func 1", "/fn1" -> func1();
            case "func 2", "/fn2" -> func2();
            case "func 3", "/fn3" -> func3();
            case "restart", "/rs" -> {
                insertControlChars('n', 1);
                mainMenuRerun();
            }
            case "clear terminal", "/cl" -> clearTerminal();
            case "list", "/ls" -> displayListOfCommands();
            case "quit", "/q" -> {
                exitPage();
                return;
            }
            default -> insertControlChars('n', 1);
        }
    }
}

// Don`t forget about importing all neccessaries classes and packages.
```
Where: 

- ```X``` → The name of your extension.
- ```func 1``` → The full command.
- ```/fn1``` → The short command.
- ```func1()``` → The method that implements the first function.

### Creating a method for displaying all commands

Your `XPage` class should include a `displayListOfCommands` method to display all commands of your extension.

```java
private static void displayListOfCommands() {
        insertControlChars('n', 1);
        String[][] commands = {
                {"Func 1", "/fn1"},
                {"Func 2", "/fn2"},
                {"Func 3", "/fn3"},
                {"Restart", "/rs"},
                {"Clear terminal", "/cl"},
                {"List", "/ls"},
                {"Quit", "/q"}
        };

        for (String[] command : commands) {
            message("·  " + command[0] + " [" + getColor(mainColor) + command[1] + getColor(layoutColor) + "]",
                    layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
        }
        insertControlChars('n', 1);
    }
    
// The displayListOfCommands() method should typically be placed right after displayXPage().
```
### Creating methods for your extension functionality implementation

Your `XPage` class should include also your methods for realization of extension functions.

- Your methods should be private
- Your methods should have correct names

### Creating Methods for Your Extension Functionality Implementation

Your `XPage` class should also include methods for implementing the extension’s functions.

#### Method Guidelines:
- Your methods should be **private**.
- Your methods should have **clear and descriptive names**.

#### Example:

```java
private static void func1() {
    insertControlChars('n', 1);
    message("Executing Function 1...", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
    // Function 1 implementation here
}

private static void func2() {
    insertControlChars('n', 1);
    message("Executing Function 2...", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
    // Function 2 implementation here
}

private static void func3() {
    insertControlChars('n', 1);
    message("Executing Function 3...", layoutColor, getDefaultTextAlignment(), getDefaultDelay(), out::print);
    // Function 3 implementation here
}

// Ensure that each function performs a specific task and follows a consistent structure for readability and maintainability.
```

## Step 5: Connecting extension to main command handler class

Go to:
```sh
src/main/java/core/commands/CommandHandler.java
```
- In ```CommandHandler.java``` go to array ```fullCmds``` and add full command for your extension,
for example ```"extension"```, you should put this before command ```"quit"```.
It’s not necessary to place it before ```"quit"```, but doing so maintains a more structured command order.


- In ```CommandHandler.java``` go to array ```shortCmds``` and add short command for your extension, for example 
```"/ext"```, you should put this before command ```"/q"```.
  It’s not necessary to place it before ```"/q"```, but doing so maintains a more structured command order.


- Then in the same class go to ```getCommandAction``` and add ```case y -> XPage::displayXPage;```,
where y is the index of your commands in  ```fullCmds``` and ```shortCmds```.

## Step 6: Add a description to ```help``` command (Not necessary)

Go to:
```sh
src/main/java/core/ui/essential/configs/DisplayManager.java
```
Find an array ```rules``` there and put ```formatCommandWithDescription(fullCmds[y], shortCmds[y], "Some text"),```

Where ```y``` is the index of your commands in  ```fullCmds``` and ```shortCmds``` and in ```"Some text"``` you should put some description.