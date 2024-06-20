public class BasicOperations {
    static double result;
    // Summation
    static  void sum(String mathStatement) {
        String[] nums = mathStatement.trim().split("\\s+");
        result = 0;
        for (String num : nums) {
            try {
                double numDouble = Double.parseDouble(num);
                result += numDouble;
            } catch (NumberFormatException e) {
                System.out.println(num + " is not a number");
                return;
            }
        }
        System.out.println("Answer: " + result + "\n" + AdditionalOperations.border + "\n");
    }

    // Subtraction
    static void sub(String mathStatement) {
        String[] nums = mathStatement.trim().split("\\s+");
        if (nums.length == 0) {
            System.out.println("No numbers to subtract.");
            return;
        }
        result = 0;
        try {
            result = Double.parseDouble(nums[0]);
        } catch (NumberFormatException e) {
            System.out.println(nums[0] + " is not a number");
            return;
        }
        for (int i = 1; i < nums.length; i++) {
            try {
                double numDouble = Double.parseDouble(nums[i]);
                result -= numDouble;
            } catch (NumberFormatException e) {
                System.out.println(nums[i] + " is not a number");
            }
        }
        System.out.println("Answer: " + result + "\n" + AdditionalOperations.border + "\n");
    }

    // Multiplication
    static void multi(String mathStatement) {
        String[] nums = mathStatement.trim().split("\\s+");
        if (nums.length == 0) {
            System.out.println("No numbers to multiply.");
            return;
        }
        result = 1;
        for (String num : nums) {
            try {
                double numDouble = Double.parseDouble(num);
                result *= numDouble;
            } catch (NumberFormatException e) {
                System.out.println(num + " is not a number");
                return;
            }
        }
        System.out.println("Answer: " + result + "\n" + AdditionalOperations.border + "\n");
    }

    // Division
    static void divide(String mathStatement) {
        String[] nums = mathStatement.trim().split("\\s+");
        if (nums.length == 0) {
            System.out.println("No numbers to divide.");
            return;
        }
        result = 1;
        try {
            result = Double.parseDouble(nums[0]);
        } catch (NumberFormatException e) {
            System.out.println(nums[0] + " is not a number");
            return;
        }
        for (int i = 1; i < nums.length; i++) {
            try {
                double numDouble = Double.parseDouble(nums[i]);
                if (numDouble == 0) {
                    System.out.println("Division by zero detected. Aborting operation.");
                    return;
                }
                result /= numDouble;
            } catch (NumberFormatException e) {
                System.out.println(nums[i] + " is not a number");
                return;
            }
        }
        System.out.println("Answer: " + result + "\n" + AdditionalOperations.border + "\n");
    }

    // Power
    static void pow(String mathStatement) {
        String[] nums = mathStatement.trim().split("\\s+");
        if (nums.length == 0) {
            System.out.println("No numbers to process.");
            return;
        }
        result = 1;
        try {
            result = Double.parseDouble(nums[0]);
        } catch (NumberFormatException e) {
            System.out.println(nums[0] + " is not a number");
            return;
        }
        for (int i = 1; i < nums.length; i++) {
            try {
                double numDouble = Double.parseDouble(nums[i]);
                result = Math.pow(result, numDouble);
            } catch (NumberFormatException e) {
                System.out.println(nums[i] + " is not a number");
                return;
            }
        }
        System.out.println("Answer: " + result + "\n" + AdditionalOperations.border + "\n");
    }

    // Show list of operations
    static void commandList() {
        System.out.println(AdditionalOperations.border + "\nOperations:\n1. sum[+]\n2. sub[-]\n3. multi[*]\n4. div[/]\n5. pow[^]\n6. info[i]\n7. exit[x]\n" + AdditionalOperations.border);
    }

    // Show version
    static void versionInfo() {
        System.out.println("Current version:\n0.2.3");
    }
}
