import layout.UserInterface;

public class Main {
    public static void main(String[] args) {
        while (true) {
            try {
                UserInterface.displayMenu();
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }
}