import java.util.Scanner;

public class Main {
    public Main() {
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(" ");

        while(true) {
            while(true) {
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
                    }
                } catch (Exception var6) {
                    Exception e = var6;
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }
    }

    static void sum(String mathStatement) {
        String[] nums = mathStatement.split(" ");
        double answer = 0.0;
        String[] var4 = nums;
        int var5 = nums.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            String num = var4[var6];

            try {
                double numDouble = Double.parseDouble(num);
                answer += numDouble;
            } catch (NumberFormatException var10) {
                System.out.println(num + " is not a number");
            }
        }

        System.out.println("Answer: " + answer + "\n-------------------\n");
    }

    static void subtract(String mathStatement) {
        String[] nums = mathStatement.split(" ");
        if (nums.length == 0) {
            System.out.println("No numbers to subtract.");
        } else {
            double answer;
            try {
                answer = Double.parseDouble(nums[0]);
            } catch (NumberFormatException var8) {
                System.out.println(nums[0] + " is not a number");
                return;
            }

            for(int i = 1; i < nums.length; ++i) {
                try {
                    double numDouble = Double.parseDouble(nums[i]);
                    answer -= numDouble;
                } catch (NumberFormatException var7) {
                    System.out.println(nums[i] + " is not a number");
                }
            }

            System.out.println("Answer: " + answer + "\n-------------------\n");
        }
    }

    static void multi(String mathStatement) {
        String[] nums = mathStatement.split(" ");
        if (nums.length == 0) {
            System.out.println("No numbers to multiply.");
        } else {
            double answer = 1.0;
            String[] var4 = nums;
            int var5 = nums.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                String num = var4[var6];

                try {
                    double numDouble = Double.parseDouble(num);
                    answer *= numDouble;
                } catch (NumberFormatException var10) {
                    System.out.println(num + " is not a number");
                }
            }

            System.out.println("Answer: " + answer + "\n-------------------\n");
        }
    }

    static void divide(String mathStatement) {
        String[] nums = mathStatement.split(" ");
        if (nums.length == 0) {
            System.out.println("No numbers to divide.");
        } else {
            double answer;
            try {
                answer = Double.parseDouble(nums[0]);
            } catch (NumberFormatException var8) {
                System.out.println(nums[0] + " is not a number");
                return;
            }

            for(int i = 1; i < nums.length; ++i) {
                try {
                    double numDouble = Double.parseDouble(nums[i]);
                    if (numDouble == 0.0) {
                        System.out.println("Division by zero detected.\nAborting operation.");
                        return;
                    }

                    answer /= numDouble;
                } catch (NumberFormatException var7) {
                    System.out.println(nums[i] + " is not a number");
                }
            }

            System.out.println("Answer: " + answer + "\n-------------------");
        }
    }

    static void power(String mathStatement) {
        String[] nums = mathStatement.split(" ");
        if (nums.length == 0) {
            System.out.println("No numbers to process.");
        } else {
            double answer;
            try {
                answer = Double.parseDouble(nums[0]);
            } catch (NumberFormatException var8) {
                System.out.println(nums[0] + " is not a number");
                return;
            }

            for(int i = 1; i < nums.length; ++i) {
                try {
                    double numDouble = Double.parseDouble(nums[i]);
                    answer = Math.pow(answer, numDouble);
                } catch (NumberFormatException var7) {
                    System.out.println(nums[i] + " is not a number");
                    return;
                }
            }

            System.out.println("Answer: " + answer + "\n-------------------");
        }
    }

    static void operationMenu() {
        System.out.println("-------------------\nOperations:\n1. sum\n2. subtract\n3. multi\n4. divide\n5. power\n6. exit\n-------------------");
    }
}
