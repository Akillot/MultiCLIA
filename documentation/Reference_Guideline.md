# **MultiCLIA Reference Guideline**

## **Introduction**
This guide provides an overview of the core methods available in **MultiCLIA**. Developers can use these methods to interact with the system, build extensions, and enhance functionality efficiently. The methods are grouped by their purpose for easier navigation.

---
## **1. User Interaction Methods**
These methods handle user input and output.

### **Displaying Messages**
```java
message(String text, String color, String alignment, int delay, Consumer<String> output)
```
- Displays a formatted message with color, alignment, and delay.

```java
slowMotionText(int delay, String alignment, boolean newLine, String... text)
```
- Prints text with a typing effect.

### **Handling User Input**
```java
String input = scanner.nextLine().trim().toLowerCase();
```
- Reads user input and formats it.

---
## **2. Page Navigation Methods**
These methods help navigate between different pages in the CLI.

```java
mainMenuRerun()
```
- Restarts the main menu.

```java
exitPage()
```
- Exits the current extension or menu.

```java
clearTerminal()
```
- Clears the CLI screen.

---
## **3. Command Handling Methods**
These methods manage user commands.

```java
displayListOfCommands(String[][] commands)
```
- Displays available commands.

```java
case "command" -> actionMethod();
```
- Handles user input and executes corresponding methods.

---
## **4. Styling and Layout Methods**
These methods help format text output.

```java
marginBorder(int top, int bottom)
```
- Adds spacing above and below content.

```java
insertControlChars(char type, int count)
```
- Inserts control characters (e.g., new lines).

---
## **5. Configuration and Utility Methods**
Useful for managing system settings and debugging.

```java
getDefaultTextAlignment()
```
- Returns the default text alignment setting.

```java
getDefaultDelay()
```
- Retrieves the default text delay setting.

```java
getColor(String colorCode)
```
- Converts a color code into a usable format.

---
## **Conclusion**
These core methods form the foundation of **MultiCLIA** and allow developers to build powerful extensions.
By understanding and utilizing them effectively, developers can create seamless and efficient CLI experiences.

