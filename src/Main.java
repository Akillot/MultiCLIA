import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(" ");
        for (;;) {
            try {
                System.out.println("-------------------\nEnter your numbers.");
                System.out.println("-------------------\nUse a 'SPACE' \nTo split numbers.\n-------------------");
                String mathStatementString = scanner.nextLine();
                operationMenu();
                System.out.print("Your choice is: ");
                String operation = scanner.nextLine();
                System.out.println("-------------------");

                switch (operation) {
                    case "sum":
                        sum(mathStatementString);
                        break;
                    case "subtract":
                        subtract(mathStatementString);
                        break;
                    case "multi":
                        multi(mathStatementString);
                        break;
                    case "divide":
                        divide(mathStatementString);
                        break;
                    case "power":
                        power(mathStatementString);
                        break;
                    case "exit":
                        return;
                    default:
                        System.out.println("Invalid operation.\n-------------------\n");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    static void sum(String mathStatement) {
        String[] nums = mathStatement.split(" ");
        double answer = 0;
        for (String num : nums) {
            try {
                double numDouble = Double.parseDouble(num);
                answer += numDouble;
            } catch (NumberFormatException e) {
                System.out.println(num + " is not a number");
            }
        }
        System.out.println("Answer: " + answer + "\n-------------------\n");
    }

    static void subtract(String mathStatement) {
        String[] nums = mathStatement.split(" ");
        if (nums.length == 0) {
            System.out.println("No numbers to subtract.");
            return;
        }

        double answer;
        try {
            answer = Double.parseDouble(nums[0]);
        } catch (NumberFormatException e) {
            System.out.println(nums[0] + " is not a number");
            return;
        }

        for (int i = 1; i < nums.length; i++) {
            try {
                double numDouble = Double.parseDouble(nums[i]);
                answer -= numDouble;
            } catch (NumberFormatException e) {
                System.out.println(nums[i] + " is not a number");
            }
        }
        System.out.println("Answer: " + answer + "\n-------------------\n");
    }

    static void multi(String mathStatement) {
        String[] nums = mathStatement.split(" ");
        if (nums.length == 0) {
            System.out.println("No numbers to multiply.");
            return;
        }

        double answer = 1;
        for (String num : nums) {
            try {
                double numDouble = Double.parseDouble(num);
                answer *= numDouble;
            } catch (NumberFormatException e) {
                System.out.println(num + " is not a number");
            }
        }
        System.out.println("Answer: " + answer + "\n-------------------\n");
    }

    static void divide(String mathStatement) {
        String[] nums = mathStatement.split(" ");
        if (nums.length == 0) {
            System.out.println("No numbers to divide.");
            return;
        }

        double answer;
        try {
            answer = Double.parseDouble(nums[0]);
        } catch (NumberFormatException e) {
            System.out.println(nums[0] + " is not a number");
            return;
        }

        for (int i = 1; i < nums.length; i++) {
            try {
                double numDouble = Double.parseDouble(nums[i]);
                if (numDouble == 0) {
                    System.out.println("Division by zero detected.\nAborting operation.");
                    return;
                }
                answer /= numDouble;
            } catch (NumberFormatException e) {
                System.out.println(nums[i] + " is not a number");
            }
        }
        System.out.println("Answer: " + answer + "\n-------------------");
    }

    static void power(String mathStatement) {
        String[] nums = mathStatement.split(" ");
        if (nums.length == 0) {
            System.out.println("No numbers to process.");
            return;
        }

        double answer;
        try {
            answer = Double.parseDouble(nums[0]);
        } catch (NumberFormatException e) {
            System.out.println(nums[0] + " is not a number");
            return;
        }

        for (int i = 1; i < nums.length; i++) {
            try {
                double numDouble = Double.parseDouble(nums[i]);
                answer = Math.pow(answer, numDouble);
            } catch (NumberFormatException e) {
                System.out.println(nums[i] + " is not a number");
                return;
            }
        }
        System.out.println("Answer: " + answer + "\n-------------------");
    }


    static void operationMenu() {
        System.out.println("-------------------\nOperations:\n" +
                "1. sum\n2. subtract\n3. multi\n4. divide\n5. power\n6. exit\n-------------------");
    }
}
