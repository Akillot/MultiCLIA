# What is an Extension in MultiCLIA?

An extension is an additional mini-application inside MultiCLIA that utilizes the core development tools of the main software.

Extensions introduce new features for users or simplify certain aspects of existing functionalities, enhancing the overall experience of using MultiCLIA.

---

# How to Create an Extension in MultiCLIA?

## Step 0: Download MultiCLIA (If Not Installed)

If you already have MultiCLIA installed, you can skip this step.  
Otherwise, follow the instructions in the [README](https://github.com/Akillot/MultiCLIA/blob/master/README.md) to download and set up the application.

---

## Step 1: Open MultiCLIA in Your IDE
Open MultiCLIA in your preferred IDE, such as **IntelliJ IDEA**, **Eclipse**, or any other Java-compatible environment.

---

## Step 2: Navigate to the Extensions Folder
Go to:
```sh
src/main/java/core/ui/extensions
```
Create a **new folder** with the name of your extension.

---

## Step 3: Create the Necessary Classes

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

## Step 4: Structure of the Main Class `[XPage]`

### Creating a Method for Displaying the Page

Your `XPage` class should include a `displayPage()` method to handle the main logic.

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
            case "func 1", "/fn1" -> generateQrCode();
            case "func 2", "/fn2" -> saveQrCodeAsImage();
            case "func 3", "/fn3" -> modifyQrCodeSize();
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
```


