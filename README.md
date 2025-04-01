
## Project Overview
This project implements a `Date` class in Java to manage and manipulate dates. The class supports creating date objects, validating their correctness, updating values, determining the day of the week, calculating the difference between dates, and sorting a list of dates. The `Main` class demonstrates these features, including handling invalid inputs.

Key features:
- Date validation with leap year consideration.
- Date updating with validation.
- Day of the week calculation.
- Difference between two dates in days.
- Readable date output (e.g., "January 1, 2023").
- Sorting a list of dates in ascending order (year, month, day).

## Compilation and Execution Instructions
1. Ensure you have the Java Development Kit (JDK) installed.
2. Save the code file as `Date.java`.
3. Open a terminal or command prompt and navigate to the file’s directory.
4. Compile the program with:  
5. Run the program with: "VScode" or "IJ"

6. Follow the console prompts: enter the number of dates, then the dates in "day month year" format. The program will also ask if you want to update a date.

## Additional Notes and Challenges
- Leap year validation is handled in the `isLeapYear` method, crucial for correctly managing February 29.
- The `getDayOfWeek` method uses Zeller’s algorithm to accurately determine the day of the week.
- The `calculateDifference` method accounts for leap years and provides the difference in years, months, weeks, and days.
- Invalid inputs (e.g., "32 13 2023") are handled with clear error messages.
- Date list sorting is implemented using the `Comparable<Date>` interface and `Collections.sort()`.
