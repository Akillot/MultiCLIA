# MultiCLIA(formerly MathCLIA)

MultiCLIA (Multi Command Line Interface Application) is a simple command-line tool for performing basic arithmetic operations. 
It allows users to input a list of numbers separated by spaces and choose an operation to perform on them: addition, subtraction, multiplication, or division and also power.

## Usage

To use MultiCLIA, follow these steps:

1. Clone the repository to your local machine.
2. Compile the Main.java file using a Java compiler.
3. Run the compiled Java program.
4. Enter a list of numbers separated by spaces when prompted.
5. Choose an operation from the menu and press Enter.
6. View the result of the operation in the console.

## Operations

MultiCLIA supports the following operations:

1. **sum**: Add all the numbers together.
2. **subtract**: Subtract subsequent numbers from the first one.
3. **multi**: Multiply all the numbers together.
4. **divide**: Divide the first number by subsequent numbers.
5. **power**: Raise the first number to the power of each subsequent number sequentially.

## Example
```
-------------------
Enter your numbers.
-------------------
Use a 'SPACE' 
To split numbers.
-------------------
23 44.1 44
-------------------
Operations:
1. sum
2. subtract
3. multi
4. divide
5. power
6. exit
-------------------
Your choice is: multi
-------------------
Answer: 44629.200000000004
-------------------

-------------------
Enter your numbers.
-------------------
Use a 'SPACE' 
To split numbers.
-------------------
9974 33 1 1 1 1 2 4 55
-------------------
Operations:
1. sum
2. subtract
3. multi
4. divide
5. exit
-------------------
Your choice is: subtract
-------------------
Answer: 9876.0
-------------------

-------------------
Enter your numbers.
-------------------
Use a 'SPACE' 
To split numbers.
-------------------
23 44
-------------------
Operations:
1. sum
2. subtract
3. multi
4. divide
5. exit
-------------------
Your choice is: exit
-------------------
```
