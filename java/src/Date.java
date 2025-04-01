import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Date implements Comparable<Date> {
    private int day, month, year;

    public Date(int day, int month, int year) {
        if (!isValidDate(day, month, year)) {
            throw new IllegalArgumentException("Invalid date!");
        }
        this.day = day;
        this.month = month;
        this.year = year;
    }
    
    private static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }
    
    private boolean isValidDate(int day, int month, int year) {
        if (year < 0 || month < 1 || month > 12 || day < 1) return false;
        int[] daysInMonth = {31, isLeapYear(year) ? 29 : 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        return day <= daysInMonth[month - 1];
    }


    public void updateDate(int day, int month, int year) {
        if (isValidDate(day, month, year)) {
            this.day = day;
            this.month = month;
            this.year = year;
        } else {
            throw new IllegalArgumentException("Invalid date!");
        }
    }

    public String getDayOfWeek() {
        int m = month < 3 ? month + 12 : month;
        int y = month < 3 ? year - 1 : year;
        int k = y % 100;
        int j = y / 100;
        int h = (day + 13 * (m + 1) / 5 + k + k / 4 + j / 4 - 2 * j) % 7;
        if (h < 0) h += 7;
        String[] days = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        return days[h];
    }

    public String calculateDifference(Date other) {
        int days1 = toDays(this);
        int days2 = toDays(other);
        int totalDays = Math.abs(days1 - days2);

        Date earlier = days1 < days2 ? this : other;
        Date later = days1 < days2 ? other : this;

        int years = later.year - earlier.year;
        int months = later.month - earlier.month + (years * 12);
        int days = later.day - earlier.day;

        if (days < 0) {
            months--;
            days += getDaysInMonth(earlier.month, earlier.year);
        }
        if (months < 0) {
            years--;
            months += 12;
        }
        if (years < 0) {
            years = 0;
            months = 0;
            days = totalDays;
        }

        int weeks = totalDays / 7;
        int remainingDays = totalDays % 7;

        return String.format("Difference: %d years, %d months, %d weeks, %d days (total %d days)",
                years, months, weeks, remainingDays, totalDays);
    }

    private int toDays(Date date) {
        int days = date.day;
        int[] daysInMonth = {31, isLeapYear(date.year) ? 29 : 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        for (int m = 0; m < date.month - 1; m++) {
            days += daysInMonth[m];
        }
        for (int y = 0; y < date.year; y++) {
            days += isLeapYear(y) ? 366 : 365;
        }
        return days;
    }

    private int getDaysInMonth(int month, int year) {
        int[] daysInMonth = {31, isLeapYear(year) ? 29 : 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
        return daysInMonth[month - 1];
    }

    public void printDate() {
        String[] months = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        System.out.println(months[month - 1] + " " + day + ", " + year);
    }

    @Override
    public int compareTo(Date other) {
        if (year != other.year) return year - other.year;
        if (month != other.month) return month - other.month;
        return day - other.day;
    }

    private static void printHeader(String title) {
        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("          " + title + "          ");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    private static void displayDates(ArrayList<Date> dates) {
        printHeader("SORTED DATES");
        for (int i = 0; i < dates.size(); i++) {
            System.out.print((i + 1) + ". ");
            dates.get(i).printDate();
            System.out.println("   Day of week: " + dates.get(i).getDayOfWeek());
        }
        if (dates.size() > 1) {
            System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println("         DIFFERENCE BETWEEN FIRST AND LAST DATE");
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            System.out.println(dates.get(0).calculateDifference(dates.get(dates.size() - 1)));
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Date> dates = new ArrayList<>();

        printHeader("DATE INPUT");
        System.out.println("Enter number of dates:");
        int count = getValidInt(scanner);

        for (int i = 0; i < count; i++) {
            System.out.println("\nEnter date #" + (i + 1) + " (day month year):");
            while (true) {
                try {
                    String[] input = scanner.nextLine().split(" ");
                    int day = Integer.parseInt(input[0]);
                    int month = Integer.parseInt(input[1]);
                    int year = Integer.parseInt(input[2]);
                    dates.add(new Date(day, month, year));
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid input! Try again (day month year):");
                }
            }
        }

        Collections.sort(dates);
        displayDates(dates);

        printHeader("DATE UPDATE");
        System.out.println("Would you like to update a date? (yes/no):");
        if (scanner.nextLine().trim().toLowerCase().equals("yes")) {
            System.out.println("Enter date number (1-" + dates.size() + "):");
            int index = getValidInt(scanner) - 1;
            if (index >= 0 && index < dates.size()) {
                System.out.println("Selected date: ");
                dates.get(index).printDate();
                System.out.println("\nEnter new date (day month year):");
                while (true) {
                    try {
                        String[] input = scanner.nextLine().split(" ");
                        int day = Integer.parseInt(input[0]);
                        int month = Integer.parseInt(input[1]);
                        int year = Integer.parseInt(input[2]);
                        dates.get(index).updateDate(day, month, year);
                        System.out.println("\nDate updated successfully!");
                        Collections.sort(dates);
                        displayDates(dates); // Пересчет и вывод после обновления
                        break;
                    } catch (Exception e) {
                        System.out.println("Invalid input! Try again (day month year):");
                    }
                }
            } else {
                System.out.println("Invalid date number!");
            }
        } else {
            System.out.println("No updates made.");
        }

        scanner.close();
    }

    private static int getValidInt(Scanner scanner) {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Enter a valid number:");
            }
        }
    }
}